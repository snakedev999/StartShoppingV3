package com.goldenapps.startshopping.carrito;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.carrito.process.ConfirmFragment;
import com.goldenapps.startshopping.carrito.process.PayFragment;
import com.goldenapps.startshopping.carrito.process.ShippFragment;
import com.goldenapps.startshopping.ui.AdminFragment;
import com.goldenapps.startshopping.ui.UsuarioFragment;
import com.goldenapps.startshopping.ui.account.AccountFragment;
import com.goldenapps.startshopping.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CompraActivity extends AppCompatActivity {


    ShippFragment shippFragment = new ShippFragment();
    PayFragment payFragment = new PayFragment();
    Fragment confirmFragment = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_compra);

        BottomNavigationView navigation = findViewById(R.id.top_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectListener);

        loadFragment(shippFragment);

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getOrder()) {
                case R.id.shipp:;
                    loadFragment(shippFragment);
                    return true;
                case R.id.payment:
                    loadFragment(payFragment);
                    return true;
                case R.id.confirm:
                    loadFragment(confirmFragment);
                    return true;

                    }

            return false;
        }
        };

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.commit();
    }
}

