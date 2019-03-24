package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    EditText oldpassword;
    EditText newpassword;
    EditText confirmnewpassword;
    Button send;
    String old, newp, confirm;
    private FirebaseUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);

        oldpassword = (EditText) findViewById(R.id.changeoldpassword);
        newpassword = (EditText) findViewById(R.id.changenewpassword);
        confirmnewpassword = (EditText) findViewById(R.id.changeconfirmpassword);
        send = (Button) findViewById(R.id.changesubmit);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                old = oldpassword.getText().toString().trim();
                newp = newpassword.getText().toString().trim();
                confirm = confirmnewpassword.getText().toString().trim();

                if(old.isEmpty()||newp.isEmpty()||confirm.isEmpty())
                Toast.makeText(ChangePassword.this,"Enter All Feilds Plz",Toast.LENGTH_LONG).show();
                else if(!newp.isEmpty()&&!newp.equals(confirm))
                {
                    Toast.makeText(ChangePassword.this,"New Password Not Matching",Toast.LENGTH_LONG).show();
                }
                else
                {
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    final String email = user.getEmail();
                    AuthCredential credential = EmailAuthProvider.getCredential(email, old);

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(ChangePassword.this, "Password Not Changed", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ChangePassword.this, "LogIN with New Password", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ChangePassword.this, Login.class));

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(ChangePassword.this, "Something Went Wrong Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            }
        });

    }
}
