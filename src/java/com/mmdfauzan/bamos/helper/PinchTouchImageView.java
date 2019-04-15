/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.ScaleGestureDetector$OnScaleGestureListener
 *  android.view.ScaleGestureDetector$SimpleOnScaleGestureListener
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnTouchListener
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  java.lang.Math
 *  java.lang.Object
 */
package com.mmdfauzan.bamos.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

@SuppressLint(value={"ClickableViewAccessibility"})
public class PinchTouchImageView
extends ImageView {
    static final int CLICK = 3;
    static final int DRAG = 1;
    static final int NONE = 0;
    static final int ZOOM = 2;
    int MODE = 0;
    Context mContext;
    Matrix mMatrix;
    protected float mOrigHeight;
    protected float mOrigWidth;
    PointF mPointLast = new PointF();
    PointF mPointStatr = new PointF();
    float mSaveScale = 1.0f;
    ScaleGestureDetector mScaleDetector;
    int mViewHeight;
    int mViewWidth;
    float[] mat;
    float maxScale = 3.0f;
    float minScale = 1.0f;
    int oldMeasuredHeight;
    int oldMeasuredWidth;

    public PinchTouchImageView(Context context) {
        super(context);
        PinchTouchImageView.super.sharedConstructing(context);
    }

    public PinchTouchImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        PinchTouchImageView.super.sharedConstructing(context);
    }

    private void sharedConstructing(Context context) {
        super.setClickable(true);
        this.mContext = context;
        this.mScaleDetector = new ScaleGestureDetector(context, (ScaleGestureDetector.OnScaleGestureListener)new ScaleListener((PinchTouchImageView)this, null));
        this.mMatrix = new Matrix();
        this.mat = new float[9];
        this.setImageMatrix(this.mMatrix);
        this.setScaleType(ImageView.ScaleType.MATRIX);
        this.setOnTouchListener(new View.OnTouchListener(){

            /*
             * Enabled aggressive block sorting
             */
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PinchTouchImageView.this.mScaleDetector.onTouchEvent(motionEvent);
                PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
                switch (motionEvent.getAction()) {
                    case 0: {
                        PinchTouchImageView.this.mPointLast.set(pointF);
                        PinchTouchImageView.this.mPointStatr.set(PinchTouchImageView.this.mPointLast);
                        PinchTouchImageView.this.MODE = 1;
                        break;
                    }
                    case 2: {
                        if (PinchTouchImageView.this.MODE != 1) break;
                        float f = pointF.x - PinchTouchImageView.this.mPointLast.x;
                        float f2 = pointF.y - PinchTouchImageView.this.mPointLast.y;
                        float f3 = PinchTouchImageView.this.getFixDragTrans(f, PinchTouchImageView.this.mViewWidth, PinchTouchImageView.this.mOrigWidth * PinchTouchImageView.this.mSaveScale);
                        float f4 = PinchTouchImageView.this.getFixDragTrans(f2, PinchTouchImageView.this.mViewHeight, PinchTouchImageView.this.mOrigHeight * PinchTouchImageView.this.mSaveScale);
                        PinchTouchImageView.this.mMatrix.postTranslate(f3, f4);
                        PinchTouchImageView.this.fixTrans();
                        PinchTouchImageView.this.mPointLast.set(pointF.x, pointF.y);
                        break;
                    }
                    case 1: {
                        PinchTouchImageView.this.MODE = 0;
                        int n = (int)Math.abs((float)(pointF.x - PinchTouchImageView.this.mPointStatr.x));
                        int n2 = (int)Math.abs((float)(pointF.y - PinchTouchImageView.this.mPointStatr.y));
                        if (n >= 3 || n2 >= 3) break;
                        PinchTouchImageView.this.performClick();
                        break;
                    }
                    case 6: {
                        PinchTouchImageView.this.MODE = 0;
                        break;
                    }
                }
                PinchTouchImageView.this.setImageMatrix(PinchTouchImageView.this.mMatrix);
                PinchTouchImageView.this.invalidate();
                return true;
            }
        });
    }

    void fixTrans() {
        this.mMatrix.getValues(this.mat);
        float f = this.mat[2];
        float f2 = this.mat[5];
        float f3 = this.getFixTrans(f, this.mViewWidth, this.mOrigWidth * this.mSaveScale);
        float f4 = this.getFixTrans(f2, this.mViewHeight, this.mOrigHeight * this.mSaveScale);
        if (f3 != 0.0f || f4 != 0.0f) {
            this.mMatrix.postTranslate(f3, f4);
        }
    }

    float getFixDragTrans(float f, float f2, float f3) {
        if (f3 <= f2) {
            f = 0.0f;
        }
        return f;
    }

    /*
     * Enabled aggressive block sorting
     */
    float getFixTrans(float f, float f2, float f3) {
        float f4;
        float f5;
        if (f3 <= f2) {
            f4 = 0.0f;
            f5 = f2 - f3;
        } else {
            f4 = f2 - f3;
            f5 = 0.0f;
        }
        if (f < f4) {
            return f4 + -f;
        }
        if (f > f5) {
            return f5 + -f;
        }
        return 0.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        block3 : {
            Drawable drawable2;
            block4 : {
                block2 : {
                    super.onMeasure(n, n2);
                    this.mViewWidth = View.MeasureSpec.getSize((int)n);
                    this.mViewHeight = View.MeasureSpec.getSize((int)n2);
                    if (this.oldMeasuredHeight == this.mViewWidth && this.oldMeasuredHeight == this.mViewHeight || this.mViewWidth == 0 || this.mViewHeight == 0) break block2;
                    this.oldMeasuredHeight = this.mViewHeight;
                    this.oldMeasuredWidth = this.mViewWidth;
                    if (this.mSaveScale != 1.0f) break block3;
                    drawable2 = this.getDrawable();
                    if (drawable2 != null && drawable2.getIntrinsicWidth() != 0 && drawable2.getIntrinsicHeight() != 0) break block4;
                }
                return;
            }
            int n3 = drawable2.getIntrinsicWidth();
            int n4 = drawable2.getIntrinsicHeight();
            float f = Math.min((float)((float)this.mViewWidth / (float)n3), (float)((float)this.mViewHeight / (float)n4));
            this.mMatrix.setScale(f, f);
            float f2 = (float)this.mViewHeight - f * (float)n4;
            float f3 = (float)this.mViewWidth - f * (float)n3;
            float f4 = f2 / 2.0f;
            float f5 = f3 / 2.0f;
            this.mMatrix.postTranslate(f5, f4);
            this.mOrigWidth = (float)this.mViewWidth - 2.0f * f5;
            this.mOrigHeight = (float)this.mViewHeight - 2.0f * f4;
            this.setImageMatrix(this.mMatrix);
        }
        this.fixTrans();
    }

    public void setMaxZoom(float f) {
        this.maxScale = f;
    }

    private class ScaleListener
    extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        final /* synthetic */ PinchTouchImageView this$0;

        private ScaleListener(PinchTouchImageView pinchTouchImageView) {
            this.this$0 = pinchTouchImageView;
        }

        /* synthetic */ ScaleListener(PinchTouchImageView pinchTouchImageView, 1 var2_2) {
            super(pinchTouchImageView);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            float f = scaleGestureDetector.getScaleFactor();
            float f2 = this.this$0.mSaveScale;
            PinchTouchImageView pinchTouchImageView = this.this$0;
            pinchTouchImageView.mSaveScale = f * pinchTouchImageView.mSaveScale;
            if (this.this$0.mSaveScale > this.this$0.maxScale) {
                this.this$0.mSaveScale = this.this$0.maxScale;
                f = this.this$0.maxScale / f2;
            } else if (this.this$0.mSaveScale < this.this$0.minScale) {
                this.this$0.mSaveScale = this.this$0.minScale;
                f = this.this$0.minScale / f2;
            }
            if (this.this$0.mOrigWidth * this.this$0.mSaveScale <= (float)this.this$0.mViewWidth || this.this$0.mOrigHeight * this.this$0.mSaveScale <= (float)this.this$0.mViewHeight) {
                this.this$0.mMatrix.postScale(f, f, (float)(this.this$0.mViewWidth / 2), (float)(this.this$0.mViewHeight / 2));
            } else {
                this.this$0.mMatrix.postScale(f, f, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            }
            this.this$0.fixTrans();
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            this.this$0.MODE = 2;
            return true;
        }
    }

}

