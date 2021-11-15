package com.goldenapps.startshopping.ui.productoDetalle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.ui.menu.MenuActivity;

public class DetalleActivity extends AppCompatActivity {

    ImageView img, back;
    TextView proName, proPrice, proDesc,proPuntaje,proCantidad;
    RatingBar ratingBar;

    String name, price, desc,punt,cantidad;
    int image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detalle);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        image = i.getIntExtra("image", 0);
        price = i.getStringExtra("price");
        desc = i.getStringExtra("desc");
        punt = i.getStringExtra("puntaje");
        cantidad = i.getStringExtra("cantidad");


        proName = findViewById(R.id.productName);
        proDesc = findViewById(R.id.prodDesc);
        proPrice = findViewById(R.id.prodPrice);
        proPuntaje = findViewById(R.id.puntaje);
        proCantidad = findViewById(R.id.cantidade);

        img = findViewById(R.id.big_image);
        back = findViewById(R.id.back2);

        proName.setText(name);
        proPrice.setText("Precio: $"+price);
        proDesc.setText(desc);
        proPuntaje.setText(punt);
        proCantidad.setText("Cantidad: "+cantidad);

        img.setImageResource(image);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                TextView text = findViewById(R.id.puntaje);
                String s = String.valueOf(ratingBar.getRating());
                text.setText(s);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DetalleActivity.this, MenuActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
}