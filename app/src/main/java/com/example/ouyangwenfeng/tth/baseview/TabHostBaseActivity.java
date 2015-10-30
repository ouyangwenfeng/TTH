package com.example.ouyangwenfeng.tth.baseview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.ouyangwenfeng.tth.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oywf on 15/10/29.
 *         实现底部栏加ViewPager抽象类
 */
public abstract class TabHostBaseActivity extends FragmentActivity {
    private LayoutInflater inflater;
    private int checkId; //当前tab位置
    private List<RelativeLayout> tabItemList; //tab列表
    private List<RelativeLayout> tabsubItemList; //tab子列表
    private List<RelativeLayout> tabTipItemList;//tab提示列表

    private List<ImageView> tabImageViewList;//tab图标列表
    private List<TextView> tabTextViewList;//tab文字列表

    public LinearLayout rootLayout; //根视图
    public BaseViewPager contentLayout; //内容视图，ViewPager为容器
    public LinearLayout bottomLayout; //底部视图，RadioGroup为容器
    public TabBarBuilder builder; //构建器

    /**
     * 初始化内容、底部视图
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = getTabBarBuilder();
        inflater = LayoutInflater.from(this);
        initLayout();
        setTabItems();
        initContent();
        setContentView(rootLayout);
    }

    /**
     * 子类需要实现返回构建器方法
     */
    public abstract TabBarBuilder getTabBarBuilder();

    /**
     * 构建器及参数
     */
    public static class TabBarBuilder {
        public ArrayList<Fragment> fragmentList; //内容视图包含的fragment列表
        public int[] defaultIcons; //默认tab icon
        public int[] selectedIcons; //选择tab icon
        public int[] texts; //tab 文字
        public int defaultTextsColor;//默认tab文字颜色
        public int selectedTextsColor;//选中tab文字颜色
        public int bottomLayoutColor;//底部背景颜色
        public int contentLayoutColor;//内容背景颜色
        public int halvingLineColor;//分割线颜色
        public float textSize;//文字大小
        public boolean canSlide;//内容视图是否能滑动
    }

    /**
     * 清空标签的提示icon
     */
    public void clearTipsLayout(int position) {
        tabTipItemList.get(position).removeAllViews();
    }

    /**
     * 设置标签的提示icon
     */
    public void setTipsLayout(int position, View view) {
        tabTipItemList.get(position).removeAllViews();
        tabTipItemList.get(position).addView(view);
    }

    /**
     * 初始化内容、底部、视图和分割线
     */
    private void initLayout() {
        rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        contentLayout = new BaseViewPager(this);
        contentLayout.setId(R.id.action_bar);
        contentLayout.setOffscreenPageLimit(builder.texts.length);
        bottomLayout = new RadioGroup(this);
        bottomLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams contentLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams bottomLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentLayoutParams.weight = 1;
        contentLayout.setLayoutParams(contentLayoutParams);
        contentLayout.setBackgroundColor(builder.contentLayoutColor);
        contentLayout.setViewPagerSlidEnabled(builder.canSlide);
        bottomLayout.setLayoutParams(bottomLayoutParams);
        bottomLayout.setBackgroundColor(builder.bottomLayoutColor);

        TextView line = new TextView(this);
        line.setBackgroundColor(builder.halvingLineColor);
        line.setHeight(1);

        rootLayout.addView(contentLayout);
        rootLayout.addView(line);
        rootLayout.addView(bottomLayout);
    }

    /**
     * 设置tab选中状态
     */
    private void setTabItemCheckState(int position) {
        tabImageViewList.get(checkId).setImageResource(builder.defaultIcons[checkId]);
        tabTextViewList.get(checkId).setTextColor(builder.defaultTextsColor);
        checkId = position;
        tabImageViewList.get(checkId).setImageResource(builder.selectedIcons[checkId]);
        tabTextViewList.get(checkId).setTextColor(builder.selectedTextsColor);
    }

    /**
     * 初始化底部子项
     */
    private void setTabItems() {
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        bottomLayout.removeAllViews();
        tabItemList = new ArrayList<RelativeLayout>();
        tabsubItemList = new ArrayList<RelativeLayout>();
        tabTipItemList = new ArrayList<RelativeLayout>();
        tabImageViewList = new ArrayList<ImageView>();
        tabTextViewList = new ArrayList<TextView>();
        for (int i = 0; i < builder.texts.length; i++) {
            final int position = i;
            RelativeLayout subItem = (RelativeLayout) inflater.inflate(R.layout.tab_item, null);
            subItem.setPadding(0, 15, 0, 15);
            subItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != checkId) {
                        setTabItemCheckState(position);
                        contentLayout.setCurrentItem(position, false);
                    }
                }
            });
            LinearLayout.LayoutParams radioButtonParams = new LinearLayout.LayoutParams(width / builder.texts.length, ViewGroup.LayoutParams.WRAP_CONTENT);
            radioButtonParams.weight = 1;
            subItem.setLayoutParams(radioButtonParams);
            ImageView imageView = (ImageView) subItem.findViewById(R.id.imageView);
            TextView tv = (TextView) subItem.findViewById(R.id.textView);
            tv.setTextSize(builder.textSize);
            imageView.setImageResource(builder.defaultIcons[i]);
            tv.setText(builder.texts[i]);
            tv.setTextColor(builder.defaultTextsColor);

            tabImageViewList.add(imageView);
            tabTextViewList.add(tv);
            RelativeLayout tipLayout = (RelativeLayout) subItem.findViewById(R.id.tip_layout);
            tabTipItemList.add(tipLayout);

            bottomLayout.addView(subItem);
            tabItemList.add(subItem);
            tabsubItemList.add(subItem);

        }
        checkId = 0;
        setTabItemCheckState(0);
    }

    /**
     * 初始化视图容器ViewPager
     */
    private void initContent() {
        contentLayout.setAdapter(new BaseFragmentPagerAdapter(this.getSupportFragmentManager(), builder.fragmentList));
        contentLayout.setCurrentItem(0);
        contentLayout.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 监听ViewPager滑动，同步底部按钮选中状态
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageSelected(int arg0) {
            if (arg0 != checkId) {
                setTabItemCheckState(arg0);
            }
        }
    }
}
