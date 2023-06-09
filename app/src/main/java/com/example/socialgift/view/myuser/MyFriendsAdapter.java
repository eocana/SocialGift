package com.example.socialgift.view.myuser;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift.R;
import com.example.socialgift.model.Friendship;
import com.example.socialgift.model.User;

import java.util.List;

public class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.FriendViewHolder> {
    private List<User> friends;
    private OnItemClickListener itemClickListener;

    public MyFriendsAdapter(List<User> friends) {
        this.friends = friends;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myfriends, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        User friend = friends.get(position);
        holder.bind(friend);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void addFriends(List<User> friends) {
        this.friends.addAll(friends);
    }

    public interface OnItemClickListener {
        void onItemClick(User friend);
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView emailTextView;

        FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.friend_name);
            emailTextView = itemView.findViewById(R.id.friend_email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            User friend = friends.get(position);
                            itemClickListener.onItemClick(friend);
                        }
                    }
                }
            });
        }

        @SuppressLint("SetTextI18n")
        void bind(User friend) {
            nameTextView.setText(friend.getName()+" "+friend.getLastName());
            emailTextView.setText(friend.getEmail());
        }
    }
}
