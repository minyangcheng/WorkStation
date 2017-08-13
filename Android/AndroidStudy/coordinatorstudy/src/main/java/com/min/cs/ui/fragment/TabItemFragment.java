package com.min.cs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.min.cs.R;
import com.min.cs.adapter.PersonAdapter;
import com.min.cs.util.DataUtils;
import com.min.framework.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by minyangcheng on 2016/10/17.
 */
public class TabItemFragment extends BaseFragment {

    @Bind(R.id.rv)
    RecyclerView mRv;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_item;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        mRv.setLayoutManager(linearLayoutManager);
        PersonAdapter adapter=new PersonAdapter(mContext);
        mRv.setAdapter(adapter);
        adapter.setData(DataUtils.getPersonData(20));
    }
}
