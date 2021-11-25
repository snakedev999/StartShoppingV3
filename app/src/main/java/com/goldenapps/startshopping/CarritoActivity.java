package com.goldenapps.startshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class CarritoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button Siguiente;
    private TextView TotalPrecio, mensaje1;

    private double PrecioTotalId = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        recyclerView = (RecyclerView)findViewById(R.id.carrito_lista);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Siguiente = (Button) findViewById(R.id.siguiente);
        TotalPrecio = (TextView) findViewById(R.id.precio_total) ;
        mensaje1 = (TextView) findViewById(R.id.mensaje1);



    }
}