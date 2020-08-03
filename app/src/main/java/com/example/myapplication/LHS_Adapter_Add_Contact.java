package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LHS_Adapter_Add_Contact extends BaseAdapter {

    public static Context contextOfApplication;

    private LinkedHashMap<String, Integer> mData = new LinkedHashMap<String, Integer>();
    private String[] mKeys, spinnerItems;

    public LHS_Adapter_Add_Contact(LinkedHashMap<String, Integer> data, String[] spinnerItems) {


        mData = data;
        mKeys = mData.keySet().toArray(new String[data.size()]);
        contextOfApplication = MainActivity.contextOfApplication;
        this.spinnerItems = spinnerItems;
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
    public View getView(final int pos, View convertView, ViewGroup parent) {

        String key = mKeys[pos];
        String Value = getItem(pos).toString();

        convertView = LayoutInflater.from(contextOfApplication).inflate(R.layout.phone_input, parent, false);

        TextView phoneNumberInput = convertView.findViewById(R.id.phoneNumberInput);

        Spinner spinner = convertView.findViewById(R.id.phoneTypeSpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(contextOfApplication, android.R.layout.simple_spinner_dropdown_item, Arrays.asList(spinnerItems));
        spinner.setAdapter(adapter2);
        spinner.setSelection(Integer.parseInt(Value));

        phoneNumberInput.setText(key);

        phoneNumberInput.setFocusable(true);

        phoneNumberInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                EditText view = (EditText) v;

                Log.d("TEST 2: ", "TEST 2");

                LinkedHashMap<String, Integer> tempHashMap = testCopy(mData);
                tempHashMap.put("jkghkjlg", 0);

                mData.clear();
                mData.putAll(tempHashMap);

//                insertLHS(mData, pos + 1, "fdsfgfdg", 0);
                notifyDataSetChanged();


            }
        });


        //do your view stuff here

        return convertView;
    }

    public static LinkedHashMap<String, Integer> testCopy(LinkedHashMap<String, Integer> lhsToCopy)
    {

        LinkedHashMap<String, Integer> newLHS = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : lhsToCopy.entrySet()) {
            String key = new String(entry.getKey());
            int value = entry.getValue();

            newLHS.put(key, value);


        }

        return newLHS;

    }

    public static <K, V> Map<K, V> deepCopyLHS(LinkedHashMap<K, V> lhsToCopy) {

        Map<K, V> original = lhsToCopy;
        Map<K, V> clone = new HashMap<K, V>();

        for (Map.Entry<K, V> entry : original.entrySet())
        {
            clone.put(entry.getKey(), entry.getValue());
        }

        return clone;

    }

    public static <K, V> void insertLHS(LinkedHashMap<K, V> map, int index, K key, V value) {
        assert (map != null);
        assert !map.containsKey(key);
        assert (index >= 0) && (index < map.size());

        int i = 0;
        List<Map.Entry<K, V>> rest = new ArrayList<Map.Entry<K, V>>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (i++ >= index) {
                rest.add(entry);
            }
        }
        map.put(key, value);
        for (int j = 0; j < rest.size(); j++) {
            Map.Entry<K, V> entry = rest.get(j);
            map.remove(entry.getKey());
            map.put(entry.getKey(), entry.getValue());
        }
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