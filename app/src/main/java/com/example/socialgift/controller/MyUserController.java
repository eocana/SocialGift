package com.example.socialgift.controller;

import android.util.Log;

import com.example.socialgift.DataManagerDB;
import com.example.socialgift.model.User;
import com.example.socialgift.view.EditUserFragment;
import com.example.socialgift.view.ShowMyUserFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyUserController {
    private ShowMyUserFragment showMyUserFragment;
    private EditUserFragment editUserFragment;
    private User currentUser;

    public MyUserController(ShowMyUserFragment showMyUserFragment) {
        this.showMyUserFragment = showMyUserFragment;
        // Obtener el usuario actual de la sesión
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        DataManagerDB.getUserByEmail(mAuth.getEmail())
                .addOnSuccessListener(user -> {
                    if (user != null){
                        currentUser = user;
                        showMyUserFragment.showUserData(currentUser);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("mAuth", "Error al obtener el usuario actual", e);
                });
    }

    public MyUserController(EditUserFragment editUserFragment) {
        this.editUserFragment = editUserFragment;
        // Obtener el usuario actual de la sesión
        FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        DataManagerDB.getUserByEmail(mAuth.getEmail())
                .addOnSuccessListener(user -> {
                    if (user != null){
                        currentUser = user;
                        editUserFragment.updateUserData(currentUser);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("mAuth", "Error al obtener el usuario actual", e);
                });
    }

    public void saveUserChanges(String firstName, String lastName, String imageUrl) {
        // Actualizar los datos del usuario actual
        //DataManagerDB.connectDataManagerDB();
        // Actualizar los datos del usuario actual
        currentUser.setName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setImage(imageUrl);
        // Guardar los cambios en la base de datos
        DataManagerDB.updateUser(currentUser);
        // Mostrar un mensaje de confirmación
        editUserFragment.showSuccessMessage();
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        showMyUserFragment.goToLoginActivity();
    }
}
