package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.socialgift.R;

public class ShowGiftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_gift);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ShowGiftFragment showGiftFragment = new ShowGiftFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.show_gift_fragment, showGiftFragment)
                .commit();
    }
}