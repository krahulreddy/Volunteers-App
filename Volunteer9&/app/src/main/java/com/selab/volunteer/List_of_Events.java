package com.selab.volunteer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class List_of_Events extends AppCompatActivity {
    /*
        String[] name={"Event1","Event2","Event3","Event4","Event5"};
        String[] list_titles = {"Marching against Pollution","Annual Program","New Year Celebration","Donation for Orphane","Campaigning"};
        String[] list_desc = {"Need some enthusiastic volunteer to alarm the authority about rising pollution","annual program will be held,so we need some volunteer","We need some volunteer for managing the things.volunterr can also join the party later","we need some volunteer to get the orphans meet their need","we need some enthusiastic volunteer to compaign against rising population"};
        String[] list_date = {" 12-01-2019"," 24-02-2109"," 31-12-2018"," 05-03-2019"," 30-02-2019"};
        String[] list_time = {" 2 PM"," 6 PM"," 12 PM"," 11 AM"," 1PM"};
        String[] list_venue = {" Gurugram"," Bangluru"," Hydrabad"," Mumbai"," Kolkata"};
    */
    FirebaseAuth mAuth;

    DatabaseReference hostedEvents;
    ListView listViewHostedEvents;


    public static HashMap<EventOneSchema , String> eventMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of__events);

        listViewHostedEvents = (ListView) findViewById(R.id.listview_hostedevents);



        mAuth = FirebaseAuth.getInstance();
        hostedEvents = FirebaseDatabase.getInstance().getReference().child("Events/" + mAuth.getUid());

        eventMap = new HashMap<>();


    }

    @Override
    protected void onStart() {
        super.onStart();

        hostedEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                eventMap.clear();

                for( DataSnapshot eventSnapshot: dataSnapshot.getChildren()) {
                    EventOneSchema eventOneSchema = eventSnapshot.getValue(EventOneSchema.class);

                    eventMap.put( eventOneSchema, eventSnapshot.getKey());
                }

                List<EventOneSchema> eventList = new ArrayList<EventOneSchema>(eventMap.keySet());

                EventList1 adapter = new EventList1(List_of_Events.this , eventList);
                listViewHostedEvents.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}