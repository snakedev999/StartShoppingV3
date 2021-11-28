package com.goldenapps.startshopping.registros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.goldenapps.startshopping.R;

public class VerProductosActivity extends AppCompatActivity {

    private Button btn_AgregarProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ver_productos);

        btn_AgregarProducto = findViewById(R.id.btn_agregarProducto);
        btn_AgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainLogin = new Intent(getApplicationContext(), RegistroProductoActivity.class);
                startActivity(mainLogin);
            }
        });
    }
}