package com.ics.admin.Student_main_app.Student_Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.Adapter.AdminAdapters.Student_Fee_Adapter;
import com.ics.admin.Fragment.VideoFragment;
import com.ics.admin.Interfaces.ProgressDialogs;
import com.ics.admin.Model.StudentsFeesEmi;
import com.ics.admin.OTPActivity;
import com.ics.admin.R;
import com.ics.admin.Student_main_app._StudentModels._Student_Pay_Fee_Model;
import com.marcoscg.dialogsheet.DialogSheet;

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
import java.util.Queue;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class Student_FeeAdapter extends RecyclerView.Adapter<Student_FeeAdapter.MyViewHolder>{

    ArrayList<_Student_Pay_Fee_Model> student_pay_fee_models ;
    Activity activity;
    private ArrayList<StudentsFeesEmi> studentsfeeList;

    public Student_FeeAdapter(Activity activity, ArrayList<_Student_Pay_Fee_Model> student_pay_fee_models) {
        this.activity = activity;
        this.student_pay_fee_models = student_pay_fee_models;

    }

    @NonNull
    @Override
    public Student_FeeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout._student_get_fee, viewGroup, false);

        return new Student_FeeAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Student_FeeAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder._st_total_amount.setText("" +  student_pay_fee_models.get(i).getTotalAmount());
        myViewHolder._st_payby.setText("" +  student_pay_fee_models.get(i).getPayby());
        myViewHolder._st_create_date.setText("" +  student_pay_fee_models.get(i).getCreateDate());
        myViewHolder._st_total_payamount.setText("" +  student_pay_fee_models.get(i).getTotalPayamount());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.studentfees);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                Button ok_btyn = dialog.findViewById(R.id.ok_btyn);
                new GETSTUDETNEMIForStudent(student_pay_fee_models.get(i).getPayId(),dialog).execute();
                ok_btyn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return student_pay_fee_models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView image_study;
        TextView _st_total_amount,_st_payby,_st_create_date,_st_total_payamount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            _st_total_amount = (TextView) itemView.findViewById(R.id._st_total_amount);
            _st_payby = (TextView) itemView.findViewById(R.id._st_payby);
            _st_create_date = (TextView) itemView.findViewById(R.id._st_create_date);
            _st_total_payamount = (TextView) itemView.findViewById(R.id._st_total_payamount);
//            image_study = (CircleImageView) itemView.findViewById(R.id.image_study);


        }
    }

    private class GETSTUDETNEMIForStudent extends AsyncTask<String, Void, String> {
        String userid;
        // String Faculty_id;
        public ProgressDialogs progressDialogss;
        Dialog dialog;
        String name; String email; String password; String mobile; String address;

        public GETSTUDETNEMIForStudent(String id, Dialog dialog) {
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
                            emi_months.setText("Month:"+emi_month.toString());
                            paymodes.setText("Mode "+paymode.toString());
                            total_payamounts.setText("Payed"+total_payamount.toString());
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
