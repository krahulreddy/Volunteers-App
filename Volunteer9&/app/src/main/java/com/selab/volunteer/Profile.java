package com.selab.volunteer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends AppCompatActivity {
    private static final int RC_PHOTO_PICKER = 2;
    String profilename1;
    private ChildEventListener childEventListener;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    TextView pname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              profilename1 = dataSnapshot.child("name").getValue().toString();
              Toast.makeText(Profile.this,"hello" + profilename1,Toast.LENGTH_SHORT).show();
              pname = findViewById(R.id.profilename);
              pname.setText(profilename1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //databaseReference.addChildEventListener();
       // profilename=databaseReference.getRoot().getClass().getName().toString();



        CircleImageView circleImageView = (CircleImageView)findViewById(R.id.profileDP);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        TextView textView=(TextView)findViewById(R.id.profilelogout);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this, Login.class);
                startActivity(intent);
            }
        });

        ImageView imageView=(ImageView) findViewById(R.id.profilelogout1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this, Login.class);
                startActivity(intent);
            }
        });

        LinearLayout settings=(LinearLayout)findViewById(R.id.profilesettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this, Settings.class);
                startActivity(intent);
            }
        });
        LinearLayout wallet=(LinearLayout)findViewById(R.id.profilewallet);
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this, MainActivity.class);
                startActivity(intent);


            }
        });
        LinearLayout events=(LinearLayout)findViewById(R.id.profilemyevents);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this, MyEvents.class);
                startActivity(intent);
            }
        });
    }
}
