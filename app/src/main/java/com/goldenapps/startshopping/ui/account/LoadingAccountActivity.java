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
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoadingAccountActivity extends AppCompatActivity {

    private String credencial;
    private String idUser;
    private ProgressBar progressBar;
    private int i;
    private boolean progress=false;
    private DatabaseReference usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_loading_account);

        Intent main = getIntent();
        idUser = main.getStringExtra("idUser");

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
        }else if(getCredencial().equals("admin")){
            Toast.makeText(getApplicationContext(),"ADMIN",Toast.LENGTH_LONG).show();
            Intent main = new Intent(LoadingAccountActivity.this, MenuActivity.class);
            main.putExtra("boolean",false);
            main.putExtra("credencial", getCredencial());
            startActivity(main);
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
