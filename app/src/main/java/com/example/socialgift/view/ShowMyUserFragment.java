package com.example.socialgift.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.socialgift.R;
import com.example.socialgift.controller.MyUserController;

public class ShowMyUserFragment extends Fragment {

    private ImageView userImageView;
    private TextView nameTextView, emailTextView;

    private MyUserController userController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_my_user, container, false);

        // Obtener los elementos de la interfaz de usuario
        userImageView = view.findViewById(R.id.show_user_image);
        nameTextView = view.findViewById(R.id.show_user_name);
        emailTextView = view.findViewById(R.id.show_user_email);

        // Crear el controlador
        userController = new MyUserController(this);

        return view;
    }

    public void showUserData(User user) {
        // Mostrar los datos del usuario en la interfaz de usuario
        nameTextView.setText(user.getName() + " " + user.getLastName());
        emailTextView.setText(user.getEmail());
        Glide.with(requireContext())
                .load(user.getImage())
                .placeholder(R.drawable.ic_user)
                .into(userImageView);
    }

    public void goToLoginActivity() {
        // Navegar a la actividad de login
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}
