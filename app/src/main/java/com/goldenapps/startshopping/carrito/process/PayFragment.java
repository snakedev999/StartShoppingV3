package com.goldenapps.startshopping.carrito.process;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.goldenapps.startshopping.R;

public class PayFragment extends Fragment {

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
        Button sgte =  view.findViewById(R.id.sgtePay);

        sgte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmFragment confirmFragment = new ConfirmFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out);
                transaction.replace(R.id.fragmentView, confirmFragment);
                transaction.addToBackStack(getActivity().getClass().getName());
                transaction.commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
