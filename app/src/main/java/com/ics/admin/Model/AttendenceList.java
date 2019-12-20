package com.ics.admin.Model;

public class AttendenceList {

    private String id;

    private String schoolId;

    private String teacherId;

    private String classId;

    private String batchId;

    private String studentId;

    private String date;

    private String attendance;

    private String remark;

    private String status;

    private String _class;

    private String batch;

    private String student_name;
    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }



    /**
     *
     * @param studentId
     * @param date
     * @param classId
     * @param teacherId
     * @param schoolId
     * @param batch
     * @param remark
     * @param id
     * @param _class
     * @param batchId
     * @param attendance
     * @param status
     */
    public AttendenceList(String id, String schoolId, String teacherId, String classId, String batchId, String studentId, String date, String attendance, String remark, String status, String _class, String batch,String student_name) {
        super();
        this.id = id;
        this.schoolId = schoolId;
        this.teacherId = teacherId;
        this.classId = classId;
        this.batchId = batchId;
        this.studentId = studentId;
        this.date = date;
        this.attendance = attendance;
        this.remark = remark;
        this.status = status;
        this._class = _class;
        this.batch = batch;
        this.student_name = student_name;
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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

}
