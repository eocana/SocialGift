package com.example.socialgift.controller;


import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.socialgift.datamanager.DataManagerCallbacks;
import com.example.socialgift.datamanager.DataManagerSocial;
import com.example.socialgift.model.User;
import com.example.socialgift.view.RequestsActivity;
import com.example.socialgift.view.UserFriendsFragment;

import java.util.List;

public class FriendsController {

    private UserFriendsFragment userFriendsFragment;
    private RequestsActivity requestsActivity;

    private Context context;

    public interface DataManagerCallback<T> {
        void onSuccess(T data);
        void onError(String errorMessage);
    }

    public FriendsController(UserFriendsFragment userFriendsFragment, Context context) {
        this.userFriendsFragment = userFriendsFragment;
        this.context = context;
    }
    public FriendsController(RequestsActivity requestsActivity, Context context) {
        this.requestsActivity = requestsActivity;
        this.context = context;
    }

    public void getUserFriends(int userId){
        DataManagerSocial.getUserFriends(userId, context, new DataManagerCallbacks.DataManagerCallbackUserList<User>(){
            @Override
            public void onSuccess(List<User> users) {
                if(users!=null){
                    UserFriendsFragment.arrayList.clear();
                    for (User u: users ) {
                        System.out.println("w :: "+u.getName());
                        UserFriendsFragment.lstUsers.add(u);
                        UserFriendsFragment.arrayList.add(u.getName());
                    }
                    UserFriendsFragment.listView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "No se han podido recuperar sus amigos",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getFriendRequests(){
        DataManagerSocial.getFriendRequests(context, new DataManagerCallbacks.DataManagerCallbackUserList<User>(){
            @Override
            public void onSuccess(List<User> users) {
                if(users!=null){
                    requestsActivity.list.clear();
                    for (User u: users ) {
                        requestsActivity.list.add(u.getName());
                    }
                }else{
                    Toast.makeText(context, "No tienes solicitudes pendientes",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "No se han podido recuperar sus solicitudes",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
