package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class _Student_Chat_Public_Community_Data {

    @SerializedName("community_id")
    @Expose
    private String communityId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("message_id")
    @Expose
    private String messageId;
    @SerializedName("school_id")
    @Expose
    private String schoolId;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_Chat_Public_Community_Data() {
    }

    /**
     *
     * @param dateTime
     * @param schoolId
     * @param messageId
     * @param communityId
     * @param message
     * @param userId
     */
    public _Student_Chat_Public_Community_Data(String communityId, String userId, String message, String dateTime, String messageId, String schoolId) {
        super();
        this.communityId = communityId;
        this.userId = userId;
        this.message = message;
        this.dateTime = dateTime;
        this.messageId = messageId;
        this.schoolId = schoolId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
