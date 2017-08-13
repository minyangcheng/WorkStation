package com.min.api.controller;

import com.min.api.bean.StudentBean;
import com.min.api.bean.UserBean;
import com.min.api.dto.Result;
import com.min.api.enums.SchoolEnum;
import com.min.api.service.SchoolService;
import com.min.api.util.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by minyangcheng on 2017/6/25.
 */
@Controller
@RequestMapping("/mobile")
public class MobileApi {

    Logger logger = LoggerFactory.getLogger(MobileApi.class);

    @Autowired
    private SchoolService schoolService;

    @RequestMapping(value = "/hello")
    public void hello(String name,HttpServletResponse response) {
        logger.debug("hello------------before");
        logger.debug("name="+name);
        String result= "你好 " + name + " 先生！";
        ResponseUtils.renderText(response,result);
        logger.debug("hello------------after");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(@RequestParam("username") String username, String userpass, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", new UserBean(username, userpass));
        map.put("group", 1);
        return map;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request,HttpServletResponse response) {
        String path = request.getServletContext().getRealPath("upload");
        String fileName = multipartFile.getOriginalFilename();
        File targetFile = new File(path, fileName);
        logger.debug("filePath=" + targetFile.getAbsolutePath());
        logger.debug("fileUrl=" + request.getContextPath() + "/upload/" + fileName);
        if (!targetFile.getParentFile().exists()) {
            targetFile.mkdirs();
        }
        try {
            if (targetFile.exists()) {
                targetFile.delete();
            }
            multipartFile.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseUtils.renderText(response,"上传文件成功，存放路径为：" + targetFile.getAbsolutePath());
    }

    @RequestMapping(value = "queryStudent")
    @ResponseBody
    public Result<List<StudentBean>> queryStudent(String name,int age){
        Result<List<StudentBean>> result=null;
        if(StringUtils.isBlank(name)){
            result=new Result<List<StudentBean>>(SchoolEnum.PARAM_ERROR.getCode(),SchoolEnum.PARAM_ERROR.getMessage());
        }else{
            List<StudentBean> studentBeanList=schoolService.queryStudentByWord(name,age);
            if(studentBeanList==null||studentBeanList.size()==0){
                result=new Result<List<StudentBean>>(SchoolEnum.NOT_FIND.getCode(),SchoolEnum.NOT_FIND.getMessage());
            }else{
                result=new Result<List<StudentBean>>(studentBeanList,SchoolEnum.SUCCESS.getMessage());
            }
        }
        return result;
    }

}
