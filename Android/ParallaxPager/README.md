## ParallaxPager视差效果
- 模仿知乎欢迎页面的视差效果，并且自己封住了一些类，便于后期应用于开发项目.
- 只需继承PagerFragment并将ParallaxPagerTransformer设置到viewpager上，即可实现视差效果。你也可以重写parallax（）方法实现自己视差移动逻辑。  

```
public abstract class PagerFragment extends Fragmet {

    protected float mParallaxCoefficient = 1.2f;
    protected float mDistanceCoefficient = 0.5f;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(getLayoutId(),container,false);
        view.setTag(this);
        ButterKnife.bind(this,view);
        return view;
    }

    public abstract int getLayoutId();

    public void parallax(View page,float position){
        if(getParallaxViews()==null||getParallaxViews().size()==0)
            return;
        float scrollXOffset=page.getWidth()*mParallaxCoefficient;
        for (View view : getParallaxViews()){
            view.setTranslationX(scrollXOffset*position);
            scrollXOffset*=mDistanceCoefficient;
        }
    }

    public abstract List<View> getParallaxViews();

}   
```
```
public class ParallaxPagerTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        Object object=page.getTag();
        if(object!=null&&object instanceof PagerFragment){
            PagerFragment pagerFragment= (PagerFragment) object;
            pagerFragment.parallax(page,position);
        }
    }
}
```
```
public class GuideSecondFragment extends PagerFragment {
	
    @Bind({R.id.guide_item_1,R.id.guide_item_2,R.id.guide_item_3})
    List<View> mViewList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guide_second;
    }

    @Override
    public List<View> getParallaxViews() {
        return mViewList;
    }
}
```
```
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initFragmentList();
    mVp.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mFragmentList));

    mVp.setPageTransformer(true, new ParallaxPagerTransformer());

    mArgbEvaluator=new ArgbEvaluator();
    mPageWidth=getWindowManager().getDefaultDisplay().getWidth();
    mTotalPageWidth=mPageWidth*mFragmentList.size();
    mStartColor= getResources().getColor(R.color.guide_start_background);
    mEndColor=getResources().getColor(R.color.guide_end_background);
    mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            MyLog.i(TAG, "onPageScrolled position=%s , positionOffset=%s", position, positionOffset);
            float radio=mPageWidth*(position+positionOffset)/mTotalPageWidth;
            int color= (int) mArgbEvaluator.evaluate(radio,mStartColor,mEndColor);
            mVp.setBackgroundColor(color);
        }

        @Override
        public void onPageSelected(int position) {
            MyLog.i(TAG, "onPageSelected position=%s ", position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    });
}
```