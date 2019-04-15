/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Color
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.LayerDrawable
 *  android.support.v4.view.PagerAdapter
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  java.lang.Enum
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Iterator
 */
package com.daimajia.slider.library.Indicators;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.R;
import com.daimajia.slider.library.SliderAdapter;
import com.daimajia.slider.library.Tricks.InfinitePagerAdapter;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import java.util.ArrayList;
import java.util.Iterator;

public class PagerIndicator
extends LinearLayout
implements ViewPagerEx.OnPageChangeListener {
    private DataSetObserver dataChangeObserver;
    private Context mContext;
    private int mDefaultSelectedColor;
    private float mDefaultSelectedHeight;
    private float mDefaultSelectedWidth;
    private int mDefaultUnSelectedColor;
    private float mDefaultUnSelectedHeight;
    private float mDefaultUnSelectedWidth;
    private Shape mIndicatorShape;
    private ArrayList<ImageView> mIndicators;
    private int mItemCount;
    private float mPadding_bottom;
    private float mPadding_left;
    private float mPadding_right;
    private float mPadding_top;
    private ViewPagerEx mPager;
    private ImageView mPreviousSelectedIndicator;
    private int mPreviousSelectedPosition;
    private Drawable mSelectedDrawable;
    private GradientDrawable mSelectedGradientDrawable;
    private LayerDrawable mSelectedLayerDrawable;
    private float mSelectedPadding_Bottom;
    private float mSelectedPadding_Left;
    private float mSelectedPadding_Right;
    private float mSelectedPadding_Top;
    private GradientDrawable mUnSelectedGradientDrawable;
    private LayerDrawable mUnSelectedLayerDrawable;
    private float mUnSelectedPadding_Bottom;
    private float mUnSelectedPadding_Left;
    private float mUnSelectedPadding_Right;
    private float mUnSelectedPadding_Top;
    private Drawable mUnselectedDrawable;
    private int mUserSetSelectedIndicatorResId;
    private int mUserSetUnSelectedIndicatorResId;
    private IndicatorVisibility mVisibility;

    public PagerIndicator(Context context) {
        super(context, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public PagerIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mItemCount = 0;
        this.mIndicatorShape = Shape.Oval;
        this.mVisibility = IndicatorVisibility.Visible;
        this.mIndicators = new ArrayList();
        this.dataChangeObserver = new DataSetObserver((PagerIndicator)this){
            final /* synthetic */ PagerIndicator this$0;
            {
                this.this$0 = pagerIndicator;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onChanged() {
                PagerAdapter pagerAdapter = PagerIndicator.access$000(this.this$0).getAdapter();
                int n = pagerAdapter instanceof InfinitePagerAdapter ? ((InfinitePagerAdapter)pagerAdapter).getRealCount() : pagerAdapter.getCount();
                if (n > PagerIndicator.access$100(this.this$0)) {
                    for (int i = 0; i < n - PagerIndicator.access$100(this.this$0); ++i) {
                        ImageView imageView = new ImageView(PagerIndicator.access$200(this.this$0));
                        imageView.setImageDrawable(PagerIndicator.access$300(this.this$0));
                        imageView.setPadding((int)PagerIndicator.access$400(this.this$0), (int)PagerIndicator.access$500(this.this$0), (int)PagerIndicator.access$600(this.this$0), (int)PagerIndicator.access$700(this.this$0));
                        this.this$0.addView((View)imageView);
                        PagerIndicator.access$800(this.this$0).add((Object)imageView);
                    }
                } else if (n < PagerIndicator.access$100(this.this$0)) {
                    for (int i = 0; i < PagerIndicator.access$100(this.this$0) - n; ++i) {
                        this.this$0.removeView((View)PagerIndicator.access$800(this.this$0).get(0));
                        PagerIndicator.access$800(this.this$0).remove(0);
                    }
                }
                PagerIndicator.access$102(this.this$0, n);
                PagerIndicator.access$000(this.this$0).setCurrentItem(20 * PagerIndicator.access$100(this.this$0) + PagerIndicator.access$000(this.this$0).getCurrentItem());
            }

            public void onInvalidated() {
                super.onInvalidated();
                this.this$0.redraw();
            }
        };
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.PagerIndicator, 0, 0);
        int n = typedArray.getInt(R.styleable.PagerIndicator_visibility, IndicatorVisibility.Visible.ordinal());
        IndicatorVisibility[] arrindicatorVisibility = IndicatorVisibility.values();
        int n2 = arrindicatorVisibility.length;
        int n3 = 0;
        do {
            if (n3 >= n2) break;
            IndicatorVisibility indicatorVisibility = arrindicatorVisibility[n3];
            if (indicatorVisibility.ordinal() == n) {
                this.mVisibility = indicatorVisibility;
                break;
            }
            ++n3;
        } while (true);
        int n4 = typedArray.getInt(R.styleable.PagerIndicator_shape, Shape.Oval.ordinal());
        Shape[] arrshape = Shape.values();
        int n5 = arrshape.length;
        int n6 = 0;
        do {
            block7 : {
                block6 : {
                    if (n6 >= n5) break block6;
                    Shape shape = arrshape[n6];
                    if (shape.ordinal() != n4) break block7;
                    this.mIndicatorShape = shape;
                }
                this.mUserSetSelectedIndicatorResId = typedArray.getResourceId(R.styleable.PagerIndicator_selected_drawable, 0);
                this.mUserSetUnSelectedIndicatorResId = typedArray.getResourceId(R.styleable.PagerIndicator_unselected_drawable, 0);
                this.mDefaultSelectedColor = typedArray.getColor(R.styleable.PagerIndicator_selected_color, Color.rgb((int)255, (int)255, (int)255));
                this.mDefaultUnSelectedColor = typedArray.getColor(R.styleable.PagerIndicator_unselected_color, Color.argb((int)33, (int)255, (int)255, (int)255));
                this.mDefaultSelectedWidth = typedArray.getDimension(R.styleable.PagerIndicator_selected_width, (float)((int)PagerIndicator.super.pxFromDp(6.0f)));
                this.mDefaultSelectedHeight = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_selected_height, (int)PagerIndicator.super.pxFromDp(6.0f));
                this.mDefaultUnSelectedWidth = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_unselected_width, (int)PagerIndicator.super.pxFromDp(6.0f));
                this.mDefaultUnSelectedHeight = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_unselected_height, (int)PagerIndicator.super.pxFromDp(6.0f));
                this.mSelectedGradientDrawable = new GradientDrawable();
                this.mUnSelectedGradientDrawable = new GradientDrawable();
                this.mPadding_left = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_padding_left, (int)PagerIndicator.super.pxFromDp(3.0f));
                this.mPadding_right = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_padding_right, (int)PagerIndicator.super.pxFromDp(3.0f));
                this.mPadding_top = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_padding_top, (int)PagerIndicator.super.pxFromDp(0.0f));
                this.mPadding_bottom = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_padding_bottom, (int)PagerIndicator.super.pxFromDp(0.0f));
                this.mSelectedPadding_Left = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_selected_padding_left, (int)this.mPadding_left);
                this.mSelectedPadding_Right = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_selected_padding_right, (int)this.mPadding_right);
                this.mSelectedPadding_Top = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_selected_padding_top, (int)this.mPadding_top);
                this.mSelectedPadding_Bottom = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_selected_padding_bottom, (int)this.mPadding_bottom);
                this.mUnSelectedPadding_Left = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_unselected_padding_left, (int)this.mPadding_left);
                this.mUnSelectedPadding_Right = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_unselected_padding_right, (int)this.mPadding_right);
                this.mUnSelectedPadding_Top = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_unselected_padding_top, (int)this.mPadding_top);
                this.mUnSelectedPadding_Bottom = typedArray.getDimensionPixelSize(R.styleable.PagerIndicator_unselected_padding_bottom, (int)this.mPadding_bottom);
                Drawable[] arrdrawable = new Drawable[]{this.mSelectedGradientDrawable};
                this.mSelectedLayerDrawable = new LayerDrawable(arrdrawable);
                Drawable[] arrdrawable2 = new Drawable[]{this.mUnSelectedGradientDrawable};
                this.mUnSelectedLayerDrawable = new LayerDrawable(arrdrawable2);
                this.setIndicatorStyleResource(this.mUserSetSelectedIndicatorResId, this.mUserSetUnSelectedIndicatorResId);
                this.setDefaultIndicatorShape(this.mIndicatorShape);
                this.setDefaultSelectedIndicatorSize(this.mDefaultSelectedWidth, this.mDefaultSelectedHeight, Unit.Px);
                this.setDefaultUnselectedIndicatorSize(this.mDefaultUnSelectedWidth, this.mDefaultUnSelectedHeight, Unit.Px);
                this.setDefaultIndicatorColor(this.mDefaultSelectedColor, this.mDefaultUnSelectedColor);
                this.setIndicatorVisibility(this.mVisibility);
                typedArray.recycle();
                return;
            }
            ++n6;
        } while (true);
    }

    static /* synthetic */ ViewPagerEx access$000(PagerIndicator pagerIndicator) {
        return pagerIndicator.mPager;
    }

    static /* synthetic */ int access$100(PagerIndicator pagerIndicator) {
        return pagerIndicator.mItemCount;
    }

    static /* synthetic */ int access$102(PagerIndicator pagerIndicator, int n) {
        pagerIndicator.mItemCount = n;
        return n;
    }

    static /* synthetic */ Context access$200(PagerIndicator pagerIndicator) {
        return pagerIndicator.mContext;
    }

    static /* synthetic */ Drawable access$300(PagerIndicator pagerIndicator) {
        return pagerIndicator.mUnselectedDrawable;
    }

    static /* synthetic */ float access$400(PagerIndicator pagerIndicator) {
        return pagerIndicator.mUnSelectedPadding_Left;
    }

    static /* synthetic */ float access$500(PagerIndicator pagerIndicator) {
        return pagerIndicator.mUnSelectedPadding_Top;
    }

    static /* synthetic */ float access$600(PagerIndicator pagerIndicator) {
        return pagerIndicator.mUnSelectedPadding_Right;
    }

    static /* synthetic */ float access$700(PagerIndicator pagerIndicator) {
        return pagerIndicator.mUnSelectedPadding_Bottom;
    }

    static /* synthetic */ ArrayList access$800(PagerIndicator pagerIndicator) {
        return pagerIndicator.mIndicators;
    }

    private float dpFromPx(float f) {
        return f / this.getContext().getResources().getDisplayMetrics().density;
    }

    private int getShouldDrawCount() {
        if (this.mPager.getAdapter() instanceof InfinitePagerAdapter) {
            return ((InfinitePagerAdapter)this.mPager.getAdapter()).getRealCount();
        }
        return this.mPager.getAdapter().getCount();
    }

    private float pxFromDp(float f) {
        return f * this.getContext().getResources().getDisplayMetrics().density;
    }

    private void resetDrawable() {
        for (View view : this.mIndicators) {
            if (this.mPreviousSelectedIndicator != null && this.mPreviousSelectedIndicator.equals((Object)view)) {
                ((ImageView)view).setImageDrawable(this.mSelectedDrawable);
                continue;
            }
            ((ImageView)view).setImageDrawable(this.mUnselectedDrawable);
        }
    }

    private void setItemAsSelected(int n) {
        ImageView imageView;
        if (this.mPreviousSelectedIndicator != null) {
            this.mPreviousSelectedIndicator.setImageDrawable(this.mUnselectedDrawable);
            this.mPreviousSelectedIndicator.setPadding((int)this.mUnSelectedPadding_Left, (int)this.mUnSelectedPadding_Top, (int)this.mUnSelectedPadding_Right, (int)this.mUnSelectedPadding_Bottom);
        }
        if ((imageView = (ImageView)this.getChildAt(n + 1)) != null) {
            imageView.setImageDrawable(this.mSelectedDrawable);
            imageView.setPadding((int)this.mSelectedPadding_Left, (int)this.mSelectedPadding_Top, (int)this.mSelectedPadding_Right, (int)this.mSelectedPadding_Bottom);
            this.mPreviousSelectedIndicator = imageView;
        }
        this.mPreviousSelectedPosition = n;
    }

    public void destroySelf() {
        if (this.mPager == null || this.mPager.getAdapter() == null) {
            return;
        }
        SliderAdapter sliderAdapter = ((InfinitePagerAdapter)this.mPager.getAdapter()).getRealAdapter();
        if (sliderAdapter != null) {
            sliderAdapter.unregisterDataSetObserver(this.dataChangeObserver);
        }
        this.removeAllViews();
    }

    public IndicatorVisibility getIndicatorVisibility() {
        return this.mVisibility;
    }

    public int getSelectedIndicatorResId() {
        return this.mUserSetSelectedIndicatorResId;
    }

    public int getUnSelectedIndicatorResId() {
        return this.mUserSetUnSelectedIndicatorResId;
    }

    @Override
    public void onPageScrollStateChanged(int n) {
    }

    @Override
    public void onPageScrolled(int n, float f, int n2) {
    }

    @Override
    public void onPageSelected(int n) {
        if (this.mItemCount == 0) {
            return;
        }
        PagerIndicator.super.setItemAsSelected(n - 1);
    }

    public void redraw() {
        this.mItemCount = this.getShouldDrawCount();
        this.mPreviousSelectedIndicator = null;
        Iterator iterator = this.mIndicators.iterator();
        while (iterator.hasNext()) {
            this.removeView((View)iterator.next());
        }
        for (int i = 0; i < this.mItemCount; ++i) {
            ImageView imageView = new ImageView(this.mContext);
            imageView.setImageDrawable(this.mUnselectedDrawable);
            imageView.setPadding((int)this.mUnSelectedPadding_Left, (int)this.mUnSelectedPadding_Top, (int)this.mUnSelectedPadding_Right, (int)this.mUnSelectedPadding_Bottom);
            this.addView((View)imageView);
            this.mIndicators.add((Object)imageView);
        }
        this.setItemAsSelected(this.mPreviousSelectedPosition);
    }

    public void setDefaultIndicatorColor(int n, int n2) {
        if (this.mUserSetSelectedIndicatorResId == 0) {
            this.mSelectedGradientDrawable.setColor(n);
        }
        if (this.mUserSetUnSelectedIndicatorResId == 0) {
            this.mUnSelectedGradientDrawable.setColor(n2);
        }
        PagerIndicator.super.resetDrawable();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDefaultIndicatorShape(Shape shape) {
        if (this.mUserSetSelectedIndicatorResId == 0) {
            if (shape == Shape.Oval) {
                this.mSelectedGradientDrawable.setShape(1);
            } else {
                this.mSelectedGradientDrawable.setShape(0);
            }
        }
        if (this.mUserSetUnSelectedIndicatorResId == 0) {
            if (shape == Shape.Oval) {
                this.mUnSelectedGradientDrawable.setShape(1);
            } else {
                this.mUnSelectedGradientDrawable.setShape(0);
            }
        }
        PagerIndicator.super.resetDrawable();
    }

    public void setDefaultIndicatorSize(float f, float f2, Unit unit) {
        this.setDefaultSelectedIndicatorSize(f, f2, unit);
        this.setDefaultUnselectedIndicatorSize(f, f2, unit);
    }

    public void setDefaultSelectedIndicatorSize(float f, float f2, Unit unit) {
        if (this.mUserSetSelectedIndicatorResId == 0) {
            float f3 = f;
            float f4 = f2;
            if (unit == Unit.DP) {
                f3 = PagerIndicator.super.pxFromDp(f);
                f4 = PagerIndicator.super.pxFromDp(f2);
            }
            this.mSelectedGradientDrawable.setSize((int)f3, (int)f4);
            PagerIndicator.super.resetDrawable();
        }
    }

    public void setDefaultUnselectedIndicatorSize(float f, float f2, Unit unit) {
        if (this.mUserSetUnSelectedIndicatorResId == 0) {
            float f3 = f;
            float f4 = f2;
            if (unit == Unit.DP) {
                f3 = PagerIndicator.super.pxFromDp(f);
                f4 = PagerIndicator.super.pxFromDp(f2);
            }
            this.mUnSelectedGradientDrawable.setSize((int)f3, (int)f4);
            PagerIndicator.super.resetDrawable();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setIndicatorStyleResource(int n, int n2) {
        this.mUserSetSelectedIndicatorResId = n;
        this.mUserSetUnSelectedIndicatorResId = n2;
        this.mSelectedDrawable = n == 0 ? this.mSelectedLayerDrawable : this.mContext.getResources().getDrawable(this.mUserSetSelectedIndicatorResId);
        this.mUnselectedDrawable = n2 == 0 ? this.mUnSelectedLayerDrawable : this.mContext.getResources().getDrawable(this.mUserSetUnSelectedIndicatorResId);
        PagerIndicator.super.resetDrawable();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setIndicatorVisibility(IndicatorVisibility indicatorVisibility) {
        if (indicatorVisibility == IndicatorVisibility.Visible) {
            this.setVisibility(0);
        } else {
            this.setVisibility(4);
        }
        PagerIndicator.super.resetDrawable();
    }

    public void setViewPager(ViewPagerEx viewPagerEx) {
        if (viewPagerEx.getAdapter() == null) {
            throw new IllegalStateException("Viewpager does not have adapter instance");
        }
        this.mPager = viewPagerEx;
        this.mPager.addOnPageChangeListener((ViewPagerEx.OnPageChangeListener)this);
        ((InfinitePagerAdapter)this.mPager.getAdapter()).getRealAdapter().registerDataSetObserver(this.dataChangeObserver);
    }

}

