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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.BasicAdmin.Masters.Batch.AddStudentActivity;
import com.ics.admin.Interfaces.ProgressDialogs;
import com.ics.admin.Model.Student_Fee_Details_Data;
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

public class AllStudentFeesAdapter extends RecyclerView.Adapter<AllStudentFeesAdapter.MyViewHolder> {

    ArrayList<Student_Fee_Details_Data> studentsArrayList = new ArrayList<>();
    Activity activity;
    private ArrayList<StudentsFeesEmi> studentsfeeList = new ArrayList<>();

    public AllStudentFeesAdapter(Activity activity, ArrayList<Student_Fee_Details_Data> studentsArrayList) {
        this.activity = activity;
        this.studentsArrayList = studentsArrayList;
        //  this.Images = Images;
    }

    @NonNull
    @Override
    public AllStudentFeesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studentfeeadapter_xml, viewGroup, false);
        return new AllStudentFeesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllStudentFeesAdapter.MyViewHolder myViewHolder, final int i) {
//        Toast.makeText(activity, "sfdsdfsdfsdf", Toast.LENGTH_SHORT).show();
        myViewHolder.name.setText("Name: "+studentsArrayList.get(i).getName());
        myViewHolder.new_mobile.setText("Mob:"+studentsArrayList.get(i).getMobile());
        myViewHolder.main_add.setText("Add:"+studentsArrayList.get(i).getAddress());
        myViewHolder.iv_date.setText("date:"+studentsArrayList.get(i).getAddDate());
//        myViewHolder.iv_time.setText("Mob:"+studentsArrayList.get(i).getti());
//        myViewHolder.v_batch.setText("Batch :"+studentsArrayList.get(i).get);

//
//        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(studentsArrayList.get(i).getFeedone().equals("feedone_yes")) {
//
//
//                    Toast.makeText(activity, "You have fee due", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(activity, "You are already Assigned", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        myViewHolder.viewdetails_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.studentfees);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                Button ok_btyn = dialog.findViewById(R.id.ok_btyn);

                new AllStudentFeesAdapter.GETSTUDETNEMIDETAILS(studentsArrayList.get(i).getId(),dialog).execute();
                ok_btyn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
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
        TextView name,add_class,mainclass,mainsubname,v_batch,main_add,iv_date,iv_time,new_mobile;
        EditText new_name,new_address;
        ImageView add_student;

        LinearLayout assignstd,hide_li;
        Button delbatch,edit_btn_save,edit_btn,viewdetails_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            v_batch = (TextView) itemView.findViewById(R.id.v_batch);
            iv_time = (TextView) itemView.findViewById(R.id.iv_time);
            add_class=(TextView)itemView.findViewById(R.id.add_class);
            add_student = (ImageView) itemView.findViewById(R.id.add_student);
            mainclass =  itemView.findViewById(R.id.mainclass);
            mainsubname = itemView.findViewById(R.id.mainsubname);
            viewdetails_btn = itemView.findViewById(R.id.viewdetails_btn);
            assignstd = itemView.findViewById(R.id.assignstd);
            iv_date = itemView.findViewById(R.id.iv_date);
            delbatch = itemView.findViewById(R.id.delbatch);
            new_name = itemView.findViewById(R.id.new_name);
            new_mobile = itemView.findViewById(R.id.iv_mob);
            new_address = itemView.findViewById(R.id.new_address);
            edit_btn_save = itemView.findViewById(R.id.edit_btn_save);
            hide_li = itemView.findViewById(R.id.hide_li);
            edit_btn = itemView.findViewById(R.id.edit_btn);
            main_add = itemView.findViewById(R.id.main_add);

//            name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent1 = new Intent(activity, StudentActivity.class);
//                    activity.startActivity(intent1);
//                }
//            });
            add_student.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, AddStudentActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });


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
                            TextView payedornot = dialog.findViewById(R.id.payedornot);
                            TextView create_dates = dialog.findViewById(R.id.create_date);
                            TextView emi_months = dialog.findViewById(R.id.emi_months);
                            TextView paybys = dialog.findViewById(R.id.payby);
                            TextView paymodes = dialog.findViewById(R.id.paymode);
                            TextView total_payamounts = dialog.findViewById(R.id.total_payamount);
                            total_amounts.setText("Amount:"+total_amount.toString());
                            create_dates.setText(create_date.toString());
                            paybys.setText(payby.toString());
                            emi_months.setText("Month:"+emi_month.toString());
                            paymodes.setText("Mode "+paymode.toString());
                            total_payamounts.setText("Payed"+total_payamount.toString());
                            float pay_or_not ;
                                    if(Float.valueOf(total_amount) >Float.valueOf(total_payamount))
                                    {
                                        pay_or_not = Float.valueOf(total_amount) -Float.valueOf(total_payamount);
                                        payedornot.setText("Remaining Fee "+String.valueOf( pay_or_not));
                                    }else {

                                        pay_or_not = Float.valueOf(total_payamount) -Float.valueOf(total_amount);
                                        if(pay_or_not <=1) {
                                            payedornot.setText("Remaining Fee 0 Rs");
                                        }else {
                                            payedornot.setText("Remaining Fee " + String.valueOf(pay_or_not) +"Rs");
                                        }
                                    }
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
