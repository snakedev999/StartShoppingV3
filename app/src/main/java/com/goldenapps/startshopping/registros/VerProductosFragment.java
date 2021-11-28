package com.goldenapps.startshopping.registros;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.adapterss.ProductoSettingsAdapter;
import com.goldenapps.startshopping.model.ModelProducto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerProductosFragment extends Fragment {

    RecyclerView recyclerViewEditProduct;
    DatabaseReference databaseReferenceProductoEdit;
    ProductoSettingsAdapter adapter;
    ArrayList<ModelProducto> listEdit;
    private Button btn_AgregarProducto;

    public VerProductosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ver_productos, container, false);

        recyclerViewEditProduct = view.findViewById(R.id.productoList);

        databaseReferenceProductoEdit = FirebaseDatabase.getInstance().getReference("Productos");
        recyclerViewEditProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        // Inflate the layout for this fragment

        listEdit = new ArrayList<>();
        adapter = new ProductoSettingsAdapter(getActivity(),listEdit);
        recyclerViewEditProduct.setAdapter(adapter);

        databaseReferenceProductoEdit.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listEdit.removeAll(listEdit);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelProducto user1 = dataSnapshot.getValue(ModelProducto.class);
                    String idProducto = dataSnapshot.getKey();
                    String nombre = user1.getNombreProducto();
                    String descripcion = user1.getDescripcionProducto();
                    int cantidad = user1.getCantidadProducto();
                    Double precio = user1.getPrecioProducto();
                    String imagen = user1.getImagenProducto();

                    listEdit.add(new ModelProducto(idProducto,nombre,descripcion,imagen,cantidad,precio));
                }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        btn_AgregarProducto = view.findViewById(R.id.btn_agregarProducto);
        btn_AgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainLogin = new Intent(getActivity(), RegistroProductoActivity.class);
                startActivity(mainLogin);
            }
        });



        return view;
    }
}