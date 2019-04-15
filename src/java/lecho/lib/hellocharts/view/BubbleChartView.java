/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.support.v4.view.ViewCompat
 *  android.util.AttributeSet
 *  android.view.View
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package lecho.lib.hellocharts.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;
import lecho.lib.hellocharts.listener.BubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.DummyBubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.provider.BubbleChartDataProvider;
import lecho.lib.hellocharts.renderer.BubbleChartRenderer;
import lecho.lib.hellocharts.renderer.ChartRenderer;
import lecho.lib.hellocharts.view.AbstractChartView;
import lecho.lib.hellocharts.view.Chart;

public class BubbleChartView
extends AbstractChartView
implements BubbleChartDataProvider {
    private static final String TAG = "BubbleChartView";
    protected BubbleChartRenderer bubbleChartRenderer;
    protected BubbleChartData data;
    protected BubbleChartOnValueSelectListener onValueTouchListener;

    public BubbleChartView(Context context) {
        super(context, null, 0);
    }

    public BubbleChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public BubbleChartView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.onValueTouchListener = new DummyBubbleChartOnValueSelectListener();
        this.bubbleChartRenderer = new BubbleChartRenderer(context, (Chart)this, (BubbleChartDataProvider)this);
        this.setChartRenderer(this.bubbleChartRenderer);
        this.setBubbleChartData(BubbleChartData.generateDummyData());
    }

    @Override
    public void callTouchListener() {
        SelectedValue selectedValue = this.chartRenderer.getSelectedValue();
        if (selectedValue.isSet()) {
            BubbleValue bubbleValue = (BubbleValue)this.data.getValues().get(selectedValue.getFirstIndex());
            this.onValueTouchListener.onValueSelected(selectedValue.getFirstIndex(), bubbleValue);
            return;
        }
        this.onValueTouchListener.onValueDeselected();
    }

    @Override
    public BubbleChartData getBubbleChartData() {
        return this.data;
    }

    @Override
    public ChartData getChartData() {
        return this.data;
    }

    public BubbleChartOnValueSelectListener getOnValueTouchListener() {
        return this.onValueTouchListener;
    }

    public void removeMargins() {
        this.bubbleChartRenderer.removeMargins();
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setBubbleChartData(BubbleChartData bubbleChartData) {
        this.data = bubbleChartData == null ? BubbleChartData.generateDummyData() : bubbleChartData;
        super.onChartDataChange();
    }

    public void setOnValueTouchListener(BubbleChartOnValueSelectListener bubbleChartOnValueSelectListener) {
        if (bubbleChartOnValueSelectListener != null) {
            this.onValueTouchListener = bubbleChartOnValueSelectListener;
        }
    }
}

