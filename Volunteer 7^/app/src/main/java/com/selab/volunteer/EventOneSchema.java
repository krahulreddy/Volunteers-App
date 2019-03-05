package com.selab.volunteer;


import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class EventOneSchema {

    public String name;
    public String date;
    public String location;
    public String description;
    public int  payment;
    public ArrayList<String> ApprovedId = new ArrayList<>();
    public ArrayList<String> UnApprovedId = new ArrayList<>();



    public EventOneSchema() {
    }

    public EventOneSchema( String name, String date, String location, String description, int payment) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.payment = payment;
    }


    public String getDate() {
        return date;
    }



}
