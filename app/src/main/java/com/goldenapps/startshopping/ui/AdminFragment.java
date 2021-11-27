package com.goldenapps.startshopping.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.activity.MapsActivity;
import com.goldenapps.startshopping.registros.RegistroCategoriaActivity;
import com.goldenapps.startshopping.registros.RegistroComunaActivity;
import com.goldenapps.startshopping.registros.RegistroProductoActivity;
import com.goldenapps.startshopping.registros.RegistroRegionActivity;
import com.goldenapps.startshopping.registros.SeleccionComunaActivity;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminFragment extends Fragment {


    DbHelper helper;
    SQLiteDatabase db;
    String idUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewAdmin = inflater.inflate(R.layout.fragment_admin, container, false);

        Button registroCategoria = viewAdmin.findViewById(R.id.btn_categoria);
        Button registroProducto = viewAdmin.findViewById(R.id.btn_producto);
        Button registroRegion = viewAdmin.findViewById(R.id.btn_region);
        Button spinner = viewAdmin.findViewById(R.id.button6);
        Button comuna = viewAdmin.findViewById(R.id.button7);
        Button mapa = viewAdmin.findViewById(R.id.btn_mapa1);

        TextView t = viewAdmin.findViewById(R.id.textView3);
        consultaUsuario(1);
        t.setText(getIdUser());


        Button salir = viewAdmin.findViewById(R.id.buttonLeave);
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(getActivity(), MenuActivity.class);
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                main.putExtra("boolean",true);
                main.putExtra("boolean2", false);
                main.putExtra("credencial2", true);
                main.putExtra("idusuario","");
                eliminarDatos(1);
                startActivity(main);
                getActivity().finishAffinity();
            }
        });

        registroCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainLogin = new Intent(getActivity(), RegistroCategoriaActivity.class);
                startActivity(mainLogin);
            }
        });

        registroProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainLogin = new Intent(getActivity(), RegistroProductoActivity.class);
                startActivity(mainLogin);
            }
        });

        registroRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainLogin = new Intent(getActivity(), RegistroRegionActivity.class);
                startActivity(mainLogin);
            }
        });

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainLogin = new Intent(getActivity(), RegistroComunaActivity.class);
                startActivity(mainLogin);
            }
        });

        comuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainLogin = new Intent(getActivity(), SeleccionComunaActivity.class);
                startActivity(mainLogin);
            }
        });

        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainLogin = new Intent(getActivity(), MapsActivity.class);
                startActivity(mainLogin);
            }
        });

        // Inflate the layout for this fragment
        return viewAdmin;
    }

    private void consultaUsuario(int id){
        helper = new DbHelper(getActivity());
        db = helper.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT IDUSUARIO FROM USUARIO WHERE ID = '"+id+"'",null);

        if(fila.moveToFirst()){
            setIdUser(fila.getString(0));

            Toast.makeText(getActivity(),"Consulta realizada correctamente",Toast.LENGTH_SHORT).show();
        }else{
            setIdUser("");
            Toast.makeText(getActivity(),"No se ha registrado el usuario ",Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

    private void eliminarDatos(int id){
        helper = new DbHelper(getActivity());
        db = helper.getWritableDatabase();

        int cantidad_filas;

        cantidad_filas = db.delete("USUARIO","ID = '"+id+"'",null);
        if(cantidad_filas == 1){
            Toast.makeText(getActivity(),"Los datos del usuario se han eliminado correctamente",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),"El usuario no esta registrado",Toast.LENGTH_SHORT).show();
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