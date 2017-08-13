package com.min.api.bean;

import java.util.List;

/**
 * Created by minyangcheng on 2017/6/25.
 */
public class ClassBean {

    private long id;
    private String name;
    private List<StudentBean> studentList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StudentBean> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentBean> studentList) {
        this.studentList = studentList;
    }
}
