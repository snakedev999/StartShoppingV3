package com.goldenapps.startshopping.carrito;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.carrito.process.ConfirmFragment;
import com.goldenapps.startshopping.carrito.process.PayFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainCarritoFragment extends Fragment {

    PayFragment payF = new PayFragment();
    ConfirmFragment confirmF = new ConfirmFragment();

    public MainCarritoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main_carrito, container, false);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out);
        transaction.commit();
        return view;
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectListener = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_shipp:

                return true;
            case R.id.navigation_payment:
                loadFragment(payF);
                return true;
            case R.id.navigation_confirm:
                loadFragment(confirmF);
                return true;

        }return false;
    };

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out);
        transaction.addToBackStack(getContext().getClass().getName());
        transaction.replace(R.id.fragmentViewProceso,fragment);
        transaction.commit();
    }
}