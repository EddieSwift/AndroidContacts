package com.eddie.contacts;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    public static final int VIEW_MODE = 0x01;
    public static final int EDIT_MODE = 0x02;

    private Contact curr;

    private ViewGroup editWrapper, textWrapper;
    private TextView nameTxt, emailTxt, phoneTxt, addressTxt, descTxt;
    private EditText inputName, inputEmail, inputPhone, inputAddress, inputDesc;

    private MenuItem doneItem, deleteItem, editItem;

    private int pos = -1;
    private int mode;

    private StoreProvider provider;

    private ProgressBar infoProgressBar;

    private MyThread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        provider = StoreProvider.getInstance();
        super.onCreate(savedInstanceState);
        mode = getIntent().getIntExtra("MODE", EDIT_MODE);
        pos = getIntent().getIntExtra("POS", -1);

        if (pos >= 0) {

            curr = provider.get(pos);
        } else {
            curr = new Contact("", "", "", "", "");
        }

        setContentView(R.layout.activity_info);
        editWrapper = findViewById(R.id.editWrapper);
        textWrapper = findViewById(R.id.textWrapper);
        nameTxt = findViewById(R.id.nameTxt);
        emailTxt = findViewById(R.id.emailTxt);
        phoneTxt = findViewById(R.id.phoneTxt);
        addressTxt = findViewById(R.id.addressTxt);
        descTxt = findViewById(R.id.descTxt);
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhone = findViewById(R.id.inputPhone);
        inputAddress = findViewById(R.id.inputAddress);
        inputDesc = findViewById(R.id.inputDesc);

        infoProgressBar = findViewById(R.id.infoProgressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_info, menu);
        doneItem = menu.findItem(R.id.done_item);
        editItem = menu.findItem(R.id.edit_item);
        deleteItem = menu.findItem(R.id.delete_item);
        if (mode == EDIT_MODE) {
            getCurrentData();
            showEditMode();
        } else {
            setCurrentData();
            showViewMode();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.done_item) {

            if (getCurrentData()) {

                myThread = new MyThread();
                myThread.execute(3000);
            }

        } else if (item.getItemId() == R.id.edit_item) {

            setCurrentData();
            showEditMode();
            mode = EDIT_MODE;

        } else if (item.getItemId() == R.id.delete_item) {

            new AlertDialog.Builder(this)
                    .setTitle("Deleting contact")
                    .setMessage("Are you sure you want to delete this contact?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            deleteCurrent();
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .setCancelable(false)
                    .create()
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    class MyThread extends AsyncTask<Integer, Integer, Integer> {


        @Override
        protected void onPreExecute() {

            infoProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {


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

            saveCurrent();
            finish();
            infoProgressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onCancelled() {

            saveCurrent();
            finish();
            infoProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void deleteCurrent() {

        if (pos >= 0) {

            provider.remove(pos);
        }

        finish();
    }

    private void saveCurrent() {

        if (pos >= 0) {

            provider.update(pos, curr);

        } else {

            provider.add(curr);
        }
    }

    private void showEditMode() {

        editWrapper.setVisibility(View.VISIBLE);
        textWrapper.setVisibility(View.GONE);
        deleteItem.setVisible(true);
        doneItem.setVisible(true);
        editItem.setVisible(false);
    }

    private void showViewMode() {

        editWrapper.setVisibility(View.GONE);
        textWrapper.setVisibility(View.VISIBLE);
        deleteItem.setVisible(false);
        doneItem.setVisible(false);
        editItem.setVisible(true);
    }

    private void setCurrentData() {

        nameTxt.setText(curr.getName());
        emailTxt.setText(curr.getEmail());
        phoneTxt.setText(curr.getPhone());
        addressTxt.setText(curr.getAddress());
        descTxt.setText(curr.getDescription());
        inputName.setText(curr.getName());
        inputEmail.setText(curr.getEmail());
        inputPhone.setText(curr.getPhone());
        inputAddress.setText(curr.getAddress());
        inputDesc.setText(curr.getDescription());
    }

    private boolean getCurrentData() {

        boolean res = false;

        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String phone = inputPhone.getText().toString();
        String address = inputAddress.getText().toString();
        String desc = inputDesc.getText().toString();
        Contact tmp = new Contact(name, email, phone, address, desc);

        if (!valid(tmp)) {

            new AlertDialog.Builder(this)
                    .setTitle("Input contact information")
                    .setMessage("All fields must be filled!")
                    .setPositiveButton("Ok", null)
                    .setCancelable(false)
                    .create()
                    .show();
        } else {

            curr = tmp;
            res = true;
        }

        return res;
    }

    private boolean valid(Contact tmp) {

        return !tmp.getName().trim().isEmpty()
                && !tmp.getEmail().trim().isEmpty()
                && !tmp.getPhone().trim().isEmpty()
                && !tmp.getAddress().trim().isEmpty()
                && !tmp.getDescription().trim().isEmpty();
    }
}

