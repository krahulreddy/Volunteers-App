package com.selab.volunteer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EventDescription extends AppCompatActivity {

    TextView textView0;
    TextView Text1;
    TextView Text2;
    TextView Text3;
    TextView Text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdescription);


        String str = getIntent().getStringExtra("EventName");
        textView0 = findViewById(R.id.textView0);
        textView0.setText(str);

        Text1 = findViewById(R.id.des_location);
        Text1.setText(getIntent().getStringExtra("Location"));

        Text2 = findViewById(R.id.des_date);
        Text2.setText(getIntent().getStringExtra("Date"));

        Text4 = findViewById(R.id.des_description);
        Text4.setText( getIntent().getStringExtra("Description"));


    }}