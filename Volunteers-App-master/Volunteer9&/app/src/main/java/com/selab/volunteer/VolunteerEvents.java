package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VolunteerEvents extends AppCompatActivity {


    DatabaseReference databaseEvents;

    FirebaseAuth mAuth;

    ListView listViewVolunteerEvents;

    static int closeEntries;

    public static HashMap<EventOneSchema , String> eventMap;
    public static HashMap<String, String> tempMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteerevents);
        Spinner spinner = findViewById(R.id.sort);
        Spinner spinner1 = findViewById(R.id.filter);
        // Spinner click listener


        // Spinner Drop down elements
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Sort");
        categories.add("Distance");
        categories.add("Name");
        categories.add("Money");
        categories.add("Rating");
        categories.add("Date");

        ArrayList<String> genres = new ArrayList<String>();
        genres.add("Filter");
        genres.add("Inauguration");
        genres.add("NGO");
        genres.add("Ceremonies");
        genres.add("Fests");


        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genres);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner1.setAdapter(dataAdapter1);

        databaseEvents = FirebaseDatabase.getInstance().getReference().child("Events");
        mAuth = FirebaseAuth.getInstance();

        listViewVolunteerEvents = (ListView) findViewById(R.id.listview_volun);
        eventMap = new HashMap<>();
        tempMap = new HashMap<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                eventMap.clear();
                tempMap.clear();

                for( DataSnapshot hostsnapshot: dataSnapshot.getChildren() )
                {

                        for( DataSnapshot eventsnapshot: hostsnapshot.getChildren())
                        {
                            EventOneSchema eventOneSchema = eventsnapshot.getValue(EventOneSchema.class);
                            tempMap.put(eventsnapshot.getKey(), hostsnapshot.getKey());
                            eventMap.put( eventOneSchema, eventsnapshot.getKey() );
                        }

                }

                List<EventOneSchema> eventList = new ArrayList<EventOneSchema>(eventMap.keySet());

                EventList2 adapter = new EventList2(VolunteerEvents.this , eventList);
                listViewVolunteerEvents.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}