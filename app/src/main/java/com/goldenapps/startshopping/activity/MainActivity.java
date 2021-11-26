package com.goldenapps.startshopping.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goldenapps.startshopping.MapMiLocalizacion;
import com.goldenapps.startshopping.MapsActivity1;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.adapterss.RecyclerAdapter;
import com.goldenapps.startshopping.model.ItemList;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    Button btn_tipo,btn_ubicacion,btn_sitio;//definimos los 3 botones que tenemos para nuestro mapa
    private RecyclerView rvLista;
    private SearchView svSearch;
    private RecyclerAdapter adapter;
    private List<ItemList> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.fragment_category);

        initViews();
        btn_sitio=(Button)findViewById(R.id.btn_sitio);// creamos un casting de tipo button y lo buscamos mediante su ID
        btn_ubicacion=(Button)findViewById(R.id.btn_ubicacion);
        btn_sitio.setOnClickListener(new View.OnClickListener() {//programamos el boton btn_sitio con el fin de que nos proporcione un mapa

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MapsActivity1.class);
                startActivity(intent);
            }
            //Metodo de Localizacion
            public void Milocalizacion(View v){
            Intent intent = new Intent(getApplicationContext(), MapMiLocalizacion.class);
            startActivity(intent);
            }
        });

    }

    private void initViews(){
        rvLista = findViewById(R.id.rvLista);
        svSearch = findViewById(R.id.svSearch);
    }




}

