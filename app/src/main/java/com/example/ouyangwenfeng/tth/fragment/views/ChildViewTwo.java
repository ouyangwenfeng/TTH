package com.example.ouyangwenfeng.tth.fragment.views;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.ouyangwenfeng.tth.R;
import com.example.ouyangwenfeng.tth.baseview.BaseView;

/**
 * Created by oywf on 15/10/29.
 */
public class ChildViewTwo extends BaseView{
    public ChildViewTwo() {
    }

    public ChildViewTwo(Context context, Activity activity, int id) {
        super(context, activity, id);
    }

    @Override
    protected void initView() {
        view = View.inflate(mContext, R.layout.baseview_two, null);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initDate() {

    }

    @Override
    public void onResume() {
        Log.i("ouyang", "子布局-----2-----> OnResume()");

    }

    @Override
    public void onPause() {
        Log.i("ouyang", "子布局-----2-----> onPause()");

    }
}
