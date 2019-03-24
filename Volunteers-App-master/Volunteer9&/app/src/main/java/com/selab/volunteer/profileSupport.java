package com.selab.volunteer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class profileSupport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_support);

        ArrayList<String> text=new ArrayList<>();
        ArrayList<Integer> image=new ArrayList<>();


        text.add("Feedback");
        image.add(R.drawable.feedback);


        text.add("Report");
        image.add(R.drawable.report);



        LinearLayoutManager manager=new LinearLayoutManager(profileSupport.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView view=findViewById(R.id.accountlistview1);
        view.setLayoutManager(manager);

        Profilesupportadapter adapater=new Profilesupportadapter(text,image, profileSupport.this);
        view.setAdapter(adapater);



    }
}
