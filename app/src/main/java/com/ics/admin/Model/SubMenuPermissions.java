package com.ics.admin.Model;

public class SubMenuPermissions {

    String  permission_id;
    String  submenu;
    String  menu_name ;
    String  mainstatus ;
    String  menu_id ;

    public SubMenuPermissions(String menu_id, String menu_name, String submenu) {
        this.menu_id =menu_id ;
        this.menu_name =menu_name ;
        this.submenu =submenu ;

    }

    public SubMenuPermissions(String menu_name) {
        this.menu_name =menu_name ;
    }

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    String  sub_id ;


    public String getMainstatus() {
        return mainstatus;
    }

    public void setMainstatus(String mainstatus) {
        this.mainstatus = mainstatus;
    }


    public String getSubmenu() {
        return submenu;
    }

    public void setSubmenu(String submenu) {
        this.submenu = submenu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String  status ;

    public String getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(String permission_id) {
        this.permission_id = permission_id;
    }

    public String getMenu_id() {
        return submenu;
    }

    public void setMenu_id(String submenu) {
        this.submenu = submenu;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public SubMenuPermissions(String permission_id, String submenu ,String  status,String  mainstatus,String  sub_id ) {
        this.permission_id = permission_id;
        this.submenu = submenu;
        this.status = status;
        this.mainstatus = mainstatus;
        this.sub_id = sub_id;

    }
}

