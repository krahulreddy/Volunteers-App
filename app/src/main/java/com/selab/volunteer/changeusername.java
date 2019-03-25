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

public class changeusername extends AppCompatActivity {

    EditText oldusername;
    EditText newusername;
    EditText confirmusername;
    Button send;
    String old, newp, confirm;
    private FirebaseUser user;

    private DatabaseReference mdata;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeusername);

        newusername = (EditText) findViewById(R.id.changenewusername);
        confirmusername = (EditText) findViewById(R.id.changeconfirmusername);
        send = (Button) findViewById(R.id.usernamesubmit);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                newp = newusername.getText().toString().trim();
                confirm = confirmusername.getText().toString().trim();

                if(newp.isEmpty()||confirm.isEmpty())
                    Toast.makeText(changeusername.this,"Enter All Feilds Plz",Toast.LENGTH_LONG).show();
                else if(!newp.isEmpty()&&!newp.equals(confirm))
                {
                    Toast.makeText(changeusername.this,"New UserName Not Matching",Toast.LENGTH_LONG).show();
                }
                else
                {

                    mAuth= FirebaseAuth.getInstance();
                    mdata= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid()).child("name");
                    mdata.setValue(newp);
                    Toast.makeText(changeusername.this, "UserNameChanged", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(changeusername.this, MainActivity.class));

                }
            }
        });

    }
    }

