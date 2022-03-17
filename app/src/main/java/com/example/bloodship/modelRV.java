package com.example.bloodship;

public class modelRV {
    String bloodGroup, discipline, lastDonate, homeDistrict, name, phone, reqDate;

    //int id;
    public modelRV(){

    }

    public modelRV(String bloodGroup, String discipline, String homeDistrict, String lastDonate, String name, String phone, String reqDate) {
        this.bloodGroup = bloodGroup;
        this.discipline = discipline;
        this.homeDistrict = homeDistrict;
        this.lastDonate = lastDonate;
        this.name = name;
        this.phone = phone;
        this.reqDate = reqDate;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getHomeDistrict() {
        return homeDistrict;
    }

    public void setHomeDistrict(String homeDistrict) {
        this.homeDistrict = homeDistrict;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastDonate() {
        return lastDonate;
    }

    public void setLastDonate(String lastDonate) {
        this.lastDonate = lastDonate;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }
}
