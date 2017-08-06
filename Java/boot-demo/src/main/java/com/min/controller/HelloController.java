package com.min.controller;

import com.min.bean.ConfigBean;
import com.min.bean.Person;
import com.min.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by minyangcheng on 2017/8/4.
 */
@RestController
public class HelloController {

    private Logger logger= LoggerFactory.getLogger(HelloController.class);

    @Value("${config.name}")
    private String name;
    @Autowired
    private ConfigBean config;
    @Autowired
    private PersonService personService;

    @RequestMapping("/hello")
    public String hello(){
        return "hello minych";
    }

    @RequestMapping("/config")
    public String readConfig(){
        return name+"  "+config.getCount();
    }

    @RequestMapping("/cache")
    public Person cache(){
        return personService.cache();
    }

}
