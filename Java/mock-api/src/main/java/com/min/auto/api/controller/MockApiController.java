package com.min.auto.api.controller;

import com.min.auto.api.bean.Result;
import com.min.auto.api.service.MockApiService;
import com.min.auto.api.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by minyangcheng on 2017/7/28.
 */
@RestController
public class MockApiController {

    @Autowired
    private MockApiService mockApiService;

    @RequestMapping("/**/*")
    public Result mock(HttpServletRequest request, HttpServletResponse response){
        String respData=mockApiService.getRespData(request,response);
        return ResultUtil.success(respData);
    }

}
