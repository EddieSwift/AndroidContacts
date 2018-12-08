package com.eddie.contacts;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class StoreProvider {

    private static StoreProvider instance;

    private static final String SP_AUTH = "AUTH";
    private static final String AUTH_CURRENT = "CURR";
    private static final String SP_DATA = "CONTACTS";
    private Context context;

//    public StoreProvider(Context context) {
//        this.context = context;
//    }

    private StoreProvider() {

    }

    public void setContext(Context context) {

        this.context = context;
    }

    public static StoreProvider getInstance() {

        if (instance == null) {

            instance = new StoreProvider();
        }

        return instance;
    }

    public void saveToken(String token) {

        context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .edit()
                .putString(AUTH_CURRENT, token)
                .apply();
    }

    public String getToken() {

        return context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .getString(AUTH_CURRENT, null);
    }


    public void clearToken() {

        context.getSharedPreferences(SP_AUTH, Context.MODE_PRIVATE)
                .edit()
                .remove(AUTH_CURRENT)
                .apply();
    }

    public List<Contact> getList() {

        ArrayList<Contact> list = new ArrayList<>();
        String token = getToken();
        String data = context.getSharedPreferences(SP_DATA, Context.MODE_PRIVATE)
                .getString(token, null);

        if (data != null) {

            String[] contacts = data.split(";");

            for (String c : contacts) {

                list.add(Contact.newInstance(c));
            }
        }
        return list;
    }

    public void add(Contact contact) {

        List<Contact> list = getList();
        list.add(contact);
        saveContacts(list);
    }

    public void update(int pos, Contact contact) {

        List<Contact> list = getList();
        list.set(pos, contact);
        saveContacts(list);
    }

    public void remove(int pos) {

        List<Contact> list = getList();
        list.remove(pos);
        saveContacts(list);
    }

    public Contact get(int pos) {

        List<Contact> list = getList();

        return list.get(pos);
    }

    private void saveContacts(List<Contact> list) {

        StringBuilder str = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {

            str.append(list.get(i).toString());

            if (i < list.size() - 1) {

                str.append(";");
            }
        }

        context.getSharedPreferences(SP_DATA, Context.MODE_PRIVATE)
                .edit()
                .putString(getToken(), str.toString())
                .apply();
    }

}

