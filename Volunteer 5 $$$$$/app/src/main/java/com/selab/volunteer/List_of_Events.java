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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class List_of_Events extends AppCompatActivity {
/*
    String[] name={"Event1","Event2","Event3","Event4","Event5"};
    String[] list_titles = {"Marching against Pollution","Annual Program","New Year Celebration","Donation for Orphane","Campaigning"};
    String[] list_desc = {"Need some enthusiastic volunteer to alarm the authority about rising pollution","annual program will be held,so we need some volunteer","We need some volunteer for managing the things.volunterr can also join the party later","we need some volunteer to get the orphans meet their need","we need some enthusiastic volunteer to compaign against rising population"};
    String[] list_date = {" 12-01-2019"," 24-02-2109"," 31-12-2018"," 05-03-2019"," 30-02-2019"};
    String[] list_time = {" 2 PM"," 6 PM"," 12 PM"," 11 AM"," 1PM"};
    String[] list_venue = {" Gurugram"," Bangluru"," Hydrabad"," Mumbai"," Kolkata"};
*/
    public String[] name = new String[100];
    public String[] location = new String[100];
    public String[] date = new String[100];
    public String[] description = new String[100];
    public String[] payment = new String[100];

    int i;
    long j;

    DatabaseReference databaseEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of__events);

        databaseEvents = FirebaseDatabase.getInstance().getReference().child("HostedEvents");

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

                final ListView listView = (ListView) findViewById(R.id.listview_hostedevents);
                CustomAdapter customAdapter = new CustomAdapter(name,location,date,description,payment);
                listView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    class CustomAdapter extends BaseAdapter {

        String[] name;
        String[] location;
        String[] date;
        String[] description;
        String[] payment;

        public CustomAdapter(String[] name, String[] location, String[] date, String[] description, String[] payment) {
            this.name = name;
            this.location = location;
            this.date = date;
            this.description = description;
            this.payment = payment;
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



                    Intent intent = new Intent(List_of_Events.this,EventDescription2.class);

                    intent.putExtra("EventName",txt_name.getText().toString());
                    intent.putExtra("Location", txt_location.getText().toString());
                    intent.putExtra("Date", txt_date.getText().toString());
                    intent.putExtra("Description", description[position]);
                    intent.putExtra("Payment", payment[position]);

                    startActivity(intent);
                }
            });

            return finalConvertView;
        }
    }


}
