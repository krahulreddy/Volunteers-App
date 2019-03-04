package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HostAnEvent extends AppCompatActivity {

    DatabaseReference databaseEvents;

    EditText EventName,EventDesc,EventTimeHours,EventTimeMinutes,EventPay,EventLocation;
    TextView EventDate;
    private Button nextbutton = null;
    private Button GoToCalender=null;
    private Button cancelhost;
    EventOneSchema eventOneSchema,formax;

    long maxid = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostanevent);
        nextbutton = (Button) findViewById(R.id.NextStep);
        EventName=(EditText)findViewById(R.id.HostAnEventName);
        EventLocation=(EditText)findViewById(R.id.HostAnEventLocation);
        EventDate=(TextView) findViewById(R.id.HostAnEventDate);
        EventDesc=(EditText)findViewById(R.id.HostAnEventDescription);
        EventTimeHours=(EditText)findViewById(R.id.HostAnEventTimeHours);
        EventTimeMinutes=(EditText)findViewById(R.id.HostAnEventTimeMinutes);
        EventPay=(EditText)findViewById(R.id.HostAnEventMoney);
        GoToCalender=(Button)findViewById(R.id.GoToCalender);
        cancelhost = findViewById(R.id.CancelHost);



        Intent incoming = getIntent();
        final String date = incoming.getStringExtra("date");
        EventDate.setText(date);

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
                final String eventdate=EventDate.getText().toString();
                final String eventdesc=EventDesc.getText().toString();
                final String eventpay=EventPay.getText().toString();
                final String eventtimehours=EventTimeHours.getText().toString();
                final String eventtimeminutes=EventTimeMinutes.getText().toString();
                final String eventlocation=EventLocation.getText().toString();
                if(eventname.length()==0)
                {
                    EventName.requestFocus();
                    EventName.setError("NAME CANNOT BE EMPTY!");
                }
                else if(!eventname.matches("[a-zA-z ]*+"))
                {
                    EventName.requestFocus();
                    EventName.setError("ONLY ALPHABETICAL CHARACTERS ALLOWED!");
                }
                else if(eventdate.length()==0) {
                    EventDate.requestFocus();
                    EventDate.setError("SELECT A DATE!");
                }
                else if(eventpay.length()==0)
                {
                    EventPay.requestFocus();
                    EventPay.setError("PAYMENT CANNOT BE EMPTY!");

                }
                else if(eventtimeminutes.length()==0)
                {
                    EventTimeMinutes.requestFocus();
                    EventTimeMinutes.setError("MINUTES CANNOT BE EMPTY!");

                }
                else if(Integer.parseInt(eventtimeminutes)>60 || Integer.parseInt(eventtimeminutes)<0)
                {
                    EventTimeMinutes.requestFocus();
                    EventTimeMinutes.setError("INVALID TIME");
                }
                else if(eventtimehours.length()==0)
                {
                    EventTimeHours.requestFocus();
                    EventTimeHours.setError("HOURS CANNOT BE EMPTY!");
                }
                else if(Integer.parseInt(eventtimehours)>24 || Integer.parseInt(eventtimehours)<0)
                {
                    EventTimeHours.requestFocus();
                    EventTimeHours.setError("INVALID TIME");
                }
                else if(eventdesc.length()==0)
                {
                    EventDesc.requestFocus();
                    EventDesc.setError("DESCRIPTION CANNOT BE EMPTY!");

                }
                else if(eventlocation.length()==0)
                {
                    EventLocation.requestFocus();
                    EventLocation.setError("LOCATION CANNOT BE EMPTY");
                }
                else {
                    Toast.makeText(getApplicationContext(),"Event information saved!",Toast.LENGTH_LONG).show();
                    //Database starts herer

                    final EventOneSchema eventOneSchema = new EventOneSchema(eventname,eventdate,eventlocation,eventdesc, Integer.parseInt(eventpay));

                    databaseEvents = FirebaseDatabase.getInstance().getReference().child("HostedEvents");
                    databaseEvents.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                                maxid = (dataSnapshot.getChildrenCount());


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    databaseEvents.child(String.valueOf(maxid + 1)).setValue(eventOneSchema);


                  //  Toast.makeText(HostAnEvent.this, "" + formax.getMaxid()   , Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(view.getContext(), HostFinal.class);
                    startActivity(i);
                }
            }
        });
    }
}

