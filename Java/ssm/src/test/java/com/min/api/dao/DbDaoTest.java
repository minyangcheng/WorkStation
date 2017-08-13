package com.min.api.dao;

import com.min.api.BaseTest;
import com.min.api.bean.StudentBean;
import com.min.api.service.SchoolService;
import com.min.api.util.GsonUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DbDaoTest extends BaseTest{

    private Logger logger= LoggerFactory.getLogger(DbDaoTest.class);

    @Autowired
    private SchoolService schoolService;
    @Autowired
    ClassDao classDao;

    @Test
    public void testQueryAll(){
        logger.debug("=======================================================");
        Object result=schoolService.queryAllClass();
        if(result==null){
            logger.debug("no result for return");
        }else {
            logger.debug(GsonUtil.toPrettyJson(result));
        }
        logger.debug("=======================================================");
    }

    @Test
    public void testQueryByClassName(){
        logger.debug("=======================================================");
        Object result=schoolService.queryStudentByClassName("三班");
        if(result==null){
            logger.debug("no result for return");
        }else {
            logger.debug(GsonUtil.toPrettyJson(result));
        }
        logger.debug("=======================================================");
    }

    @Test
    public void testAddClassBean(){
        logger.debug("成功插入："+schoolService.addClassBean("五班")+"条");
    }

    @Test
    public void testQueryNameAndAge(){
        logger.debug("=======================================================");
        Object result=schoolService.queryStudentByWord("张",10);
        if(result==null){
            logger.debug("no result for return");
        }else {
            logger.debug(GsonUtil.toPrettyJson(result));
        }
        logger.debug("=======================================================");
    }

    @Test
    public void testClassDao(){
        Integer id=classDao.queryClassIdByName("三班");
        logger.debug("---------> id="+id);
    }

    @Test
    public void addStudentToClass(){
        StudentBean studentBean=new StudentBean();
        studentBean.setName("min_1");
        studentBean.setAge(12);
        int num=schoolService.addStudentToClass("三班",studentBean);
        logger.debug("---------> num="+num);
    }

}
