/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapShader
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.RectF
 *  android.graphics.Shader
 *  android.graphics.Shader$TileMode
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.support.annotation.ColorInt
 *  android.support.annotation.ColorRes
 *  android.support.annotation.DrawableRes
 *  android.util.AttributeSet
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  java.lang.Deprecated
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 */
package de.hdodenhof.circleimageview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;
import de.hdodenhof.circleimageview.R;

public class CircleImageView
extends ImageView {
    private static final Bitmap.Config BITMAP_CONFIG;
    private static final int COLORDRAWABLE_DIMENSION = 2;
    private static final int DEFAULT_BORDER_COLOR = -16777216;
    private static final boolean DEFAULT_BORDER_OVERLAY;
    private static final int DEFAULT_BORDER_WIDTH;
    private static final int DEFAULT_FILL_COLOR;
    private static final ImageView.ScaleType SCALE_TYPE;
    private Bitmap mBitmap;
    private int mBitmapHeight;
    private final Paint mBitmapPaint;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBorderColor;
    private boolean mBorderOverlay;
    private final Paint mBorderPaint;
    private float mBorderRadius;
    private final RectF mBorderRect;
    private int mBorderWidth;
    private ColorFilter mColorFilter;
    private boolean mDisableCircularTransformation;
    private float mDrawableRadius;
    private final RectF mDrawableRect;
    private int mFillColor;
    private final Paint mFillPaint;
    private boolean mReady;
    private boolean mSetupPending;
    private final Matrix mShaderMatrix;

    static {
        SCALE_TYPE = ImageView.ScaleType.CENTER_CROP;
        BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    }

    public CircleImageView(Context context) {
        super(context);
        this.mDrawableRect = new RectF();
        this.mBorderRect = new RectF();
        this.mShaderMatrix = new Matrix();
        this.mBitmapPaint = new Paint();
        this.mBorderPaint = new Paint();
        this.mFillPaint = new Paint();
        this.mBorderColor = -16777216;
        this.mBorderWidth = 0;
        this.mFillColor = 0;
        CircleImageView.super.init();
    }

    public CircleImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public CircleImageView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mDrawableRect = new RectF();
        this.mBorderRect = new RectF();
        this.mShaderMatrix = new Matrix();
        this.mBitmapPaint = new Paint();
        this.mBorderPaint = new Paint();
        this.mFillPaint = new Paint();
        this.mBorderColor = -16777216;
        this.mBorderWidth = 0;
        this.mFillColor = 0;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleImageView, n, 0);
        this.mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.CircleImageView_civ_border_width, 0);
        this.mBorderColor = typedArray.getColor(R.styleable.CircleImageView_civ_border_color, -16777216);
        this.mBorderOverlay = typedArray.getBoolean(R.styleable.CircleImageView_civ_border_overlay, false);
        this.mFillColor = typedArray.getColor(R.styleable.CircleImageView_civ_fill_color, 0);
        typedArray.recycle();
        CircleImageView.super.init();
    }

    private void applyColorFilter() {
        if (this.mBitmapPaint != null) {
            this.mBitmapPaint.setColorFilter(this.mColorFilter);
        }
    }

    private RectF calculateBounds() {
        int n = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
        int n2 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
        int n3 = Math.min((int)n, (int)n2);
        float f = (float)this.getPaddingLeft() + (float)(n - n3) / 2.0f;
        float f2 = (float)this.getPaddingTop() + (float)(n2 - n3) / 2.0f;
        return new RectF(f, f2, f + (float)n3, f2 + (float)n3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Bitmap getBitmapFromDrawable(Drawable drawable2) {
        if (drawable2 == null) {
            return null;
        }
        if (drawable2 instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable2).getBitmap();
        }
        try {
            Bitmap bitmap;
            Bitmap bitmap2 = drawable2 instanceof ColorDrawable ? Bitmap.createBitmap((int)2, (int)2, (Bitmap.Config)BITMAP_CONFIG) : (bitmap = Bitmap.createBitmap((int)drawable2.getIntrinsicWidth(), (int)drawable2.getIntrinsicHeight(), (Bitmap.Config)BITMAP_CONFIG));
            Canvas canvas = new Canvas(bitmap2);
            drawable2.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable2.draw(canvas);
            return bitmap2;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        this.mReady = true;
        if (this.mSetupPending) {
            this.setup();
            this.mSetupPending = false;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initializeBitmap() {
        this.mBitmap = this.mDisableCircularTransformation ? null : this.getBitmapFromDrawable(this.getDrawable());
        this.setup();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setup() {
        if (!this.mReady) {
            this.mSetupPending = true;
            return;
        }
        if (this.getWidth() == 0 && this.getHeight() == 0) return;
        {
            if (this.mBitmap == null) {
                this.invalidate();
                return;
            }
        }
        this.mBitmapShader = new BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        this.mBitmapPaint.setAntiAlias(true);
        this.mBitmapPaint.setShader((Shader)this.mBitmapShader);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setColor(this.mBorderColor);
        this.mBorderPaint.setStrokeWidth((float)this.mBorderWidth);
        this.mFillPaint.setStyle(Paint.Style.FILL);
        this.mFillPaint.setAntiAlias(true);
        this.mFillPaint.setColor(this.mFillColor);
        this.mBitmapHeight = this.mBitmap.getHeight();
        this.mBitmapWidth = this.mBitmap.getWidth();
        this.mBorderRect.set(this.calculateBounds());
        this.mBorderRadius = Math.min((float)((this.mBorderRect.height() - (float)this.mBorderWidth) / 2.0f), (float)((this.mBorderRect.width() - (float)this.mBorderWidth) / 2.0f));
        this.mDrawableRect.set(this.mBorderRect);
        if (!this.mBorderOverlay && this.mBorderWidth > 0) {
            this.mDrawableRect.inset((float)this.mBorderWidth - 1.0f, (float)this.mBorderWidth - 1.0f);
        }
        this.mDrawableRadius = Math.min((float)(this.mDrawableRect.height() / 2.0f), (float)(this.mDrawableRect.width() / 2.0f));
        this.applyColorFilter();
        this.updateShaderMatrix();
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateShaderMatrix() {
        float f;
        float f2;
        float f3 = 0.0f;
        this.mShaderMatrix.set(null);
        if ((float)this.mBitmapWidth * this.mDrawableRect.height() > this.mDrawableRect.width() * (float)this.mBitmapHeight) {
            f = this.mDrawableRect.height() / (float)this.mBitmapHeight;
            f2 = 0.5f * (this.mDrawableRect.width() - f * (float)this.mBitmapWidth);
        } else {
            f = this.mDrawableRect.width() / (float)this.mBitmapWidth;
            f3 = 0.5f * (this.mDrawableRect.height() - f * (float)this.mBitmapHeight);
            f2 = 0.0f;
        }
        this.mShaderMatrix.setScale(f, f);
        this.mShaderMatrix.postTranslate((float)((int)(f2 + 0.5f)) + this.mDrawableRect.left, (float)((int)(f3 + 0.5f)) + this.mDrawableRect.top);
        this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public int getBorderWidth() {
        return this.mBorderWidth;
    }

    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    @Deprecated
    public int getFillColor() {
        return this.mFillColor;
    }

    public ImageView.ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    public boolean isBorderOverlay() {
        return this.mBorderOverlay;
    }

    public boolean isDisableCircularTransformation() {
        return this.mDisableCircularTransformation;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        if (this.mDisableCircularTransformation) {
            super.onDraw(canvas);
            return;
        } else {
            if (this.mBitmap == null) return;
            {
                if (this.mFillColor != 0) {
                    canvas.drawCircle(this.mDrawableRect.centerX(), this.mDrawableRect.centerY(), this.mDrawableRadius, this.mFillPaint);
                }
                canvas.drawCircle(this.mDrawableRect.centerX(), this.mDrawableRect.centerY(), this.mDrawableRadius, this.mBitmapPaint);
                if (this.mBorderWidth <= 0) return;
                {
                    canvas.drawCircle(this.mBorderRect.centerX(), this.mBorderRect.centerY(), this.mBorderRadius, this.mBorderPaint);
                    return;
                }
            }
        }
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        CircleImageView.super.setup();
    }

    public void setAdjustViewBounds(boolean bl) {
        if (bl) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    public void setBorderColor(@ColorInt int n) {
        if (n == this.mBorderColor) {
            return;
        }
        this.mBorderColor = n;
        this.mBorderPaint.setColor(this.mBorderColor);
        this.invalidate();
    }

    @Deprecated
    public void setBorderColorResource(@ColorRes int n) {
        this.setBorderColor(this.getContext().getResources().getColor(n));
    }

    public void setBorderOverlay(boolean bl) {
        if (bl == this.mBorderOverlay) {
            return;
        }
        this.mBorderOverlay = bl;
        CircleImageView.super.setup();
    }

    public void setBorderWidth(int n) {
        if (n == this.mBorderWidth) {
            return;
        }
        this.mBorderWidth = n;
        CircleImageView.super.setup();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (colorFilter == this.mColorFilter) {
            return;
        }
        this.mColorFilter = colorFilter;
        CircleImageView.super.applyColorFilter();
        this.invalidate();
    }

    public void setDisableCircularTransformation(boolean bl) {
        if (this.mDisableCircularTransformation == bl) {
            return;
        }
        this.mDisableCircularTransformation = bl;
        CircleImageView.super.initializeBitmap();
    }

    @Deprecated
    public void setFillColor(@ColorInt int n) {
        if (n == this.mFillColor) {
            return;
        }
        this.mFillColor = n;
        this.mFillPaint.setColor(n);
        this.invalidate();
    }

    @Deprecated
    public void setFillColorResource(@ColorRes int n) {
        this.setFillColor(this.getContext().getResources().getColor(n));
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        CircleImageView.super.initializeBitmap();
    }

    public void setImageDrawable(Drawable drawable2) {
        super.setImageDrawable(drawable2);
        CircleImageView.super.initializeBitmap();
    }

    public void setImageResource(@DrawableRes int n) {
        super.setImageResource(n);
        CircleImageView.super.initializeBitmap();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        CircleImageView.super.initializeBitmap();
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        super.setPadding(n, n2, n3, n4);
        CircleImageView.super.setup();
    }

    public void setPaddingRelative(int n, int n2, int n3, int n4) {
        super.setPaddingRelative(n, n2, n3, n4);
        CircleImageView.super.setup();
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format((String)"ScaleType %s not supported.", (Object[])new Object[]{scaleType}));
        }
    }
}

