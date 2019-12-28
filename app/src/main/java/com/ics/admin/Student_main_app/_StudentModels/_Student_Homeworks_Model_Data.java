package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _Student_Homeworks_Model_Data {
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
    @SerializedName("work_type")
    @Expose
    private String workType;
    @SerializedName("work_date")
    @Expose
    private String workDate;
    @SerializedName("homework")
    @Expose
    private String homework;
    @SerializedName("daysforwok")
    @Expose
    private String daysforwok;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("Batch")
    @Expose
    private String batch;
    @SerializedName("teacher")
    @Expose
    private String teacher;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_Homeworks_Model_Data() {
    }

    /**
     *
     * @param homework
     * @param batch
     * @param batchId
     * @param classId
     * @param teacherId
     * @param workDate
     * @param teacher
     * @param schoolId
     * @param workType
     * @param daysforwok
     * @param id
     * @param _class
     * @param status
     */
    public _Student_Homeworks_Model_Data(String id, String schoolId, String teacherId, String classId, String batchId, String workType, String workDate, String homework, String daysforwok, String status, String _class, String batch, String teacher) {
        super();
        this.id = id;
        this.schoolId = schoolId;
        this.teacherId = teacherId;
        this.classId = classId;
        this.batchId = batchId;
        this.workType = workType;
        this.workDate = workDate;
        this.homework = homework;
        this.daysforwok = daysforwok;
        this.status = status;
        this._class = _class;
        this.batch = batch;
        this.teacher = teacher;
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

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getWorkDate() {
        return workDate;
    }

    public void setWorkDate(String workDate) {
        this.workDate = workDate;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }

    public String getDaysforwok() {
        return daysforwok;
    }

    public void setDaysforwok(String daysforwok) {
        this.daysforwok = daysforwok;
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
