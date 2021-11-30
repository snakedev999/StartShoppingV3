package com.goldenapps.startshopping.carrito;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.adapterss.CarritoAdapter;
import com.goldenapps.startshopping.carrito.process.ShippFragment;
import com.goldenapps.startshopping.model.ModelCarrito;
import com.goldenapps.startshopping.model.ModelItemCarrito;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CarritoFragment extends Fragment {

    private DbHelper helper;
    private SQLiteDatabase db;
    private String idUser = "";
    private String idCarrito;
    private double total;
    private ArrayList<ModelItemCarrito> listItemsCarrito;
    private ArrayList<ModelCarrito> listCarrito;
    private CarritoAdapter carritoAdapter;
    private DatabaseReference databaseReferenceCarritoUsuario;
    private DatabaseReference databaseReferenceItemCarritoUsuario;
    private TextView tv_precioTotalCarrito;


    private RecyclerView recyclerView;

    public CarritoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        databaseReferenceCarritoUsuario = FirebaseDatabase.getInstance().getReference("Carrito");
        databaseReferenceItemCarritoUsuario = FirebaseDatabase.getInstance().getReference("ItemCarrito");
        recyclerView = view.findViewById(R.id.recyclerCarrito);
        tv_precioTotalCarrito = view.findViewById(R.id.tv_precioTotalCarrito);
        Button btn_siguiente = view.findViewById(R.id.btn_siguiente);
        consultaUsuario(1);

        listCarrito = new ArrayList<>();
        listItemsCarrito = new ArrayList<>();
        carritoAdapter = new CarritoAdapter(getActivity(), listItemsCarrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(carritoAdapter);

        databaseReferenceCarritoUsuario.orderByChild("idUsuarioCarrito").equalTo(getIdUser()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCarrito.removeAll(listItemsCarrito);
                if (snapshot.exists()) {
                    for (DataSnapshot oCarrito : snapshot.getChildren()) {
                        ModelCarrito carrito = oCarrito.getValue(ModelCarrito.class);
                        String id = oCarrito.getKey();
                        int estado = carrito.getEstadoCarrito();
                        setIdCarrito(id);
                        listCarrito.add(new ModelCarrito(id, estado));
                    }
                    for (ModelCarrito modelCarrito : listCarrito){
                        if (modelCarrito.getEstadoCarrito() == 0){
                            String n = modelCarrito.getIdCarrito();
                            databaseReferenceItemCarritoUsuario.orderByChild("idCarrito").equalTo(n).addValueEventListener(new ValueEventListener() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    listItemsCarrito.removeAll(listItemsCarrito);
                                    total = 0.0;
                                    if (snapshot.exists()) {
                                        for (DataSnapshot oItemCarrito : snapshot.getChildren()) {
                                            ModelItemCarrito itemCarrito = oItemCarrito.getValue(ModelItemCarrito.class);
                                            String idItemCarrito = oItemCarrito.getKey();
                                            String idCarrito = itemCarrito.getIdCarrito();
                                            String idProductoCarrito = itemCarrito.getIdProducto();
                                            int cantidad = itemCarrito.getCantidadItem();
                                            double subTotal = itemCarrito.getSubTotalItem();
                                            String imagenPro = itemCarrito.getImagen();
                                            String nombre = itemCarrito.getNombre();
                                            double precioUnit = itemCarrito.getPrecioUnitario();
                                            listItemsCarrito.add(new ModelItemCarrito(idItemCarrito,idCarrito,idProductoCarrito, cantidad, subTotal,precioUnit,imagenPro,nombre));
                                        }
                                        carritoAdapter.notifyDataSetChanged();
                                        for (ModelItemCarrito modelItemCarrito : listItemsCarrito){
                                            total = total + modelItemCarrito.getSubTotalItem();
                                        }
                                        tv_precioTotalCarrito.setText("$"+Double.toString(total));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                } else {
                    try {
                        ModelCarrito carritoNew = new ModelCarrito(getIdUser(), 0, 0.0);
                        String modelId = databaseReferenceCarritoUsuario.push().getKey();
                        if (modelId != null) {
                            databaseReferenceCarritoUsuario.child(modelId).setValue(carritoNew);
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getIdUser().equals("")){
                    ShippFragment shippFragment = new ShippFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out);
                    transaction.replace(R.id.frame_containerCarrito, shippFragment);
                    transaction.addToBackStack(getContext().getClass().getName());
                    transaction.commit();
                }else{

                }
            }
        });
        return view;
    }

    private void consultaUsuario(int id){
        helper = new DbHelper(getActivity());
        db = helper.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT IDUSUARIO FROM USUARIO WHERE ID = '"+id+"'",null);

        if(fila.moveToFirst()){
            setIdUser(fila.getString(0));
        }else{
            setIdUser("");
        }
        db.close();
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }
}