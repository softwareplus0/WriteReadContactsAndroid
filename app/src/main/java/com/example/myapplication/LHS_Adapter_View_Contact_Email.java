package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class LHS_Adapter_View_Contact_Email extends BaseAdapter {

    public static Context contextOfApplication;

    private LinkedHashMap<String, Integer> mData = new LinkedHashMap<String, Integer>();
    private String[] mKeys, spinnerItems;

    public LHS_Adapter_View_Contact_Email(LinkedHashMap<String, Integer> data) {
        mData = data;
        mKeys = mData.keySet().toArray(new String[data.size()]);
        contextOfApplication = MainActivity.contextOfApplication;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        String key = mKeys[pos];
        String Value = getItem(pos).toString();

        convertView = LayoutInflater.from(contextOfApplication).inflate(R.layout.view_contact_section, parent, false);

        TextView email = convertView.findViewById(R.id.viewData);
        TextView emailType = convertView.findViewById(R.id.viewType);

        email.setText(key);
        emailType.setText(getTypeIndexEmail(Integer.parseInt(Value)));

        //do your view stuff here

        return convertView;
    }

    /**
     * Gets the Email.TYPE index given a string
     * of the type.
     *
     * @param emailType the email type to get the index
     *                  of.
     * @return the index of the TYPE of emailType.
     */

    private static String getTypeIndexEmail(int emailType) {

        HashMap<Integer, String> typeToInt = new HashMap<>();

        typeToInt.put(0, "Custom");
        typeToInt.put(1, "Home");
        typeToInt.put(2, "Work");
        typeToInt.put(3, "Other");
        typeToInt.put(4, "Mobile");


        return typeToInt.get(emailType);


    }

}