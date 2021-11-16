package com.goldenapps.startshopping.ui.account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelAccount;
import com.goldenapps.startshopping.ui.AdminFragment;
import com.goldenapps.startshopping.ui.UsuarioFragment;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoadingAccountActivity extends AppCompatActivity {

    private DatabaseReference usuario;
    private String credencial;
    private String idUser;
    

    private ProgressBar progressBar;
    private int i;
    private boolean progress=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_loading_account);

        Intent mainLogin = getIntent();
        idUser = mainLogin.getStringExtra("idUser");

        i = 0;
        progressBar = findViewById(R.id.progressBar);
        comprobacion();
    }

    @Override
    public void onBackPressed() {

    }

    private void verficacion(){
        if(getCredencial().equals("usuario")){
            Toast.makeText(getApplicationContext(),"Usuario",Toast.LENGTH_LONG).show();
            Intent mainMenu = new Intent(LoadingAccountActivity.this, MenuActivity.class);
            mainMenu.putExtra("boolean",false);
            mainMenu.putExtra("credencial", false);
            mainMenu.putExtra("credencial2", true);
            mainMenu.putExtra("idusuario",idUser);
            startActivity(mainMenu);
            finish();
        }else if(getCredencial().equals("admin")){
            Toast.makeText(getApplicationContext(),"ADMIN",Toast.LENGTH_LONG).show();
            Intent mainMenu = new Intent(LoadingAccountActivity.this, MenuActivity.class);
            mainMenu.putExtra("boolean",false);
            mainMenu.putExtra("credencial", true);
            mainMenu.putExtra("credencial2", false);
            mainMenu.putExtra("idusuario",idUser);
            startActivity(mainMenu);
            finish();
        }
    }


    private void comprobacion(){
        usuario = FirebaseDatabase.getInstance().getReference("Usuarios");
        usuario.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelAccount userAccount = snapshot.getValue(ModelAccount.class);
                setCredencial(userAccount.tipoUsuario);
                verficacion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public String getCredencial() {
        return credencial;
    }

    public void setCredencial(String credencial) {
        this.credencial = credencial;
    }
}
