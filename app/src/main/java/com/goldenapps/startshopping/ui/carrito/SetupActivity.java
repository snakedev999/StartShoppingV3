package com.goldenapps.startshopping.ui.carrito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.goldenapps.startshopping.R;

public class SetupActivity extends AppCompatActivity {

    private EditText name, fono, region, ciudad, nameCalle,Ncalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setup);
    }
}