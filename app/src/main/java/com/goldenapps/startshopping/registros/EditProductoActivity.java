package com.goldenapps.startshopping.registros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.goldenapps.startshopping.R;

public class EditProductoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_producto);

        Intent i = getIntent();
        String name = i.getStringExtra("name1");

    }
}