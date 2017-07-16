package com.min.dependent;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.min.module_a.ModuleAMainActivity;
import com.min.module_b.ModuleBMainActivity;

import org.kymjs.cjframe.CJActivityUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("APP"+"___"+getString(R.string.from)+"___"+getString(R.string.from_2));

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ModuleAMainActivity.class);
            }
        });
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(ModuleBMainActivity.class);
                return false;
            }
        });
    }

    private void startActivity(Class clazz){
        startActivity(new Intent(this,clazz));
    }

    public void startPluginActivity(View view){
        File file=new File(Environment.getExternalStorageDirectory(),"Test.apk");
        if(file.exists()){
            CJActivityUtils.startPlugin(this,file.getAbsolutePath());
        }
    }

}
