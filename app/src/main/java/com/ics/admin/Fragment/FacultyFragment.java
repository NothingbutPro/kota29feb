package com.ics.admin.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.ics.admin.Adapter.AdminAdapters.FacultyAdapter;
import com.ics.admin.BasicAdmin.AddFacultyActivity;
import com.ics.admin.Model.Faculties;
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
import java.util.Arrays;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;


public class FacultyFragment extends Fragment {

    RecyclerView recyclerView;
    com.github.clans.fab.FloatingActionButton fab;
    Shared_Preference shared_preference;
    ArrayList Names = new ArrayList<>(Arrays.asList("Faculty Name", " Faculty Number", "Faculty Class", "Address"));
    ArrayList<Integer> Images = new ArrayList<>(Arrays.asList(R.drawable.homework,R.drawable.ic_community));
    ArrayList<Faculties> facultiesArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_faculty, container, false);

        // FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.fab);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        shared_preference=new Shared_Preference();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AddFacultyActivity.class);
                startActivity(intent);
//                getActivity().finish();


            }
        });
        new GetAllFaculties(shared_preference.getId(getActivity())).execute();

        return view;


    }


    private class GetAllFaculties extends AsyncTask<String, Void, String> {
        String Mobile_Number;
        String Faculty_id;
        private Dialog dialog;


        public GetAllFaculties(String Faculty_id) {
            this.Faculty_id =Faculty_id;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getallteacher");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("user_id", Faculty_id);


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
                    if(!jsonObject1.getBoolean("responce")){
                        Toast.makeText(getActivity(), "Nothing found", Toast.LENGTH_SHORT).show();
//                        getotp.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplication(),"strong OTP"+result, Toast.LENGTH_SHORT).show();
                    }else {
                        for(int i=0;i<jsonObject1.getJSONArray("data").length();i++) {
                            JSONObject jsonObject = jsonObject1.getJSONArray("data").getJSONObject(i);
                            String userid = jsonObject.getString("user_id");
                            String name = jsonObject.getString("name");
                            String email = jsonObject.getString("email");
                            String password = jsonObject.getString("password");
                            String mobile = jsonObject.getString("mobile");
                            String address = jsonObject.getString("address");
                            String status = jsonObject.getString("status");
                            String type = jsonObject.getString("type");
                            String addedby = jsonObject.getString("addedby");
                            String fromwhere = "forfaculty";

                            facultiesArrayList.add(new Faculties(userid,name,email,password,mobile,address,status,type,addedby, fromwhere));
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

                        FacultyAdapter facultyAdapter = new FacultyAdapter(getActivity(),facultiesArrayList);
                        recyclerView.setAdapter(facultyAdapter); // set the Adapter to RecyclerView
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

