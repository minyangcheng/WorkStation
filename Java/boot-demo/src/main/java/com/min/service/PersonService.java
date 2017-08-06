package com.min.service;

import com.min.bean.Person;
import com.min.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by minyangcheng on 2017/8/6.
 */
@Service
public class PersonService {

    private Logger logger= LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Cacheable("books")
    public Person cache(){
        logger.info("findOne called....");
        return personRepository.findOne(1L);
    }

}
