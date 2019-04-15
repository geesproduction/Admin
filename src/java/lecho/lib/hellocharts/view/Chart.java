/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package lecho.lib.hellocharts.view;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.gesture.ChartTouchHandler;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.renderer.AxesRenderer;
import lecho.lib.hellocharts.renderer.ChartRenderer;

public interface Chart {
    public void animationDataFinished();

    public void animationDataUpdate(float var1);

    public void callTouchListener();

    public void cancelDataAnimation();

    public AxesRenderer getAxesRenderer();

    public ChartComputator getChartComputator();

    public ChartData getChartData();

    public ChartRenderer getChartRenderer();

    public Viewport getCurrentViewport();

    public float getMaxZoom();

    public Viewport getMaximumViewport();

    public SelectedValue getSelectedValue();

    public ChartTouchHandler getTouchHandler();

    public float getZoomLevel();

    public ZoomType getZoomType();

    public boolean isContainerScrollEnabled();

    public boolean isInteractive();

    public boolean isScrollEnabled();

    public boolean isValueSelectionEnabled();

    public boolean isValueTouchEnabled();

    public boolean isViewportCalculationEnabled();

    public boolean isZoomEnabled();

    public void moveTo(float var1, float var2);

    public void moveToWithAnimation(float var1, float var2);

    public void resetViewports();

    public void selectValue(SelectedValue var1);

    public void setChartRenderer(ChartRenderer var1);

    public void setContainerScrollEnabled(boolean var1, ContainerScrollType var2);

    public void setCurrentViewport(Viewport var1);

    public void setCurrentViewportWithAnimation(Viewport var1);

    public void setCurrentViewportWithAnimation(Viewport var1, long var2);

    public void setDataAnimationListener(ChartAnimationListener var1);

    public void setInteractive(boolean var1);

    public void setMaxZoom(float var1);

    public void setMaximumViewport(Viewport var1);

    public void setScrollEnabled(boolean var1);

    public void setValueSelectionEnabled(boolean var1);

    public void setValueTouchEnabled(boolean var1);

    public void setViewportAnimationListener(ChartAnimationListener var1);

    public void setViewportCalculationEnabled(boolean var1);

    public void setViewportChangeListener(ViewportChangeListener var1);

    public void setZoomEnabled(boolean var1);

    public void setZoomLevel(float var1, float var2, float var3);

    public void setZoomLevelWithAnimation(float var1, float var2, float var3);

    public void setZoomType(ZoomType var1);

    public void startDataAnimation();

    public void startDataAnimation(long var1);
}

