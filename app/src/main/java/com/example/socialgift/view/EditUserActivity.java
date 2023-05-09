package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.socialgift.R;
import com.example.socialgift.view.EditUserFragment;

public class EditUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Agregar el fragmento del encabezado
        /*HeaderFragment headerFragment = new HeaderFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.header_container, headerFragment)
                .commit();*/

        // Agregar el fragmento del cuerpo principal
        EditUserFragment editUserFragment = new EditUserFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, editUserFragment)
                .commit();

        // Agregar el fragmento del pie de página
       /* FooterFragment footerFragment = new FooterFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.footer_container, footerFragment)
                .commit();*/
    }
}
