package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _Student_All_Packages_data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("school_id")
    @Expose
    private String schoolId;
    @SerializedName("addedby")
    @Expose
    private String addedby;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("video_image")
    @Expose
    private String videoImage;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("Course")
    @Expose
    private String course;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_All_Packages_data() {
    }

    /**
     *
     * @param date
     * @param addedby
     * @param description
     * @param video
     * @param title
     * @param type
     * @param classId
     * @param videoUrl
     * @param schoolId
     * @param videoImage
     * @param course
     * @param id
     * @param _class
     * @param courseId
     * @param status
     */
    public _Student_All_Packages_data(String id, String schoolId, String addedby, String classId, String courseId, String date, String videoImage, String video, String videoUrl, String title, String description, String type, String status, String _class, String course) {
        super();
        this.id = id;
        this.schoolId = schoolId;
        this.addedby = addedby;
        this.classId = classId;
        this.courseId = courseId;
        this.date = date;
        this.videoImage = videoImage;
        this.video = video;
        this.videoUrl = videoUrl;
        this.title = title;
        this.description = description;
        this.type = type;
        this.status = status;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(String videoImage) {
        this.videoImage = videoImage;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
