package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.socialgift.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private final int ID_HOME = 1;
    private final int ID_USER = 1;
    private final int ID_BASKET = 1;
    private final int ID_MENU = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationbar);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.menu);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_searchBar, SearchFragment.class, null)
                    .commit();
        }
    }
}