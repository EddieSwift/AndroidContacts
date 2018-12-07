package com.eddie.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactListAdapter extends BaseAdapter {
    private List<Contact> contacts;

    public ContactListAdapter(List<Contact> list) {
        contacts = list;
    }


    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_contact,parent,false);
        }
        Contact curr = contacts.get(position);
        TextView nameTxt = convertView.findViewById(R.id.nameTxt);
        TextView emailTxt = convertView.findViewById(R.id.emailTxt);
        nameTxt.setText(curr.getName());
        emailTxt.setText(curr.getEmail());
        return convertView;
    }
}
