package com.min.main;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.min.main.util.L;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG=MainActivity.class.getSimpleName();

    private DexClassLoader mDexClassLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        installPlugin();
    }

    private void installPlugin(){
        try {
            String plugin=getFilesDir().getAbsolutePath()+"/plugin";

            String dexPath= Environment.getExternalStorageDirectory()+"/Test.apk";
            File dexFile=new File(dexPath);
            if(!dexFile.exists()){
                L.d(TAG,"plugin not found");
                return;
            }

            String optimizedDirectory=plugin+"/opts";
            File optimizedDirFile=new File(optimizedDirectory);
            if(!optimizedDirFile.exists()){
                optimizedDirFile.mkdirs();
            }
            mDexClassLoader=new DexClassLoader(dexPath,optimizedDirectory,null,getClassLoader().getParent());

            dexFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMessage(View view){
        try {
            Class clazz=mDexClassLoader.loadClass("com.min.plugin.Message");
            Method method=clazz.getMethod("getMessage");
            String mess= (String) method.invoke(clazz.newInstance());
            L.d(TAG,"mess=%s",mess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
