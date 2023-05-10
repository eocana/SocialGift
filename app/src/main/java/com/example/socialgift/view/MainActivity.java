package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.socialgift.R;
import com.example.socialgift.model.User;
import com.example.socialgift.model.UserSession;

public class MainActivity extends AppCompatActivity {

    private Button editUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUserButton = findViewById(R.id.editUserButton);
        UserSession userSession = new UserSession((User) getIntent().getSerializableExtra("session"));

        System.out.println("MY USER IS: "+userSession.getEmail());

        editUserButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ShowMyUserActivity.class);
            startActivity(intent);
        });



    }
}