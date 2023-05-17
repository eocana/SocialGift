package com.example.socialgift.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.socialgift.R;
import com.example.socialgift.controller.MyUserController;
import com.example.socialgift.model.User;

public class ShowMyUserFragment extends Fragment {

    private ImageView userImageView;
    private TextView nameTextView;

    private Button editButton, logoutButton;

    private MyUserController userController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_my_user, container, false);

        // Obtener los elementos de la interfaz de usuario
        userImageView = view.findViewById(R.id.user_image);
        nameTextView = view.findViewById(R.id.user_name);
        editButton = view.findViewById(R.id.edit_button);
        logoutButton = view.findViewById(R.id.logout_button);

        // Crear el controlador
        userController = new MyUserController(this);

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
                userController.signOut();
            }
        });


        return view;
    }

    public void showUserData(User user) {
        // Mostrar los datos del usuario en la interfaz de usuario
        nameTextView.setText(user.getName() + " " + user.getLastName());
        ImageView userImageView = requireView().findViewById(R.id.user_image);
        String url = user.getImage();

        Glide.with(this)
                .load(url)
                .error(R.drawable.baseline_person_24)
                .circleCrop()
                .into(userImageView);
    }

    public void goToLoginActivity() {
        // Navegar a la actividad de login
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }



}
