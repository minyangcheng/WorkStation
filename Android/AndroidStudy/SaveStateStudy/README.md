##SaveStateDemo
###碰到的问题和解决方法

- 1.通过横竖屏切换，模拟activity在后台被系统回收重建事件。
- 2.onSaveInstanceState和onRestoreInstanceState执行时间周期：onPause->onSaveInstanceState->onCreate->onRestoreInstanceState->onResume
- 3.用fragment做导航时出现的页面重叠现象，是由于host activity被系统回收重建导致。解决方法参见demo代码。
- 4.fragment调用fragmentrasation add后执行：onCreate-->onResume,而后调用hide/show后执行：onHiddenChanged。其onResume和onPause和host activity同步。
- 5.当activity重建的时候，只有id的控件view的onSaveInstanceState和onRestoreInstanceState才能被回调，进而才能恢复之前的数据。
- 6.view保存和恢复参考demo代码，主要是在恢复是记得requestlayout一下，就可以将之前的数据重新显示在页面上。