package com.goldenapps.startshopping.registros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.goldenapps.startshopping.R;

public class SettingsFirebaseActivity extends AppCompatActivity {

    VerProductosFragment verProductosFragment = new VerProductosFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_settings_firebase);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_containerSettings, verProductosFragment);
        transaction.commit();

    }
}