package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;
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
    String type;
    long maxid = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostanevent);
        Intent incoming = getIntent();

        final String date = incoming.getStringExtra("date");
        nextbutton = (Button) findViewById(R.id.NextStep);
        EventName=(EditText)findViewById(R.id.HostAnEventName);
        EventDate=(TextView) findViewById(R.id.HostAnEventDate);
        EventDesc=(EditText)findViewById(R.id.HostAnEventDescription);
        TimePicker HostAnEventTime=(TimePicker)findViewById(R.id.HostAnEventTime) ;
        EventPay=(EditText)findViewById(R.id.HostAnEventMoney);
        GoToCalender=(Button)findViewById(R.id.GoToCalender);
        cancelhost=(Button)findViewById(R.id.CancelHost) ;

        Spinner spinner = findViewById(R.id.types);
        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("Charity");
        typelist.add("Sports");
        typelist.add("Cultural");
        typelist.add("Others");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text

                if(selectedItemText.compareTo("Charity")==0){
                    type="Charity";
                }
                if(selectedItemText.compareTo("Sports")==0){
                    type="Sports";
                }
                if(selectedItemText.compareTo("Cultural")==0){
                    type="Cultural";
                }
                if(selectedItemText.compareTo("Others")==0){
                    type="Others";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typelist);

         //Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         //attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

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
                if (date == null) {
                    EventDate.requestFocus();
                    EventDate.setError("");
                    Toast.makeText(getApplicationContext(),"Select Event Date!",Toast.LENGTH_LONG).show();;
                } else {
                    final String eventdateS = EventDate.getText().toString().trim();
                    final String eventname = EventName.getText().toString().trim();
                    final String eventdesc = EventDesc.getText().toString().trim();
                    final String eventpay = EventPay.getText().toString().trim();
                    final String eventlocation = "nitk";
                    Date presdate = Calendar.getInstance().getTime();
                    if (eventdateS != null) {
                        try {
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            eventdate = df.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (eventdateS != null) {
                        if (presdate.after(eventdate)) {
                            EventDate.requestFocus();
                            EventDate.setError("EVENT DATE CANNOT BE A PAST DATE!");
                        }
                    }
                    if (eventname.length() == 0) {
                        EventName.requestFocus();
                        EventName.setError("NAME CANNOT BE EMPTY!");
                    } else if (eventdateS.length() == 0) {
                        EventDate.requestFocus();
                        EventDate.setError("EVENT DATE CANNOT BE EMPTY");
                    }
                     else if (!eventname.matches("[a-zA-z ]*+")) {
                        EventName.requestFocus();
                        EventName.setError("ONLY ALPHABETICAL CHARACTERS ALLOWED!");
                    } else if (date.length() == 0) {
                        EventDate.requestFocus();
                        EventDate.setError("SELECT A DATE!");
                    } else if (eventpay.length() == 0) {
                        EventPay.requestFocus();
                        EventPay.setError("PAYMENT CANNOT BE EMPTY!");

                    } else if (eventdesc.length() == 0) {
                        EventDesc.requestFocus();
                        EventDesc.setError("DESCRIPTION CANNOT BE EMPTY!");

                    } else {
                        Toast.makeText(getApplicationContext(), "Event information saved!", Toast.LENGTH_LONG).show();

                        final EventOneSchema eventOneSchema = new EventOneSchema(eventname, date, eventlocation, eventdesc, Integer.parseInt(eventpay));
                        eventOneSchema.closeEntries = false;


                        databaseEvents = FirebaseDatabase.getInstance().getReference().child("Events/" + mAuth.getUid());
                        String eventId = databaseEvents.push().getKey();

                        databaseEvents.child(eventId).setValue(eventOneSchema);

                        Intent i = new Intent(view.getContext(), MapsActivity.class);
                        i.putExtra("EventId", eventId);
                        startActivity(i);
                    }
                }
            }
        });
    }
}