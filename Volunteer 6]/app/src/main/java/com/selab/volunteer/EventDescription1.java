package com.selab.volunteer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventDescription1 extends AppCompatActivity {

    TextView textView0;
    TextView Text1;
    TextView Text2;
    TextView Text3;
    TextView Text4;

    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdescription1);


        String str = getIntent().getStringExtra("Name");
        textView0 = findViewById(R.id.textView0);
        textView0.setText(str);

        Text1 = findViewById(R.id.des_location);
        Text1.setText(getIntent().getStringExtra("Location"));

        Text2 = findViewById(R.id.des_date);
        Text2.setText(getIntent().getStringExtra("Date"));

        Text3 = findViewById(R.id.des_payment);
        Text3.setText( getIntent().getStringExtra("Payment"));

        Text4 = findViewById(R.id.des_description);
        Text4.setText( getIntent().getStringExtra("Description"));

        b = findViewById(R.id.Register);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventDescription1.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                
            }
        });



    }

}