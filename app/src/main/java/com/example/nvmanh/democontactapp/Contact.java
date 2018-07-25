package com.example.nvmanh.democontactapp;

public class Contact {
    private String mDisplayName;
    private String mPhoneNumber;

    public Contact(String displayName, String phoneNumber) {
        this.mDisplayName = displayName;
        this.mPhoneNumber = phoneNumber;
    }

    public String getmDisplayName() {
        return mDisplayName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }
}
