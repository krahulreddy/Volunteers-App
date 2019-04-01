package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import java.util.List;

public class EventDescription1 extends AppCompatActivity {

    TextView textView0;
    TextView Text1;
    TextView Text2;
    TextView Text3;
    TextView Text4;

    FirebaseAuth mAuth;
    static DatabaseReference databaseReference;
    ListView listViewReqVol;
    List <String> reqVol;

    static String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdescription1);

        mAuth = FirebaseAuth.getInstance();


        str = getIntent().getStringExtra("EventId");   //str is this event ID
        listViewReqVol = findViewById(R.id.listview_reqVol);
        reqVol = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Events/" + mAuth.getUid() + "/" + str);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textView0 = findViewById(R.id.textView0);
                textView0.setText(dataSnapshot.child("name").getValue().toString());

                Text1 = findViewById(R.id.des_location);
                Text1.setText(dataSnapshot.child("location").getValue().toString());

                Text2 = findViewById(R.id.des_date);
                Text2.setText(dataSnapshot.child("date").getValue().toString());

                Text3 = findViewById(R.id.des_payment);
                Text3.setText(dataSnapshot.child("payment").getValue().toString());

                Text4 = findViewById(R.id.des_description);
                Text4.setText(dataSnapshot.child("description").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button ChatBox = findViewById(R.id.Button_chatbox);
        final Button CloseEntriesButton = findViewById(R.id.Button_closeEntries);
        final Button OpenEntriesButton = findViewById(R.id.Button_openEntries);

        ChatBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDescription1.this,Chatbox.class);
                intent.putExtra("EventId", str);
                startActivity(intent);
            }
        });

        CloseEntriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseEntriesButton.setBackground(getResources().getDrawable(R.drawable.round_button1));
                OpenEntriesButton.setBackground(getResources().getDrawable(R.drawable.round_button));
                databaseReference.child("closeEntries").setValue(true);
                Toast.makeText(EventDescription1.this, "Entries are CLOSED", Toast.LENGTH_SHORT).show();
            }
        });
        OpenEntriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseEntriesButton.setBackground(getResources().getDrawable(R.drawable.round_button));
                OpenEntriesButton.setBackground(getResources().getDrawable(R.drawable.round_button1));
                databaseReference.child("closeEntries").setValue(false);
                Toast.makeText(EventDescription1.this, "Entries are OPENED", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference requestedReference = databaseReference.child("unApprovedId");


        requestedReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                reqVol.clear();

                for( DataSnapshot reqIdSnapshot: dataSnapshot.getChildren()) {
                    reqVol.add(reqIdSnapshot.getValue().toString());


                }

                RequestedVolunteersList adapter = new RequestedVolunteersList(EventDescription1.this , reqVol);
                listViewReqVol.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}