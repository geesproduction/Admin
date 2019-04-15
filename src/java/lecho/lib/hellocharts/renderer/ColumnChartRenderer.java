/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$FontMetricsInt
 *  android.graphics.Paint$Style
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.graphics.RectF
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
import lecho.lib.hellocharts.formatter.ColumnChartValueFormatter;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.provider.ColumnChartDataProvider;
import lecho.lib.hellocharts.renderer.AbstractChartRenderer;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;

public class ColumnChartRenderer
extends AbstractChartRenderer {
    public static final int DEFAULT_COLUMN_TOUCH_ADDITIONAL_WIDTH_DP = 4;
    public static final int DEFAULT_SUBCOLUMN_SPACING_DP = 1;
    private static final int MODE_CHECK_TOUCH = 1;
    private static final int MODE_DRAW = 0;
    private static final int MODE_HIGHLIGHT = 2;
    private float baseValue;
    private Paint columnPaint = new Paint();
    private ColumnChartDataProvider dataProvider;
    private RectF drawRect = new RectF();
    private float fillRatio;
    private int subcolumnSpacing;
    private Viewport tempMaximumViewport = new Viewport();
    private int touchAdditionalWidth;
    private PointF touchedPoint = new PointF();

    public ColumnChartRenderer(Context context, Chart chart, ColumnChartDataProvider columnChartDataProvider) {
        super(context, chart);
        this.dataProvider = columnChartDataProvider;
        this.subcolumnSpacing = ChartUtils.dp2px(this.density, 1);
        this.touchAdditionalWidth = ChartUtils.dp2px(this.density, 4);
        this.columnPaint.setAntiAlias(true);
        this.columnPaint.setStyle(Paint.Style.FILL);
        this.columnPaint.setStrokeCap(Paint.Cap.SQUARE);
    }

    private float calculateColumnWidth() {
        float f = this.fillRatio * (float)this.computator.getContentRectMinusAllMargins().width() / this.computator.getVisibleViewport().width();
        if (f < 2.0f) {
            f = 2.0f;
        }
        return f;
    }

    private void calculateMaxViewport() {
        ColumnChartData columnChartData = this.dataProvider.getColumnChartData();
        this.tempMaximumViewport.set(-0.5f, this.baseValue, (float)columnChartData.getColumns().size() - 0.5f, this.baseValue);
        if (columnChartData.isStacked()) {
            this.calculateMaxViewportForStacked(columnChartData);
            return;
        }
        this.calculateMaxViewportForSubcolumns(columnChartData);
    }

    private void calculateMaxViewportForStacked(ColumnChartData columnChartData) {
        for (Column column : columnChartData.getColumns()) {
            float f = this.baseValue;
            float f2 = this.baseValue;
            for (SubcolumnValue subcolumnValue : column.getValues()) {
                if (subcolumnValue.getValue() >= this.baseValue) {
                    f += subcolumnValue.getValue();
                    continue;
                }
                f2 += subcolumnValue.getValue();
            }
            if (f > this.tempMaximumViewport.top) {
                this.tempMaximumViewport.top = f;
            }
            if (!(f2 < this.tempMaximumViewport.bottom)) continue;
            this.tempMaximumViewport.bottom = f2;
        }
    }

    private void calculateMaxViewportForSubcolumns(ColumnChartData columnChartData) {
        Iterator iterator = columnChartData.getColumns().iterator();
        while (iterator.hasNext()) {
            for (SubcolumnValue subcolumnValue : ((Column)iterator.next()).getValues()) {
                if (subcolumnValue.getValue() >= this.baseValue && subcolumnValue.getValue() > this.tempMaximumViewport.top) {
                    this.tempMaximumViewport.top = subcolumnValue.getValue();
                }
                if (!(subcolumnValue.getValue() < this.baseValue) || !(subcolumnValue.getValue() < this.tempMaximumViewport.bottom)) continue;
                this.tempMaximumViewport.bottom = subcolumnValue.getValue();
            }
        }
    }

    private void calculateRectToDraw(SubcolumnValue subcolumnValue, float f, float f2, float f3, float f4) {
        this.drawRect.left = f;
        this.drawRect.right = f2;
        if (subcolumnValue.getValue() >= this.baseValue) {
            this.drawRect.top = f4;
            this.drawRect.bottom = f3 - (float)this.subcolumnSpacing;
            return;
        }
        this.drawRect.bottom = f4;
        this.drawRect.top = f3 + (float)this.subcolumnSpacing;
    }

    private void checkRectToDraw(int n, int n2) {
        if (this.drawRect.contains(this.touchedPoint.x, this.touchedPoint.y)) {
            this.selectedValue.set(n, n2, SelectedValue.SelectedValueType.COLUMN);
        }
    }

    private void checkTouchForStacked(float f, float f2) {
        this.touchedPoint.x = f;
        this.touchedPoint.y = f2;
        ColumnChartData columnChartData = this.dataProvider.getColumnChartData();
        float f3 = ColumnChartRenderer.super.calculateColumnWidth();
        int n = 0;
        Iterator iterator = columnChartData.getColumns().iterator();
        while (iterator.hasNext()) {
            ColumnChartRenderer.super.processColumnForStacked(null, (Column)iterator.next(), f3, n, 1);
            ++n;
        }
    }

    private void checkTouchForSubcolumns(float f, float f2) {
        this.touchedPoint.x = f;
        this.touchedPoint.y = f2;
        ColumnChartData columnChartData = this.dataProvider.getColumnChartData();
        float f3 = ColumnChartRenderer.super.calculateColumnWidth();
        int n = 0;
        Iterator iterator = columnChartData.getColumns().iterator();
        while (iterator.hasNext()) {
            ColumnChartRenderer.super.processColumnForSubcolumns(null, (Column)iterator.next(), f3, n, 1);
            ++n;
        }
    }

    private void drawColumnForStacked(Canvas canvas) {
        ColumnChartData columnChartData = this.dataProvider.getColumnChartData();
        float f = ColumnChartRenderer.super.calculateColumnWidth();
        int n = 0;
        Iterator iterator = columnChartData.getColumns().iterator();
        while (iterator.hasNext()) {
            ColumnChartRenderer.super.processColumnForStacked(canvas, (Column)iterator.next(), f, n, 0);
            ++n;
        }
    }

    private void drawColumnsForSubcolumns(Canvas canvas) {
        ColumnChartData columnChartData = this.dataProvider.getColumnChartData();
        float f = ColumnChartRenderer.super.calculateColumnWidth();
        int n = 0;
        Iterator iterator = columnChartData.getColumns().iterator();
        while (iterator.hasNext()) {
            ColumnChartRenderer.super.processColumnForSubcolumns(canvas, (Column)iterator.next(), f, n, 0);
            ++n;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawLabel(Canvas canvas, Column column, SubcolumnValue subcolumnValue, boolean bl, float f) {
        float f2;
        int n;
        float f3;
        float f4;
        float f5;
        block13 : {
            int n2;
            block14 : {
                block11 : {
                    block12 : {
                        n = column.getFormatter().formatChartValue(this.labelBuffer, subcolumnValue);
                        if (n == 0) break block11;
                        float f6 = this.labelPaint.measureText(this.labelBuffer, this.labelBuffer.length - n, n);
                        n2 = Math.abs((int)this.fontMetrics.ascent);
                        f3 = this.drawRect.centerX() - f6 / 2.0f - (float)this.labelMargin;
                        f5 = this.drawRect.centerX() + f6 / 2.0f + (float)this.labelMargin;
                        if (!bl || !((float)n2 < this.drawRect.height() - (float)(2 * this.labelMargin))) break block12;
                        if (subcolumnValue.getValue() >= this.baseValue) {
                            f4 = this.drawRect.top;
                            f2 = this.drawRect.top + (float)n2 + (float)(2 * this.labelMargin);
                        } else {
                            f4 = this.drawRect.bottom - (float)n2 - (float)(2 * this.labelMargin);
                            f2 = this.drawRect.bottom;
                        }
                        break block13;
                    }
                    if (!bl) break block14;
                }
                return;
            }
            if (subcolumnValue.getValue() >= this.baseValue) {
                f4 = this.drawRect.top - f - (float)n2 - (float)(2 * this.labelMargin);
                if (f4 < (float)this.computator.getContentRectMinusAllMargins().top) {
                    f4 = f + this.drawRect.top;
                    f2 = f + this.drawRect.top + (float)n2 + (float)(2 * this.labelMargin);
                } else {
                    f2 = this.drawRect.top - f;
                }
            } else {
                f2 = f + this.drawRect.bottom + (float)n2 + (float)(2 * this.labelMargin);
                if (f2 > (float)this.computator.getContentRectMinusAllMargins().bottom) {
                    f4 = this.drawRect.bottom - f - (float)n2 - (float)(2 * this.labelMargin);
                    f2 = this.drawRect.bottom - f;
                } else {
                    f4 = f + this.drawRect.bottom;
                }
            }
        }
        this.labelBackgroundRect.set(f3, f4, f5, f2);
        this.drawLabelTextAndBackground(canvas, this.labelBuffer, this.labelBuffer.length - n, n, subcolumnValue.getDarkenColor());
    }

    private void drawSubcolumn(Canvas canvas, Column column, SubcolumnValue subcolumnValue, boolean bl) {
        canvas.drawRect(this.drawRect, this.columnPaint);
        if (column.hasLabels()) {
            ColumnChartRenderer.super.drawLabel(canvas, column, subcolumnValue, bl, this.labelOffset);
        }
    }

    private void highlightColumnForStacked(Canvas canvas) {
        ColumnChartData columnChartData = this.dataProvider.getColumnChartData();
        float f = ColumnChartRenderer.super.calculateColumnWidth();
        ColumnChartRenderer.super.processColumnForStacked(canvas, (Column)columnChartData.getColumns().get(this.selectedValue.getFirstIndex()), f, this.selectedValue.getFirstIndex(), 2);
    }

    private void highlightColumnsForSubcolumns(Canvas canvas) {
        ColumnChartData columnChartData = this.dataProvider.getColumnChartData();
        float f = ColumnChartRenderer.super.calculateColumnWidth();
        ColumnChartRenderer.super.processColumnForSubcolumns(canvas, (Column)columnChartData.getColumns().get(this.selectedValue.getFirstIndex()), f, this.selectedValue.getFirstIndex(), 2);
    }

    private void highlightSubcolumn(Canvas canvas, Column column, SubcolumnValue subcolumnValue, int n, boolean bl) {
        if (this.selectedValue.getSecondIndex() == n) {
            this.columnPaint.setColor(subcolumnValue.getDarkenColor());
            canvas.drawRect(this.drawRect.left - (float)this.touchAdditionalWidth, this.drawRect.top, this.drawRect.right + (float)this.touchAdditionalWidth, this.drawRect.bottom, this.columnPaint);
            if (column.hasLabels() || column.hasLabelsOnlyForSelected()) {
                this.drawLabel(canvas, column, subcolumnValue, bl, this.labelOffset);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void processColumnForStacked(Canvas canvas, Column column, float f, int n, int n2) {
        float f2 = this.computator.computeRawX(n);
        float f3 = f / 2.0f;
        float f4 = this.baseValue;
        float f5 = this.baseValue;
        int n3 = 0;
        Iterator iterator = column.getValues().iterator();
        while (iterator.hasNext()) {
            float f6;
            SubcolumnValue subcolumnValue = (SubcolumnValue)iterator.next();
            this.columnPaint.setColor(subcolumnValue.getColor());
            if (subcolumnValue.getValue() >= this.baseValue) {
                f6 = f4;
                f4 += subcolumnValue.getValue();
            } else {
                f6 = f5;
                f5 += subcolumnValue.getValue();
            }
            float f7 = this.computator.computeRawY(f6);
            float f8 = this.computator.computeRawY(f6 + subcolumnValue.getValue());
            this.calculateRectToDraw(subcolumnValue, f2 - f3, f2 + f3, f7, f8);
            switch (n2) {
                default: {
                    throw new IllegalStateException("Cannot process column in mode: " + n2);
                }
                case 0: {
                    this.drawSubcolumn(canvas, column, subcolumnValue, true);
                    break;
                }
                case 2: {
                    this.highlightSubcolumn(canvas, column, subcolumnValue, n3, true);
                    break;
                }
                case 1: {
                    this.checkRectToDraw(n, n3);
                }
            }
            ++n3;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void processColumnForSubcolumns(Canvas canvas, Column column, float f, int n, int n2) {
        float f2 = (f - (float)(this.subcolumnSpacing * (-1 + column.getValues().size()))) / (float)column.getValues().size();
        if (f2 < 1.0f) {
            f2 = 1.0f;
        }
        float f3 = this.computator.computeRawX(n);
        float f4 = f / 2.0f;
        float f5 = this.computator.computeRawY(this.baseValue);
        float f6 = f3 - f4;
        int n3 = 0;
        Iterator iterator = column.getValues().iterator();
        do {
            SubcolumnValue subcolumnValue;
            block11 : {
                block10 : {
                    if (!iterator.hasNext()) break block10;
                    subcolumnValue = (SubcolumnValue)iterator.next();
                    this.columnPaint.setColor(subcolumnValue.getColor());
                    if (!(f6 > f3 + f4)) break block11;
                }
                return;
            }
            float f7 = this.computator.computeRawY(subcolumnValue.getValue());
            this.calculateRectToDraw(subcolumnValue, f6, f6 + f2, f5, f7);
            switch (n2) {
                default: {
                    throw new IllegalStateException("Cannot process column in mode: " + n2);
                }
                case 0: {
                    this.drawSubcolumn(canvas, column, subcolumnValue, false);
                    break;
                }
                case 2: {
                    this.highlightSubcolumn(canvas, column, subcolumnValue, n3, false);
                    break;
                }
                case 1: {
                    this.checkRectToDraw(n, n3);
                }
            }
            f6 += f2 + (float)this.subcolumnSpacing;
            ++n3;
        } while (true);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean checkTouch(float f, float f2) {
        this.selectedValue.clear();
        if (this.dataProvider.getColumnChartData().isStacked()) {
            ColumnChartRenderer.super.checkTouchForStacked(f, f2);
            do {
                return this.isTouched();
                break;
            } while (true);
        }
        ColumnChartRenderer.super.checkTouchForSubcolumns(f, f2);
        return this.isTouched();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void draw(Canvas canvas) {
        if (this.dataProvider.getColumnChartData().isStacked()) {
            ColumnChartRenderer.super.drawColumnForStacked(canvas);
            if (!this.isTouched()) return;
            {
                ColumnChartRenderer.super.highlightColumnForStacked(canvas);
                return;
            }
        } else {
            ColumnChartRenderer.super.drawColumnsForSubcolumns(canvas);
            if (!this.isTouched()) return;
            {
                ColumnChartRenderer.super.highlightColumnsForSubcolumns(canvas);
                return;
            }
        }
    }

    @Override
    public void drawUnclipped(Canvas canvas) {
    }

    @Override
    public void onChartDataChanged() {
        super.onChartDataChanged();
        ColumnChartData columnChartData = this.dataProvider.getColumnChartData();
        this.fillRatio = columnChartData.getFillRatio();
        this.baseValue = columnChartData.getBaseValue();
        this.onChartViewportChanged();
    }

    @Override
    public void onChartSizeChanged() {
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

