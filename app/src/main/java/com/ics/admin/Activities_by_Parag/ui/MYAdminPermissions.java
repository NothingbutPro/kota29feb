package com.ics.admin.Activities_by_Parag.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ics.admin.Adapter.MenuExpandableAdapter;
import com.ics.admin.Model.MenuPermisssion;
import com.ics.admin.Model.SubMenuPermissions;
import com.ics.admin.OTPActivity;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class MYAdminPermissions extends AppCompatActivity {
    public List<String> _MenuPermisssionslistDataHeader = new ArrayList<>();
    ArrayList<SubMenuPermissions> menuPermissionsSubList= new ArrayList<>();
    ArrayList<String> menuPermissionsSubListString= new ArrayList<>();
    public ArrayList<MenuPermisssion> menuPermisssionheaderListStrings;
    ArrayList<String>menuPermisssionheaderList = new ArrayList<>();
    public HashMap<SubMenuPermissions, List<SubMenuPermissions>> _ListHashMaplistDataChild = new HashMap<>();
    RecyclerView adminec;
    private MenuExpandableAdapter menuExpandableAdapter;
    Shared_Preference shared_preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myadmin_permissions);
        adminec = findViewById(R.id.adminec);
        shared_preference=new Shared_Preference();
        new GETALLMYPERMISSIONS(shared_preference.getId(MYAdminPermissions.this)).execute();
    }
    private class GETALLMYPERMISSIONS extends AsyncTask<String, Void, String> {
        String Faculty_id;
        private Dialog dialog;

        public GETALLMYPERMISSIONS(String Faculty_id) {
            this.Faculty_id = Faculty_id;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getsidemenu");

                JSONObject postDataParams = new JSONObject();
//                postDataParams.put("user_id", OTPActivity.Faculty_id);
                postDataParams.put("user_id", Faculty_id);
//                postDataParams.put("teacher_id", "4");


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
//                       Intent intent = new Intent(OTPActivity.this , LoginActivity.class);
//                       startActivity(intent);
                        Toast.makeText(getApplication(),"You are not registerd"+result, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String menu_name =null;
                        SubMenuPermissions sUbMenuModel = null;
                        for(int i=0;i<jsonObject.getJSONObject("data").length();i++)
                        {
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("a"+i);

                            for (int px=0;px<jsonArray.length();px++)
                            {
                                String menu_id = jsonArray.getJSONObject(px).getString("menu_id");
                                menu_name = jsonArray.getJSONObject(px).getString("menu_name");
                                String submenu = jsonArray.getJSONObject(px).getString("submenu");
                                sUbMenuModel = new SubMenuPermissions(menu_id,menu_name,submenu);

//                                menuPermissionsSubListString.
                                if(px==0) {
                                    menuPermissionsSubList.add(sUbMenuModel);
//                                    _MenuPermisssionslistDataHeader.add(menu_name);

                                }

                            }
//                            _ListHashMaplistDataChild.put(sUbMenuModel,menuPermissionsSubList);

                        }
//                        Log.e("full hash map",""+_ListHashMaplistDataChild.entrySet());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MYAdminPermissions.this);
                        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
                        adminec.setLayoutManager(linearLayoutManager);
                        menuExpandableAdapter = new MenuExpandableAdapter(MYAdminPermissions.this, menuPermissionsSubList);
                        adminec.setAdapter(menuExpandableAdapter);
                        menuExpandableAdapter.notifyDataSetChanged();
                        adminec.requestLayout();
//                        for(int i=0;i<jsonObject.getJSONArray("data").length();i++)
//                        {
//                            JSONObject jsonObject1 = jsonObject.getJSONArray("data").getJSONObject(i);
//                            String  permission_id =jsonObject1.getString("permission_id");
//                            String  menu_id =jsonObject1.getString("menu_id");
//                            String  menu_name =jsonObject1.getString("menu_name");
//                            String  status =jsonObject1.getString("status");
//                            if(i<5)
//                            {
//                                MenuItem fact1 = navigationView.findViewById(R.id.nav_home);
//                                fact1.setTitle(""+menu_name);
//
//                            }
////                            menuPermisssionList.add(new MenuPermisssion(permission_id,menu_id,menu_name,status));
////sdf
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
}
