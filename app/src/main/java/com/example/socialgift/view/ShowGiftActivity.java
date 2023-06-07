package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }
}