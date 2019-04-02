package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class VolunteerEvents extends AppCompatActivity implements SearchView.OnQueryTextListener {


    DatabaseReference databaseEvents;
    List<EventOneSchema> eventList1 = new ArrayList<>();
    List<EventOneSchema> eventList2 = new ArrayList<>();
    List<EventOneSchema> eventList3 = new ArrayList<>();

    List<EventOneSchema> eventList8 = new ArrayList<>();
    FirebaseAuth mAuth;
    SearchView editsearch ;
    EventList2 adapter5;
    EventList2 adapter6;
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

        editsearch =  findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);
        // Spinner Drop down elements
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Sort");
        categories.add("Name");
        categories.add("Money");
        categories.add("Location");
        categories.add("Date");

        ArrayList<String> genres = new ArrayList<>();
        genres.add("Filter");
        genres.add("Charity");
        genres.add("Sports");
        genres.add("Cultural");
        genres.add("Others");


        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genres);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner1.setAdapter(dataAdapter1);

        databaseEvents = FirebaseDatabase.getInstance().getReference().child("Events");
        mAuth = FirebaseAuth.getInstance();

        listViewVolunteerEvents = findViewById(R.id.listview_volun);
        eventMap = new HashMap<>();
        tempMap = new HashMap<>();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                if(selectedItemText.compareTo("Name")==0){
                    EventList2 adapter1;
                    adapter1 = new EventList2(VolunteerEvents.this , eventList1);
                    listViewVolunteerEvents.setAdapter(adapter1);
                }
                if(selectedItemText.compareTo("Money")==0){
                    EventList2 adapter2 = new EventList2(VolunteerEvents.this , eventList3);
                    listViewVolunteerEvents.setAdapter(adapter2);
                }
                if(selectedItemText.compareTo("Location")==0){
                    EventList2 adapter3 = new EventList2(VolunteerEvents.this , eventList2);
                    listViewVolunteerEvents.setAdapter(adapter3);
                }
                if(selectedItemText.compareTo("Date")==0){
                    EventList2 adapter3 = new EventList2(VolunteerEvents.this , eventList8);
                    listViewVolunteerEvents.setAdapter(adapter3);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText1 = (String) parent.getItemAtPosition(position);
                // Notify the selected item text
                //Toast.makeText(getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
                if (selectedItemText1.compareTo("Sports") == 0) {
                    List<EventOneSchema> eventList4 = new ArrayList<>();
                    for (int i = 0; i < eventList1.size(); i++) {
                        if (eventList1.get(i).type.equals("Sports"))
                        {eventList4.add(eventList1.get(i));}
                    }
                    adapter6 = new EventList2(VolunteerEvents.this, eventList4);
                    listViewVolunteerEvents.setAdapter(adapter6);
                }
                if (selectedItemText1.compareTo("Cultural") == 0) {
                    List<EventOneSchema> eventList5 = new ArrayList<>();
                    for (int i = 0; i < eventList1.size(); i++) {

                        if (eventList1.get(i).type.equals("Cultural"))
                        {eventList5.add(eventList1.get(i));}
                    }
                    adapter6 = new EventList2(VolunteerEvents.this, eventList5);
                    listViewVolunteerEvents.setAdapter(adapter6);
                }
                if (selectedItemText1.equals("Others")) {
                    List<EventOneSchema> eventList6 = new ArrayList<>();
                    for (int i = 0; i < eventList2.size(); i++) {
                        if (eventList2.get(i).type.equals("Others"))
                        {   eventList6.add(eventList2.get(i));}
                    }
                    adapter6 = new EventList2(VolunteerEvents.this, eventList6);
                    listViewVolunteerEvents.setAdapter(adapter6);
                }
                if (selectedItemText1.compareTo("Charity") == 0) {
                    List<EventOneSchema> eventList7 = new ArrayList<>();
                    for (int i = 0; i < eventList1.size(); i++) {

                        if (eventList1.get(i).type.equals("Charity"))
                            eventList7.add(eventList1.get(i));
                    }
                    adapter6 = new EventList2(VolunteerEvents.this, eventList7);
                    listViewVolunteerEvents.setAdapter(adapter6);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                        if(hostsnapshot.getKey().equals(mAuth.getUid()))
                            continue;

                        for( DataSnapshot eventsnapshot: hostsnapshot.getChildren())
                        {

                            EventOneSchema eventOneSchema = eventsnapshot.getValue(EventOneSchema.class);
                            tempMap.put(eventsnapshot.getKey(), hostsnapshot.getKey());
                            eventMap.put( eventOneSchema, eventsnapshot.getKey() );
                        }

                }

                List<EventOneSchema> eventList = new ArrayList<>(eventMap.keySet());
                eventList1 = new ArrayList<>((eventMap.keySet()));
                eventList2 = new ArrayList<>((eventMap.keySet()));
                eventList3 = new ArrayList<>((eventMap.keySet()));
                Collections.sort(eventList1,new Sortbyname());
                Collections.sort(eventList2,new Sortbylocation());
                Collections.sort(eventList3,new SortbyPayment());
                Collections.sort(eventList8,new SortbyDate());
                EventList2 adapter = new EventList2(VolunteerEvents.this , eventList);
                listViewVolunteerEvents.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        List<EventOneSchema> f = new ArrayList<>();
      for(int i=0;i<eventList1.size();i++)
      {
          if(eventList1.get(i).name.contains(newText))
              f.add(eventList1.get(i));
      }
        adapter5 = new EventList2(VolunteerEvents.this , f);
      listViewVolunteerEvents.setAdapter(adapter5);
      return false;
      }



}
class Sortbyname implements Comparator<EventOneSchema>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(EventOneSchema a,  EventOneSchema b)
    {
        return a.name.compareToIgnoreCase(b.name);
    }
}

class Sortbylocation implements Comparator<EventOneSchema>
{
    // Used for sorting in ascending order of
    // roll name
    public int compare(EventOneSchema a, EventOneSchema b)
    {
        return a.location.compareToIgnoreCase(b.location);
    }
}
class SortbyPayment implements Comparator<EventOneSchema>
{
    // Used for sorting in ascending order of
    // roll name
    public int compare(EventOneSchema a, EventOneSchema b)
    {
        return b.payment - a.payment;
    }
}

class SortbyDate implements Comparator<EventOneSchema>
{
    // Used for sorting in ascending order of
    // roll name
    public int compare(EventOneSchema a, EventOneSchema b)
    {
        return a.date. compareTo(b.date);
    }
}

