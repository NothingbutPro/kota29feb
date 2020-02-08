package com.ics.admin.Adapter.AdminAdapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.ics.admin.BasicAdmin.EditStuffs;
import com.ics.admin.BasicAdmin.Masters.Batch.AddStudentActivity;
import com.ics.admin.BasicAdmin.StudentDetails.AssignStudentActivity;
import com.ics.admin.CommonJavaClass.DeleteDialog;
import com.ics.admin.Interfaces.ProgressDialogs;
import com.ics.admin.Model.Students;
import com.ics.admin.Model.StudentsFeesEmi;
import com.ics.admin.R;

import org.json.JSONArray;
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

import javax.net.ssl.HttpsURLConnection;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    ArrayList<Students> studentsArrayList = new ArrayList<>();
    Activity activity;
    private ArrayList<StudentsFeesEmi> studentsfeeList = new ArrayList<>();
    private View view;

    public StudentAdapter(Activity activity, ArrayList<Students> studentsArrayList) {
        this.activity = activity;
        this.studentsArrayList = studentsArrayList;
        //  this.Images = Images;
    }

    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studentadapter_xml, viewGroup, false);
        return new StudentAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.name.setText(studentsArrayList.get(i).getName());

//        myViewHolder.
        if(studentsArrayList.get(i).getAssigned().equals("assigned"))
        {
//           myViewHolder.assignstd.setVisibility(View.GONE);
            myViewHolder.mainclass.setText(studentsArrayList.get(i).getClassx());
            myViewHolder.v_batch.setText(studentsArrayList.get(i).getBatch());
        }
        else
            {
            myViewHolder.mainclass.setVisibility(View.GONE);

        }
        myViewHolder.assignstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!studentsArrayList.get(i).getAssigned().equals("assigned")) {
                    Intent intent = new Intent(v.getContext(), AssignStudentActivity.class);
                    intent.putExtra("student_name", studentsArrayList.get(i).getName());
                    intent.putExtra("student_id", studentsArrayList.get(i).getId());
                    v.getContext().startActivity(intent);
                    ((Activity )v.getContext()).finish();
                }else {
                    Toast.makeText(activity, "You are already Assigned", Toast.LENGTH_SHORT).show();
                }
            }
        });
        myViewHolder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vx = view;
                try {
                    view = v.getRootView().findViewById(R.id.hide_li);
                    ((ViewGroup) myViewHolder.hide_li.getParent()).removeView(myViewHolder.hide_li);

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
//                if(myViewHolder.hide_li.getVisibility() == View.VISIBLE)
//                {
//                    myViewHolder.hide_li.setVisibility(View.GONE);
//                }else {
//                    myViewHolder.hide_li.setVisibility(View.VISIBLE);
//                }
            }
        });
        myViewHolder.edit_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = myViewHolder.name.getText().toString();
                String mobile = myViewHolder.new_mobile.getText().toString();
                String address = myViewHolder.new_address.getText().toString();
                String url = "http://ihisaab.in/school_lms/api/update_student";
                new EditStuffs("update_student", activity, "",studentsArrayList.get(i).getId(),name,mobile,address, url).execute();
            }
        });
//        myViewHolder.add_class.setText("Address: "+studentsArrayList.get(i).get());
        myViewHolder.st_mobile.setText(String.valueOf(studentsArrayList.get(i).getMobile()));
        myViewHolder.delbatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteDialog().DeleteDialog("id",activity,studentsArrayList.get(i).getId(),"http://ihisaab.in/school_lms/api/delete_student");
//                new CommunityFragment.DELETStuff("id",activity,studentsArrayList.get(i).getId(),"http://ihisaab.in/school_lms/api/delete_student").execute();
            }
        });
        if(studentsArrayList.get(i).getFeedone().equals("feedone_yes")) {

//            Toast.makeText(activity, "You have fee due", Toast.LENGTH_SHORT).show();
            myViewHolder.delbatch.setVisibility(View.GONE);
            myViewHolder.edit_btn.setVisibility(View.GONE);
            myViewHolder.assignstd.setVisibility(View.GONE);
        }else {
//            Toast.makeText(activity, "You are already Assigned", Toast.LENGTH_SHORT).show();
        }
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentsArrayList.get(i).getFeedone().equals("feedone_yes")) {

                    final Dialog dialog = new Dialog(activity);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.studentfees);
                    Button ok_btyn = dialog.findViewById(R.id.ok_btyn);
                    new GETSTUDETNEMIDETAILS(studentsArrayList.get(i).getId(),dialog).execute();
                    ok_btyn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    Toast.makeText(activity, "You have fee due", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(activity, "You are already Assigned", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        myViewHolder.mainsubname.setText(""+studentsArrayList.get(i).get);

    }

    @Override
    public int getItemCount() {
        return studentsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // public CircleImageView circleimageview;
        TextView name,add_class,mainclass,mainsubname,v_batch,st_mobile;
        EditText new_name,new_mobile,new_address;
        ImageView add_student;

        LinearLayout assignstd,hide_li;
        ImageView delbatch,edit_btn;
        Button edit_btn_save;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            v_batch = (TextView) itemView.findViewById(R.id.v_batch);
            add_class=(TextView)itemView.findViewById(R.id.add_class);
            st_mobile=(TextView)itemView.findViewById(R.id.st_mobile);
            add_student = (ImageView) itemView.findViewById(R.id.add_student);
            mainclass =  itemView.findViewById(R.id.mainclass);
            mainsubname = itemView.findViewById(R.id.mainsubname);
            assignstd = itemView.findViewById(R.id.assignstd);
            delbatch = itemView.findViewById(R.id.delbatch);
            new_name = itemView.findViewById(R.id.new_name);
            new_mobile = itemView.findViewById(R.id.new_mobile);
            new_address = itemView.findViewById(R.id.new_address);
            edit_btn_save = itemView.findViewById(R.id.edit_btn_save);
            hide_li = itemView.findViewById(R.id.hide_li);
            edit_btn = itemView.findViewById(R.id.edit_btn);

//            name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent1 = new Intent(activity, StudentActivity.class);
//                    activity.startActivity(intent1);
//                }
//            });
//            add_student.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(activity, AddStudentActivity.class);
//                    activity.startActivity(intent);
//                    activity.finish();
//                }
//            });


        }
    }

    private class GETSTUDETNEMIDETAILS extends AsyncTask<String, Void, String> {
        String userid;
        // String Faculty_id;
        public ProgressDialogs progressDialogss;
        Dialog dialog;
        String name; String email; String password; String mobile; String address;

        public GETSTUDETNEMIDETAILS(String id, Dialog dialog) {
            this.userid = id;
            this.dialog = dialog;
        }

        @Override
        protected void onPreExecute() {
            progressDialogss=  new ProgressDialogs();
            progressDialogss.ProgressMe((Context) activity ,activity );
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/api/getstudentfee");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("student_id", userid);
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
                    progressDialogss.EndMe();
                    jsonObject1 = new JSONObject(result);
                    if(jsonObject1.getBoolean("responce")){
                        JSONArray jsonArray = jsonObject1.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                            String pay_id = jsonObject11.getString("pay_id");
                            String school_id = jsonObject11.getString("school_id");
                            String addedby = jsonObject11.getString("addedby");
                            String class_id = jsonObject11.getString("class_id");
                            String course_id = jsonObject11.getString("course_id");
                            String student_id = jsonObject11.getString("student_id");
                            String create_date = jsonObject11.getString("create_date");
                            String total_amount = jsonObject11.getString("total_amount");
                            String payby = jsonObject11.getString("payby");
                            String emi_month = jsonObject11.getString("emi_month");
                            String paymode = jsonObject11.getString("paymode");
                            String total_payamount = jsonObject11.getString("total_payamount");
                            TextView total_amounts = dialog.findViewById(R.id.total_amount);
                            TextView create_dates = dialog.findViewById(R.id.create_date);
                            TextView emi_months = dialog.findViewById(R.id.emi_months);
                            TextView paybys = dialog.findViewById(R.id.payby);
                            TextView paymodes = dialog.findViewById(R.id.paymode);
                            TextView total_payamounts = dialog.findViewById(R.id.total_payamount);
                            total_amounts.setText("Amount:"+total_amount.toString());
                            create_dates.setText(create_date.toString());
                            paybys.setText(payby.toString());
                            emi_months.setText("Emi Month"+emi_month.toString());
                            paymodes.setText("Pay Mode"+paymode.toString());
                            total_payamounts.setText("Total Payed"+total_payamount.toString());
                            studentsfeeList.add(new StudentsFeesEmi(pay_id,school_id,addedby,class_id,course_id,student_id,create_date,total_amount,payby,emi_month,paymode,total_payamount));
                        }
                    }else {
                        Toast.makeText(activity, "No students found ,assign them first", Toast.LENGTH_SHORT).show();
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