package com.goldenapps.startshopping.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UsuarioFragment extends Fragment {

    private SharedPreferences sharedPreferences3;
    private SharedPreferences.Editor editor3;
    private Boolean guardaIdBoolean;
    String idUser;
    TextView id;

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
        Bundle b = getArguments();
        idUser = b.getString("idUsuario1");
        id.setText(idUser);


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
                startActivity(mainMenu );
                getActivity().finishAffinity();
            }
        });

        // Inflate the layout for this fragment
        return blank;
    }
}