/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$FontMetricsInt
 *  android.graphics.Paint$Style
 *  android.graphics.Rect
 *  android.graphics.Typeface
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Float
 *  java.lang.IllegalArgumentException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Array
 *  java.util.Iterator
 *  java.util.List
 */
package lecho.lib.hellocharts.renderer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.formatter.AxisValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.AxisAutoValues;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.util.FloatUtils;
import lecho.lib.hellocharts.view.Chart;

public class AxesRenderer {
    private static final int BOTTOM = 3;
    private static final int DEFAULT_AXIS_MARGIN_DP = 2;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int TOP;
    private static final char[] labelWidthChars;
    private AxisAutoValues[] autoValuesBufferTab;
    private float[][] autoValuesToDrawTab;
    private int axisMargin;
    private Chart chart;
    private ChartComputator computator;
    private float density;
    private Paint.FontMetricsInt[] fontMetricsTab;
    private float[] labelBaselineTab;
    private char[] labelBuffer;
    private int[] labelDimensionForMarginsTab;
    private int[] labelDimensionForStepsTab;
    private Paint[] labelPaintTab;
    private int[] labelTextAscentTab;
    private int[] labelTextDescentTab;
    private int[] labelWidthTab;
    private Paint[] linePaintTab;
    private float[][] linesDrawBufferTab;
    private float[] nameBaselineTab;
    private Paint[] namePaintTab;
    private float[][] rawValuesTab;
    private float scaledDensity;
    private float[] separationLineTab;
    private int[] tiltedLabelXTranslation;
    private int[] tiltedLabelYTranslation;
    private int[] valuesToDrawNumTab;
    private AxisValue[][] valuesToDrawTab;

    static {
        labelWidthChars = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};
    }

    public AxesRenderer(Context context, Chart chart) {
        Paint[] arrpaint = new Paint[]{new Paint(), new Paint(), new Paint(), new Paint()};
        this.labelPaintTab = arrpaint;
        Paint[] arrpaint2 = new Paint[]{new Paint(), new Paint(), new Paint(), new Paint()};
        this.namePaintTab = arrpaint2;
        Paint[] arrpaint3 = new Paint[]{new Paint(), new Paint(), new Paint(), new Paint()};
        this.linePaintTab = arrpaint3;
        this.nameBaselineTab = new float[4];
        this.labelBaselineTab = new float[4];
        this.separationLineTab = new float[4];
        this.labelWidthTab = new int[4];
        this.labelTextAscentTab = new int[4];
        this.labelTextDescentTab = new int[4];
        this.labelDimensionForMarginsTab = new int[4];
        this.labelDimensionForStepsTab = new int[4];
        this.tiltedLabelXTranslation = new int[4];
        this.tiltedLabelYTranslation = new int[4];
        Paint.FontMetricsInt[] arrfontMetricsInt = new Paint.FontMetricsInt[]{new Paint.FontMetricsInt(), new Paint.FontMetricsInt(), new Paint.FontMetricsInt(), new Paint.FontMetricsInt()};
        this.fontMetricsTab = arrfontMetricsInt;
        this.labelBuffer = new char[64];
        this.valuesToDrawNumTab = new int[4];
        int[] arrn = new int[]{4, 0};
        this.rawValuesTab = (float[][])Array.newInstance((Class)Float.TYPE, (int[])arrn);
        int[] arrn2 = new int[]{4, 0};
        this.autoValuesToDrawTab = (float[][])Array.newInstance((Class)Float.TYPE, (int[])arrn2);
        this.valuesToDrawTab = (AxisValue[][])Array.newInstance(AxisValue.class, (int[])new int[]{4, 0});
        int[] arrn3 = new int[]{4, 0};
        this.linesDrawBufferTab = (float[][])Array.newInstance((Class)Float.TYPE, (int[])arrn3);
        AxisAutoValues[] arraxisAutoValues = new AxisAutoValues[]{new AxisAutoValues(), new AxisAutoValues(), new AxisAutoValues(), new AxisAutoValues()};
        this.autoValuesBufferTab = arraxisAutoValues;
        this.chart = chart;
        this.computator = chart.getChartComputator();
        this.density = context.getResources().getDisplayMetrics().density;
        this.scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        this.axisMargin = ChartUtils.dp2px(this.density, 2);
        for (int i = 0; i < 4; ++i) {
            this.labelPaintTab[i].setStyle(Paint.Style.FILL);
            this.labelPaintTab[i].setAntiAlias(true);
            this.namePaintTab[i].setStyle(Paint.Style.FILL);
            this.namePaintTab[i].setAntiAlias(true);
            this.linePaintTab[i].setStyle(Paint.Style.STROKE);
            this.linePaintTab[i].setAntiAlias(true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkRawValue(Rect rect, float f, boolean bl, int n, boolean bl2) {
        if (!bl) return true;
        if (bl2) {
            float f2 = this.labelTextAscentTab[3] + this.axisMargin;
            float f3 = this.labelTextAscentTab[0] + this.axisMargin;
            if (f <= (float)rect.bottom - f2 && f >= f3 + (float)rect.top) return true;
            return false;
        }
        float f4 = this.labelWidthTab[n] / 2;
        if (!(f >= f4 + (float)rect.left) || !(f <= (float)rect.right - f4)) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawAxisLabelsAndName(Canvas canvas, Axis axis, int n) {
        boolean bl;
        float f;
        float f2;
        block12 : {
            block13 : {
                block11 : {
                    f2 = 0.0f;
                    bl = AxesRenderer.super.isAxisVertical(n);
                    if (1 != n && 2 != n) break block11;
                    f = this.labelBaselineTab[n];
                    break block12;
                }
                if (n == 0) break block13;
                f = 0.0f;
                f2 = 0.0f;
                if (3 != n) break block12;
            }
            f2 = this.labelBaselineTab[n];
            f = 0.0f;
        }
        for (int i = 0; i < this.valuesToDrawNumTab[n]; ++i) {
            int n2;
            if (axis.isAutoGenerated()) {
                float f3 = this.autoValuesToDrawTab[n][i];
                n2 = axis.getFormatter().formatValueForAutoGeneratedAxis(this.labelBuffer, f3, this.autoValuesBufferTab[n].decimals);
            } else {
                AxisValue axisValue = this.valuesToDrawTab[n][i];
                n2 = axis.getFormatter().formatValueForManualAxis(this.labelBuffer, axisValue);
            }
            if (bl) {
                f2 = this.rawValuesTab[n][i];
            } else {
                f = this.rawValuesTab[n][i];
            }
            if (axis.hasTiltedLabels()) {
                canvas.save();
                canvas.translate((float)this.tiltedLabelXTranslation[n], (float)this.tiltedLabelYTranslation[n]);
                canvas.rotate(-45.0f, f, f2);
                canvas.drawText(this.labelBuffer, this.labelBuffer.length - n2, n2, f, f2, this.labelPaintTab[n]);
                canvas.restore();
                continue;
            }
            canvas.drawText(this.labelBuffer, this.labelBuffer.length - n2, n2, f, f2, this.labelPaintTab[n]);
        }
        Rect rect = this.computator.getContentRectMinusAxesMargins();
        if (!TextUtils.isEmpty((CharSequence)axis.getName())) {
            if (!bl) {
                canvas.drawText(axis.getName(), (float)rect.centerX(), this.nameBaselineTab[n], this.namePaintTab[n]);
                return;
            }
            canvas.save();
            canvas.rotate(-90.0f, (float)rect.centerY(), (float)rect.centerY());
            canvas.drawText(axis.getName(), (float)rect.centerY(), this.nameBaselineTab[n], this.namePaintTab[n]);
            canvas.restore();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawAxisLines(Canvas canvas, Axis axis, int n) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        boolean bl;
        float f7;
        float f8;
        block9 : {
            Rect rect;
            block10 : {
                block8 : {
                    rect = this.computator.getContentRectMinusAxesMargins();
                    f8 = 0.0f;
                    f6 = 0.0f;
                    bl = AxesRenderer.super.isAxisVertical(n);
                    if (1 != n && 2 != n) break block8;
                    f4 = f3 = this.separationLineTab[n];
                    f7 = rect.bottom;
                    f5 = rect.top;
                    f = rect.left;
                    f2 = rect.right;
                    break block9;
                }
                if (n == 0) break block10;
                f4 = 0.0f;
                f7 = 0.0f;
                f3 = 0.0f;
                f5 = 0.0f;
                f = 0.0f;
                f2 = 0.0f;
                f6 = 0.0f;
                f8 = 0.0f;
                if (3 != n) break block9;
            }
            f4 = rect.left;
            f3 = rect.right;
            f7 = f5 = this.separationLineTab[n];
            f6 = rect.top;
            f8 = rect.bottom;
            f = 0.0f;
            f2 = 0.0f;
        }
        if (axis.hasSeparationLine()) {
            canvas.drawLine(f4, f7, f3, f5, this.labelPaintTab[n]);
        }
        if (axis.hasLines()) {
            int n2;
            for (n2 = 0; n2 < this.valuesToDrawNumTab[n]; ++n2) {
                if (bl) {
                    f6 = f8 = this.rawValuesTab[n][n2];
                } else {
                    f = f2 = this.rawValuesTab[n][n2];
                }
                this.linesDrawBufferTab[n][0 + n2 * 4] = f;
                this.linesDrawBufferTab[n][1 + n2 * 4] = f6;
                this.linesDrawBufferTab[n][2 + n2 * 4] = f2;
                this.linesDrawBufferTab[n][3 + n2 * 4] = f8;
            }
            canvas.drawLines(this.linesDrawBufferTab[n], 0, n2 * 4, this.linePaintTab[n]);
        }
    }

    private int getAxisNameMargin(Axis axis, int n) {
        boolean bl = TextUtils.isEmpty((CharSequence)axis.getName());
        int n2 = 0;
        if (!bl) {
            n2 = 0 + this.labelTextAscentTab[n] + this.labelTextDescentTab[n] + this.axisMargin;
        }
        return n2;
    }

    private void initAxis(Axis axis, int n) {
        if (axis == null) {
            return;
        }
        AxesRenderer.super.initAxisAttributes(axis, n);
        AxesRenderer.super.initAxisMargin(axis, n);
        AxesRenderer.super.initAxisMeasurements(axis, n);
    }

    private void initAxisAttributes(Axis axis, int n) {
        AxesRenderer.super.initAxisPaints(axis, n);
        AxesRenderer.super.initAxisTextAlignment(axis, n);
        if (axis.hasTiltedLabels()) {
            AxesRenderer.super.initAxisDimensionForTiltedLabels(n);
            AxesRenderer.super.intiTiltedLabelsTranslation(axis, n);
            return;
        }
        AxesRenderer.super.initAxisDimension(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initAxisDimension(int n) {
        if (1 == n || 2 == n) {
            this.labelDimensionForMarginsTab[n] = this.labelWidthTab[n];
            this.labelDimensionForStepsTab[n] = this.labelTextAscentTab[n];
            return;
        } else {
            if (n != 0 && 3 != n) return;
            {
                this.labelDimensionForMarginsTab[n] = this.labelTextAscentTab[n] + this.labelTextDescentTab[n];
                this.labelDimensionForStepsTab[n] = this.labelWidthTab[n];
                return;
            }
        }
    }

    private void initAxisDimensionForTiltedLabels(int n) {
        int n2 = (int)Math.sqrt((double)(Math.pow((double)this.labelWidthTab[n], (double)2.0) / 2.0));
        int n3 = (int)Math.sqrt((double)(Math.pow((double)this.labelTextAscentTab[n], (double)2.0) / 2.0));
        this.labelDimensionForMarginsTab[n] = n3 + n2;
        this.labelDimensionForStepsTab[n] = Math.round((float)(0.75f * (float)this.labelDimensionForMarginsTab[n]));
    }

    private void initAxisMargin(Axis axis, int n) {
        int n2;
        block2 : {
            block3 : {
                boolean bl = axis.isInside();
                n2 = 0;
                if (bl) break block2;
                if (axis.isAutoGenerated()) break block3;
                boolean bl2 = axis.getValues().isEmpty();
                n2 = 0;
                if (bl2) break block2;
            }
            n2 = 0 + (this.axisMargin + this.labelDimensionForMarginsTab[n]);
        }
        AxesRenderer.super.insetContentRectWithAxesMargins(n2 + AxesRenderer.super.getAxisNameMargin(axis, n), n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initAxisMeasurements(Axis axis, int n) {
        if (1 == n) {
            if (axis.isInside()) {
                this.labelBaselineTab[n] = this.computator.getContentRectMinusAllMargins().left + this.axisMargin;
                this.nameBaselineTab[n] = this.computator.getContentRectMinusAxesMargins().left - this.axisMargin - this.labelTextDescentTab[n];
            } else {
                this.labelBaselineTab[n] = this.computator.getContentRectMinusAxesMargins().left - this.axisMargin;
                this.nameBaselineTab[n] = this.labelBaselineTab[n] - (float)this.axisMargin - (float)this.labelTextDescentTab[n] - (float)this.labelDimensionForMarginsTab[n];
            }
            this.separationLineTab[n] = this.computator.getContentRectMinusAllMargins().left;
            return;
        }
        if (2 == n) {
            if (axis.isInside()) {
                this.labelBaselineTab[n] = this.computator.getContentRectMinusAllMargins().right - this.axisMargin;
                this.nameBaselineTab[n] = this.computator.getContentRectMinusAxesMargins().right + this.axisMargin + this.labelTextAscentTab[n];
            } else {
                this.labelBaselineTab[n] = this.computator.getContentRectMinusAxesMargins().right + this.axisMargin;
                this.nameBaselineTab[n] = this.labelBaselineTab[n] + (float)this.axisMargin + (float)this.labelTextAscentTab[n] + (float)this.labelDimensionForMarginsTab[n];
            }
            this.separationLineTab[n] = this.computator.getContentRectMinusAllMargins().right;
            return;
        }
        if (3 == n) {
            if (axis.isInside()) {
                this.labelBaselineTab[n] = this.computator.getContentRectMinusAllMargins().bottom - this.axisMargin - this.labelTextDescentTab[n];
                this.nameBaselineTab[n] = this.computator.getContentRectMinusAxesMargins().bottom + this.axisMargin + this.labelTextAscentTab[n];
            } else {
                this.labelBaselineTab[n] = this.computator.getContentRectMinusAxesMargins().bottom + this.axisMargin + this.labelTextAscentTab[n];
                this.nameBaselineTab[n] = this.labelBaselineTab[n] + (float)this.axisMargin + (float)this.labelDimensionForMarginsTab[n];
            }
            this.separationLineTab[n] = this.computator.getContentRectMinusAllMargins().bottom;
            return;
        }
        if (n != 0) {
            throw new IllegalArgumentException("Invalid axis position: " + n);
        }
        if (axis.isInside()) {
            this.labelBaselineTab[n] = this.computator.getContentRectMinusAllMargins().top + this.axisMargin + this.labelTextAscentTab[n];
            this.nameBaselineTab[n] = this.computator.getContentRectMinusAxesMargins().top - this.axisMargin - this.labelTextDescentTab[n];
        } else {
            this.labelBaselineTab[n] = this.computator.getContentRectMinusAxesMargins().top - this.axisMargin - this.labelTextDescentTab[n];
            this.nameBaselineTab[n] = this.labelBaselineTab[n] - (float)this.axisMargin - (float)this.labelDimensionForMarginsTab[n];
        }
        this.separationLineTab[n] = this.computator.getContentRectMinusAllMargins().top;
    }

    private void initAxisPaints(Axis axis, int n) {
        Typeface typeface = axis.getTypeface();
        if (typeface != null) {
            this.labelPaintTab[n].setTypeface(typeface);
            this.namePaintTab[n].setTypeface(typeface);
        }
        this.labelPaintTab[n].setColor(axis.getTextColor());
        this.labelPaintTab[n].setTextSize((float)ChartUtils.sp2px(this.scaledDensity, axis.getTextSize()));
        this.labelPaintTab[n].getFontMetricsInt(this.fontMetricsTab[n]);
        this.namePaintTab[n].setColor(axis.getTextColor());
        this.namePaintTab[n].setTextSize((float)ChartUtils.sp2px(this.scaledDensity, axis.getTextSize()));
        this.linePaintTab[n].setColor(axis.getLineColor());
        this.labelTextAscentTab[n] = Math.abs((int)this.fontMetricsTab[n].ascent);
        this.labelTextDescentTab[n] = Math.abs((int)this.fontMetricsTab[n].descent);
        this.labelWidthTab[n] = (int)this.labelPaintTab[n].measureText(labelWidthChars, 0, axis.getMaxLabelChars());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initAxisTextAlignment(Axis axis, int n) {
        this.namePaintTab[n].setTextAlign(Paint.Align.CENTER);
        if (n == 0 || 3 == n) {
            this.labelPaintTab[n].setTextAlign(Paint.Align.CENTER);
            return;
        }
        if (1 == n) {
            if (axis.isInside()) {
                this.labelPaintTab[n].setTextAlign(Paint.Align.LEFT);
                return;
            }
            this.labelPaintTab[n].setTextAlign(Paint.Align.RIGHT);
            return;
        }
        if (2 != n) return;
        {
            if (axis.isInside()) {
                this.labelPaintTab[n].setTextAlign(Paint.Align.RIGHT);
                return;
            }
        }
        this.labelPaintTab[n].setTextAlign(Paint.Align.LEFT);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void insetContentRectWithAxesMargins(int n, int n2) {
        if (1 == n2) {
            this.chart.getChartComputator().insetContentRect(n, 0, 0, 0);
            return;
        } else {
            if (2 == n2) {
                this.chart.getChartComputator().insetContentRect(0, 0, n, 0);
                return;
            }
            if (n2 == 0) {
                this.chart.getChartComputator().insetContentRect(0, n, 0, 0);
                return;
            }
            if (3 != n2) return;
            {
                this.chart.getChartComputator().insetContentRect(0, 0, 0, n);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void intiTiltedLabelsTranslation(Axis axis, int n) {
        int n2;
        int n3 = (int)Math.sqrt((double)(Math.pow((double)this.labelWidthTab[n], (double)2.0) / 2.0));
        int n4 = (int)Math.sqrt((double)(Math.pow((double)this.labelTextAscentTab[n], (double)2.0) / 2.0));
        int n5 = 0;
        if (axis.isInside()) {
            if (1 == n) {
                n2 = n4;
            } else if (2 == n) {
                n5 = -n3 / 2;
                n2 = 0;
            } else if (n == 0) {
                n5 = n4 + n3 / 2 - this.labelTextAscentTab[n];
                n2 = 0;
            } else {
                n2 = 0;
                n5 = 0;
                if (3 == n) {
                    n5 = -n3 / 2;
                    n2 = 0;
                }
            }
        } else if (1 == n) {
            n5 = -n3 / 2;
            n2 = 0;
        } else if (2 == n) {
            n2 = n4;
            n5 = 0;
        } else if (n == 0) {
            n5 = -n3 / 2;
            n2 = 0;
        } else {
            n2 = 0;
            n5 = 0;
            if (3 == n) {
                n5 = n4 + n3 / 2 - this.labelTextAscentTab[n];
                n2 = 0;
            }
        }
        this.tiltedLabelXTranslation[n] = n2;
        this.tiltedLabelYTranslation[n] = n5;
    }

    private boolean isAxisVertical(int n) {
        if (1 == n || 2 == n) {
            return true;
        }
        if (n == 0 || 3 == n) {
            return false;
        }
        throw new IllegalArgumentException("Invalid axis position " + n);
    }

    private void onChartDataOrSizeChanged() {
        this.initAxis(this.chart.getChartData().getAxisXTop(), 0);
        this.initAxis(this.chart.getChartData().getAxisXBottom(), 3);
        this.initAxis(this.chart.getChartData().getAxisYLeft(), 1);
        this.initAxis(this.chart.getChartData().getAxisYRight(), 2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void prepareAutoGeneratedAxis(Axis axis, int n) {
        int n2;
        float f;
        float f2;
        Viewport viewport = this.computator.getVisibleViewport();
        Rect rect = this.computator.getContentRectMinusAllMargins();
        boolean bl = AxesRenderer.super.isAxisVertical(n);
        if (bl) {
            f = viewport.bottom;
            f2 = viewport.top;
            n2 = rect.height();
        } else {
            f = viewport.left;
            f2 = viewport.right;
            n2 = rect.width();
        }
        FloatUtils.computeAutoGeneratedAxisValues(f, f2, n2 / this.labelDimensionForStepsTab[n] / 2, this.autoValuesBufferTab[n]);
        if (axis.hasLines() && this.linesDrawBufferTab[n].length < 4 * this.autoValuesBufferTab[n].valuesNumber) {
            this.linesDrawBufferTab[n] = new float[4 * this.autoValuesBufferTab[n].valuesNumber];
        }
        if (this.rawValuesTab[n].length < this.autoValuesBufferTab[n].valuesNumber) {
            this.rawValuesTab[n] = new float[this.autoValuesBufferTab[n].valuesNumber];
        }
        if (this.autoValuesToDrawTab[n].length < this.autoValuesBufferTab[n].valuesNumber) {
            this.autoValuesToDrawTab[n] = new float[this.autoValuesBufferTab[n].valuesNumber];
        }
        int n3 = 0;
        int n4 = 0;
        do {
            if (n4 >= this.autoValuesBufferTab[n].valuesNumber) {
                this.valuesToDrawNumTab[n] = n3;
                return;
            }
            float f3 = bl ? this.computator.computeRawY(this.autoValuesBufferTab[n].values[n4]) : this.computator.computeRawX(this.autoValuesBufferTab[n].values[n4]);
            if (AxesRenderer.super.checkRawValue(rect, f3, axis.isInside(), n, bl)) {
                this.rawValuesTab[n][n3] = f3;
                this.autoValuesToDrawTab[n][n3] = this.autoValuesBufferTab[n].values[n4];
                ++n3;
            }
            ++n4;
        } while (true);
    }

    private void prepareAxisToDraw(Axis axis, int n) {
        if (axis.isAutoGenerated()) {
            AxesRenderer.super.prepareAutoGeneratedAxis(axis, n);
            return;
        }
        AxesRenderer.super.prepareCustomAxis(axis, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void prepareCustomAxis(Axis axis, int n) {
        float f;
        float f2;
        Viewport viewport = this.computator.getMaximumViewport();
        Viewport viewport2 = this.computator.getVisibleViewport();
        Rect rect = this.computator.getContentRectMinusAllMargins();
        boolean bl = AxesRenderer.super.isAxisVertical(n);
        float f3 = 1.0f;
        if (bl) {
            if (viewport.height() > 0.0f && viewport2.height() > 0.0f) {
                f3 = (float)rect.height() * (viewport.height() / viewport2.height());
            }
            f = viewport2.bottom;
            f2 = viewport2.top;
        } else {
            if (viewport.width() > 0.0f && viewport2.width() > 0.0f) {
                f3 = (float)rect.width() * (viewport.width() / viewport2.width());
            }
            f = viewport2.left;
            f2 = viewport2.right;
        }
        if (f3 == 0.0f) {
            f3 = 1.0f;
        }
        int n2 = (int)Math.max((double)1.0, (double)Math.ceil((double)(1.5 * (double)(axis.getValues().size() * this.labelDimensionForStepsTab[n]) / (double)f3)));
        if (axis.hasLines() && this.linesDrawBufferTab[n].length < 4 * axis.getValues().size()) {
            this.linesDrawBufferTab[n] = new float[4 * axis.getValues().size()];
        }
        if (this.rawValuesTab[n].length < axis.getValues().size()) {
            this.rawValuesTab[n] = new float[axis.getValues().size()];
        }
        if (this.valuesToDrawTab[n].length < axis.getValues().size()) {
            this.valuesToDrawTab[n] = new AxisValue[axis.getValues().size()];
        }
        int n3 = 0;
        int n4 = 0;
        Iterator iterator = axis.getValues().iterator();
        do {
            float f4;
            if (!iterator.hasNext()) {
                this.valuesToDrawNumTab[n] = n4;
                return;
            }
            AxisValue axisValue = (AxisValue)iterator.next();
            float f5 = axisValue.getValue();
            if (!(f5 >= f) || !(f5 <= f2)) continue;
            if (n3 % n2 == 0 && AxesRenderer.super.checkRawValue(rect, f4 = bl ? this.computator.computeRawY(f5) : this.computator.computeRawX(f5), axis.isInside(), n, bl)) {
                this.rawValuesTab[n][n4] = f4;
                this.valuesToDrawTab[n][n4] = axisValue;
                ++n4;
            }
            ++n3;
        } while (true);
    }

    public void drawInBackground(Canvas canvas) {
        Axis axis;
        Axis axis2;
        Axis axis3;
        Axis axis4 = this.chart.getChartData().getAxisYLeft();
        if (axis4 != null) {
            AxesRenderer.super.prepareAxisToDraw(axis4, 1);
            AxesRenderer.super.drawAxisLines(canvas, axis4, 1);
        }
        if ((axis = this.chart.getChartData().getAxisYRight()) != null) {
            AxesRenderer.super.prepareAxisToDraw(axis, 2);
            AxesRenderer.super.drawAxisLines(canvas, axis, 2);
        }
        if ((axis2 = this.chart.getChartData().getAxisXBottom()) != null) {
            AxesRenderer.super.prepareAxisToDraw(axis2, 3);
            AxesRenderer.super.drawAxisLines(canvas, axis2, 3);
        }
        if ((axis3 = this.chart.getChartData().getAxisXTop()) != null) {
            AxesRenderer.super.prepareAxisToDraw(axis3, 0);
            AxesRenderer.super.drawAxisLines(canvas, axis3, 0);
        }
    }

    public void drawInForeground(Canvas canvas) {
        Axis axis;
        Axis axis2;
        Axis axis3;
        Axis axis4 = this.chart.getChartData().getAxisYLeft();
        if (axis4 != null) {
            AxesRenderer.super.drawAxisLabelsAndName(canvas, axis4, 1);
        }
        if ((axis = this.chart.getChartData().getAxisYRight()) != null) {
            AxesRenderer.super.drawAxisLabelsAndName(canvas, axis, 2);
        }
        if ((axis2 = this.chart.getChartData().getAxisXBottom()) != null) {
            AxesRenderer.super.drawAxisLabelsAndName(canvas, axis2, 3);
        }
        if ((axis3 = this.chart.getChartData().getAxisXTop()) != null) {
            AxesRenderer.super.drawAxisLabelsAndName(canvas, axis3, 0);
        }
    }

    public void onChartDataChanged() {
        this.onChartDataOrSizeChanged();
    }

    public void onChartSizeChanged() {
        this.onChartDataOrSizeChanged();
    }

    public void resetRenderer() {
        this.computator = this.chart.getChartComputator();
    }
}

