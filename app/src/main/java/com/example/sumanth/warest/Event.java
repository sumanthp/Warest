package com.example.sumanth.warest;

/**
 * Created by sumanth on 5/10/17.
 */

public class Event {
    public String eventName;
    public String eventDetails;
    public String eventDate;

    public Event(String eventName,String eventDetails,String eventDate)
    {
        this.eventDate = eventDate;
        this.eventName = eventName;
        this.eventDetails = eventDetails;
    }

}
