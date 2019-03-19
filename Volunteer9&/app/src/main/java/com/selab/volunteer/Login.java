package com.selab.volunteer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {
    private int flag=0;
    private String email;
    private String password;
    private Intent intent, intent1;
    FirebaseAuth mAuth;

    EditText Username;
    EditText Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {}
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       Username=findViewById(R.id.username);
       Password=findViewById(R.id.password);
        final Button Login=(Button)findViewById(R.id.login) ;
        TextView forgotpassword=(TextView)findViewById(R.id.forgotpassword);

        TextView signup = (TextView)findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(Login.this, MainActivity.class);
            // Toast.makeText(Login.this, mAuth.getUid(), Toast.LENGTH_SHORT).show();
            DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid()).child("name");

            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Toast.makeText(Login.this, ""+ dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //intent.putExtra("CurrentUserId", mAuth.getUid() );
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), "Login Successful!!", Toast.LENGTH_LONG).show();
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                email=Username.getText().toString();
                password=Password.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Username.setError("Enter Username");
                    flag=1;
                }
                if(TextUtils.isEmpty(password)) {
                    Password.setError("Enter Password");
                    flag=1;
                }

                if(flag==0)
                {
//                    if(mAuth.getCurrentUser().)
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(com.selab.volunteer.Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                if(!mAuth.getCurrentUser().isEmailVerified())
                                {
                                    Toast.makeText(getApplicationContext(), "Verify " + mAuth.getCurrentUser().getEmail() + " and then login", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                   // Toast.makeText(Login.this, mAuth.getUid(), Toast.LENGTH_SHORT).show();
                                    DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid()).child("name");

                                        reff.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Toast.makeText(Login.this, ""+ dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    //intent.putExtra("CurrentUserId", mAuth.getUid() );
                                    startActivity(intent);
                                    //Toast.makeText(getApplicationContext(), "Login Successful!!", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error!!\nLogin Unsuccessful", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(com.selab.volunteer.Login.this,ForgotPassword.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1=new Intent(com.selab.volunteer.Login.this, SignUp.class);
                startActivity(intent1);
            }
        });

    }


}
