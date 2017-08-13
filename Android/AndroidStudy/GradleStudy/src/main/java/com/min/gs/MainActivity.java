package com.min.gs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tv)
    TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initInfo();
    }

    private void initInfo() {
        StringBuilder sb=new StringBuilder();
        String appId="appId:"+BuildConfig.APPLICATION_ID+"\n";
        String versionName="versionName:"+BuildConfig.VERSION_NAME+" vercode:"+BuildConfig.VERSION_CODE+"\n";
        String FLAVOR="flavor:"+BuildConfig.FLAVOR+"\n";
        String buildType="buildType:"+BuildConfig.BUILD_TYPE+"\n";
        String umengValue= "umengValue:"+PackageUtils.getMetaData(this,"UMENG_CHANNEL_VALUE")+"\n";
        String packageName="packageName:"+this.getPackageName()+"\n";
        String buildTime="buildTime:"+getString(R.string.build_time)+"\n";
        String buildHost="buildHost:"+getString(R.string.build_host)+"\n";
        String debug="debug:"+BuildConfig.DEBUG+"\n";
        sb.append(appId)
                .append(versionName)
                .append(FLAVOR)
                .append(buildType)
                .append(umengValue)
                .append(packageName)
                .append(buildTime)
                .append(buildHost)
                .append(debug);
        mTv.setText(sb.toString());
    }
}
