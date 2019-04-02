package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    Intent Newpage;
    private int flag=0;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        final ProgressBar progressBar = findViewById(R.id.forgotpassword_bar);
        progressBar.setVisibility(View.INVISIBLE);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        Button forgot =(Button)findViewById(R.id.reset);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                Newpage = new Intent(ForgotPassword.this, Login.class);
                TextView email = (TextView) findViewById((R.id.email));
                String mail = email.getText().toString();

                String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(mail);

                if (matcher.matches()) {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override

                        public void onComplete(@NonNull Task<Void> task) {
                            TextView print = findViewById(R.id.print);
                            if(task.isSuccessful()) {
                                print.setText("A link has been sent to the given email id.");
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                            else {
                                print.setText("Error!!\nPlease check the email address");
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                }
                else {
                    email.setError("Enter a valid email id!!");
                    flag = 1;
                }
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}