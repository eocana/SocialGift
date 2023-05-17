package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socialgift.R;
import com.example.socialgift.controller.UsersController;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etFirstName, etLastName, etConfirmPassword;
    private Button btnRegister;
    private UsersController usersController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.register_email);
        etPassword = findViewById(R.id.register_password);
        etConfirmPassword = findViewById(R.id.register_confirm_password);
        etFirstName = findViewById(R.id.register_name);
        etLastName = findViewById(R.id.register_last_name);
        btnRegister = findViewById(R.id.register_button);
        btnRegister.setEnabled(true);

        //controller = new RegisterController(this);
        usersController = new UsersController(this, this);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();
                String firstName = etFirstName.getText().toString().trim();
                String lastName = etLastName.getText().toString().trim();

                if (validateFields(email, lastName, firstName, password, confirmPassword)){
                    btnRegister.setEnabled(true);
                }

                usersController.createUser(email, password, firstName, lastName, "https://rockfm-cdnmed.agilecontent.com/resources/jpg/1/2/1627558630021.jpg");


            }
        });
    }

    //TODO Comprobar porque no funciona correctamente
    private boolean validateFields(String name, String lastName, String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Escribe todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (isValidEmail(email)){
            Toast.makeText(RegisterActivity.this, "Escribe un correo valido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}