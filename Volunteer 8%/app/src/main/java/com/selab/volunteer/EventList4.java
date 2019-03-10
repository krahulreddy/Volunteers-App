package com.selab.volunteer;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventList4 extends ArrayAdapter<EventOneSchema> {

    private Activity context;
    private List<EventOneSchema> eventList;

    public EventList4(Activity context   , List<EventOneSchema> eventList) {

        super(context, R.layout.customlayout, eventList);
        this.context = context;
        this.eventList = eventList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.customlayout, null, true);

        TextView textView_eventName = (TextView) listViewItem.findViewById(R.id.textView_eventName);
        TextView textView_eventDate = (TextView) listViewItem.findViewById(R.id.textView_eventDate);
        TextView textView_eventLocation = (TextView) listViewItem.findViewById(R.id.textView_eventLocation);

        final EventOneSchema eventOneSchema = eventList.get(position);

        textView_eventName.setText(eventOneSchema.name);
        textView_eventDate.setText(eventOneSchema.date);
        textView_eventLocation.setText(eventOneSchema.location);

        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDescription4.class);
                intent.putExtra("EventId", VolunteerEvents.eventMap.get(eventOneSchema) );

                context.startActivity(intent);
            }
        });

        return listViewItem;
    }
}
