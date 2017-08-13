package com.min.easyrecycleview.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.min.easyrecycleview.R;
import com.min.easyrecycleview.adapter.PersonAdapter;
import com.min.easyrecycleview.util.DataUtil;
import com.min.easyrecycleview.util.DrawableUtil;
import com.min.easyrecycleview.view.divider.DividerGridItemDecoration;
import com.min.easyrecycleview.view.divider.DividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewActivity extends AppCompatActivity {

    @Bind(R.id.rv_list)
    RecyclerView mListRv;

    private PersonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        ButterKnife.bind(this);
    }

    private void initData() {
        mAdapter.setData(DataUtil.getDataList(15));
    }

    @OnClick(R.id.btn_list)
    void clickListBtn(){
        RecyclerView.LayoutManager lm=new LinearLayoutManager(this);
        mListRv.setLayoutManager(lm);
        mAdapter=new PersonAdapter(this);
        mListRv.setAdapter(mAdapter);
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this
                ,DividerItemDecoration.VERTICAL_LIST);
        mListRv.addItemDecoration(itemDecoration);

        initData();
    }

    @OnClick(R.id.btn_grid)
    void clickGridBtn(){
        RecyclerView.LayoutManager lm=new GridLayoutManager(this,3);
        mListRv.setLayoutManager(lm);
        mAdapter=new PersonAdapter(this);
        mListRv.setAdapter(mAdapter);
        DividerGridItemDecoration itemDecoration = new DividerGridItemDecoration(this
                ,DrawableUtil.getShapeDrawable(Color.parseColor("#0000FF"),3));
        mListRv.addItemDecoration(itemDecoration);

        initData();
    }

    @OnClick(R.id.btn_insert)
    void clickInsertBtn(){
        int pos=mAdapter.getData().size();
        mAdapter.getData().add("i am new insert" + pos);
//        mAdapter.notifyItemInserted(pos);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_remove)
    void clickRemoveBtn(){
        int lastPos=mAdapter.getData().size()-1;
        mAdapter.getData().remove(lastPos);
//        mAdapter.notifyItemRemoved(lastPos);
        mAdapter.notifyDataSetChanged();
    }

}
