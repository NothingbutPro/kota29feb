package com.ics.admin.Student_main_app.Student_UI;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.OTPActivity;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.LovelyProgressDialogs;
import com.ics.admin.Student_main_app.Student_Adapters.Student_FeeAdapter;
import com.ics.admin.Student_main_app._StudentModels._Student_Pay_Fee_Model;

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

public class _Student_Pay_Fee extends Fragment {
    RecyclerView _std_pay_fee_rec;
    Student_FeeAdapter studentFeeAdapter;
    ArrayList<_Student_Pay_Fee_Model> student_pay_fee_models = new ArrayList<>();
    LovelyProgressDialogs lovelyProgressDialogs;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._student_pay_fee, container, false);
        _std_pay_fee_rec = view.findViewById(R.id._std_pay_fee_rec);
        lovelyProgressDialogs = new LovelyProgressDialogs(getActivity());
        lovelyProgressDialogs.StartDialog("Getting Your Fee Details");
        new GETALLMYFEES(new Shared_Preference().getStudent_id(getActivity())).execute();
        return view;
    }

    private class GETALLMYFEES extends AsyncTask<String, Void, String> {
        String student_id;
        private Dialog dialog;

        public GETALLMYFEES(String student_id) {
            this.student_id = student_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(" http://ihisaab.in/school_lms/Studentapi/getstudentfee");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("student_id", student_id);


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
                        cancel(true);
//                       Intent intent = new Intent(OTPActivity.this , LoginActivity.class);
//                       startActivity(intent);
                        for(int i =0;i<jsonObject.getJSONArray("data").length();i++)
                        {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(i);
                            String pay_id = jsonObject1.getString("pay_id");
                            String school_id = jsonObject1.getString("school_id");
                            String addedby = jsonObject1.getString("addedby");
                            String class_id = jsonObject1.getString("class_id");
                            String course_id = jsonObject1.getString("course_id");
                            String student_id = jsonObject1.getString("student_id");
                            String create_date = jsonObject1.getString("create_date");
                            String total_amount = jsonObject1.getString("total_amount");
                            String payby = jsonObject1.getString("payby");
                            String emi_month = jsonObject1.getString("emi_month");
                            String paymode = jsonObject1.getString("paymode");
                            String total_payamount = jsonObject1.getString("total_payamount");
                            student_pay_fee_models.add(new _Student_Pay_Fee_Model(pay_id,school_id,addedby,class_id,course_id,student_id,create_date,total_amount,payby
                            ,emi_month,paymode,total_payamount));
                        }
                        studentFeeAdapter = new Student_FeeAdapter(getActivity() , student_pay_fee_models);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        _std_pay_fee_rec.setLayoutManager(linearLayoutManager);
                        _std_pay_fee_rec.setAdapter(studentFeeAdapter);
//                .show();
                    }else {
                        Toast.makeText(getActivity(), "Nothing found", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }
                lovelyProgressDialogs.DismissDialog();
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
