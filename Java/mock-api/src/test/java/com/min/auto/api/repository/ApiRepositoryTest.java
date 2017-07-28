package com.min.auto.api.repository;

import com.google.gson.Gson;
import com.min.auto.api.util.GsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by minyangcheng on 2017/7/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiRepositoryTest {

    private Logger logger= LoggerFactory.getLogger(ApiRepositoryTest.class);

    @Autowired
    private ApiRepository apiRepository;

    @Test
    public void test(){
        logger.debug(GsonUtil.toPrettyJson(apiRepository.findAll()));
    }

}