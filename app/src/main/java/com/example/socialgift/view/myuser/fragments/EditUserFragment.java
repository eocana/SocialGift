package com.example.socialgift.view.myuser.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.socialgift.R;
import com.example.socialgift.controller.UsersController;
import com.example.socialgift.model.User;
import com.example.socialgift.view.myuser.ShowMyUserActivity;

public class EditUserFragment extends Fragment {

    private EditText firstNameEditText, lastNameEditText, imageUrl;
    private Button saveButton, cancelButton, selectImageButton;

    private Uri selectedImageUri;
   private UsersController usersController;

    private ActivityResultLauncher<String> imageSelectionLauncher;
    private static final int IMAGE_SELECTION_REQUEST_CODE = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        // Obtener los elementos de la interfaz de usuario
        firstNameEditText = view.findViewById(R.id.edit_user_firstname);
        lastNameEditText = view.findViewById(R.id.edit_user_lastname);
        imageUrl = view.findViewById(R.id.edit_user_image_url);
        saveButton = view.findViewById(R.id.edit_user_save_button);
        cancelButton = view.findViewById(R.id.edit_user_cancel_button);
        selectImageButton = view.findViewById(R.id.select_image_button);


        usersController = new UsersController(this, this.getContext());

        //TODO: Hacer que si no sea hecho click en la imagen, cambiar pasar como null o otro nombre la url.
        imageSelectionLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            selectedImageUri = result;
                            Log.d("EditUserFragment", "Selected Image Uri: " + selectedImageUri);
                        }
                    }
                });

        // Agregar el listener del botón "Guardar"
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores actualizados de los campos

                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                usersController.updateUser(firstName, lastName, selectedImageUri);

                ShowMyUserActivity activity = (ShowMyUserActivity) requireActivity();
                activity.replaceWithShowMyUserFragment();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMyUserActivity activity = (ShowMyUserActivity) requireActivity();
                activity.replaceWithShowMyUserFragment();
            }
        });

        // Agregar el listener del botón "Seleccionar imagen"
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelectionLauncher.launch("image/*");
            }
        });

        return view;
    }


    public void updateUserData(User user) {
        // Actualizar los campos de la interfaz con los datos del usuario
        firstNameEditText.setText(user.getName());
        lastNameEditText.setText(user.getLastName());
        imageUrl.setText(user.getImage());
    }

    public void showSuccessMessage() {
        // Mostrar un mensaje de confirmación
        Toast.makeText(getActivity(), "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
    }
}

