package com.min.seed.db;

import com.min.seed.app.App;
import com.min.seed.db.dao.DaoMaster;
import com.min.seed.db.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

public class DBOperate {

    private DaoSession daoSession = null;
    private DaoMaster daoMaster = null;
    private final static String DB_NAME = "app.db";

    private static DBOperate singleDB = new DBOperate();

    private DBOperate() {
    }

    public static DBOperate getInstance() {
        return singleDB;
    }

    private DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            GreendaoDBOpenHelper helper = new GreendaoDBOpenHelper(App.getContext(), DB_NAME);
            Database db = helper.getWritableDb();
            daoMaster = new DaoMaster(db);
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }
}
