package com.example.socialgift.view.myuser.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.socialgift.R;
import com.example.socialgift.controller.UsersController;
import com.example.socialgift.model.User;
import com.example.socialgift.model.Wishlist;
import com.example.socialgift.view.LoginActivity;
import com.example.socialgift.view.myuser.MyFriendsActivity;
import com.example.socialgift.view.myuser.MyGiftsActivity;
import com.example.socialgift.view.myuser.MyWishlistsActivity;

import java.util.List;

public class ShowMyUserFragment extends Fragment {

    private ImageView userImageView;
    private TextView nameTextView, friendsCountTextView, reservedGiftsCountTextView, wishlistsCountTextView;
    private LinearLayout wishlistContainer;
    private Button editButton, logoutButton, allWishlistsButton;

    //private MyUserController userController;
     private UsersController userController;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_my_user, container, false);

        // Obtener los elementos de la interfaz de usuario
        userImageView = view.findViewById(R.id.user_image);
        friendsCountTextView = view.findViewById(R.id.friends_count);
        reservedGiftsCountTextView = view.findViewById(R.id.reserved_gifts_count);
        wishlistsCountTextView = view.findViewById(R.id.wishlists_count);
        nameTextView = view.findViewById(R.id.user_name);
        editButton = view.findViewById(R.id.edit_button);
        logoutButton = view.findViewById(R.id.logout_button);
        allWishlistsButton = view.findViewById(R.id.view_all_button);

        wishlistContainer = view.findViewById(R.id.wishlist_container);


        // Crear el controlador
        //userController = new MyUserController(this);
        userController = new UsersController(this, this.getContext());

        allWishlistsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyWishlistsActivity.class);
                startActivity(intent);
            }
        });
        // Configurar los clics en los elementos para abrir nuevas actividades
        friendsCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyFriendsActivity.class);
                startActivity(intent);
            }
        });

        wishlistsCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyWishlistsActivity.class);
                startActivity(intent);
            }
        });



        reservedGiftsCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyGiftsActivity.class);
                startActivity(intent);
            }
        });

        // Agregar el listener del botón "Editar"
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la pantalla de edición de usuario
                EditUserFragment editUserFragment = new EditUserFragment();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.show_my_user_container, editUserFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Agregar el listener del botón "Cerrar sesión"
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar sesión del usuario y navegar a la pantalla de inicio de sesión
                userController.signOut(ShowMyUserFragment.this);
            }
        });


        return view;
    }

    @SuppressLint("SetTextI18n")
    public void showUserData(User user) {
        // Mostrar los datos del usuario en la interfaz de usuario
        userController.getWishlistsCount(new UsersController.DataManagerCallback<Integer>() {
            @Override
            public void onSuccess(Integer count) {
                wishlistsCountTextView.setText("Wishlists: " + count);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_GET_WISHLISTS", errorMessage);
            }
        });

        userController.getReservedGiftsCount(new UsersController.DataManagerCallback<Integer>() {
            @Override
            public void onSuccess(Integer count) {
                reservedGiftsCountTextView.setText("Gifts: " + count);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_GET_GIFTS_RESERVED", errorMessage);
            }
        });

        userController.getFriendsCount(new UsersController.DataManagerCallback<Integer>() {
            @Override
            public void onSuccess(Integer count) {
                TextView friendsCountTextView = requireView().findViewById(R.id.friends_count);
                friendsCountTextView.setText("Amigos: " + count);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_GET_FRIENDS", errorMessage);
            }
        });

        nameTextView.setText(user.getName() + " " + user.getLastName());
        ImageView userImageView = requireView().findViewById(R.id.user_image);
        String url = user.getImage();

        Glide.with(this)
                .load(url)
                .error(R.drawable.baseline_person_24)
                .circleCrop()
                .into(userImageView);

        userController.getClosestWishlists(new UsersController.DataManagerCallback<List<Wishlist>>() {
            @Override
            public void onSuccess(List<Wishlist> wishlists) {
                int count = 0;
                for (Wishlist wishlist : wishlists) {
                    if (count >= 3) {
                        break;
                    }
                    addWishlistTextView(wishlist.getName()+" ("+wishlist.getEndDate()+")");
                    count++;
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_GET_WISHLISTS", errorMessage);
            }
        });

    }

    public void goToLoginActivity() {
        // Navegar a la actividad de login
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    private void addWishlistTextView(String wishlistName) {
        TextView textView = new TextView(requireContext());
        textView.setText(wishlistName);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        wishlistContainer.addView(textView);
    }



}
