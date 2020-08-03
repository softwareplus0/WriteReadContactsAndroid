/**
 * Main activity. This reads all contacts currently stored on the phone
 * and displays them in a list.
 */

package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class MainActivity extends AppCompatActivity {

    public static Context contextOfApplication;
    public static NewContact selectedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();

        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission. WRITE_CONTACTS}, 100);


        ContactsList contactsList = new ContactsList();
        ArrayList<NewContact> listOfContacts = contactsList.getContacts();
        ContactAdapter contactAdapter = new ContactAdapter(this, listOfContacts);
        final ListView listView = (ListView) findViewById(R.id.test5);
        listView.setAdapter(contactAdapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedContact = (NewContact) listView.getItemAtPosition(position);

                Intent myIntent = new Intent(contextOfApplication, view_contact.class);
                startActivity(myIntent);

            }
        });



        Button addContact = findViewById(R.id.addContactButton);

        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(contextOfApplication, Add_Contact.class);
                startActivity(myIntent);



            }
        });

    }

    protected void onRestart() {

        super.onRestart();
        finish();
        startActivity(getIntent());

    }




}





