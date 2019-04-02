package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Waitlisted extends AppCompatActivity {

    DatabaseReference databaseEvents;
    FirebaseAuth mAuth;

    int i;

    Button button_a,button_w;
    ListView listViewUnApprovedEvents;

    public static HashMap<EventOneSchema , String> eventMap;
    public static HashMap<String, String> tempMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitlisted);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("UnApproved Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button button_a = (Button) findViewById(R.id.button_approved);
        final Button button_w = (Button) findViewById(R.id.button_waitlisted);

        databaseEvents = FirebaseDatabase.getInstance().getReference().child("Events");
        mAuth = FirebaseAuth.getInstance();

        listViewUnApprovedEvents = (ListView) findViewById(R.id.listView_UnApproved);
        eventMap = new HashMap<>();
        tempMap = new HashMap<>();


        button_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Waitlisted.this,Approved.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

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

                        for( DataSnapshot childUn : eventsnapshot.child("unApprovedId").getChildren() )
                        {
                            if(childUn.getValue().toString().equals(mAuth.getUid()))
                            {
                                EventOneSchema eventOneSchema = eventsnapshot.getValue(EventOneSchema.class);
                                tempMap.put(eventsnapshot.getKey(), hostsnapshot.getKey());
                                eventMap.put( eventOneSchema, eventsnapshot.getKey() );
                            }
                        }

                    }

                }

                List<EventOneSchema> eventList = new ArrayList(eventMap.keySet());

                EventList3 adapter = new EventList3(Waitlisted.this , eventList);
                listViewUnApprovedEvents.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
