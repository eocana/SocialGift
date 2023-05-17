package com.example.socialgift.controller;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.socialgift.DataManagerDB;
import com.example.socialgift.model.User;
import com.example.socialgift.view.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class RegisterController {

    private RegisterActivity view;
    private FirebaseAuth mAuth;

    public RegisterController(RegisterActivity view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    public void register(String email, String password, String firstName, String lastName) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Save user info to Firebase Realtime Database
                            saveUserInfoToDatabase(firstName, lastName, email);
                            // Navigate to the main activity
                            view.navigateToMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(view, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserInfoToDatabase(String firstName, String lastName, String email) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //DataManagerDB.connectDataManagerDB();
        DataManagerDB.addUser(UUID.randomUUID().toString(),  email, "https://source.boringavatars.com/marble/120/" + firstName + "%20" + lastName + "?colors=264653,2a9d8f,e9c46a,f4a261,e76f51",lastName, firstName);
    }
}
