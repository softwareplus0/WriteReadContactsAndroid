package com.example.myapplication;

import java.util.LinkedHashMap;

public class NewContact {

    private long ID = 0;
    private String name = "";
    private LinkedHashMap<String, Integer> phoneToPhoneType = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> emailToEmailType = new LinkedHashMap<>();


    public NewContact(long ID, String name, LinkedHashMap<String, Integer> phoneToPhoneType, LinkedHashMap<String, Integer> emailToEmailType)
    {

        this.ID = ID;
        this.name = name;
        this.phoneToPhoneType = phoneToPhoneType;
        this.emailToEmailType = emailToEmailType;

    }

    public long getID()
    {
        return ID;
    }

    public String getFirstName()
    {

        return name.split(" ")[0];     //must be made to account for the actual first name.

    }

    public String getLastName()
    {

        return name.split(" ")[0];     //must be made to account for the actual last name.

    }

    public LinkedHashMap<String, Integer> getPhoneToPhoneType()
    {
        return phoneToPhoneType;
    }

    public LinkedHashMap<String, Integer> getEmailToEmailType()
    {
        return emailToEmailType;
    }



}
