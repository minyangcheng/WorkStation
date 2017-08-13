##介绍

自定义StateLayout，轻松实现loading/content/empty/error场景下的页面切换与展示，并且支持各类型页面点击监听。

##用法
1.在layout文件中单独可单独配置各个状态的布局文件,也定义单独状态下的文件显示数据
 
	<com.min.library.StateLayout
            android:id="@+id/stateLayout"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"
            app:empty_layout="@layout/status_empty_custom"
            app:error_text="this is custom error page">

            <TextView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:gravity="center"
                android:text="this is content"/>

    </com.min.library.StateLayout>

2.在style文件中统一配置各种状态的布局文件    

	<style name="AppTheme" 	parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="studentNameColor">@color/green_dark</item>
        <item name="stateLayoutDefStyle">@style/statelayoutThemeStyle</item>
    </style>

    <style name="statelayoutThemeStyle">
        <item name="loading_layout">@layout/status_loading</item>
        <item name="empty_layout">@layout/status_empty</item>
        <item name="error_layout">@layout/status_error</item>
    </style>

3.一般情况下都是只设置error状态下的监听，特殊情况下可以通过stateLayout.getEmptyView()方式设置监听

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
