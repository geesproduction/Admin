/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$FontMetricsInt
 *  android.graphics.Paint$Style
 *  android.graphics.PointF
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.graphics.Xfermode
 *  android.text.TextUtils
 *  java.lang.CharSequence
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Iterator
 *  java.util.List
 */
package lecho.lib.hellocharts.renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.text.TextUtils;
import java.util.Iterator;
import java.util.List;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.formatter.PieChartValueFormatter;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.provider.PieChartDataProvider;
import lecho.lib.hellocharts.renderer.AbstractChartRenderer;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;

public class PieChartRenderer
extends AbstractChartRenderer {
    private static final float DEFAULT_LABEL_INSIDE_RADIUS_FACTOR = 0.7f;
    private static final float DEFAULT_LABEL_OUTSIDE_RADIUS_FACTOR = 1.0f;
    private static final int DEFAULT_START_ROTATION = 45;
    private static final int DEFAULT_TOUCH_ADDITIONAL_DP = 8;
    private static final float MAX_WIDTH_HEIGHT = 100.0f;
    private static final int MODE_DRAW = 0;
    private static final int MODE_HIGHLIGHT = 1;
    private Paint centerCirclePaint = new Paint();
    private float centerCircleScale;
    private Paint.FontMetricsInt centerCircleText1FontMetrics = new Paint.FontMetricsInt();
    private Paint centerCircleText1Paint = new Paint();
    private Paint.FontMetricsInt centerCircleText2FontMetrics = new Paint.FontMetricsInt();
    private Paint centerCircleText2Paint = new Paint();
    private float circleFillRatio = 1.0f;
    private PieChartDataProvider dataProvider;
    private RectF drawCircleOval = new RectF();
    private boolean hasCenterCircle;
    private boolean hasLabels;
    private boolean hasLabelsOnlyForSelected;
    private boolean hasLabelsOutside;
    private float maxSum;
    private RectF originCircleOval = new RectF();
    private int rotation = 45;
    private Paint separationLinesPaint = new Paint();
    private Paint slicePaint = new Paint();
    private PointF sliceVector = new PointF();
    private Bitmap softwareBitmap;
    private Canvas softwareCanvas = new Canvas();
    private Viewport tempMaximumViewport = new Viewport();
    private int touchAdditional;
    private PieChartValueFormatter valueFormatter;

    public PieChartRenderer(Context context, Chart chart, PieChartDataProvider pieChartDataProvider) {
        super(context, chart);
        this.dataProvider = pieChartDataProvider;
        this.touchAdditional = ChartUtils.dp2px(this.density, 8);
        this.slicePaint.setAntiAlias(true);
        this.slicePaint.setStyle(Paint.Style.FILL);
        this.centerCirclePaint.setAntiAlias(true);
        this.centerCirclePaint.setStyle(Paint.Style.FILL);
        this.centerCirclePaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.centerCircleText1Paint.setAntiAlias(true);
        this.centerCircleText1Paint.setTextAlign(Paint.Align.CENTER);
        this.centerCircleText2Paint.setAntiAlias(true);
        this.centerCircleText2Paint.setTextAlign(Paint.Align.CENTER);
        this.separationLinesPaint.setAntiAlias(true);
        this.separationLinesPaint.setStyle(Paint.Style.STROKE);
        this.separationLinesPaint.setStrokeCap(Paint.Cap.ROUND);
        this.separationLinesPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.separationLinesPaint.setColor(0);
    }

    private void calculateCircleOval() {
        Rect rect = this.computator.getContentRectMinusAllMargins();
        float f = Math.min((float)((float)rect.width() / 2.0f), (float)((float)rect.height() / 2.0f));
        float f2 = rect.centerX();
        float f3 = rect.centerY();
        float f4 = f2 - f + (float)this.touchAdditional;
        float f5 = f3 - f + (float)this.touchAdditional;
        float f6 = f2 + f - (float)this.touchAdditional;
        float f7 = f3 + f - (float)this.touchAdditional;
        this.originCircleOval.set(f4, f5, f6, f7);
        float f8 = 0.5f * this.originCircleOval.width() * (1.0f - this.circleFillRatio);
        this.originCircleOval.inset(f8, f8);
    }

    private void calculateMaxViewport() {
        this.tempMaximumViewport.set(0.0f, 100.0f, 100.0f, 0.0f);
        this.maxSum = 0.0f;
        for (SliceValue sliceValue : this.dataProvider.getPieChartData().getValues()) {
            this.maxSum += Math.abs((float)sliceValue.getValue());
        }
    }

    private void drawCenterCircle(Canvas canvas) {
        int n;
        float f;
        PieChartData pieChartData;
        float f2;
        block3 : {
            block2 : {
                pieChartData = this.dataProvider.getPieChartData();
                float f3 = this.originCircleOval.width() / 2.0f * pieChartData.getCenterCircleScale();
                f = this.originCircleOval.centerX();
                f2 = this.originCircleOval.centerY();
                canvas.drawCircle(f, f2, f3, this.centerCirclePaint);
                if (TextUtils.isEmpty((CharSequence)pieChartData.getCenterText1())) break block2;
                n = Math.abs((int)this.centerCircleText1FontMetrics.ascent);
                if (TextUtils.isEmpty((CharSequence)pieChartData.getCenterText2())) break block3;
                int n2 = Math.abs((int)this.centerCircleText2FontMetrics.ascent);
                canvas.drawText(pieChartData.getCenterText1(), f, f2 - 0.2f * (float)n, this.centerCircleText1Paint);
                canvas.drawText(pieChartData.getCenterText2(), f, f2 + (float)n2, this.centerCircleText2Paint);
            }
            return;
        }
        canvas.drawText(pieChartData.getCenterText1(), f, f2 + (float)(n / 4), this.centerCircleText1Paint);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawLabel(Canvas canvas, SliceValue sliceValue, float f, float f2) {
        float f3;
        float f4;
        float f5;
        float f6;
        this.sliceVector.set((float)Math.cos((double)Math.toRadians((double)(f + f2 / 2.0f))), (float)Math.sin((double)Math.toRadians((double)(f + f2 / 2.0f))));
        PieChartRenderer.super.normalizeVector(this.sliceVector);
        int n = this.valueFormatter.formatChartValue(this.labelBuffer, sliceValue);
        if (n == 0) {
            return;
        }
        float f7 = this.labelPaint.measureText(this.labelBuffer, this.labelBuffer.length - n, n);
        int n2 = Math.abs((int)this.fontMetrics.ascent);
        float f8 = this.originCircleOval.centerX();
        float f9 = this.originCircleOval.centerY();
        float f10 = this.originCircleOval.width() / 2.0f;
        float f11 = this.hasLabelsOutside ? f10 * 1.0f : (this.hasCenterCircle ? f10 - (f10 - f10 * this.centerCircleScale) / 2.0f : f10 * 0.7f);
        float f12 = f8 + f11 * this.sliceVector.x;
        float f13 = f9 + f11 * this.sliceVector.y;
        if (this.hasLabelsOutside) {
            if (f12 > f8) {
                f5 = f12 + (float)this.labelMargin;
                f3 = f12 + f7 + (float)(3 * this.labelMargin);
            } else {
                f5 = f12 - f7 - (float)(3 * this.labelMargin);
                f3 = f12 - (float)this.labelMargin;
            }
            if (f13 > f9) {
                f6 = f13 + (float)this.labelMargin;
                f4 = f13 + (float)n2 + (float)(3 * this.labelMargin);
            } else {
                f6 = f13 - (float)n2 - (float)(3 * this.labelMargin);
                f4 = f13 - (float)this.labelMargin;
            }
        } else {
            f5 = f12 - f7 / 2.0f - (float)this.labelMargin;
            f3 = f12 + f7 / 2.0f + (float)this.labelMargin;
            f6 = f13 - (float)(n2 / 2) - (float)this.labelMargin;
            f4 = f13 + (float)(n2 / 2) + (float)this.labelMargin;
        }
        this.labelBackgroundRect.set(f5, f6, f3, f4);
        this.drawLabelTextAndBackground(canvas, this.labelBuffer, this.labelBuffer.length - n, n, sliceValue.getDarkenColor());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawSeparationLines(Canvas canvas) {
        int n;
        PieChartData pieChartData = this.dataProvider.getPieChartData();
        if (pieChartData.getValues().size() >= 2 && (n = ChartUtils.dp2px(this.density, pieChartData.getSlicesSpacing())) >= 1) {
            float f = 360.0f / this.maxSum;
            float f2 = this.rotation;
            float f3 = this.originCircleOval.width() / 2.0f;
            this.separationLinesPaint.setStrokeWidth((float)n);
            Iterator iterator = pieChartData.getValues().iterator();
            while (iterator.hasNext()) {
                float f4 = f * Math.abs((float)((SliceValue)iterator.next()).getValue());
                this.sliceVector.set((float)Math.cos((double)Math.toRadians((double)f2)), (float)Math.sin((double)Math.toRadians((double)f2)));
                PieChartRenderer.super.normalizeVector(this.sliceVector);
                float f5 = this.sliceVector.x * (f3 + (float)this.touchAdditional) + this.originCircleOval.centerX();
                float f6 = this.sliceVector.y * (f3 + (float)this.touchAdditional) + this.originCircleOval.centerY();
                canvas.drawLine(this.originCircleOval.centerX(), this.originCircleOval.centerY(), f5, f6, this.separationLinesPaint);
                f2 += f4;
            }
        }
    }

    private void drawSlice(Canvas canvas, SliceValue sliceValue, float f, float f2, int n) {
        this.sliceVector.set((float)Math.cos((double)Math.toRadians((double)(f + f2 / 2.0f))), (float)Math.sin((double)Math.toRadians((double)(f + f2 / 2.0f))));
        this.normalizeVector(this.sliceVector);
        this.drawCircleOval.set(this.originCircleOval);
        if (1 == n) {
            this.drawCircleOval.inset((float)(-this.touchAdditional), (float)(-this.touchAdditional));
            this.slicePaint.setColor(sliceValue.getDarkenColor());
            canvas.drawArc(this.drawCircleOval, f, f2, true, this.slicePaint);
            return;
        }
        this.slicePaint.setColor(sliceValue.getColor());
        canvas.drawArc(this.drawCircleOval, f, f2, true, this.slicePaint);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawSlices(Canvas canvas) {
        PieChartData pieChartData = this.dataProvider.getPieChartData();
        float f = 360.0f / this.maxSum;
        float f2 = this.rotation;
        int n = 0;
        Iterator iterator = pieChartData.getValues().iterator();
        while (iterator.hasNext()) {
            SliceValue sliceValue = (SliceValue)iterator.next();
            float f3 = f * Math.abs((float)sliceValue.getValue());
            if (this.isTouched() && this.selectedValue.getFirstIndex() == n) {
                PieChartRenderer.super.drawSlice(canvas, sliceValue, f2, f3, 1);
            } else {
                PieChartRenderer.super.drawSlice(canvas, sliceValue, f2, f3, 0);
            }
            f2 += f3;
            ++n;
        }
        return;
    }

    private void normalizeVector(PointF pointF) {
        float f = pointF.length();
        pointF.set(pointF.x / f, pointF.y / f);
    }

    private float pointToAngle(float f, float f2, float f3, float f4) {
        double d = f - f3;
        double d2 = f2 - f4;
        return 90.0f + (360.0f + (float)Math.toDegrees((double)Math.atan2((double)(-d), (double)d2))) % 360.0f;
    }

    @Override
    public boolean checkTouch(float f, float f2) {
        this.selectedValue.clear();
        PieChartData pieChartData = this.dataProvider.getPieChartData();
        float f3 = this.originCircleOval.centerX();
        float f4 = this.originCircleOval.centerY();
        float f5 = this.originCircleOval.width() / 2.0f;
        this.sliceVector.set(f - f3, f2 - f4);
        if (this.sliceVector.length() > f5 + (float)this.touchAdditional) {
            return false;
        }
        if (pieChartData.hasCenterCircle() && this.sliceVector.length() < f5 * pieChartData.getCenterCircleScale()) {
            return false;
        }
        float f6 = (360.0f + (PieChartRenderer.super.pointToAngle(f, f2, f3, f4) - (float)this.rotation)) % 360.0f;
        float f7 = 360.0f / this.maxSum;
        float f8 = 0.0f;
        int n = 0;
        Iterator iterator = pieChartData.getValues().iterator();
        while (iterator.hasNext()) {
            float f9 = f7 * Math.abs((float)((SliceValue)iterator.next()).getValue());
            if (f6 >= f8) {
                this.selectedValue.set(n, n, SelectedValue.SelectedValueType.NONE);
            }
            f8 += f9;
            ++n;
        }
        return this.isTouched();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void draw(Canvas canvas) {
        Canvas canvas2;
        if (this.softwareBitmap != null) {
            canvas2 = this.softwareCanvas;
            canvas2.drawColor(0, PorterDuff.Mode.CLEAR);
        } else {
            canvas2 = canvas;
        }
        PieChartRenderer.super.drawSlices(canvas2);
        PieChartRenderer.super.drawSeparationLines(canvas2);
        if (this.hasCenterCircle) {
            PieChartRenderer.super.drawCenterCircle(canvas2);
        }
        this.drawLabels(canvas2);
        if (this.softwareBitmap != null) {
            canvas.drawBitmap(this.softwareBitmap, 0.0f, 0.0f, null);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void drawLabels(Canvas canvas) {
        PieChartData pieChartData = this.dataProvider.getPieChartData();
        float f = 360.0f / this.maxSum;
        float f2 = this.rotation;
        int n = 0;
        Iterator iterator = pieChartData.getValues().iterator();
        while (iterator.hasNext()) {
            SliceValue sliceValue = (SliceValue)iterator.next();
            float f3 = f * Math.abs((float)sliceValue.getValue());
            if (this.isTouched()) {
                if (this.hasLabels) {
                    PieChartRenderer.super.drawLabel(canvas, sliceValue, f2, f3);
                } else if (this.hasLabelsOnlyForSelected && this.selectedValue.getFirstIndex() == n) {
                    PieChartRenderer.super.drawLabel(canvas, sliceValue, f2, f3);
                }
            } else if (this.hasLabels) {
                PieChartRenderer.super.drawLabel(canvas, sliceValue, f2, f3);
            }
            f2 += f3;
            ++n;
        }
        return;
    }

    @Override
    public void drawUnclipped(Canvas canvas) {
    }

    public int getChartRotation() {
        return this.rotation;
    }

    public float getCircleFillRatio() {
        return this.circleFillRatio;
    }

    public RectF getCircleOval() {
        return this.originCircleOval;
    }

    public SliceValue getValueForAngle(int n, SelectedValue selectedValue) {
        PieChartData pieChartData = this.dataProvider.getPieChartData();
        float f = (360.0f + (float)(n - this.rotation)) % 360.0f;
        float f2 = 360.0f / this.maxSum;
        float f3 = 0.0f;
        int n2 = 0;
        for (SliceValue sliceValue : pieChartData.getValues()) {
            float f4 = f2 * Math.abs((float)sliceValue.getValue());
            if (f >= f3) {
                if (selectedValue != null) {
                    selectedValue.set(n2, n2, SelectedValue.SelectedValueType.NONE);
                }
                return sliceValue;
            }
            f3 += f4;
            ++n2;
        }
        return null;
    }

    @Override
    public void onChartDataChanged() {
        super.onChartDataChanged();
        PieChartData pieChartData = this.dataProvider.getPieChartData();
        this.hasLabelsOutside = pieChartData.hasLabelsOutside();
        this.hasLabels = pieChartData.hasLabels();
        this.hasLabelsOnlyForSelected = pieChartData.hasLabelsOnlyForSelected();
        this.valueFormatter = pieChartData.getFormatter();
        this.hasCenterCircle = pieChartData.hasCenterCircle();
        this.centerCircleScale = pieChartData.getCenterCircleScale();
        this.centerCirclePaint.setColor(pieChartData.getCenterCircleColor());
        if (pieChartData.getCenterText1Typeface() != null) {
            this.centerCircleText1Paint.setTypeface(pieChartData.getCenterText1Typeface());
        }
        this.centerCircleText1Paint.setTextSize((float)ChartUtils.sp2px(this.scaledDensity, pieChartData.getCenterText1FontSize()));
        this.centerCircleText1Paint.setColor(pieChartData.getCenterText1Color());
        this.centerCircleText1Paint.getFontMetricsInt(this.centerCircleText1FontMetrics);
        if (pieChartData.getCenterText2Typeface() != null) {
            this.centerCircleText2Paint.setTypeface(pieChartData.getCenterText2Typeface());
        }
        this.centerCircleText2Paint.setTextSize((float)ChartUtils.sp2px(this.scaledDensity, pieChartData.getCenterText2FontSize()));
        this.centerCircleText2Paint.setColor(pieChartData.getCenterText2Color());
        this.centerCircleText2Paint.getFontMetricsInt(this.centerCircleText2FontMetrics);
        this.onChartViewportChanged();
    }

    @Override
    public void onChartSizeChanged() {
        this.calculateCircleOval();
        if (this.computator.getChartWidth() > 0 && this.computator.getChartHeight() > 0) {
            this.softwareBitmap = Bitmap.createBitmap((int)this.computator.getChartWidth(), (int)this.computator.getChartHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
            this.softwareCanvas.setBitmap(this.softwareBitmap);
        }
    }

    @Override
    public void onChartViewportChanged() {
        if (this.isViewportCalculationEnabled) {
            this.calculateMaxViewport();
            this.computator.setMaxViewport(this.tempMaximumViewport);
            this.computator.setCurrentViewport(this.computator.getMaximumViewport());
        }
    }

    public void setChartRotation(int n) {
        this.rotation = (360 + n % 360) % 360;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCircleFillRatio(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        this.circleFillRatio = f;
        PieChartRenderer.super.calculateCircleOval();
    }

    public void setCircleOval(RectF rectF) {
        this.originCircleOval = rectF;
    }
}

