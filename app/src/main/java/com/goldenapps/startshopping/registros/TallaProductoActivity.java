package com.goldenapps.startshopping.registros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelRegion;
import com.goldenapps.startshopping.model.ModelTallaProducto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TallaProductoActivity extends AppCompatActivity {

    private String idProducto;
    private EditText tallaPro;
    private DatabaseReference databaseReferenceTalla = FirebaseDatabase.getInstance().getReference("TallaProducto");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_talla_producto);

        Intent in = getIntent();
        idProducto = in.getStringExtra("idProducto");

        tallaPro = findViewById(R.id.edt_Talla);
        Button agregarTalla = findViewById(R.id.btn_agregarTalla);

        agregarTalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarTallaUp();
            }
        });


    }

    private void agregarTallaUp(){
        try {
            String talla;
            talla = tallaPro.getText().toString();
            ModelTallaProducto modelTallaProducto = new ModelTallaProducto(idProducto,talla);
            String modelId = databaseReferenceTalla.push().getKey();
            if(modelId != null){
                databaseReferenceTalla.child(modelId).setValue(modelTallaProducto);
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}