package com.selab.volunteer;

import com.google.firebase.database.DatabaseReference;

public class EventOneSchema {
    public String name;
    public String date;
    public String location;
    public String description;
    public int  payment;
    public long maxid;

    DatabaseReference databaseEvents;

    public EventOneSchema(String name, String date, String location, String description, int payment) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.payment = payment;
    }

    public EventOneSchema(long maxid) {
        this.maxid = maxid;
    }

    public EventOneSchema() {
    }

    public String getEventName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public String getEventLocation(){
        return location;
    }

    public int getPayment() {
        return payment;
    }

    public long getMaxid() {
        return maxid;
    }
}
