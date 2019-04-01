package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

public class CalenderActivity extends AppCompatActivity {

    private  static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(CalenderActivity.this, HostAnEvent.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                month++;
                String date = dayOfMonth + "/" + month + "/"+ year ;
                Log.d(TAG, "onSelectedDayChange: dd/mm/yyyy:" + date);
                Intent intent = new Intent(CalenderActivity.this, HostAnEvent.class);
                intent.putExtra("date",date);
                startActivity(intent);

            }
        });

    }
}