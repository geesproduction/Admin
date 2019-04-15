/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  com.nineoldandroids.animation.ObjectAnimator
 *  com.nineoldandroids.view.ViewHelper
 *  java.lang.Object
 *  java.lang.String
 */
package com.daimajia.slider.library.Animations;

import android.view.View;
import com.daimajia.slider.library.Animations.BaseAnimationInterface;
import com.daimajia.slider.library.R;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class DescriptionAnimation
implements BaseAnimationInterface {
    @Override
    public void onCurrentItemDisappear(View view) {
    }

    @Override
    public void onNextItemAppear(View view) {
        View view2 = view.findViewById(R.id.description_layout);
        if (view2 != null) {
            float f = ViewHelper.getY((View)view2);
            view.findViewById(R.id.description_layout).setVisibility(0);
            float[] arrf = new float[]{f + (float)view2.getHeight(), f};
            ObjectAnimator.ofFloat((Object)view2, (String)"y", (float[])arrf).setDuration(500L).start();
        }
    }

    @Override
    public void onPrepareCurrentItemLeaveScreen(View view) {
        if (view.findViewById(R.id.description_layout) != null) {
            view.findViewById(R.id.description_layout).setVisibility(4);
        }
    }

    @Override
    public void onPrepareNextItemShowInScreen(View view) {
        if (view.findViewById(R.id.description_layout) != null) {
            view.findViewById(R.id.description_layout).setVisibility(4);
        }
    }
}

