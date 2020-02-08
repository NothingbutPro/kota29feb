package com.ics.admin.CommonJavaClass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.ics.admin.R;

public class AdminProgressdialog  {
    ProgressBar progressBar;
    Dialog dialog;
    public AdminProgressdialog( Context context ) {
         dialog = new Dialog(context);
        dialog.setContentView(R.layout.adminprogressbarxml);
        dialog.setCancelable(true);
         progressBar = (ProgressBar)dialog.findViewById(R.id.spin_kit);
        Sprite custom = new CubeGrid();
        progressBar.setIndeterminateDrawable(custom);
        dialog.show();
    }
  public Boolean EndProgress(){
      dialog.dismiss();
      if(dialog.isShowing()) {
          progressBar.setVisibility(View.GONE);
          dialog.dismiss();
          return false;
      }else {
          return true;
      }
//        if(progressBar.getVisibility())

  }
}
