package com.ics.admin.Model;

public class ViewFees {
    private String id;

    private String schoolId;

    private String addedby;

    private String classId;

    private String courseId;

    private String feeAmount;

    private String status;

    private String createDate;

    private String _class;

    private String course;



    /**
     *
     * @param feeAmount
     * @param classId
     * @param addedby
     * @param schoolId
     * @param course
     * @param id
     * @param _class
     * @param courseId
     * @param status
     * @param createDate
     */
    public ViewFees(String id, String schoolId, String addedby, String classId, String courseId, String feeAmount, String status, String createDate, String _class, String course) {
        super();
        this.id = id;
        this.schoolId = schoolId;
        this.addedby = addedby;
        this.classId = classId;
        this.courseId = courseId;
        this.feeAmount = feeAmount;
        this.status = status;
        this.createDate = createDate;
        this._class = _class;
        this.course = course;
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

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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
