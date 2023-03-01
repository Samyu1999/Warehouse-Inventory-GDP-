package com.example.warehouseinventory.model;

import java.util.ArrayList;
import java.util.Date;

public class RequestModel {
    String name;
    String username;
    String status;
    String address;
    String rid, pid, uid;
    Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRid() {
        return rid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RequestModel(String name, String status, String address, String rid, Date date) {
        this.name = name;
        this.status = status;
        this.address = address;
        this.rid = rid;
        this.date = date;
    }
    public RequestModel(String name, String status, String address, Date date, String username, String pid, String uid, String rid) {
        this.username = username;
        this.name = name;
        this.status = status;
        this.address = address;
        this.pid = pid;
        this.uid = uid;
        this.rid = rid;
        this.date = date;
    }
}
