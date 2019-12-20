package com.ics.admin.BasicAdmin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class EditStuffs extends AsyncTask<String, Void, String> {
    String user_id;
    //++++++++++++++++++++++String Home works
    String new_homework;
    String newdaysforwok;
    String newwork_type;
    String newhomeworkdate;
    String batchId;
    String teacherId;
 //+++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++For Edit packages++++++++++++++++++++++
// String update_enquiry, Activity activity, String classx, String enquiryId, String enquiry_by, String mobile, String enquiry_type, String followup_type, String course, String followup_date, String remark, String url, String byenquiry
    //+++++++++++++++++++ +++++++++++++++++
    //For Video Package
    String title;
    String description; String create_date;String expire_date; String amount; String discount_amount; String video_time;
    //
    String myeditstring;
    String update_names;
    private Dialog dialog;
    String main_id;
    String sub_id;
    int numbers;
    String name;
    String mobile;
    String address;
    Activity activity;
    String urls;
//    public void DeleteDialog(Activity activity) {
//
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
//        builder1.setMessage("Write your message here.");
//        builder1.setCancelable(true);
//
//        builder1.setPositiveButton(
//                "Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
////                        del_or_not = 1;
//                    }
//                });
//
//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
////                        del_or_not = 2;
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//
//    }

    public EditStuffs(String eit_ids, Activity activity, String main_id, String sub_id, String myedit, String urlss)
    {
        this.update_names = eit_ids;
        this.activity = activity;
        this.main_id = main_id;
        this.sub_id = sub_id;
        this.myeditstring = myedit;
        this.urls = urlss;
        this.numbers = 3;
    }

    public EditStuffs(String update_course, Activity activity, String main_id, String sub_id, String name, String mobile, String address, String urlss) {
        this.update_names = update_course;
        this.activity = activity;
        this.main_id = main_id;
        this.sub_id = sub_id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.urls = urlss;
        this.numbers = 3;
    }

    public EditStuffs(String update_course, Activity activity, String main_id, String sub_id, String newdaysforwok, String newwork_type, String newhomeworkdate, String new_homework, String url, String batchId, String teacherId) {
        this.update_names = update_course;
        this.activity = activity;
        this.main_id = main_id;
        this.sub_id = sub_id;
        this.newdaysforwok = newdaysforwok;
        this.newwork_type = newwork_type;
        this.newhomeworkdate = newhomeworkdate;
        this.new_homework = new_homework;
        this.urls = url;
        this.batchId = batchId;
        this.teacherId = teacherId;
    }

//+++++++++++++++++++"update_enquiry", activity, classArrayList.get(i).getClassx(),classArrayList.get(i).getEnquiryId(),enquiry_by,mobile,enquiry_type, followup_type,classArrayList.get(i).getCourse(),followup_date,remark,url
    public EditStuffs(String updatevideoPackage, Activity activity, String classId, String id, String title, String description, String create_date, String expire_date, String amount, String discount_amount, String video_time, String url,String sxv) {
        this.update_names = updatevideoPackage;
        this.activity = activity;
        this.main_id = classId;
        this.sub_id = id;
        this.title = title;
        this.create_date = create_date;
        this.expire_date = expire_date;
        this.amount = amount;
        this.description = description;
        this.discount_amount = discount_amount;
        this.video_time = video_time;
        this.urls = url;
    }

    public EditStuffs(String update_video, Activity activity, String main_id, String sub_id, String userId, String urls, String tittels) {
        this.update_names = update_video;
        this.activity = activity;
        this.main_id = main_id;
        this.sub_id = sub_id;
        this.myeditstring = tittels;
        this.urls = urls;
        this.numbers = 3;
    }


    @Override
    protected String doInBackground(String... arg0) {

        try {

            URL url = new URL(urls);

            JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
            //++++++++++++++++++++++++++++Same but for different ids
            if(update_names.equals("update_Batch"))
            {
                postDataParams.put("batch_id", sub_id);
                postDataParams.put("class_id", main_id);
                postDataParams.put("batch", myeditstring);
            }else  if(update_names.equals("update_subject"))
            {
                postDataParams.put("subject_id", sub_id);
                postDataParams.put("subject", myeditstring);
            }else  if(update_names.equals("update_Course"))
            {
                postDataParams.put("id", sub_id);
                postDataParams.put("class_id", main_id);
                postDataParams.put("course", myeditstring);
            }else
                if(update_names.equals("update_student"))
            {
                postDataParams.put("id", sub_id);
                postDataParams.put("name", name);
                postDataParams.put("mobile", mobile);
                postDataParams.put("address", address);
            }else
                if(update_names.equals("update_video"))
            {
                postDataParams.put("class_id", main_id);
                postDataParams.put("course_id", sub_id);
                postDataParams.put("title", title);
                postDataParams.put("description", address);
            }

            else
                if(update_names.equals("update_homework"))
            {
                postDataParams.put("id", sub_id);
                postDataParams.put("class_id",main_id);
                postDataParams.put("batch_id",batchId);
                postDataParams.put("teacher_id",teacherId);
                postDataParams.put("work_date",newhomeworkdate);
                postDataParams.put("homework",new_homework);
                postDataParams.put("work_type",newwork_type);
                postDataParams.put(" daysforwok",newdaysforwok);
            }
            else
                if(update_names.equals("update_enquiry"))
            {
                postDataParams.put("enquiry_id", sub_id);
                postDataParams.put("class_id", main_id);
                postDataParams.put("enquiry_by", title);
                postDataParams.put("course", amount);
                postDataParams.put("mobile", description);
                postDataParams.put("enquiry_type", create_date);
                postDataParams.put("followup_type", expire_date);
                postDataParams.put("followup_date", discount_amount);
                postDataParams.put("remark", video_time);
            }else  if(update_names.equals("updatevideoPackage"))
            {
                postDataParams.put("id", sub_id);
                postDataParams.put("title", title);
                postDataParams.put("description", description);
                postDataParams.put("video_id", main_id);
                postDataParams.put("create_date", create_date);
                postDataParams.put("expire_date", expire_date);
                postDataParams.put("amount", amount);
                postDataParams.put("discount_amount", discount_amount);
                postDataParams.put("video_time", video_time);
            }else  if(update_names.equals("updatefee"))
            {
                postDataParams.put("id", sub_id);
                postDataParams.put("class_id", sub_id);
                postDataParams.put("course_id", sub_id);
                postDataParams.put("fee_amount", sub_id);

            }

//            postDataParams.put(user_id, sub_id);
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
                    Intent intent = new Intent(activity , activity.getClass());
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