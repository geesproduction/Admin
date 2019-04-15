/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Object
 *  java.util.List
 */
package lecho.lib.hellocharts.renderer;

import android.content.Context;
import java.util.List;
import lecho.lib.hellocharts.provider.ColumnChartDataProvider;
import lecho.lib.hellocharts.provider.LineChartDataProvider;
import lecho.lib.hellocharts.renderer.ColumnChartRenderer;
import lecho.lib.hellocharts.renderer.ComboChartRenderer;
import lecho.lib.hellocharts.renderer.LineChartRenderer;
import lecho.lib.hellocharts.view.Chart;

public class ComboLineColumnChartRenderer
extends ComboChartRenderer {
    private ColumnChartRenderer columnChartRenderer;
    private LineChartRenderer lineChartRenderer;

    public ComboLineColumnChartRenderer(Context context, Chart chart, ColumnChartDataProvider columnChartDataProvider, LineChartDataProvider lineChartDataProvider) {
        super(context, chart, new ColumnChartRenderer(context, chart, columnChartDataProvider), new LineChartRenderer(context, chart, lineChartDataProvider));
    }

    public ComboLineColumnChartRenderer(Context context, Chart chart, ColumnChartDataProvider columnChartDataProvider, LineChartRenderer lineChartRenderer) {
        super(context, chart, new ColumnChartRenderer(context, chart, columnChartDataProvider), lineChartRenderer);
    }

    public ComboLineColumnChartRenderer(Context context, Chart chart, ColumnChartRenderer columnChartRenderer, LineChartDataProvider lineChartDataProvider) {
        super(context, chart, columnChartRenderer, new LineChartRenderer(context, chart, lineChartDataProvider));
    }

    public ComboLineColumnChartRenderer(Context context, Chart chart, ColumnChartRenderer columnChartRenderer, LineChartRenderer lineChartRenderer) {
        super(context, chart);
        this.columnChartRenderer = columnChartRenderer;
        this.lineChartRenderer = lineChartRenderer;
        this.renderers.add((Object)this.columnChartRenderer);
        this.renderers.add((Object)this.lineChartRenderer);
    }
}

