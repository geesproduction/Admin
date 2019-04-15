/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.GradientDrawable$Orientation
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.support.v4.view.ViewCompat
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewDebug
 *  android.view.ViewDebug$ExportedProperty
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 *  android.widget.Scroller
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 */
package me.tangke.slidemenu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import me.tangke.slidemenu.R;
import me.tangke.slidemenu.utils.ScrollDetectors;

public class SlideMenu
extends ViewGroup {
    public static final Interpolator DEFAULT_INTERPOLATOR = new Interpolator(){

        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return 1.0f + f2 * (f2 * (f2 * (f2 * f2)));
        }
    };
    public static final int FLAG_DIRECTION_LEFT = 1;
    public static final int FLAG_DIRECTION_RIGHT = 2;
    private static final int MAX_DURATION = 500;
    public static final int MODE_SLIDE_CONTENT = 2;
    public static final int MODE_SLIDE_WINDOW = 1;
    private static final int POSITION_LEFT = -1;
    private static final int POSITION_MIDDLE = 0;
    private static final int POSITION_RIGHT = 1;
    public static final int STATE_CLOSE = 1;
    public static final int STATE_DRAG = 8;
    public static final int STATE_OPEN_LEFT = 2;
    public static final int STATE_OPEN_MASK = 6;
    public static final int STATE_OPEN_RIGHT = 4;
    public static final int STATE_SCROLL = 16;
    private static int STATUS_BAR_HEIGHT;
    private View mContent;
    private int mContentBoundsLeft;
    private int mContentBoundsRight;
    private Rect mContentHitRect;
    private OnContentTapListener mContentTapListener;
    private volatile int mCurrentContentOffset;
    private int mCurrentContentPosition;
    private int mCurrentState;
    private Rect mEdgeSlideDetectRect;
    private int mEdgeSlideWidth;
    private int mHeight;
    private Interpolator mInterpolator;
    private boolean mIsEdgeSlideEnable;
    private boolean mIsPendingResolveSlideMode;
    private boolean mIsTapInContent;
    private boolean mIsTapInEdgeSlide;
    private float mLastMotionX;
    private float mPressedX;
    private float mPressedY;
    private View mPrimaryMenu;
    @ViewDebug.ExportedProperty
    private Drawable mPrimaryShadowDrawable;
    @ViewDebug.ExportedProperty
    private float mPrimaryShadowWidth;
    private Scroller mScroller;
    private View mSecondaryMenu;
    @ViewDebug.ExportedProperty
    private Drawable mSecondaryShadowDrawable;
    @ViewDebug.ExportedProperty
    private float mSecondaryShadowWidth;
    private int mSlideDirectionFlag;
    private int mSlideMode;
    private OnSlideStateChangeListener mSlideStateChangeListener;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private int mWidth;

    public SlideMenu(Context context) {
        super(context, null);
    }

    public SlideMenu(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.slideMenuStyle);
    }

    /*
     * Enabled aggressive block sorting
     */
    public SlideMenu(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.mSlideMode = 2;
        this.mIsEdgeSlideEnable = true;
        this.mTouchSlop = ViewConfiguration.get((Context)context).getScaledTouchSlop();
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mContentHitRect = new Rect();
        this.mEdgeSlideDetectRect = new Rect();
        STATUS_BAR_HEIGHT = (int)SlideMenu.getStatusBarHeight(context);
        this.setWillNotDraw(false);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SlideMenu, n, 0);
        this.setPrimaryShadowWidth(typedArray.getDimension(R.styleable.SlideMenu_primaryShadowWidth, 30.0f));
        this.setSecondaryShadowWidth(typedArray.getDimension(R.styleable.SlideMenu_secondaryShadowWidth, 30.0f));
        Drawable drawable2 = typedArray.getDrawable(R.styleable.SlideMenu_primaryShadowDrawable);
        if (drawable2 == null) {
            GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
            int[] arrn = new int[]{0, Color.argb((int)99, (int)0, (int)0, (int)0)};
            drawable2 = new GradientDrawable(orientation, arrn);
        }
        this.setPrimaryShadowDrawable(drawable2);
        Drawable drawable3 = typedArray.getDrawable(R.styleable.SlideMenu_secondaryShadowDrawable);
        if (drawable3 == null) {
            GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
            int[] arrn = new int[]{Color.argb((int)99, (int)0, (int)0, (int)0), 0};
            drawable3 = new GradientDrawable(orientation, arrn);
        }
        this.setSecondaryShadowDrawable(drawable3);
        int n2 = typedArray.getResourceId(R.styleable.SlideMenu_interpolator, -1);
        Interpolator interpolator = -1 == n2 ? DEFAULT_INTERPOLATOR : AnimationUtils.loadInterpolator((Context)context, (int)n2);
        this.setInterpolator(interpolator);
        this.mSlideDirectionFlag = typedArray.getInt(R.styleable.SlideMenu_slideDirection, 3);
        this.setEdgeSlideEnable(typedArray.getBoolean(R.styleable.SlideMenu_edgeSlide, false));
        this.setEdgetSlideWidth(typedArray.getDimensionPixelSize(R.styleable.SlideMenu_edgeSlideWidth, 100));
        typedArray.recycle();
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawShadow(Canvas canvas) {
        int n;
        int n2;
        int n3;
        block5 : {
            block4 : {
                if (this.mContent == null) break block4;
                n2 = this.mContent.getLeft();
                n = this.mWidth;
                n3 = this.mHeight;
                if (this.mPrimaryShadowDrawable != null) {
                    this.mPrimaryShadowDrawable.setBounds((int)((float)n2 - this.mPrimaryShadowWidth), 0, n2, n3);
                    this.mPrimaryShadowDrawable.draw(canvas);
                }
                if (this.mSecondaryShadowDrawable != null) break block5;
            }
            return;
        }
        this.mSecondaryShadowDrawable.setBounds(n2 + n, 0, (int)((float)(n + n2) + this.mSecondaryShadowWidth), n3);
        this.mSecondaryShadowDrawable.draw(canvas);
    }

    public static float getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int n = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (n != 0) {
            return resources.getDimension(n);
        }
        return 0.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void invalidateMenuState() {
        int n = this.mCurrentContentOffset < 0 ? -1 : (this.mCurrentContentOffset == 0 ? 0 : 1);
        this.mCurrentContentPosition = n;
        switch (this.mCurrentContentPosition) {
            default: {
                return;
            }
            case -1: {
                this.invalidateViewVisibility(this.mPrimaryMenu, 4);
                this.invalidateViewVisibility(this.mSecondaryMenu, 0);
                return;
            }
            case 0: {
                this.invalidateViewVisibility(this.mPrimaryMenu, 4);
                this.invalidateViewVisibility(this.mSecondaryMenu, 4);
                return;
            }
            case 1: 
        }
        this.invalidateViewVisibility(this.mPrimaryMenu, 0);
        this.invalidateViewVisibility(this.mSecondaryMenu, 4);
    }

    private void invalidateViewVisibility(View view, int n) {
        if (view != null && view.getVisibility() != n) {
            view.setVisibility(n);
        }
    }

    private boolean isAttacthedInContentView() {
        View view = (View)this.getParent();
        return view != null && 16908290 == view.getId() && 2 == this.mSlideMode && this.getRootView() == view && 1 == this.mSlideMode;
    }

    private boolean isTapInContent(float f, float f2) {
        View view = this.mContent;
        if (view != null) {
            view.getHitRect(this.mContentHitRect);
            return this.mContentHitRect.contains((int)f, (int)f2);
        }
        return false;
    }

    private boolean isTapInEdgeSlide(float f, float f2) {
        Rect rect = this.mEdgeSlideDetectRect;
        View view = this.mPrimaryMenu;
        boolean bl = false;
        if (view != null) {
            this.getHitRect(rect);
            rect.right = this.mEdgeSlideWidth;
            bl = false | rect.contains((int)f, (int)f2);
        }
        if (this.mSecondaryMenu != null) {
            this.getHitRect(rect);
            rect.left = rect.right - this.mEdgeSlideWidth;
            bl |= rect.contains((int)f, (int)f2);
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void removeViewFromParent(View view) {
        ViewGroup viewGroup;
        if (view == null || (viewGroup = (ViewGroup)view.getParent()) == null) {
            return;
        }
        viewGroup.removeView(view);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setCurrentOffset(int n) {
        int n2;
        int n3 = this.mSlideDirectionFlag;
        int n4 = (n3 & 2) == 2 ? this.mContentBoundsRight : 0;
        int n5 = n3 & 1;
        int n6 = 0;
        if (n5 == 1) {
            n6 = this.mContentBoundsLeft;
        }
        this.mCurrentContentOffset = n2 = Math.min((int)n4, (int)Math.max((int)n, (int)n6));
        if (this.mSlideStateChangeListener != null) {
            float f;
            if (n2 > 0) {
                f = 1.0f * (float)n2 / (float)this.mContentBoundsRight;
            } else {
                f = 0.0f;
                if (n2 < 0) {
                    f = 1.0f * (float)(-n2) / (float)this.mContentBoundsLeft;
                }
            }
            this.mSlideStateChangeListener.onSlideOffsetChange(f);
        }
        SlideMenu.super.invalidateMenuState();
        this.invalidate();
        this.requestLayout();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof LayoutParams)) {
            throw new IllegalArgumentException("The parameter params must a instance of " + LayoutParams.class.getName());
        }
        if (layoutParams == null) {
            return;
        }
        switch (((LayoutParams)layoutParams).role) {
            default: {
                return;
            }
            case 0: {
                this.removeView(this.mContent);
                this.mContent = view;
                break;
            }
            case 1: {
                this.removeView(this.mPrimaryMenu);
                this.mPrimaryMenu = view;
                break;
            }
            case 2: {
                this.removeView(this.mSecondaryMenu);
                this.mSecondaryMenu = view;
            }
        }
        SlideMenu.super.invalidateMenuState();
        super.addView(view, n, layoutParams);
    }

    protected final boolean canScrollHorizontally(View view, int n, int n2, int n3) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n4 = view.getScrollX();
            int n5 = view.getScrollY();
            int n6 = viewGroup.getChildCount();
            for (int i = 0; i < n6; ++i) {
                View view2 = viewGroup.getChildAt(i);
                int n7 = view2.getLeft();
                int n8 = view2.getTop();
                if (n2 + n4 < n7 || n2 + n4 >= view2.getRight() || n3 + n5 < n8 || n3 + n5 >= view2.getBottom() || view2.getVisibility() != 0 || !ScrollDetectors.canScrollHorizontal(view2, n) && !this.canScrollHorizontally(view2, n, n2 + n4 - n7, n3 + n5 - n8)) continue;
                return true;
            }
        }
        return ViewCompat.canScrollHorizontally((View)view, (int)(-n));
    }

    protected final boolean canScrollVertically(View view, int n, int n2, int n3) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n4 = view.getScrollX();
            int n5 = view.getScrollY();
            int n6 = viewGroup.getChildCount();
            for (int i = 0; i < n6; ++i) {
                View view2 = viewGroup.getChildAt(i);
                int n7 = view2.getLeft();
                int n8 = view2.getTop();
                if (n2 + n4 < n7 || n2 + n4 >= view2.getRight() || n3 + n5 < n8 || n3 + n5 >= view2.getBottom() || view2.getVisibility() != 0 || !ScrollDetectors.canScrollVertical(view2, n) && !this.canScrollVertically(view2, n, n2 + n4 - n7, n3 + n5 - n8)) continue;
                return true;
            }
        }
        return ViewCompat.canScrollVertically((View)view, (int)(-n));
    }

    public void close(boolean bl) {
        if (1 == this.mCurrentState) {
            return;
        }
        if (bl) {
            this.smoothScrollContentTo(0);
            return;
        }
        this.mScroller.abortAnimation();
        SlideMenu.super.setCurrentOffset(0);
        this.setCurrentState(1);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void computeScroll() {
        block3 : {
            block2 : {
                if (16 != this.mCurrentState && !this.isOpen()) break block2;
                if (!this.mScroller.computeScrollOffset()) break block3;
                this.setCurrentOffset(this.mScroller.getCurrX());
            }
            return;
        }
        int n = this.mCurrentContentOffset == 0 ? 1 : (this.mCurrentContentOffset > 0 ? 2 : 4);
        this.setCurrentState(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (1 != keyEvent.getAction()) return super.dispatchKeyEvent(keyEvent);
        boolean bl = this.isOpen();
        switch (keyEvent.getKeyCode()) {
            case 4: {
                if (!bl) return super.dispatchKeyEvent(keyEvent);
                {
                    this.close(true);
                    return true;
                }
            }
            case 21: {
                if (2 == this.mCurrentState) {
                    this.close(true);
                    return true;
                }
                if (bl) return super.dispatchKeyEvent(keyEvent);
                {
                    this.open(true, true);
                    return true;
                }
            }
            default: {
                return super.dispatchKeyEvent(keyEvent);
            }
            case 22: 
        }
        if (4 == this.mCurrentState) {
            this.close(true);
            return true;
        }
        if (bl) return super.dispatchKeyEvent(keyEvent);
        {
            this.open(false, true);
            return true;
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (1 == motionEvent.getAction()) {
            this.requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    protected void drag(float f, float f2) {
        this.mCurrentContentOffset += (int)(f2 - f);
        SlideMenu.super.setCurrentOffset(this.mCurrentContentOffset);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        SlideMenu.super.drawShadow(canvas);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void endDrag(float f, float f2) {
        int n = this.mCurrentContentPosition;
        boolean bl = Math.abs((float)f2) > 400.0f;
        switch (n) {
            case -1: {
                if (f2 < 0.0f && bl || f2 >= 0.0f && !bl) {
                    this.smoothScrollContentTo(this.mContentBoundsLeft, f2);
                    return;
                }
                if (!(f2 > 0.0f && bl) && (!(f2 <= 0.0f) || bl)) return;
                {
                    this.smoothScrollContentTo(0, f2);
                    return;
                }
            }
            default: {
                return;
            }
            case 0: {
                this.setCurrentState(1);
                return;
            }
            case 1: {
                if (f2 > 0.0f && bl || f2 <= 0.0f && !bl) {
                    this.smoothScrollContentTo(this.mContentBoundsRight, f2);
                    return;
                }
                if (!(f2 < 0.0f && bl) && (!(f2 >= 0.0f) || bl)) return;
                this.smoothScrollContentTo(0, f2);
                return;
            }
        }
    }

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public int getCurrentState() {
        return this.mCurrentState;
    }

    protected Drawable getDefaultContentBackground(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16842836, typedValue, true);
        return context.getResources().getDrawable(typedValue.resourceId);
    }

    public float getEdgeSlideWidth() {
        return this.mEdgeSlideWidth;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public OnContentTapListener getOnContentTapListener() {
        return this.mContentTapListener;
    }

    public OnSlideStateChangeListener getOnSlideStateChangeListener() {
        return this.mSlideStateChangeListener;
    }

    public View getPrimaryMenu() {
        return this.mPrimaryMenu;
    }

    public Drawable getPrimaryShadowDrawable() {
        return this.mPrimaryShadowDrawable;
    }

    public float getPrimaryShadowWidth() {
        return this.mPrimaryShadowWidth;
    }

    public View getSecondaryMenu() {
        return this.mSecondaryMenu;
    }

    public Drawable getSecondaryShadowDrawable() {
        return this.mSecondaryShadowDrawable;
    }

    public float getSecondaryShadowWidth() {
        return this.mSecondaryShadowWidth;
    }

    public int getSlideDirection() {
        return this.mSlideDirectionFlag;
    }

    public int getSlideMode() {
        return this.mSlideMode;
    }

    public boolean isEdgeSlideEnable() {
        return this.mIsEdgeSlideEnable;
    }

    public boolean isOpen() {
        return (6 & this.mCurrentState) != 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        float f = motionEvent.getX();
        float f2 = motionEvent.getY();
        int n = this.mCurrentState;
        if (8 == n || 16 == n) return true;
        switch (motionEvent.getAction()) {
            default: {
                return false;
            }
            case 0: {
                this.mLastMotionX = f;
                this.mPressedX = f;
                this.mPressedY = f2;
                this.mIsTapInContent = SlideMenu.super.isTapInContent(f, f2);
                this.mIsTapInEdgeSlide = SlideMenu.super.isTapInEdgeSlide(f, f2);
                if (!this.isOpen() || !this.mIsTapInContent) return false;
                return true;
            }
            case 2: {
                float f3 = f - this.mPressedX;
                float f4 = f2 - this.mPressedY;
                if (this.mIsEdgeSlideEnable && !this.mIsTapInEdgeSlide && this.mCurrentState == 1) {
                    return false;
                }
                if (Math.abs((float)f4) >= (float)this.mTouchSlop && this.mIsTapInContent && this.canScrollVertically((View)this, (int)f4, (int)f, (int)f2)) {
                    this.requestDisallowInterceptTouchEvent(true);
                    return false;
                }
                if (!(Math.abs((float)f3) >= (float)this.mTouchSlop) || !this.mIsTapInContent || this.canScrollHorizontally((View)this, (int)f3, (int)f, (int)f2)) return false;
                this.setCurrentState(8);
                return true;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getChildCount();
        int n6 = this.getPaddingLeft();
        int n7 = this.getPaddingRight();
        int n8 = this.getPaddingTop();
        int n9 = this.mSlideMode == 1 ? STATUS_BAR_HEIGHT : 0;
        int n10 = 0;
        while (n10 < n5) {
            View view = this.getChildAt(n10);
            int n11 = view.getMeasuredWidth();
            int n12 = view.getMeasuredHeight();
            switch (((LayoutParams)view.getLayoutParams()).role) {
                case 0: {
                    view.bringToFront();
                    view.layout(n6 + this.mCurrentContentOffset, n8, n6 + n11 + this.mCurrentContentOffset, n8 + n12);
                    break;
                }
                case 1: {
                    this.mContentBoundsRight = n11;
                    view.layout(n6, n9 + n8, n6 + n11, n12 + (n9 + n8));
                    break;
                }
                case 2: {
                    this.mContentBoundsLeft = -n11;
                    view.layout(n3 - n - n7 - n11, n9 + n8, n3 - n - n7, n12 + (n9 + n8));
                    break;
                }
            }
            ++n10;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3 = this.getChildCount();
        int n4 = this.mSlideMode;
        int n5 = STATUS_BAR_HEIGHT;
        int n6 = View.MeasureSpec.getSize((int)n2);
        int n7 = View.MeasureSpec.getMode((int)n2);
        boolean bl = false;
        int n8 = 0;
        int n9 = 0;
        for (int i = 0; i < n3; ++i) {
            View view = this.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            boolean bl2 = 1073741824 != n7 && -1 == layoutParams.height;
            bl |= bl2;
            switch (layoutParams.role) {
                case 0: {
                    this.measureChildWithMargins(view, n, 0, n2, 0);
                }
                default: {
                    break;
                }
                case 1: 
                case 2: {
                    int n10 = n4 == 1 ? View.MeasureSpec.makeMeasureSpec((int)(n6 - n5), (int)View.MeasureSpec.getMode((int)n2)) : n2;
                    this.measureChildWithMargins(view, n, 0, n10, 0);
                }
            }
            n8 = Math.max((int)n8, (int)view.getMeasuredWidth());
            n9 = Math.max((int)n9, (int)view.getMeasuredHeight());
        }
        int n11 = n8 + (this.getPaddingLeft() + this.getPaddingRight());
        int n12 = n9 + (this.getPaddingTop() + this.getPaddingBottom());
        this.setMeasuredDimension(SlideMenu.resolveSize((int)n11, (int)n), SlideMenu.resolveSize((int)n12, (int)n2));
        if (bl) {
            for (int i = 0; i < n3; ++i) {
                View view = this.getChildAt(i);
                if (8 == view.getVisibility() || -1 != view.getLayoutParams().height) continue;
                this.measureChildWithMargins(view, n, 0, View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)1073741824), 0);
            }
        }
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState)parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mPrimaryShadowWidth = savedState.primaryShadowWidth;
        this.mSecondaryShadowWidth = savedState.secondaryShadaryWidth;
        this.mSlideDirectionFlag = savedState.slideDirectionFlag;
        this.setSlideMode(savedState.slideMode);
        this.mCurrentState = savedState.currentState;
        this.mCurrentContentOffset = savedState.currentContentOffset;
        SlideMenu.super.invalidateMenuState();
        this.requestLayout();
        this.invalidate();
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.primaryShadowWidth = this.mPrimaryShadowWidth;
        savedState.secondaryShadaryWidth = this.mSecondaryShadowWidth;
        savedState.slideDirectionFlag = this.mSlideDirectionFlag;
        savedState.slideMode = this.mSlideMode;
        savedState.currentState = this.mCurrentState;
        savedState.currentContentOffset = this.mCurrentContentOffset;
        return savedState;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.mWidth = n;
        this.mHeight = n2;
        if (this.mIsPendingResolveSlideMode) {
            this.resolveSlideMode();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onTouchEvent(MotionEvent var1) {
        var2_2 = var1.getX();
        var3_3 = var1.getY();
        var4_4 = this.mCurrentState;
        var5_5 = var1.getAction();
        switch (var5_5) {
            case 0: {
                this.mLastMotionX = var2_2;
                this.mPressedX = var2_2;
                this.mPressedY = var3_3;
                this.mIsTapInContent = SlideMenu.super.isTapInContent(var2_2, var3_3);
                this.mIsTapInEdgeSlide = SlideMenu.super.isTapInEdgeSlide(var2_2, var3_3);
                if (this.mIsTapInContent == false) return true;
                this.mScroller.abortAnimation();
                ** break;
            }
            case 2: {
                this.mVelocityTracker.addMovement(var1);
                if (this.mIsEdgeSlideEnable && !this.mIsTapInEdgeSlide) {
                    var7_7 = this.mCurrentState;
                    var6_6 = false;
                    if (var7_7 == 1) return var6_6;
                }
                if (Math.abs((float)(var2_2 - this.mPressedX)) >= (float)this.mTouchSlop && this.mIsTapInContent && var4_4 != 8) {
                    this.getParent().requestDisallowInterceptTouchEvent(true);
                    this.setCurrentState(8);
                }
                if (8 != var4_4) {
                    this.mLastMotionX = var2_2;
                    return false;
                }
                this.drag(this.mLastMotionX, var2_2);
                this.mLastMotionX = var2_2;
            }
lbl29: // 3 sources:
            default: {
                return true;
            }
            case 1: 
            case 3: 
            case 4: 
        }
        if (8 == var4_4) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            this.endDrag(var2_2, this.mVelocityTracker.getXVelocity());
        } else if (this.mIsTapInContent && 1 == var5_5) {
            this.performContentTap();
        }
        this.mVelocityTracker.clear();
        this.getParent().requestDisallowInterceptTouchEvent(false);
        this.mIsTapInEdgeSlide = false;
        this.mIsTapInContent = false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void open(boolean bl, boolean bl2) {
        if (this.isOpen()) {
            return;
        }
        int n = bl ? this.mContentBoundsLeft : this.mContentBoundsRight;
        if (bl2) {
            this.smoothScrollContentTo(n);
            return;
        }
        this.mScroller.abortAnimation();
        SlideMenu.super.setCurrentOffset(n);
        int n2 = bl ? 2 : 4;
        this.setCurrentState(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void performContentTap() {
        if (this.isOpen()) {
            this.smoothScrollContentTo(0);
            return;
        } else {
            if (this.mContentTapListener == null) return;
            {
                this.mContentTapListener.onContentTap(this);
                return;
            }
        }
    }

    protected void resolveSlideMode() {
        ViewGroup viewGroup = (ViewGroup)this.getRootView();
        ViewGroup viewGroup2 = (ViewGroup)viewGroup.findViewById(16908290);
        View view = this.mContent;
        if (viewGroup == null || view == null || this.getChildCount() == 0) {
            return;
        }
        TypedValue typedValue = new TypedValue();
        this.getContext().getTheme().resolveAttribute(16842836, typedValue, true);
        switch (this.mSlideMode) {
            default: {
                return;
            }
            case 1: {
                SlideMenu.removeViewFromParent((View)this);
                LayoutParams layoutParams = new LayoutParams(view.getLayoutParams());
                SlideMenu.removeViewFromParent(view);
                viewGroup2.addView(view);
                View view2 = viewGroup.getChildAt(0);
                view2.setBackgroundResource(0);
                SlideMenu.removeViewFromParent(view2);
                this.addView(view2, (ViewGroup.LayoutParams)layoutParams);
                viewGroup.addView((View)this);
                this.setBackgroundResource(typedValue.resourceId);
                return;
            }
            case 2: 
        }
        this.setBackgroundResource(0);
        SlideMenu.removeViewFromParent((View)this);
        View view3 = viewGroup2.getChildAt(0);
        View view4 = this.mContent;
        LayoutParams layoutParams = (LayoutParams)view4.getLayoutParams();
        SlideMenu.removeViewFromParent(view3);
        SlideMenu.removeViewFromParent(view4);
        view4.setBackgroundResource(typedValue.resourceId);
        viewGroup.addView(view4);
        viewGroup2.addView((View)this);
        this.addView(view3, (ViewGroup.LayoutParams)layoutParams);
    }

    protected void setCurrentState(int n) {
        if (this.mSlideStateChangeListener != null && n != this.mCurrentState) {
            this.mSlideStateChangeListener.onSlideStateChange(n);
        }
        this.mCurrentState = n;
    }

    public void setEdgeSlideEnable(boolean bl) {
        this.mIsEdgeSlideEnable = bl;
    }

    public void setEdgetSlideWidth(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Edge slide width must above 0");
        }
        this.mEdgeSlideWidth = n;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
        this.mScroller = new Scroller(this.getContext(), interpolator);
    }

    public void setOnContentTapListener(OnContentTapListener onContentTapListener) {
        this.mContentTapListener = onContentTapListener;
    }

    public void setOnSlideStateChangeListener(OnSlideStateChangeListener onSlideStateChangeListener) {
        this.mSlideStateChangeListener = onSlideStateChangeListener;
    }

    public void setPrimaryShadowDrawable(Drawable drawable2) {
        this.mPrimaryShadowDrawable = drawable2;
    }

    public void setPrimaryShadowWidth(float f) {
        this.mPrimaryShadowWidth = f;
        this.invalidate();
    }

    public void setSecondaryShadowDrawable(Drawable drawable2) {
        this.mSecondaryShadowDrawable = drawable2;
    }

    public void setSecondaryShadowWidth(float f) {
        this.mSecondaryShadowWidth = f;
        this.invalidate();
    }

    public void setSlideDirection(int n) {
        this.mSlideDirectionFlag = n;
    }

    public void setSlideMode(int n) {
        if (SlideMenu.super.isAttacthedInContentView()) {
            throw new IllegalStateException("SlidingMenu must be the root of layout");
        }
        if (this.mSlideMode == n) {
            return;
        }
        this.mSlideMode = n;
        if (this.getChildCount() == 0) {
            this.mIsPendingResolveSlideMode = true;
            return;
        }
        this.resolveSlideMode();
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public void smoothScrollContentTo(int n) {
        this.smoothScrollContentTo(n, 0.0f);
    }

    public void smoothScrollContentTo(int n, float f) {
        this.setCurrentState(16);
        int n2 = n - this.mCurrentContentOffset;
        float f2 = Math.abs((float)f);
        int n3 = 400;
        if (f2 > 0.0f) {
            n3 = 3 * Math.round((float)(1000.0f * Math.abs((float)((float)n2 / f2))));
        }
        int n4 = Math.min((int)n3, (int)500);
        this.mScroller.abortAnimation();
        this.mScroller.startScroll(this.mCurrentContentOffset, 0, n2, 0, n4);
        this.invalidate();
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public static final int ROLE_CONTENT = 0;
        public static final int ROLE_PRIMARY_MENU = 1;
        public static final int ROLE_SECONDARY_MENU = 2;
        public int role;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(int n, int n2, int n3) {
            super(n, n2);
            this.role = n3;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SlideMenu_Layout, 0, 0);
            int n = typedArray.getIndexCount();
            for (int i = 0; i < n; ++i) {
                int n2 = typedArray.getIndex(i);
                if (R.styleable.SlideMenu_Layout_layout_role != n2) continue;
                this.role = typedArray.getInt(R.styleable.SlideMenu_Layout_layout_role, -1);
            }
            switch (this.role) {
                default: {
                    throw new IllegalArgumentException("You must specified a layout_role for this view");
                }
                case 0: {
                    this.width = -1;
                    this.height = -1;
                }
                case 1: 
                case 2: 
            }
            typedArray.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            if (layoutParams instanceof LayoutParams) {
                this.role = ((LayoutParams)layoutParams).role;
            }
        }
    }

    public static interface OnContentTapListener {
        public void onContentTap(SlideMenu var1);
    }

    public static interface OnSlideStateChangeListener {
        public void onSlideOffsetChange(float var1);

        public void onSlideStateChange(int var1);
    }

    public static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        public int currentContentOffset;
        public int currentState;
        public float primaryShadowWidth;
        public float secondaryShadaryWidth;
        public int slideDirectionFlag;
        public int slideMode;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.primaryShadowWidth = parcel.readFloat();
            this.secondaryShadaryWidth = parcel.readFloat();
            this.slideDirectionFlag = parcel.readInt();
            this.slideMode = parcel.readInt();
            this.currentState = parcel.readInt();
            this.currentContentOffset = parcel.readInt();
        }

        /* synthetic */ SavedState(Parcel parcel, me.tangke.slidemenu.SlideMenu$1 var2_2) {
            super(parcel);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeFloat(this.primaryShadowWidth);
            parcel.writeFloat(this.secondaryShadaryWidth);
            parcel.writeInt(this.slideDirectionFlag);
            parcel.writeInt(this.slideMode);
            parcel.writeInt(this.currentState);
            parcel.writeInt(this.currentContentOffset);
        }

    }

}

