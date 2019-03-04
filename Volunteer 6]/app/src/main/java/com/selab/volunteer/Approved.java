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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Approved extends AppCompatActivity {

    public String[] name = new String[100];

    public String[] location = new String[100];

    public String[] date = new String[100];

    public String[] approved = new String[100];

    public String[] description = new String[100];


    DatabaseReference databaseEvents;
    int i;
    long j;

    Button button_a,button_w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approved);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button button_a = (Button) findViewById(R.id.button_approved);
        final Button button_w = (Button) findViewById(R.id.button_waitlisted);

        databaseEvents = FirebaseDatabase.getInstance().getReference().child("Events");

        databaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(i = 0; i< dataSnapshot.getChildrenCount(); i++) {
                        Boolean app = dataSnapshot.child(String.valueOf(i)).child("approved").getValue(Boolean.class);
                    if( app ) {
                        name[(int)j] = dataSnapshot.child(String.valueOf(i)).child("name").getValue().toString();
                        location[(int)j] = dataSnapshot.child(String.valueOf(i)).child("location").getValue().toString();
                        date[(int)j] = dataSnapshot.child(String.valueOf(i)).child("date").getValue().toString();
                        approved[(int)j] = dataSnapshot.child(String.valueOf(i)).child("approved").getValue().toString();
                        description[(int)j] = dataSnapshot.child(String.valueOf(i)).child("description").getValue().toString();
                        j++;
                    }
                }

                final ListView listView = (ListView) findViewById(R.id.listView);
                Approved.CustomAdapter customAdapter = new Approved.CustomAdapter(name,location,date,approved,description);
                listView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Approved.this,Waitlisted.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    class CustomAdapter extends BaseAdapter {
        String[] name;
        String[] location;
        String[] date;
        String[] approved;
        String[] description;

        CustomAdapter(String[] name, String[] location, String[] date, String[] approved, String[] description) {
            this.name = name;
            this.location = location;
            this.date = date;
            this.approved = approved;
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
                    TextView txt_date = finalConvertView.findViewById(R.id.textView_date);


                    Intent intent = new Intent(Approved.this,EventDescription.class);

                    intent.putExtra("EventName",txt_name.getText().toString());
                    intent.putExtra("Location", txt_location.getText().toString());
                    intent.putExtra("Date", txt_date.getText().toString());
                    intent.putExtra("Approved", approved[position]);
                    intent.putExtra("Description", description[position]);

                    startActivity(intent);
                }
            });

            return finalConvertView;
        }
    }


}
