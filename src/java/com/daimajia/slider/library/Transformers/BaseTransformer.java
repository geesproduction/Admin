/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  com.nineoldandroids.view.ViewHelper
 *  java.lang.Float
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.HashMap
 */
package com.daimajia.slider.library.Transformers;

import android.view.View;
import com.daimajia.slider.library.Animations.BaseAnimationInterface;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.nineoldandroids.view.ViewHelper;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseTransformer
implements ViewPagerEx.PageTransformer {
    private HashMap<View, ArrayList<Float>> h = new HashMap();
    boolean isApp;
    boolean isDis;
    private BaseAnimationInterface mCustomAnimationInterface;

    protected boolean hideOffscreenPages() {
        return true;
    }

    protected boolean isPagingEnabled() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onPostTransform(View view, float f) {
        if (this.mCustomAnimationInterface != null) {
            if (f == -1.0f || f == 1.0f) {
                this.mCustomAnimationInterface.onCurrentItemDisappear(view);
                this.isApp = true;
            } else if (f == 0.0f) {
                this.mCustomAnimationInterface.onNextItemAppear(view);
                this.isDis = true;
            }
            if (this.isApp && this.isDis) {
                this.h.clear();
                this.isApp = false;
                this.isDis = false;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onPreTransform(View view, float f) {
        float f2;
        block8 : {
            block9 : {
                block7 : {
                    float f3 = view.getWidth();
                    ViewHelper.setRotationX((View)view, (float)0.0f);
                    ViewHelper.setRotationY((View)view, (float)0.0f);
                    ViewHelper.setRotation((View)view, (float)0.0f);
                    ViewHelper.setScaleX((View)view, (float)1.0f);
                    ViewHelper.setScaleY((View)view, (float)1.0f);
                    ViewHelper.setPivotX((View)view, (float)0.0f);
                    ViewHelper.setPivotY((View)view, (float)0.0f);
                    ViewHelper.setTranslationY((View)view, (float)0.0f);
                    float f4 = this.isPagingEnabled() ? 0.0f : f * -f3;
                    ViewHelper.setTranslationX((View)view, (float)f4);
                    if (this.hideOffscreenPages()) {
                        float f5 = f <= -1.0f || f >= 1.0f ? 0.0f : 1.0f;
                        ViewHelper.setAlpha((View)view, (float)f5);
                    } else {
                        ViewHelper.setAlpha((View)view, (float)1.0f);
                    }
                    if (this.mCustomAnimationInterface == null || this.h.containsKey((Object)view) && ((ArrayList)this.h.get((Object)view)).size() != 1 || !(f > -1.0f) || !(f < 1.0f)) break block7;
                    if (this.h.get((Object)view) == null) {
                        this.h.put((Object)view, (Object)new ArrayList());
                    }
                    ((ArrayList)this.h.get((Object)view)).add((Object)Float.valueOf((float)f));
                    if (((ArrayList)this.h.get((Object)view)).size() != 2) break block7;
                    float f6 = ((Float)((ArrayList)this.h.get((Object)view)).get(0)).floatValue();
                    f2 = ((Float)((ArrayList)this.h.get((Object)view)).get(1)).floatValue() - ((Float)((ArrayList)this.h.get((Object)view)).get(0)).floatValue();
                    if (!(f6 > 0.0f)) break block8;
                    if (!(f2 > -1.0f) || !(f2 < 0.0f)) break block9;
                    this.mCustomAnimationInterface.onPrepareNextItemShowInScreen(view);
                }
                return;
            }
            this.mCustomAnimationInterface.onPrepareCurrentItemLeaveScreen(view);
            return;
        }
        if (f2 > -1.0f && f2 < 0.0f) {
            this.mCustomAnimationInterface.onPrepareCurrentItemLeaveScreen(view);
            return;
        }
        this.mCustomAnimationInterface.onPrepareNextItemShowInScreen(view);
    }

    protected abstract void onTransform(View var1, float var2);

    public void setCustomAnimationInterface(BaseAnimationInterface baseAnimationInterface) {
        this.mCustomAnimationInterface = baseAnimationInterface;
    }

    @Override
    public void transformPage(View view, float f) {
        this.onPreTransform(view, f);
        this.onTransform(view, f);
        this.onPostTransform(view, f);
    }
}

