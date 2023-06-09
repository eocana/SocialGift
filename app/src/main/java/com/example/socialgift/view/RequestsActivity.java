package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.socialgift.R;
import com.example.socialgift.controller.FriendsController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestsActivity extends AppCompatActivity {

    ListView listView;
    public ArrayList<String> list = new ArrayList<>();
    private FriendsController friendsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listView = (ListView) findViewById(R.id.listview);


        friendsController = new FriendsController(this, this);
        friendsController.getFriendRequests();

        listView.setAdapter(new RequestsAdapter( list, this) );
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }

}
