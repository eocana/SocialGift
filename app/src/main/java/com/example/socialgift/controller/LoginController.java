package com.example.socialgift.controller;

import android.nfc.Tag;

import androidx.annotation.NonNull;

import com.example.socialgift.view.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginController {
    private LoginActivity loginActivity;

    public LoginController(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;

    }

    public void login(String email, String password) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // La autenticación fue exitosa
                            loginActivity.onLoginSuccess();
                        } else {
                            // La autenticación falló
                            String message = task.getException().getMessage();
                            loginActivity.onLoginError(message);
                        }
                    }
                });
    }
}