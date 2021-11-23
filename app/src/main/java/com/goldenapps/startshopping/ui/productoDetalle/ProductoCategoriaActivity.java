package com.goldenapps.startshopping.ui.productoDetalle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.adapterss.ProductoAdapter;
import com.goldenapps.startshopping.model.ModelProducto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductoCategoriaActivity extends AppCompatActivity {

    private String idCategoriaProducto;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference databaseReferenceProductoCategoria;
    ProductoAdapter productoAdapter;
    ArrayList<ModelProducto> listaProductoCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_categoria);

        recyclerView = findViewById(R.id.productoCategoriaList);

        Intent i = getIntent();
        idCategoriaProducto = i.getStringExtra("idCategoriaProducto");
        databaseReferenceProductoCategoria = FirebaseDatabase.getInstance().getReference("Productos");

        listaProductoCategoria = new ArrayList<>();
        productoAdapter = new ProductoAdapter(getApplicationContext(),listaProductoCategoria);

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productoAdapter);

        databaseReferenceProductoCategoria.orderByChild("idCategoriaProducto").equalTo(idCategoriaProducto).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductoCategoria.removeAll(listaProductoCategoria);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelProducto user = dataSnapshot.getValue(ModelProducto.class);
                    listaProductoCategoria.add(user);
                }
                productoAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}