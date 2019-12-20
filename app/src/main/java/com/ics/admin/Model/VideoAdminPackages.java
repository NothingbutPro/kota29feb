package com.ics.admin.Model;

public class VideoAdminPackages {

    private String id;

    private String schoolId;

    private String addedby;

    private String videoId;

    private String title;

    private String description;

    private String createDate;

    private String expireDate;

    private String amount;

    private String discountAmount;

    private String videoTime;


    private String status;


    public VideoAdminPackages(String id, String schoolId, String addedby, String videoId, String title, String description, String createDate, String expireDate, String amount, String discountAmount, String videoTime, String status) {
        this.id = id;
        this.schoolId = schoolId;
        this.addedby = addedby;
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.videoTime = videoTime;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getVideoTime() {
        return videoTime;
    }

    public void setVideoTime(String videoTime) {
        this.videoTime = videoTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
