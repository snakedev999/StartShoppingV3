package com.goldenapps.startshopping.registros;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.load.resource.gif.StreamGifDecoder;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelComuna;
import com.goldenapps.startshopping.model.ModelRegion;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SeleccionComunaActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceComuna;
    TextView txt;
    Spinner oSpinnerRegiondComuna,oSpinnerComunadRegion;
    String idRegionComuna;
    List<ModelComuna> comunas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_comuna);

        oSpinnerRegiondComuna = findViewById(R.id.spinnerRegiondComuna);
        oSpinnerComunadRegion = findViewById(R.id.spinnerComunasdRegion);
        txt = findViewById(R.id.tv_spinnerRegionComuna1);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReferenceComuna = FirebaseDatabase.getInstance().getReference();

        loadRegionComuna();
    }

    private void loadRegionComuna(){
        List<ModelRegion> regiones = new ArrayList<>();
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
                    oSpinnerRegiondComuna.setAdapter(regionArrayAdapter);
                    oSpinnerRegiondComuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String idRegionComuna = regiones.get(i).getIdRegion();
                            comunas = new ArrayList<>();
                            databaseReferenceComuna.child("Comuna").orderByChild("idRegionComuna").equalTo(idRegionComuna).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        for(DataSnapshot oComuna : snapshot.getChildren()){
                                            ModelComuna comuna = oComuna.getValue(ModelComuna.class);
                                            String id = oComuna.getKey();
                                            String nombre = comuna.getNombreComuna();
                                            comunas.add(new ModelComuna(id,nombre,false));
                                        }

                                        Collections.sort(comunas, new Comparator<ModelComuna>() {
                                            @Override
                                            public int compare(ModelComuna modelComuna, ModelComuna t1) {
                                                return modelComuna.getNombreComuna().compareTo(t1.getNombreComuna());
                                            }
                                        });

                                        ArrayAdapter<ModelComuna> comunaArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,comunas);
                                        oSpinnerComunadRegion.setAdapter(comunaArrayAdapter);
                                        oSpinnerComunadRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                String comuna = adapterView.getItemAtPosition(i).toString();
                                                String id = comunas.get(i).getIdComuna();
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