package com.example.ouyangwenfeng.tth.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ouyangwenfeng.tth.R;
import com.example.ouyangwenfeng.tth.baseview.BaseView;
import com.example.ouyangwenfeng.tth.fragment.views.ChildViewOne;
import com.example.ouyangwenfeng.tth.fragment.views.ChildViewThree;
import com.example.ouyangwenfeng.tth.fragment.views.ChildViewTwo;
import com.example.ouyangwenfeng.tth.ui.pageindicator.NewTabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oywf on 15/10/29.
 * <p/>
 * 集成带tab滑动的嵌套fragment
 * 通过view方式来实现framgent的二级嵌套
 */
public class FragmentOneMain extends Fragment {

    //当前fragment的id标识
    private int id;
    //当前fragment背景色
    private int color;
    //当前主view页面
    protected View mView;
    //当前页面项
    private int currentPosition;


    //头部tab滑动控件
    private String[] titleList;//标题列表
    private NewTabPageIndicator tabPageIndicator;
    private ViewPager mViewPager;
    private BaseView baseView;
    private List<BaseView> baseViewList;
    private BaseViewPageAdapter adapter;


    public static FragmentOneMain getInstance(int id, int color) {
        FragmentOneMain fragment = new FragmentOneMain();
        fragment.id = id;
        fragment.color = color;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //初始化布局
        initView(inflater, container);
        //设置监听
        setListener();
        return mView;
    }


    public void initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        Context ctxWithTheme = new ContextThemeWrapper(
                getActivity().getApplicationContext(),
                R.style.Theme_PageIndicatorDefaultsNew);
        getActivity().getWindow().setBackgroundDrawable(null);
        LayoutInflater localLayoutInflater = inflater
                .cloneInContext(ctxWithTheme);
        mView = localLayoutInflater.inflate(R.layout.fragment_one_main, container, false);
        //初始化控件
        tabPageIndicator = (NewTabPageIndicator) findViewById(R.id.baseviews_indicator);
        mViewPager = (ViewPager) findViewById(R.id.baseviews_viewpager);
        init();
    }

    /**
     * 设置控件监听
     */
    public void setListener() {

    }

    /**
     * 初始化界面
     */
    public void init() {
        baseViewList = new ArrayList<BaseView>();
        titleList = getResources().getStringArray(R.array.baseviews_tabs);
        BaseView v = null;
        for (int i = 1; i < 4; i++) {
            switch (i) {
                case 1:
                    v = new ChildViewOne(getActivity(), getActivity(), 1);
                    break;
                case 2:
                    v = new ChildViewTwo(getActivity(), getActivity(), 2);
                    break;
                case 3:
                    v = new ChildViewThree(getActivity(), getActivity(), 3);
                    break;
            }
            baseViewList.add(v);
        }
        adapter = new BaseViewPageAdapter(baseViewList, titleList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(pageChangeListener);
        tabPageIndicator.setTextSizeIsChange(true, getResources().getColor(R.color
                        .tab_text_color_selector),
                getResources().getColor(R.color.tab_text_color_default));
        tabPageIndicator.setOnPageChangeListener(pageChangeListener);
        tabPageIndicator.setViewPager(mViewPager);
        mViewPager.setCurrentItem(currentPosition);
    }

    /**
     * 根据id去查找控件
     *
     * @param id
     * @return
     */
    protected View findViewById(int id) {
        return mView.findViewById(id);
    }


    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager
            .OnPageChangeListener() {
        @Override
        public void onPageScrollStateChanged(int position) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            if (currentPosition != position) {
                baseViewList.get(currentPosition).onPause();
            }
            baseViewList.get(position).onResume();
            currentPosition = position;
            baseView = baseViewList.get(currentPosition);
        }
    };


}
