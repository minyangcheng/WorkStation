package com.min.ws.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.min.ws.R;
import com.min.ws.fragment.ContentFragment;
import com.min.ws.util.L;
import com.min.ws.util.ListUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentAnimActivity extends AppCompatActivity {

    private static final String TAG="FragmentAnimActivity_TEST";

    private ContentFragment mFragment_A;
    private ContentFragment mFragment_B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_anim);
        ButterKnife.bind(this);

        mFragment_A=ContentFragment.newInstance("A");
        mFragment_B=ContentFragment.newInstance("B");
    }

    @OnClick(R.id.btn_look)
    void onClickLook(){
        logStack();
    }

    @OnClick(R.id.btn_pop)
    void onClickPop(){
        getSupportFragmentManager().popBackStackImmediate();

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit)
                .add(R.id.fl_content, mFragment_B)
                .commit();
    }

    @OnClick(R.id.btn_add)
    void onClickAdd(){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit)
                .add(R.id.fl_content, mFragment_A)
                .addToBackStack(mFragment_A.getContentTag())
                .commit();
    }

    @OnClick(R.id.btn_remove)
    void onClickRemove(){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .remove(mFragment_A)
                .addToBackStack(mFragment_A.getContentTag())
                .commit();
    }

    public void logStack(){
        int count=getSupportFragmentManager().getBackStackEntryCount();
        L.d(TAG,"BackStackEntryCount=%s",count);
        List<Fragment> fragmentList=getSupportFragmentManager().getFragments();
        if(!ListUtils.isEmpty(fragmentList)){
            StringBuilder sb=new StringBuilder();
            sb.append("getFragments");
            for (int i = 0; i < fragmentList.size(); i++) {
                if(fragmentList.get(i)==null){
                    sb.append(", "+(i)+"=null");
                }else{
                    sb.append(", "+(i)+"="+((ContentFragment)fragmentList.get(i)).getContentTag());
                }
            }
            L.d(TAG,sb.toString());
        }else{
            L.d(TAG,"getFragments is empty");
        }
    }

}
