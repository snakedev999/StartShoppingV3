package com.goldenapps.startshopping.registros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelComuna;
import com.goldenapps.startshopping.model.ModelRegion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegistroComunaActivity extends AppCompatActivity {

    Spinner oSpinnerRegionComuna;
    EditText oEdtNombreComuna;
    Button oBtnRegistroComuna;
    TextView regionComuna;
    DatabaseReference databaseReference;

    String regionSeleccionada;
    String idRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_comuna);

        oSpinnerRegionComuna = findViewById(R.id.spinnerRegionComuna);
        oEdtNombreComuna = findViewById(R.id.edt_nombreComuna);
        regionComuna = findViewById(R.id.tv_spinnerRegionComuna);
        oBtnRegistroComuna = findViewById(R.id.btn_RegistrarComuna);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        loadRegion();
        oBtnRegistroComuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarComunaRegion();
            }
        });

    }

    private void registrarComunaRegion(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Comuna");
        try {
            String nombreComuna;
            nombreComuna = oEdtNombreComuna.getText().toString();
            ModelComuna modelComuna = new ModelComuna(idRegion,nombreComuna);
            String modelId = databaseReference.push().getKey();
            if(modelId != null){
                oBtnRegistroComuna.setVisibility(View.INVISIBLE);
                databaseReference.child(modelId).setValue(modelComuna);
                Toast.makeText(RegistroComunaActivity.this,"Comuna registrada correctamente!",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.getStackTrace();
        }

    }

    private void loadRegion(){
        final List<ModelRegion> regiones = new ArrayList<>();
        databaseReference.child("Region").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot oRegion : snapshot.getChildren()){
                        ModelRegion region = oRegion.getValue(ModelRegion.class);
                        String id = oRegion.getKey();
                        String nombre = region.getNombreRegion();
                        String numero = region.getNumeroRomano();
                        regiones.add(new ModelRegion(id,nombre,numero,false));
                    }

                    ArrayAdapter<ModelRegion> regionArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,regiones);
                    oSpinnerRegionComuna.setAdapter(regionArrayAdapter);
                    oSpinnerRegionComuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            regionSeleccionada = adapterView.getItemAtPosition(i).toString();
                            idRegion = regiones.get(i).getIdRegion();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}