package com.example.prajna.employee;

import java.io.Serializable;

/**
 * Created by prajna on 24/8/16.
 */
public class Employee implements Serializable {
    private String First_name;
    private String Last_name;
    private String email;
    private String Mobile;
    private String DOB;
    private String Company;
    private String Dept;
    private String Designation;
    private String Add;

    public String getFirst_name() {
        return First_name;
    }

    public void setFirst_name(String firstname) {
        First_name = firstname;
    }

    public String getLast_name() {
        return Last_name;
    }

    public void setLast_name(String lastname) {
        Last_name = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getDepartment() {
        return Dept;
    }

    public void setDepartment(String dept) {
        Dept = dept;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String desig) {
        Designation = desig;
    }

    public String getAddress() {
        return Add;
    }

    public void setAddress(String add) {
        Add = add;
    }
}