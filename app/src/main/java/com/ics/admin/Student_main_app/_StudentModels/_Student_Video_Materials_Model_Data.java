package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _Student_Video_Materials_Model_Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("school_id")
    @Expose
    private String schoolId;
    @SerializedName("addedby")
    @Expose
    private String addedby;
    @SerializedName("demo_video_image")
    @Expose
    private String demoVideoImage;
    @SerializedName("demo_video_id")
    @Expose
    private String demoVideoId;
    @SerializedName("video_id")
    @Expose
    private String videoId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("expire_date")
    @Expose
    private String expireDate;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("video_time")
    @Expose
    private String videoTime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("location")
    @Expose
    private String location;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_Video_Materials_Model_Data() {
    }

    /**
     *
     * @param amount
     * @param addedby
     * @param demoVideoImage
     * @param description
     * @param discountAmount
     * @param videoId
     * @param title
     * @param schoolId
     * @param demoVideoId
     * @param expireDate
     * @param location
     * @param id
     * @param videoTime
     * @param createDate
     * @param status
     */
    public _Student_Video_Materials_Model_Data(String id, String schoolId, String addedby, String demoVideoImage, String demoVideoId, String videoId, String title, String description, String createDate, String expireDate, String amount, String discountAmount, String videoTime, String status, String location) {
        super();
        this.id = id;
        this.schoolId = schoolId;
        this.addedby = addedby;
        this.demoVideoImage = demoVideoImage;
        this.demoVideoId = demoVideoId;
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.expireDate = expireDate;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.videoTime = videoTime;
        this.status = status;
        this.location = location;
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

    public String getDemoVideoImage() {
        return demoVideoImage;
    }

    public void setDemoVideoImage(String demoVideoImage) {
        this.demoVideoImage = demoVideoImage;
    }

    public String getDemoVideoId() {
        return demoVideoId;
    }

    public void setDemoVideoId(String demoVideoId) {
        this.demoVideoId = demoVideoId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
