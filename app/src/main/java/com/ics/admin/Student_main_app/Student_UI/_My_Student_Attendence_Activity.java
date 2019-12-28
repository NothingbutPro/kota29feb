package com.ics.admin.Student_main_app.Student_UI;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ics.admin.Api_Retrofit.Retro_urls;
import com.ics.admin.Api_Retrofit.StudentApis;
import com.ics.admin.R;
import com.ics.admin.ShareRefrance.Shared_Preference;
import com.ics.admin.Student_main_app.LovelyProgressDialogs;
import com.ics.admin.Student_main_app.Student_Adapters._MY_Attendence_Adapter;
import com.ics.admin.Student_main_app._StudentModels._My_Student_Attendence_Model;
import com.ics.admin.Student_main_app._StudentModels._My_Student_Attendence_Data;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class _My_Student_Attendence_Activity extends AppCompatActivity {
    private HorizontalCalendar horizontalCalendar;
    HorizontalCalendarView horizontalCalendarView;
    RecyclerView _my_attendenc_rec;
    _MY_Attendence_Adapter student_attendance_adapter;
    ArrayList<_My_Student_Attendence_Data> studentAttendenceDataArrayList = new ArrayList<>();
    LovelyProgressDialogs lovelyProgressDialogs;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._my_student_attende_activity);
//        View view = ((Activity)this).getWindow().getDecorView().findViewById(R.layout._my_student_attende_activity);
        _my_attendenc_rec = findViewById(R.id._my_attendenc_rec);
        lovelyProgressDialogs = new LovelyProgressDialogs(_My_Student_Attendence_Activity.this);
        lovelyProgressDialogs.StartDialog("Attending Announcememt");
//        horizontalCalendarView = view.findViewById(R.id.attenddateview);
        //++++++++++++++++Calenderwa+++++++++++++++++++++++++++
        /* start 2 months ago from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -2);

        /* end after 2 months from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 2);

        // Default Date set to Today.
        final Calendar defaultSelectedDate = Calendar.getInstance();
//        horizontalCalendar = view.findViewById(R.id.attendcalendarView);
        horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.attendcalendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .configure()
                .formatTopText("MMM")
                .formatMiddleText("dd")
                .formatBottomText("EEE")
                .showTopText(true)
                .showBottomText(false)
                .textColor(Color.LTGRAY, Color.WHITE)
                .colorTextMiddle(Color.LTGRAY, Color.parseColor("#ffd54f"))
                .end()
                .defaultSelectedDate(defaultSelectedDate)
                .build();
//        String selectedDateStr = DateFormat.format("yyyy-MM-dd, ", horizontalCalendar.getSelectedDate().).toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            GetAllMyAttendence(new Shared_Preference().getStudent_id(this), LocalDate.now().toString());
        }
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                String selectedDateStr = DateFormat.format("yyyy-MM-dd, ", date).toString();
                // Toast.makeText(getActivity(), selectedDateStr + " selected!", Toast.LENGTH_SHORT).show();
                Log.i("onDateSelected", selectedDateStr + " - Position = " + position);
                try {
                    _my_attendenc_rec.setVisibility(View.VISIBLE);
                    if (studentAttendenceDataArrayList.size() != 0) {
                        studentAttendenceDataArrayList.clear();
                        student_attendance_adapter.notifyDataSetChanged();
                    }
                    GetAllMyAttendence(new Shared_Preference().getStudent_id(_My_Student_Attendence_Activity.this), selectedDateStr);
                } catch (Exception e) {
                    _my_attendenc_rec.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }

        });

        Log.i("Default Date", DateFormat.format("EEE, MMM d, yyyy", defaultSelectedDate).toString());
        //+++++++++++++++++++++

//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void GetAllMyAttendence(String Student_id , String attdate) {
//        LovelyProgressDialog lovelyProgressDialog =  new LovelyProgressDialog(getActivity())
//                .setIcon(R.drawable.asr_logo)
//                .setTitle("Registering Please wait")
//                .setTopColorRes(R.color.red);
//        lovelyProgressDialog.setCancelable(false);
        Log.e("Student_id" , "is"+Student_id);
        Log.e("attdate" , "is"+attdate);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS).writeTimeout(100, TimeUnit.SECONDS).build();
        Retrofit RetroLogin = new Retrofit.Builder().client(client)
                .baseUrl(Retro_urls.The_Base).addConverterFactory(GsonConverterFactory.create())
                .build();
        StudentApis RegApi = RetroLogin.create(StudentApis.class);
        Call<_My_Student_Attendence_Model> RegisterCall = RegApi.MY_STUDENT_ATTENDENCE_CALL(Student_id , attdate);
        RegisterCall.enqueue(new Callback<_My_Student_Attendence_Model>() {
            @Override
            public void onResponse(Call<_My_Student_Attendence_Model> call, Response<_My_Student_Attendence_Model> response) {
                Gson gson = new Gson();
                Log.d("attendence string" , ""+gson.toJson(response.body()) );
                if(response.body().getResponce()) {
                     studentAttendenceDataArrayList = response.body().getData();
                     student_attendance_adapter = new _MY_Attendence_Adapter(_My_Student_Attendence_Activity.this, studentAttendenceDataArrayList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_My_Student_Attendence_Activity.this);
                    _my_attendenc_rec.setLayoutManager(linearLayoutManager);
                    _my_attendenc_rec.setAdapter(student_attendance_adapter);

//                            Log.d("string" , ""+response.body().getData().getEmail());
                    if (response != null) {
                        lovelyProgressDialogs.DismissDialog();
                        Toast.makeText(_My_Student_Attendence_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        lovelyProgressDialogs.DismissDialog();
                        Toast.makeText(_My_Student_Attendence_Activity.this, "Either Email is duplicate or Password", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    lovelyProgressDialogs.DismissDialog();
                    Toast.makeText(_My_Student_Attendence_Activity.this, "No attendance found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<_My_Student_Attendence_Model> call, Throwable t) {
                lovelyProgressDialogs.DismissDialog();
                Log.d("local cause" , ""+t.getLocalizedMessage());
                Log.d("local cause" , ""+t.getMessage());
                Toast.makeText(_My_Student_Attendence_Activity.this, "No Attendance Found", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
