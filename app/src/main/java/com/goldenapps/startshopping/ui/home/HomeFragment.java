package com.goldenapps.startshopping.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.adapterss.ProductoAdapter;
import com.goldenapps.startshopping.model.ModelProducto;
import com.goldenapps.startshopping.model.ModelRegion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference database;
    ProductoAdapter myAdapter;
    ArrayList<ModelProducto> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("Productos");
        list = new ArrayList<>();
        myAdapter = new ProductoAdapter(getContext(),list);

        /*layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);*/

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.removeAll(list);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelProducto user = dataSnapshot.getValue(ModelProducto.class);
                    String idProducto = dataSnapshot.getKey();
                    String nombre = user.getNombreProducto();
                    String descripcion = user.getDescripcionProducto();
                    int cantidad = user.getCantidadProducto();
                    Double precio = user.getPrecioProducto();
                    String imagen = user.getImagenProducto();

                    list.add(new ModelProducto(idProducto,nombre,descripcion,imagen,cantidad,precio));
                }
                myAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return view;


    }
}