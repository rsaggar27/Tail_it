package com.example.tail_it.show_worker;

import java.util.Date;

public class WorkerBean {
    String wname;
    String mobile;
    String gender;
    Date doe;
    String splz;

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDoe() {
        return doe;
    }

    public void setDoe(Date doe) {
        this.doe = doe;
    }

    public String getSplz() {
        return splz;
    }

    public void setSplz(String splz) {
        this.splz = splz;
    }

    public WorkerBean(String wname, String mobile, String gender, Date doe, String splz) {
        this.wname = wname;
        this.mobile = mobile;
        this.gender = gender;
        this.doe = doe;
        this.splz = splz;
    }

    public WorkerBean() {
    }
}
