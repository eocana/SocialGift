package com.example.socialgift.view.myuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.socialgift.R;
import com.example.socialgift.controller.MyFriendsController;
import com.example.socialgift.model.User;
import com.example.socialgift.view.myuser.adapters.MyFriendsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsActivity extends AppCompatActivity implements MyFriendsAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private MyFriendsAdapter friendsAdapter;
    private MyFriendsController friendsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);

        recyclerView = findViewById(R.id.friends_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        friendsAdapter = new MyFriendsAdapter(new ArrayList<>());
        friendsAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(friendsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        friendsController = new MyFriendsController(this, this);
        friendsController.loadFriends();
    }

    @Override
    public void onItemClick(User friend) {
        // Implementa el código para abrir la activizdad de perfil de amigo
        Log.d("MyFriendsActivity", "onItemClick: friend ID " + friend.getId());
    }

    public void showFriends(List<User> friends) {
        Log.d("MyFriendsActivity", "showFriends: " + friends.size() + " friends");
        friendsAdapter.setFriends(friends);
        friendsAdapter.notifyDataSetChanged();
    }

    public void showMoreFriends(List<User> friends) {
        friendsAdapter.addFriends(friends);
        friendsAdapter.notifyDataSetChanged();
    }
}
