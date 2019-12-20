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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.admin.Adapter.MenuExpandableAdapter;
import com.ics.admin.BasicAdmin.TeachersDetails.ViewTeachersActivity;
import com.ics.admin.DeleteDialog;
import com.ics.admin.FaculttyPermission;
import com.ics.admin.Interfaces.ProgressDialogs;
import com.ics.admin.Model.Faculties;
import com.ics.admin.Model.MenuPermisssion;
import com.ics.admin.Model.SubMenuPermissions;
import com.ics.admin.R;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ics.admin.BasicAdmin.HomeWork.HomeWorkActivity.selectteacher;
import static com.ics.admin.BasicAdmin.HomeWork.HomeWorkActivity.selectteacherStrings;
import static com.ics.admin.BasicAdmin.HomeWork.HomeWorkActivity.teacher_id;

public class ViewTeachersonAdapter extends RecyclerView.Adapter<ViewTeachersonAdapter.MyViewHolder> {
    ProgressDialogs progressDialogs = new ProgressDialogs();
    ArrayList<Faculties> facultiesArrayList = new ArrayList<>();
    ArrayList<MenuPermisssion>menuPermisssionheaderList = new ArrayList<>();
    ArrayList<String>menuPermisssionheaderListStrings = new ArrayList<>();
    ArrayList<SubMenuPermissions> menuPermissionsSubList= new ArrayList<>();
    HashMap<String, List<SubMenuPermissions>> menuPermissionsSubListHash = new HashMap<>();

    Activity activity;

    public ViewTeachersonAdapter(Activity activity, ArrayList facultiesArrayList)
    {
        this.activity = activity;
        this.facultiesArrayList = facultiesArrayList;


    }@NonNull
    @Override
    public ViewTeachersonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.faculty_adapter,viewGroup,false);
        return new ViewTeachersonAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewTeachersonAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.factname.setText(""+facultiesArrayList.get(i).getName());
        myViewHolder.factnumber.setText(""+facultiesArrayList.get(i).getMobile());
        myViewHolder.assignjob.setVisibility(View.GONE);
        myViewHolder.deltecher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ihisaab.in/school_lms/Adminapi/delete_teacher";
                new DeleteDialog().DeleteDialog("user_id",activity , facultiesArrayList.get(i).getUserId(), url);
            }
        });

        myViewHolder.editteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogs.ProgressMe((Context)activity ,activity);
                progressDialogs.KeepMeAlive();
                new NOWEDITTEACHER(2,myViewHolder,i).execute();
            }
        });
        myViewHolder.editteachbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolder.teacehrdli.getVisibility() == View.VISIBLE)
                {
                   myViewHolder.teacehrdli.setVisibility(View.GONE);
                }else {
                    myViewHolder.teacehrdli.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void showpermissions(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater li = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = li.inflate(R.layout.facultypermissiondialog, null);

//
//        rolesexpand.setonc
        builder.setPositiveButton("OK", null);
        builder.setView(dialogLayout);
        builder.show();
    }

    @Override
    public int getItemCount()
    {
        return facultiesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView circleimageview;
        TextView deltecher;
        Button editteacher;
        EditText edtteachename,edtemailtxt,edtmobileed;
        TextView factname;
        TextView factnumber;
        LinearLayout teacehrdli;
        com.github.florent37.shapeofview.shapes.CutCornerView editteachbtn;
        LinearLayout assignjob;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

//            name = (TextView) itemView.findViewById(R.id.name);
            factname = (TextView) itemView.findViewById(R.id.factnames);
            factnumber = (TextView) itemView.findViewById(R.id.factnumbers);
            deltecher = (TextView) itemView.findViewById(R.id.deltecher);
//            address = (TextView) itemView.findViewById(R.id.address);
            assignjob =  itemView.findViewById(R.id.assignjobs);
            edtteachename =  itemView.findViewById(R.id.edtteachename);
            editteacher =  itemView.findViewById(R.id.teachedtbtn);
            edtemailtxt =  itemView.findViewById(R.id.edtemailtxt);
            edtmobileed =  itemView.findViewById(R.id.edtmobileed);
            editteachbtn =  itemView.findViewById(R.id.editteachbtn);
            teacehrdli =  itemView.findViewById(R.id.teacehrdli);
//            editteachbtn =  itemView.findViewById(R.id.editteachbtn);
//            rolesexpand = (TextView) itemView.findViewById(R.id.rolesexpand);
//            circleimageview=(CircleImageView)itemView.findViewById(R.id.circleimageview);

        }
    }

    private class GetAllPermissions extends AsyncTask<String, Void, String> {
        String Name;
        String Phone_Number;
        String Email;
        String Password;
        String Address;
        String Faculty_id;
        private Dialog dialog;
        ExpandableListView rolesexpand;

        public GetAllPermissions(ExpandableListView rolesexpand) {
            this.rolesexpand = rolesexpand;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getmenulist");

                JSONObject postDataParams = new JSONObject();

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
                            menuPermisssionheaderListStrings.add(menu_name);
                            Log.e("menu_name name",""+menu_name);
                            menuPermisssionheaderList.add(new MenuPermisssion(permission_id,menu_id,menu_name,status));

                        }
                        int k=0;
                        for(;k<menuPermisssionheaderList.size();k++)
                        {
                            new ViewTeachersonAdapter.GETSubMenu(k,menuPermisssionheaderList.get(k).getMenu_name(),menuPermisssionheaderList.get(k).getMenu_id() ,2).execute();
                        }
                        if(k >=menuPermisssionheaderList.size() ) {
//                            menuExpandableAdapter = new MenuExpandableAdapter(activity.getContext(), menuPermisssionheaderList, menuPermisssionheaderListStrings, menuPermissionsSubListHash);
                            //        rolesexpand.setAdapter(menuExpandableAdapter);
                            // menuExpandableAdapter.notifyDataSetChanged();
                            rolesexpand.requestLayout();
                        }
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

    private class GETSubMenu extends AsyncTask<String, Void, String> {
        String menu_id;
        int user_id;
        String menu_name;
        int position;

        public GETSubMenu(int k, String menu_name, String menu_id, int user_id) {
            this.menu_id = menu_id;
            this.menu_name = menu_name;
            this.user_id = user_id;
            this.position = k;
        }


        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getsubmenulist");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("user_id", user_id);
                postDataParams.put("menu_id", menu_id);

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
                    if (!jsonObject.getBoolean("responce")) {
                        //    getotp.setVisibility(View.VISIBLE);
//                        Intent
                    } else {
                        int i = 0;
                        for (; i < jsonObject.getJSONArray("data").length(); i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(i);
                            String permission_id = jsonObject1.getString("permission_id");
                            String submenu = jsonObject1.getString("submenu");
                            String status = jsonObject1.getString("status");
//                            String menu_name = jsonObject1.getString("menu_name");
                            Log.e("submenu name",""+submenu);
                            menuPermissionsSubList.add(position , new SubMenuPermissions(permission_id, submenu,status,"mainstatus","sdfsdf"));

                        }
//                        if(position>= menuPermisssionheaderList.size()) {
                        if(i>=jsonObject.getJSONArray("data").length()) {
                            menuPermissionsSubListHash.put(menuPermisssionheaderListStrings.get(position), menuPermissionsSubList);
                        }

//                        }else {
                        Toast.makeText(activity, "position is"+position, Toast.LENGTH_SHORT).show();
//                        }
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

    private class NOWEDITTEACHER extends AsyncTask<String, Void, String> {
        int Faculty_id;
        private Dialog dialog;

        MyViewHolder myViewHolder;
        int position;

        public NOWEDITTEACHER(int Faculty_id, MyViewHolder myViewHolder, int i) {
            this.Faculty_id =Faculty_id;
            this.myViewHolder =myViewHolder;
            this.position =i;

        }

        @Override
        protected String doInBackground(String... arg0) {

            try {
                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/updatestaff");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("otp", Mobile_Number);
                postDataParams.put("name",myViewHolder.edtteachename.getText().toString() );
                postDataParams.put("email",myViewHolder.edtemailtxt.getText().toString() );
                postDataParams.put("mobile",myViewHolder.edtmobileed.getText().toString() );
                postDataParams.put("user_id",facultiesArrayList.get(position).getUserId());
                postDataParams.put("type", facultiesArrayList.get(position).getType());


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
                progressDialogs.EndMe();
                JSONObject jsonObject1 = null;
                Log.e("PostRegistration", result.toString());
                try {

                    jsonObject1 = new JSONObject(result);
                    if(jsonObject1.getBoolean("responce")){
                        Intent intent = new Intent(activity , ViewTeachersActivity.class);
                        activity.startActivity(intent);
                        activity.finish();
//                        getotp.setVisibility(View.VISIBLE);
//                      }

                    }else {
                        Toast.makeText(activity, "Nothing found", Toast.LENGTH_SHORT).show();
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
