package com.goldenapps.startshopping.carrito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.adapterss.CarritoAdapter;
import com.goldenapps.startshopping.model.ModelCarrito;
import com.goldenapps.startshopping.model.ModelItemCarrito;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CarritoActivity extends AppCompatActivity {

    private DbHelper helper;
    private SQLiteDatabase db;
    private String idUser = "";
    private String idCarrito;
    private String idCa;
    private ArrayList<ModelItemCarrito> listItemsCarrito;
    private ArrayList<ModelCarrito> listCarrito;
    private CarritoAdapter carritoAdapter;
    private DatabaseReference databaseReferenceCarritoUsuario;
    private DatabaseReference databaseReferenceItemCarritoUsuario;


    private RecyclerView recyclerView;


    private Button sgte;
    private TextView TotalPrecio, mensaje1;

    private double PrecioTotalId = 0.0;

    //ELIMINAR DESPUES
    private FirebaseAuth auth;
    private String CurrentUserId;
    private DatabaseReference UserRef;


    //hacer consulta de un carrito por id del usuario y que el estado de este sea 0 = no comprado

    private CarritoFragment carritoFragment = new CarritoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_carrito);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_containerCarrito, carritoFragment);
        transaction.commit();
    }


}