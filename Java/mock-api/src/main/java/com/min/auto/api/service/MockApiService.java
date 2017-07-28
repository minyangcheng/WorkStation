package com.min.auto.api.service;

import com.google.gson.reflect.TypeToken;
import com.min.auto.api.bean.ApiBean;
import com.min.auto.api.enums.ResultEnum;
import com.min.auto.api.exception.ServerException;
import com.min.auto.api.repository.ApiRepository;
import com.min.auto.api.util.GsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by minyangcheng on 2017/7/28.
 */
@Service
public class MockApiService {

    private Logger logger= LoggerFactory.getLogger(MockApiService.class);

    @Autowired
    private ApiRepository apiRepository;

    public String getRespData(HttpServletRequest request, HttpServletResponse response){
        String path=request.getRequestURI();
        ApiBean apiBean=apiRepository.findByPath(path);
        if(apiBean!=null){
            checkParams(request,apiBean);
            return apiBean.getRespData();
        }else{
            throw new ServerException(ResultEnum.NOT_FIND_API.getCode(),ResultEnum.NOT_FIND_API.getMessage());
        }
    }

    private void checkParams(HttpServletRequest request,ApiBean apiBean) {
        Map<String,String[]> reqDataMap=request.getParameterMap();
        if(reqDataMap!=null&& StringUtils.isNotEmpty(apiBean.getRespData())){
            Map<String,String> needMap=GsonUtil.gson.fromJson(apiBean.getReqData()
                    , new TypeToken<Map<String, String>>(){}.getType());
            Set<String> needSet=needMap.keySet();
            Set<String> reqParams=reqDataMap.keySet();
            Iterator<String> iterator=needSet.iterator();
            String s;
            while (iterator.hasNext()){
                s=iterator.next();
                if(!reqParams.contains(s)){
                    throw new ServerException(ResultEnum.FAIL.getCode(),"缺少参数"+s);
                }
            }
        }
    }

}
