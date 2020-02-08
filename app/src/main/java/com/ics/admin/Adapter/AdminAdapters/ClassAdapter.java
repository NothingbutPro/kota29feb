package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.gurutouchlabs.kenneth.elegantdialog.ElegantDialog;
import com.ics.admin.BasicAdmin.Masters.Forclass.ClassViewActivity;
import com.ics.admin.Model.ClassNAmes;
import com.ics.admin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.Inflater;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder>{
    ArrayList<ClassNAmes>classArrayList = new ArrayList<>();
    Activity activity;
    View view;
    public ClassAdapter(ClassViewActivity activity, ArrayList<ClassNAmes> classArrayList) {
        this.activity=activity;
        this.classArrayList = classArrayList;

    }

    @NonNull
    @Override
    public ClassAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.class_adapter, viewGroup, false);

        return new ClassAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.add_class.setText(""+classArrayList.get(i).getClass_name());

        myViewHolder.deleli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                builder1.setMessage("Are you sure want to delete");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                new DeleteCLASS(classArrayList.get(i).getUserId() ).execute();
//                        del_or_not = 1;
//                                new DeleteDialog.DELETStuff( Del_ids , activity,  user_id,  url).execute();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
//                        del_or_not = 2;
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });
        myViewHolder.btnsaveedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EDITTHECLASS(classArrayList.get(i).getUserId() ,myViewHolder.edtclass.getText().toString()).execute();
            }
        });
        myViewHolder.edtli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogs = new Dialog(v.getContext());
                // Create Alert using Builder
//                View view = activity.getLayoutInflater().inflate(R.id.claseditli , null);
                View vx = view;
                try {
                    view = v.getRootView().findViewById(R.id.claseditli);
                    ((ViewGroup) myViewHolder.claseditli.getParent()).removeView(myViewHolder.claseditli);

                    view.setVisibility(View.VISIBLE);
                }catch (Exception e)
                {
                    view =vx;
                    e.printStackTrace();
                }
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(v.getContext())
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT).setCornerRadius(25)
                        .setFooterView(view)
                       .setHeaderView(R.layout.edit_layout_header);
//                dialog.dismiss();
                CFAlertDialog cfAlertDialog = builder.create();
                cfAlertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                cfAlertDialog.show();
// Show the alert
//                builder.show();
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////                ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT);
////                View view = new LinearLayout(activity);
////                View view = activity.getLayoutInflater().inflate(R.id.claseditli , null);
//                View view = v.getRootView().findViewById(R.id.claseditli);
//                ((ViewGroup)myViewHolder.claseditli.getParent()).removeView(myViewHolder.claseditli);
//                view.setVisibility(View.VISIBLE);
//                dialog.setContentView(view);
////                dialog.getWindow().setLayout(width, height);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                dialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return classArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView add_class;
        Button btnsaveedt;
        EditText edtclass;
        LinearLayout claseditli;
        ImageView edtli,deleli;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            add_class = (TextView) itemView.findViewById(R.id.add_class);
            edtclass =  itemView.findViewById(R.id.edtclass);
            edtli =  itemView.findViewById(R.id.edtli);
            claseditli =  itemView.findViewById(R.id.claseditli);
            btnsaveedt =  itemView.findViewById(R.id.btnsaveedt);
            deleli =  itemView.findViewById(R.id.deleli);
        }

    }

    private class EDITTHECLASS extends AsyncTask<String, Void, String> {
        String user_id;
         String newclassname;
        private Dialog dialog;


        public EDITTHECLASS(String user_id, String newclassname)
        {
            this.user_id = user_id;
             this.newclassname=newclassname;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/update_class");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("class_id", user_id);
                postDataParams.put("class_name", newclassname);
                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
//                dialog.dismiss();

                JSONObject jsonObject1 = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject1 = new JSONObject(result);
                    if(jsonObject1.getBoolean("responce")){
                       Intent intent = new Intent(activity ,activity.getClass() );
                       activity.startActivity(intent);
                       activity.finish();
//                        Toast.makeText(ClassViewActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {


                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }

    private class DeleteCLASS extends AsyncTask<String, Void, String> {
        String user_id;
        String newclassname;
        private Dialog dialog;


        public DeleteCLASS(String user_id)
        {
            this.user_id = user_id;

        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/deleteclasslist");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("class_id", user_id);
                Log.e("postDataParams", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /*milliseconds*/);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
//                dialog.dismiss();

                JSONObject jsonObject1 = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject1 = new JSONObject(result);
                    if(jsonObject1.getBoolean("responce")){
                        Intent intent = new Intent(activity , ClassViewActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
//                        Toast.makeText(ClassViewActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {


                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));

            }
            return result.toString();
        }
    }
}
