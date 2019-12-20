package com.ics.admin.Model;

public class Enqiries {

    private String enquiryId;
    private String schoolId;
    private String enquiryBy;
    private String enquiryDate;
    private String name;
    private String mobile;
    private String enquiryType;
    private String followupType;
    private String followupDate;
    private String remark;
    private String status;
    private String _class;
    private String course;



    private String classx;


    public Enqiries(String enquiryId, String schoolId, String enquiryBy, String enquiryDate, String name, String mobile, String enquiryType, String followupType, String followupDate, String remark, String status, String _class, String course, String classx) {
        this.enquiryId = enquiryId;
        this.schoolId = schoolId;
        this.enquiryBy = enquiryBy;
        this.enquiryDate = enquiryDate;
        this.name = name;
        this.mobile = mobile;
        this.enquiryType = enquiryType;
        this.followupType = followupType;
        this.followupDate = followupDate;
        this.remark = remark;
        this.status = status;
        this._class = _class;
        this.course = course;
        this.classx = classx;
    }
    public String getClassx() {
        return classx;
    }

    public void setClassx(String classx) {
        this.classx = classx;
    }
    public String getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getEnquiryBy() {
        return enquiryBy;
    }

    public void setEnquiryBy(String enquiryBy) {
        this.enquiryBy = enquiryBy;
    }

    public String getEnquiryDate() {
        return enquiryDate;
    }

    public void setEnquiryDate(String enquiryDate) {
        this.enquiryDate = enquiryDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }

    public String getFollowupType() {
        return followupType;
    }

    public void setFollowupType(String followupType) {
        this.followupType = followupType;
    }

    public String getFollowupDate() {
        return followupDate;
    }

    public void setFollowupDate(String followupDate) {
        this.followupDate = followupDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }


}
