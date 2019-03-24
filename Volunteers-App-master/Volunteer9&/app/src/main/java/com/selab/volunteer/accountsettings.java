package com.selab.volunteer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class accountsettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountsettings);

        /*LinearLayout layout=(LinearLayout)findViewById(R.id.accountpicholder);
        LayoutInflater inflater=LayoutInflater.from(this);

        for(int i=0;i<3;i++)
        {
            View view=inflater.inflate(R.layout.accountview,layout,false);
            layout.addView(view);
        }
        */

         ArrayList<String> text=new ArrayList<>();
         ArrayList<Integer> image=new ArrayList<>();

         text.add("Change UserName");
         image.add(R.drawable.changeusername);

        text.add("Change Password");
        image.add(R.drawable.changepassword);

        text.add("Change PhoneNumber");
        image.add(R.drawable.changephone);

        text.add("Remove DP");
        image.add(R.drawable.changephoto);

        LinearLayoutManager manager=new LinearLayoutManager(accountsettings.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView view=findViewById(R.id.accountlistview);
        view.setLayoutManager(manager);

        AccountAdapater adapater=new AccountAdapater(text,image, accountsettings.this);
        view.setAdapter(adapater);





    }
}
