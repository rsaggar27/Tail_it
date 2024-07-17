package com.example.tail_it.measurement_explorer;

import java.util.Date;

public class MeasureBean
{
    int orderid;
    String mobile;
    String dress;
    Date doo;
    Date dod;
    String worker;
    String measurements;

    public MeasureBean() {
    }

    public MeasureBean(int orderid, String mobile, String dress, Date doo, Date dod, String worker, String measurements) {
        this.orderid = orderid;
        this.mobile = mobile;
        this.dress = dress;
        this.doo = doo;
        this.dod = dod;
        this.worker = worker;
        this.measurements = measurements;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDress() {
        return dress;
    }

    public void setDress(String dress) {
        this.dress = dress;
    }

    public Date getDoo() {
        return doo;
    }

    public void setDoo(Date doo) {
        this.doo = doo;
    }

    public Date getDod() {
        return dod;
    }

    public void setDod(Date dod) {
        this.dod = dod;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getMeasurements() {
        return measurements;
    }

    public void setMeasurements(String measurements) {
        this.measurements = measurements;
    }
}
