/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.support.v4.view.PagerAdapter
 *  android.support.v4.view.ViewPager
 *  android.support.v4.widget.SwipeRefreshLayout
 *  android.view.View
 *  android.webkit.WebView
 *  android.widget.HorizontalScrollView
 *  java.lang.Class
 *  java.lang.IllegalAccessException
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.NoSuchMethodException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.InvocationTargetException
 *  java.lang.reflect.Method
 *  java.util.WeakHashMap
 */
package me.tangke.slidemenu.utils;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;
import me.tangke.slidemenu.utils.ScrollDetectorFactory;

public class ScrollDetectors {
    private static final WeakHashMap<Class<? extends View>, ScrollDetector> IMPLES = new WeakHashMap();
    private static ScrollDetectorFactory mFactory;

    public static boolean canScrollHorizontal(View view, int n) {
        ScrollDetector scrollDetector = ScrollDetectors.getImplements(view);
        if (scrollDetector == null) {
            return false;
        }
        return scrollDetector.canScrollHorizontal(view, n);
    }

    public static boolean canScrollVertical(View view, int n) {
        ScrollDetector scrollDetector = ScrollDetectors.getImplements(view);
        if (scrollDetector == null) {
            return false;
        }
        return scrollDetector.canScrollVertical(view, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static ScrollDetector getImplements(View view) {
        void var3_5;
        Class class_ = view.getClass();
        ScrollDetector scrollDetector = (ScrollDetector)IMPLES.get((Object)class_);
        if (scrollDetector != null) {
            return scrollDetector;
        }
        if (view instanceof ViewPager) {
            ViewPagerScrollDetector viewPagerScrollDetector = new ViewPagerScrollDetector(null);
        } else if (view instanceof HorizontalScrollView) {
            HorizontalScrollViewScrollDetector horizontalScrollViewScrollDetector = new HorizontalScrollViewScrollDetector(null);
        } else if (view instanceof WebView) {
            WebViewScrollDetector webViewScrollDetector = new WebViewScrollDetector(null);
        } else if (view instanceof SwipeRefreshLayout) {
            SwipeRefreshLayoutScrollDetector swipeRefreshLayoutScrollDetector = new SwipeRefreshLayoutScrollDetector(null);
        } else {
            ScrollDetectorFactory scrollDetectorFactory = mFactory;
            ScrollDetector scrollDetector2 = null;
            if (scrollDetectorFactory == null) return scrollDetector2;
            ScrollDetector scrollDetector3 = mFactory.newScrollDetector(view);
        }
        IMPLES.put((Object)class_, (Object)var3_5);
        return var3_5;
    }

    public static void setScrollDetectorFactory(ScrollDetectorFactory scrollDetectorFactory) {
        mFactory = scrollDetectorFactory;
    }

    private static class HorizontalScrollViewScrollDetector
    implements ScrollDetector {
        private HorizontalScrollViewScrollDetector() {
        }

        /* synthetic */ HorizontalScrollViewScrollDetector(1 var1) {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean canScrollHorizontal(View view, int n) {
            HorizontalScrollView horizontalScrollView = (HorizontalScrollView)view;
            int n2 = horizontalScrollView.getScrollX();
            return horizontalScrollView.getChildCount() != 0 && (n < 0 && n2 < horizontalScrollView.getChildAt(0).getWidth() - horizontalScrollView.getWidth() || n > 0 && n2 > 0);
        }

        @Override
        public boolean canScrollVertical(View view, int n) {
            return false;
        }
    }

    public static interface ScrollDetector {
        public boolean canScrollHorizontal(View var1, int var2);

        public boolean canScrollVertical(View var1, int var2);
    }

    private static class SwipeRefreshLayoutScrollDetector
    implements ScrollDetector {
        private SwipeRefreshLayoutScrollDetector() {
        }

        /* synthetic */ SwipeRefreshLayoutScrollDetector(1 var1) {
        }

        @Override
        public boolean canScrollHorizontal(View view, int n) {
            return false;
        }

        @Override
        public boolean canScrollVertical(View view, int n) {
            return true;
        }
    }

    private static class ViewPagerScrollDetector
    implements ScrollDetector {
        private ViewPagerScrollDetector() {
        }

        /* synthetic */ ViewPagerScrollDetector(1 var1) {
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean canScrollHorizontal(View view, int n) {
            block3 : {
                block2 : {
                    ViewPager viewPager = (ViewPager)view;
                    PagerAdapter pagerAdapter = viewPager.getAdapter();
                    if (pagerAdapter == null || pagerAdapter.getCount() == 0) break block2;
                    int n2 = viewPager.getCurrentItem();
                    if (n < 0 && n2 < -1 + pagerAdapter.getCount() || n > 0 && n2 > 0) break block3;
                }
                return false;
            }
            return true;
        }

        @Override
        public boolean canScrollVertical(View view, int n) {
            return false;
        }
    }

    private static class WebViewScrollDetector
    implements ScrollDetector {
        private WebViewScrollDetector() {
        }

        /* synthetic */ WebViewScrollDetector(1 var1) {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean canScrollHorizontal(View view, int n) {
            int n2;
            int n3;
            int n4;
            block8 : {
                Method method = WebView.class.getDeclaredMethod("computeHorizontalScrollOffset", new Class[0]);
                Method method2 = WebView.class.getDeclaredMethod("computeHorizontalScrollRange", new Class[0]);
                method.setAccessible(true);
                method2.setAccessible(true);
                n3 = (Integer)method.invoke((Object)view, new Object[0]);
                n4 = (Integer)method2.invoke((Object)view, new Object[0]);
                if (n <= 0) break block8;
                if (view.getScrollX() > 0) return true;
            }
            if (n >= 0) return false;
            try {
                n2 = view.getWidth();
            }
            catch (NoSuchMethodException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
                do {
                    return false;
                    break;
                } while (true);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                illegalArgumentException.printStackTrace();
                return false;
            }
            catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
                return false;
            }
            catch (InvocationTargetException invocationTargetException) {
                invocationTargetException.printStackTrace();
                return false;
            }
            if (n3 >= n4 - n2) return false;
            return true;
        }

        @Override
        public boolean canScrollVertical(View view, int n) {
            return false;
        }
    }

}

