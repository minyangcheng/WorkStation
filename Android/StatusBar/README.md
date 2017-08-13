## StatusBar
- 在实现translucent status bar时，用过fitsystemwindow属性和网上的一些开源库如StatusBarUtil，来避免防止内容布局被拉到statusbar下面，使用效果都打不到项目需求,具体情形如下：
  1. 自定义toolbar的布局是放在fragment layout中，并且有些fragment的布局需要被拉到状态栏底部，不能直接统一设置host activity的status bar，网上的一些开源库只是单纯的解决了在activity中实现沉浸效果
  2. 在layout中设置fitsystemwindow属性，会出现设置此属性的view高度没有增加，paddingtop却增加了
- 原理：其实仔细想一下，当我们设置activity的style为TRANSLUCENT_STATUS时，出现的问题解释主内容区域被拉到了状态栏底部。当然有时候我们是需要被拉到状态栏底部的，比如全部显示一张图片或轮播广告图。
现在就是唯一需要解决的问题就是避免被拉到底部，这里我做了一个包装view类的控件，该控件会默认添加一个和状态栏高度一样的子控件，就搞定所有的了。

#### 用法
1.设置沉浸式模式
```
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
```

2.将toolbar的主要内容包裹在StatusBarSpaceAssistLayout里面
```
<?xml version="1.0" encoding="utf-8"?>
<com.min.statusbar.view.StatusBarSpaceAssistLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:id="@+id/sbsl_statusbar">

    <RelativeLayout
        android:id="@+id/toolbar"
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:minHeight="?attr/actionBarSize"
        android:background="?attr/colorPrimary">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="16sp"
            android:text="this is title"
            android:layout_centerInParent="true"/>
    </RelativeLayout>

</com.min.statusbar.view.StatusBarSpaceAssistLayout>
```

3.布局文件中可以直接使用此自定义toolbar
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <include layout="@layout/include_custom_toolbar"/>

    <Button
        android:id="@+id/btn_change_visiable"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="6dp"
        android:padding="6dp"
        android:text="change_toolbar_visiable"/>

    <Button
        android:id="@+id/btn_change_color"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="6dp"
        android:padding="6dp"
        android:text="change_statusbar_color"/>

</LinearLayout>
```
```