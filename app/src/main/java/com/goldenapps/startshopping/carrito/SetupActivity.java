package com.goldenapps.startshopping.carrito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.registros.RegistroCategoriaActivity;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.goldenapps.startshopping.ui.productoDetalle.DetalleActivity;

public class SetupActivity extends AppCompatActivity {

    private ImageButton backW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setup);

        backW = (ImageButton) findViewById(R.id.backWhite);


        backW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SetupActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}