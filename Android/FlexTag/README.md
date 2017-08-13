###标签布局
该控件是基于google强大的flexbox布局控件上封装，便于项目中快速应用标签效果。可以直接通过adapter预选项、得到选择项、更新数据。 

```
//添加依赖
compile 'com.google.android:flexbox:0.1.2'
```

```
//设置adapter
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
```

```
//设置预选项
public void setPreOption(View view){
    MyLog.i(TAG,"setPreOption");
    mTagAdapter.setPreSelectSet(0,1);
}

//得到选择项
public void getSelectOption(View view){
    MyLog.i(TAG,"getSelectOption");
    Set<Integer> selectSet=mTagAdapter.getSelectSet();
    if(selectSet.isEmpty()){
        MyLog.i(TAG,"selectSet is empty");
    }else{
        MyLog.i(TAG,"selectSet=%s",selectSet.toString());
    }
}

//更新数据
public void updateData(View view){
    MyLog.i(TAG,"updateData");
    String[] dataArr={"南山南","不在犹豫","真的爱你","海阔天空","喜欢你","结局"
            ,"c#","我要学编程","黄家驹","我哈哈"};
    mTagAdapter.setData(Arrays.asList(dataArr));
}
```