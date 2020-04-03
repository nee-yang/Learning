package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private int oid;
    private Date createTime;
    private String number;
    private String note;
    private User user;
    private  int uid;

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOid() {
        return oid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getNumber() {
        return number;
    }

    public String getNote() {
        return note;
    }

    public User getUser() {
        return user;
    }
}
