package com.example.socialgift.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.socialgift.R;
import com.example.socialgift.datamanager.DataManagerAPI;
import com.example.socialgift.model.User;
import com.example.socialgift.model.UserSession;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button editUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* editUserButton = findViewById(R.id.editUserButton);
        UserSession userSession = new UserSession((User) getIntent().getSerializableExtra("session"));
        System.out.println("MY USER IS: "+userSession.getEmail());
        editUserButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, ShowMyUserActivity.class);
            startActivity(intent);
        });*/

        DataManagerAPI.searchUser("test", this, new DataManagerAPI.DataManagerCallbackUserList<User>() {
            @Override
            public void onSuccess(List<User> userList) {
                // Manejar la lista de usuarios encontrados
                // por ejemplo, mostrarlos en una lista o realizar otras operaciones necesarias
                // Printa por consola los usuarios encontrados
                for (User user : userList) {
                    System.out.println("ID: "+user.getId()+" | Name: "+user.getName()+" | Last name:"+user.getLastName()+" | Email: "+user.getEmail());
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Manejar el error en la búsqueda de usuarios
                // por ejemplo, mostrar un mensaje de error o realizar otras operaciones necesarias
            }
        });


    }
}