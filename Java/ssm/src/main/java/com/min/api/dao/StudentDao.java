package com.min.api.dao;

import com.min.api.bean.ClassBean;
import com.min.api.bean.StudentBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by minyangcheng on 2017/6/25.
 */
@Repository
public interface StudentDao {

    List<StudentBean> queryByWordAndAge(@Param("word") String word, @Param("age")int age);

    Integer add(StudentBean studentBean);

}
