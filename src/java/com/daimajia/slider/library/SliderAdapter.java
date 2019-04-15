/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.support.v4.view.PagerAdapter
 *  android.view.View
 *  android.view.ViewGroup
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.Iterator
 */
package com.daimajia.slider.library;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import java.util.ArrayList;
import java.util.Iterator;

public class SliderAdapter
extends PagerAdapter
implements BaseSliderView.ImageLoadListener {
    private Context mContext;
    private ArrayList<BaseSliderView> mImageContents;

    public SliderAdapter(Context context) {
        this.mContext = context;
        this.mImageContents = new ArrayList();
    }

    public <T extends BaseSliderView> void addSlider(T t) {
        ((BaseSliderView)t).setOnImageLoadListener((BaseSliderView.ImageLoadListener)this);
        this.mImageContents.add(t);
        this.notifyDataSetChanged();
    }

    public void destroyItem(ViewGroup viewGroup, int n, Object object) {
        viewGroup.removeView((View)object);
    }

    public int getCount() {
        return this.mImageContents.size();
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public BaseSliderView getSliderView(int n) {
        if (n < 0 || n >= this.mImageContents.size()) {
            return null;
        }
        return (BaseSliderView)this.mImageContents.get(n);
    }

    public Object instantiateItem(ViewGroup viewGroup, int n) {
        View view = ((BaseSliderView)this.mImageContents.get(n)).getView();
        viewGroup.addView(view);
        return view;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void onEnd(boolean bl, BaseSliderView baseSliderView) {
        if (!baseSliderView.isErrorDisappear()) return;
        if (bl) {
            return;
        }
        Iterator iterator = this.mImageContents.iterator();
        do {
            if (!iterator.hasNext()) return;
        } while (!((BaseSliderView)iterator.next()).equals((Object)baseSliderView));
        this.removeSlider(baseSliderView);
    }

    @Override
    public void onStart(BaseSliderView baseSliderView) {
    }

    public void removeAllSliders() {
        this.mImageContents.clear();
        this.notifyDataSetChanged();
    }

    public <T extends BaseSliderView> void removeSlider(T t) {
        if (this.mImageContents.contains(t)) {
            this.mImageContents.remove(t);
            this.notifyDataSetChanged();
        }
    }

    public void removeSliderAt(int n) {
        if (this.mImageContents.size() > n) {
            this.mImageContents.remove(n);
            this.notifyDataSetChanged();
        }
    }
}

