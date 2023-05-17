package com.example.socialgift.controller;

import android.content.Context;
import android.util.Log;

import com.example.socialgift.DataManagerAPI;
import com.example.socialgift.view.LoginActivity;
import com.example.socialgift.view.RegisterActivity;

public class UsersController {

    private RegisterActivity registerActivity;
    private LoginActivity loginActivity;
    private final Context context;

    /*public UsersController(Context context) {
        this.context = context;
    }*/

    public UsersController(RegisterActivity registerActivity, Context context) {
        this.registerActivity = registerActivity;
        this.context = context;
    }

    public UsersController(LoginActivity loginActivity, Context context) {
        this.loginActivity = loginActivity;
        this.context = context;
    }


    public void createUser(String email, String password, String firstName, String lastName, String url) {
        // Llamada al método createUser del DataManagerAPI
        DataManagerAPI.createUser(firstName, lastName, email, password, url, context, new DataManagerAPI.DataManagerCallback() {
            @Override
            public void onSuccess() {
                registerActivity.navigateToLoginActivity();
                Log.d("API_SUCCESS_CREATE_USER", "Usuario creado correctamente");
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_CREATE_USER", errorMessage);
            }
        });
    }

    public void loginUser(String email, String password) {
        DataManagerAPI.loginUser(email, password, context, new DataManagerAPI.DataManagerCallbackLogin() {
            @Override
            public void onSuccess(String accessToken) {
                // El inicio de sesión fue exitoso, manejar el token de acceso o realizar otras operaciones necesarias
                // Por ejemplo, guardar el token en las preferencias compartidas para futuras solicitudes
                // Y luego navegar a la siguiente actividad
                Log.d("API_SUCCESS_LOGIN_USER", "Mi token de acceso es: " + accessToken);
                //saveAccessToken(accessToken);
                loginActivity.onLoginSuccess();
            }

            @Override
            public void onError(String errorMessage) {
                // El inicio de sesión falló, manejar el error o mostrar un mensaje de error
                loginActivity.onLoginError(errorMessage);
            }
        });
    }


    // Otros métodos relacionados con la gestión de usuarios
}
