package com.example.socialgift.view;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socialgift.DataManagerDB;
import com.example.socialgift.R;
import com.example.socialgift.controller.LoginController;
import com.example.socialgift.model.UserSession;
import com.google.firebase.FirebaseApp;

import java.util.Objects;

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
        DataManagerDB.connectDataManagerDB();
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        loginController = new LoginController(this);

        loginButton.setEnabled(false);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!isValidEmail(email)) {
                    emailEditText.setError("Correo inválido");
                    return;
                }

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginController.login(email, password);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LoginActivity.this, "Me quiero registrar", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No se requiere implementación
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean enableButton = !TextUtils.isEmpty(emailEditText.getText()) &&
                        !TextUtils.isEmpty(passwordEditText.getText());
                loginButton.setEnabled(enableButton);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No se requiere implementación
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No se requiere implementación
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean enableButton = !TextUtils.isEmpty(emailEditText.getText()) &&
                        !TextUtils.isEmpty(passwordEditText.getText());
                loginButton.setEnabled(enableButton);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No se requiere implementación
            }
        });


    }

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void onLoginSuccess() {
        DataManagerDB.getUserByEmail(emailEditText.getText().toString().trim())
                .addOnSuccessListener(user -> {
                    if (user != null) {
                        UserSession userSession = new UserSession(user);
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("session", userSession);
                        Log.d(TAG, "Inicio de sesión exitoso");
                        //Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    // manejar la excepción aquí
                    // mostrar mensaje de error o realizar alguna acción en consecuencia
                    Log.d(TAG, String.valueOf(e));
                    Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
                });
    }

    public void onLoginError(String message) {
        // El login falló, mostrar un mensaje de error
        Log.d(TAG, "Inicio de sesión fallido: " + message);
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}