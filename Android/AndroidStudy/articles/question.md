## 问题

1. 为什么会出现fragment，它比view有哪些好处？
	
> Android introduced fragments in Android 3.0 (API level 11), primarily to support more dynamic and flexible UI designs on large screens, such as tablets. Because a tablet's screen is much larger than that of a handset, there's more room to combine and interchange UI components. Fragments allow such designs without the need for you to manage complex changes to the view hierarchy. By dividing the layout of an activity into fragments, you become able to modify the activity's appearance at runtime and preserve those changes in a back stack that's managed by the activity.

1. android开始引入fragment概念在android 3.0时，最基本的目的是为了支持在大屏设备上动态和灵活的布局，使用fragment可以更方便的管理复杂的视图层级，是一种很好的碎片化解决方案。
2. fragment可以将activity模块化开发，提高代码内聚性，是activity中的代码不会过于臃肿；更好的处理生命周期的变化和管理复杂的视图层级。