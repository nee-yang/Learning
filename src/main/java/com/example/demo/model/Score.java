package com.example.demo.model;

import java.io.Serializable;

public class Score implements Serializable {
    private int sid;
    private int cid;
    private double score;

    public void setSid(int sid) {
        this.sid = sid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getSid() {
        return sid;
    }

    public int getCid() {
        return cid;
    }

    public double getScore() {
        return score;
    }
}
