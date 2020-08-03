package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class LHS_Adapter_View_Contact_Phone extends BaseAdapter {

    public static Context contextOfApplication;

    private LinkedHashMap<String, Integer> mData = new LinkedHashMap<String, Integer>();
    private String[] mKeys, spinnerItems;

    public LHS_Adapter_View_Contact_Phone(LinkedHashMap<String, Integer> data) {
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

        TextView phoneNumber = convertView.findViewById(R.id.viewData);
        TextView phoneNumberType = convertView.findViewById(R.id.viewType);

        phoneNumber.setText(key);
        phoneNumberType.setText(getTypeIndexPhone(Integer.parseInt(Value)));


        //do your view stuff here

        return convertView;
    }

    /**
     * Gets the Phone.TYPE index given a string
     * of the type.
     * @param phoneType the phone type to get the index
     *                  of.
     * @return the index of the TYPE of phoneType.
     */

    private static String getTypeIndexPhone(int phoneType)
    {

        HashMap<Integer, String> typeToInt = new HashMap<>();

        typeToInt.put(1, "Home");
        typeToInt.put(2, "Mobile");
        typeToInt.put(3, "Work");
        typeToInt.put(4, "Work Fax");
        typeToInt.put(5, "Home Fax");
        typeToInt.put(6, "Pager");
        typeToInt.put(7, "Other");
        typeToInt.put(8, "Callback");
        typeToInt.put(9, "Car");
        typeToInt.put(10, "Company Main");
        typeToInt.put(11, "ISDN");
        typeToInt.put(12, "Main");
        typeToInt.put(13, "Other Fax");
        typeToInt.put(14, "Radio");
        typeToInt.put(15, "Telex");
        typeToInt.put(16, "TTY_TDD");
        typeToInt.put(17, "Work Mobile");
        typeToInt.put(18, "Work Pager");
        typeToInt.put(19, "Assistant");
        typeToInt.put(20, "MMS");

        return typeToInt.get(phoneType);



    }

    /**
     * Gets the Email.TYPE index given a string
     * of the type.
     *
     * @param emailType the email type to get the index
     *                  of.
     * @return the index of the TYPE of emailType.
     */

    private static int getTypeIndexEmail(String emailType) {

        HashMap<String, Integer> typeToInt = new HashMap<>();

        typeToInt.put("Custom", 0);
        typeToInt.put("Home", 1);
        typeToInt.put("Work", 2);
        typeToInt.put("Other", 3);
        typeToInt.put("Mobile", 4);


        return typeToInt.get(emailType);


    }

}