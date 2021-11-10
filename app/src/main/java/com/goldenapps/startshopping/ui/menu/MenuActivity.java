package com.goldenapps.startshopping.ui.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.ui.AdminFragment;
import com.goldenapps.startshopping.ui.UsuarioFragment;
import com.goldenapps.startshopping.ui.account.AccountFragment;
import com.goldenapps.startshopping.ui.category.CategoryFragment;
import com.goldenapps.startshopping.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferences2;
    private SharedPreferences sharedPreferences3;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editor2;
    private SharedPreferences.Editor editor3;

    private Boolean aBoolean;
    private Boolean boolean2;
    private int tipoCuenta;
    private int tipoCuenta2;
    private Boolean credencial;
    private Boolean credencial2;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTipoCuenta2() {
        return tipoCuenta2;
    }

    public void setTipoCuenta2(int tipoCuenta2) {
        this.tipoCuenta2 = tipoCuenta2;
    }

    public int getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(int tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public int getNumeroVerificador() {
        return numeroVerificador;
    }

    public void setNumeroVerificador(int numeroVerificador) {
        this.numeroVerificador = numeroVerificador;
    }

    private int numeroVerificador = 0;



    HomeFragment homeFragment = new HomeFragment();
    CategoryFragment categoryFragment = new CategoryFragment();
    UsuarioFragment usuarioFragment = new UsuarioFragment();
    AdminFragment adminFragment = new AdminFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_menu);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectListener);

        inicializarPreferences();
        inicializarPreferences2();
        inicializarPreferences3();

        Intent mainMenu = getIntent();
        boolean2 = mainMenu.getBooleanExtra("boolean",true);
        aBoolean = mainMenu.getBooleanExtra("boolean2",true);
        credencial = mainMenu.getBooleanExtra("credencial",true);
        credencial2 = mainMenu.getBooleanExtra("credencial2",true);
        id = mainMenu.getStringExtra("idusuario");


        verificadorOpc(boolean2,aBoolean,credencial,credencial2);

        loadFragment(homeFragment);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(homeFragment);
                    return true;
                case R.id.navigation_category:
                    loadFragment(categoryFragment);
                    return true;
                case R.id.navigation_account:
                    if (numeroVerificador == 0) {
                        AccountFragment accountFragment = new AccountFragment();
                        loadFragment(accountFragment);
                    }else{
                        if (tipoCuenta == 1) {
                            Bundle bundle = new Bundle();
                            bundle.putString("idUsuario1",id);
                            usuarioFragment.setArguments(bundle);
                            loadFragment(usuarioFragment);
                        }
                        if (tipoCuenta2 == 1){
                            Bundle bundle = new Bundle();
                            bundle.putString("idUsuario1",id);
                            adminFragment.setArguments(bundle);
                            loadFragment(adminFragment);
                        }
                    }
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


    private void inicializarPreferences(){
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private void inicializarPreferences2(){
        sharedPreferences2 = this.getPreferences(Context.MODE_PRIVATE);
        editor2 = sharedPreferences2.edit();
    }

    private void inicializarPreferences3(){
        sharedPreferences3 = this.getPreferences(Context.MODE_PRIVATE);
        editor3 = sharedPreferences3.edit();
    }

    private void verificadorOpc(Boolean booleanResult,Boolean b, Boolean credencial,Boolean credencial2){
        setNumeroVerificador(this.sharedPreferences.getInt("llave",0));
        if(!booleanResult){
            setNumeroVerificador(1);
            guardarOpc();
            Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();
        }

        if(!b){
            setNumeroVerificador(0);
            guardarOpc();
        }

        setTipoCuenta(this.sharedPreferences2.getInt("a",0));
        if (!credencial){
            setTipoCuenta(1);
            guardarOpc2();
        }
        setTipoCuenta2(this.sharedPreferences3.getInt("b",0));
        if(!credencial2) {
            setTipoCuenta2(1);
            guardarOpc3();
        }
    }


    private void guardarOpc(){
        editor.putInt("llave",getNumeroVerificador());
        editor.apply();
    }

    private void guardarOpc2(){
        editor2.putInt("a",getTipoCuenta());
        editor2.apply();
        editor3.putInt("b",0);
        editor3.apply();
    }

    private void guardarOpc3(){
        editor2.putInt("a",0);
        editor2.apply();
        editor3.putInt("b",getTipoCuenta2());
        editor3.apply();
    }
}