package com.example.socialgift.view;

import android.app.Activity;
import android.os.Bundle;

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

        // Agrega el fragment al layout activity_show_my_user_container
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_show_my_user_container, showMyUserFragment)
                .commit();

        // Crea una instancia del controlador MyUserController
        myUserController = new MyUserController(showMyUserFragment);
    }

    // Método que se llama cuando se presiona el botón "Cerrar Sesión"
    public void signOutButtonClicked(View view) {
        myUserController.signOut();
    }

    // Método que se llama cuando se presiona el botón "Editar"
    public void editButtonClicked(View view) {
        // Crea una instancia del fragment EditUserFragment
        EditUserFragment editUserFragment = new EditUserFragment();

        // Reemplaza el fragment actual con el fragment de edición
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_show_my_user_container, editUserFragment)
                .addToBackStack(null)
                .commit();

        // Crea una nueva instancia del controlador MyUserController con el fragment de edición
        myUserController = new MyUserController(editUserFragment);
    }
}
