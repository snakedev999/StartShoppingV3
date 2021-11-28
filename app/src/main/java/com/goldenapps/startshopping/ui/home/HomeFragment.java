package com.goldenapps.startshopping.ui.home;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.adapterss.ProductoAdapter;
import com.goldenapps.startshopping.model.ModelCarrito;
import com.goldenapps.startshopping.model.ModelItemCarrito;
import com.goldenapps.startshopping.model.ModelProducto;
import com.goldenapps.startshopping.model.ModelRegion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference database;
    DatabaseReference databaseReferenceItemsCountCarrito;
    private DatabaseReference databaseReferenceCarrito;
    ProductoAdapter myAdapter;
    ArrayList<ModelProducto> list;
    ArrayList<ModelItemCarrito> listItemsCarrito;
    private DbHelper helper;
    private SQLiteDatabase db;
    private String idUsuarioCarrito;
    private String idCarritoUsuario;
    private NotificationBadge notificationBadge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        consultaUsuario(1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.userList);
        notificationBadge = view.findViewById(R.id.badge);
        database = FirebaseDatabase.getInstance().getReference("Productos");
        databaseReferenceCarrito = FirebaseDatabase.getInstance().getReference("Carrito");
        databaseReferenceItemsCountCarrito = FirebaseDatabase.getInstance().getReference("ItemCarrito");
        list = new ArrayList<>();
        listItemsCarrito = new ArrayList<>();
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

        databaseReferenceCarrito.orderByChild("idUsuarioCarrito").equalTo(getIdUsuarioCarrito()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot oCarrito : snapshot.getChildren()){
                        ModelCarrito carrito = oCarrito.getValue(ModelCarrito.class);
                        String id = oCarrito.getKey();
                        setIdCarritoUsuario(id);
                    }

                    String idC = getIdCarritoUsuario();

                    databaseReferenceItemsCountCarrito.orderByChild("idCarrito").equalTo(idC).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                listItemsCarrito.removeAll(listItemsCarrito);
                                for(DataSnapshot oItems : snapshot.getChildren()){
                                    String id = oItems.getKey();
                                    listItemsCarrito.add(new ModelItemCarrito(id));
                                }

                                int n = listItemsCarrito.size();

                                notificationBadge.setNumber(n);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;

    }

    private void consultaUsuario(int id){
        helper = new DbHelper(getActivity());
        db = helper.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT IDUSUARIO FROM USUARIO WHERE ID = '"+id+"'",null);

        if(fila.moveToFirst()){
            setIdUsuarioCarrito(fila.getString(0));
        }else{
            setIdUsuarioCarrito("");
        }
        db.close();
    }


    public String getIdUsuarioCarrito() {
        return idUsuarioCarrito;
    }

    public void setIdUsuarioCarrito(String idUsuarioCarrito) {
        this.idUsuarioCarrito = idUsuarioCarrito;
    }

    public String getIdCarritoUsuario() {
        return idCarritoUsuario;
    }

    public void setIdCarritoUsuario(String idCarritoUsuario) {
        this.idCarritoUsuario = idCarritoUsuario;
    }
}