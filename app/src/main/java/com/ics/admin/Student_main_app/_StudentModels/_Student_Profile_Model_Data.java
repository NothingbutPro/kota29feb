package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _Student_Profile_Model_Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("parent_name")
    @Expose
    private String parentName;
    @SerializedName("parent_mobile")
    @Expose
    private String parentMobile;
    @SerializedName("education")
    @Expose
    private String education;
    @SerializedName("skills")
    @Expose
    private String skills;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("batch_id")
    @Expose
    private String batchId;
    @SerializedName("school_id")
    @Expose
    private String schoolId;
    @SerializedName("add_date")
    @Expose
    private String addDate;
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
     * No args constructor for use in serialization
     *
     */
    public _Student_Profile_Model_Data() {
    }

    /**
     *
     * @param pincode
     * @param education
     * @param address
     * @param city
     * @param mobile
     * @param batch
     * @param profileImage
     * @param batchId
     * @param createdOn
     * @param addDate
     * @param skills
     * @param password
     * @param parentName
     * @param classId
     * @param schoolId
     * @param name
     * @param id
     * @param parentMobile
     * @param state
     * @param _class
     * @param courseId
     * @param email
     * @param status
     */
    public _Student_Profile_Model_Data(String id, String name, String mobile, String email, String password, String parentName, String parentMobile, String education, String skills, String address, String state, String city, String pincode, String profileImage, String createdOn, String classId, String courseId, String batchId, String schoolId, String addDate, String status, String _class, String batch) {
        super();
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.parentName = parentName;
        this.parentMobile = parentMobile;
        this.education = education;
        this.skills = skills;
        this.address = address;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
        this.profileImage = profileImage;
        this.createdOn = createdOn;
        this.classId = classId;
        this.courseId = courseId;
        this.batchId = batchId;
        this.schoolId = schoolId;
        this.addDate = addDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
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
