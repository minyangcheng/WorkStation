package com.min.dbcompare.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.min.dbcompare.dao.DaoMaster;
import com.min.dbcompare.dao.DaoSession;
import com.min.dbcompare.db.GreendaoDBOpenHelper;

import org.litepal.LitePalApplication;

/**
 * Created by minyangcheng on 2016/7/31.
 */
public class MyApplication extends Application {

    private static MyApplication mContext;

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        LitePalApplication.initialize(this);
    }

    public static MyApplication getContext(){
        return mContext;
    }

    private static DaoMaster getDaoMaster(final Context context) {
        if (daoMaster == null) {
            GreendaoDBOpenHelper helper=new GreendaoDBOpenHelper(context,null);
            SQLiteDatabase db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(MyApplication.getContext());
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

}
