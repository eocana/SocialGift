package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.socialgift.R;
import com.example.socialgift.controller.UsersController;

public class ShowWishlistActivity extends AppCompatActivity {
    private UsersController usersController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_wishlist);

        usersController = new UsersController(this, this);

        usersController.wishlistsUser(1);
    }
}