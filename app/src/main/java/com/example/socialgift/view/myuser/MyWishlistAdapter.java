package com.example.socialgift.view.myuser;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.controller.MyWishlistController;
import com.example.socialgift.model.Wishlist;

import java.util.ArrayList;
import java.util.List;

public class MyWishlistAdapter extends RecyclerView.Adapter<MyWishlistAdapter.WishlistViewHolder> {
    private MyWishlistController myWishlistController;
    private List<Wishlist> wishlists;
    private OnItemClickListener itemClickListener;

    public MyWishlistAdapter(MyWishlistController myWishlistController) {
        this.myWishlistController = myWishlistController;
        this.wishlists = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mywishlist, parent, false);
        return new WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistViewHolder holder, int position) {
        Wishlist wishlist = wishlists.get(position);
        holder.bind(wishlist);
    }

    @Override
    public int getItemCount() {
        return wishlists.size();
    }

    public void setWishlists(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

    public interface OnItemClickListener {
        void onItemClick(Wishlist wishlist);
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView totalGiftsTextView;
        private TextView endDateTextView;

        public WishlistViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.wishlist_title);
            descriptionTextView = itemView.findViewById(R.id.wishlist_description);
            totalGiftsTextView = itemView.findViewById(R.id.wishlist_totalgifts);
            endDateTextView = itemView.findViewById(R.id.wishlist_enddate);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Wishlist wishlist) {

            titleTextView.setText(wishlist.getName());
            descriptionTextView.setText("Descripción: "+wishlist.getDescription());

            if (wishlist.getGifts() != null){
                totalGiftsTextView.setText("Total de regalos: "+wishlist.getGifts().size());
            } else {
                totalGiftsTextView.setText("Total de regalos: 0");
            }

            if (wishlist.getEndDate() != null) {
                endDateTextView.setText(wishlist.getEndDate().toString());
            } else {
                endDateTextView.setText("");
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Wishlist wishlist = wishlists.get(position);
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(wishlist);
                }
            }
        }
    }
}
