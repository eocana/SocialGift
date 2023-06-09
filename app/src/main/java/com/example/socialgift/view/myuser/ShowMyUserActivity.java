package com.example.socialgift.view.myuser;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.socialgift.R;
import com.example.socialgift.view.myuser.fragments.ShowMyUserFragment;


public class ShowMyUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_user);



 /*       */

        // Agregar el fragmento del encabezado
        /*HeaderFragment headerFragment = new HeaderFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.header_container, headerFragment)
                .commit();*/

        // Crea una instancia del fragment ShowMyUserFragment
        ShowMyUserFragment showMyUserFragment = new ShowMyUserFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.show_my_user_container, showMyUserFragment)
                .commit();

        // Agregar el fragmento del pie de página
       /* FooterFragment footerFragment = new FooterFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.footer_container, footerFragment)
                .commit();*/
    }

    public void replaceWithShowMyUserFragment() {
        ShowMyUserFragment showMyUserFragment = new ShowMyUserFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.show_my_user_container, showMyUserFragment)
                .commit();
    }
}
