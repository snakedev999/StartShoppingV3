package com.goldenapps.startshopping.carrito.process;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.carrito.SetupActivity;
import com.goldenapps.startshopping.model.ModelComuna;
import com.goldenapps.startshopping.model.ModelDomicilio;
import com.goldenapps.startshopping.model.ModelRegion;
import com.goldenapps.startshopping.registros.EditProductoActivity;
import com.goldenapps.startshopping.registros.RegistroComunaActivity;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShippFragment extends Fragment {

    private DbHelper helper;
    private SQLiteDatabase db;
    private String idUser;

    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceComuna;
    private DatabaseReference databaseReferenceDomicilio;
    private Spinner oSpinnerRegiondComuna,oSpinnerComunadRegion;
    private List<ModelComuna> comunas;
    private String idRegionComuna;
    private Button procesoPago;
    private EditText edt_RutReceptor,edt_nombreReceptor,edt_numeroTelefono,edt_direccionReceptor,edt_NumeroDomicilio;

    public ShippFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipp, container, false);

        consultaUsuario(1);
        edt_RutReceptor = view.findViewById(R.id.rutReceptor);
        edt_nombreReceptor = view.findViewById(R.id.nombreReceptor);
        edt_numeroTelefono = view.findViewById(R.id.numeroTelefono);
        edt_direccionReceptor = view.findViewById(R.id.direccionDomicilio);
        edt_NumeroDomicilio = view.findViewById(R.id.nDomicilio);
        procesoPago = view.findViewById(R.id.procesoPago);

        oSpinnerRegiondComuna = view.findViewById(R.id.spinnerRegiondComuna);
        oSpinnerComunadRegion = view.findViewById(R.id.spinnerComunasdRegion);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReferenceComuna = FirebaseDatabase.getInstance().getReference();
        databaseReferenceDomicilio = FirebaseDatabase.getInstance().getReference();
        loadRegionComuna();



        procesoPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditProductoActivity.class);
                startActivity(i);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void loadFragment(){
        guardarDatosDomicilio();
    }

    private void guardarDatosDomicilio(){
        String idUsuario = getIdUser();
        String idComuna = getIdRegionComuna();
        String direccion = edt_direccionReceptor.getText().toString();
        int numeroDomicilio = Integer.parseInt(edt_NumeroDomicilio.getText().toString());
        int numeroTelefono = Integer.parseInt(edt_numeroTelefono.getText().toString());
        String rutRecpetor = edt_RutReceptor.getText().toString();
        String nombreReceptor = edt_nombreReceptor.getText().toString();
        databaseReferenceDomicilio.child("Domicilio").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    ModelDomicilio modelDomicilio = new ModelDomicilio(idUsuario,idComuna,direccion,numeroDomicilio,numeroTelefono,rutRecpetor,nombreReceptor,0.0,0.0);
                    String modelId = databaseReferenceDomicilio.push().getKey();
                    if(modelId != null){
                        databaseReferenceDomicilio.child(modelId).setValue(modelDomicilio);
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

                    ArrayAdapter<ModelRegion> regionArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line,regiones);
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

                                        ArrayAdapter<ModelComuna> comunaArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line,comunas);
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

    public String getIdRegionComuna() {
        return idRegionComuna;
    }

    public void setIdRegionComuna(String idRegionComuna) {
        this.idRegionComuna = idRegionComuna;
    }
}