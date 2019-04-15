/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  java.lang.Object
 */
package com.daimajia.slider.library.Animations;

import android.view.View;

public interface BaseAnimationInterface {
    public void onCurrentItemDisappear(View var1);

    public void onNextItemAppear(View var1);

    public void onPrepareCurrentItemLeaveScreen(View var1);

    public void onPrepareNextItemShowInScreen(View var1);
}

