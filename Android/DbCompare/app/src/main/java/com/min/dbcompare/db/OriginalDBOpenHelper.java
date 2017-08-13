package com.min.dbcompare.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.min.dbcompare.app.MyApplication;

/**
 * Created by minyangcheng on 2016/7/30.
 */
public class OriginalDBOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="orginal_db";
    public static final int VERSION=1;

    private static OriginalDBOpenHelper helper;

    public OriginalDBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static OriginalDBOpenHelper getInstance(){
        if(helper==null){
            synchronized (OriginalDBOpenHelper.class){
                if(helper==null){
                    helper=new OriginalDBOpenHelper(MyApplication.getContext());
                }
            }
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE person ( id INTEGER PRIMARY KEY AUTOINCREMENT,name text,age INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
