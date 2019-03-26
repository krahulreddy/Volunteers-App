package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Transact extends AppCompatActivity {
    DatabaseReference databaseReference, tempdata,tempdata2;
    FirebaseAuth mAuth;
    DataSnapshot dataSnapshot;
    Button send;
    TextInputEditText receiver, money;
    String amount, receivemoney;
    int amount1,receivemoney1,receivemoney2;
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transact);
        mAuth = FirebaseAuth.getInstance();
        receiver = (TextInputEditText) findViewById(R.id.receivermail);
        money = (TextInputEditText) findViewById(R.id.receivermoney);


        //mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                amount = dataSnapshot.child("wallet").getValue().toString();
                amount1 = Integer.parseInt(amount);
               //Toast.makeText(Transact.this, ""+amount1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        send = (Button)findViewById(R.id.sendmoney);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount1 < Integer.parseInt(money.getText().toString()) || Integer.parseInt(money.getText().toString()) <= 0){
                    Toast.makeText(Transact.this, "Cannot Transact", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Transact.this, Wallet.class);
                    startActivity(intent);
                }
                else {
                    tempdata2 = FirebaseDatabase.getInstance().getReference().child("Users");
                    tempdata2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot childsnap : dataSnapshot.getChildren()){
                                if((Objects.requireNonNull(childsnap.child("mail").getValue()).toString()).equals(receiver.getText().toString().trim())){
                                    text = childsnap.getKey().toString();
                                    break;
                                }
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //for (int i = 0; i < 8; i++) {
                    if(text!=null) {
                        tempdata = FirebaseDatabase.getInstance().getReference().child("Users/" + text);
                        tempdata.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                receivemoney = dataSnapshot.child("wallet").getValue().toString();
                                receivemoney1 = Integer.parseInt(receivemoney);
                                receivemoney2 = receivemoney1;
                                receivemoney1 = receivemoney2 + Integer.parseInt(money.getText().toString());
                                tempdata.child("wallet").setValue(receivemoney1);
                                amount1 = amount1 - Integer.parseInt(money.getText().toString());
                                databaseReference.child("wallet").setValue(amount1);

                                //Toast.makeText(Transact.this, ""+receivemoney1, Toast.LENGTH_SHORT).show();
                               // receivemoney1 = receivemoney2 + Integer.parseInt(money.getText().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Intent intent1 = new Intent(Transact.this, Profile.class);
                        startActivity(intent1);



                        //}
                    }
                    else
                    {
                        Toast.makeText(Transact.this, "Invalid User", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    }


