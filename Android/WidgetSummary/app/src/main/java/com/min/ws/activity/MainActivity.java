package com.min.ws.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.min.ws.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_tab_fragment)
    void clickTabWithFragment(){
        go(TabActivity.class);
    }

    @OnClick(R.id.btn_tab_viewpager)
    void clickTabWithViewPager(){
        go(TabViewPagerActivity.class);
    }

    @OnClick(R.id.btn_sliding_tab)
    void clickSlidingTab(){
        go(SlidingTabActivity.class);
    }

    @OnClick(R.id.btn_pager_indicator)
    void clickPageIndicator(){
        go(PagerIndicatorActivity.class);
    }

    @OnClick(R.id.btn_msg_view)
    void clickMsgView(){
        go(MsgViewActivity.class);
    }

    @OnClick(R.id.btn_observable_scrollview)
    void clickObservableScrollview(){
        go(ObservableScrollActivity.class);
    }

    @OnClick(R.id.btn_observable_recyclerview)
    void clickObservableRecyclerview(){
        go(ObservableRecyclerViewActivity.class);
    }

    @OnClick(R.id.btn_gif)
    void clickGif(){
        go(GifActivity.class);
    }

    @OnClick(R.id.btn_tint_imageview)
    void clickBtnImageView(){
        go(TintImageViewActivity.class);
    }

    @OnClick(R.id.btn_fragment_anim)
    void clickBtnFragmentAnim(){
        go(FragmentAnimActivity.class);
    }

    @OnClick(R.id.btn_powermanager)
    void clickBtnPowerManager(){
        go(PowerManagerActivity.class);
    }

    @OnClick(R.id.btn_preference_activity)
    void clickBtnPreferenceActivity(){
        go(PreferenceActTemplateActivity.class);
    }

    @OnClick(R.id.btn_toolbar)
    void clickBtnToolbar(){
        go(ToolbarActivity.class);
    }

    @OnClick(R.id.btn_loaction)
    void clickBtnLoaction(){
        go(LocationActivity.class);
    }

    @OnClick(R.id.btn_banner)
    void clickBtnBanner(){
        go(RecycleViewBannerActivity.class);
    }

    @OnClick(R.id.btn_edit_text)
    void clickBtnEditText(){
        go(EditTextActivity.class);
    }

    @OnClick(R.id.btn_photoview)
    void clickBtnPhotoview(){
        go(PhotoViewActivity.class);
    }

    @OnClick(R.id.btn_widget)
    void clickBtnWidget(){
        go(NormalWidgetActivity.class);
    }

    private void go(Class clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

}
