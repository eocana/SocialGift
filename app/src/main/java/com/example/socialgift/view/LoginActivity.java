package com.example.socialgift.view;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socialgift.R;
import com.example.socialgift.controller.LoginController;
import com.google.firebase.FirebaseApp;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;

    private Button loginButton;

    private Button registerButton;

    private LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        loginController = new LoginController(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                loginController.login(email, password);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Me quiero registrar", Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                //startActivity(intent);
            }
        });
    }

    public void onLoginSuccess() {
        // El login fue exitoso, hacer algo aquí
        Log.d(TAG, "Inicio de sesión exitoso");
        Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show();
    }

    public void onLoginError(String message) {
        // El login falló, mostrar un mensaje de error
        Log.d(TAG, "Inicio de sesión fallido: " + message);
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}