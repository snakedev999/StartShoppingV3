package com.goldenapps.startshopping.carrito.process;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelDomicilio;
import com.goldenapps.startshopping.model.ModelRegion;
import com.goldenapps.startshopping.registros.RegistroRegionActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PayFragment extends Fragment {

    DbHelper helper;
    SQLiteDatabase db;
    String idUser;

    private DatabaseReference databaseReferenceDomicilio;
    private String id,idComuna,nombre,direccion,rut;
    private int nDomi,nTelefono;
    private ArrayList<ModelDomicilio> listDomicilios;
    private Spinner spinnerDomicilio;
    private String idDomicilio;

    public PayFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pay, container, false);
        spinnerDomicilio = view.findViewById(R.id.spinnerDomicilio);
        listDomicilios = new ArrayList<>();
        databaseReferenceDomicilio = FirebaseDatabase.getInstance().getReference();

        getParentFragmentManager().setFragmentResultListener("infoCliente",getActivity(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                id = result.getString("idUsuario");
                idComuna = result.getString("idComuna");
                direccion = result.getString("dir");
                nDomi = result.getInt("nDomi");
                nTelefono = result.getInt("numeroTelefono");
                rut = result.getString("rut");
                nombre = result.getString("nombre");
            }
        });
        Button sgte =  view.findViewById(R.id.sgtePay);

        sgte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatosDomicilio();
                ConfirmFragment confirmFragment = new ConfirmFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out);
                transaction.replace(R.id.fragmentViewProceso, confirmFragment);
                transaction.addToBackStack(getActivity().getClass().getName());
                transaction.commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }



    private void loadDomicilio(){
        final List<ModelRegion> regiones = new ArrayList<>();
        databaseReferenceDomicilio.child("Domicilio").orderByChild("idUsuario").equalTo(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot oDomicilio : snapshot.getChildren()){
                        ModelDomicilio domicilio = oDomicilio.getValue(ModelDomicilio.class);
                        String id = oDomicilio.getKey();
                        String dire = domicilio.getDireccionDomicilio();
                        listDomicilios.add(new ModelDomicilio(id,dire));
                    }

                    ArrayAdapter<ModelRegion> regionArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line,regiones);
                    spinnerDomicilio.setAdapter(regionArrayAdapter);
                    spinnerDomicilio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            idDomicilio = regiones.get(i).getIdRegion();
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

    private void guardarDatosDomicilio(){

    }

    private void guardarTarjeta(){
        databaseReferenceDomicilio.child("Tarjeta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    ModelDomicilio modelDomicilio = new ModelDomicilio(id,idComuna,direccion,nDomi,nTelefono,rut,nombre,0.0,0.0);
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
}
