package com.example.socialgift.controller;


import android.content.Context;
import android.util.Log;

import com.example.socialgift.datamanager.DataManagerAPI;
import com.example.socialgift.datamanager.DataManagerCallbacks;
import com.example.socialgift.datamanager.MercadoExpressAPI;
import com.example.socialgift.model.Gift;
import com.example.socialgift.model.Product;
import com.example.socialgift.model.User;
import com.example.socialgift.model.Wishlist;
import com.example.socialgift.view.myuser.MyGiftsActivity;

import java.util.ArrayList;
import java.util.List;



public class MyGiftsController {

    public interface MyGiftsCallback {
        void onGiftsLoaded(List<Product> gifts, List<String> propietarios);
        void onGiftsLoadError(String errorMessage);
    }

    private MyGiftsActivity activity;
    private Context context;

    List<Gift> giftsList;
    List<Product> productsList = new ArrayList<>();
    List<String> propietarios = new ArrayList<>();

    public MyGiftsController(Context context, MyGiftsActivity activity) {
        this.activity = activity;
        this.context = context;
        this.giftsList = new ArrayList<>();
    }

    public void loadGifts() {
        DataManagerAPI.getGiftsReserved(DataManagerAPI.getObjectUser().getId(), context, new DataManagerCallbacks.DataManagerCallbackListGift<Gift>() {
            @Override
            public void onSuccess(List<Gift> gifts) {
                /*for (Gift a : gifts) {
                    int idProduct = extractProductId(a.getProductUrl());
                    if (idProduct == -1) {
                        Log.e("MyGiftsController", "Error al extraer el id del producto");
                    } else {
                        MercadoExpressAPI.getAProduct(idProduct, context, new DataManagerCallbacks.DataManagerCallbackProduct<Product>() {
                            @Override
                            public void onSuccess(Product product) {
                                productsList.add(product);
                            }

                            @Override
                            public void onError(String errorMessage) {
                                Log.e("MyGiftsController", "getAProduct: "+errorMessage);
                            }
                        });

                        DataManagerAPI.getWishlist(a.getWishlistId(), context, new DataManagerCallbacks.DataManagerCallbackWishlist<Wishlist>() {
                            @Override
                            public void onSuccess(Wishlist wishlist) {
                                DataManagerAPI.getUser(wishlist.getIdUser(), context, new DataManagerCallbacks.DataManagerCallbackUser<User>() {

                                    @Override
                                    public void onSuccess(User user) {
                                        propietarios.add(user.getName()+" "+user.getLastName());
                                    }

                                    @Override
                                    public void onError(String errorMessage) {
                                        Log.e("MyGiftsController", "getUser: "+errorMessage);
                                    }
                                });
                            }

                            @Override
                            public void onError(String errorMessage) {
                                Log.e("MyGiftsController", "getWishlist: "+errorMessage);
                            }
                        });
                    }
                }*/
                //activity.showGifts(productsList, propietarios);
                activity.showGifts(gifts);

            }

            @Override
            public void onError(String errorMessage) {
                Log.e("MyGiftsController", "getGiftsReserved: "+errorMessage);
            }
        });
    }
    public int extractProductId(String productUrl) {
        int lastSlashIndex = productUrl.lastIndexOf("/");
        if (lastSlashIndex != -1 && lastSlashIndex < productUrl.length() - 1) {
            String productIdString = productUrl.substring(lastSlashIndex + 1);
            try {
                return Integer.parseInt(productIdString);
            } catch (NumberFormatException e) {
                // Error de conversión, manejar según sea necesario
                e.printStackTrace();
            }
        }
        return -1; // Valor predeterminado en caso de error o ID no válido
    }

}
