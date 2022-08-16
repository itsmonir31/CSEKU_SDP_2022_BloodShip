package com.example.bloodship.Models;

public class NestedParentModel {
    String Problem;
    String bg;
    String quantity;
    String time;
    String date;
    String address;
    String a_contact;
    String r_contact;
    String req_date, need, managed, othersM;

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    String reqID;

    public NestedParentModel(){

    }

    public NestedParentModel(String problem, String bg, String quantity, String time, String date, String address, String a_contact, String r_contact, String req_date, String reqID, String need, String Managed, String othersM) {
        Problem = problem;
        this.bg = bg;
        this.quantity = quantity;
        this.time = time;
        this.date = date;
        this.address = address;
        this.a_contact = a_contact;
        this.r_contact = r_contact;
        this.req_date = req_date;
        this.reqID = reqID;
        this.need = need;
        this.managed = managed;
        this.othersM = othersM;
    }

    public String getProblem() {
        return Problem;
    }

    public void setProblem(String problem) {
        Problem = problem;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getA_contact() {
        return a_contact;
    }

    public void setA_contact(String a_contact) {
        this.a_contact = a_contact;
    }

    public String getR_contact() {
        return r_contact;
    }

    public void setR_contact(String r_contact) {
        this.r_contact = r_contact;
    }

    public String getReq_date() {
        return req_date;
    }

    public void setReq_date(String req_date) {
        this.req_date = req_date;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getManaged() {
        return managed;
    }

    public void setManaged(String managed) {
        this.managed = managed;
    }

    public String getOthersM() {
        return othersM;
    }

    public void setOthersM(String othersM) {
        this.othersM = othersM;
    }
}
