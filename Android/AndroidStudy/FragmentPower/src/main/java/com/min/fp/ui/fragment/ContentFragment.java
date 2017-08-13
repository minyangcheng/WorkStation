package com.min.fp.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.min.fp.R;
import com.min.fp.util.FragmentManagerUtil;
import com.min.framework.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by minyangcheng on 2016/10/20.
 */
public class ContentFragment extends BaseFragment{

    private static final String ARG_TITLE="titleArg";

    @Bind(R.id.tv)
    TextView mTv;
    @Bind(R.id.btn)
    Button mBtn;

    private String mTitle;

    public static ContentFragment newInstance(String title){
        ContentFragment fragment=new ContentFragment();
        Bundle bundle=new Bundle();
        bundle.putString(ARG_TITLE,title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if(bundle!=null){
            mTitle=bundle.getString(ARG_TITLE);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!TextUtils.isEmpty(mTitle)){
            mTv.setText(String.valueOf(mTitle));
            setButton();
        }
    }

    private void setButton(){
        if(TextUtils.isEmpty(mTitle)) return;
        if(mTitle.equals("D")){
            mBtn.setVisibility(View.VISIBLE);
            mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManagerUtil.pop(getFragmentManager(),ContentFragment.this);
                }
            });
        }else if(mTitle.equals("E")){
            mBtn.setVisibility(View.VISIBLE);
            mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManagerUtil.pop(getFragmentManager(),ContentFragment.this);
                }
            });
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }
}
