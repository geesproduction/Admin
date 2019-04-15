/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.SystemClock
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  java.lang.Object
 */
package lecho.lib.hellocharts.gesture;

import android.content.Context;
import android.os.SystemClock;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

public class ZoomerCompat {
    private static final int DEFAULT_SHORT_ANIMATION_DURATION = 200;
    private long mAnimationDurationMillis = 200L;
    private float mCurrentZoom;
    private float mEndZoom;
    private boolean mFinished = true;
    private Interpolator mInterpolator = new DecelerateInterpolator();
    private long mStartRTC;

    public ZoomerCompat(Context context) {
    }

    public void abortAnimation() {
        this.mFinished = true;
        this.mCurrentZoom = this.mEndZoom;
    }

    public boolean computeZoom() {
        if (this.mFinished) {
            return false;
        }
        long l = SystemClock.elapsedRealtime() - this.mStartRTC;
        if (l >= this.mAnimationDurationMillis) {
            this.mFinished = true;
            this.mCurrentZoom = this.mEndZoom;
            return false;
        }
        float f = 1.0f * (float)l / (float)this.mAnimationDurationMillis;
        this.mCurrentZoom = this.mEndZoom * this.mInterpolator.getInterpolation(f);
        return true;
    }

    public void forceFinished(boolean bl) {
        this.mFinished = bl;
    }

    public float getCurrZoom() {
        return this.mCurrentZoom;
    }

    public void startZoom(float f) {
        this.mStartRTC = SystemClock.elapsedRealtime();
        this.mEndZoom = f;
        this.mFinished = false;
        this.mCurrentZoom = 1.0f;
    }
}

