package com.goldenapps.startshopping.ui;

import android.content.Intent;
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

public class AdminFragment extends Fragment {


    String idUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewAdmin = inflater.inflate(R.layout.fragment_admin, container, false);

        TextView t = viewAdmin.findViewById(R.id.textView3);
        Bundle b = getArguments();
        idUser = b.getString("idUsuario1");
        t.setText(idUser);

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
                startActivity(main);
                getActivity().finishAffinity();
            }
        });

        // Inflate the layout for this fragment
        return viewAdmin;
    }
}