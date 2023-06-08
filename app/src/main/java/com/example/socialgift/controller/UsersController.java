package com.example.socialgift.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.socialgift.datamanager.DataManagerAPI;
import com.example.socialgift.datamanager.DataManagerCallbacks;
import com.example.socialgift.datamanager.DataManagerSocial;
import com.example.socialgift.model.Gift;
import com.example.socialgift.model.User;
import com.example.socialgift.model.Wishlist;
import com.example.socialgift.view.EditUserFragment;
import com.example.socialgift.view.LoginActivity;
import com.example.socialgift.view.RegisterActivity;
import com.example.socialgift.view.SearchFragment;
import com.example.socialgift.view.ShowGiftFragment;
import com.example.socialgift.view.ShowMyUserFragment;
import com.example.socialgift.view.ShowReservedActivity;
import com.example.socialgift.view.ShowReservedFragment;
import com.example.socialgift.view.ShowUserProfile;
import com.example.socialgift.view.ShowWishlistFragment;

import java.util.List;

public class UsersController {

    private ShowMyUserFragment showMyUserFragment;
    private RegisterActivity registerActivity;
    private SearchFragment searchFragment;
    private EditUserFragment editUserFragment;
    private ShowUserProfile showUserProfile;
    private LoginActivity loginActivity;
    private ShowWishlistFragment showWishlistFragment;
    private ShowReservedFragment showReservedFragment;
    private ShowGiftFragment showGiftFragment;
    private ShowReservedActivity showReservedActivity;
    private Context context;


    public interface DataManagerCallback<T> {
        void onSuccess(T data);
        void onError(String errorMessage);
    }

    /**
     * Constructor de la clase UsersController cuando quiero registrarme
     * @param registerActivity Fragmento que implementa la interfaz ShowMyUserFragment
     * @param context Contexto de la aplicación
     */
    public UsersController(RegisterActivity registerActivity, Context context) {
        this.registerActivity = registerActivity;
        this.context = context;
    }
    public UsersController(ShowUserProfile showUserProfile, Context context) {
        this.showUserProfile = showUserProfile;
        this.context = context;
    }

    /**
     * Constructor de la clase UsersController cuando quiero loggearme
     * @param searchFragment Fragmento que implementa la interfaz ShowMyUserFragment
     * @param context Contexto de la aplicación
     */
    public UsersController(SearchFragment searchFragment, Context context) {
        this.searchFragment = searchFragment;
        this.context = context;
    }

    public UsersController(ShowWishlistFragment showWishlistFragment, Context context) {
        this.showWishlistFragment = showWishlistFragment;
        this.context = context;
    }
    public UsersController(ShowGiftFragment showGiftFragment, Context context) {
        this.showGiftFragment = showGiftFragment;
        this.context = context;
    }
    public UsersController(ShowReservedFragment showReservedFragment, Context context) {
        this.showReservedFragment = showReservedFragment;
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

    public void getWishlistsCount(int id,DataManagerCallback<Integer> callback) {
        DataManagerAPI.wishlistsUser(id,context, new DataManagerCallbacks.DataManagerCallbackWishlists<Wishlist>() {
            @Override
            public void onSuccess(List<Wishlist> wishlists) {
                int count = wishlists.size();
                callback.onSuccess(count);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
    public void getReservedGiftsCount(DataManagerCallback<Integer> callback) {
        DataManagerAPI.getGiftsReserved(DataManagerAPI.getObjectUser().getId(), context, new DataManagerCallbacks.DataManagerCallbackListGift<Gift>() {
            @Override
            public void onSuccess(List<Gift> gifts) {
                int count = gifts.size();
                callback.onSuccess(count);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
    public void getFriendsCount(DataManagerCallback<Integer> callback) {
        DataManagerSocial.getUserFriends(DataManagerAPI.getObjectUser().getId(), context, new DataManagerCallbacks.DataManagerCallbackUserList<User>() {
            @Override
            public void onSuccess(List<User> users) {
                int count = users.size();
                callback.onSuccess(count);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
    public void searchUser(String searchTerm) {
        DataManagerAPI.searchUser(searchTerm, context, new DataManagerCallbacks.DataManagerCallbackUserList<>(){
        
            @Override
            public void onSuccess(List<User> list) {
                Log.d("API_SUCCESS_SEARCH_USER", "Mi LISTA DE USUARIOS ES:  " + list);
                System.out.println("lista :: "+list);
                if(list!=null){
                    for (User u: list ) {
                        SearchFragment.arrayList.add(u.getEmail());
                        SearchFragment.lstUsers.add(u);
                    }
                    SearchFragment.listView.setVisibility(View.VISIBLE);
                    SearchFragment.adapter.getFilter().filter(searchTerm);
                    System.out.println(SearchFragment.arrayList);
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_SEARCH_USER", errorMessage);

            }
        });
    }

    public void wishlistsUser(int id) {
        DataManagerAPI.wishlistsUser(id, context, new DataManagerCallbacks.DataManagerCallbackWishlists<>() {
            @Override
            public void onSuccess(List<Wishlist> wishlists) {
                Log.d("API_SUCCESS_SEARCH_USER", "Mi LISTA DE WISHLIST ES:  " + wishlists);
                if(wishlists!=null){
                    ShowWishlistFragment.arrayList.clear();
                    for (Wishlist w: wishlists ) {
                        System.out.println("w :: "+w.getName());
                        ShowWishlistFragment.lstWishlist.add(w);
                        ShowWishlistFragment.arrayList.add(w.getName());
                    }
                    ShowWishlistFragment.listView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_SEARCH_USER", errorMessage);
            }
        });
    }
    public void getGiftsReserved(int id){
        DataManagerAPI.getGiftsReserved(id, context, new DataManagerCallbacks.DataManagerCallbackListGift<>() {

            @Override
            public void onSuccess(List<Gift> gift) {
                System.out.println("MI ID QUE LE ESTOY PASANDO AL RESERVADO ES :: "+id);
                Log.d("API_SUCCESS_SEARCH_USER", "Mi LISTA DE WISHLIST ES:  " + gift);
                System.out.println("lista reseved gifts:: "+gift);
                if(gift!=null){
                    for (Gift w: gift ) {
                        //ShowReservedFragment.lstGift.add(w);
                        String[] result = w.getProductUrl().split("/");
                        //ShowReservedFragment.arrayList.add(result[result.length-1]);
                        ShowReservedFragment.mercadoExpressController.getAProduct(Integer.parseInt(result[result.length-1]),1);
                    }
                    ShowReservedFragment.listView.setVisibility(View.VISIBLE);;
                    //ShowGiftFragment.productsId = ShowReservedFragment.lstGift;
                }else{
                    Toast.makeText(context, "No tiene regalos reservados",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_SEARCH_USER", errorMessage);
            }
        });
    }

    public void bookGift(int giftId){
        DataManagerAPI.bookGift(giftId, context, new DataManagerCallbacks.DataManagerCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Se ha creado la reserva",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "No se ha creado la reserva",Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Otros métodos relacionados con la gestión de usuarios
}
