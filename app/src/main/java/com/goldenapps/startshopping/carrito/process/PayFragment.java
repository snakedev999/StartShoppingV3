package com.goldenapps.startshopping.carrito.process;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelDomicilio;
import com.goldenapps.startshopping.registros.RegistroRegionActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PayFragment extends Fragment {

    private DatabaseReference databaseReferenceDomicilio;
    private String id,idComuna,nombre,direccion,rut;
    private int nDomi,nTelefono;

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
}
