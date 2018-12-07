package com.eddie.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView contactList;
    private ContactListAdapter adapter;
    private TextView emptyTxt;
    private StoreProvider provider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        provider = StoreProvider.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        contactList = findViewById(R.id.contactList);
        emptyTxt = findViewById(R.id.emptyTxt);
        contactList.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Contact> list = provider.getList();
        if(list.isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            contactList.setVisibility(View.GONE);
        }else{
            emptyTxt.setVisibility(View.GONE);
            contactList.setVisibility(View.VISIBLE);
            adapter = new ContactListAdapter(list);
            contactList.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,InfoActivity.class);
        intent.putExtra("MODE",InfoActivity.VIEW_MODE);
        intent.putExtra("POS",position);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_item){
            addNewContact();
        }else if(item.getItemId() == R.id.logout_item){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        setResult(RESULT_OK);
        finish();
    }

    private void addNewContact() {
        Intent intent = new Intent(this,InfoActivity.class);
        intent.putExtra("MODE",InfoActivity.EDIT_MODE);
        startActivity(intent);
    }
}

