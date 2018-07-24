package com.example.nvmanh.democontactapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 124;
    private RecyclerView mRecyclerContact;
    private List<Contact> mContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
        initView();
        setupView();
    }

    private void initView(){
        mRecyclerContact = findViewById(R.id.recycler_contact);
    }

    private void setupView() {
        if (mContacts != null) {
            ContactAdapter contactAdapter = new ContactAdapter(this, mContacts);
            mRecyclerContact.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerContact.setAdapter(contactAdapter);
            contactAdapter.notifyDataSetChanged();
        }
    }
    private List<Contact> fetchContact(){
        List<Contact> contacts = new ArrayList<>();
        Uri uri1 = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getContentResolver().query(uri1, projection,
                null, null, null);

        while (cursor.moveToNext()){
            String name = cursor.getString(0);
            String phoneNumber = cursor.getString(1);
            Contact contact = new Contact(name, phoneNumber);
            contacts.add(contact);
        }
        cursor.close();
        return contacts;
    }

    private void initPermission(){
        String[] permissions = {Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE};
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(permissions, REQUEST_CODE_PERMISSION);
            } else {
                mContacts = fetchContact();
            }
        } else {
            mContacts = fetchContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_PERMISSION){
            for(int i = permissions.length - 1; i >= 0; i--){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    finish();
                    return;
                }
            }
            mContacts = fetchContact();
            setupView();
        }
    }
}
