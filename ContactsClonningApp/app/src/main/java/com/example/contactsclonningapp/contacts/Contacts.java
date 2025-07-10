package com.example.contactsclonningapp.contacts;

public class Contacts {
    int contactImage;
    String contactName;
    String contactNumbar;


    public Contacts(int contactImage, String contactName, String contactNumbar) {
        this.contactImage = contactImage;
        this.contactName = contactName;
        this.contactNumbar = contactNumbar;
    }

    public int getContactImage() {
        return contactImage;
    }

    public void setContactImage(int contactImage) {
        this.contactImage = contactImage;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumbar() {
        return contactNumbar;
    }

    public void setContactNumbar(String contactNumbar) {
        this.contactNumbar = contactNumbar;
    }
}
