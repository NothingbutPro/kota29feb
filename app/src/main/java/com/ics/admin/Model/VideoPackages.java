package com.ics.admin.Model;

public class VideoPackages {

    private String id;

    private String schoolId;

    private String addedby;

    private String classId;

    private String courseId;

    private String date;

    private String videoImage;

    private String video;

    private String videoUrl;

    private String title;

    private String description;

    private String status;

    private String _class;

    private String course;

    private String video_time;

    private String package_select;


    public String getPackage_select() {
        return package_select;
    }

    public void setPackage_select(String package_select) {
        this.package_select = package_select;
    }


    public VideoPackages(String id, String schoolId, String addedby, String classId, String courseId, String date, String videoImage, String video, String videoUrl, String title, String description, String status, String _class, String course, String package_select,String video_time) {
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
        this.status = status;
        this._class = _class;
        this.course = course;
        this.package_select = package_select;
        this.video_time = video_time;
    }

    public String getVideo_time() {
        return video_time;
    }

    public void setVideo_time(String video_time) {
        this.video_time = video_time;
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
