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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VolunteerEvents extends AppCompatActivity {

    // Declare Variables

    public String[] name = new String[100];
    public String[] location = new String[100];
    public String[] date = new String[100];
    public String[] payment = new String[100];

    public String[] description = new String[100];
    DatabaseReference databaseEvents;
    int i;
    long j;




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

        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(i = 0; i< (j=dataSnapshot.getChildrenCount()); i++) {
                    name[i] = dataSnapshot.child(String.valueOf(i)).child("name").getValue().toString();
                    location[i] = dataSnapshot.child(String.valueOf(i)).child("location").getValue().toString();
                    date[i] = dataSnapshot.child(String.valueOf(i)).child("date").getValue().toString();
                    description[i] = dataSnapshot.child(String.valueOf(i)).child("description").getValue().toString();
                    payment[i] = dataSnapshot.child(String.valueOf(i)).child("payment").getValue().toString();
                }

                final ListView listView = (ListView) findViewById(R.id.listview_volun);
                CustomAdapter customAdapter =new CustomAdapter(name,date,location,payment,description);
                listView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    class CustomAdapter extends BaseAdapter {

        String[] name;
        String[] date;
        String[] location;

        String[] payment;
        String[] description;

        public CustomAdapter(String[] name, String[] date, String[] location, String[] payment, String[] description) {
            this.name = name;
            this.date = date;
            this.location = location;
            this.payment = payment;
            this.description = description;
        }

        @Override
        public int getCount() {
            return (int)j;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customlayout, null);

            TextView textView_name = (TextView)convertView.findViewById(R.id.textView_name);
            TextView textView_location = (TextView)convertView.findViewById(R.id.textView_location);
            TextView textView_date = (TextView)convertView.findViewById(R.id.textView_date);



            textView_name.setText(name[position]);
            textView_location.setText(location[position]);
            textView_date.setText(date[position]);



            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView txt_name = finalConvertView.findViewById(R.id.textView_name);
                    TextView txt_location = finalConvertView.findViewById(R.id.textView_location);
                    TextView txt_date = finalConvertView.findViewById(R.id.date);


                    Intent intent = new Intent(VolunteerEvents.this,EventDescription1.class);

                    intent.putExtra("Name",txt_name.getText().toString());
                    intent.putExtra("Date",txt_date.getText().toString());
                    intent.putExtra("Location", txt_location.getText().toString());
                    intent.putExtra("Payment", payment[position]);
                    intent.putExtra("Description", description[position]);

                    startActivity(intent);
                }
            });

            return finalConvertView;
        }

}}