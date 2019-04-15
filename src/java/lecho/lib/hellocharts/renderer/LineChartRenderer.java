/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$FontMetricsInt
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  java.lang.Float
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
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
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.Iterator;
import java.util.List;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.formatter.LineChartValueFormatter;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.provider.LineChartDataProvider;
import lecho.lib.hellocharts.renderer.AbstractChartRenderer;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;

public class LineChartRenderer
extends AbstractChartRenderer {
    private static final int DEFAULT_LINE_STROKE_WIDTH_DP = 3;
    private static final int DEFAULT_TOUCH_TOLERANCE_MARGIN_DP = 4;
    private static final float LINE_SMOOTHNESS = 0.16f;
    private static final int MODE_DRAW = 0;
    private static final int MODE_HIGHLIGHT = 1;
    private float baseValue;
    private int checkPrecision;
    private LineChartDataProvider dataProvider;
    private Paint linePaint = new Paint();
    private Path path = new Path();
    private Paint pointPaint = new Paint();
    private Bitmap softwareBitmap;
    private Canvas softwareCanvas = new Canvas();
    private Viewport tempMaximumViewport = new Viewport();
    private int touchToleranceMargin;

    public LineChartRenderer(Context context, Chart chart, LineChartDataProvider lineChartDataProvider) {
        super(context, chart);
        this.dataProvider = lineChartDataProvider;
        this.touchToleranceMargin = ChartUtils.dp2px(this.density, 4);
        this.linePaint.setAntiAlias(true);
        this.linePaint.setStyle(Paint.Style.STROKE);
        this.linePaint.setStrokeCap(Paint.Cap.ROUND);
        this.linePaint.setStrokeWidth((float)ChartUtils.dp2px(this.density, 3));
        this.pointPaint.setAntiAlias(true);
        this.pointPaint.setStyle(Paint.Style.FILL);
        this.checkPrecision = ChartUtils.dp2px(this.density, 2);
    }

    private int calculateContentRectInternalMargin() {
        int n = 0;
        for (Line line : this.dataProvider.getLineChartData().getLines()) {
            int n2;
            if (!this.checkIfShouldDrawPoints(line) || (n2 = 4 + line.getPointRadius()) <= n) continue;
            n = n2;
        }
        return ChartUtils.dp2px(this.density, n);
    }

    private void calculateMaxViewport() {
        this.tempMaximumViewport.set(Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_VALUE, Float.MAX_VALUE);
        Iterator iterator = this.dataProvider.getLineChartData().getLines().iterator();
        while (iterator.hasNext()) {
            for (PointValue pointValue : ((Line)iterator.next()).getValues()) {
                if (pointValue.getX() < this.tempMaximumViewport.left) {
                    this.tempMaximumViewport.left = pointValue.getX();
                }
                if (pointValue.getX() > this.tempMaximumViewport.right) {
                    this.tempMaximumViewport.right = pointValue.getX();
                }
                if (pointValue.getY() < this.tempMaximumViewport.bottom) {
                    this.tempMaximumViewport.bottom = pointValue.getY();
                }
                if (!(pointValue.getY() > this.tempMaximumViewport.top)) continue;
                this.tempMaximumViewport.top = pointValue.getY();
            }
        }
    }

    private boolean checkIfShouldDrawPoints(Line line) {
        return line.hasPoints() || line.getValues().size() == 1;
    }

    private void drawArea(Canvas canvas, Line line) {
        int n = line.getValues().size();
        if (n < 2) {
            return;
        }
        Rect rect = this.computator.getContentRectMinusAllMargins();
        float f = Math.min((float)rect.bottom, (float)Math.max((float)this.computator.computeRawY(this.baseValue), (float)rect.top));
        float f2 = Math.max((float)this.computator.computeRawX(((PointValue)line.getValues().get(0)).getX()), (float)rect.left);
        float f3 = Math.min((float)this.computator.computeRawX(((PointValue)line.getValues().get(n - 1)).getX()), (float)rect.right);
        this.path.lineTo(f3, f);
        this.path.lineTo(f2, f);
        this.path.close();
        this.linePaint.setStyle(Paint.Style.FILL);
        this.linePaint.setAlpha(line.getAreaTransparency());
        canvas.drawPath(this.path, this.linePaint);
        this.linePaint.setStyle(Paint.Style.STROKE);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawLabel(Canvas canvas, Line line, PointValue pointValue, float f, float f2, float f3) {
        float f4;
        float f5;
        Rect rect = this.computator.getContentRectMinusAllMargins();
        int n = line.getFormatter().formatChartValue(this.labelBuffer, pointValue);
        if (n == 0) {
            return;
        }
        float f6 = this.labelPaint.measureText(this.labelBuffer, this.labelBuffer.length - n, n);
        int n2 = Math.abs((int)this.fontMetrics.ascent);
        float f7 = f - f6 / 2.0f - (float)this.labelMargin;
        float f8 = f + f6 / 2.0f + (float)this.labelMargin;
        if (pointValue.getY() >= this.baseValue) {
            f4 = f2 - f3 - (float)n2 - (float)(2 * this.labelMargin);
            f5 = f2 - f3;
        } else {
            f4 = f2 + f3;
            f5 = f2 + f3 + (float)n2 + (float)(2 * this.labelMargin);
        }
        if (f4 < (float)rect.top) {
            f4 = f2 + f3;
            f5 = f2 + f3 + (float)n2 + (float)(2 * this.labelMargin);
        }
        if (f5 > (float)rect.bottom) {
            f4 = f2 - f3 - (float)n2 - (float)(2 * this.labelMargin);
            f5 = f2 - f3;
        }
        if (f7 < (float)rect.left) {
            f7 = f;
            f8 = f + f6 + (float)(2 * this.labelMargin);
        }
        if (f8 > (float)rect.right) {
            f7 = f - f6 - (float)(2 * this.labelMargin);
            f8 = f;
        }
        this.labelBackgroundRect.set(f7, f4, f8, f5);
        this.drawLabelTextAndBackground(canvas, this.labelBuffer, this.labelBuffer.length - n, n, line.getDarkenColor());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawPath(Canvas canvas, Line line) {
        LineChartRenderer.super.prepareLinePaint(line);
        int n = 0;
        for (PointValue pointValue : line.getValues()) {
            float f = this.computator.computeRawX(pointValue.getX());
            float f2 = this.computator.computeRawY(pointValue.getY());
            if (n == 0) {
                this.path.moveTo(f, f2);
            } else {
                this.path.lineTo(f, f2);
            }
            ++n;
        }
        canvas.drawPath(this.path, this.linePaint);
        if (line.isFilled()) {
            LineChartRenderer.super.drawArea(canvas, line);
        }
        this.path.reset();
    }

    private void drawPoint(Canvas canvas, Line line, PointValue pointValue, float f, float f2, float f3) {
        if (ValueShape.SQUARE.equals((Object)line.getShape())) {
            canvas.drawRect(f - f3, f2 - f3, f + f3, f2 + f3, this.pointPaint);
            return;
        }
        if (ValueShape.CIRCLE.equals((Object)line.getShape())) {
            canvas.drawCircle(f, f2, f3, this.pointPaint);
            return;
        }
        if (ValueShape.DIAMOND.equals((Object)line.getShape())) {
            canvas.save();
            canvas.rotate(45.0f, f, f2);
            canvas.drawRect(f - f3, f2 - f3, f + f3, f2 + f3, this.pointPaint);
            canvas.restore();
            return;
        }
        throw new IllegalArgumentException("Invalid point shape: " + (Object)((Object)line.getShape()));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawPoints(Canvas canvas, Line line, int n, int n2) {
        this.pointPaint.setColor(line.getPointColor());
        int n3 = 0;
        Iterator iterator = line.getValues().iterator();
        while (iterator.hasNext()) {
            float f;
            PointValue pointValue = (PointValue)iterator.next();
            int n4 = ChartUtils.dp2px(this.density, line.getPointRadius());
            float f2 = this.computator.computeRawX(pointValue.getX());
            if (this.computator.isWithinContentRect(f2, f = this.computator.computeRawY(pointValue.getY()), this.checkPrecision)) {
                if (n2 == 0) {
                    LineChartRenderer.super.drawPoint(canvas, line, pointValue, f2, f, n4);
                    if (line.hasLabels()) {
                        LineChartRenderer.super.drawLabel(canvas, line, pointValue, f2, f, n4 + this.labelOffset);
                    }
                } else {
                    if (1 != n2) {
                        throw new IllegalStateException("Cannot process points in mode: " + n2);
                    }
                    LineChartRenderer.super.highlightPoint(canvas, line, pointValue, f2, f, n, n3);
                }
            }
            ++n3;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawSmoothPath(Canvas canvas, Line line) {
        LineChartRenderer.super.prepareLinePaint(line);
        int n = line.getValues().size();
        float f = Float.NaN;
        float f2 = Float.NaN;
        float f3 = Float.NaN;
        float f4 = Float.NaN;
        float f5 = Float.NaN;
        float f6 = Float.NaN;
        for (int i = 0; i < n; ++i) {
            float f7;
            float f8;
            int n2;
            if (Float.isNaN((float)f5)) {
                PointValue pointValue = (PointValue)line.getValues().get(i);
                f5 = this.computator.computeRawX(pointValue.getX());
                f6 = this.computator.computeRawY(pointValue.getY());
            }
            if (Float.isNaN((float)f3)) {
                if (i > 0) {
                    PointValue pointValue = (PointValue)line.getValues().get(i - 1);
                    f3 = this.computator.computeRawX(pointValue.getX());
                    f4 = this.computator.computeRawY(pointValue.getY());
                } else {
                    f3 = f5;
                    f4 = f6;
                }
            }
            if (Float.isNaN((float)f)) {
                if (i > 1) {
                    PointValue pointValue = (PointValue)line.getValues().get(i - 2);
                    f = this.computator.computeRawX(pointValue.getX());
                    f2 = this.computator.computeRawY(pointValue.getY());
                } else {
                    f = f3;
                    f2 = f4;
                }
            }
            if (i < (n2 = n - 1)) {
                PointValue pointValue = (PointValue)line.getValues().get(i + 1);
                f7 = this.computator.computeRawX(pointValue.getX());
                f8 = this.computator.computeRawY(pointValue.getY());
            } else {
                f7 = f5;
                f8 = f6;
            }
            if (i == 0) {
                this.path.moveTo(f5, f6);
            } else {
                float f9 = f5 - f;
                float f10 = f6 - f2;
                float f11 = f7 - f3;
                float f12 = f8 - f4;
                float f13 = f3 + 0.16f * f9;
                float f14 = f4 + 0.16f * f10;
                float f15 = f5 - 0.16f * f11;
                float f16 = f6 - 0.16f * f12;
                this.path.cubicTo(f13, f14, f15, f16, f5, f6);
            }
            f = f3;
            f2 = f4;
            f3 = f5;
            f4 = f6;
            f5 = f7;
            f6 = f8;
        }
        canvas.drawPath(this.path, this.linePaint);
        if (line.isFilled()) {
            LineChartRenderer.super.drawArea(canvas, line);
        }
        this.path.reset();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawSquarePath(Canvas canvas, Line line) {
        LineChartRenderer.super.prepareLinePaint(line);
        int n = 0;
        float f = 0.0f;
        for (PointValue pointValue : line.getValues()) {
            float f2 = this.computator.computeRawX(pointValue.getX());
            float f3 = this.computator.computeRawY(pointValue.getY());
            if (n == 0) {
                this.path.moveTo(f2, f3);
            } else {
                this.path.lineTo(f2, f);
                this.path.lineTo(f2, f3);
            }
            f = f3;
            ++n;
        }
        canvas.drawPath(this.path, this.linePaint);
        if (line.isFilled()) {
            LineChartRenderer.super.drawArea(canvas, line);
        }
        this.path.reset();
    }

    private void highlightPoint(Canvas canvas, Line line, PointValue pointValue, float f, float f2, int n, int n2) {
        if (this.selectedValue.getFirstIndex() == n && this.selectedValue.getSecondIndex() == n2) {
            int n3 = ChartUtils.dp2px(this.density, line.getPointRadius());
            this.pointPaint.setColor(line.getDarkenColor());
            this.drawPoint(canvas, line, pointValue, f, f2, n3 + this.touchToleranceMargin);
            if (line.hasLabels() || line.hasLabelsOnlyForSelected()) {
                this.drawLabel(canvas, line, pointValue, f, f2, n3 + this.labelOffset);
            }
        }
    }

    private void highlightPoints(Canvas canvas) {
        int n = this.selectedValue.getFirstIndex();
        LineChartRenderer.super.drawPoints(canvas, (Line)this.dataProvider.getLineChartData().getLines().get(n), n, 1);
    }

    private boolean isInArea(float f, float f2, float f3, float f4, float f5) {
        float f6 = f3 - f;
        float f7 = f4 - f2;
        return Math.pow((double)f6, (double)2.0) + Math.pow((double)f7, (double)2.0) <= 2.0 * Math.pow((double)f5, (double)2.0);
    }

    private void prepareLinePaint(Line line) {
        this.linePaint.setStrokeWidth((float)ChartUtils.dp2px(this.density, line.getStrokeWidth()));
        this.linePaint.setColor(line.getColor());
        this.linePaint.setPathEffect(line.getPathEffect());
    }

    @Override
    public boolean checkTouch(float f, float f2) {
        this.selectedValue.clear();
        LineChartData lineChartData = this.dataProvider.getLineChartData();
        int n = 0;
        for (Line line : lineChartData.getLines()) {
            if (LineChartRenderer.super.checkIfShouldDrawPoints(line)) {
                int n2 = ChartUtils.dp2px(this.density, line.getPointRadius());
                int n3 = 0;
                for (PointValue pointValue : line.getValues()) {
                    if (LineChartRenderer.super.isInArea(this.computator.computeRawX(pointValue.getX()), this.computator.computeRawY(pointValue.getY()), f, f2, n2 + this.touchToleranceMargin)) {
                        this.selectedValue.set(n, n3, SelectedValue.SelectedValueType.LINE);
                    }
                    ++n3;
                }
            }
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
        LineChartData lineChartData = this.dataProvider.getLineChartData();
        if (this.softwareBitmap != null) {
            canvas2 = this.softwareCanvas;
            canvas2.drawColor(0, PorterDuff.Mode.CLEAR);
        } else {
            canvas2 = canvas;
        }
        for (Line line : lineChartData.getLines()) {
            if (!line.hasLines()) continue;
            if (line.isCubic()) {
                LineChartRenderer.super.drawSmoothPath(canvas2, line);
                continue;
            }
            if (line.isSquare()) {
                LineChartRenderer.super.drawSquarePath(canvas2, line);
                continue;
            }
            LineChartRenderer.super.drawPath(canvas2, line);
        }
        if (this.softwareBitmap != null) {
            canvas.drawBitmap(this.softwareBitmap, 0.0f, 0.0f, null);
        }
    }

    @Override
    public void drawUnclipped(Canvas canvas) {
        LineChartData lineChartData = this.dataProvider.getLineChartData();
        int n = 0;
        for (Line line : lineChartData.getLines()) {
            if (LineChartRenderer.super.checkIfShouldDrawPoints(line)) {
                LineChartRenderer.super.drawPoints(canvas, line, n, 0);
            }
            ++n;
        }
        if (this.isTouched()) {
            LineChartRenderer.super.highlightPoints(canvas);
        }
    }

    @Override
    public void onChartDataChanged() {
        super.onChartDataChanged();
        int n = this.calculateContentRectInternalMargin();
        this.computator.insetContentRectByInternalMargins(n, n, n, n);
        this.baseValue = this.dataProvider.getLineChartData().getBaseValue();
        this.onChartViewportChanged();
    }

    @Override
    public void onChartSizeChanged() {
        int n = this.calculateContentRectInternalMargin();
        this.computator.insetContentRectByInternalMargins(n, n, n, n);
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
}

