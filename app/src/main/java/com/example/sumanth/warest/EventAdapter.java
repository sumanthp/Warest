package com.example.sumanth.warest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sumanth on 5/10/17.
 */

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(Context context, ArrayList<Event> Events)
    {
        super(context,0,Events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Event event = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.events_layout,parent,false);
        }
        TextView eventName = (TextView)convertView.findViewById(R.id.event_name);
        TextView eventDetails = (TextView)convertView.findViewById(R.id.event_details);
        TextView eventDate = (TextView)convertView.findViewById(R.id.event_date);

        eventName.setText(event.eventName);
        eventDetails.setText(event.eventDetails);
        eventDate.setText(event.eventDate);

        return convertView;

    }
}
