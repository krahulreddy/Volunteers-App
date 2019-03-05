package com.selab.volunteer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EventDescription1 extends AppCompatActivity {

    TextView textView0;
    TextView Text1;
    TextView Text2;
    TextView Text3;
    TextView Text4;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdescription1);

        mAuth = FirebaseAuth.getInstance();


        final String str = getIntent().getStringExtra("EventId");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Events/" + VolunteerEvents.tempMap.get(str) + "/" + str);

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


        Button b = findViewById(R.id.Register);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //directly adding data without any condition
                String key = databaseReference.child("unApprovedId").push().getKey();
                databaseReference.child("unApprovedId").child(key).setValue(mAuth.getUid());

                Toast.makeText(EventDescription1.this, "Successfully Registered...", Toast.LENGTH_SHORT).show();
            }
        });






    }}