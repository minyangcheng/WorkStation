package com.min.repository;

import com.min.bean.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by minyangcheng on 2017/8/6.
 */
public interface PersonRepository extends JpaRepository<Person,Long> {
}
