package com.ics.admin.Interfaces;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public  class GetDates {
    private String dates;

    public void PlaceDate(Context context , EditText DateEdit) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.d("LOG_APP", "DATE SELECTED " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                        dates = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        dates = year  + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        DateEdit.setText(dates);
                        //PUT YOUR LOGING HERE
                        //UNCOMMENT THIS LINE TO CALL TIMEPICKER
                        //openTimePicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }


}
