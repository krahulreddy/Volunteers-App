package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePhoneNumber extends AppCompatActivity {

    EditText newusername;
    EditText confirmusername;
    Button send;
    String  newp, confirm;
    private FirebaseUser user;

    private DatabaseReference mdata;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone_number);

        newusername = (EditText) findViewById(R.id.changenewphonenumber);
        confirmusername = (EditText) findViewById(R.id.changeconfirmphonenumber);
        send = (Button) findViewById(R.id.phonenumbersubmit);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                newp = newusername.getText().toString().trim();
                confirm = confirmusername.getText().toString().trim();

                if(newp.isEmpty()||confirm.isEmpty())
                    Toast.makeText(ChangePhoneNumber.this,"Enter All Feilds Plz",Toast.LENGTH_LONG).show();
                else if(!newp.isEmpty()&&!newp.equals(confirm))
                {
                    Toast.makeText(ChangePhoneNumber.this,"New PhoneNumber Not Matching",Toast.LENGTH_LONG).show();
                }
                else if(newp.length()!=10){
                    Toast.makeText(ChangePhoneNumber.this, "Invalid PhoneNo", Toast.LENGTH_SHORT).show();

                }
                else
                {

                    mAuth= FirebaseAuth.getInstance();
                    mdata= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid()).child("phone");
                    mdata.setValue(newp);
                    Toast.makeText(ChangePhoneNumber.this, "PhoneNumberChanged", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ChangePhoneNumber.this, MainActivity.class));

                }
            }
        });

    }
    }

