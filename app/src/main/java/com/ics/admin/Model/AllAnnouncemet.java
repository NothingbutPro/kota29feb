package com.ics.admin.Model;

public class AllAnnouncemet {

    private String id;

    private String userId;

    private String classId;

    private String batchId;

    private String notification;

    private String date;
    private String cr_date;

    private String status;
    private String Class_;
    private String Batch;

    public String getClass_() {
        return Class_;
    }

    public void setClass_(String class_) {
        Class_ = class_;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        this.Batch = batch;
    }

    public String getCr_date() {
        return cr_date;
    }

    public void setCr_date(String cr_date) {
        this.cr_date = cr_date;
    }

    /**
     *  @param id
     * @param userId
     * @param classId
     * @param batchId
     * @param notification
     * @param date
     * @param status
     * @param batch
     * @param class_

     */
    public AllAnnouncemet(String id, String userId, String classId, String batchId, String notification, String date, String status, String batch, String class_,String cr_date) {
        super();
        this.id = id;
        this.userId = userId;
        this.classId = classId;
        this.batchId = batchId;
        this.notification = notification;
        this.date = date;
        this.status = status;
        this.Batch = batch;
        this.Class_ = class_;
        this.cr_date = cr_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
