package com.example.myapplication;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class view_contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        NewContact selectedContact = MainActivity.selectedContact;

        LHS_Adapter_View_Contact_Phone viewContactAdapter = new LHS_Adapter_View_Contact_Phone(selectedContact.getPhoneToPhoneType());
        ListView phoneListView = findViewById(R.id.viewphoneInputList);
        phoneListView.setAdapter(viewContactAdapter);

        setListViewHeightBasedOnChildren(phoneListView);

        LHS_Adapter_View_Contact_Email viewContactAdapterEmail = new LHS_Adapter_View_Contact_Email(selectedContact.getEmailToEmailType());
        ListView emailListView = findViewById(R.id.viewemailInputList);
        emailListView.setAdapter(viewContactAdapterEmail);

        setListViewHeightBasedOnChildren(emailListView);


        ImageView contactIcon = findViewById(R.id.contactImageView);
        contactIcon.setImageBitmap(openPhoto(selectedContact.getID()));

        ScrollView scrollView = findViewById(R.id.viewContactScrollView);


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

    private Bitmap openPhoto(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getContentResolver().query(photoUri,
                new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
            }
        } finally {
            cursor.close();
        }
        return null;

    }

}
