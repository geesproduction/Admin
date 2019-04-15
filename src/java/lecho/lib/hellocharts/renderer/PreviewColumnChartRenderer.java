/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 */
package lecho.lib.hellocharts.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.provider.ColumnChartDataProvider;
import lecho.lib.hellocharts.renderer.ColumnChartRenderer;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;

public class PreviewColumnChartRenderer
extends ColumnChartRenderer {
    private static final int DEFAULT_PREVIEW_STROKE_WIDTH_DP = 2;
    private static final int DEFAULT_PREVIEW_TRANSPARENCY = 64;
    private static final int FULL_ALPHA = 255;
    private Paint previewPaint = new Paint();

    public PreviewColumnChartRenderer(Context context, Chart chart, ColumnChartDataProvider columnChartDataProvider) {
        super(context, chart, columnChartDataProvider);
        this.previewPaint.setAntiAlias(true);
        this.previewPaint.setColor(-3355444);
        this.previewPaint.setStrokeWidth((float)ChartUtils.dp2px(this.density, 2));
    }

    @Override
    public void drawUnclipped(Canvas canvas) {
        super.drawUnclipped(canvas);
        Viewport viewport = this.computator.getCurrentViewport();
        float f = this.computator.computeRawX(viewport.left);
        float f2 = this.computator.computeRawY(viewport.top);
        float f3 = this.computator.computeRawX(viewport.right);
        float f4 = this.computator.computeRawY(viewport.bottom);
        this.previewPaint.setAlpha(64);
        this.previewPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(f, f2, f3, f4, this.previewPaint);
        this.previewPaint.setStyle(Paint.Style.STROKE);
        this.previewPaint.setAlpha(255);
        canvas.drawRect(f, f2, f3, f4, this.previewPaint);
    }

    public int getPreviewColor() {
        return this.previewPaint.getColor();
    }

    public void setPreviewColor(int n) {
        this.previewPaint.setColor(n);
    }
}

