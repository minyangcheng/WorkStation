package com.min.seed.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.facebook.stetho.common.LogUtil;
import com.min.seed.db.dao.DaoMaster;

import org.greenrobot.greendao.database.StandardDatabase;

/**
 * Created by minyangcheng on 2016/8/5.
 */
public class GreendaoDBOpenHelper extends DaoMaster.OpenHelper {

    private static final String TAG = "GreendaoDBOpenHelper";

    public GreendaoDBOpenHelper(Context context, String dbName) {
        super(context, dbName, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.d(TAG, "数据库升级 oldVersion=%s,newVersion=%s", oldVersion, newVersion);
        StandardDatabase database = new StandardDatabase(db);
    }

}
