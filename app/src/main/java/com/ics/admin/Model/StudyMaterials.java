package com.ics.admin.Model;

public class StudyMaterials {
//    @SerializedName("id")
//    @Expose
    private String id;
//    @SerializedName("Class")
//    @Expose
    private String _class;
//    @SerializedName("title")
//    @Expose
    private String title;
//    @SerializedName("pdf_file")
//    @Expose
    private String pdfFile;



    private String class_id;

    public StudyMaterials(String id, String _class, String title, String pdfFile, String class_id) {
        this.id = id;
        this._class = _class;
        this.title = title;
        this.pdfFile = pdfFile;
        this.class_id = class_id;
    }
    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
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
}
