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

import java.util.ArrayList;

public class Profilesupportadapter extends RecyclerView.Adapter<Profilesupportadapter.GooglePay> {

    public Profilesupportadapter(ArrayList<String> text, ArrayList<Integer> image, Context contexts) {
        this.text = text;
        this.image = image;
        this.contexts = contexts;
    }

    private ArrayList<String> text=new ArrayList<>();
    private ArrayList<Integer> image=new ArrayList<>();
    private Context contexts;



    @NonNull
    @Override
    public Profilesupportadapter.GooglePay onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)  {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.accountview,viewGroup,false);
        return new Profilesupportadapter.GooglePay(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Profilesupportadapter.GooglePay googlePay, final int i) {
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
                        intent[0] =new Intent(contexts, feedbacktab.class);
                        contexts.startActivity(intent[0]);
                        break;
                    case 1:
                        intent[0] =new Intent(contexts, Report.class);
                        contexts.startActivity(intent[0]);
                        break;


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
