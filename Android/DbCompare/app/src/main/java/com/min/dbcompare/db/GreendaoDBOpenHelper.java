package com.min.dbcompare.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.min.dbcompare.app.MyApplication;
import com.min.dbcompare.dao.DaoMaster;
import com.min.dbcompare.dao.StudentDao;
import com.min.dbcompare.util.L;
import com.min.dbcompare.util.MigrationHelper;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;

/**
 * Created by minyangcheng on 2016/8/5.
 */
public class GreendaoDBOpenHelper extends DaoMaster.OpenHelper {

    private static final String TAG="GreendaoDBOpenHelper";

    public GreendaoDBOpenHelper(Context context,SQLiteDatabase.CursorFactory factory) {
        super(context, "greendao_db.db", factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        L.d(TAG,"数据库升级 oldVersion=%s,newVersion=%s",oldVersion,newVersion);
        StandardDatabase database=new StandardDatabase(db);
        MigrationHelper.migrate(database, StudentDao.class);
    }

}
