package com.ics.admin.BasicAdmin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
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

public class  DELETStuffs extends AsyncTask<String, Void, String> {
    String user_id;
    String newclassname;
    private Dialog dialog;

    Activity activity;
    String urls;


    public DELETStuffs(String Del_ids ,Activity activity, String user_id, String url)
    {
        this.user_id = user_id;
        this.activity = activity;
        this.urls = url;
        this.newclassname = Del_ids;

    }


    @Override
    protected String doInBackground(String... arg0) {

        try {

            URL url = new URL(urls);

            JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
            postDataParams.put(newclassname, user_id);
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