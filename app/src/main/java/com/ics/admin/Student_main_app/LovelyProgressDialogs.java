package com.ics.admin.Student_main_app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.R;
import com.victor.loading.book.BookLoading;
import com.victor.loading.rotate.RotateLoading;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

public class LovelyProgressDialogs {
    LovelyProgressDialog lovelyProgressDialogs;
    public Dialog dialog;
    BookLoading bookloading;
//    RotateLoading rotateloading;
    Context context;
    public LovelyProgressDialogs(Context context) {
        this.context = context;
    }

    @SuppressLint("ResourceAsColor")
    public void StartDialog(String Message) {
        // Create custom dialog object
         dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout._student_dialog_box);
        // Set dialog title
        dialog.setTitle("Custom Dialog");

        // set values for custom dialog components - text, image and button
         bookloading = dialog.findViewById(R.id.bookloading);
//        rotateloading = dialog.findViewById(R.id.rotateloading);
        bookloading.setBackgroundColor(android.R.color.transparent);
        bookloading.start();

        dialog.show();


        // if decline button is clicked, close the custom dialog
//     lovelyProgressDialogs = new LovelyProgressDialog(context)
//            .setTitle(""+Message)
//            .setTopColorRes(R.color.red).setCancelable(false);
//        lovelyProgressDialogs.show();
        }
        public void DismissDialog(){
        if(dialog.isShowing())
        {
            new CountDownTimer(2000,1000)
            {

                @Override
                public void onTick(long millisUntilFinished) {
                    Log.e("time ticking" , ""+String.valueOf(millisUntilFinished/100));
                }

                @Override
                public void onFinish() {
                    Toast.makeText(context, "Loading finish", Toast.LENGTH_SHORT).show();
                    bookloading.stop();
                    dialog.dismiss();
                }
            }.start();

        }
//            lovelyProgressDialogs.dismiss();
        }
        public  void  Dismissspecial(){
            if(dialog.isShowing())
            {
                bookloading.stop();
                dialog.dismiss();
            }
        }
}
