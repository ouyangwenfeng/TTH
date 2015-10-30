package com.example.ouyangwenfeng.tth.main;

import android.os.Bundle;

import com.example.ouyangwenfeng.tth.R;
import com.example.ouyangwenfeng.tth.baseview.TabHostBaseActivity;
import com.example.ouyangwenfeng.tth.fragment.FragmentFourMain;
import com.example.ouyangwenfeng.tth.fragment.FragmentOneMain;
import com.example.ouyangwenfeng.tth.fragment.FragmentThreeMain;
import com.example.ouyangwenfeng.tth.fragment.FragmentTwoMain;

import android.graphics.Color;
import android.support.v4.app.Fragment;

import java.util.ArrayList;


/**
 * Created by oywf on 15/10/29.
 * <p/>
 * 底部选项栏入口Activity，继承TabHostBaseActivity，并实现getTabBarBuilder，自定义tabbar
 */
public class MainActivity extends TabHostBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public TabBarBuilder getTabBarBuilder() {
        TabHostBaseActivity.TabBarBuilder builder = new TabHostBaseActivity.TabBarBuilder();
        //设置默认图标
        builder.defaultIcons = new int[]{R.mipmap.app_information_tab_default,
                R.mipmap.app_bbs_tab_default,
                R.mipmap.app_lib_tab_default,
                R.mipmap.app_feature_tab_default};
        //设置选中图标
        builder.selectedIcons = new int[]{R.mipmap.app_information_tab_selected,
                R.mipmap.app_bbs_tab_selected,
                R.mipmap.app_lib_tab_selected,
                R.mipmap.app_feature_tab_selected
        };
        //设置文案标题
        builder.texts = new int[]{R.string.tab1_title,
                R.string.tab2_title,
                R.string.tab3_title,
                R.string.tab4_title};
        //设置默认字体颜色
        builder.defaultTextsColor = Color.rgb(104, 104, 104);
        //设置选中字体颜色
        builder.selectedTextsColor = Color.rgb(37, 163, 235);
        //设置底部背景颜色
        builder.contentLayoutColor = Color.WHITE;
        builder.bottomLayoutColor = Color.rgb(250, 250, 250);
        //设置分割线颜色
        builder.halvingLineColor = Color.GRAY;
        builder.textSize = 12;
        //是否允许tab页面滑动切换
        builder.canSlide = true;
        //设置每个页面的fragment
        ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(FragmentOneMain.getInstance(1, Color.GREEN));
        fragmentArrayList.add(new FragmentTwoMain());
        fragmentArrayList.add(new FragmentThreeMain());
        fragmentArrayList.add(new FragmentFourMain());
        builder.fragmentList = fragmentArrayList;
        return builder;
    }

}

