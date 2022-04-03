package com.example.bloodship.Models;

public class NestedChildModel {
    String studentID, bloodGroup, discipline, name, phone;

    public NestedChildModel(){

    }

    public NestedChildModel(String studentID, String bloodGroup, String discipline, String name, String phone) {
        this.studentID = studentID;
        this.bloodGroup = bloodGroup;
        this.discipline = discipline;
        this.name = name;
        this.phone = phone;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
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
}
