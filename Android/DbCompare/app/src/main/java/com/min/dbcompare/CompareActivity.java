package com.min.dbcompare;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.min.dbcompare.app.MyApplication;
import com.min.dbcompare.bean.GreendaoPerson;
import com.min.dbcompare.bean.LitepalPerson;
import com.min.dbcompare.bean.OriginalPerson;
import com.min.dbcompare.bean.OrmlitePerson;
import com.min.dbcompare.db.OriginalDBOpenHelper;
import com.min.dbcompare.db.OrmliteDatabaseHelper;
import com.min.dbcompare.util.L;

import org.litepal.crud.DataSupport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompareActivity extends AppCompatActivity {

    private static final String TAG="CompareActivity_TEST";

    @Bind(R.id.tv_origianl_clear)
    TextView mOriginalClearTv;
    @Bind(R.id.tv_origianl_insert)
    TextView mOriginalInsertTv;
    @Bind(R.id.tv_origianl_query)
    TextView mOriginalQueryTv;

    @Bind(R.id.tv_litepal_clear)
    TextView mLitepalClearTv;
    @Bind(R.id.tv_litepal_insert)
    TextView mLitepalInsertTv;
    @Bind(R.id.tv_litepal_query)
    TextView mLitepalQueryTv;

    @Bind(R.id.tv_ormlite_clear)
    TextView mOrmliteClearTv;
    @Bind(R.id.tv_ormlite_insert)
    TextView mOrmliteInsertTv;
    @Bind(R.id.tv_ormlite_query)
    TextView mOrmliteQueryTv;

    @Bind(R.id.tv_greendao_clear)
    TextView mGreendaoClearTv;
    @Bind(R.id.tv_greendao_insert)
    TextView mGreendaoInsertTv;
    @Bind(R.id.tv_greendao_query)
    TextView mGreendaoQueryTv;

    @Bind(R.id.et_insert_num)
    EditText mInsertNum;

    private int NUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompareActivity.this.finish();
            }
        });

        NUM=Integer.parseInt(mInsertNum.getText().toString().trim());
        mInsertNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str=mInsertNum.getText().toString();
                if(TextUtils.isEmpty(str)){
                    NUM=0;
                }else {
                    try {
                        NUM=Integer.parseInt(str.trim());
                    } catch (NumberFormatException e) {
                        NUM=0;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.btn_origianl_clear)
    void clickBtnOriginalClear(){
        long startTime=getNowTime();
        String sql="DELETE FROM person";
        OriginalDBOpenHelper.getInstance().getWritableDatabase().execSQL(sql);
        setTimeStrToView(mOriginalClearTv, startTime, null);
    }

    @OnClick(R.id.btn_origianl_insert)
    void clickBtnOriginalInsert(){
        List<String> sqlList=new ArrayList<>();
        String sql=null;
        for (int i=0;i<NUM;i++){
            sql="INSERT INTO person(name,age) VALUES('%1$s',12)";
            sql=String.format(sql,"minyangcheng_"+i);
            sqlList.add(sql);
        }

        long startTime=getNowTime();
        SQLiteDatabase db=OriginalDBOpenHelper.getInstance().getWritableDatabase();
        db.beginTransaction();
        try {
            for(int i=0;i<sqlList.size();i++){
                db.execSQL(sqlList.get(i));
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            L.e(TAG,e);
        }finally {
            db.endTransaction();
        }
        setTimeStrToView(mOriginalInsertTv,startTime,null);
    }

    @OnClick(R.id.btn_origianl_query)
    void clickBtnOriginalQuery(){
        long startTime=getNowTime();
        String sql="SELECT * from person";
        Cursor cursor=OriginalDBOpenHelper.getInstance()
                .getWritableDatabase().rawQuery(sql, null);
        List<OriginalPerson> personList=new ArrayList<>();
        OriginalPerson person=null;
        String name=null;
        int age=0;
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                name=cursor.getString(1);
                age=cursor.getInt(2);
                person=new OriginalPerson();
                person.setName(name);
                person.setAge(age);
                personList.add(person);
            }
        }
        setTimeStrToView(mOriginalQueryTv,startTime,"count="+personList.size());
    }

    @OnClick(R.id.btn_litepal_clear)
    void clickBtnLitepalClear(){
        long startTime=getNowTime();
        DataSupport.deleteAll(LitepalPerson.class);
        setTimeStrToView(mLitepalClearTv, startTime, null);
    }

    @OnClick(R.id.btn_litepal_insert)
    void clickBtnLitepalInsert(){
        List<LitepalPerson> personList=new ArrayList<>();
        LitepalPerson person=null;
        for(int i=0;i<NUM;i++){
            person=new LitepalPerson();
            person.setName("minyangcheng_"+i);
            person.setId(i);
            personList.add(person);
        }
        long startTime=getNowTime();
        DataSupport.saveAll(personList);
        setTimeStrToView(mLitepalInsertTv, startTime, null);
    }

    @OnClick(R.id.btn_litepal_query)
    void clickBtnLitepalQuery(){
        long startTime=getNowTime();
        List<LitepalPerson> personList=DataSupport.findAll(LitepalPerson.class);
        setTimeStrToView(mLitepalQueryTv, startTime, "count="+personList.size());
    }

    @OnClick(R.id.btn_ormlite_clear)
    void clickBtnOrmliteClear(){
        long startTime=getNowTime();
        try {
            OrmliteDatabaseHelper.getInstance()
                    .getPerSonDao()
                    .deleteBuilder()
                    .delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setTimeStrToView(mOrmliteClearTv, startTime, null);
    }

    @OnClick(R.id.btn_ormlite_insert)
    void clickBtnOrmliteInsert(){
        final List<OrmlitePerson> personList=new ArrayList<>();
        OrmlitePerson person=null;
        for(int i=0;i<NUM;i++){
            person=new OrmlitePerson();
            person.setName("minyangcheng_"+i);
            person.setAge(i);
            personList.add(person);
        }

        long startTime=getNowTime();
        try {
            OrmliteDatabaseHelper.getInstance()
                    .getPerSonDao()
                    .callBatchTasks(new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            for (int i = 0; i < personList.size(); i++) {
                                L.i(TAG,"ormlite insert thread id=%s",Thread.currentThread().getId());
                                OrmliteDatabaseHelper.getInstance()
                                        .getPerSonDao()
                                        .create(personList.get(i));
                            }
                            return null;
                        }
                    });
        } catch (Exception e) {
            L.e(TAG, e);
        }
        setTimeStrToView(mOrmliteInsertTv, startTime, null);
    }

    @OnClick(R.id.btn_ormlite_query)
    void clickBtnOrmliteQuery(){
        long startTime=getNowTime();
        int size=-1;
        try {
            List<OrmlitePerson> personList=OrmliteDatabaseHelper.getInstance()
                    .getPerSonDao()
                    .queryForAll();
            if(personList!=null){
                size=personList.size();
            }
        } catch (SQLException e) {
            L.e(TAG,e);
        }
        setTimeStrToView(mOrmliteQueryTv, startTime, "count="+size);
    }

    @OnClick(R.id.btn_greendao_clear)
    void clickBtnGreendaoClear(){
        long startTime=getNowTime();
        MyApplication.getDaoSession().getGreendaoPersonDao().deleteAll();
        setTimeStrToView(mGreendaoClearTv, startTime, null);
    }

    @OnClick(R.id.btn_greendao_insert)
    void clickBtnGreendaoInsert(){
        final List<GreendaoPerson> personList=new ArrayList<>();
        GreendaoPerson person=null;
        for(int i=0;i<NUM;i++){
            person=new GreendaoPerson();
            person.setName("minyangcheng_"+i);
            person.setAge(i);
            personList.add(person);
        }

        long startTime=getNowTime();
        MyApplication.getDaoSession()
                .getGreendaoPersonDao()
                .insertInTx(personList);
        setTimeStrToView(mGreendaoInsertTv, startTime, null);
    }

    @OnClick(R.id.btn_greendao_query)
    void clickBtnGreendaoQuery(){
        long startTime=getNowTime();
        int size=-1;
        List<GreendaoPerson> personList=MyApplication.getDaoSession()
                .getGreendaoPersonDao().loadAll();
        if(personList!=null){
            size=personList.size();
        }
        setTimeStrToView(mGreendaoQueryTv, startTime, "count="+size);
    }

    private void setTimeStrToView(TextView tv,long startTime,String appendStr){
        String str="耗时:"+String.valueOf(getNowTime()-startTime)+"ms";
        if(!TextUtils.isEmpty(appendStr)){
            str=str+"    "+appendStr;
        }
        tv.setText(str);
    }

    private long getNowTime(){
        return System.currentTimeMillis();
    }

}
