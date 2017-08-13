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

import org.greenrobot.greendao.query.CloseableListIterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GreendaoActivity extends AppCompatActivity {

    private static final String TAG="GreendaoActivity_TEST";

    private StudentDao mStudentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
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
                GreendaoActivity.this.finish();
            }
        });
    }

    @OnClick(R.id.btn_insert)
    void clickBtnInsert(){
        List<Student> studentList=new ArrayList<>();
        for(int i=0;i<40;i++){
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

    @OnClick(R.id.btn_insert_or_replace)
    void clickBtnInsertOrReplace(){
        Student student=new Student();
        student.setId(1L);
        student.setName("zhang_" + 0);
        student.setAge(0);
        student.setScore(0 + 0.3);
        student.setBirth(new Date());
        mStudentDao.insertOrReplace(student);
    }

    @OnClick(R.id.btn_delete)
    void clickBtnDelete(){
        mStudentDao.deleteAll();
//        mStudentDao.deleteByKey(12L);
        L.i(TAG, "deleteAll success");
    }

    @OnClick(R.id.btn_update)
    void clickBtnUpdate(){
        Student student=new Student();
        student.setId(12L);
        student.setName("zhang");
        student.setAge(13);
        student.setScore(1.4);
        student.setBirth(new Date());
        //student必须在设置了主键
        mStudentDao.update(student);
        L.i(TAG, "update success");
    }

    @OnClick(R.id.btn_query_raw)
    void clickBtnQueryRaw(){
        List<Student> studentList=mStudentDao.queryRaw("where AGE>=? and AGE<=? ORDER BY _id DESC LIMIT 2 OFFSET 1", new String[]{"20","25"});
        i(studentList, "clickBtnQueryRaw");
    }

    @OnClick(R.id.btn_query_builder)
    void clickBtnQueryBuilder(){
//        List<Student> studentList=mStudentDao.queryBuilder()
//                .where(StudentDao.Properties.Age.eq(24), StudentDao.Properties.Name.eq("min_24"))
//                .list();
//        List<Student> studentList=mStudentDao.queryBuilder()
//                .whereOr(StudentDao.Properties.Age.le(5), StudentDao.Properties.Score.gt(38))
//                .orderDesc(StudentDao.Properties.Age)
//                .list();
        List<Student> studentList=mStudentDao.queryBuilder()
                .where(StudentDao.Properties.Age.le(3))
                .list();
        i(studentList, "clickBtnQueryBuilder");
    }

    @OnClick(R.id.btn_query_iterator)
    void clickBtnQuery(){
        try {
            CloseableListIterator<Student> iterator=mStudentDao.queryBuilder()
                    .where(StudentDao.Properties.Age.le(3))
                    .build()
                    .listIterator();
            while (iterator.hasNext()){
                Student student=iterator.next();
                L.i(TAG,"id=%s,name=%s",student.getId(),student.getName());
            }
            iterator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_batch_delete)
    void clickBtnBatchDelete(){
        mStudentDao.queryBuilder()
                .where(StudentDao.Properties.Score.gt(38))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
        L.i(TAG, "batch delete success");
    }

    @OnClick(R.id.btn_query_by_original)
    void clickBtnQueryByOriginal(){
        /*
        * 更新插入数据最好用greendao自带的api进行操作。因为greendao自带有对象缓存机制，如果用下面这种方式更新数据库表后，在
        * 操作完成后，查询处理出来的数据还是保持原样,当然也可以每次查询之前调用getDaoSession().clear(),推荐用greendao api
        * 进行更新数据操作。
        * 官方说明:http://greenrobot.org/greendao/documentation/sessions/
        */
        mStudentDao.getDatabase().execSQL("UPDATE STUDENT SET stuName='zhang' WHERE _id=1");
//        Student student=new Student(1L,"zhang",0,0.0,null);
//        mStudentDao.update(student);
        L.i(TAG, "original sql execute success");
    }

    @OnClick(R.id.btn_query_blur)
    void clickBtnQueryBlur(){
        List<Student> studentList=mStudentDao.queryRaw("where stuName LIKE '%in%' LIMIT 2", new String[]{});
        i(studentList, "clickBtnQueryBlur");
    }

    @OnClick(R.id.btn_test)
    void clickBtnTest(){
        final Student student=new Student();
        student.setAge(100);
        student.setAddress("hangzhou");
        student.setBirth(new Date());
        mStudentDao.save(student);
        findViewById(R.id.btn_test)
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        student.delete();
                    }
                },10000);
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
