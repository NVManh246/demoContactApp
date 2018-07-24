package com.example.nvmanh.democontactapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private static final String TEL = "tel:";
    private Context mContext;
    private List<Contact> mContacts;

    public ContactAdapter(Context context, List<Contact> contacts){
        this.mContext = context;
        this.mContacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_contact, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.mTextContact.setText(String.valueOf(contact.getmDisplayName().charAt(0)).toUpperCase());
        holder.mTextDisplayName.setText(contact.getmDisplayName());
        holder.mTextPhoneNumber.setText(contact.getmPhoneNumber());
        callPhone(holder.mImageCallPhone, contact.getmPhoneNumber());
    }

    @Override
    public int getItemCount() {
        if(mContacts != null){
            return mContacts.size();
        }
        return 0;
    }

    private void callPhone(ImageView imageCallPhone, final String phoneNumber){
        imageCallPhone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent intentCallPhone = new Intent(Intent.ACTION_CALL);
                intentCallPhone.setData(Uri.parse(TEL + phoneNumber));
                mContext.startActivity(intentCallPhone);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextContact;
        private TextView mTextDisplayName;
        private TextView mTextPhoneNumber;
        private ImageView mImageCallPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextContact = itemView.findViewById(R.id.text_contact);
            mTextDisplayName = itemView.findViewById(R.id.text_display_name);
            mTextPhoneNumber = itemView.findViewById(R.id.text_phone_number);
            mImageCallPhone = itemView.findViewById(R.id.image_call);
        }
    }
}
