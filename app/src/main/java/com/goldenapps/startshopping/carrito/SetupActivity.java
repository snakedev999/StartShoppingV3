package com.goldenapps.startshopping.carrito;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.ui.UsuarioFragment;

public class SetupActivity extends AppCompatActivity {

    private EditText name, fono, region, ciudad, nameCalle,Ncalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setup);
    }


    public void Salir9(View view){
        Intent salir9 = new Intent(this, UsuarioFragment.class);
        startActivity(salir9);
    }


}