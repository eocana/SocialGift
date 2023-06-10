package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.socialgift.R;
import com.example.socialgift.controller.FriendsController;
import com.example.socialgift.model.User;
import com.google.android.material.internal.ContextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestsActivity extends AppCompatActivity {

    public static ListView listView;
    public static ArrayList<String> list = new ArrayList<>();
    public static ArrayList<User> lstUsers = new ArrayList<>();
    private static FriendsController friendsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listView = (ListView) findViewById(R.id.listview);


        friendsController = new FriendsController(this, this);
        friendsController.getFriendRequests();


    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }
    public static void acceptRequest(String emailUser){
        for (User u : lstUsers) {
            if (u.getEmail() == emailUser){
                friendsController.acceptFriendRequest(u.getId(), emailUser);
            }
        }
    }
    public static void rejectRequest(String emailUser){
        for (User u : lstUsers) {
            if (u.getEmail() == emailUser){
                friendsController.rejectFriendRequest(u.getId(), emailUser);
            }
        }
    }

}
