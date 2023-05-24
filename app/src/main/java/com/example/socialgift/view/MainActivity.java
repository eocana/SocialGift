package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.socialgift.R;
import com.example.socialgift.model.User;
import com.example.socialgift.model.Wishlist;

public class MainActivity extends AppCompatActivity {

    private Button user;
    private Button wishlist;
    private Button other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.btnUser);
        wishlist = findViewById(R.id.btnWishlist);
        other = findViewById(R.id.btnSocial);

    }

    public void goToUserActivity(User user) {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void goToWishlistActivity(Wishlist wishlist) {
        Intent intent = new Intent(this, WishlistActivity.class);
        intent.putExtra("wishlist", wishlist);
        startActivity(intent);
    }
}