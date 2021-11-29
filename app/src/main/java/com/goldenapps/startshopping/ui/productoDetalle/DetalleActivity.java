package com.goldenapps.startshopping.ui.productoDetalle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.carrito.CarritoActivity;
import com.goldenapps.startshopping.model.ModelProducto;
import com.goldenapps.startshopping.model.ModelPuntajeProducto;
import com.goldenapps.startshopping.model.ModelTallaProducto;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class DetalleActivity extends AppCompatActivity {

    ImageView img, back, k;
    TextView proName, proPrice, proDesc,proPuntaje,proCantidad, proTalla;
    RatingBar ratingBar;
    EditText tallaDeseda;
    double ratingTotal;
    private DbHelper helper;
    private SQLiteDatabase db;
    private String idUser = "";
    TextView textRating;
    private ArrayList<ModelPuntajeProducto> listPuntajeProducto;
    private DatabaseReference databaseReferencePuntaje = FirebaseDatabase.getInstance().getReference("PuntajeProducto");

    String idPro,name, descrip,image,talla;
    int cantidad;
    double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detalle);
        listPuntajeProducto = new ArrayList<>();
        consultaUsuario(1);

        Intent i = getIntent();

        idPro = i.getStringExtra("idPro");
        name = i.getStringExtra("name");
        image = i.getStringExtra("image");
        descrip = i.getStringExtra("descrip");
        cantidad = i.getIntExtra("cantidad",0);
        price = i.getDoubleExtra("price",0.0);
        talla = i.getStringExtra("talla");

        proName = findViewById(R.id.productName);
        proDesc = findViewById(R.id.prodDesc);
        proPrice = findViewById(R.id.prodPrice);
        proPuntaje = findViewById(R.id.puntaje);
        proCantidad = findViewById(R.id.tv_cantidadProducto);
        proTalla = findViewById(R.id.textView9);
        textRating = findViewById(R.id.puntaje);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tallaDeseda = findViewById(R.id.edt_tallaDeseada);
        String s = String.valueOf(ratingBar.getRating());
        textRating.setText(s);

        img = findViewById(R.id.big_image);
        back = findViewById(R.id.back2);
        k = findViewById(R.id.cart123);

        proName.setText(name);
        proPrice.setText("Precio: $"+price);
        proDesc.setText(descrip);
        proCantidad.setText("Cantidad: "+cantidad);
        proTalla.setText("Talla: "+talla);

        if (!talla.equals("")){
            proTalla.setVisibility(View.VISIBLE);
            tallaDeseda.setVisibility(View.VISIBLE);
        }else{
            proTalla.setVisibility(View.INVISIBLE);
            tallaDeseda.setVisibility(View.INVISIBLE);
        }


        Glide.with(getApplicationContext()).load(image).into(img);


        databaseReferencePuntaje.orderByChild("idProducto").equalTo(idPro).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    listPuntajeProducto.removeAll(listPuntajeProducto);
                    for(DataSnapshot oPuntaje : snapshot.getChildren()){
                        ModelPuntajeProducto puntajeProducto = oPuntaje.getValue(ModelPuntajeProducto.class);
                        String idPuntaje = oPuntaje.getKey();
                        String idProducto = puntajeProducto.getIdProducto();
                        String idUsuario = puntajeProducto.getIdUsuario();
                        double puntaje = puntajeProducto.getPuntajeProducto();
                        listPuntajeProducto.add(new ModelPuntajeProducto(idPuntaje,idProducto,idUsuario,puntaje));
                    }

                    for (ModelPuntajeProducto modelPuntajeProducto : listPuntajeProducto){
                        if (modelPuntajeProducto.getIdProducto().equals(idPro)) {
                            ratingTotal = ratingTotal + modelPuntajeProducto.getPuntajeProducto();
                        }
                    }
                    int sizeArray = listPuntajeProducto.size();
                    double rating = ratingTotal /sizeArray;
                    String s = String.valueOf(rating);
                    textRating.setText(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(!getIdUser().equals("")){
                    listPuntajeProducto.removeAll(listPuntajeProducto);
                    databaseReferencePuntaje.orderByChild("idUsuario").equalTo(getIdUser()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                for(DataSnapshot oPuntaje : snapshot.getChildren()){
                                    ModelPuntajeProducto puntajeProducto = oPuntaje.getValue(ModelPuntajeProducto.class);
                                    String idPuntaje = oPuntaje.getKey();
                                    String idProducto = puntajeProducto.getIdProducto();
                                    String idUsuario = puntajeProducto.getIdUsuario();
                                    double puntaje = puntajeProducto.getPuntajeProducto();
                                    listPuntajeProducto.add(new ModelPuntajeProducto(idPuntaje,idProducto,idUsuario,puntaje));
                                }

                                for (ModelPuntajeProducto modelPuntajeProducto : listPuntajeProducto){
                                    if (modelPuntajeProducto.getIdProducto().equals(idPro) && modelPuntajeProducto.getIdUsuario().equals(getIdUser())) {
                                        String idPro = modelPuntajeProducto.getIdProducto();
                                        String idUser = modelPuntajeProducto.getIdUsuario();
                                        double puntaje = ratingBar.getRating();

                                        ModelPuntajeProducto modelPuntajeProducto1 = new ModelPuntajeProducto(idPro,idUser,puntaje);
                                        databaseReferencePuntaje.child(modelPuntajeProducto.getIdPuntaje()).setValue(modelPuntajeProducto1);
                                    }
                                }
                                String s = String.valueOf(ratingBar.getRating());
                                textRating.setText(s);
                            }else{
                                String idTalla = databaseReferencePuntaje.push().getKey();
                                if(idTalla != null){
                                    ModelPuntajeProducto modelPuntajeProducto = new ModelPuntajeProducto(idPro,idUser,ratingBar.getRating());
                                    databaseReferencePuntaje.child(idTalla).setValue(modelPuntajeProducto);
                                    String s = String.valueOf(ratingBar.getRating());
                                    textRating.setText(s);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DetalleActivity.this, MenuActivity.class);
                startActivity(i);
                finish();

            }
        });


        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(), CarritoActivity.class);
                startActivity(in);
            }
        });
    }

    private void consultaUsuario(int id){
        helper = new DbHelper(getApplicationContext());
        db = helper.getWritableDatabase();

        Cursor fila = db.rawQuery("SELECT IDUSUARIO FROM USUARIO WHERE ID = '"+id+"'",null);

        if(fila.moveToFirst()){
            setIdUser(fila.getString(0));
        }else{
            setIdUser("");
        }
        db.close();
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}