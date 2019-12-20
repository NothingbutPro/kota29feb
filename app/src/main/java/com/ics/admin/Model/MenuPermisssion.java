package com.ics.admin.Model;

public class MenuPermisssion {
    String  permission_id;
    String  menu_id;
    String  menu_name ;
    String  status ;

    public MenuPermisssion(String permission_id, String menu_id, String menu_name) {
        this.permission_id = permission_id;
        this.menu_id = menu_id;
        this.menu_name = menu_name;
//        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(String permission_id) {
        this.permission_id = permission_id;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public MenuPermisssion(String permission_id, String menu_id, String menu_name ,String status) {
        this.permission_id = permission_id;
        this.menu_id = menu_id;
        this.menu_name = menu_name;
        this.status = status;
    }
}
