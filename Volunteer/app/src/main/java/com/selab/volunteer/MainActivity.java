package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessageFragment()).commit();

            navigationView.setCheckedItem(R.id.nav_events);
            NavigationView navigationview = (NavigationView) findViewById(R.id.nav_view);
            View headerview = navigationview.getHeaderView(0);

            ImageView view=(ImageView)headerview.findViewById(R.id.pro1);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(MainActivity.this,Profile.class);
                    startActivity(intent1);

        }
    });
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch(menuItem.getItemId()){

            case R.id.nav_events:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Events()).commit();
                Intent intent = new Intent(MainActivity.this, MyEvents.class);
                startActivity(intent);

                break;
            case R.id.nav_volunteer:
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new VolunteeringFragment()).commit();
                Intent intent1 = new Intent(MainActivity.this, VolunteerEvents.class);
                startActivity(intent1);
                break;
            case R.id.nav_wallet:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Wallet()).commit();
                break;
            case R.id.nav_host:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Hosting()).commit();
                Intent intent2 = new Intent(MainActivity.this, HostAnEvent.class);
                startActivity(intent2);

                break;



            case R.id.nav_share: {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String shareBody = "Your body here";
                String sharesub = "Your subject here";
                share.putExtra(Intent.EXTRA_SUBJECT, sharesub);
                share.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(share, "Share Using"));
            }
            break;
            case R.id.nav_feedback:
                Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show();
               Intent feedback = new Intent(MainActivity.this,feedbacktab.class);
               startActivity(feedback);
                break;
            case R.id.nav_report:
                Intent report = new Intent(MainActivity.this,Report.class);
                startActivity(report);
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
