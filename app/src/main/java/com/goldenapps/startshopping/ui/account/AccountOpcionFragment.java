package com.goldenapps.startshopping.ui.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.goldenapps.startshopping.R;

public class AccountOpcionFragment extends Fragment {

    LoginFragment loginFragment = new LoginFragment();
    RegisterFragment registerFragment = new RegisterFragment();

    public AccountOpcionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragmentBlank= inflater.inflate(R.layout.fragment_account_opcion, container, false);

        Button btn = viewFragmentBlank.findViewById(R.id.button3);
        Button btn2 = viewFragmentBlank.findViewById(R.id.button4);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out);
                transaction.replace(R.id.frame_container_account, loginFragment);
                transaction.addToBackStack(getContext().getClass().getName());
                transaction.commit();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out);
                transaction.replace(R.id.frame_container_account, registerFragment);
                transaction.addToBackStack(getContext().getClass().getName());
                transaction.commit();
            }
        });
        // Inflate the layout for this fragment
        return viewFragmentBlank;
    }

    public void onBackPressed() {
        super.getActivity().onBackPressed();
        getActivity().overridePendingTransition(R.anim.zoom_in, 0);
    }
}