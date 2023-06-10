package com.example.socialgift.view.myuser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.socialgift.R;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import com.example.socialgift.controller.MyGiftsController;
import com.example.socialgift.model.Gift;
import com.example.socialgift.view.myuser.adapters.MyGiftsAdapter;

import java.util.List;

public class MyGiftsActivity extends AppCompatActivity implements MyGiftsAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private MyGiftsAdapter giftsAdapter;
    private MyGiftsController giftsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gifts);
        giftsController = new MyGiftsController(this, this);

        recyclerView = findViewById(R.id.gifts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        giftsAdapter = new MyGiftsAdapter();
        giftsAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(giftsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        giftsController.loadGifts();

    }

    public void showGifts(List<Gift> gifts) {
        Log.d("MyGiftsActivity", "showGifts: " + gifts.size() + " gifts");
        giftsAdapter.setGifts(gifts);
        giftsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Gift gift) {
        Log.d("MyGiftsActivity", "onItemClick: gift ID " + gift.getId());
        // Abrir la nueva actividad con el ID del regalo
        // Intent intent = new Intent(this, GiftDetailsActivity.class);
        // intent.putExtra("gift_id", gift.getId());
        // startActivity(intent);

    }

}
