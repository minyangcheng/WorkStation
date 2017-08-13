package com.min.dbcompare;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.min.dbcompare.app.MyApplication;
import com.min.dbcompare.bean.Student;
import com.min.dbcompare.dao.StudentDao;
import com.min.dbcompare.util.L;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DbUpgradeActivity extends AppCompatActivity {

    private static final String TAG="DbUpgradeActivity";

    private StudentDao mStudentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_upgrade);
        ButterKnife.bind(this);
        initViews();

        //greenDao debug的Tag greenDao
        mStudentDao=MyApplication.getDaoSession().getStudentDao();
        mStudentDao.queryBuilder().LOG_SQL=true;
        mStudentDao.queryBuilder().LOG_VALUES=true;
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbUpgradeActivity.this.finish();
            }
        });
    }

    @OnClick(R.id.btn_insert)
    void clickBtnInsert(){
        //数据库版本为1时，添加数据
        List<Student> studentList=new ArrayList<>();
        for(int i=0;i<5;i++){
            Student student=new Student();
            student.setName("min_"+i);
            student.setAge(i);
            student.setScore(i+0.3);
            student.setBirth(new Date());
            studentList.add(student);
        }
        mStudentDao.insertInTx(studentList);
        L.i(TAG, "insert success");
    }

    @OnClick(R.id.btn_query_all)
    void clickBtnQueryAll(){
        //当数据库升级的时候，student表中还存在之前的数据
        List<Student> studentList=mStudentDao.loadAll();
        i(studentList,"clickBtnQueryAll");
    }

    private void i(List<Student> dataList,String methodName){
        if(dataList==null||dataList.size()==0){
            return;
        }
        L.i(TAG, "----------" + methodName+"----------");
        for (Student student : dataList){
            L.i(TAG, "id=%s,name=%s,age=%s,score=%s", student.getId()
                    , student.getName(), student.getAge(),student.getScore());
        }
        L.i(TAG, "----------------------------------------------------------");
    }

}
