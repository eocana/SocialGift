package com.example.socialgift.view.myuser.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.model.Gift;

import java.util.ArrayList;
import java.util.List;

public class MyGiftsAdapter extends RecyclerView.Adapter<MyGiftsAdapter.GiftViewHolder> {
    private List<Gift> gifts;
    private OnItemClickListener itemClickListener;

    public MyGiftsAdapter() {
        this.gifts = new ArrayList<>();
        //this.propietarios = new ArrayList<>();
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
        //this.propietarios = propietarios;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public GiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mygift, parent, false);
        return new GiftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftViewHolder holder, int position) {
        Gift gift = gifts.get(position);
        holder.bind(gift);
    }

    @Override
    public int getItemCount() {
        return gifts.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Gift gift);
    }

    public class GiftViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView productUrlTextView;
        //private TextView productOwnerTextView;

        public GiftViewHolder(@NonNull View itemView) {
            super(itemView);
            productUrlTextView = itemView.findViewById(R.id.gift_name_text_view);
            //productOwnerTextView = itemView.findViewById(R.id.gift_owner_text_view);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Gift gift) {
            productUrlTextView.setText(gift.getProductUrl());
            //productOwnerTextView.setText("Para el usuario: " + propietario);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && itemClickListener != null) {
                Gift gift = gifts.get(position);
                itemClickListener.onItemClick(gift);
            }
        }
    }
}
