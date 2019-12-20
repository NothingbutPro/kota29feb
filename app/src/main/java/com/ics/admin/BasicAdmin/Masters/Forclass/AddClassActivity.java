package com.ics.admin.BasicAdmin.Masters.Forclass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class AddClassActivity extends AppCompatActivity {
    EditText edt_name;
    Button btn_save;
    Shared_Preference shared_preference;
    ArrayList<String> ClassNAmes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        edt_name = (EditText) findViewById(R.id.edt_name_s);
        btn_save = (Button) findViewById(R.id.btn_save_s);

        shared_preference=new Shared_Preference();


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Classname(Shared_Preference.getId(AddClassActivity.this),edt_name.getText().toString()).execute();
              //  Toast.makeText(AddClassActivity.this, "Add Class Secessfull", Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(AddClassActivity.this ,AddBatchActivity.class);
//                startActivity(intent);
            }

        });
    }
    private class Classname extends AsyncTask<String, Void, String> {

        String user_id;
        String class_name;
        private Dialog dialog;

        public Classname(String toString, String num) {
            this.user_id = toString;
            this.class_name=num;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/add_class");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("user_id", user_id);
//                postDataParams.put("name", Faculty_id);
                postDataParams.put("class_name", class_name);

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

                JSONObject jsonObject = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject = new JSONObject(result);

                    if(jsonObject.getBoolean("responce")){

                        Toast.makeText(AddClassActivity.this, "Add class Successfull", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AddClassActivity.this, ClassViewActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
//
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
