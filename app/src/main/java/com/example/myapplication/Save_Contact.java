package com.example.myapplication;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Save_Contact {

    private String firstName = "";
    private String lastName = "";

    private LinkedHashMap<String, Integer> phoneToPhoneType;
    private LinkedHashMap<String, Integer> emailToEmailType;

    private String fullName = "";

    private static Context contextOfApplication;


    public Save_Contact(NewContact contact) {

        contextOfApplication = Add_Contact.contextOfApplication;

        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.phoneToPhoneType = contact.getPhoneToPhoneType();
        this.emailToEmailType = contact.getEmailToEmailType();

        fullName = contact.getFirstName() + " " + contact.getLastName();

    }

    public void saveToPhone() {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());


        if (fullName != null) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            fullName).build());
        }

        //------------------------------------------------------ Mobile Number

        for (Map.Entry<String, Integer> entry : phoneToPhoneType.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();


            if (key != null) {
                ops.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, key)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                value).build()); //spinner selection does not line up with actual TYPE.
            }

        }

        //------------------------------------------------------ Email

        for (Map.Entry<String, Integer> entry : emailToEmailType.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();


            if (key != null) {
                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Email.DATA, key)
                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, value)
                        .build());
            }

        }

        try {
            contextOfApplication.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(contextOfApplication, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


}
