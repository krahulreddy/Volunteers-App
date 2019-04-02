package com.selab.volunteer;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class feedbacktab extends AppCompatActivity {

    private int flag=0;
    private int check;
    private String name;
    private String femail;
    private String Reason;
    private String Repoemail;
    private String fsubject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacktab);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final EditText Name=(EditText)findViewById(R.id.feedbackname);
        final EditText email=(EditText)findViewById(R.id.feedbackemail);
        final EditText subject=(EditText)findViewById(R.id.feedbacksubject);
        final EditText suggestion=(EditText)findViewById(R.id.feedbacksuggestions);
        Button submit=(Button)findViewById(R.id.feedbacksubmit) ;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                name=Name.getText().toString();
                femail=email.getText().toString();
                Reason=subject.getText().toString();
                fsubject=suggestion.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Name.setError("Enter Name");
                    flag=1;
                }
                if(TextUtils.isEmpty(femail)) {
                    email.setError("Enter Email");
                    flag=1;
                }
                if(TextUtils.isEmpty(Reason))
                {
                    subject.setError("Enter subject");
                    flag=1;
                }
                if(TextUtils.isEmpty(fsubject))
                {
                    suggestion.setError("Enter Suggestion");
                    flag=1;
                }

                if(flag==0)
                {
                    Intent report=new Intent(feedbacktab.this,MainActivity.class);
                    startActivity(report);
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setData(Uri.parse("mailto:"));
                    i.putExtra(Intent.EXTRA_EMAIL,new String[] {"hemanthsai392@gmail.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                    i.putExtra(Intent.EXTRA_TEXT,"Dear Binary Warriors, \n"+ suggestion.getText().toString()+"\n Regards, " +email.getText().toString());

                    try{
                        startActivity(Intent.createChooser(i,"send mail"));
                    }catch(android.content.ActivityNotFoundException ex){
                        Toast.makeText(feedbacktab.this,"no mail app found", Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        Toast.makeText(feedbacktab.this,"unexpected error" + ex.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });    }
}
