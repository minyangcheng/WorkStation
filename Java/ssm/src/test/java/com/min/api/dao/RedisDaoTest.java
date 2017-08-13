package com.min.api.dao;

import com.min.api.BaseTest;
import com.min.api.bean.StudentBean;
import com.min.api.bean.UserBean;
import com.min.api.util.GsonUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

/**
 * Created by minyangcheng on 2017/6/25.
 */
public class RedisDaoTest extends BaseTest {

    private Logger logger= LoggerFactory.getLogger(RedisDaoTest.class);

    @Resource
    private RedisTemplate<String,UserBean> redisTemplate;

    @Test
    public void save() {
        /*redisTemplate.opsForList();
        redisTemplate.opsForSet();
        redisTemplate.opsForHash()*/
        UserBean userBean=new UserBean();
        userBean.setId(1);
        userBean.setUsername("minych");
        userBean.setUserpass("123");
        ValueOperations<String, UserBean> valueOper = redisTemplate.opsForValue();
        valueOper.set(userBean.getId()+"", userBean);
    }

    @Test
    public void read() {
        ValueOperations<String, UserBean> valueOper = redisTemplate.opsForValue();
        UserBean userBean= valueOper.get(1+"");
        logger.debug(GsonUtil.toPrettyJson(userBean));
    }

    @Test
    public void delete() {
        ValueOperations<String, UserBean> valueOper = redisTemplate.opsForValue();
        RedisOperations<String,UserBean> RedisOperations  = valueOper.getOperations();
        RedisOperations.delete(1+"");
    }

}
