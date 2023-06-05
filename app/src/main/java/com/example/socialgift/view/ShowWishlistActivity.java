package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.socialgift.R;
import com.example.socialgift.controller.UsersController;
import com.example.socialgift.model.Wishlist;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowWishlistActivity extends AppCompatActivity {
    public static ArrayList<Wishlist> listWishlist = new ArrayList<>();
    public static ArrayAdapter<String> adapter;
    public static ArrayList<String> values;
    private UsersController usersController;
    public static ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_wishlist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ShowWishlistFragment showWishlistFragment = new ShowWishlistFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.show_wishlist_fragment, showWishlistFragment)
                .commit();


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