package com.goldenapps.startshopping.ui;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.goldenapps.startshopping.carrito.CarritoActivity;
import com.goldenapps.startshopping.carrito.SetupActivity;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UsuarioFragment extends Fragment {

    private DbHelper helper;
    private SQLiteDatabase db;
    private String idUser;
    private TextView id;

    private SharedPreferences sharedPreferences3;
    private SharedPreferences.Editor editor3;
    private Boolean guardaIdBoolean;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View blank = inflater.inflate(R.layout.fragment_usuario, container, false);

        id = blank.findViewById(R.id.textView);
        consultaUsuario(1);
        id.setText(getIdUser());


        Button kart = (Button) blank.findViewById(R.id.btnkart);
        kart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        Button btn = (Button) blank.findViewById(R.id.btnsetup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SetupActivity.class);
                startActivity(i);


            }
        });



        Button cart = (Button) blank.findViewById(R.id.btncarrito);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getActivity(), CarritoActivity.class);
                startActivity(cart);


            }
        });



        Button n2 = (Button) blank.findViewById(R.id.button2);
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenu = new Intent(getActivity(), MenuActivity.class);
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                mainMenu.putExtra("boolean",true);
                mainMenu.putExtra("boolean2", false);
                mainMenu.putExtra("credencial", true);

                eliminarDatos(1);

                startActivity(mainMenu );
                getActivity().finishAffinity();
            }
        });

        // Inflate the layout for this fragment
        return blank;
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

    private void eliminarDatos(int id){
        helper = new DbHelper(getActivity());
        db = helper.getWritableDatabase();

        int cantidad_filas;

        cantidad_filas = db.delete("USUARIO","ID = '"+id+"'",null);
        if(cantidad_filas == 1){
        }else{
        }
        db.close();
    }
}