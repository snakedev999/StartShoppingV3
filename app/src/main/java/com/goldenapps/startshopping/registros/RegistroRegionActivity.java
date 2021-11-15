package com.goldenapps.startshopping.registros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelRegion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegistroRegionActivity extends AppCompatActivity {

    TextView textView;
    String n;
    private Button registrarRegion;
    private ArrayList<ModelRegion> listRegion = new ArrayList<>();
    private ProgressBar progressBar_Region;
    private EditText nombreRegion,abreviaturaRegion,numeroRomanoRegion;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registro_region);

        nombreRegion = (EditText) findViewById(R.id.edt_nombreRegion);
        abreviaturaRegion = (EditText) findViewById(R.id.edt_abreviaturaRegion);
        numeroRomanoRegion = (EditText) findViewById(R.id.edt_numeroRomano);
        registrarRegion = (Button) findViewById(R.id.btn_registrarRegion);
        progressBar_Region = findViewById(R.id.progressBar_Region);
        textView = findViewById(R.id.textView7);

        progressBar_Region.setVisibility(View.INVISIBLE);

        databaseReference = FirebaseDatabase.getInstance().getReference("Region");
        registrarRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarRegion();
            }
        });
        actualiza();
    }

    private void actualiza() {
        n = "";
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelRegion region = dataSnapshot.getValue(ModelRegion.class);
                    listRegion.add(region);
                    n = n +"\n"+ region.toString();
                }
                textView.setText(n);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void enviarRegion(){
        try {
            String nombre,abreviatura,numero;
            nombre = nombreRegion.getText().toString();
            abreviatura = abreviaturaRegion.getText().toString();
            numero = numeroRomanoRegion.getText().toString();
            ModelRegion modelRegion = new ModelRegion(nombre,abreviatura,numero);
            String modelId = databaseReference.push().getKey();
            if(modelId != null){
                registrarRegion.setVisibility(View.INVISIBLE);
                progressBar_Region.setVisibility(View.VISIBLE);
                databaseReference.child(modelId).setValue(modelRegion);
                limpiar();
                actualiza();
                Toast.makeText(RegistroRegionActivity.this,"Region registrada correctamente!",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.getStackTrace();
        }

    }

    private void limpiar() {
        abreviaturaRegion.setText("");
        numeroRomanoRegion.setText("");
        nombreRegion.setText("");
        registrarRegion.setVisibility(View.VISIBLE);
        progressBar_Region.setVisibility(View.INVISIBLE);
    }

}