/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 */
package lecho.lib.hellocharts.renderer;

import android.content.Context;
import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.renderer.AbstractChartRenderer;
import lecho.lib.hellocharts.renderer.ChartRenderer;
import lecho.lib.hellocharts.view.Chart;

public class ComboChartRenderer
extends AbstractChartRenderer {
    protected List<ChartRenderer> renderers = new ArrayList();
    protected Viewport unionViewport = new Viewport();

    public ComboChartRenderer(Context context, Chart chart) {
        super(context, chart);
    }

    @Override
    public boolean checkTouch(float f, float f2) {
        this.selectedValue.clear();
        int n = -1 + this.renderers.size();
        do {
            block4 : {
                block3 : {
                    if (n < 0) break block3;
                    ChartRenderer chartRenderer = (ChartRenderer)this.renderers.get(n);
                    if (!chartRenderer.checkTouch(f, f2)) break block4;
                    this.selectedValue.set(chartRenderer.getSelectedValue());
                }
                for (int i = n - 1; i >= 0; --i) {
                    ((ChartRenderer)this.renderers.get(i)).clearTouch();
                }
                break;
            }
            --n;
        } while (true);
        return this.isTouched();
    }

    @Override
    public void clearTouch() {
        Iterator iterator = this.renderers.iterator();
        while (iterator.hasNext()) {
            ((ChartRenderer)iterator.next()).clearTouch();
        }
        this.selectedValue.clear();
    }

    @Override
    public void draw(Canvas canvas) {
        Iterator iterator = this.renderers.iterator();
        while (iterator.hasNext()) {
            ((ChartRenderer)iterator.next()).draw(canvas);
        }
    }

    @Override
    public void drawUnclipped(Canvas canvas) {
        Iterator iterator = this.renderers.iterator();
        while (iterator.hasNext()) {
            ((ChartRenderer)iterator.next()).drawUnclipped(canvas);
        }
    }

    @Override
    public void onChartDataChanged() {
        super.onChartDataChanged();
        Iterator iterator = this.renderers.iterator();
        while (iterator.hasNext()) {
            ((ChartRenderer)iterator.next()).onChartDataChanged();
        }
        this.onChartViewportChanged();
    }

    @Override
    public void onChartSizeChanged() {
        Iterator iterator = this.renderers.iterator();
        while (iterator.hasNext()) {
            ((ChartRenderer)iterator.next()).onChartSizeChanged();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onChartViewportChanged() {
        if (this.isViewportCalculationEnabled) {
            int n = 0;
            for (ChartRenderer chartRenderer : this.renderers) {
                chartRenderer.onChartViewportChanged();
                if (n == 0) {
                    this.unionViewport.set(chartRenderer.getMaximumViewport());
                } else {
                    this.unionViewport.union(chartRenderer.getMaximumViewport());
                }
                ++n;
            }
            this.computator.setMaxViewport(this.unionViewport);
            this.computator.setCurrentViewport(this.unionViewport);
        }
    }
}

