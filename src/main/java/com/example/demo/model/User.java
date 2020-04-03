package com.example.demo.model;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private int uid;
    private String uname;
    private String address;
    private String  birthday;
    private String sex;
    private List<Order> orderList;

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getUid() {
        return uid;
    }

    public String getUname() {
        return uname;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }
}
