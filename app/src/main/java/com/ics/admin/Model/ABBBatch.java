package com.ics.admin.Model;

public class ABBBatch {
    private String userId;
    private String batch;
    private  String  class_id;
    private  String  actual_class_id;
    private  String  MainName;
    private  String  MainSubName;

    public String getActual_class_id() {
        return actual_class_id;
    }

    public void setActual_class_id(String actual_class_id) {
        this.actual_class_id = actual_class_id;
    }

    public String getMainName() {
        return MainName;
    }

    public void setMainName(String mainName) {
        MainName = mainName;
    }

    public String getMainSubName() {
        return MainSubName;
    }

    public void setMainSubName(String mainSubName) {
        MainSubName = mainSubName;
    }

    public ABBBatch(String userid, String name, String email, String  MainName,String  MainSubName, String actual_class_id) {
        this.userId = userid;
        this.batch = name;
        this.class_id = email;
        this.MainName = MainName;
        this.MainSubName = MainSubName;
        this.actual_class_id = actual_class_id;
    }

    public ABBBatch(String userid, String name, String email, String  MainName,String  MainSubName) {
        this.userId = userid;
        this.batch = name;
        this.class_id = email;
        this.MainName = MainName;
        this.MainSubName = MainSubName;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getBatch() {

        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }
}
