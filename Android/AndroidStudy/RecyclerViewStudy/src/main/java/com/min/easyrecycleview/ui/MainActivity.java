package com.min.easyrecycleview.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.min.easyrecycleview.R;
import com.min.easyrecycleview.adapter.PersonAdapter;
import com.min.easyrecycleview.util.DataUtil;
import com.min.easyrecycleview.util.DrawableUtil;
import com.min.easyrecycleview.view.divider.DividerGridItemDecoration;
import com.min.easyrecycleview.view.divider.DividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_recyclerview)
    void clickRecycleView(){
        go(RecyclerViewActivity.class);
    }

    @OnClick(R.id.btn_embed)
    void clickEmbed(){
        go(EmbedActivity.class);
    }

    private void go(Class clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

}
