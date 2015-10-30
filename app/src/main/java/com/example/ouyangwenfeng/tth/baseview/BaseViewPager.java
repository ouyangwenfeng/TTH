package com.example.ouyangwenfeng.tth.baseview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by oywf on 15/10/29.
 * <p/>
 * ViewPager 基类，实现设置不滑动
 */
public class BaseViewPager extends ViewPager {

    private boolean enabled = true;

    public BaseViewPager(Context context) {
        super(context);
    }

    public BaseViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (enabled) {
            try {
                return super.onTouchEvent(event);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (enabled) {
            try {
                return super.onInterceptTouchEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 设置viewpager滑动是否有效
     *
     * @param enabled
     */
    public void setViewPagerSlidEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

