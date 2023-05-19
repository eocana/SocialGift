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
        void onSuccess(List<Wishlist> wishlist);
        void onError(String errorMessage);
    }

    interface DataManagerCallbackWishlist<Wishlist> {
        void onSuccess(Wishlist wishlist);
        void onError(String errorMessage);
    }

    interface DataManagerCallbackGift<Gift> {
        void onSuccess(Gift wishlist);
        void onError(String errorMessage);
    }
}
