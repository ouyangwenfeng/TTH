package com.example.ouyangwenfeng.tth.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import com.example.ouyangwenfeng.tth.baseview.BaseView;
import java.util.List;


/**
 * Created by oywf on 15/10/29.
 *
 * view+pagerview形式
 */
public class BaseViewPageAdapter extends PagerAdapter {

    private List<BaseView> list;
    private String[] titleList;

    public BaseViewPageAdapter(List<BaseView> list, String[] titleList) {
        this.list = list;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    /**
     * item被销毁的时候
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        BaseView view = list.get(position);
        vp.removeView(view.getView());
    }

    /**
     * item被生成的时候
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewPager vp = (ViewPager) container;
        BaseView view = list.get(position);
        vp.addView(view.getView());
        return view.getView();
    }
}

