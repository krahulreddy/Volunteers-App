package com.selab.volunteer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AccountAdapater extends RecyclerView.Adapter<AccountAdapater.GooglePay> {

    public AccountAdapater(ArrayList<String> text, ArrayList<Integer> image, Context contexts) {
        this.text = text;
        this.image = image;
        this.contexts = contexts;
    }

    private ArrayList<String> text=new ArrayList<>();
    private ArrayList<Integer> image=new ArrayList<>();
    private Context contexts;



    @NonNull
    @Override
    public GooglePay onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)  {

        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.accountview,viewGroup,false);
        return new GooglePay(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GooglePay googlePay, final int i) {
        final Intent[] intent = {null};

        googlePay.textview.setText(text.get(i));
        googlePay.imageview.setImageResource(image.get(i));
        googlePay.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(intents.get(i));
                //Toast.makeText(contexts, text.get(i), Toast.LENGTH_SHORT).show();

                switch(i)
                {
                    case 0:
                        intent[0] =new Intent(contexts, changeusername.class);
                        contexts.startActivity(intent[0]);
                        break;
                    case 1:
                        intent[0] =new Intent(contexts,ChangePassword.class);
                        contexts.startActivity(intent[0]);
                        break;
                    case 2:
                        intent[0] =new Intent(contexts, ChangePhoneNumber.class);
                        contexts.startActivity(intent[0]);
                        break;
                    case 3:
                       // intent[0] =new Intent(contexts,changeusername.class);
                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("url").setValue("https://mir-s3-cdn-cf.behance.net/project_modules/1400/a6938438650505.598fa5ee0cdf7.jpg");
                        Toast.makeText(contexts, "ProfilePic Removed", Toast.LENGTH_SHORT).show();
                        //break;

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return text.size();
    }



    public class GooglePay extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView textview;
        Context context;
        public GooglePay(@NonNull View itemView) {
            super(itemView);
            context=itemView.getContext();
            imageview=itemView.findViewById(R.id.accountimageholder);
            textview=itemView.findViewById(R.id.accounttextholder);
        }


    }
}