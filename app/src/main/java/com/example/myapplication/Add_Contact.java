/**
 * Cancel button returns to MainActivity.
 * Save button does not work yet.
 *
 * When a contact is saved, the MainActivity is reloaded and all
 * contacts currently on the phone are loaded again. This can
 * be optimized by only adding the individual contact instead of
 * loading the whole list of contacts again.
 *
 */

package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Add_Contact extends AppCompatActivity {

    public static Context contextOfApplication;
    public static LinkedHashMap<String, Integer> emailsAndType = new LinkedHashMap<>();
    public static LinkedHashMap<String, Integer> phoneNumbersAndType = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__contact);

        contextOfApplication = getApplicationContext();






        emailsAndType.put("TEST", 0);

        String[] emailSpinnerItems = new String[]{"Home", "Work", "Other", "Mobile", "Custom"};
        LHS_Adapter_Add_Contact email_adapter = new LHS_Adapter_Add_Contact(emailsAndType, emailSpinnerItems);
        ListView emailListView = findViewById(R.id.emailInputList);
        emailListView.setAdapter(email_adapter);

        setListViewHeightBasedOnChildren(emailListView);




        phoneNumbersAndType.put("TEST", 0);

        String[] phoneSpinnerItems = new String[]{"Mobile", "Home", "Work", "Work Fax", "Home Fax",
                "Pager", "Other", "Custom", "Callback", "Car", "Company Main", "ISDN", "Main", "Other Fax",
                "Radio", "Telex", "TTY TDD", "Work Mobile", "Work Pager", "Assistant", "MMS"};
        LHS_Adapter_Add_Contact phone_adapter = new LHS_Adapter_Add_Contact(phoneNumbersAndType, phoneSpinnerItems);
        ListView phoneListView = findViewById(R.id.phoneInputList);
        phoneListView.setAdapter(phone_adapter);

        setListViewHeightBasedOnChildren(phoneListView);

        LinearLayout linearLayout = (LinearLayout) getViewByPosition(0, phoneListView);
        EditText phoneNumberTextView = linearLayout.findViewById(R.id.phoneNumberInput);

        Log.d("TEST 1: ", phoneNumberTextView.getText().toString());







        Button cancelButton = findViewById(R.id.cancelButton1);
        Button saveButton = findViewById(R.id.saveButton1);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent myIntent = new Intent(contextOfApplication, MainActivity.class);
                startActivity(myIntent);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinkedHashMap<String, Integer> phoneToPhoneType = new LinkedHashMap<>();
                LinkedHashMap<String, Integer> emailToEmailType = new LinkedHashMap<>();

                EditText firstName = findViewById(R.id.editText1);
                EditText lastName = findViewById(R.id.editText2);


                String firstNameString = firstName.getText().toString();
                String lastNameString = lastName.getText().toString();



                NewContact contactToSave = new NewContact((int) (Math.random() * 100 + 1),firstNameString + " " + lastNameString, phoneToPhoneType, emailToEmailType);


                Save_Contact save_contact = new Save_Contact(contactToSave);
                save_contact.saveToPhone();

                Intent myIntent = new Intent(contextOfApplication, MainActivity.class);
                startActivity(myIntent);

            }



        });



    }



    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    /**
     * Gets the Phone.TYPE index given a string
     * of the type.
     * @param phoneType the phone type to get the index
     *                  of.
     * @return the index of the TYPE of phoneType.
     */

    private static int getTypeIndexPhone(String phoneType)
    {

        HashMap<String, Integer> typeToInt = new HashMap<>();

        typeToInt.put("Home", 1);
        typeToInt.put("Mobile", 2);
        typeToInt.put("Work", 3);
        typeToInt.put("Work Fax", 4);
        typeToInt.put("Home Fax", 5);
        typeToInt.put("Pager", 6);
        typeToInt.put("Other", 7);
        typeToInt.put("Callback", 8);
        typeToInt.put("Car", 9);
        typeToInt.put("Company Main", 10);
        typeToInt.put("ISDN", 11);
        typeToInt.put("Main", 12);
        typeToInt.put("Other Fax", 13);
        typeToInt.put("Radio", 14);
        typeToInt.put("Telex", 15);
        typeToInt.put("TTY_TDD", 16);
        typeToInt.put("Work Mobile", 17);
        typeToInt.put("Work Pager", 18);
        typeToInt.put("Assistant", 19);
        typeToInt.put("MMS", 20);

        return typeToInt.get(phoneType);



    }

    /**
     * Gets the Email.TYPE index given a string
     * of the type.
     * @param emailType the email type to get the index
     *                  of.
     * @return the index of the TYPE of emailType.
     */

    private static int getTypeIndexEmail(String emailType)
    {

        HashMap<String, Integer> typeToInt = new HashMap<>();

        typeToInt.put("Custom", 0);
        typeToInt.put("Home", 1);
        typeToInt.put("Work", 2);
        typeToInt.put("Other", 3);
        typeToInt.put("Mobile", 4);


        return typeToInt.get(emailType);



    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += mView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
