package com.ics.admin.Interfaces;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogs  {
    ProgressDialog CommonDialog;
    Context context ;
    Activity activity;
    public  void  ProgressMe(Context context , Activity activity)
   {
       this.context = context;
       this.activity = activity;
       KeepMeAlive();
   }
   public  void  KeepMeAlive(){
        CommonDialog = new ProgressDialog(context);
        CommonDialog.show();
        CommonDialog.setCancelable(false);
        CommonDialog.setMessage("Please wait");
   }
   public Boolean EndMe(){
        CommonDialog.dismiss();
        return true;
   }
}
