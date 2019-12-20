package com.ics.admin.Model;

public class ABBCoursemodel {
        private String userId;
        private String class_id;
        String stud_id;
        String name;
        private String course;
        private String mainname;
        private String submainname;

    private String actual_class_id;


    public String getActual_class_id() {
        return actual_class_id;
    }

    public void setActual_class_id(String actual_class_id) {
        this.actual_class_id = actual_class_id;
    }

    public ABBCoursemodel(String userId, String class_id, String course, String mainname, String submainname,String actual_class_id) {
        this.userId = userId;
        this.class_id = class_id;
        this.course = course;
        this.mainname = mainname;
        this.submainname = submainname;
        this.actual_class_id = actual_class_id;
    }

    public ABBCoursemodel(String stud_id, String name) {
        this.stud_id =stud_id;
        this.name =name;
    }
//    public  ABBCoursemodel(String Fees , String id,String name,String mobile)

    public String getStud_id() {
        return stud_id;
    }

    public void setStud_id(String stud_id) {
        this.stud_id = stud_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainname() {
        return mainname;
    }

    public void setMainname(String mainname) {
        this.mainname = mainname;
    }

    public String getSubmainname() {
        return submainname;
    }

    public void setSubmainname(String submainname) {
        this.submainname = submainname;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
