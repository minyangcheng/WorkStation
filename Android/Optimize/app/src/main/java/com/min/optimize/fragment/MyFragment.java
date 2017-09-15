package com.min.optimize.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.min.optimize.R;

/**
 * Created by minyangcheng on 2017/9/15.
 */

public class MyFragment extends Fragment {

    private String toastTip = "toast in MyFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        methodWithGlobalVariable();
        methodWithLocalVariable();
        return view;
    }

    public void methodWithGlobalVariable() {
        Toast.makeText(getActivity(), toastTip, Toast.LENGTH_SHORT).show();
    }

    public void methodWithLocalVariable() {
        String logMessage = "log in MyFragment";
        logMessage = logMessage.toLowerCase();
        System.out.println(logMessage);
    }

}
