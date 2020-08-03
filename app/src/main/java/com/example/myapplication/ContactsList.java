package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static android.content.ContentValues.TAG;

public class ContactsList {


    private static Context contextOfApplication = MainActivity.contextOfApplication;
    ArrayList<NewContact> newContactList = new ArrayList<>();

    public ContactsList() {


        String id = "";
        String contactName = "";
        int phoneType = 0;
        String phoneNo = "";

        int emailType = 0;
        String email = "";


        Cursor cursor = contextOfApplication.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        Integer contactsCount = cursor.getCount();
        if (contactsCount > 0) {
            while (cursor.moveToNext()) {

                LinkedHashMap<String, Integer> phoneToPhoneType = new LinkedHashMap<>();
                LinkedHashMap<String, Integer> emailToEmailType = new LinkedHashMap<>();

                id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    Cursor pCursor = contextOfApplication.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    if (pCursor != null) {
                        while (pCursor.moveToNext()) {

                            phoneType = pCursor.getInt(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                            phoneNo = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                            phoneToPhoneType.put(phoneNo, phoneType);

                        }
                        pCursor.close();
                    }


                    Cursor cur1 = contextOfApplication.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (cur1.moveToNext()) {


                        emailType = cur1.getInt(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                        if (email != null) {
                            emailToEmailType.put(email, emailType);
                        }

                    }
                    cur1.close();


                    NewContact newContact = new NewContact(getContactIDFromNumber(phoneNo, contextOfApplication), contactName, phoneToPhoneType, emailToEmailType);
                    newContactList.add(newContact);

                }
            }
            cursor.close();
        }


    }

    public ArrayList<NewContact> getContacts() {
        return newContactList;
    }


    private static long getContactIDFromNumber(String contactNumber, Context context) {
        String UriContactNumber = Uri.encode(contactNumber);
        long phoneContactID = new Random().nextInt();
        Cursor contactLookupCursor = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, UriContactNumber),
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID}, null, null, null);
        while (contactLookupCursor.moveToNext()) {
            phoneContactID = contactLookupCursor.getLong(contactLookupCursor.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
        }
        contactLookupCursor.close();

        return phoneContactID;
    }

    /**
     * Gets the Phone.TYPE index given a string
     * of the type.
     *
     * @param phoneType the phone type to get the index
     *                  of.
     * @return the index of the TYPE of phoneType.
     */

    private static int getTypeIndexPhone(String phoneType) {

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
