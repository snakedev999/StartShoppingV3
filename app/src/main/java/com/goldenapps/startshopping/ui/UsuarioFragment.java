package com.goldenapps.startshopping.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UsuarioFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View blank = inflater.inflate(R.layout.fragment_usuario, container, false);

        Button n2 = (Button) blank.findViewById(R.id.button2);
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(getActivity(), MenuActivity.class);
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                main.putExtra("boolean",true);
                main.putExtra("boolean2", false);
                main.putExtra("credencial", true);
                startActivity(main);
                getActivity().finishAffinity();
            }
        });

        // Inflate the layout for this fragment
        return blank;
    }
}