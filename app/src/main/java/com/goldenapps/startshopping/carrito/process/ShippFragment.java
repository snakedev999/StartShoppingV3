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

public class ShippFragment extends Fragment {


    public ShippFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipp, container, false);

        Button si = view.findViewById(R.id.sgteS);

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayFragment payF = new PayFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out);
                transaction.replace(R.id.fragmentView, payF);
                transaction.addToBackStack(getContext().getClass().getName());
                transaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}