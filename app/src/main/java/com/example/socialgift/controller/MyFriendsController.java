package com.example.socialgift.controller;

import android.content.Context;
import android.util.Log;

import com.example.socialgift.datamanager.DataManagerAPI;
import com.example.socialgift.datamanager.DataManagerCallbacks;
import com.example.socialgift.datamanager.DataManagerSocial;
import com.example.socialgift.model.User;
import com.example.socialgift.view.myuser.MyFriendsActivity;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsController implements DataManagerCallbacks {
    private Context context;
    private MyFriendsActivity activity;
    private int currentPage = 0;
    private final int itemsPerPage  = 10;
    private List<User> loadedFriends = new ArrayList<>();

    public MyFriendsController(Context context, MyFriendsActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void loadFriends() {
        DataManagerSocial.getUserFriends(DataManagerAPI.getObjectUser().getId(),activity, new DataManagerCallbackUserList<User>() {
            @Override
            public void onSuccess(List<User> friends) {
              /*  loadedFriends.addAll(friends);
                int startIndex = currentPage * itemsPerPage;
                int endIndex = Math.min(startIndex + itemsPerPage, loadedFriends.size());
                List<User> paginatedFriends = loadedFriends.subList(startIndex, endIndex);*/

                activity.showFriends(friends);
            }

            @Override
            public void onError(String errorMessage) {
                Log.d("MyFriendsController", errorMessage);
            }
        });

    }

    public void loadMoreFriends() {
        currentPage++;
        int startIndex = currentPage * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, loadedFriends.size());
        List<User> paginatedFriends = loadedFriends.subList(startIndex, endIndex);
        activity.showMoreFriends(paginatedFriends);
    }
}
