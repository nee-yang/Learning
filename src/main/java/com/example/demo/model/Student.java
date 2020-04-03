package com.example.demo.model;
import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable{
    private int sid;
    private String sname;
    private Date date_of_birth;
    private String gender;

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public String getSname() {

        return sname;
    }

    public String getGender() {
        return gender;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSid() {
        return sid;
    }
}
