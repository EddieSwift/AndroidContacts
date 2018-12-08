package com.eddie.contacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int LIST_ACTIVITY = 0x01;
    private StoreProvider storeProvider;

    private EditText inputEmail, inputPassword;
    private Button loginBtn, registrationBtn;

    private ProgressBar loginProgress;

    private MyThread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        storeProvider = StoreProvider.getInstance();

        super.onCreate(savedInstanceState);

        if (storeProvider.getToken() != null) {

            showList();
        }

        setContentView(R.layout.activity_login);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        loginProgress = findViewById(R.id.loginProgressBar);
        registrationBtn = findViewById(R.id.registration_btn);
        registrationBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.loginBtn) {

            myThread = new MyThread();
            myThread.execute(3000);

        }

    }

    class MyThread extends AsyncTask<Integer, Integer, Integer> {


        @Override
        protected void onPreExecute() {

            loginProgress.setVisibility(View.VISIBLE);
            loginBtn.setEnabled(false);
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
            showList();
            loginProgress.setVisibility(View.INVISIBLE);
            loginBtn.setEnabled(true);
        }

        @Override
        protected void onCancelled() {

            saveCurrent();
            showList();
            loginProgress.setVisibility(View.INVISIBLE);
            loginBtn.setEnabled(true);
        }
    }

    private void showList() {

        Intent intent = new Intent(this, ListActivity.class);
        startActivityForResult(intent, LIST_ACTIVITY);

    }

    private void saveCurrent() {

        String token = inputEmail.getText() + "&" + inputPassword.getText();
        storeProvider.saveToken(token);
    }



    private void clearLogin() {

        storeProvider.clearToken();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LIST_ACTIVITY) {

            if (resultCode == RESULT_OK) {

                clearLogin();

            } else {

                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

