/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.SystemClock
 *  android.support.v4.os.ParcelableCompat
 *  android.support.v4.os.ParcelableCompatCreatorCallbacks
 *  android.support.v4.view.AccessibilityDelegateCompat
 *  android.support.v4.view.KeyEventCompat
 *  android.support.v4.view.MotionEventCompat
 *  android.support.v4.view.PagerAdapter
 *  android.support.v4.view.VelocityTrackerCompat
 *  android.support.v4.view.ViewCompat
 *  android.support.v4.view.ViewConfigurationCompat
 *  android.support.v4.widget.EdgeEffectCompat
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.SoundEffectConstants
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.animation.Interpolator
 *  android.widget.Scroller
 *  com.daimajia.slider.library.Tricks.InfinitePagerAdapter
 *  com.daimajia.slider.library.Tricks.ViewPagerEx$MyAccessibilityDelegate
 *  com.daimajia.slider.library.Tricks.ViewPagerEx$SavedState$1
 *  java.lang.Boolean
 *  java.lang.Class
 *  java.lang.ClassLoader
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.NoSuchMethodException
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Throwable
 *  java.lang.reflect.Method
 *  java.util.ArrayList
 *  java.util.Collections
 *  java.util.Comparator
 */
package com.daimajia.slider.library.Tricks;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.daimajia.slider.library.Tricks.InfinitePagerAdapter;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * Exception performing whole class analysis.
 */
public class ViewPagerEx
extends ViewGroup {
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<ItemInfo> COMPARATOR;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    private static final int[] LAYOUT_ATTRS;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPagerEx";
    private static final boolean USE_CACHE;
    private static final Interpolator sInterpolator;
    private static final ViewPositionComparator sPositionComparator;
    private int mActivePointerId;
    private PagerAdapter mAdapter;
    private OnAdapterChangeListener mAdapterChangeListener;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    private int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable;
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout;
    private float mFirstOffset;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mIgnoreGutter;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset;
    private EdgeEffectCompat mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit;
    private ArrayList<OnPageChangeListener> mOnPageChangeListeners;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState;
    private ClassLoader mRestoredClassLoader;
    private int mRestoredCurItem;
    private EdgeEffectCompat mRightEdge;
    private int mScrollState;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private Method mSetChildrenDrawingOrderEnabled;
    private final ItemInfo mTempItem;
    private final Rect mTempRect;
    private int mTopPageBounds;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    static {
        LAYOUT_ATTRS = new int[]{16842931};
        COMPARATOR = new Comparator<ItemInfo>(){

            public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
                return itemInfo.position - itemInfo2.position;
            }
        };
        sInterpolator = new Interpolator(){

            public float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return 1.0f + f2 * (f2 * (f2 * (f2 * f2)));
            }
        };
        sPositionComparator = new ViewPositionComparator();
    }

    public ViewPagerEx(Context context) {
        super(context);
        this.mItems = new ArrayList();
        this.mTempItem = new ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mNeedCalculatePageOffsets = false;
        this.mOnPageChangeListeners = new ArrayList();
        this.mEndScrollRunnable = new Runnable(){

            public void run() {
                ViewPagerEx.this.setScrollState(0);
                ViewPagerEx.this.populate();
            }
        };
        this.mScrollState = 0;
        this.initViewPager();
    }

    public ViewPagerEx(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mItems = new ArrayList();
        this.mTempItem = new ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mNeedCalculatePageOffsets = false;
        this.mOnPageChangeListeners = new ArrayList();
        this.mEndScrollRunnable = new /* invalid duplicate definition of identical inner class */;
        this.mScrollState = 0;
        this.initViewPager();
    }

    static /* synthetic */ PagerAdapter access$200(ViewPagerEx viewPagerEx) {
        return viewPagerEx.mAdapter;
    }

    static /* synthetic */ int access$300(ViewPagerEx viewPagerEx) {
        return viewPagerEx.mCurItem;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculatePageOffsets(ItemInfo itemInfo, int n, ItemInfo itemInfo2) {
        int n2;
        float f;
        block12 : {
            int n3;
            block15 : {
                int n4;
                float f2;
                ItemInfo itemInfo3;
                block14 : {
                    int n5;
                    block13 : {
                        n2 = this.mAdapter.getCount();
                        int n6 = ViewPagerEx.super.getClientWidth();
                        f = n6 > 0 ? (float)this.mPageMargin / (float)n6 : 0.0f;
                        if (itemInfo2 == null) break block12;
                        n5 = itemInfo2.position;
                        if (n5 >= itemInfo.position) break block13;
                        n4 = 0;
                        f2 = f + (itemInfo2.offset + itemInfo2.widthFactor);
                        break block14;
                    }
                    if (n5 <= itemInfo.position) break block12;
                    n3 = -1 + this.mItems.size();
                    float f3 = itemInfo2.offset;
                    break block15;
                }
                for (int i = n5 + 1; i <= itemInfo.position && n4 < this.mItems.size(); f2 += f + itemInfo3.widthFactor, ++i) {
                    itemInfo3 = (ItemInfo)this.mItems.get(n4);
                    while (i > itemInfo3.position && n4 < -1 + this.mItems.size()) {
                        itemInfo3 = (ItemInfo)this.mItems.get(++n4);
                    }
                    while (i < itemInfo3.position) {
                        f2 += f + this.mAdapter.getPageWidth(i);
                        ++i;
                    }
                    itemInfo3.offset = f2;
                }
                break block12;
            }
            for (int i = n5 - 1; i >= itemInfo.position && n3 >= 0; --i) {
                ItemInfo itemInfo4 = (ItemInfo)this.mItems.get(n3);
                while (i < itemInfo4.position && n3 > 0) {
                    itemInfo4 = (ItemInfo)this.mItems.get(--n3);
                }
                while (i > itemInfo4.position) {
                    f3 -= f + this.mAdapter.getPageWidth(i);
                    --i;
                }
                itemInfo4.offset = f3 -= f + itemInfo4.widthFactor;
            }
        }
        int n7 = this.mItems.size();
        float f4 = itemInfo.offset;
        int n8 = -1 + itemInfo.position;
        float f5 = itemInfo.position == 0 ? itemInfo.offset : -3.4028235E38f;
        this.mFirstOffset = f5;
        float f6 = itemInfo.position == n2 - 1 ? itemInfo.offset + itemInfo.widthFactor - 1.0f : Float.MAX_VALUE;
        this.mLastOffset = f6;
        for (int i = n - 1; i >= 0; --i, --n8) {
            ItemInfo itemInfo5 = (ItemInfo)this.mItems.get(i);
            while (n8 > itemInfo5.position) {
                PagerAdapter pagerAdapter = this.mAdapter;
                int n9 = n8 - 1;
                f4 -= f + pagerAdapter.getPageWidth(n8);
                n8 = n9;
            }
            itemInfo5.offset = f4 -= f + itemInfo5.widthFactor;
            if (itemInfo5.position != 0) continue;
            this.mFirstOffset = f4;
        }
        float f7 = f + (itemInfo.offset + itemInfo.widthFactor);
        int n10 = 1 + itemInfo.position;
        int n11 = n + 1;
        do {
            if (n11 >= n7) {
                this.mNeedCalculatePageOffsets = false;
                return;
            }
            ItemInfo itemInfo6 = (ItemInfo)this.mItems.get(n11);
            while (n10 < itemInfo6.position) {
                PagerAdapter pagerAdapter = this.mAdapter;
                int n12 = n10 + 1;
                f7 += f + pagerAdapter.getPageWidth(n10);
                n10 = n12;
            }
            if (itemInfo6.position == n2 - 1) {
                this.mLastOffset = f7 + itemInfo6.widthFactor - 1.0f;
            }
            itemInfo6.offset = f7;
            f7 += f + itemInfo6.widthFactor;
            ++n11;
            ++n10;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void completeScroll(boolean bl) {
        boolean bl2 = this.mScrollState == 2;
        if (bl2) {
            ViewPagerEx.super.setScrollingCacheEnabled(false);
            this.mScroller.abortAnimation();
            int n = this.getScrollX();
            int n2 = this.getScrollY();
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            if (n != n3 || n2 != n4) {
                this.scrollTo(n3, n4);
            }
        }
        this.mPopulatePending = false;
        for (int i = 0; i < this.mItems.size(); ++i) {
            ItemInfo itemInfo = (ItemInfo)this.mItems.get(i);
            if (!itemInfo.scrolling) continue;
            bl2 = true;
            itemInfo.scrolling = false;
        }
        if (bl2) {
            if (!bl) {
                this.mEndScrollRunnable.run();
                return;
            }
            ViewCompat.postOnAnimation((View)this, (Runnable)this.mEndScrollRunnable);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int determineTargetPage(int n, float f, int n2, int n3) {
        int n4;
        if (Math.abs((int)n3) > this.mFlingDistance && Math.abs((int)n2) > this.mMinimumVelocity) {
            n4 = n2 > 0 ? n : n + 1;
        } else {
            float f2 = n >= this.mCurItem ? 0.4f : 0.6f;
            n4 = (int)(f2 + (f + (float)n));
        }
        if (this.mItems.size() <= 0) return n4;
        ItemInfo itemInfo = (ItemInfo)this.mItems.get(0);
        ItemInfo itemInfo2 = (ItemInfo)this.mItems.get(-1 + this.mItems.size());
        return Math.max((int)itemInfo.position, (int)Math.min((int)n4, (int)itemInfo2.position));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void enableLayers(boolean bl) {
        int n = this.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            int n3 = bl ? 2 : 0;
            ViewCompat.setLayerType((View)this.getChildAt(n2), (int)n3, null);
            ++n2;
        }
        return;
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        if (rect == null) {
            rect = new Rect();
        }
        if (view == null) {
            rect.set(0, 0, 0, 0);
            return rect;
        } else {
            rect.left = view.getLeft();
            rect.right = view.getRight();
            rect.top = view.getTop();
            rect.bottom = view.getBottom();
            ViewParent viewParent = view.getParent();
            while (viewParent instanceof ViewGroup && viewParent != this) {
                ViewGroup viewGroup = (ViewGroup)viewParent;
                rect.left += viewGroup.getLeft();
                rect.right += viewGroup.getRight();
                rect.top += viewGroup.getTop();
                rect.bottom += viewGroup.getBottom();
                viewParent = viewGroup.getParent();
            }
        }
        return rect;
    }

    private int getClientWidth() {
        return this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
    }

    /*
     * Enabled aggressive block sorting
     */
    private ItemInfo infoForCurrentScrollPosition() {
        int n = this.getClientWidth();
        float f = n > 0 ? (float)this.getScrollX() / (float)n : 0.0f;
        float f2 = 0.0f;
        if (n > 0) {
            f2 = (float)this.mPageMargin / (float)n;
        }
        int n2 = -1;
        float f3 = 0.0f;
        float f4 = 0.0f;
        boolean bl = true;
        ItemInfo itemInfo = null;
        int n3 = 0;
        while (n3 < this.mItems.size()) {
            ItemInfo itemInfo2 = (ItemInfo)this.mItems.get(n3);
            if (!bl && itemInfo2.position != n2 + 1) {
                itemInfo2 = this.mTempItem;
                itemInfo2.offset = f2 + (f3 + f4);
                itemInfo2.position = n2 + 1;
                itemInfo2.widthFactor = this.mAdapter.getPageWidth(itemInfo2.position);
                --n3;
            }
            float f5 = itemInfo2.offset;
            float f6 = f2 + (f5 + itemInfo2.widthFactor);
            if (!bl) {
                if (!(f >= f5)) return itemInfo;
            }
            if (f < f6) return itemInfo2;
            if (n3 == -1 + this.mItems.size()) {
                return itemInfo2;
            }
            n2 = itemInfo2.position;
            f3 = f5;
            f4 = itemInfo2.widthFactor;
            itemInfo = itemInfo2;
            ++n3;
            bl = false;
        }
        return itemInfo;
    }

    private boolean isGutterDrag(float f, float f2) {
        return f < (float)this.mGutterSize && f2 > 0.0f || f > (float)(this.getWidth() - this.mGutterSize) && f2 < 0.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n = MotionEventCompat.getActionIndex((MotionEvent)motionEvent);
        if (MotionEventCompat.getPointerId((MotionEvent)motionEvent, (int)n) == this.mActivePointerId) {
            int n2 = n == 0 ? 1 : 0;
            this.mLastMotionX = MotionEventCompat.getX((MotionEvent)motionEvent, (int)n2);
            this.mActivePointerId = MotionEventCompat.getPointerId((MotionEvent)motionEvent, (int)n2);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private boolean pageScrolled(int n) {
        boolean bl;
        if (this.mItems.size() == 0) {
            this.mCalledSuper = false;
            this.onPageScrolled(0, 0.0f, 0);
            boolean bl2 = this.mCalledSuper;
            bl = false;
            if (!bl2) {
                throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            }
        } else {
            ItemInfo itemInfo = ViewPagerEx.super.infoForCurrentScrollPosition();
            int n2 = ViewPagerEx.super.getClientWidth();
            int n3 = n2 + this.mPageMargin;
            float f = (float)this.mPageMargin / (float)n2;
            int n4 = itemInfo.position;
            float f2 = ((float)n / (float)n2 - itemInfo.offset) / (f + itemInfo.widthFactor);
            int n5 = (int)(f2 * (float)n3);
            this.mCalledSuper = false;
            this.onPageScrolled(n4, f2, n5);
            if (!this.mCalledSuper) {
                throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            }
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean performDrag(float f) {
        boolean bl;
        float f2 = this.mLastMotionX - f;
        this.mLastMotionX = f;
        float f3 = f2 + (float)this.getScrollX();
        int n = ViewPagerEx.super.getClientWidth();
        float f4 = (float)n * this.mFirstOffset;
        float f5 = (float)n * this.mLastOffset;
        boolean bl2 = true;
        boolean bl3 = true;
        ItemInfo itemInfo = (ItemInfo)this.mItems.get(0);
        ItemInfo itemInfo2 = (ItemInfo)this.mItems.get(-1 + this.mItems.size());
        if (itemInfo.position != 0) {
            bl2 = false;
            f4 = itemInfo.offset * (float)n;
        }
        if (itemInfo2.position != -1 + this.mAdapter.getCount()) {
            bl3 = false;
            f5 = itemInfo2.offset * (float)n;
        }
        if (f3 < f4) {
            bl = false;
            if (bl2) {
                float f6 = f4 - f3;
                bl = this.mLeftEdge.onPull(Math.abs((float)f6) / (float)n);
            }
            f3 = f4;
        } else {
            float f7 = f3 FCMPL f5;
            bl = false;
            if (f7 > 0) {
                bl = false;
                if (bl3) {
                    float f8 = f3 - f5;
                    bl = this.mRightEdge.onPull(Math.abs((float)f8) / (float)n);
                }
                f3 = f5;
            }
        }
        this.mLastMotionX += f3 - (float)((int)f3);
        this.scrollTo((int)f3, this.getScrollY());
        ViewPagerEx.super.pageScrolled((int)f3);
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void recomputeScrollPosition(int n, int n2, int n3, int n4) {
        if (n2 > 0 && !this.mItems.isEmpty()) {
            int n5 = n3 + (n - this.getPaddingLeft() - this.getPaddingRight());
            int n6 = n4 + (n2 - this.getPaddingLeft() - this.getPaddingRight());
            int n7 = (int)((float)this.getScrollX() / (float)n6 * (float)n5);
            this.scrollTo(n7, this.getScrollY());
            if (this.mScroller.isFinished()) return;
            {
                int n8 = this.mScroller.getDuration() - this.mScroller.timePassed();
                ItemInfo itemInfo = this.infoForPosition(this.mCurItem);
                this.mScroller.startScroll(n7, 0, (int)(itemInfo.offset * (float)n), 0, n8);
                return;
            }
        } else {
            ItemInfo itemInfo = this.infoForPosition(this.mCurItem);
            float f = itemInfo != null ? Math.min((float)itemInfo.offset, (float)this.mLastOffset) : 0.0f;
            int n9 = (int)(f * (float)(n - this.getPaddingLeft() - this.getPaddingRight()));
            if (n9 == this.getScrollX()) return;
            {
                ViewPagerEx.super.completeScroll(false);
                this.scrollTo(n9, this.getScrollY());
                return;
            }
        }
    }

    private void removeNonDecorViews() {
        for (int i = 0; i < this.getChildCount(); ++i) {
            if (((LayoutParams)this.getChildAt((int)i).getLayoutParams()).isDecor) continue;
            this.removeViewAt(i);
            --i;
        }
    }

    private void requestParentDisallowInterceptTouchEvent(boolean bl) {
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(bl);
        }
    }

    private void scrollToItem(int n, boolean bl, int n2, boolean bl2) {
        ItemInfo itemInfo = this.infoForPosition(n);
        int n3 = 0;
        if (itemInfo != null) {
            n3 = (int)((float)ViewPagerEx.super.getClientWidth() * Math.max((float)this.mFirstOffset, (float)Math.min((float)itemInfo.offset, (float)this.mLastOffset)));
        }
        if (bl) {
            this.smoothScrollTo(n3, 0, n2);
            if (bl2) {
                ViewPagerEx.super.triggerOnPageChangeEvent(n);
            }
            return;
        }
        if (bl2) {
            ViewPagerEx.super.triggerOnPageChangeEvent(n);
        }
        ViewPagerEx.super.completeScroll(false);
        this.scrollTo(n3, 0);
        ViewPagerEx.super.pageScrolled(n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setScrollState(int n) {
        if (this.mScrollState != n) {
            this.mScrollState = n;
            if (this.mPageTransformer != null) {
                boolean bl = n != 0;
                ViewPagerEx.super.enableLayers(bl);
            }
            for (OnPageChangeListener onPageChangeListener : this.mOnPageChangeListeners) {
                if (onPageChangeListener == null) continue;
                onPageChangeListener.onPageScrollStateChanged(n);
            }
        }
    }

    private void setScrollingCacheEnabled(boolean bl) {
        if (this.mScrollingCacheEnabled != bl) {
            this.mScrollingCacheEnabled = bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = new ArrayList();
            } else {
                this.mDrawingOrderedChildren.clear();
            }
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                View view = this.getChildAt(i);
                this.mDrawingOrderedChildren.add((Object)view);
            }
            Collections.sort(this.mDrawingOrderedChildren, (Comparator)sPositionComparator);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void triggerOnPageChangeEvent(int n) {
        for (OnPageChangeListener onPageChangeListener : this.mOnPageChangeListeners) {
            if (onPageChangeListener == null) continue;
            InfinitePagerAdapter infinitePagerAdapter = (InfinitePagerAdapter)this.mAdapter;
            if (infinitePagerAdapter.getRealCount() == 0) return;
            {
                onPageChangeListener.onPageSelected(n % infinitePagerAdapter.getRealCount());
            }
        }
        if (this.mInternalPageChangeListener == null) {
            return;
        }
        this.mInternalPageChangeListener.onPageSelected(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        int n3 = arrayList.size();
        int n4 = this.getDescendantFocusability();
        if (n4 != 393216) {
            for (int i = 0; i < this.getChildCount(); ++i) {
                ItemInfo itemInfo;
                View view = this.getChildAt(i);
                if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem) continue;
                view.addFocusables(arrayList, n, n2);
            }
        }
        if (n4 == 262144 && n3 != arrayList.size() || !this.isFocusable() || (n2 & 1) == 1 && this.isInTouchMode() && !this.isFocusableInTouchMode() || arrayList == null) {
            return;
        }
        arrayList.add((Object)this);
    }

    ItemInfo addNewItem(int n, int n2) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = n;
        itemInfo.object = this.mAdapter.instantiateItem((ViewGroup)this, n);
        itemInfo.widthFactor = this.mAdapter.getPageWidth(n);
        if (n2 < 0 || n2 >= this.mItems.size()) {
            this.mItems.add((Object)itemInfo);
            return itemInfo;
        }
        this.mItems.add(n2, (Object)itemInfo);
        return itemInfo;
    }

    public void addOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        if (!this.mOnPageChangeListeners.contains((Object)onPageChangeListener)) {
            this.mOnPageChangeListeners.add((Object)onPageChangeListener);
        }
    }

    public void addTouchables(ArrayList<View> arrayList) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            ItemInfo itemInfo;
            View view = this.getChildAt(i);
            if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem) continue;
            view.addTouchables(arrayList);
        }
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (!this.checkLayoutParams(layoutParams)) {
            layoutParams = this.generateLayoutParams(layoutParams);
        }
        LayoutParams layoutParams2 = (LayoutParams)layoutParams;
        layoutParams2.isDecor |= view instanceof Decor;
        if (this.mInLayout) {
            if (layoutParams2 != null && layoutParams2.isDecor) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            layoutParams2.needsMeasure = true;
            this.addViewInLayout(view, n, layoutParams);
            return;
        }
        super.addView(view, n, layoutParams);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean arrowScroll(int var1) {
        block12 : {
            block11 : {
                block10 : {
                    var2_2 = this.findFocus();
                    if (var2_2 != this) break block10;
                    var2_2 = null;
                    break block11;
                }
                if (var2_2 != null) break block12;
            }
lbl8: // 3 sources:
            do {
                block14 : {
                    block16 : {
                        block15 : {
                            block13 : {
                                var10_3 = FocusFinder.getInstance().findNextFocus((ViewGroup)this, var2_2, var1);
                                if (var10_3 == null || var10_3 == var2_2) break block13;
                                if (var1 == 17) {
                                    var14_4 = ViewPagerEx.super.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var10_3).left;
                                    var15_5 = ViewPagerEx.super.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var2_2).left;
                                    var11_6 = var2_2 != null && var14_4 >= var15_5 ? this.pageLeft() : var10_3.requestFocus();
                                } else {
                                    var11_6 = false;
                                    if (var1 == 66) {
                                        var12_12 = ViewPagerEx.super.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var10_3).left;
                                        var13_13 = ViewPagerEx.super.getChildRectInPagerCoordinates((Rect)this.mTempRect, (View)var2_2).left;
                                        var11_6 = var2_2 != null && var12_12 <= var13_13 ? this.pageRight() : var10_3.requestFocus();
                                    }
                                }
                                break block14;
                            }
                            if (var1 != 17 && var1 != 1) break block15;
                            var11_6 = this.pageLeft();
                            break block14;
                        }
                        if (var1 == 66) break block16;
                        var11_6 = false;
                        if (var1 != 2) break block14;
                    }
                    var11_6 = this.pageRight();
                }
                if (var11_6 == false) return var11_6;
                this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection((int)var1));
                return var11_6;
                break;
            } while (true);
        }
        var3_7 = var2_2.getParent();
        do {
            block18 : {
                block17 : {
                    var4_8 = var3_7 instanceof ViewGroup;
                    var5_9 = false;
                    if (!var4_8) break block17;
                    if (var3_7 != this) break block18;
                    var5_9 = true;
                }
                if (var5_9) ** GOTO lbl8
                var6_10 = new StringBuilder();
                var6_10.append(var2_2.getClass().getSimpleName());
                var8_11 = var2_2.getParent();
                while (var8_11 instanceof ViewGroup) {
                    var6_10.append(" => ").append(var8_11.getClass().getSimpleName());
                    var8_11 = var8_11.getParent();
                }
                break;
            }
            var3_7 = var3_7.getParent();
        } while (true);
        Log.e((String)"ViewPagerEx", (String)("arrowScroll tried to find focus based on non-child current focused view " + var6_10.toString()));
        var2_2 = null;
        ** while (true)
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        this.setScrollState(1);
        this.mLastMotionX = 0.0f;
        this.mInitialMotionX = 0.0f;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
        long l = SystemClock.uptimeMillis();
        MotionEvent motionEvent = MotionEvent.obtain((long)l, (long)l, (int)0, (float)0.0f, (float)0.0f, (int)0);
        this.mVelocityTracker.addMovement(motionEvent);
        motionEvent.recycle();
        this.mFakeDragBeginTime = l;
        return true;
    }

    protected boolean canScroll(View view, boolean bl, int n, int n2, int n3) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n4 = view.getScrollX();
            int n5 = view.getScrollY();
            for (int i = -1 + viewGroup.getChildCount(); i >= 0; --i) {
                View view2 = viewGroup.getChildAt(i);
                if (n2 + n4 < view2.getLeft() || n2 + n4 >= view2.getRight() || n3 + n5 < view2.getTop() || n3 + n5 >= view2.getBottom() || !this.canScroll(view2, true, n, n2 + n4 - view2.getLeft(), n3 + n5 - view2.getTop())) continue;
                return true;
            }
        }
        return bl && ViewCompat.canScrollHorizontally((View)view, (int)(-n));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean canScrollHorizontally(int n) {
        boolean bl = true;
        if (this.mAdapter == null) {
            return false;
        }
        int n2 = ViewPagerEx.super.getClientWidth();
        int n3 = this.getScrollX();
        if (n < 0) {
            if (n3 <= (int)((float)n2 * this.mFirstOffset)) return false;
            return bl;
        }
        if (n <= 0) return false;
        if (n3 >= (int)((float)n2 * this.mLastOffset)) return false;
        return bl;
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams && super.checkLayoutParams(layoutParams);
    }

    public void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            int n = this.getScrollX();
            int n2 = this.getScrollY();
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            if (n != n3 || n2 != n4) {
                this.scrollTo(n3, n4);
                if (!this.pageScrolled(n3)) {
                    this.mScroller.abortAnimation();
                    this.scrollTo(0, n4);
                }
            }
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        this.completeScroll(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    void dataSetChanged() {
        int n;
        this.mExpectedAdapterCount = n = this.mAdapter.getCount();
        boolean bl = this.mItems.size() < 1 + 2 * this.mOffscreenPageLimit && this.mItems.size() < n;
        int n2 = this.mCurItem;
        boolean bl2 = false;
        for (int i = 0; i < this.mItems.size(); ++i) {
            ItemInfo itemInfo = (ItemInfo)this.mItems.get(i);
            int n3 = this.mAdapter.getItemPosition(itemInfo.object);
            if (n3 == -1) continue;
            if (n3 == -2) {
                this.mItems.remove(i);
                --i;
                if (!bl2) {
                    this.mAdapter.startUpdate((ViewGroup)this);
                    bl2 = true;
                }
                this.mAdapter.destroyItem((ViewGroup)this, itemInfo.position, itemInfo.object);
                bl = true;
                if (this.mCurItem != itemInfo.position) continue;
                n2 = Math.max((int)0, (int)Math.min((int)this.mCurItem, (int)(n - 1)));
                bl = true;
                continue;
            }
            if (itemInfo.position == n3) continue;
            if (itemInfo.position == this.mCurItem) {
                n2 = n3;
            }
            itemInfo.position = n3;
            bl = true;
        }
        if (bl2) {
            this.mAdapter.finishUpdate((ViewGroup)this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (bl) {
            int n4 = this.getChildCount();
            for (int i = 0; i < n4; ++i) {
                LayoutParams layoutParams = (LayoutParams)this.getChildAt(i).getLayoutParams();
                if (layoutParams.isDecor) continue;
                layoutParams.widthFactor = 0.0f;
            }
            this.setCurrentItemInternal(n2, false, true);
            this.requestLayout();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || this.executeKeyEvent(keyEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            ItemInfo itemInfo;
            View view = this.getChildAt(i);
            if (view.getVisibility() != 0 || (itemInfo = this.infoForChild(view)) == null || itemInfo.position != this.mCurItem || !view.dispatchPopulateAccessibilityEvent(accessibilityEvent)) continue;
            return true;
        }
        return false;
    }

    float distanceInfluenceForSnapDuration(float f) {
        return (float)Math.sin((double)((float)(0.4712389167638204 * (double)(f - 0.5f))));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int n = ViewCompat.getOverScrollMode((View)this);
        if (n == 0 || n == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1) {
            boolean bl = this.mLeftEdge.isFinished();
            boolean bl2 = false;
            if (!bl) {
                int n2 = canvas.save();
                int n3 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
                int n4 = this.getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float)(-n3 + this.getPaddingTop()), this.mFirstOffset * (float)n4);
                this.mLeftEdge.setSize(n3, n4);
                bl2 = false | this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(n2);
            }
            if (!this.mRightEdge.isFinished()) {
                int n5 = canvas.save();
                int n6 = this.getWidth();
                int n7 = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate((float)(-this.getPaddingTop()), -(1.0f + this.mLastOffset) * (float)n6);
                this.mRightEdge.setSize(n7, n6);
                bl2 |= this.mRightEdge.draw(canvas);
                canvas.restoreToCount(n5);
            }
            if (!bl2) return;
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        this.mLeftEdge.finish();
        this.mRightEdge.finish();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable2 = this.mMarginDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            drawable2.setState(this.getDrawableState());
        }
    }

    public void endFakeDrag() {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
        int n = (int)VelocityTrackerCompat.getXVelocity((VelocityTracker)velocityTracker, (int)this.mActivePointerId);
        this.mPopulatePending = true;
        int n2 = this.getClientWidth();
        int n3 = this.getScrollX();
        ItemInfo itemInfo = this.infoForCurrentScrollPosition();
        this.setCurrentItemInternal(this.determineTargetPage(itemInfo.position, ((float)n3 / (float)n2 - itemInfo.offset) / itemInfo.widthFactor, n, (int)(this.mLastMotionX - this.mInitialMotionX)), true, true, n);
        this.endDrag();
        this.mFakeDragging = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean executeKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) return false;
        {
            switch (keyEvent.getKeyCode()) {
                default: {
                    return false;
                }
                case 21: {
                    return this.arrowScroll(17);
                }
                case 22: {
                    return this.arrowScroll(66);
                }
                case 61: {
                    if (Build.VERSION.SDK_INT < 11) return false;
                    if (KeyEventCompat.hasNoModifiers((KeyEvent)keyEvent)) {
                        return this.arrowScroll(2);
                    }
                    if (!KeyEventCompat.hasModifiers((KeyEvent)keyEvent, (int)1)) return false;
                    return this.arrowScroll(1);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void fakeDragBy(float f) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        this.mLastMotionX = f + this.mLastMotionX;
        float f2 = (float)this.getScrollX() - f;
        int n = ViewPagerEx.super.getClientWidth();
        float f3 = (float)n * this.mFirstOffset;
        float f4 = (float)n * this.mLastOffset;
        ItemInfo itemInfo = (ItemInfo)this.mItems.get(0);
        ItemInfo itemInfo2 = (ItemInfo)this.mItems.get(-1 + this.mItems.size());
        if (itemInfo.position != 0) {
            f3 = itemInfo.offset * (float)n;
        }
        if (itemInfo2.position != -1 + this.mAdapter.getCount()) {
            f4 = itemInfo2.offset * (float)n;
        }
        if (f2 < f3) {
            f2 = f3;
        } else if (f2 > f4) {
            f2 = f4;
        }
        this.mLastMotionX += f2 - (float)((int)f2);
        this.scrollTo((int)f2, this.getScrollY());
        ViewPagerEx.super.pageScrolled((int)f2);
        long l = SystemClock.uptimeMillis();
        MotionEvent motionEvent = MotionEvent.obtain((long)this.mFakeDragBeginTime, (long)l, (int)2, (float)this.mLastMotionX, (float)0.0f, (int)0);
        this.mVelocityTracker.addMovement(motionEvent);
        motionEvent.recycle();
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return this.generateDefaultLayoutParams();
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected int getChildDrawingOrder(int n, int n2) {
        int n3;
        if (this.mDrawingOrder == 2) {
            n3 = n - 1 - n2;
            do {
                return ((LayoutParams)((View)this.mDrawingOrderedChildren.get((int)n3)).getLayoutParams()).childIndex;
                break;
            } while (true);
        }
        n3 = n2;
        return ((LayoutParams)((View)this.mDrawingOrderedChildren.get((int)n3)).getLayoutParams()).childIndex;
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    ItemInfo infoForAnyChild(View view) {
        ViewParent viewParent;
        while ((viewParent = view.getParent()) != this) {
            if (viewParent == null || !(viewParent instanceof View)) {
                return null;
            }
            view = (View)viewParent;
        }
        return this.infoForChild(view);
    }

    ItemInfo infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); ++i) {
            ItemInfo itemInfo = (ItemInfo)this.mItems.get(i);
            if (!this.mAdapter.isViewFromObject(view, itemInfo.object)) continue;
            return itemInfo;
        }
        return null;
    }

    ItemInfo infoForPosition(int n) {
        for (int i = 0; i < this.mItems.size(); ++i) {
            ItemInfo itemInfo = (ItemInfo)this.mItems.get(i);
            if (itemInfo.position != n) continue;
            return itemInfo;
        }
        return null;
    }

    void initViewPager() {
        this.setWillNotDraw(false);
        this.setDescendantFocusability(262144);
        this.setFocusable(true);
        Context context = this.getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop((ViewConfiguration)viewConfiguration);
        this.mMinimumVelocity = (int)(400.0f * f);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffectCompat(context);
        this.mRightEdge = new EdgeEffectCompat(context);
        this.mFlingDistance = (int)(25.0f * f);
        this.mCloseEnough = (int)(2.0f * f);
        this.mDefaultGutterSize = (int)(16.0f * f);
        ViewCompat.setAccessibilityDelegate((View)this, (AccessibilityDelegateCompat)new /* Unavailable Anonymous Inner Class!! */);
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, (int)1);
        }
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    protected void onDetachedFromWindow() {
        this.removeCallbacks(this.mEndScrollRunnable);
        super.onDetachedFromWindow();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            int n = this.getScrollX();
            int n2 = this.getWidth();
            float f = (float)this.mPageMargin / (float)n2;
            int n3 = 0;
            ItemInfo itemInfo = (ItemInfo)this.mItems.get(0);
            float f2 = itemInfo.offset;
            int n4 = this.mItems.size();
            int n5 = itemInfo.position;
            int n6 = ((ItemInfo)this.mItems.get((int)(n4 - 1))).position;
            for (int i = n5; i < n6; ++i) {
                float f3;
                while (i > itemInfo.position && n3 < n4) {
                    ArrayList<ItemInfo> arrayList = this.mItems;
                    itemInfo = (ItemInfo)arrayList.get(++n3);
                }
                if (i == itemInfo.position) {
                    f3 = (itemInfo.offset + itemInfo.widthFactor) * (float)n2;
                    f2 = f + (itemInfo.offset + itemInfo.widthFactor);
                } else {
                    float f4 = this.mAdapter.getPageWidth(i);
                    f3 = (f2 + f4) * (float)n2;
                    f2 += f4 + f;
                }
                if (f3 + (float)this.mPageMargin > (float)n) {
                    this.mMarginDrawable.setBounds((int)f3, this.mTopPageBounds, (int)(0.5f + (f3 + (float)this.mPageMargin)), this.mBottomPageBounds);
                    this.mMarginDrawable.draw(canvas);
                }
                if (f3 > (float)(n + n2)) break;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n = 255 & motionEvent.getAction();
        if (n == 3 || n == 1) {
            this.mIsBeingDragged = false;
            this.mIsUnableToDrag = false;
            this.mActivePointerId = -1;
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            return false;
        }
        if (n != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        switch (n) {
            case 2: {
                int n2 = this.mActivePointerId;
                if (n2 == -1) break;
                int n3 = MotionEventCompat.findPointerIndex((MotionEvent)motionEvent, (int)n2);
                float f = MotionEventCompat.getX((MotionEvent)motionEvent, (int)n3);
                float f2 = f - this.mLastMotionX;
                float f3 = Math.abs((float)f2);
                float f4 = MotionEventCompat.getY((MotionEvent)motionEvent, (int)n3);
                float f5 = Math.abs((float)(f4 - this.mInitialMotionY));
                if (f2 != 0.0f && !ViewPagerEx.super.isGutterDrag(this.mLastMotionX, f2) && this.canScroll((View)this, false, (int)f2, (int)f, (int)f4)) {
                    this.mLastMotionX = f;
                    this.mLastMotionY = f4;
                    this.mIsUnableToDrag = true;
                    return false;
                }
                if (f3 > (float)this.mTouchSlop && 0.5f * f3 > f5) {
                    this.mIsBeingDragged = true;
                    ViewPagerEx.super.requestParentDisallowInterceptTouchEvent(true);
                    ViewPagerEx.super.setScrollState(1);
                    float f6 = f2 > 0.0f ? this.mInitialMotionX + (float)this.mTouchSlop : this.mInitialMotionX - (float)this.mTouchSlop;
                    this.mLastMotionX = f6;
                    this.mLastMotionY = f4;
                    ViewPagerEx.super.setScrollingCacheEnabled(true);
                } else if (f5 > (float)this.mTouchSlop) {
                    this.mIsUnableToDrag = true;
                }
                if (!this.mIsBeingDragged || !ViewPagerEx.super.performDrag(f)) break;
                ViewCompat.postInvalidateOnAnimation((View)this);
                break;
            }
            case 0: {
                float f;
                float f7;
                this.mInitialMotionX = f = motionEvent.getX();
                this.mLastMotionX = f;
                this.mInitialMotionY = f7 = motionEvent.getY();
                this.mLastMotionY = f7;
                this.mActivePointerId = MotionEventCompat.getPointerId((MotionEvent)motionEvent, (int)0);
                this.mIsUnableToDrag = false;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == 2 && Math.abs((int)(this.mScroller.getFinalX() - this.mScroller.getCurrX())) > this.mCloseEnough) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    this.populate();
                    this.mIsBeingDragged = true;
                    ViewPagerEx.super.requestParentDisallowInterceptTouchEvent(true);
                    ViewPagerEx.super.setScrollState(1);
                    break;
                }
                ViewPagerEx.super.completeScroll(false);
                this.mIsBeingDragged = false;
                break;
            }
            case 6: {
                ViewPagerEx.super.onSecondaryPointerUp(motionEvent);
                break;
            }
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getChildCount();
        int n6 = n3 - n;
        int n7 = n4 - n2;
        int n8 = this.getPaddingLeft();
        int n9 = this.getPaddingTop();
        int n10 = this.getPaddingRight();
        int n11 = this.getPaddingBottom();
        int n12 = this.getScrollX();
        int n13 = 0;
        for (int i = 0; i < n5; ++i) {
            int n14;
            int n15;
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (!layoutParams.isDecor) continue;
            int n16 = 7 & layoutParams.gravity;
            int n17 = 112 & layoutParams.gravity;
            switch (n16) {
                default: {
                    n14 = n8;
                    break;
                }
                case 3: {
                    n14 = n8;
                    n8 += view.getMeasuredWidth();
                    break;
                }
                case 1: {
                    n14 = Math.max((int)((n6 - view.getMeasuredWidth()) / 2), (int)n8);
                    break;
                }
                case 5: {
                    n14 = n6 - n10 - view.getMeasuredWidth();
                    n10 += view.getMeasuredWidth();
                }
            }
            switch (n17) {
                default: {
                    n15 = n9;
                    break;
                }
                case 48: {
                    n15 = n9;
                    n9 += view.getMeasuredHeight();
                    break;
                }
                case 16: {
                    n15 = Math.max((int)((n7 - view.getMeasuredHeight()) / 2), (int)n9);
                    break;
                }
                case 80: {
                    n15 = n7 - n11 - view.getMeasuredHeight();
                    n11 += view.getMeasuredHeight();
                }
            }
            int n18 = n14 + n12;
            view.layout(n18, n15, n18 + view.getMeasuredWidth(), n15 + view.getMeasuredHeight());
            ++n13;
        }
        int n19 = n6 - n8 - n10;
        for (int i = 0; i < n5; ++i) {
            ItemInfo itemInfo;
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            if (layoutParams.isDecor || (itemInfo = this.infoForChild(view)) == null) continue;
            int n20 = n8 + (int)((float)n19 * itemInfo.offset);
            int n21 = n9;
            if (layoutParams.needsMeasure) {
                layoutParams.needsMeasure = false;
                view.measure(View.MeasureSpec.makeMeasureSpec((int)((int)((float)n19 * layoutParams.widthFactor)), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)(n7 - n9 - n11), (int)1073741824));
            }
            view.layout(n20, n21, n20 + view.getMeasuredWidth(), n21 + view.getMeasuredHeight());
        }
        this.mTopPageBounds = n9;
        this.mBottomPageBounds = n7 - n11;
        this.mDecorChildCount = n13;
        if (this.mFirstLayout) {
            this.scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        this.setMeasuredDimension(ViewPagerEx.getDefaultSize((int)0, (int)n), ViewPagerEx.getDefaultSize((int)0, (int)n2));
        int n3 = this.getMeasuredWidth();
        this.mGutterSize = Math.min((int)(n3 / 10), (int)this.mDefaultGutterSize);
        int n4 = n3 - this.getPaddingLeft() - this.getPaddingRight();
        int n5 = this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom();
        int n6 = this.getChildCount();
        for (int i = 0; i < n6; ++i) {
            LayoutParams layoutParams;
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8 || (layoutParams = (LayoutParams)view.getLayoutParams()) == null || !layoutParams.isDecor) continue;
            int n7 = 7 & layoutParams.gravity;
            int n8 = 112 & layoutParams.gravity;
            int n9 = Integer.MIN_VALUE;
            int n10 = Integer.MIN_VALUE;
            boolean bl = n8 == 48 || n8 == 80;
            boolean bl2 = n7 == 3 || n7 == 5;
            if (bl) {
                n9 = 1073741824;
            } else if (bl2) {
                n10 = 1073741824;
            }
            int n11 = n4;
            int n12 = n5;
            if (layoutParams.width != -2) {
                n9 = 1073741824;
                if (layoutParams.width != -1) {
                    n11 = layoutParams.width;
                }
            }
            if (layoutParams.height != -2) {
                n10 = 1073741824;
                if (layoutParams.height != -1) {
                    n12 = layoutParams.height;
                }
            }
            view.measure(View.MeasureSpec.makeMeasureSpec((int)n11, (int)n9), View.MeasureSpec.makeMeasureSpec((int)n12, (int)n10));
            if (bl) {
                n5 -= view.getMeasuredHeight();
                continue;
            }
            if (!bl2) continue;
            n4 -= view.getMeasuredWidth();
        }
        this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)n4, (int)1073741824);
        this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int)n5, (int)1073741824);
        this.mInLayout = true;
        this.populate();
        this.mInLayout = false;
        int n13 = this.getChildCount();
        int n14 = 0;
        while (n14 < n13) {
            LayoutParams layoutParams;
            View view = this.getChildAt(n14);
            if (!(view.getVisibility() == 8 || (layoutParams = (LayoutParams)view.getLayoutParams()) != null && layoutParams.isDecor)) {
                view.measure(View.MeasureSpec.makeMeasureSpec((int)((int)((float)n4 * layoutParams.widthFactor)), (int)1073741824), this.mChildHeightMeasureSpec);
            }
            ++n14;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onPageScrolled(int n, float f, int n2) {
        if (this.mDecorChildCount > 0) {
            int n3 = this.getScrollX();
            int n4 = this.getPaddingLeft();
            int n5 = this.getPaddingRight();
            int n6 = this.getWidth();
            int n7 = this.getChildCount();
            for (int i = 0; i < n7; ++i) {
                int n8;
                int n9;
                View view = this.getChildAt(i);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (!layoutParams.isDecor) continue;
                switch (7 & layoutParams.gravity) {
                    default: {
                        n9 = n4;
                        break;
                    }
                    case 3: {
                        n9 = n4;
                        n4 += view.getWidth();
                        break;
                    }
                    case 1: {
                        n9 = Math.max((int)((n6 - view.getMeasuredWidth()) / 2), (int)n4);
                        break;
                    }
                    case 5: {
                        n9 = n6 - n5 - view.getMeasuredWidth();
                        n5 += view.getMeasuredWidth();
                    }
                }
                if ((n8 = n9 + n3 - view.getLeft()) == 0) continue;
                view.offsetLeftAndRight(n8);
            }
        }
        for (OnPageChangeListener onPageChangeListener : this.mOnPageChangeListeners) {
            if (onPageChangeListener == null) continue;
            onPageChangeListener.onPageScrolled(n, f, n2);
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(n, f, n2);
        }
        if (this.mPageTransformer != null) {
            int n10 = this.getScrollX();
            int n11 = this.getChildCount();
            for (int i = 0; i < n11; ++i) {
                View view = this.getChildAt(i);
                if (((LayoutParams)view.getLayoutParams()).isDecor) continue;
                float f2 = (float)(view.getLeft() - n10) / (float)ViewPagerEx.super.getClientWidth();
                this.mPageTransformer.transformPage(view, f2);
            }
        }
        this.mCalledSuper = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        int n2;
        int n3;
        int n4;
        int n5 = this.getChildCount();
        if ((n & 2) != 0) {
            n3 = 0;
            n2 = 1;
            n4 = n5;
        } else {
            n3 = n5 - 1;
            n2 = -1;
            n4 = -1;
        }
        int n6 = n3;
        while (n6 != n4) {
            ItemInfo itemInfo;
            View view = this.getChildAt(n6);
            if (view.getVisibility() == 0 && (itemInfo = this.infoForChild(view)) != null && itemInfo.position == this.mCurItem && view.requestFocus(n, rect)) {
                return true;
            }
            n6 += n2;
        }
        return false;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState)parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.mAdapter != null) {
            this.mAdapter.restoreState(savedState.adapterState, savedState.loader);
            this.setCurrentItemInternal(savedState.position, false, true);
            return;
        }
        this.mRestoredCurItem = savedState.position;
        this.mRestoredAdapterState = savedState.adapterState;
        this.mRestoredClassLoader = savedState.loader;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        if (this.mAdapter != null) {
            savedState.adapterState = this.mAdapter.saveState();
        }
        return savedState;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n != n3) {
            ViewPagerEx.super.recomputeScrollPosition(n, n3, this.mPageMargin, this.mPageMargin);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mFakeDragging) {
            return true;
        }
        if (motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) {
            return false;
        }
        if (this.mAdapter == null) return false;
        if (this.mAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int n = 255 & motionEvent.getAction();
        boolean bl = false;
        switch (n) {
            case 0: {
                float f;
                float f2;
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                this.populate();
                this.mInitialMotionX = f2 = motionEvent.getX();
                this.mLastMotionX = f2;
                this.mInitialMotionY = f = motionEvent.getY();
                this.mLastMotionY = f;
                this.mActivePointerId = MotionEventCompat.getPointerId((MotionEvent)motionEvent, (int)0);
                return true;
            }
            case 2: {
                if (!this.mIsBeingDragged) {
                    int n2 = MotionEventCompat.findPointerIndex((MotionEvent)motionEvent, (int)this.mActivePointerId);
                    float f = MotionEventCompat.getX((MotionEvent)motionEvent, (int)n2);
                    float f3 = Math.abs((float)(f - this.mLastMotionX));
                    float f4 = MotionEventCompat.getY((MotionEvent)motionEvent, (int)n2);
                    float f5 = Math.abs((float)(f4 - this.mLastMotionY));
                    if (f3 > (float)this.mTouchSlop && f3 > f5) {
                        this.mIsBeingDragged = true;
                        ViewPagerEx.super.requestParentDisallowInterceptTouchEvent(true);
                        float f6 = f - this.mInitialMotionX > 0.0f ? this.mInitialMotionX + (float)this.mTouchSlop : this.mInitialMotionX - (float)this.mTouchSlop;
                        this.mLastMotionX = f6;
                        this.mLastMotionY = f4;
                        ViewPagerEx.super.setScrollState(1);
                        ViewPagerEx.super.setScrollingCacheEnabled(true);
                        ViewParent viewParent = this.getParent();
                        if (viewParent != null) {
                            viewParent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                boolean bl2 = this.mIsBeingDragged;
                bl = false;
                if (!bl2) break;
                bl = false | ViewPagerEx.super.performDrag(MotionEventCompat.getX((MotionEvent)motionEvent, (int)MotionEventCompat.findPointerIndex((MotionEvent)motionEvent, (int)this.mActivePointerId)));
                break;
            }
            case 1: {
                boolean bl3 = this.mIsBeingDragged;
                bl = false;
                if (!bl3) break;
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                int n3 = (int)VelocityTrackerCompat.getXVelocity((VelocityTracker)velocityTracker, (int)this.mActivePointerId);
                this.mPopulatePending = true;
                int n4 = ViewPagerEx.super.getClientWidth();
                int n5 = this.getScrollX();
                ItemInfo itemInfo = ViewPagerEx.super.infoForCurrentScrollPosition();
                this.setCurrentItemInternal(ViewPagerEx.super.determineTargetPage(itemInfo.position, ((float)n5 / (float)n4 - itemInfo.offset) / itemInfo.widthFactor, n3, (int)(MotionEventCompat.getX((MotionEvent)motionEvent, (int)MotionEventCompat.findPointerIndex((MotionEvent)motionEvent, (int)this.mActivePointerId)) - this.mInitialMotionX)), true, true, n3);
                this.mActivePointerId = -1;
                ViewPagerEx.super.endDrag();
                bl = this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
                break;
            }
            case 3: {
                boolean bl4 = this.mIsBeingDragged;
                bl = false;
                if (!bl4) break;
                ViewPagerEx.super.scrollToItem(this.mCurItem, true, 0, false);
                this.mActivePointerId = -1;
                ViewPagerEx.super.endDrag();
                bl = this.mLeftEdge.onRelease() | this.mRightEdge.onRelease();
                break;
            }
            case 5: {
                int n6 = MotionEventCompat.getActionIndex((MotionEvent)motionEvent);
                this.mLastMotionX = MotionEventCompat.getX((MotionEvent)motionEvent, (int)n6);
                this.mActivePointerId = MotionEventCompat.getPointerId((MotionEvent)motionEvent, (int)n6);
                return true;
            }
            case 6: {
                ViewPagerEx.super.onSecondaryPointerUp(motionEvent);
                this.mLastMotionX = MotionEventCompat.getX((MotionEvent)motionEvent, (int)MotionEventCompat.findPointerIndex((MotionEvent)motionEvent, (int)this.mActivePointerId));
                bl = false;
                break;
            }
        }
        if (!bl) return true;
        ViewCompat.postInvalidateOnAnimation((View)this);
        return true;
    }

    boolean pageLeft() {
        if (this.mCurItem > 0) {
            this.setCurrentItem(-1 + this.mCurItem, true);
            return true;
        }
        return false;
    }

    boolean pageRight() {
        if (this.mAdapter != null && this.mCurItem < -1 + this.mAdapter.getCount()) {
            this.setCurrentItem(1 + this.mCurItem, true);
            return true;
        }
        return false;
    }

    void populate() {
        this.populate(this.mCurItem);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void populate(int n) {
        ItemInfo itemInfo;
        int n2;
        int n3;
        block26 : {
            ItemInfo itemInfo2;
            int n4;
            block27 : {
                float f;
                int n5;
                float f2;
                ItemInfo itemInfo3;
                n2 = 2;
                int n6 = this.mCurItem;
                itemInfo2 = null;
                if (n6 != n) {
                    n2 = this.mCurItem < n ? 66 : 17;
                    itemInfo2 = this.infoForPosition(this.mCurItem);
                    this.mCurItem = n;
                }
                if (this.mAdapter == null) {
                    ViewPagerEx.super.sortChildDrawingOrder();
                    return;
                }
                if (this.mPopulatePending) {
                    ViewPagerEx.super.sortChildDrawingOrder();
                    return;
                }
                if (this.getWindowToken() == null) return;
                this.mAdapter.startUpdate((ViewGroup)this);
                int n7 = this.mOffscreenPageLimit;
                int n8 = Math.max((int)0, (int)(this.mCurItem - n7));
                int n9 = this.mAdapter.getCount();
                int n10 = Math.min((int)(n9 - 1), (int)(n7 + this.mCurItem));
                if (n9 != this.mExpectedAdapterCount) {
                    String string2;
                    try {
                        String string3;
                        string2 = string3 = this.getResources().getResourceName(this.getId());
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        string2 = Integer.toHexString((int)this.getId());
                        throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + n9 + " Pager id: " + string2 + " Pager class: " + (Object)this.getClass() + " Problematic adapter: " + (Object)this.mAdapter.getClass());
                    }
                    throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + n9 + " Pager id: " + string2 + " Pager class: " + (Object)this.getClass() + " Problematic adapter: " + (Object)this.mAdapter.getClass());
                }
                n4 = 0;
                do {
                    block29 : {
                        block28 : {
                            int n11 = this.mItems.size();
                            itemInfo = null;
                            if (n4 >= n11) break block28;
                            ItemInfo itemInfo4 = (ItemInfo)this.mItems.get(n4);
                            if (itemInfo4.position < this.mCurItem) break block29;
                            int n12 = itemInfo4.position;
                            int n13 = this.mCurItem;
                            itemInfo = null;
                            if (n12 == n13) {
                                itemInfo = itemInfo4;
                            }
                        }
                        if (itemInfo == null && n9 > 0) {
                            itemInfo = this.addNewItem(this.mCurItem, n4);
                        }
                        if (itemInfo != null) {
                            break;
                        }
                        break block26;
                    }
                    ++n4;
                } while (true);
                float f3 = 0.0f;
                int n14 = n4 - 1;
                ItemInfo itemInfo5 = n14 >= 0 ? (ItemInfo)this.mItems.get(n14) : null;
                int n15 = ViewPagerEx.super.getClientWidth();
                float f4 = n15 <= 0 ? 0.0f : 2.0f - itemInfo.widthFactor + (float)this.getPaddingLeft() / (float)n15;
                int n16 = -1 + this.mCurItem;
                do {
                    block33 : {
                        int n17;
                        block31 : {
                            block32 : {
                                block30 : {
                                    if (n16 < 0) break block30;
                                    if (!(f3 >= f4) || n16 >= n8) break block31;
                                    if (itemInfo5 != null) break block32;
                                }
                                f = itemInfo.widthFactor;
                                n5 = n4 + 1;
                                if (f < 2.0f) {
                                    int n18 = this.mItems.size();
                                    itemInfo3 = n5 < n18 ? (ItemInfo)this.mItems.get(n5) : null;
                                    f2 = n15 <= 0 ? 0.0f : 2.0f + (float)this.getPaddingRight() / (float)n15;
                                    break;
                                }
                                break block27;
                            }
                            int n19 = itemInfo5.position;
                            if (n16 == n19 && !itemInfo5.scrolling) {
                                this.mItems.remove(n14);
                                PagerAdapter pagerAdapter = this.mAdapter;
                                Object object = itemInfo5.object;
                                pagerAdapter.destroyItem((ViewGroup)this, n16, object);
                                --n4;
                                itemInfo5 = --n14 >= 0 ? (ItemInfo)this.mItems.get(n14) : null;
                            }
                            break block33;
                        }
                        if (itemInfo5 != null && n16 == (n17 = itemInfo5.position)) {
                            f3 += itemInfo5.widthFactor;
                            itemInfo5 = --n14 >= 0 ? (ItemInfo)this.mItems.get(n14) : null;
                        } else {
                            int n20 = n14 + 1;
                            f3 += this.addNewItem((int)n16, (int)n20).widthFactor;
                            ++n4;
                            itemInfo5 = n14 >= 0 ? (ItemInfo)this.mItems.get(n14) : null;
                        }
                    }
                    --n16;
                } while (true);
                for (int i = 1 + this.mCurItem; i < n9; ++i) {
                    int n21;
                    if (f >= f2 && i > n10) {
                        if (itemInfo3 == null) break;
                        int n22 = itemInfo3.position;
                        if (i != n22 || itemInfo3.scrolling) continue;
                        this.mItems.remove(n5);
                        PagerAdapter pagerAdapter = this.mAdapter;
                        Object object = itemInfo3.object;
                        pagerAdapter.destroyItem((ViewGroup)this, i, object);
                        int n23 = this.mItems.size();
                        if (n5 < n23) {
                            itemInfo3 = (ItemInfo)this.mItems.get(n5);
                            continue;
                        }
                        itemInfo3 = null;
                        continue;
                    }
                    if (itemInfo3 != null && i == (n21 = itemInfo3.position)) {
                        f += itemInfo3.widthFactor;
                        int n24 = this.mItems.size();
                        if (++n5 < n24) {
                            itemInfo3 = (ItemInfo)this.mItems.get(n5);
                            continue;
                        }
                        itemInfo3 = null;
                        continue;
                    }
                    ItemInfo itemInfo6 = this.addNewItem(i, n5);
                    f += itemInfo6.widthFactor;
                    int n25 = this.mItems.size();
                    itemInfo3 = ++n5 < n25 ? (ItemInfo)this.mItems.get(n5) : null;
                }
            }
            ViewPagerEx.super.calculatePageOffsets(itemInfo, n4, itemInfo2);
        }
        PagerAdapter pagerAdapter = this.mAdapter;
        int n26 = this.mCurItem;
        Object object = itemInfo != null ? itemInfo.object : null;
        pagerAdapter.setPrimaryItem((ViewGroup)this, n26, object);
        this.mAdapter.finishUpdate((ViewGroup)this);
        int n27 = this.getChildCount();
        for (int i = 0; i < n27; ++i) {
            ItemInfo itemInfo7;
            View view = this.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            layoutParams.childIndex = i;
            if (layoutParams.isDecor || layoutParams.widthFactor != 0.0f || (itemInfo7 = this.infoForChild(view)) == null) continue;
            layoutParams.widthFactor = itemInfo7.widthFactor;
            layoutParams.position = itemInfo7.position;
        }
        ViewPagerEx.super.sortChildDrawingOrder();
        if (!this.hasFocus()) return;
        View view = this.findFocus();
        ItemInfo itemInfo8 = view != null ? this.infoForAnyChild(view) : null;
        if (itemInfo8 != null) {
            if (itemInfo8.position == this.mCurItem) return;
        }
        int n28 = 0;
        while (n28 < (n3 = this.getChildCount())) {
            View view2 = this.getChildAt(n28);
            ItemInfo itemInfo9 = this.infoForChild(view2);
            if (itemInfo9 != null && itemInfo9.position == this.mCurItem) {
                if (view2.requestFocus(n2)) return;
            }
            ++n28;
        }
    }

    public void removeOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListeners.remove((Object)onPageChangeListener);
    }

    public void removeView(View view) {
        if (this.mInLayout) {
            this.removeViewInLayout(view);
            return;
        }
        super.removeView(view);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setAdapter(PagerAdapter pagerAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver((DataSetObserver)this.mObserver);
            this.mAdapter.startUpdate((ViewGroup)this);
            for (int i = 0; i < this.mItems.size(); ++i) {
                ItemInfo itemInfo = (ItemInfo)this.mItems.get(i);
                this.mAdapter.destroyItem((ViewGroup)this, itemInfo.position, itemInfo.object);
            }
            this.mAdapter.finishUpdate((ViewGroup)this);
            this.mItems.clear();
            ViewPagerEx.super.removeNonDecorViews();
            this.mCurItem = 0;
            this.scrollTo(0, 0);
        }
        PagerAdapter pagerAdapter2 = this.mAdapter;
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver((ViewPagerEx)this, null);
            }
            this.mAdapter.registerDataSetObserver((DataSetObserver)this.mObserver);
            this.mPopulatePending = false;
            boolean bl = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                this.setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (!bl) {
                this.populate();
            } else {
                this.requestLayout();
            }
        }
        if (this.mAdapterChangeListener != null && pagerAdapter2 != pagerAdapter) {
            this.mAdapterChangeListener.onAdapterChanged(pagerAdapter2, pagerAdapter);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void setChildrenDrawingOrderEnabledCompat(boolean bl) {
        if (Build.VERSION.SDK_INT < 7) return;
        if (this.mSetChildrenDrawingOrderEnabled == null) {
            try {
                Class[] arrclass = new Class[]{Boolean.TYPE};
                this.mSetChildrenDrawingOrderEnabled = ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", arrclass);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.e((String)TAG, (String)"Can't find setChildrenDrawingOrderEnabled", (Throwable)noSuchMethodException);
            }
        }
        try {
            Method method = this.mSetChildrenDrawingOrderEnabled;
            Object[] arrobject = new Object[]{bl};
            method.invoke((Object)this, arrobject);
            return;
        }
        catch (Exception exception) {
            Log.e((String)TAG, (String)"Error changing children drawing order", (Throwable)exception);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCurrentItem(int n) {
        this.mPopulatePending = false;
        boolean bl = !this.mFirstLayout;
        this.setCurrentItemInternal(n, bl, false);
    }

    public void setCurrentItem(int n, boolean bl) {
        this.mPopulatePending = false;
        this.setCurrentItemInternal(n, bl, false);
    }

    void setCurrentItemInternal(int n, boolean bl, boolean bl2) {
        this.setCurrentItemInternal(n, bl, bl2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    void setCurrentItemInternal(int n, boolean bl, boolean bl2, int n2) {
        int n3;
        boolean bl3 = true;
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            ViewPagerEx.super.setScrollingCacheEnabled(false);
            return;
        }
        if (!bl2 && this.mCurItem == n && this.mItems.size() != 0) {
            ViewPagerEx.super.setScrollingCacheEnabled(false);
            return;
        }
        if (n < 0) {
            n = 0;
        } else if (n >= this.mAdapter.getCount()) {
            n = -1 + this.mAdapter.getCount();
        }
        if (n > (n3 = this.mOffscreenPageLimit) + this.mCurItem || n < this.mCurItem - n3) {
            for (int i = 0; i < this.mItems.size(); ++i) {
                ((ItemInfo)this.mItems.get((int)i)).scrolling = bl3;
            }
        }
        if (this.mCurItem == n) {
            bl3 = false;
        }
        if (this.mFirstLayout) {
            this.mCurItem = n;
            ViewPagerEx.super.triggerOnPageChangeEvent(n);
            this.requestLayout();
            return;
        }
        this.populate(n);
        ViewPagerEx.super.scrollToItem(n, bl, n2, bl3);
    }

    OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public void setOffscreenPageLimit(int n) {
        if (n < 1) {
            Log.w((String)TAG, (String)("Requested offscreen page limit " + n + " too small; defaulting to " + 1));
            n = 1;
        }
        if (n != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = n;
            this.populate();
        }
    }

    void setOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        this.mAdapterChangeListener = onAdapterChangeListener;
    }

    public void setPageMargin(int n) {
        int n2 = this.mPageMargin;
        this.mPageMargin = n;
        int n3 = this.getWidth();
        ViewPagerEx.super.recomputeScrollPosition(n3, n3, n, n2);
        this.requestLayout();
    }

    public void setPageMarginDrawable(int n) {
        this.setPageMarginDrawable(this.getContext().getResources().getDrawable(n));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPageMarginDrawable(Drawable drawable2) {
        this.mMarginDrawable = drawable2;
        if (drawable2 != null) {
            this.refreshDrawableState();
        }
        boolean bl = drawable2 == null;
        this.setWillNotDraw(bl);
        this.invalidate();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setPageTransformer(boolean bl, PageTransformer pageTransformer) {
        int n = 1;
        int n2 = pageTransformer != null ? n : 0;
        int n3 = this.mPageTransformer != null ? n : 0;
        int n4 = n2 != n3 ? n : 0;
        this.mPageTransformer = pageTransformer;
        this.setChildrenDrawingOrderEnabledCompat((boolean)n2);
        if (n2 != 0) {
            if (bl) {
                n = 2;
            }
            this.mDrawingOrder = n;
        } else {
            this.mDrawingOrder = 0;
        }
        if (n4 != 0) {
            this.populate();
        }
    }

    void smoothScrollTo(int n, int n2) {
        this.smoothScrollTo(n, n2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    void smoothScrollTo(int n, int n2, int n3) {
        int n4;
        if (this.getChildCount() == 0) {
            ViewPagerEx.super.setScrollingCacheEnabled(false);
            return;
        }
        int n5 = this.getScrollX();
        int n6 = this.getScrollY();
        int n7 = n - n5;
        int n8 = n2 - n6;
        if (n7 == 0 && n8 == 0) {
            ViewPagerEx.super.completeScroll(false);
            this.populate();
            ViewPagerEx.super.setScrollState(0);
            return;
        }
        ViewPagerEx.super.setScrollingCacheEnabled(true);
        ViewPagerEx.super.setScrollState(2);
        int n9 = ViewPagerEx.super.getClientWidth();
        int n10 = n9 / 2;
        float f = Math.min((float)1.0f, (float)(1.0f * (float)Math.abs((int)n7) / (float)n9));
        float f2 = (float)n10 + (float)n10 * this.distanceInfluenceForSnapDuration(f);
        int n11 = Math.abs((int)n3);
        if (n11 > 0) {
            n4 = 4 * Math.round((float)(1000.0f * Math.abs((float)(f2 / (float)n11))));
        } else {
            float f3 = (float)n9 * this.mAdapter.getPageWidth(this.mCurItem);
            n4 = (int)(100.0f * (1.0f + (float)Math.abs((int)n7) / (f3 + (float)this.mPageMargin)));
        }
        int n12 = Math.min((int)n4, (int)600);
        this.mScroller.startScroll(n5, n6, n7, n8, n12);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        return super.verifyDrawable(drawable2) || drawable2 == this.mMarginDrawable;
    }

    static interface Decor {
    }

    static class ItemInfo {
        Object object;
        float offset;
        int position;
        boolean scrolling;
        float widthFactor;

        ItemInfo() {
        }
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        int childIndex;
        public int gravity;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        float widthFactor;

        public LayoutParams() {
            super(-1, -1);
            this.widthFactor = 0.0f;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.widthFactor = 0.0f;
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, LAYOUT_ATTRS);
            this.gravity = typedArray.getInteger(0, 48);
            typedArray.recycle();
        }
    }

    static interface OnAdapterChangeListener {
        public void onAdapterChanged(PagerAdapter var1, PagerAdapter var2);
    }

    public static interface OnPageChangeListener {
        public void onPageScrollStateChanged(int var1);

        public void onPageScrolled(int var1, float var2, int var3);

        public void onPageSelected(int var1);
    }

    public static interface PageTransformer {
        public void transformPage(View var1, float var2);
    }

    private class PagerObserver
    extends DataSetObserver {
        final /* synthetic */ ViewPagerEx this$0;

        private PagerObserver(ViewPagerEx viewPagerEx) {
            this.this$0 = viewPagerEx;
        }

        /* synthetic */ PagerObserver(ViewPagerEx viewPagerEx, 1 var2_2) {
            super(viewPagerEx);
        }

        public void onChanged() {
            this.this$0.dataSetChanged();
        }

        public void onInvalidated() {
            this.this$0.dataSetChanged();
        }
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator((ParcelableCompatCreatorCallbacks)new 1());
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel);
            if (classLoader == null) {
                classLoader = this.getClass().getClassLoader();
            }
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader);
            this.loader = classLoader;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString((int)System.identityHashCode((Object)((Object)this))) + " position=" + this.position + "}";
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, n);
        }
    }

    static class ViewPositionComparator
    implements Comparator<View> {
        ViewPositionComparator() {
        }

        public int compare(View view, View view2) {
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams)view2.getLayoutParams();
            if (layoutParams.isDecor != layoutParams2.isDecor) {
                if (layoutParams.isDecor) {
                    return 1;
                }
                return -1;
            }
            return layoutParams.position - layoutParams2.position;
        }
    }

}

