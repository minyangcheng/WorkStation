package com.min.seed.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.min.seed.R;

/**
 * Created by minyangcheng on 2017/8/30.
 */

public class TestDialog extends DialogFragment {

    public static void show(FragmentManager fragmentManager) {
        TestDialog dialog = new TestDialog();
        dialog.show(fragmentManager, TestDialog.class.getName());
    }

    public TestDialog() {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogWrap);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_test, container, false);
    }
}
