package com.eddie.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView contactList;
    private ContactListAdapter adapter;
    private TextView emptyTxt;
    private StoreProvider provider;

    private ProgressBar listProgressBar;

    private MyThread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        provider = StoreProvider.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        contactList = findViewById(R.id.contactList);
        emptyTxt = findViewById(R.id.emptyTxt);
        contactList.setOnItemClickListener(this);

        listProgressBar = findViewById(R.id.listProgressBar);

    }

    @Override
    protected void onStart() {

        super.onStart();

        contactList.setVisibility(View.INVISIBLE);

        myThread = new MyThread();
        myThread.execute(3000);

    }

    class MyThread extends AsyncTask<Integer, Integer, Integer> {


        @Override
        protected void onPreExecute() {

            listProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //super.onProgressUpdate(values);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {

            int sleepTime = integers[0];

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {



            listProgressBar.setVisibility(View.INVISIBLE);

            List<Contact> list = provider.getList();

            if (list.isEmpty()) {

                emptyTxt.setVisibility(View.VISIBLE);
                contactList.setVisibility(View.GONE);

            } else {

                emptyTxt.setVisibility(View.GONE);
                contactList.setVisibility(View.VISIBLE);
                adapter = new ContactListAdapter(list);
                contactList.setAdapter(adapter);
            }
        }

        @Override
        protected void onCancelled() {

            listProgressBar.setVisibility(View.INVISIBLE);

            List<Contact> list = provider.getList();

            if (list.isEmpty()) {

                emptyTxt.setVisibility(View.VISIBLE);
                contactList.setVisibility(View.GONE);

            } else {

                emptyTxt.setVisibility(View.GONE);
                contactList.setVisibility(View.VISIBLE);
                adapter = new ContactListAdapter(list);
                contactList.setAdapter(adapter);
            };
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("MODE", InfoActivity.VIEW_MODE);
        intent.putExtra("POS", position);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_item) {

            addNewContact();

        } else if (item.getItemId() == R.id.logout_item) {

            new AlertDialog.Builder(this)
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to log out from your account?")
                    .setCancelable(false)
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            logout();
                        }
                    })
                    .create()
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        setResult(RESULT_OK);
        finish();
    }

    private void addNewContact() {

        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("MODE", InfoActivity.EDIT_MODE);
        startActivity(intent);
    }
}

