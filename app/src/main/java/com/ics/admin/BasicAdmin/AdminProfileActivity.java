package com.ics.admin.BasicAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ics.admin.Adapter.AdminAdapters.BatchAdapter;
import com.ics.admin.BasicAdmin.Masters.Batch.AddBatchActivity;
import com.ics.admin.Model.ABBBatch;
import com.ics.admin.R;
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
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class AdminProfileActivity extends AppCompatActivity {
    TextView admin_name,types,status_of,profile_mob,pro_email,pro_address,pro_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        admin_name = findViewById(R.id.admin_name);
        types = findViewById(R.id.types);
        status_of = findViewById(R.id.status_of);
        profile_mob = findViewById(R.id.profile_mob);
        pro_email = findViewById(R.id.pro_email);
        pro_address = findViewById(R.id.pro_address);
        pro_password = findViewById(R.id.pro_password);
        new GETMYINFORMATIONS(new Shared_Preference().getId(this)).execute();
    }

    private class GETMYINFORMATIONS extends AsyncTask<String, Void, String> {

        String userid;
        // String Faculty_id;
        private Dialog dialog;


        public GETMYINFORMATIONS(String i) {
            this.userid = i;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getprofile");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("user_id", userid);


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
                    if(!jsonObject1.getBoolean("responce"))
                    {
                        Toast.makeText(AdminProfileActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                        admin_name.setText(jsonObject1.getString("name"));
                        if(jsonObject1.getString("type").equals("1")) {
                            types.setText(jsonObject1.getString("Admin"));
                        }else if( jsonObject1.getString("type").equals("2")){
                            types.setText(jsonObject1.getString("Faculty"));
                        }else {
                            types.setText(jsonObject1.getString("Staff"));
                        }
                        if(jsonObject1.getString("status").equals("1"))
                        {
                            status_of.setText("Active");
                            status_of.setTextColor(Color.GREEN);
                        }else {
                            status_of.setText("Not Active");
                            status_of.setTextColor(Color.RED);
                        }
                        profile_mob.setText(jsonObject1.getString("mobile"));
                        pro_email.setText(jsonObject1.getString("email"));
                        pro_address.setText(jsonObject1.getString("address"));
                        pro_password.setText(jsonObject1.getString("password"));
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {



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
