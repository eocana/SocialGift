package com.example.socialgift.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socialgift.R;
import com.example.socialgift.controller.MyUserController;


public class ShowMyUserActivity extends AppCompatActivity {

    private MyUserController myUserController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_user);

        // Crea una instancia del fragment ShowMyUserFragment
        ShowMyUserFragment showMyUserFragment = new ShowMyUserFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.show_my_user_fragment, showMyUserFragment)
                .commit();
    }

    // Método que se llama cuando se presiona el botón "Cerrar Sesión"
    public void signOutButtonClicked(View view) {
        myUserController.signOut();
    }

    // Método que se llama cuando se presiona el botón "Editar"
    public void editButtonClicked(View view) {
        Intent intent = new Intent(this, EditUserActivity.class);
        startActivity(intent);
    }
}
