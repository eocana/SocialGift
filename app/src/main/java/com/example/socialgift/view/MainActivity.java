package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

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



    }
}