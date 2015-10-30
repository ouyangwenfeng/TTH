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
public class ChildViewThree extends BaseView{
    public ChildViewThree() {
    }

    public ChildViewThree(Context context, Activity activity, int id) {
        super(context, activity, id);
    }

    @Override
    protected void initView() {
        view = View.inflate(mContext, R.layout.baseview_three, null);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initDate() {

    }

    @Override
    public void onResume() {
        Log.i("ouyang", "子布局-----3-----> OnResume()");

    }

    @Override
    public void onPause() {
        Log.i("ouyang", "子布局-----3-----> onPause()");

    }
}
