package com.example.ouyangwenfeng.tth.ui.pageindicator;

/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2011 Jake Wharton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ouyangwenfeng.tth.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * This widget implements the dynamic action bar tab behavior that can change
 * across different configurations or circumstances.
 */
public class NewTabPageIndicator extends HorizontalScrollView implements PageIndicator {

    /**
     * Title text used when no title is provided by the adapter.
     */
    private static final CharSequence EMPTY_TITLE = "";

    /**
     * Interface for a callback when the selected tab has been reselected.
     */
    public interface OnTabReselectedListener {
        /**
         * Callback when the selected tab has been reselected.
         *
         * @param position Position of the current center item.
         */
        void onTabReselected(int position);

        void onTabItemClicked(int position);
    }

    private Runnable mTabSelector;

    private final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
            TabView tabView = (TabView) view;
            final int oldSelected = mViewPager.getCurrentItem();
            final int newSelected = tabView.getIndex();
            mViewPager.setCurrentItem(newSelected, false);
            if (oldSelected == newSelected && mTabReselectedListener != null) {
                mTabReselectedListener.onTabReselected(newSelected);
            }
            if (oldSelected != newSelected && mTabReselectedListener != null) {
                mTabReselectedListener.onTabItemClicked(newSelected);
            }
        }
    };

    private final IcsLinearLayout mTabLayout;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;

    private int mMaxTabWidth;
    private int mSelectedTabIndex;
    private boolean changeSize = false; //默认开启字体可改变
    private int fouseColor;   //默认焦点颜色
    private int defaultColor;  //默认失焦颜色
    private OnTabReselectedListener mTabReselectedListener;

    public NewTabPageIndicator(Context context) {
        this(context, null);
    }

    public NewTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
            this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }

        setHorizontalScrollBarEnabled(false);

        mTabLayout = new IcsLinearLayout(context,
                R.attr.vpiTabPageIndicatorStyle);
        addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT,
                MATCH_PARENT));
    }

    public void setOnTabReselectedListener(OnTabReselectedListener listener) {
        mTabReselectedListener = listener;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);
        final int childCount = mTabLayout.getChildCount();
        if (childCount > 1
                && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
            if (childCount > 2) {
                mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
            } else {
                mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
            }
        } else {
            mMaxTabWidth = -1;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft()
                        - (getWidth() - tabView.getWidth()) / 2;
                smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

    private void addTab(int index, CharSequence text, int iconResId) {
        final TabView tabView = new TabView(getContext());
        tabView.mIndex = index;
        tabView.setFocusable(true);
        tabView.setOnClickListener(mTabClickListener);
        tabView.setText(text);

        if (iconResId != 0) {
            tabView.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0);
        }

        mTabLayout.addView(tabView, new LinearLayout.LayoutParams(WRAP_CONTENT,
                MATCH_PARENT, 1));
        /** 更改字体大小，WRAP_CONTENT会根据具体大小调节，MATCH_PARENT直接分开两个
         mTabLayout.addView(tabView, new LinearLayout.LayoutParams(WRAP_CONTENT,
         MATCH_PARENT, 1));*
         */
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (mListener != null) {
            mListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        int pos = mSelectedTabIndex;
        setCurrentItem(arg0);
        //失去焦点变小动画
        if (changeSize) {
            View posView = mTabLayout.getChildAt(pos);
            if (posView instanceof TextView) {
                TextView posTextView = (TextView) posView;
                posTextView.setTextColor(defaultColor);
                Animation animation = AnimationUtils.loadAnimation(getContext(),
                        R.anim.tab_text_scale_small);
                animation.setFillAfter(true);
                posTextView.startAnimation(animation);
            }
        }
        //控制启动字体大小开关<--beg-->
        //<--end-->
        if (mListener != null) {
            mListener.onPageSelected(arg0);
        }
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }
        final PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException(
                    "ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        view.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        IconPagerAdapter iconAdapter = null;
        if (adapter instanceof IconPagerAdapter) {
            iconAdapter = (IconPagerAdapter) adapter;
        }
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            if (title == null) {
                title = EMPTY_TITLE;
            }
            int iconResId = 0;
            if (iconAdapter != null) {
                iconResId = iconAdapter.getIconResId(i);
            }
            addTab(i, title, iconResId);
        }
        if (mSelectedTabIndex > count) {
            mSelectedTabIndex = count - 1;
        }
        setCurrentItem(mSelectedTabIndex);
        requestLayout();
    }

    public void notifyDataSetChanged(int position) {
        mSelectedTabIndex = position;
        notifyDataSetChanged();
    }


    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }

        mSelectedTabIndex = item;

        mViewPager.setCurrentItem(item, false);
        final int tabCount = mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            final View child = mTabLayout.getChildAt(i);
            final boolean isSelected = (i == item);
            //第一次进来的时候需要设置默认颜色跟字体
            if (changeSize) {
                if (child instanceof TextView) {
                    if (i == item) {
                        TextView fousetextView = (TextView) child;
                        fousetextView.setTextColor(fouseColor);
                        Animation animation = AnimationUtils.loadAnimation(getContext(),
                                R.anim.tab_text_scale_big);
                        animation.setFillAfter(true);
                        fousetextView.startAnimation(animation);
                    } else {
                        ((TextView) child).setTextColor(defaultColor);
                    }
                }
            }
            child.setSelected(isSelected);
            if (isSelected) {
                animateToTab(item);
            }
        }
    }

    //是否开启更改字体大小,更改字体大小
    public void setTextSizeIsChange(boolean change, int selectColor, int unselectColor) {
        changeSize = change;
        fouseColor = selectColor;
        defaultColor = unselectColor;
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }

    private class TabView extends TextView {
        private int mIndex;

        public TabView(Context context) {
            super(context, null, R.attr.vpiTabPageIndicatorStyle);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            // Re-measure if we went beyond our maximum size.
            if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth,
                        MeasureSpec.EXACTLY), heightMeasureSpec);
            }
        }

        public int getIndex() {
            return mIndex;
        }
    }


}
