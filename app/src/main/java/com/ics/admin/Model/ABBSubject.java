package com.ics.admin.Model;

public class ABBSubject
{
    private String userId;

    private String subject;

    public ABBSubject(String userId, String subject) {
        this.userId = userId;
        this.subject = subject;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {

        this.subject = subject;
    }
}
