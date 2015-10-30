package com.example.ouyangwenfeng.tth.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ouyangwenfeng.tth.R;

/**
 * Created by oywf on 15/10/29.
 */
public class FragmentThreeMain extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =View.inflate(getActivity(), R.layout.fragment_three_main,null);
        return view;
    }
}
