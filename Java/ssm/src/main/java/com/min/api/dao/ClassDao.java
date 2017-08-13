package com.min.api.dao;

import com.min.api.bean.ClassBean;
import com.min.api.bean.UserBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by minyangcheng on 2017/6/25.
 */
@Repository
public interface ClassDao {

    List<ClassBean> queryAll();

    ClassBean queryByName(String name);

    Integer add(ClassBean bean);

    Integer queryClassIdByName(String name);

}
