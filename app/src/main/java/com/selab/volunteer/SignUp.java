package com.selab.volunteer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    Intent Newpage;
    private int flag=0;
    private FirebaseAuth mAuth;
    DatabaseReference dataBase,dataBase1;
    DataSnapshot dataSnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        dataBase = FirebaseDatabase.getInstance().getReference().child("Users");
        final Button signup =(Button)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                Newpage = new Intent(SignUp.this, Login.class);

                TextView name = findViewById(R.id.name);
                TextView mail = findViewById(R.id.mail);
                TextView phone = findViewById(R.id.phone);
                TextView pass = findViewById(R.id.pass);
                TextView pass_cpy = findViewById(R.id.pass_cpy);

                final String Name = name.getText().toString().trim();
                final String Mail = mail.getText().toString();
                final String Phone = phone.getText().toString();
                final String Pass = pass.getText().toString();
                final String Cpy_Pass = pass_cpy.getText().toString();

                if (TextUtils.isEmpty(Name))
                {
                    name.setError("Enter name!!");
                    flag = 1;
                }
                if (TextUtils.isEmpty(Mail))
                {
                    mail.setError("Enter email!!");
                    flag = 1;
                }
                if (TextUtils.isEmpty(Phone))
                {
                    phone.setError("Enter Phone number!!");
                    flag = 1;
                }
                if (TextUtils.isEmpty(Pass))
                {
                    pass.setError("Enter Password!!");
                    flag = 1;
                }

                String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(Mail);

                Pattern name_pattern = Pattern.compile("[A-Za-z ]+");
                Matcher name_matcher = name_pattern.matcher(Name);

                if(!name_matcher.matches())
                {
                    name.setError("invalid name!!");
                    flag++;
                }
                if(!matcher.matches()) {
                    mail.setError("invalid e-mail");
                    flag++;
                }
                if(Phone.length() != 10){
                    phone.setError("invalid phone number");
                    flag++;
                }
                if(Pass.length() < 6){
                    pass.setError("Atleast 6 chars");
                    flag++;
                }
                if(!Pass.equals(Cpy_Pass)) {
                    pass_cpy.setError("Password Mismatch");
                    flag++;
                }

                if(flag == 0) {
                    //Toast.makeText(getApplicationContext(), "HI", Toast.LENGTH_LONG).show();
                    mAuth.createUserWithEmailAndPassword(Mail, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Users user;
                                            user = new Users();
                                            user.setName(Name);
                                            user.setMail(Mail);
                                            user.setPhone(Phone);
                                            user.setwallet();
                                            user.seturl();
                                            dataBase.child(mAuth.getUid()).setValue(user);
                                            mAuth.signOut();
                                            while(mAuth.getCurrentUser()!= null);
                                          /*  dataBase1 = FirebaseDatabase.getInstance().getReference();
                                            dataBase1.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    int users = (int)dataSnapshot.child("UserCount").getValue();
                                                    dataBase1.child("UserCount").setValue(users++);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            //Toast.makeText(getApplicationContext(),"users",Toast.LENGTH_SHORT).show();
                                           // dataBase1.setValue(users);
                                            Toast.makeText(SignUp.this, "bitch rahul", Toast.LENGTH_SHORT).show(); */
                                            startActivity(Newpage);
                                            Toast.makeText(getApplicationContext(), "Registration Successful!!\nVerify Email and login", Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Verification email could not be sent!! Check email id", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Registration could not be completed!\nPlease Try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}