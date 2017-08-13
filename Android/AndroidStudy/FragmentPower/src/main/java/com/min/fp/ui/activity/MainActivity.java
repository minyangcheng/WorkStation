package com.min.fp.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.min.fp.R;
import com.min.fp.ui.fragment.ContentFragment;
import com.min.fp.util.FragmentManagerUtil;
import com.min.framework.base.BaseActivity;
import com.min.framework.base.BaseFragment;
import com.min.framework.util.L;
import com.min.framework.util.ToastUtils;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    private static final String TAG="MainActivity_TEST";
    private static final String KEY_INDEX="indexKey";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nvView)
    NavigationView mNavigationView;
    ActionBarDrawerToggle mActionBarDrawerToggle;

    private int mIndex;
    private BaseFragment mNowFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d(TAG, "onCreate");
        if(savedInstanceState!=null){
            mIndex=savedInstanceState.getInt(KEY_INDEX,0);
        }
        setDrawerToggle();
        setNavgationContent(mNavigationView);
        initToolbar(mToolbar);
        setFragmentToContent(mIndex);
    }

    private void setFragmentToContent(int index){
        mIndex=index;
        MenuItem menuItem=mNavigationView.getMenu().getItem(index);
        menuItem.setChecked(true);
        //更新toolbar
        mToolbar.setTitle(menuItem.getTitle());
        mToolbar.getMenu().clear();
        switch (index){
            case 0:
                mToolbar.inflateMenu(R.menu.menu_a);
                break;
            case 1:
                mToolbar.inflateMenu(R.menu.menu_b);
                break;
            case 2:
                mToolbar.inflateMenu(R.menu.menu_c);
                break;
        }
        //更新content内容区域
        mNowFragment=ContentFragment.newInstance(menuItem.getTitle().toString());
        FragmentManagerUtil.setFragment(getSupportFragmentManager()
                , mNowFragment
                , R.id.content);
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ToastUtils.showShortToast(getContext(), item.getTitle());
                return true;
            }
        });
    }

    private void setDrawerToggle() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    private void setNavgationContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        handleNavigationItemClick(menuItem);
                        return true;
                    }
                });
        View headerView=navigationView.getHeaderView(0);
        TextView usernameTv= (TextView) headerView.findViewById(R.id.tv_username);
        usernameTv.setText("minyangcheng");
    }

    private void handleNavigationItemClick(MenuItem menuItem){
        mDrawerLayout.closeDrawers();
        int id=menuItem.getItemId();
        switch (id){
            case R.id.item_a:
                setFragmentToContent(0);
                break;
            case R.id.item_b:
                setFragmentToContent(1);
                break;
            case R.id.item_c:
                setFragmentToContent(2);
                break;
            case R.id.item_d:
                FragmentManagerUtil.putFragment(getSupportFragmentManager()
                            ,mNowFragment
                            ,ContentFragment.newInstance(menuItem.getTitle().toString())
                            ,R.id.drawer_layout);
                break;
            case R.id.item_e:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //当前不是在第一个栏目，则点击回退键会自动退回到首页
        if(closeDrawer()){
            return;
        }else if(mIndex>0){
            setFragmentToContent(0);
        }else{
            super.onBackPressed();
        }
    }

    public boolean closeDrawer(){
        if(mDrawerLayout.isDrawerOpen(mNavigationView)){
            mDrawerLayout.closeDrawer(mNavigationView);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d(TAG, "onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mIndex);
        L.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        L.d(TAG,"onRestoreInstanceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d(TAG, "onDestroy");
    }
}
