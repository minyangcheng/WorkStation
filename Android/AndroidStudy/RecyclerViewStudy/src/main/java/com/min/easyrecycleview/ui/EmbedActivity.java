package com.min.easyrecycleview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.min.easyrecycleview.R;
import com.min.easyrecycleview.adapter.PersonAdapter;
import com.min.easyrecycleview.util.DataUtil;
import com.min.easyrecycleview.view.divider.DividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EmbedActivity extends AppCompatActivity {

    @Bind(R.id.rv_outer)
    RecyclerView mOutRv;

    private PersonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embed);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
        mOutRv.setLayoutManager(lm);
        mAdapter=new PersonAdapter(this);
        mOutRv.setAdapter(mAdapter);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this
                ,DividerItemDecoration.VERTICAL_LIST);
        mOutRv.addItemDecoration(itemDecoration);
        mAdapter.setData(DataUtil.getDataList(7));

        setHeader();
    }

    private void setHeader() {
        View headerView= LayoutInflater.from(this).inflate(R.layout.item_header, mOutRv,false);
        RecyclerView innerRv= (RecyclerView) headerView.findViewById(R.id.rv_inner);
        RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
        innerRv.setLayoutManager(lm);
        PersonAdapter adapter=new PersonAdapter(this);
        innerRv.setAdapter(adapter);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this
                ,DividerItemDecoration.VERTICAL_LIST);
        innerRv.addItemDecoration(itemDecoration);
        adapter.setData(DataUtil.getDataList(5));

        mAdapter.setHeaderView(headerView);
    }

}
