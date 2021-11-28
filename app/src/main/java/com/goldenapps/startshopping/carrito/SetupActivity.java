package com.goldenapps.startshopping.carrito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.goldenapps.startshopping.R;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setup);
    }
}