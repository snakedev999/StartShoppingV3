package com.goldenapps.startshopping.carrito.process;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.Fecha;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelDomicilio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity {

    DbHelper helper;
    SQLiteDatabase db;
    String idUser;

    private DatabaseReference databaseReferenceDomicilio;
    private Spinner oSpinnerDomicilio;
    private String regionSeleccionada;
    private String idDomicilio;
    private EditText venci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pay);

        consultaUsuario(1);
        oSpinnerDomicilio = findViewById(R.id.spinnerDomicilio);
        venci = findViewById(R.id.vencimiento);
        databaseReferenceDomicilio = FirebaseDatabase.getInstance().getReference();

        loadDomicilio();

        venci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        Button sgte =  findViewById(R.id.sgtePay);

        sgte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }


    private void showDatePickerDialog() {
        Fecha newFragment = Fecha.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                venci.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void loadDomicilio() {
        final List<ModelDomicilio> domicilios = new ArrayList<>();
        databaseReferenceDomicilio.child("Domicilio").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot oRegion : snapshot.getChildren()) {
                        ModelDomicilio modelDomicilio = oRegion.getValue(ModelDomicilio.class);
                        String id = oRegion.getKey();
                        String nombre = modelDomicilio.getDireccionDomicilio();
                        domicilios.add(new ModelDomicilio(id,nombre));
                    }

                    ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,domicilios);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    oSpinnerDomicilio.setAdapter(aa);
                    oSpinnerDomicilio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            regionSeleccionada = adapterView.getItemAtPosition(i).toString();
                            idDomicilio = domicilios.get(i).getIdDomicilio();
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
}