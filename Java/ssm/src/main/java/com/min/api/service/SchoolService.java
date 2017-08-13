package com.min.api.service;

import com.min.api.bean.ClassBean;
import com.min.api.bean.StudentBean;
import com.min.api.dao.ClassDao;
import com.min.api.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by minyangcheng on 2017/6/25.
 */
@Service
public class SchoolService {

    @Autowired
    private ClassDao classDao;
    @Autowired
    private StudentDao studentDao;

    /**
     * 查询所有班级
     * @return
     */
    public List<ClassBean> queryAllClass(){
        return classDao.queryAll();
    }

    /**
     * 查询指定班级的所有学生
     * @param name
     * @return
     */
    public List<StudentBean> queryStudentByClassName(String name){
        ClassBean classBean=classDao.queryByName(name);
        if(classBean==null) return null;
        return classBean.getStudentList();
    }

    /**
     * 添加班级
     * @param name
     * @return
     */
    public int addClassBean(String name){
        Integer classId=classDao.queryClassIdByName(name);
        if(classId==null){
            ClassBean classBean=new ClassBean();
            classBean.setName(name);
            return classDao.add(classBean);
        }else{
            return 0;
        }
    }

    /**
     * 向指定班级中添加学生
     * @param name
     * @param studentBean
     * @return
     */
    public int addStudentToClass(String name,StudentBean studentBean){
        Integer classId=classDao.queryClassIdByName(name);
        if(classId!=null){
            studentBean.setcId(classId);
            return studentDao.add(studentBean);
        }
        return 0;
    }

    /**
     * 查询带有特殊word，并且age>10的学生
     * @param word
     * @return
     */
    public List<StudentBean> queryStudentByWord(String word,int age){
        return studentDao.queryByWordAndAge(word,age);
    }

}
