package com.selab.volunteer;

import android.os.Bundle;
import android.support.annotation.ColorInt;
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

import java.util.Objects;

public class EventDescription2 extends AppCompatActivity {

    TextView textView0;
    TextView Text1;
    TextView Text2;
    TextView Text3;
    TextView Text4;

    FirebaseAuth mAuth;
    static DatabaseReference databaseReference;

    static int closeEntries;

    static String str;

    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdescription2);

        mAuth = FirebaseAuth.getInstance();


        str = getIntent().getStringExtra("EventId");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Events/" + VolunteerEvents.tempMap.get(str) + "/" + str);

        final Button b = findViewById(R.id.Register);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (Objects.requireNonNull(dataSnapshot.child("closeEntries").getValue()).toString().equals("true")) {
                        b.setEnabled(false);
                    } else {
                        b.setEnabled(true);
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        DatabaseReference referUnApproved = databaseReference.child("unApprovedId");
        final String key = databaseReference.child("unApprovedId").push().getKey();

        referUnApproved.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;

                for( DataSnapshot requestedSnap: dataSnapshot.getChildren()) {
                    if (requestedSnap.getValue().toString().compareTo(mAuth.getUid()) != 0) {
                        {
                            i++;
                        }
                    } else {
                        b.setEnabled(false);
                        b.setText("Registered");
                        b.setTextColor(getResources().getColor(R.color.black));
                        b.setBackgroundColor(getResources().getColor(R.color.blue300));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference referApproved = databaseReference.child("approvedId");

        referApproved.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;

                for( DataSnapshot requestedSnap: dataSnapshot.getChildren()) {
                    if (requestedSnap.getValue().toString().compareTo(mAuth.getUid()) != 0) {
                        {
                            i++;
                        }
                    } else {
                        b.setEnabled(false);
                        b.setText("Registered And Accepted");
                        b.setTextColor(getResources().getColor(R.color.black));
                        b.setBackgroundColor(getResources().getColor(R.color.blue300));
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("unApprovedId").child(key).setValue(mAuth.getUid());
                Toast.makeText(EventDescription2.this, "Successfully Registered...", Toast.LENGTH_SHORT).show();
                b.setEnabled(false);
                b.setText("Registered");
                b.setTextColor(getResources().getColor(R.color.black));
                b.setBackgroundColor(getResources().getColor(R.color.blue300));

            }});

    }

    @Override
    protected void onStart() {
        super.onStart();

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


    }







    }
