package com.goldenapps.startshopping.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldenapps.startshopping.R;

public class AccountFragment extends Fragment {

    private AccountOpcionFragment accountOpcionFragment = new AccountOpcionFragment();

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFragmentAccount = inflater.inflate(R.layout.activity_setup, container, false);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_account, accountOpcionFragment);
        transaction.commit();

        // Inflate the layout for this fragment
        return viewFragmentAccount;
    }
}