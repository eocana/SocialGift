package com.example.socialgift.view.myuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.socialgift.R;
import com.example.socialgift.controller.MyWishlistController;
import com.example.socialgift.model.Wishlist;

import java.util.ArrayList;
import java.util.List;

public class MyWishlistsActivity extends AppCompatActivity implements MyWishlistAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private MyWishlistAdapter wishlistAdapter;
    private MyWishlistController wishlistController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wishlists);

        wishlistController = new MyWishlistController(this, this);

        recyclerView = findViewById(R.id.wishlists_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wishlistAdapter = new MyWishlistAdapter(wishlistController);
        wishlistAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(wishlistAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        wishlistController.loadWishlists();
    }

    public void showWishlists(List<Wishlist> wishlists) {
        wishlistAdapter.setWishlists(wishlists);
        wishlistAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Wishlist wishlist) {
        Log.d("MyWishlistActivity", "onItemClick: wishlist ID " + wishlist.getId());
       /* // Abrir la nueva actividad con el ID de la wishlist
        Intent intent = new Intent(this, WishlistDetailsActivity.class);
        intent.putExtra("wishlist_id", wishlist.getId());
        startActivity(intent);*/
    }
}
