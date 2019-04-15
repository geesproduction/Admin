/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  java.lang.Object
 */
package me.tangke.slidemenu.utils;

import android.view.View;
import me.tangke.slidemenu.utils.ScrollDetectors;

public interface ScrollDetectorFactory {
    public ScrollDetectors.ScrollDetector newScrollDetector(View var1);
}

