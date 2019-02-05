package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String desc = i.getStringExtra("desc");
        String time = i.getStringExtra("time");
        String venue = i.getStringExtra("venue");
        String date = i.getStringExtra("date");


        Log.d("details: ",""+title+":"+desc+":"+time+":"+date+":"+venue+":");


        TextView Ttitle,Tdesc, Ttime, Tvenue, Tdate;
        Ttitle = findViewById(R.id.title);
        Tdate = findViewById(R.id.date);
        Tdesc = findViewById(R.id.desc);
        Ttime = findViewById(R.id.time);
        Tvenue = findViewById(R.id.venue);


        Ttitle.setText(title);
        Tdesc.setText("Description: "+desc);
        Tdate.setText("Date: "+date);
        Ttime.setText("Time: "+time);
        Tvenue.setText("Venue: "+venue);

    }
}
