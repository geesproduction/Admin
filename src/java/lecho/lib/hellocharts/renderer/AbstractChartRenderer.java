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
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.util.DisplayMetrics
 *  java.lang.Object
 */
package lecho.lib.hellocharts.renderer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.renderer.ChartRenderer;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;

public abstract class AbstractChartRenderer
implements ChartRenderer {
    public int DEFAULT_LABEL_MARGIN_DP = 4;
    protected Chart chart;
    protected ChartComputator computator;
    protected float density;
    protected Paint.FontMetricsInt fontMetrics = new Paint.FontMetricsInt();
    protected boolean isValueLabelBackgroundAuto;
    protected boolean isValueLabelBackgroundEnabled;
    protected boolean isViewportCalculationEnabled = true;
    protected Paint labelBackgroundPaint = new Paint();
    protected RectF labelBackgroundRect = new RectF();
    protected char[] labelBuffer = new char[64];
    protected int labelMargin;
    protected int labelOffset;
    protected Paint labelPaint = new Paint();
    protected float scaledDensity;
    protected SelectedValue selectedValue = new SelectedValue();

    public AbstractChartRenderer(Context context, Chart chart) {
        this.density = context.getResources().getDisplayMetrics().density;
        this.scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        this.chart = chart;
        this.computator = chart.getChartComputator();
        this.labelOffset = this.labelMargin = ChartUtils.dp2px(this.density, this.DEFAULT_LABEL_MARGIN_DP);
        this.labelPaint.setAntiAlias(true);
        this.labelPaint.setStyle(Paint.Style.FILL);
        this.labelPaint.setTextAlign(Paint.Align.LEFT);
        this.labelPaint.setTypeface(Typeface.defaultFromStyle((int)1));
        this.labelPaint.setColor(-1);
        this.labelBackgroundPaint.setAntiAlias(true);
        this.labelBackgroundPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void clearTouch() {
        this.selectedValue.clear();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void drawLabelTextAndBackground(Canvas canvas, char[] arrc, int n, int n2, int n3) {
        float f;
        float f2;
        if (this.isValueLabelBackgroundEnabled) {
            if (this.isValueLabelBackgroundAuto) {
                this.labelBackgroundPaint.setColor(n3);
            }
            canvas.drawRect(this.labelBackgroundRect, this.labelBackgroundPaint);
            f2 = this.labelBackgroundRect.left + (float)this.labelMargin;
            f = this.labelBackgroundRect.bottom - (float)this.labelMargin;
        } else {
            f2 = this.labelBackgroundRect.left;
            f = this.labelBackgroundRect.bottom;
        }
        canvas.drawText(arrc, n, n2, f2, f, this.labelPaint);
    }

    @Override
    public Viewport getCurrentViewport() {
        return this.computator.getCurrentViewport();
    }

    @Override
    public Viewport getMaximumViewport() {
        return this.computator.getMaximumViewport();
    }

    @Override
    public SelectedValue getSelectedValue() {
        return this.selectedValue;
    }

    @Override
    public boolean isTouched() {
        return this.selectedValue.isSet();
    }

    @Override
    public boolean isViewportCalculationEnabled() {
        return this.isViewportCalculationEnabled;
    }

    @Override
    public void onChartDataChanged() {
        ChartData chartData = this.chart.getChartData();
        Typeface typeface = this.chart.getChartData().getValueLabelTypeface();
        if (typeface != null) {
            this.labelPaint.setTypeface(typeface);
        }
        this.labelPaint.setColor(chartData.getValueLabelTextColor());
        this.labelPaint.setTextSize((float)ChartUtils.sp2px(this.scaledDensity, chartData.getValueLabelTextSize()));
        this.labelPaint.getFontMetricsInt(this.fontMetrics);
        this.isValueLabelBackgroundEnabled = chartData.isValueLabelBackgroundEnabled();
        this.isValueLabelBackgroundAuto = chartData.isValueLabelBackgroundAuto();
        this.labelBackgroundPaint.setColor(chartData.getValueLabelBackgroundColor());
        this.selectedValue.clear();
    }

    @Override
    public void resetRenderer() {
        this.computator = this.chart.getChartComputator();
    }

    @Override
    public void selectValue(SelectedValue selectedValue) {
        this.selectedValue.set(selectedValue);
    }

    @Override
    public void setCurrentViewport(Viewport viewport) {
        if (viewport != null) {
            this.computator.setCurrentViewport(viewport);
        }
    }

    @Override
    public void setMaximumViewport(Viewport viewport) {
        if (viewport != null) {
            this.computator.setMaxViewport(viewport);
        }
    }

    @Override
    public void setViewportCalculationEnabled(boolean bl) {
        this.isViewportCalculationEnabled = bl;
    }
}

