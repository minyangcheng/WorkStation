package com.min.dbcompare.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.min.dbcompare.app.MyApplication;
import com.min.dbcompare.bean.OrmlitePerson;

import java.sql.SQLException;

public class OrmliteDatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "ormlite_db.db";
	private static final int DATABASE_VERSION = 1;

	private static OrmliteDatabaseHelper databaseHelper;

	private Dao<OrmlitePerson, Integer> personDao = null;

	public OrmliteDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static OrmliteDatabaseHelper getInstance(){
		if(databaseHelper==null){
			synchronized (OrmliteDatabaseHelper.class){
				if(databaseHelper==null){
					databaseHelper=new OrmliteDatabaseHelper(MyApplication.getContext());
				}
			}
		}
		return databaseHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, OrmlitePerson.class);
		} catch (SQLException e) {
			Log.e(OrmliteDatabaseHelper.class.getName(), "Can't create database", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, OrmlitePerson.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(OrmliteDatabaseHelper.class.getName(), "Can't drop databases", e);
		}
	}

	public Dao<OrmlitePerson, Integer> getPerSonDao() throws SQLException {
		if (personDao == null) {
			personDao = getDao(OrmlitePerson.class);
		}
		return personDao;
	}

	@Override
	public void close() {
		super.close();
		personDao = null;
	}
}
