package com.selab.volunteer;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    FirebaseAuth mAuth;
    DatabaseReference reff;


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

        final String reqId = reqVolList.get(position);

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

        //textView_userId.setText( reqId );

        //Button accept and reject working

        acceptedReference = EventDescription1.databaseReference.child("approvedId");
        mAuth = FirebaseAuth.getInstance();

       /* Button acceptReq = context.findViewById(R.id.Button_acceptReq);
        Button rejectReq = context.findViewById(R.id.Button_rejectReq);

        acceptReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adding in approvedId;
                String key = EventDescription2.databaseReference.child("approvedId").push().getKey();
                EventDescription2.databaseReference.child("approvedId").child(key).setValue(reqId);

                //removing from unapprovedId
                EventDescription2.databaseReference.child("unApprovedId").child(reqId).removeValue();
            }
        });
        */
        return listViewItem;
    }
}
