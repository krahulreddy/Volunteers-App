package com.selab.volunteer;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RequestedVolunteersList extends ArrayAdapter<String> {

    private Activity context;
    private List<String> reqVolList;

    public RequestedVolunteersList(Activity context   , List<String> reqVolList) {

        super(context, R.layout.customlayout2, reqVolList);
        this.context = context;
        this.reqVolList = reqVolList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.customlayout2, null, true);

        TextView textView_userId = (TextView) listViewItem.findViewById(R.id.textView_userId);

        final String reqId = reqVolList.get(position);

        textView_userId.setText( reqId );


        return listViewItem;
    }
}
