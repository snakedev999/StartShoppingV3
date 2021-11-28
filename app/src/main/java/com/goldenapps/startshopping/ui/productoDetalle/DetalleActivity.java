package com.goldenapps.startshopping.ui.productoDetalle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.carrito.CarritoActivity;
import com.goldenapps.startshopping.carrito.CompraActivity;
import com.goldenapps.startshopping.ui.menu.MenuActivity;

public class DetalleActivity extends AppCompatActivity {

    ImageView img, back, k;
    TextView proName, proPrice, proDesc,proPuntaje,proCantidad;
    RatingBar ratingBar;

    String name, descrip,image;
    int cantidad;
    double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detalle);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        image = i.getStringExtra("image");
        descrip = i.getStringExtra("descrip");
        cantidad = i.getIntExtra("cantidad",0);
        price = i.getDoubleExtra("price",0.0);


        proName = findViewById(R.id.productName);
        proDesc = findViewById(R.id.prodDesc);
        proPrice = findViewById(R.id.prodPrice);
        proPuntaje = findViewById(R.id.puntaje);
        proCantidad = findViewById(R.id.tv_cantidadProducto);

        img = findViewById(R.id.big_image);
        back = findViewById(R.id.back2);
        k = findViewById(R.id.cart123);

        proName.setText(name);
        proPrice.setText("Precio: $"+price);
        proDesc.setText(descrip);
        proCantidad.setText("Cantidad: "+cantidad);

        Glide.with(getApplicationContext()).load(image).into(img);

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


        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(), CarritoActivity.class);
                startActivity(in);
                finish();

            }
        });
    }
}