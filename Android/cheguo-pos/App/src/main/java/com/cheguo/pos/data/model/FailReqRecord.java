package com.cheguo.pos.data.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FailReqRecord {

    @Id
    private Long id;

    private String jsonStr;

    @Generated(hash = 166707904)
    public FailReqRecord(Long id, String jsonStr) {
        this.id = id;
        this.jsonStr = jsonStr;
    }

    @Generated(hash = 106345895)
    public FailReqRecord() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJsonStr() {
        return this.jsonStr;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

}
