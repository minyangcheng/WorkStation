package com.min.auto.api.repository;

import com.min.auto.api.bean.ApiBean;
import com.min.auto.api.bean.Girl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepository extends JpaRepository<ApiBean, Integer> {

    public ApiBean findByPath(String path);

}
