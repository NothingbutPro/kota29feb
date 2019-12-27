package com.ics.admin.Student_main_app;

import android.content.Context;

import com.ics.admin.R;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

public class LovelyProgressDialogs {
    LovelyProgressDialog lovelyProgressDialogs;
    Context context;
    public LovelyProgressDialogs(Context context) {
        this.context = context;
    }

    public void StartDialog(String Message) {
     lovelyProgressDialogs = new LovelyProgressDialog(context)
            .setTitle(""+Message)
            .setTopColorRes(R.color.red).setCancelable(false);
        lovelyProgressDialogs.show();
        }
        public void DismissDialog(){
            lovelyProgressDialogs.dismiss();
        }
}
