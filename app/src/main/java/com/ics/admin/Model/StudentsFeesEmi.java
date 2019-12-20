package com.ics.admin.Model;

public class StudentsFeesEmi {

    private String payId;

    private String schoolId;

    private String addedby;

    private String classId;

    private String courseId;

    private String studentId;

    private String createDate;

    private String totalAmount;

    private String payby;

    private String emiMonth;

    private String paymode;

    private String totalPayamount;

    /**
     * No args constructor for use in serialization
     *
     */

    /**
     *
     * @param studentId
     * @param totalAmount
     * @param classId
     * @param emiMonth
     * @param addedby
     * @param schoolId
     * @param payby
     * @param paymode
     * @param payId
     * @param courseId
     * @param totalPayamount
     * @param createDate
     */
    public StudentsFeesEmi(String payId, String schoolId, String addedby, String classId, String courseId, String studentId, String createDate, String totalAmount, String payby, String emiMonth, String paymode, String totalPayamount) {
        super();
        this.payId = payId;
        this.schoolId = schoolId;
        this.addedby = addedby;
        this.classId = classId;
        this.courseId = courseId;
        this.studentId = studentId;
        this.createDate = createDate;
        this.totalAmount = totalAmount;
        this.payby = payby;
        this.emiMonth = emiMonth;
        this.paymode = paymode;
        this.totalPayamount = totalPayamount;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPayby() {
        return payby;
    }

    public void setPayby(String payby) {
        this.payby = payby;
    }

    public String getEmiMonth() {
        return emiMonth;
    }

    public void setEmiMonth(String emiMonth) {
        this.emiMonth = emiMonth;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getTotalPayamount() {
        return totalPayamount;
    }

    public void setTotalPayamount(String totalPayamount) {
        this.totalPayamount = totalPayamount;
    }

}
