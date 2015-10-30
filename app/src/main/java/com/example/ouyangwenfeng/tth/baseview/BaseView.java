package com.example.ouyangwenfeng.tth.baseview;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * Created oywf  on 2015/10/29.
 * <p/>
 * 二级view嵌套页面基础类
 */
public abstract class BaseView {
    protected Context mContext;
    protected Activity mActivity;
    protected Bundle bundle;
    protected View view;
    protected int id;//当前view标识


    public BaseView() {
    }

    public BaseView(Context mContext) {
        this.mContext = mContext;
        initView();
        setListener();
        initDate();
    }

    public BaseView(Context mContext, Activity mActivity,int id) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.id=id;
        initView();
        setListener();
        initDate();
    }

    public BaseView(Context mContext, Bundle bundle) {
        this.mContext = mContext;
        this.bundle = bundle;
        initView();
        setListener();
        initDate();
    }

    public View getView() {
        if (view.getLayoutParams() == null) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout
                    .LayoutParams.FILL_PARENT,
                    RelativeLayout.LayoutParams.FILL_PARENT);
            view.setLayoutParams(params);
        }
        return view;
    }

    public View findViewById(int id) {
        return view.findViewById(id);
    }

    protected abstract void initView();

    protected abstract void setListener();

    protected abstract void initDate();

    /**
     * 像fragment一样添加生命周期
     */
    public abstract void onResume();

    public abstract void onPause();

}

