package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.socialgift.R;
import com.example.socialgift.controller.MercadoExpressController;
import com.example.socialgift.controller.UsersController;
import com.example.socialgift.model.Gift;
import com.example.socialgift.model.Product;
import com.example.socialgift.model.Wishlist;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.socialgift.datamanager.DataManagerAPI;
import com.example.socialgift.datamanager.DataManagerCallbacks;
import com.example.socialgift.datamanager.MercadoExpressAPI;
import com.example.socialgift.model.User;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button editUserButton;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigationbar);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.menu);



        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.ic_home:
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.ic_user:
                    startActivity(new Intent(this, ShowMyUserActivity.class));
                    break;
                case R.id.ic_basket:
                    break;
                case R.id.ic_menu:
                    break;
            }
            return false;
        });

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
        /*Gift gift = new Gift();
        gift.setBooked(false);
        gift.setPriority(1);
        gift.setProduct_url("https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1/products/2");
        gift.setWishlist_id(1);

        DataManagerAPI.createGift(gift, this, new DataManagerCallbacks.DataManagerCallback(){
            @Override
            public void onSuccess() {
                System.out.println("SE HA CREADO EL GIFT");
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("NO SE HA CREADO EL GIFT");
                System.out.println("Error :: "+errorMessage);
            }
        });*/

        /*DataManagerAPI.bookGift(329,this, new DataManagerCallbacks.DataManagerCallback(){

            @Override
            public void onSuccess() {
                System.out.println("Se ha creado la reserva");
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("No se ha creado la reserva");
                System.out.println("Error : "+errorMessage);
            }
        });*/

       /*MercadoExpressAPI.getAllProducts(this, new DataManagerCallbacks.DataManagerCallbackProductList<Product>() {
            @Override
            public void onSuccess(List list) {
                // Manejar el usuario encontrado
                // por ejemplo, mostrarlo en un formulario o realizar otras operaciones necesarias
                System.out.println("MI ID ES: "+list.size());
            }

            @Override
            public void onError(String errorMessage) {
                // Manejar el error en la búsqueda del usuario
                // por ejemplo, mostrar un mensaje de error o realizar otras operaciones necesarias
            }
        });
*/
       /*Wishlist wishlist1 = new Wishlist();
        wishlist1.setName("Wishlist 1");
        wishlist1.setDescription("Description 1");


        List<Gift> lst = new ArrayList<>();
        lst.add();
        wishlist1.setGifts(lst);



      DataManagerAPI.createWishlist(wishlist1, this, new DataManagerCallbacks.DataManagerCallback() {
            @Override
            public void onSuccess() {
                System.out.println("SE HA CREADO LA WISHLIST");
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("NO SE HA CREADO LA WISHLIST");
            }

        });

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