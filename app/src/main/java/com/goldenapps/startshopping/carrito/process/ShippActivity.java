package com.goldenapps.startshopping.carrito.process;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelComuna;
import com.goldenapps.startshopping.model.ModelDomicilio;
import com.goldenapps.startshopping.model.ModelRegion;
import com.goldenapps.startshopping.registros.EditProductoActivity;
import com.goldenapps.startshopping.ui.productoDetalle.ProductoCategoriaActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShippActivity extends AppCompatActivity {

    private DbHelper helper;
    private SQLiteDatabase db;
    private String idUser;

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceComuna;
    private DatabaseReference databaseReferenceDomicilio;
    private Spinner oSpinnerRegiondComuna,oSpinnerComunadRegion;
    private List<ModelComuna> comunas;
    private ArrayList<ModelDomicilio> listDomicilio;
    private String idRegionComuna;
    private Boolean bandera;
    private ViewStub viewStub;
    private Button procesoPago;
    private EditText edt_RutReceptor,edt_nombreReceptor,edt_numeroTelefono,edt_direccionReceptor,edt_NumeroDomicilio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_shipp);

        consultaUsuario(1);
        edt_RutReceptor = findViewById(R.id.rutReceptor);
        edt_nombreReceptor = findViewById(R.id.nombreReceptor);
        edt_numeroTelefono = findViewById(R.id.numeroTelefono);
        edt_direccionReceptor = findViewById(R.id.direccionDomicilio);
        edt_NumeroDomicilio = findViewById(R.id.nDomicilio);
        procesoPago = findViewById(R.id.procesoPago);

        oSpinnerRegiondComuna = findViewById(R.id.spinnerRegiondComuna);
        oSpinnerComunadRegion = findViewById(R.id.spinnerComunasdRegion);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReferenceComuna = FirebaseDatabase.getInstance().getReference();
        databaseReferenceDomicilio = FirebaseDatabase.getInstance().getReference("Domicilio");
        loadRegionComuna();

        procesoPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idUsuario = getIdUser();
                String idComuna = getIdRegionComuna();
                String direccion = edt_direccionReceptor.getText().toString();
                int numeroDomicilio = Integer.parseInt(edt_NumeroDomicilio.getText().toString());
                int numeroTelefono = Integer.parseInt(edt_numeroTelefono.getText().toString());
                String rutRecpetor = edt_RutReceptor.getText().toString();
                String nombreReceptor = edt_nombreReceptor.getText().toString();
                Intent i =new Intent(getApplicationContext(), ProductoCategoriaActivity.class);
                i.putExtra("idUsuario", idUsuario);
                i.putExtra("idComuna", idComuna);
                i.putExtra("dir",direccion);
                i.putExtra("nDomi",numeroDomicilio);
                i.putExtra("nTelefono",numeroTelefono);
                i.putExtra("rut",rutRecpetor);
                i.putExtra("nombre",nombreReceptor);
                startActivity(i);
            }
        });
    }


    private void guardarDatosDomicilio(){

        databaseReferenceDomicilio.child("Domicilio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    //ModelDomicilio modelDomicilio = new ModelDomicilio(idUsuario,idComuna,direccion,numeroDomicilio,numeroTelefono,rutRecpetor,nombreReceptor,0.0,0.0);
                    String modelId = databaseReferenceDomicilio.push().getKey();
                    if(modelId != null){
                        //databaseReferenceDomicilio.child(modelId).setValue(modelDomicilio);
                    }
                }catch (Exception e){
                    e.getStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

                                                if (id != null){
                                                    setIdRegionComuna(comunas.get(i).getIdComuna());
                                                }
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

    private void consultaUsuario(int id){
        helper = new DbHelper(getApplicationContext());
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

    public String getIdRegionComuna() {
        return idRegionComuna;
    }

    public void setIdRegionComuna(String idRegionComuna) {
        this.idRegionComuna = idRegionComuna;
    }
}