package com.example.socialgift.datamanager;

import java.util.List;

public interface DataManagerCallbacks {

    interface DataManagerCallback {
        void onSuccess();

        void onError(String errorMessage);
    }
    interface DataManagerCallbackUser<User> {
        void onSuccess(User user);
        void onError(String errorMessage);
    }

    interface DataManagerCallbackLogin {
        void onSuccess(String token);
        void onError(String errorMessage);
    }

    interface DataManagerCallbackUserList<User> {
        void onSuccess(List<User> userList);
        void onError(String errorMessage);
    }

    interface DataManagerCallbackWishlists<Wishlist> {
        void onSuccess(List<Wishlist> wishlistList);
        void onError(String errorMessage);
    }

    interface DataManagerCallbackWishlist<Wishlist> {
        void onSuccess(Wishlist wishlist);
        void onError(String errorMessage);
    }

    interface DataManagerCallbackGift<Gift> {
        void onSuccess(Gift gift);
        void onError(String errorMessage);
    }

    interface DataManagerCallbackListGift<Gift> {
        void onSuccess(List<Gift> gift);
        void onError(String errorMessage);
    }

    interface DataManagerCallbackProduct<Product> {
        void onSuccess(Product product);
        void onError(String errorMessage);
    }

    interface DataManagerCallbackProductList<Product> {
        void onSuccess(List<Product> productList);
        void onError(String errorMessage);
    }

    interface DataManagerCallbackCategory<Category> {
        void onSuccess(Category category);
        void onError(String errorMessage);
    }

    interface  DataManagerCallbackCategories<Category> {
        void onSuccess(List<Category> categoryList);
        void onError(String errorMessage);
    }


}
