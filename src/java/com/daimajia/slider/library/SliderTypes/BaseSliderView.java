/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageView
 *  com.daimajia.slider.library.SliderTypes.BaseSliderView$2
 *  com.squareup.picasso.Callback
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.io.File
 *  java.lang.Enum
 *  java.lang.IllegalStateException
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 */
package com.daimajia.slider.library.SliderTypes;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.io.File;

public abstract class BaseSliderView {
    private Bundle mBundle;
    protected Context mContext;
    private String mDescription;
    private int mEmptyPlaceHolderRes;
    private boolean mErrorDisappear;
    private int mErrorPlaceHolderRes;
    private File mFile;
    private ImageLoadListener mLoadListener;
    protected OnSliderClickListener mOnSliderClickListener;
    private Picasso mPicasso;
    private int mRes;
    private ScaleType mScaleType = ScaleType.Fit;
    private String mUrl;

    protected BaseSliderView(Context context) {
        this.mContext = context;
    }

    static /* synthetic */ ImageLoadListener access$000(BaseSliderView baseSliderView) {
        return baseSliderView.mLoadListener;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void bindEventAndShow(View view, ImageView imageView) {
        block12 : {
            RequestCreator requestCreator;
            block14 : {
                Picasso picasso;
                block15 : {
                    block13 : {
                        view.setOnClickListener(new View.OnClickListener((BaseSliderView)this){
                            final /* synthetic */ BaseSliderView val$me;
                            {
                                this.val$me = baseSliderView2;
                            }

                            public void onClick(View view) {
                                if (BaseSliderView.this.mOnSliderClickListener != null) {
                                    BaseSliderView.this.mOnSliderClickListener.onSliderClick(this.val$me);
                                }
                            }
                        });
                        if (imageView == null) break block12;
                        if (this.mLoadListener != null) {
                            this.mLoadListener.onStart((BaseSliderView)this);
                        }
                        picasso = this.mPicasso != null ? this.mPicasso : Picasso.with((Context)this.mContext);
                        if (this.mUrl == null) break block13;
                        requestCreator = picasso.load(this.mUrl);
                        break block14;
                    }
                    if (this.mFile == null) break block15;
                    requestCreator = picasso.load(this.mFile);
                    break block14;
                }
                if (this.mRes == 0) break block12;
                requestCreator = picasso.load(this.mRes);
            }
            if (requestCreator != null) {
                if (this.getEmpty() != 0) {
                    requestCreator.placeholder(this.getEmpty());
                }
                if (this.getError() != 0) {
                    requestCreator.error(this.getError());
                }
                switch (3.$SwitchMap$com$daimajia$slider$library$SliderTypes$BaseSliderView$ScaleType[this.mScaleType.ordinal()]) {
                    case 1: {
                        requestCreator.fit();
                        break;
                    }
                    case 2: {
                        requestCreator.fit().centerCrop();
                        break;
                    }
                    case 3: {
                        requestCreator.fit().centerInside();
                        break;
                    }
                }
                requestCreator.into(imageView, (Callback)new 2((BaseSliderView)this, view, (BaseSliderView)this));
                return;
            }
        }
    }

    public BaseSliderView bundle(Bundle bundle) {
        this.mBundle = bundle;
        return this;
    }

    public BaseSliderView description(String string2) {
        this.mDescription = string2;
        return this;
    }

    public BaseSliderView empty(int n) {
        this.mEmptyPlaceHolderRes = n;
        return this;
    }

    public BaseSliderView error(int n) {
        this.mErrorPlaceHolderRes = n;
        return this;
    }

    public BaseSliderView errorDisappear(boolean bl) {
        this.mErrorDisappear = bl;
        return this;
    }

    public Bundle getBundle() {
        return this.mBundle;
    }

    public Context getContext() {
        return this.mContext;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public int getEmpty() {
        return this.mEmptyPlaceHolderRes;
    }

    public int getError() {
        return this.mErrorPlaceHolderRes;
    }

    public Picasso getPicasso() {
        return this.mPicasso;
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public abstract View getView();

    public BaseSliderView image(int n) {
        if (this.mUrl != null || this.mFile != null) {
            throw new IllegalStateException("Call multi image function,you only have permission to call it once");
        }
        this.mRes = n;
        return this;
    }

    public BaseSliderView image(File file) {
        if (this.mUrl != null || this.mRes != 0) {
            throw new IllegalStateException("Call multi image function,you only have permission to call it once");
        }
        this.mFile = file;
        return this;
    }

    public BaseSliderView image(String string2) {
        if (this.mFile != null || this.mRes != 0) {
            throw new IllegalStateException("Call multi image function,you only have permission to call it once");
        }
        this.mUrl = string2;
        return this;
    }

    public boolean isErrorDisappear() {
        return this.mErrorDisappear;
    }

    public void setOnImageLoadListener(ImageLoadListener imageLoadListener) {
        this.mLoadListener = imageLoadListener;
    }

    public BaseSliderView setOnSliderClickListener(OnSliderClickListener onSliderClickListener) {
        this.mOnSliderClickListener = onSliderClickListener;
        return this;
    }

    public void setPicasso(Picasso picasso) {
        this.mPicasso = picasso;
    }

    public BaseSliderView setScaleType(ScaleType scaleType) {
        this.mScaleType = scaleType;
        return this;
    }

    public static interface ImageLoadListener {
        public void onEnd(boolean var1, BaseSliderView var2);

        public void onStart(BaseSliderView var1);
    }

    public static interface OnSliderClickListener {
        public void onSliderClick(BaseSliderView var1);
    }

    public static final class ScaleType
    extends Enum<ScaleType> {
        private static final /* synthetic */ ScaleType[] $VALUES;
        public static final /* enum */ ScaleType CenterCrop = new ScaleType();
        public static final /* enum */ ScaleType CenterInside = new ScaleType();
        public static final /* enum */ ScaleType Fit = new ScaleType();
        public static final /* enum */ ScaleType FitCenterCrop = new ScaleType();

        static {
            ScaleType[] arrscaleType = new ScaleType[]{CenterCrop, CenterInside, Fit, FitCenterCrop};
            $VALUES = arrscaleType;
        }

        public static ScaleType valueOf(String string2) {
            return (ScaleType)Enum.valueOf(ScaleType.class, (String)string2);
        }

        public static ScaleType[] values() {
            return (ScaleType[])$VALUES.clone();
        }
    }

}

