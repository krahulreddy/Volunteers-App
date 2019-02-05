package com.selab.volunteer;

public class EventOneSchema {
    public String name;
    public String date;
    public String location;
    public String description;
    public int  payment;

    public EventOneSchema(String name, String date, String location, String description, int payment) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
        this.payment = payment;
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
}
