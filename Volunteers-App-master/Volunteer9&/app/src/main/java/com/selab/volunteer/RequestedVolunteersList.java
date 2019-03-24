package com.selab.volunteer;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestedVolunteersList extends ArrayAdapter<String> {

    private Activity context;
    private List<String> reqVolList;
    DatabaseReference acceptedReference;
    DatabaseReference rejectedReference;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference reff;
    String reqId;


    public RequestedVolunteersList(Activity context   , List<String> reqVolList) {

        super(context, R.layout.customlayout2, reqVolList);
        this.context = context;
        this.reqVolList = reqVolList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.customlayout2, null, true);

        final TextView textView_userId = (TextView) listViewItem.findViewById(R.id.textView_userId);

        reqId = reqVolList.get(position);

        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(reqId).child("name");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textView_userId.setText( dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        //Button accept and reject working

       acceptedReference = EventDescription1.databaseReference.child("approvedId");
       rejectedReference = EventDescription1.databaseReference.child("unApprovedId");




        Button acceptReq = listViewItem.findViewById(R.id.Button_acceptReq);
        Button rejectReq = listViewItem.findViewById(R.id.Button_rejectReq);

        acceptReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adding in approvedId;
                String key = acceptedReference.push().getKey();
                acceptedReference.child(key).setValue(reqId);

                //removing from unapprovedId

                rejectedReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for( DataSnapshot userId : dataSnapshot.getChildren())
                        {
                            if(userId.getValue().toString().equals(reqId))
                            {
                                DatabaseReference reff = userId.getRef();
                                reff.removeValue();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        rejectReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //removing from unapprovedId
                rejectedReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for( DataSnapshot userId : dataSnapshot.getChildren())
                        {
                            if(userId.getValue().toString().equals(reqId))
                            {
                                DatabaseReference reff = userId.getRef();
                                reff.removeValue();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return listViewItem;
    }
}
