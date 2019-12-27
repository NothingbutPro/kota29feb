package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _My_Student_Attendence_Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("school_id")
    @Expose
    private String schoolId;
    @SerializedName("teacher_id")
    @Expose
    private String teacherId;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("batch_id")
    @Expose
    private String batchId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("attendance")
    @Expose
    private String attendance;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("name")
    @Expose
    private String name;
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
    public _My_Student_Attendence_Data() {
    }

    /**
     *
     * @param date
     * @param batch
     * @param remark
     * @param batchId
     * @param studentId
     * @param classId
     * @param teacherId
     * @param schoolId
     * @param name
     * @param id
     * @param _class
     * @param attendance
     * @param status
     */
    public _My_Student_Attendence_Data(String id, String schoolId, String teacherId, String classId, String batchId, String studentId, String date, String attendance, String remark, String status, String name, String _class, String batch) {
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
        this.name = name;
        this._class = _class;
        this.batch = batch;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
