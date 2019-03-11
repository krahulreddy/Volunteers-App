package com.selab.volunteer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Report extends AppCompatActivity {

    private int flag=0;
    private int check;
    private String name;
    private String Event;
    private String Reason;
    private String Repoemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        final EditText email=(EditText)findViewById(R.id.reportemail);
        email.setVisibility(View.GONE);
        check=0;

        final EditText Name=(EditText)findViewById(R.id.reportName);
        final EditText event=(EditText)findViewById(R.id.reportevent);
        final EditText reason=(EditText)findViewById(R.id.reportreason);
        Button submit=(Button)findViewById(R.id.reportsubmit) ;
        final CheckBox box=(CheckBox)findViewById(R.id.box);

        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(box.isChecked())
                {
                    check=1;
                    email.setVisibility(View.VISIBLE);
                }
                else
                {
                    check=0;
                    email.setVisibility(View.GONE);
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
                name=Name.getText().toString();
                Event=event.getText().toString();
                Reason=reason.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Name.setError("Enter Name");
                    flag=1;
                }
                if(TextUtils.isEmpty(Event)) {
                    event.setError("Enter Event");
                    flag=1;
                }
                if(TextUtils.isEmpty(Reason))
                {
                    reason.setError("Enter Reason");
                    flag=1;
                }

                if(check==1)
                {
                    Repoemail=email.getText().toString();
                    if(TextUtils.isEmpty(Repoemail))
                    {
                        flag=1;
                        email.setError("Enter Email");
                    }
                }

                if(flag==0)
                {
                    Intent report=new Intent(Report.this,MainActivity.class);
                    startActivity(report);
                    Toast.makeText(Report.this,"Reported Successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });    }
}
