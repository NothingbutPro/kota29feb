package com.ics.admin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.ics.admin.Adapter.MainMenuAdapter;
import com.ics.admin.Model.MenuPermisssion;
import com.ics.admin.ShareRefrance.Shared_Preference;

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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class FaculttyPermission extends AppCompatActivity {
    RecyclerView subrec,mainrec;
    List<MenuPermisssion> menuPermisssionList = new ArrayList<>();
    MainMenuAdapter mainMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultty_permission);
//
        mainrec = findViewById(R.id.mainrec);
        new GETAllPermiSSIons(2).execute();
    }

    private class GETAllPermiSSIons extends AsyncTask<String, Void, String> {
        String Name;
        String Phone_Number;
        String Email;
        String Password;
        String Address;
        private Dialog dialog;
        String Faculty_id;
        int ep;
        public GETAllPermiSSIons(int ep) {
            this.ep = ep;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getmenulist");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("user_id", new Shared_Preference().getId(FaculttyPermission.this));
                postDataParams.put("teacher_id", getIntent().getStringExtra("teacher_id"));


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

        @SuppressLint("WrongConstant")
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
//                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    if(!jsonObject.getBoolean("responce")){
                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
                    }
                    else
                    {
                        for(int i=0;i<jsonObject.getJSONArray("data").length();i++)
                        {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(i);
                            String  permission_id =jsonObject1.getString("permission_id");
                            String  menu_id =jsonObject1.getString("menu_id");
                            String  menu_name =jsonObject1.getString("menu_name");
                            String  status =jsonObject1.getString("status");
                            menuPermisssionList.add(new MenuPermisssion(permission_id,menu_id,menu_name,status));
//sdf
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FaculttyPermission.this);
                        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
                        mainrec.setLayoutManager(linearLayoutManager);
                        mainMenuAdapter = new MainMenuAdapter(FaculttyPermission.this ,menuPermisssionList );
                        mainrec.setAdapter(mainMenuAdapter);
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
