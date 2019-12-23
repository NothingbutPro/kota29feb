package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _Student_Announcements {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("batch_id")
    @Expose
    private String batchId;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("cr_date")
    @Expose
    private String crDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("Batch")
    @Expose
    private String batch;



    /**
     *
     * @param date
     * @param notification
     * @param classId
     * @param batch
     * @param id
     * @param crDate
     * @param _class
     * @param batchId
     * @param userId
     * @param status
     */
    public _Student_Announcements(String id, String userId, String classId, String batchId, String notification, String date, String crDate, String status, String _class, String batch) {
        super();
        this.id = id;
        this.userId = userId;
        this.classId = classId;
        this.batchId = batchId;
        this.notification = notification;
        this.date = date;
        this.crDate = crDate;
        this.status = status;
        this._class = _class;
        this.batch = batch;
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

    public String getCrDate() {
        return crDate;
    }

    public void setCrDate(String crDate) {
        this.crDate = crDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
