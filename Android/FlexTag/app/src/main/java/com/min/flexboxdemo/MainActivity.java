package com.min.flexboxdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.min.flexboxdemo.util.MyLog;
import com.min.flexboxdemo.view.tag.TagAdapter;
import com.min.flexboxdemo.view.tag.TagViewLayout;

import java.util.Arrays;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG=MainActivity.class.getSimpleName();

    private FloatingActionButton mFab;

    private TagViewLayout mTagView;
    private TagAdapter<String> mTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initTagData();
    }

    private void initTagData(){
        mTagAdapter=new TagAdapter<String>(this) {
            @Override
            public View getView(ViewGroup viewParent, int position) {
                TextView tv= (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_tag,viewParent,false);
                tv.setText(getData().get(position));
                return tv;
            }

            @Override
            public void setSelectViewBg(ViewGroup viewParent, int position, View view) {
                view.setBackgroundResource(R.drawable.shape_tag_bg_f);
            }

            @Override
            public void setUnSelectViewBg(ViewGroup viewParent, int position, View view) {
                view.setBackgroundResource(R.drawable.shape_tag_bg_n);
            }
        };
        mTagView.setAdapter(mTagAdapter);

        String[] dataArr={"java","html","html5","http","physon","C++"
                ,"c#","我要学编程","黄家驹","南山南"};
        mTagAdapter.setData(Arrays.asList(dataArr));
    }

    public void setPreOption(View view){
        MyLog.i(TAG, "setPreOption");
        mTagAdapter.setPreSelectSet(0, 1);
    }

    public void getSelectOption(View view){
        MyLog.i(TAG,"getSelectOption");
        Set<Integer> selectSet=mTagAdapter.getSelectSet();
        if(selectSet.isEmpty()){
            MyLog.i(TAG,"selectSet is empty");
            snakbarToast("selectSet is empty");
        }else{
            MyLog.i(TAG,"selectSet=%s",selectSet.toString());
            snakbarToast(selectSet.toString());
        }
    }

    public void updateData(View view){
        MyLog.i(TAG,"updateData");
        String[] dataArr={"南山南","不在犹豫","真的爱你","海阔天空","喜欢你","结局"
                ,"c#","我要学编程","黄家驹","我哈哈"};
        mTagAdapter.setData(Arrays.asList(dataArr));
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTagView= (TagViewLayout) findViewById(R.id.view_tag);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snakbarToast("Replace with your own action");
            }
        });
    }

    private void snakbarToast(String str){
        Snackbar.make(mFab, str, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
