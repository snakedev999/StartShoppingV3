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
    BlankFragment blankFragment = new BlankFragment();

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

    private void blank(){
        FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
        transaction1.replace(R.id.frame_container_account, blankFragment);
        transaction1.addToBackStack(getContext().getClass().getName());
        transaction1.commit();
    }

    public void act2(View View) {
        Intent act = new Intent(getActivity(), RegisterFragment.class);
        startActivity(act);
        getActivity().overridePendingTransition(R.anim.translate_left_side, 0);
    }

    public void cambio(View View) {
        Intent info = new Intent(getActivity(),LoginFragment.class);
        startActivity(info);
        getActivity().overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
    }

    public void onBackPressed() {
        super.getActivity().onBackPressed();
        getActivity().overridePendingTransition(R.anim.zoom_in, 0);
    }
}