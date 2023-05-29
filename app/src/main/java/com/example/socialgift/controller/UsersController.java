package com.example.socialgift.controller;

import android.content.Context;
import android.util.Log;

import com.example.socialgift.datamanager.DataManagerAPI;
import com.example.socialgift.model.User;
import com.example.socialgift.view.EditUserFragment;
import com.example.socialgift.view.LoginActivity;
import com.example.socialgift.view.RegisterActivity;
import com.example.socialgift.view.ShowMyUserFragment;

public class UsersController {

    private ShowMyUserFragment showMyUserFragment;
    private RegisterActivity registerActivity;

    private EditUserFragment editUserFragment;
    private LoginActivity loginActivity;
    private Context context;

    /**
     * Constructor de la clase UsersController cuando quiero registrarme
     * @param registerActivity Fragmento que implementa la interfaz ShowMyUserFragment
     * @param context Contexto de la aplicación
     */
    public UsersController(RegisterActivity registerActivity, Context context) {
        this.registerActivity = registerActivity;
        this.context = context;
    }

    /**
     * Constructor de la clase UsersController cuando quiero loggearme
     * @param loginActivity Fragmento que implementa la interfaz ShowMyUserFragment
     * @param context Contexto de la aplicación
     */
    public UsersController(LoginActivity loginActivity, Context context) {
        this.loginActivity = loginActivity;
        this.context = context;
    }


    /**
     * Constructor de la clase UsersController cuando quiero mostrar mi usuario
     * @param showMyUserFragment Fragmento que implementa la interfaz ShowMyUserFragment
     * @param context Contexto de la aplicación
     */
    public UsersController(ShowMyUserFragment showMyUserFragment, Context context) {
        this.showMyUserFragment = showMyUserFragment;
        this.context = context;

        DataManagerAPI.getMyUser(context, new DataManagerAPI.DataManagerCallbackUser<User>() {
            @Override
            public void onSuccess(User user) {
                showMyUserFragment.showUserData(user);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_GET_MY_USER", errorMessage);
            }
        });
    }

    /**
     * Constructor de la clase UsersController cuando quiero editar mi usuario
     * @param editUserFragment Fragmento que implementa la interfaz EditUserFragment
     * @param context Contexto de la aplicación
     */
    public UsersController(EditUserFragment editUserFragment, Context context){
        this.context = context;
        this.editUserFragment = editUserFragment;
        editUserFragment.updateUserData(DataManagerAPI.getObjectUser());
    }

    /**
     * Método para cerrar sesión
     */
    public void signOut(ShowMyUserFragment showMyUserFragment) {
        DataManagerAPI.logOut();
        showMyUserFragment.goToLoginActivity();
    }

    /**
     * Método para crear un usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @param firstName Nombre del usuario
     * @param lastName Apellido del usuario
     * @param url Url de la imagen del usuario
     */
    public void createUser(String email, String password, String firstName, String lastName, String url) {
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

    /**
     * Método para loggearse
     * @param email Email del usuario
     * @param password Contraseña del usuario
     */
    public void loginUser(String email, String password) {
        DataManagerAPI.loginUser(email, password, context, new DataManagerAPI.DataManagerCallbackLogin() {
            @Override
            public void onSuccess(String accessToken) {
                Log.d("API_SUCCESS_LOGIN_USER", "Mi token de acceso es: " + accessToken);
                //saveAccessToken(accessToken);
                loginActivity.onLoginSuccess();
            }

            @Override
            public void onError(String errorMessage) {
                loginActivity.onLoginError(errorMessage);
            }
        });
    }

    /**
     * Método para actualizar un usuario
     * @param firstName Nombre del usuario
     * @param lastName Apellido del usuario
     * @param imageUrl Url de la imagen del usuario
     */
    public void updateUser(String firstName, String lastName, String imageUrl) {
        // Llamada al método updateUser del DataManagerAPI
        //TODO: url image
        DataManagerAPI.updateUser(firstName, lastName, imageUrl, context, new DataManagerAPI.DataManagerCallback() {
            @Override
            public void onSuccess() {
                editUserFragment.showSuccessMessage();
                Log.d("API_SUCCESS_UPDATE_USER", "Usuario actualizado correctamente");
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_UPDATE_USER", errorMessage);
            }
        });
    }

    // Otros métodos relacionados con la gestión de usuarios
}
