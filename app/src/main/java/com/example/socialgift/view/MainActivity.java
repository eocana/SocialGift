package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;
import android.widget.Button;

import com.example.socialgift.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.socialgift.datamanager.DataManagerAPI;
import com.example.socialgift.datamanager.DataManagerCallbacks;
import com.example.socialgift.model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button editUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationbar);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.menu);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_searchBar, SearchFragment.class, null)
                    .commit();
        }
        DataManagerAPI.getMyUser(this, new DataManagerCallbacks.DataManagerCallbackUser<User>() {
            @Override
            public void onSuccess(User user) {
                // Manejar el usuario encontrado
                // por ejemplo, mostrarlo en un formulario o realizar otras operaciones necesarias
                System.out.println("MI ID ES: "+user.getId());
            }

            @Override
            public void onError(String errorMessage) {
                // Manejar el error en la búsqueda del usuario
                // por ejemplo, mostrar un mensaje de error o realizar otras operaciones necesarias
            }
        });

/*        Wishlist wishlist1 = new Wishlist();
        wishlist1.setName("Wishlist 1");
        wishlist1.setDescription("Description 1");
        wishlist1.setIdUser(1);*/

/*        DataManagerAPI.createWishlist(wishlist1, this, new DataManagerCallbacks.DataManagerCallback() {
            @Override
            public void onSuccess() {
                // Manejar la creación de la wishlist
                // por ejemplo, mostrar un mensaje de éxito o realizar otras operaciones necesarias

            }

            @Override
            public void onError(String errorMessage) {
                // Manejar el error en la creación de la wishlist
                // por ejemplo, mostrar un mensaje de error o realizar otras operaciones necesarias
            }
        });*/

     /* DataManagerAPI.getWishlist(105, this, new DataManagerCallbacks.DataManagerCallbackWishlist<Wishlist>() {
           @Override
           public void onSuccess(Wishlist wishlist) {
                // Manejar la lista de wishlists encontradas
                // por ejemplo, mostrarlos en una lista o realizar otras operaciones necesarias
                // Printa por consola los usuarios encontrados
               System.out.println("ID: "+wishlist.getId()+" | Name: "+wishlist.getName()+" | Description:"+wishlist.getDescription()+" | Id user: "+wishlist.getIdUser());
               wishlist.setName("Wishlist Nueva 100");
               DataManagerAPI.editWishlist(wishlist, getApplicationContext(), new DataManagerCallbacks.DataManagerCallback() {
                   @Override
                   public void onSuccess() {
                          // Manejar la edición de la wishlist
                          // por ejemplo, mostrar un mensaje de éxito o realizar otras operaciones necesarias

                   }

                   @Override
                   public void onError(String errorMessage) {
                       Log.e("ERROR", errorMessage);
                   }
               });
           }

           @Override
           public void onError(String errorMessage) {
                // Manejar el error en la búsqueda de wishlists
                // por ejemplo, mostrar un mensaje de error o realizar otras operaciones necesarias
               System.out.println("ERROR: "+errorMessage);
           }
       });*/


    }
}