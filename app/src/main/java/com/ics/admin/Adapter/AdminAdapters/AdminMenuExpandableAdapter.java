package com.ics.admin.Adapter.AdminAdapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ics.admin.Adapter.SubMenuAdapter;
import com.ics.admin.Adapter.SubPermissionAdapter;
import com.ics.admin.MasterManuActivity;
import com.ics.admin.Model.SubMenuPermissions;
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
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminMenuExpandableAdapter  extends RecyclerView.Adapter<AdminMenuExpandableAdapter.MyViewHolder> {
    public static ArrayList<String> Myproducttiles = new ArrayList<>();
    public List<SubMenuPermissions> menuPermisssionList;
    //    public List<SubMenuPermissions> subMenuPermisssionsList = new ArrayList<>();
    SubPermissionAdapter subPermissionAdapter;
    int pos_try;
    String PsoitionNAme;
    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
    private Context mContext;
    private SubMenuPermissions menuPermisssion;
    private RecyclerView subrec;
    ArrayList<SubMenuPermissions> menuPermissionsSubList= new ArrayList<>();
    private SubMenuAdapter menuExpandableAdapter;
    String menuid;
    Shared_Preference shared_preference;
    public AdminMenuExpandableAdapter(Context context, ArrayList<SubMenuPermissions> menuPermisssion) {
        mContext = context;
        this.menuPermisssionList = menuPermisssion;
        setHasStableIds(true);
    }

    @Override
    public AdminMenuExpandableAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminprmission, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminprmission, parent, false);
        return new AdminMenuExpandableAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdminMenuExpandableAdapter.MyViewHolder holder, final int position) {
        menuPermisssion = menuPermisssionList.get(position);
        this.pos_try = position;
        holder.namestff.setText(menuPermisssionList.get(position).getMenu_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "working on it", Toast.LENGTH_SHORT).show();
                try {
                    PsoitionNAme = menuPermisssionList.get(position).getMenu_name();
                    Intent in = new Intent(mContext, MasterManuActivity.class);
                    if(PsoitionNAme.equals("Homework")) {
                        in.putExtra("ids", "Home Work");
                    }else if(PsoitionNAme.contains("Fee")){
                        in.putExtra("ids", "Fee Structure Management");
                    }else {
                        in.putExtra("ids", PsoitionNAme);
                    }
                    mContext.startActivity(in);

                    menuPermissionsSubList.clear();
                    //  AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    Dialog dialog = new Dialog(v.getContext());
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.facultypermissiondialog);

                    Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

//                    LayoutInflater li = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View dialogLayout = li.inflate(R.layout.facultypermissiondialog, null);
                    TextView roles = dialog.findViewById(R.id.roles);
                    ToggleButton dialogtoggel = dialog.findViewById(R.id.dialogtoggel);
                    subrec = dialog.findViewById(R.id.subrec);
                    shared_preference = new Shared_Preference();
                    subrec.setVisibility(View.VISIBLE);
                    dialogtoggel.setVisibility(View.GONE);

                    roles.setText(menuPermisssionList.get(position).getMenu_name());
//                roles.setText(menuPermisssionList.get(position).getMenu_name());
//                    dialog.show();
//                    new GELALLMYPERMISSIONS(String.valueOf(position), shared_preference.getId(mContext)).execute();

                }catch (Exception e)
                {
                    Toast.makeText(mContext, "Please wait", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
////        rolesexpand.setonc
//                builder.setPositiveButton("OK", null);
//                builder.setView(dialogLayout);
//                builder.show();
            }
        });
        StrictMode.setVmPolicy(builder.build());

    }


    private class GELALLMYPERMISSIONS  extends AsyncTask<String, Void, String> {
        String position;
        private Dialog dialog;
        String Faculty_id;

        public GELALLMYPERMISSIONS(String position, String id) {
            this.position = position;
            this.Faculty_id=id;
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://ihisaab.in/school_lms/Adminapi/getsidemenu");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("user_id",Faculty_id);
//                postDataParams.put("user_id",Faculty_id);
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
                        Toast.makeText(mContext,"You are not registerd"+result, Toast.LENGTH_SHORT).show();
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
//                                if(px==0)
                                if(PsoitionNAme.equals(menu_name)) {
                                    menuPermissionsSubList.add(sUbMenuModel);
                                }
//                                    _MenuPermisssionslistDataHeader.add(menu_name);

//                                }

                            }
//                            _ListHashMaplistDataChild.put(sUbMenuModel,menuPermissionsSubList);

                        }
//                        Log.e("full hash map",""+_ListHashMaplistDataChild.entrySet());
                        Log.e("DATA :","__________________________________>>>"+menuPermissionsSubList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        subrec.setLayoutManager(linearLayoutManager);
                        menuExpandableAdapter = new SubMenuAdapter(mContext, menuPermissionsSubList);
                        subrec.setAdapter(menuExpandableAdapter);
                        menuExpandableAdapter.notifyDataSetChanged();
                        subrec.requestLayout();
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
    @Override
    public int getItemCount() {
        return menuPermisssionList.size();
    }

    @Override
    public long getItemId(int position) {
//        return super.getItemId(position);
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView stffimg;
        TextView namestff, emailstff, stffid;
        ToggleButton maintoggel;

        public MyViewHolder(View itemView) {
            super(itemView);
            //   mname = (TextView) itemView.findViewById(R.id.mname);
            // manu_img =  itemView.findViewById(R.id.manu_img);
            namestff = itemView.findViewById(R.id.name);
            maintoggel = itemView.findViewById(R.id.maintoggel);

            //     adddes =  itemView.findViewById(R.id.adddes);


        }
}
}
