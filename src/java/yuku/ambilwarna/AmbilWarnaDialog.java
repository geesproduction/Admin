/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.graphics.Color
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.GradientDrawable$Orientation
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.ImageView
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  java.lang.Math
 *  java.lang.Object
 */
package yuku.ambilwarna;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import yuku.ambilwarna.AmbilWarnaSquare;
import yuku.ambilwarna.R;

public class AmbilWarnaDialog {
    int alpha;
    final float[] currentColorHsv;
    final AlertDialog dialog;
    final OnAmbilWarnaListener listener;
    private final boolean supportsAlpha;
    final ImageView viewAlphaCheckered;
    final ImageView viewAlphaCursor;
    final View viewAlphaOverlay;
    final ViewGroup viewContainer;
    final ImageView viewCursor;
    final View viewHue;
    final View viewNewColor;
    final View viewOldColor;
    final AmbilWarnaSquare viewSatVal;
    final ImageView viewTarget;

    public AmbilWarnaDialog(Context context, int n, OnAmbilWarnaListener onAmbilWarnaListener) {
        super(context, n, false, onAmbilWarnaListener);
    }

    /*
     * Enabled aggressive block sorting
     */
    public AmbilWarnaDialog(Context context, int n, boolean bl, OnAmbilWarnaListener onAmbilWarnaListener) {
        int n2 = 8;
        this.currentColorHsv = new float[3];
        this.supportsAlpha = bl;
        this.listener = onAmbilWarnaListener;
        if (!bl) {
            n |= -16777216;
        }
        Color.colorToHSV((int)n, (float[])this.currentColorHsv);
        this.alpha = Color.alpha((int)n);
        final View view = LayoutInflater.from((Context)context).inflate(R.layout.ambilwarna_dialog, null);
        this.viewHue = view.findViewById(R.id.ambilwarna_viewHue);
        this.viewSatVal = (AmbilWarnaSquare)view.findViewById(R.id.ambilwarna_viewSatBri);
        this.viewCursor = (ImageView)view.findViewById(R.id.ambilwarna_cursor);
        this.viewOldColor = view.findViewById(R.id.ambilwarna_oldColor);
        this.viewNewColor = view.findViewById(R.id.ambilwarna_newColor);
        this.viewTarget = (ImageView)view.findViewById(R.id.ambilwarna_target);
        this.viewContainer = (ViewGroup)view.findViewById(R.id.ambilwarna_viewContainer);
        this.viewAlphaOverlay = view.findViewById(R.id.ambilwarna_overlay);
        this.viewAlphaCursor = (ImageView)view.findViewById(R.id.ambilwarna_alphaCursor);
        this.viewAlphaCheckered = (ImageView)view.findViewById(R.id.ambilwarna_alphaCheckered);
        View view2 = this.viewAlphaOverlay;
        int n3 = bl ? 0 : n2;
        view2.setVisibility(n3);
        ImageView imageView = this.viewAlphaCursor;
        int n4 = bl ? 0 : n2;
        imageView.setVisibility(n4);
        ImageView imageView2 = this.viewAlphaCheckered;
        if (bl) {
            n2 = 0;
        }
        imageView2.setVisibility(n2);
        this.viewSatVal.setHue(AmbilWarnaDialog.super.getHue());
        this.viewOldColor.setBackgroundColor(n);
        this.viewNewColor.setBackgroundColor(n);
        this.viewHue.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 2 || motionEvent.getAction() == 0 || motionEvent.getAction() == 1) {
                    float f;
                    float f2 = motionEvent.getY();
                    if (f2 < 0.0f) {
                        f2 = 0.0f;
                    }
                    if (f2 > (float)AmbilWarnaDialog.this.viewHue.getMeasuredHeight()) {
                        f2 = (float)AmbilWarnaDialog.this.viewHue.getMeasuredHeight() - 0.001f;
                    }
                    if ((f = 360.0f - f2 * (360.0f / (float)AmbilWarnaDialog.this.viewHue.getMeasuredHeight())) == 360.0f) {
                        f = 0.0f;
                    }
                    AmbilWarnaDialog.this.setHue(f);
                    AmbilWarnaDialog.this.viewSatVal.setHue(AmbilWarnaDialog.this.getHue());
                    AmbilWarnaDialog.this.moveCursor();
                    AmbilWarnaDialog.this.viewNewColor.setBackgroundColor(AmbilWarnaDialog.this.getColor());
                    AmbilWarnaDialog.this.updateAlphaView();
                    return true;
                }
                return false;
            }
        });
        if (bl) {
            this.viewAlphaCheckered.setOnTouchListener(new View.OnTouchListener(){

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 2 || motionEvent.getAction() == 0 || motionEvent.getAction() == 1) {
                        float f = motionEvent.getY();
                        if (f < 0.0f) {
                            f = 0.0f;
                        }
                        if (f > (float)AmbilWarnaDialog.this.viewAlphaCheckered.getMeasuredHeight()) {
                            f = (float)AmbilWarnaDialog.this.viewAlphaCheckered.getMeasuredHeight() - 0.001f;
                        }
                        int n = Math.round((float)(255.0f - f * (255.0f / (float)AmbilWarnaDialog.this.viewAlphaCheckered.getMeasuredHeight())));
                        AmbilWarnaDialog.this.setAlpha(n);
                        AmbilWarnaDialog.this.moveAlphaCursor();
                        int n2 = AmbilWarnaDialog.this.getColor();
                        int n3 = n << 24 | 16777215 & n2;
                        AmbilWarnaDialog.this.viewNewColor.setBackgroundColor(n3);
                        return true;
                    }
                    return false;
                }
            });
        }
        this.viewSatVal.setOnTouchListener(new View.OnTouchListener(){

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 2 || motionEvent.getAction() == 0 || motionEvent.getAction() == 1) {
                    float f = motionEvent.getX();
                    float f2 = motionEvent.getY();
                    if (f < 0.0f) {
                        f = 0.0f;
                    }
                    if (f > (float)AmbilWarnaDialog.this.viewSatVal.getMeasuredWidth()) {
                        f = AmbilWarnaDialog.this.viewSatVal.getMeasuredWidth();
                    }
                    if (f2 < 0.0f) {
                        f2 = 0.0f;
                    }
                    if (f2 > (float)AmbilWarnaDialog.this.viewSatVal.getMeasuredHeight()) {
                        f2 = AmbilWarnaDialog.this.viewSatVal.getMeasuredHeight();
                    }
                    AmbilWarnaDialog.this.setSat(f * (1.0f / (float)AmbilWarnaDialog.this.viewSatVal.getMeasuredWidth()));
                    AmbilWarnaDialog.this.setVal(1.0f - f2 * (1.0f / (float)AmbilWarnaDialog.this.viewSatVal.getMeasuredHeight()));
                    AmbilWarnaDialog.this.moveTarget();
                    AmbilWarnaDialog.this.viewNewColor.setBackgroundColor(AmbilWarnaDialog.this.getColor());
                    return true;
                }
                return false;
            }
        });
        this.dialog = new AlertDialog.Builder(context).setPositiveButton(17039370, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                if (AmbilWarnaDialog.this.listener != null) {
                    AmbilWarnaDialog.this.listener.onOk(AmbilWarnaDialog.this, AmbilWarnaDialog.this.getColor());
                }
            }
        }).setNegativeButton(17039360, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                if (AmbilWarnaDialog.this.listener != null) {
                    AmbilWarnaDialog.this.listener.onCancel(AmbilWarnaDialog.this);
                }
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener(){

            public void onCancel(DialogInterface dialogInterface) {
                if (AmbilWarnaDialog.this.listener != null) {
                    AmbilWarnaDialog.this.listener.onCancel(AmbilWarnaDialog.this);
                }
            }
        }).create();
        this.dialog.setView(view, 0, 0, 0, 0);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            public void onGlobalLayout() {
                AmbilWarnaDialog.this.moveCursor();
                if (AmbilWarnaDialog.this.supportsAlpha) {
                    AmbilWarnaDialog.this.moveAlphaCursor();
                }
                AmbilWarnaDialog.this.moveTarget();
                if (AmbilWarnaDialog.this.supportsAlpha) {
                    AmbilWarnaDialog.this.updateAlphaView();
                }
                view.getViewTreeObserver().removeGlobalOnLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
            }
        });
    }

    private float getAlpha() {
        return this.alpha;
    }

    private int getColor() {
        int n = Color.HSVToColor((float[])this.currentColorHsv);
        return this.alpha << 24 | 16777215 & n;
    }

    private float getHue() {
        return this.currentColorHsv[0];
    }

    private float getSat() {
        return this.currentColorHsv[1];
    }

    private float getVal() {
        return this.currentColorHsv[2];
    }

    private void setAlpha(int n) {
        this.alpha = n;
    }

    private void setHue(float f) {
        this.currentColorHsv[0] = f;
    }

    private void setSat(float f) {
        this.currentColorHsv[1] = f;
    }

    private void setVal(float f) {
        this.currentColorHsv[2] = f;
    }

    private void updateAlphaView() {
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.TOP_BOTTOM;
        int[] arrn = new int[]{Color.HSVToColor((float[])this.currentColorHsv), 0};
        GradientDrawable gradientDrawable = new GradientDrawable(orientation, arrn);
        this.viewAlphaOverlay.setBackgroundDrawable((Drawable)gradientDrawable);
    }

    public AlertDialog getDialog() {
        return this.dialog;
    }

    protected void moveAlphaCursor() {
        int n = this.viewAlphaCheckered.getMeasuredHeight();
        float f = (float)n - this.getAlpha() * (float)n / 255.0f;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)this.viewAlphaCursor.getLayoutParams();
        layoutParams.leftMargin = (int)((double)this.viewAlphaCheckered.getLeft() - Math.floor((double)(this.viewAlphaCursor.getMeasuredWidth() / 2)) - (double)this.viewContainer.getPaddingLeft());
        layoutParams.topMargin = (int)((double)(f + (float)this.viewAlphaCheckered.getTop()) - Math.floor((double)(this.viewAlphaCursor.getMeasuredHeight() / 2)) - (double)this.viewContainer.getPaddingTop());
        this.viewAlphaCursor.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    protected void moveCursor() {
        float f = (float)this.viewHue.getMeasuredHeight() - this.getHue() * (float)this.viewHue.getMeasuredHeight() / 360.0f;
        if (f == (float)this.viewHue.getMeasuredHeight()) {
            f = 0.0f;
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)this.viewCursor.getLayoutParams();
        layoutParams.leftMargin = (int)((double)this.viewHue.getLeft() - Math.floor((double)(this.viewCursor.getMeasuredWidth() / 2)) - (double)this.viewContainer.getPaddingLeft());
        layoutParams.topMargin = (int)((double)(f + (float)this.viewHue.getTop()) - Math.floor((double)(this.viewCursor.getMeasuredHeight() / 2)) - (double)this.viewContainer.getPaddingTop());
        this.viewCursor.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    protected void moveTarget() {
        float f = this.getSat() * (float)this.viewSatVal.getMeasuredWidth();
        float f2 = (1.0f - this.getVal()) * (float)this.viewSatVal.getMeasuredHeight();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)this.viewTarget.getLayoutParams();
        layoutParams.leftMargin = (int)((double)(f + (float)this.viewSatVal.getLeft()) - Math.floor((double)(this.viewTarget.getMeasuredWidth() / 2)) - (double)this.viewContainer.getPaddingLeft());
        layoutParams.topMargin = (int)((double)(f2 + (float)this.viewSatVal.getTop()) - Math.floor((double)(this.viewTarget.getMeasuredHeight() / 2)) - (double)this.viewContainer.getPaddingTop());
        this.viewTarget.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public void show() {
        this.dialog.show();
    }

    public static interface OnAmbilWarnaListener {
        public void onCancel(AmbilWarnaDialog var1);

        public void onOk(AmbilWarnaDialog var1, int var2);
    }

}

