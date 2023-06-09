package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.socialgift.R;
import com.example.socialgift.controller.UsersController;
import com.example.socialgift.model.User;
import com.example.socialgift.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;


public class ShowUserProfile extends AppCompatActivity {

        Button wishlist;
        Button reserved;
        private TextView nameTextView, friendsCountTextView, reservedGiftsCountTextView, wishlistsCountTextView;
        User user;
        UsersController userController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_profile);

        user = SearchFragment.user;
        //showUserData(user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        TextView myAwesomeTextView2 = (TextView)findViewById(R.id.userName);

        new DownloadImageFromInternet((ImageView) findViewById(R.id.user_image)).execute(SearchFragment.user.getImage());
        //myAwesomeTextView.setText(SearchFragment.user.getId());
        System.out.println("id user :: "+SearchFragment.user.getId());
        myAwesomeTextView2.setText(SearchFragment.user.getName() +" "+SearchFragment.user.getLastName());
        friendsCountTextView = findViewById(R.id.friends_count);
        reservedGiftsCountTextView = findViewById(R.id.reserved_gifts_count);
        wishlistsCountTextView = findViewById(R.id.wishlists_count);
        userController = new UsersController(this, getApplicationContext());
        System.out.println("USER ID :: "+SearchFragment.user.getId());
        showUserData(SearchFragment.user);
        wishlist = (Button) findViewById(R.id.wishlist);
        wishlist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ShowWishlistActivity.class);
                startActivity(myIntent);
            }
        });


        reserved = (Button) findViewById(R.id.reserved);
        reserved.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ShowReservedActivity.class);
                startActivity(myIntent);
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...",Toast.LENGTH_SHORT).show();
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

    }
    public void showUserData(User user) {
        // Mostrar los datos del usuario en la interfaz de usuario
        userController.getWishlistsCount(user.getId(), new UsersController.DataManagerCallback<Integer>() {
            @Override
            public void onSuccess(Integer count) {
                if(count!=null){
                    wishlistsCountTextView.setText("Wishlists: " + count);
                }else{
                    wishlistsCountTextView.setText("Wishlists: 0");
                }

            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_GET_WISHLISTS", errorMessage);
                wishlistsCountTextView.setText("Wishlists: 0");
            }
        });

        userController.getReservedGiftsCount(user.getId(), new UsersController.DataManagerCallback<Integer>() {
            @Override
            public void onSuccess(Integer count) {
                reservedGiftsCountTextView.setText("Regalos reservados: " + count);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_GET_GIFTS_RESERVED", errorMessage);
                reservedGiftsCountTextView.setText("Regalos reservados: 0");

            }
        });

        userController.getFriendsCount(new UsersController.DataManagerCallback<Integer>() {
            @Override
            public void onSuccess(Integer count) {
                TextView friendsCountTextView = findViewById(R.id.friends_count);
                if(count!=null){
                    friendsCountTextView.setText("Amigos: " + count);
                }else{
                    friendsCountTextView.setText("Amigos: 0");
                }


            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_GET_FRIENDS", errorMessage);
                friendsCountTextView.setText("Amigos: 0");
            }
        });
    }
}