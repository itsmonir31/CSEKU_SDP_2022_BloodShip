package com.example.bloodship.Models;

public class ModelReport {
    String name, bg, reqDate, total, throughApp;
    int archive, reqID;

    public ModelReport(){

    }

    public ModelReport(String name, String bg, String reqDate, String total, String throughApp, int archive, int reqID) {
        this.name = name;
        this.bg = bg;
        this.reqDate = reqDate;
        this.total = total;
        this.throughApp = throughApp;
        this.archive = archive;
        this.reqID = reqID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getThroughApp() {
        return throughApp;
    }

    public void setThroughApp(String throughApp) {
        this.throughApp = throughApp;
    }

    public int getArchive() {
        return archive;
    }

    public void setArchive(int archive) {
        this.archive = archive;
    }

    public int getReqID() {
        return reqID;
    }

    public void setReqID(int reqID) {
        this.reqID = reqID;
    }
}
