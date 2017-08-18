package com.min.seed.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SearchBean {

    @Id
    private Long id;

    private String searchStr;

    private Long time;

    private int userid;

    @Generated(hash = 1384194645)
    public SearchBean(Long id, String searchStr, Long time, int userid) {
        this.id = id;
        this.searchStr = searchStr;
        this.time = time;
        this.userid = userid;
    }

    @Generated(hash = 562045751)
    public SearchBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchStr() {
        return this.searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

}
