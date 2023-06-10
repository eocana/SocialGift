package com.example.socialgift.controller;

import android.content.Context;
import android.util.Log;

import com.example.socialgift.datamanager.DataManagerAPI;
import com.example.socialgift.datamanager.DataManagerCallbacks;
import com.example.socialgift.model.Wishlist;
import com.example.socialgift.view.myuser.MyWishlistsActivity;

import java.util.ArrayList;
import java.util.List;

public class MyWishlistController implements DataManagerCallbacks {

    private MyWishlistsActivity activity;
    private Context context;
    private int currentPage = 0;
    private final int itemsPerPage  = 10;
    private List<Wishlist> loadedWishlists = new ArrayList<>();


    public MyWishlistController(Context context, MyWishlistsActivity activity) {
        this.activity = activity;
        this.context = context;
    }

    public void loadWishlists() {
        DataManagerAPI.wishlistsMyUser(activity, new DataManagerCallbackWishlists<Wishlist>() {
            @Override
            public void onSuccess(List<Wishlist> wishlists) {
                //loadedWishlists.addAll(wishlists);
                /*int startIndex = currentPage * itemsPerPage;
                int endIndex = Math.min(startIndex + itemsPerPage, loadedWishlists.size());
                List<Wishlist> paginatedWishlists = loadedWishlists.subList(startIndex, endIndex);*/

                activity.showWishlists(wishlists);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("MyWishlistController", errorMessage);
            }
        });
    }

    public void loadMoreWishlists() {
      /*  currentPage++;
        int startIndex = currentPage * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, loadedWishlists.size());
        List<Wishlist> paginatedWishlists = loadedWishlists.subList(startIndex, endIndex);
        activity.showMoreWishlists(paginatedWishlists);*/
    }

}
