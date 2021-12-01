package com.goldenapps.startshopping.carrito;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.carrito.process.ConfirmFragment;
import com.goldenapps.startshopping.carrito.process.PayFragment;
import com.goldenapps.startshopping.carrito.process.ShippFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainCarritoActivity extends AppCompatActivity {

    ShippFragment shippF = new ShippFragment();
    PayFragment payF = new PayFragment();
    ConfirmFragment confirmF = new ConfirmFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_carrito);

        BottomNavigationView navigation= findViewById(R.id.bottom_navigationk);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectListener);

        loadFragment(shippF);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.shipp:
                            loadFragment(shippF);
                            return true;
                        case R.id.payment:
                            loadFragment(payF);
                            return true;
                        case R.id.confirm:
                            loadFragment(confirmF);
                            return true;

                    }return false;
                }
            };

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentview,fragment);
        transaction.commit();
    }



}

