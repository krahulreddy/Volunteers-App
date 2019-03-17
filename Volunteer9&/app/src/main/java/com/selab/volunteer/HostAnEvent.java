package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HostAnEvent extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference databaseEvents;


    EditText EventName,EventDesc,EventPay;
    TextView EventDate;
    Date eventdate;
    private Button nextbutton = null;
    private Button GoToCalender=null;
    private Button cancelhost=null;
    int EventTimeHours,EventTimeMinutes;
    EventOneSchema eventOneSchema,formax;

    long maxid = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostanevent);
        nextbutton = (Button) findViewById(R.id.NextStep);
        EventName=(EditText)findViewById(R.id.HostAnEventName);
        EventDate=(TextView) findViewById(R.id.HostAnEventDate);
        EventDesc=(EditText)findViewById(R.id.HostAnEventDescription);
        TimePicker HostAnEventTime=(TimePicker)findViewById(R.id.HostAnEventTime) ;
        EventPay=(EditText)findViewById(R.id.HostAnEventMoney);
        GoToCalender=(Button)findViewById(R.id.GoToCalender);
        cancelhost=(Button)findViewById(R.id.CancelHost) ;
        Intent incoming = getIntent();
        final String date = incoming.getStringExtra("date");
        EventDate.setText(date);
        HostAnEventTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();
                EventTimeHours=hourOfDay;
                EventTimeMinutes=minute;

            }
        });
        mAuth=FirebaseAuth.getInstance();

        GoToCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentj = new Intent(HostAnEvent.this,CalenderActivity.class);
                startActivity(intentj);
            }
        });
        cancelhost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HostAnEvent.this, MainActivity.class);
                startActivity(intent);
            }
        });
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String eventname=EventName.getText().toString();
                final String eventdesc=EventDesc.getText().toString();
                final String eventpay=EventPay.getText().toString();
                //Have to remove this hardcoded location and add money to the database
                final String eventlocation="nitk";
                Date presdate= Calendar.getInstance().getTime();
                try {
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    eventdate= df.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(eventname.length()==0)
                {
                    EventName.requestFocus();
                    EventName.setError("NAME CANNOT BE EMPTY!");
                }
                else if(presdate.after(eventdate))
                {
                    EventDate.requestFocus();
                    EventDate.setError("EVENT DATE CANNOT BE A PAST DATE!");
                }
                else if(!eventname.matches("[a-zA-z ]*+"))
                {
                    EventName.requestFocus();
                    EventName.setError("ONLY ALPHABETICAL CHARACTERS ALLOWED!");
                }
                else if(date.length()==0) {
                    EventDate.requestFocus();
                    EventDate.setError("SELECT A DATE!");
                }
                else if(eventpay.length()==0)
                {
                    EventPay.requestFocus();
                    EventPay.setError("PAYMENT CANNOT BE EMPTY!");

                }
                else if(eventdesc.length()==0)
                {
                    EventDesc.requestFocus();
                    EventDesc.setError("DESCRIPTION CANNOT BE EMPTY!");

                }
                else {
                    Toast.makeText(getApplicationContext(),"Event information saved!",Toast.LENGTH_LONG).show();
                    //Database starts here

                    final EventOneSchema eventOneSchema = new EventOneSchema(eventname,date,eventlocation,eventdesc, Integer.parseInt(eventpay));



                    databaseEvents = FirebaseDatabase.getInstance().getReference().child("Events/" + mAuth.getUid());
                    String eventId = databaseEvents.push().getKey();

                    databaseEvents.child(eventId).setValue(eventOneSchema);


                  //  Toast.makeText(HostAnEvent.this, "" + formax.getMaxid()   , Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(view.getContext(), MapsActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}

