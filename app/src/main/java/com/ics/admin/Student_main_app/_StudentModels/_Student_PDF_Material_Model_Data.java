package com.ics.admin.Student_main_app._StudentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _Student_PDF_Material_Model_Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pdf_file")
    @Expose
    private String pdfFile;
    @SerializedName("addedby")
    @Expose
    private String addedby;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Class")
    @Expose
    private String _class;

    /**
     * No args constructor for use in serialization
     *
     */
    public _Student_PDF_Material_Model_Data() {
    }

    /**
     *
     * @param date
     * @param classId
     * @param pdfFile
     * @param addedby
     * @param id
     * @param _class
     * @param title
     * @param status
     */
    public _Student_PDF_Material_Model_Data(String id, String classId, String title, String pdfFile, String addedby, String date, String status, String _class) {
        super();
        this.id = id;
        this.classId = classId;
        this.title = title;
        this.pdfFile = pdfFile;
        this.addedby = addedby;
        this.date = date;
        this.status = status;
        this._class = _class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(String pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

}
