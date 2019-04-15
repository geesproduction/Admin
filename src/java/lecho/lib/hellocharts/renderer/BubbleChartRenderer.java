/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$FontMetricsInt
 *  android.graphics.Paint$Style
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.RectF
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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.Iterator;
import java.util.List;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.formatter.BubbleChartValueFormatter;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.provider.BubbleChartDataProvider;
import lecho.lib.hellocharts.renderer.AbstractChartRenderer;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;

public class BubbleChartRenderer
extends AbstractChartRenderer {
    private static final int DEFAULT_TOUCH_ADDITIONAL_DP = 4;
    private static final int MODE_DRAW = 0;
    private static final int MODE_HIGHLIGHT = 1;
    private PointF bubbleCenter = new PointF();
    private Paint bubblePaint = new Paint();
    private RectF bubbleRect = new RectF();
    private float bubbleScaleX;
    private float bubbleScaleY;
    private BubbleChartDataProvider dataProvider;
    private boolean hasLabels;
    private boolean hasLabelsOnlyForSelected;
    private boolean isBubbleScaledByX = true;
    private float maxRadius;
    private float minRawRadius;
    private Viewport tempMaximumViewport = new Viewport();
    private int touchAdditional;
    private BubbleChartValueFormatter valueFormatter;

    public BubbleChartRenderer(Context context, Chart chart, BubbleChartDataProvider bubbleChartDataProvider) {
        super(context, chart);
        this.dataProvider = bubbleChartDataProvider;
        this.touchAdditional = ChartUtils.dp2px(this.density, 4);
        this.bubblePaint.setAntiAlias(true);
        this.bubblePaint.setStyle(Paint.Style.FILL);
    }

    private void calculateMaxViewport() {
        float f = Float.MIN_VALUE;
        this.tempMaximumViewport.set(Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_VALUE, Float.MAX_VALUE);
        BubbleChartData bubbleChartData = this.dataProvider.getBubbleChartData();
        for (BubbleValue bubbleValue : bubbleChartData.getValues()) {
            if (Math.abs((float)bubbleValue.getZ()) > f) {
                f = Math.abs((float)bubbleValue.getZ());
            }
            if (bubbleValue.getX() < this.tempMaximumViewport.left) {
                this.tempMaximumViewport.left = bubbleValue.getX();
            }
            if (bubbleValue.getX() > this.tempMaximumViewport.right) {
                this.tempMaximumViewport.right = bubbleValue.getX();
            }
            if (bubbleValue.getY() < this.tempMaximumViewport.bottom) {
                this.tempMaximumViewport.bottom = bubbleValue.getY();
            }
            if (!(bubbleValue.getY() > this.tempMaximumViewport.top)) continue;
            this.tempMaximumViewport.top = bubbleValue.getY();
        }
        this.maxRadius = (float)Math.sqrt((double)((double)f / 3.141592653589793));
        this.bubbleScaleX = this.tempMaximumViewport.width() / (4.0f * this.maxRadius);
        if (this.bubbleScaleX == 0.0f) {
            this.bubbleScaleX = 1.0f;
        }
        this.bubbleScaleY = this.tempMaximumViewport.height() / (4.0f * this.maxRadius);
        if (this.bubbleScaleY == 0.0f) {
            this.bubbleScaleY = 1.0f;
        }
        this.bubbleScaleX *= bubbleChartData.getBubbleScale();
        this.bubbleScaleY *= bubbleChartData.getBubbleScale();
        this.tempMaximumViewport.inset(-this.maxRadius * this.bubbleScaleX, -this.maxRadius * this.bubbleScaleY);
        this.minRawRadius = ChartUtils.dp2px(this.density, this.dataProvider.getBubbleChartData().getMinBubbleRadius());
    }

    private void drawBubble(Canvas canvas, BubbleValue bubbleValue) {
        float f = BubbleChartRenderer.super.processBubble(bubbleValue, this.bubbleCenter) - (float)this.touchAdditional;
        this.bubbleRect.inset((float)this.touchAdditional, (float)this.touchAdditional);
        this.bubblePaint.setColor(bubbleValue.getColor());
        BubbleChartRenderer.super.drawBubbleShapeAndLabel(canvas, bubbleValue, f, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawBubbleShapeAndLabel(Canvas canvas, BubbleValue bubbleValue, float f, int n) {
        if (ValueShape.SQUARE.equals((Object)bubbleValue.getShape())) {
            canvas.drawRect(this.bubbleRect, this.bubblePaint);
        } else {
            if (!ValueShape.CIRCLE.equals((Object)bubbleValue.getShape())) {
                throw new IllegalArgumentException("Invalid bubble shape: " + (Object)((Object)bubbleValue.getShape()));
            }
            canvas.drawCircle(this.bubbleCenter.x, this.bubbleCenter.y, f, this.bubblePaint);
        }
        if (1 == n) {
            if (!this.hasLabels && !this.hasLabelsOnlyForSelected) return;
            {
                BubbleChartRenderer.super.drawLabel(canvas, bubbleValue, this.bubbleCenter.x, this.bubbleCenter.y);
                return;
            }
        } else {
            if (n != 0) {
                throw new IllegalStateException("Cannot process bubble in mode: " + n);
            }
            if (!this.hasLabels) return;
            {
                BubbleChartRenderer.super.drawLabel(canvas, bubbleValue, this.bubbleCenter.x, this.bubbleCenter.y);
                return;
            }
        }
    }

    private void drawBubbles(Canvas canvas) {
        Iterator iterator = this.dataProvider.getBubbleChartData().getValues().iterator();
        while (iterator.hasNext()) {
            BubbleChartRenderer.super.drawBubble(canvas, (BubbleValue)iterator.next());
        }
    }

    private void drawLabel(Canvas canvas, BubbleValue bubbleValue, float f, float f2) {
        Rect rect = this.computator.getContentRectMinusAllMargins();
        int n = this.valueFormatter.formatChartValue(this.labelBuffer, bubbleValue);
        if (n == 0) {
            return;
        }
        float f3 = this.labelPaint.measureText(this.labelBuffer, this.labelBuffer.length - n, n);
        int n2 = Math.abs((int)this.fontMetrics.ascent);
        float f4 = f - f3 / 2.0f - (float)this.labelMargin;
        float f5 = f + f3 / 2.0f + (float)this.labelMargin;
        float f6 = f2 - (float)(n2 / 2) - (float)this.labelMargin;
        float f7 = f2 + (float)(n2 / 2) + (float)this.labelMargin;
        if (f6 < (float)rect.top) {
            f6 = f2;
            f7 = f2 + (float)n2 + (float)(2 * this.labelMargin);
        }
        if (f7 > (float)rect.bottom) {
            f6 = f2 - (float)n2 - (float)(2 * this.labelMargin);
            f7 = f2;
        }
        if (f4 < (float)rect.left) {
            f4 = f;
            f5 = f + f3 + (float)(2 * this.labelMargin);
        }
        if (f5 > (float)rect.right) {
            f4 = f - f3 - (float)(2 * this.labelMargin);
            f5 = f;
        }
        this.labelBackgroundRect.set(f4, f6, f5, f7);
        this.drawLabelTextAndBackground(canvas, this.labelBuffer, this.labelBuffer.length - n, n, bubbleValue.getDarkenColor());
    }

    private void highlightBubble(Canvas canvas, BubbleValue bubbleValue) {
        float f = BubbleChartRenderer.super.processBubble(bubbleValue, this.bubbleCenter);
        this.bubblePaint.setColor(bubbleValue.getDarkenColor());
        BubbleChartRenderer.super.drawBubbleShapeAndLabel(canvas, bubbleValue, f, 1);
    }

    private void highlightBubbles(Canvas canvas) {
        BubbleChartRenderer.super.highlightBubble(canvas, (BubbleValue)this.dataProvider.getBubbleChartData().getValues().get(this.selectedValue.getFirstIndex()));
    }

    /*
     * Enabled aggressive block sorting
     */
    private float processBubble(BubbleValue bubbleValue, PointF pointF) {
        float f;
        float f2 = this.computator.computeRawX(bubbleValue.getX());
        float f3 = this.computator.computeRawY(bubbleValue.getY());
        float f4 = (float)Math.sqrt((double)((double)Math.abs((float)bubbleValue.getZ()) / 3.141592653589793));
        if (this.isBubbleScaledByX) {
            float f5 = f4 * this.bubbleScaleX;
            f = this.computator.computeRawDistanceX(f5);
        } else {
            float f6 = f4 * this.bubbleScaleY;
            f = this.computator.computeRawDistanceY(f6);
        }
        if (f < this.minRawRadius + (float)this.touchAdditional) {
            f = this.minRawRadius + (float)this.touchAdditional;
        }
        this.bubbleCenter.set(f2, f3);
        if (ValueShape.SQUARE.equals((Object)bubbleValue.getShape())) {
            this.bubbleRect.set(f2 - f, f3 - f, f2 + f, f3 + f);
        }
        return f;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean checkTouch(float f, float f2) {
        this.selectedValue.clear();
        BubbleChartData bubbleChartData = this.dataProvider.getBubbleChartData();
        int n = 0;
        Iterator iterator = bubbleChartData.getValues().iterator();
        while (iterator.hasNext()) {
            BubbleValue bubbleValue = (BubbleValue)iterator.next();
            float f3 = BubbleChartRenderer.super.processBubble(bubbleValue, this.bubbleCenter);
            if (ValueShape.SQUARE.equals((Object)bubbleValue.getShape())) {
                if (this.bubbleRect.contains(f, f2)) {
                    this.selectedValue.set(n, n, SelectedValue.SelectedValueType.NONE);
                }
            } else {
                if (!ValueShape.CIRCLE.equals((Object)bubbleValue.getShape())) {
                    throw new IllegalArgumentException("Invalid bubble shape: " + (Object)((Object)bubbleValue.getShape()));
                }
                float f4 = f - this.bubbleCenter.x;
                float f5 = f2 - this.bubbleCenter.y;
                if ((float)Math.sqrt((double)(f4 * f4 + f5 * f5)) <= f3) {
                    this.selectedValue.set(n, n, SelectedValue.SelectedValueType.NONE);
                }
            }
            ++n;
        }
        return this.isTouched();
    }

    @Override
    public void draw(Canvas canvas) {
        BubbleChartRenderer.super.drawBubbles(canvas);
        if (this.isTouched()) {
            BubbleChartRenderer.super.highlightBubbles(canvas);
        }
    }

    @Override
    public void drawUnclipped(Canvas canvas) {
    }

    @Override
    public void onChartDataChanged() {
        super.onChartDataChanged();
        BubbleChartData bubbleChartData = this.dataProvider.getBubbleChartData();
        this.hasLabels = bubbleChartData.hasLabels();
        this.hasLabelsOnlyForSelected = bubbleChartData.hasLabelsOnlyForSelected();
        this.valueFormatter = bubbleChartData.getFormatter();
        this.onChartViewportChanged();
    }

    @Override
    public void onChartSizeChanged() {
        Rect rect = this.chart.getChartComputator().getContentRectMinusAllMargins();
        if (rect.width() < rect.height()) {
            this.isBubbleScaledByX = true;
            return;
        }
        this.isBubbleScaledByX = false;
    }

    @Override
    public void onChartViewportChanged() {
        if (this.isViewportCalculationEnabled) {
            this.calculateMaxViewport();
            this.computator.setMaxViewport(this.tempMaximumViewport);
            this.computator.setCurrentViewport(this.computator.getMaximumViewport());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void removeMargins() {
        float f;
        Rect rect = this.computator.getContentRectMinusAllMargins();
        if (rect.height() == 0 || rect.width() == 0) {
            return;
        }
        float f2 = this.computator.computeRawDistanceX(this.maxRadius * this.bubbleScaleX);
        float f3 = this.computator.computeRawDistanceY(this.maxRadius * this.bubbleScaleY);
        float f4 = this.computator.getMaximumViewport().width() / (float)rect.width();
        float f5 = this.computator.getMaximumViewport().height() / (float)rect.height();
        float f6 = 0.0f;
        if (this.isBubbleScaledByX) {
            f = 0.75f * (f5 * (f3 - f2));
        } else {
            f6 = 0.75f * (f4 * (f2 - f3));
            f = 0.0f;
        }
        Viewport viewport = this.computator.getMaximumViewport();
        viewport.inset(f6, f);
        Viewport viewport2 = this.computator.getCurrentViewport();
        viewport2.inset(f6, f);
        this.computator.setMaxViewport(viewport);
        this.computator.setCurrentViewport(viewport2);
    }
}

