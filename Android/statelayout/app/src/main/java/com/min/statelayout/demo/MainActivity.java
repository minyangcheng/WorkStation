package com.min.statelayout.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.min.library.StateLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.stateLayout)
    StateLayout stateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        stateLayout.setOnRetryListener(new StateLayout.OnRetryListener() {
            @Override
            public void onRetry() {
                Toast.makeText(MainActivity.this, "you should connect net", Toast.LENGTH_SHORT).show();
            }
        });
        stateLayout.getEmptyView().findViewById(R.id.go_somewhere)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "go somewhere", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.btn_loading)
    void clickBtnLoading(){
        stateLayout.showLoadingStatus();
    }

    @OnClick(R.id.btn_content)
    void clickBtnContent(){
        stateLayout.showContentStatus();
    }

    @OnClick(R.id.btn_empty)
    void clickBtnEmpty(){
        stateLayout.showEmptyStatus();
    }

    @OnClick(R.id.btn_error)
    void clickBtnError(){
        stateLayout.showErrorStatus();
    }

}
