package com.goldenapps.startshopping.carrito;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.ui.account.RegisterFragment;
import com.goldenapps.startshopping.ui.category.CategoryFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CarritoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button sgte;
    private TextView TotalPrecio, mensaje1;

    private double PrecioTotalId = 0.0;

    //ELIMINAR DESPUES
    private FirebaseAuth auth;
    private String CurrentUserId;
    private DatabaseReference UserRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_carrito);

        //  BORRAR TAMBIEN
        auth = FirebaseAuth.getInstance();
        CurrentUserId = auth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Usuarios");

        //


        recyclerView = (RecyclerView) findViewById(R.id.carrito_lista);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        sgte = (Button) findViewById(R.id.siguiente);
        TotalPrecio = (TextView) findViewById(R.id.precio_total);
        mensaje1 = (TextView) findViewById(R.id.mensaje1);


        sgte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CategoryFragment.class);
                intent.putExtra("Total", String.valueOf(PrecioTotalId));
                startActivity(intent);
                finish();
            }
        });

    }
   /* @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null){
            EnviarAlLogin();
        }else{
            VerificarUsuarioExistente();
        }
    }*/

    private void EnviarAlLogin() {
        Intent intent = new Intent(CarritoActivity.this, RegisterFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }


    private void EnviarAlSetup() {
        Intent intent = new Intent(CarritoActivity.this, SetupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    private void VerificarUsuarioExistente(){
        final String CurrentUserId = auth.getCurrentUser().getUid();
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChild(CurrentUserId));
                EnviarAlSetup();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}});




    }
}