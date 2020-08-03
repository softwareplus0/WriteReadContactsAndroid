package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<NewContact> {

    public ContactAdapter(Context context, ArrayList<NewContact> arrayAdapter)
    {
        super(context, 0, arrayAdapter);

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        NewContact contact = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);

        }

        // Lookup view for data population

        TextView firstNameTextView = (TextView) convertView.findViewById(R.id.tvName);
        TextView lastNameTextView = (TextView) convertView.findViewById(R.id.tvHome);
        TextView iconText = convertView.findViewById(R.id.icon_text);

        // Populate the data into the template view using the data object

        firstNameTextView.setText(contact.getFirstName());
        firstNameTextView.setTextColor(Color.BLACK);

        lastNameTextView.setText(" " + contact.getLastName());
        lastNameTextView.setTextColor(Color.BLACK);

        iconText.setText(contact.getFirstName().substring(0, 1).toUpperCase());
        iconText.setAlpha(0.8f);

        // Return the completed view to render on screen

        return convertView;

    }

}
