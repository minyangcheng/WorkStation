package com.min.dagger.demo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    ApplicationBean applicationBean1;
    @Inject
    ApplicationBean applicationBean2;
    @Inject
    ActivityBean activityBean;
    @Inject
    ActivityBean activityBean1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DaggerApplication application = (DaggerApplication) getApplication();
        ApplicationComponent applicationComponent = application.getAppComponent();
        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule("d"))
                .build();
        activityComponent.inject(this);
        Log.d("Dagger", "Activity activityBean:" + activityBean);
        Log.d("Dagger", "Activity activityBean1:" + activityBean1);
        Log.d("Dagger", "Activity applicationBean1:" + applicationBean1);
        Log.d("Dagger", "Activity applicationBean2:" + applicationBean2);
        OtherClass otherClass = new OtherClass();
    }


    class OtherClass {
        @Inject
        ApplicationBean applicationBean1;
        @Inject
        ApplicationBean applicationBean2;
        @Inject
        ActivityBean activityBean;


        public OtherClass() {
            DaggerApplication application = (DaggerApplication) getApplication();
            ApplicationComponent applicationComponent = application.getAppComponent();
            ActivityComponent activityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(applicationComponent)
                    .activityModule(new ActivityModule("d"))
                    .build();
            activityComponent.inject(this);
            Log.d("Dagger", "OtherClass activityBean:" + this.activityBean);
            Log.d("Dagger", "OtherClass applicationBean1:" + this.applicationBean1);
            Log.d("Dagger", "OtherClass applicationBean2:" + this.applicationBean2);
        }
    }
}
