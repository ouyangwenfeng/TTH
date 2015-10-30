package com.example.ouyangwenfeng.tth.baseview;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

/**
 * ViewPager Adapter基类
 * <p/>
 * Created by oywf on 15/10/29.
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    public ArrayList<Fragment> list;

    public BaseFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }
}

