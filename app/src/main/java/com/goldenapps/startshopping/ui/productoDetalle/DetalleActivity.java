package com.goldenapps.startshopping.ui.productoDetalle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.carrito.CarritoActivity;
import com.goldenapps.startshopping.model.ModelCarrito;
import com.goldenapps.startshopping.model.ModelItemCarrito;
import com.goldenapps.startshopping.model.ModelProducto;
import com.goldenapps.startshopping.model.ModelPuntajeProducto;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetalleActivity extends AppCompatActivity {

    private ImageView img, back, k;
    private TextView proName, proPrice, proDesc,proPuntaje,proCantidad, proTalla;
    private RatingBar ratingBar;
    private EditText tallaDeseda;
    private FloatingActionButton fl_carrito;
    private DbHelper helper;
    private SQLiteDatabase db;
    private String idUser = "";
    private String idCarrito;
    private TextView textRating;
    private Button mProducto,rProducto;
    private TextView cantidadDeseada;
    int cantidadDeseadaInt = 1;
    private String v;
    int cantidadDeseadaEnviar;
    private String idProductoPuntajeQryUsuario;
    private String idUsuarioPuntajeQryUsuario;
    private String idProductoPuntajeQryProducto,idUsuarioPuntajeQryProducto;
    private String idPuntajeQryProducto;
    private String idPuntajeQryUsuario;
    private boolean bandera;
    private ArrayList<ModelPuntajeProducto> listPuntajeProductoUsuario;
    private ArrayList<ModelPuntajeProducto> listPuntajeProducto;
    private ArrayList<ModelPuntajeProducto> listPuntaje;
    private DatabaseReference databaseReferenceCarrito;
    private DatabaseReference databaseReferenceItemCarrito;
    private DatabaseReference databaseReferencePuntaje = FirebaseDatabase.getInstance().getReference();

    String idPro,name, descrip,image,talla;
    int cantidad;
    double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detalle);
        listPuntajeProductoUsuario = new ArrayList<>();
        listPuntajeProducto = new ArrayList<>();
        listPuntaje = new ArrayList<>();
        consultaUsuario(1);

        idPro = "";

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
        mProducto = findViewById(R.id.btn_mProducto);
        rProducto = findViewById(R.id.btn_rProducto);
        cantidadDeseada = findViewById(R.id.tv_cantidadDesseada);
        fl_carrito = findViewById(R.id.fl_carrito);
        rProducto.setEnabled(false);
        cantidadDeseadaEnviar = 1;

        mProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidadDeseadaInt = cantidadDeseadaInt + 1;

                if(cantidadDeseadaInt>=cantidad){
                    rProducto.setEnabled(true);
                    mProducto.setEnabled(false);
                    cantidadDeseada.setText(Integer.toString(cantidadDeseadaInt));
                    cantidadDeseadaEnviar = cantidadDeseadaInt;
                }else{
                    rProducto.setEnabled(true);
                    mProducto.setEnabled(true);
                    cantidadDeseada.setText(Integer.toString(cantidadDeseadaInt));
                    cantidadDeseadaEnviar = cantidadDeseadaInt;
                }
            }
        });

        rProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidadDeseadaInt = cantidadDeseadaInt - 1;
                if(cantidadDeseadaInt<=1){
                    rProducto.setEnabled(false);
                    mProducto.setEnabled(true);
                    cantidadDeseada.setText(Integer.toString(cantidadDeseadaInt));
                    cantidadDeseadaEnviar = cantidadDeseadaInt;
                }else if (cantidadDeseadaInt > cantidad){
                    rProducto.setEnabled(true);
                    mProducto.setEnabled(false);
                    cantidadDeseada.setText(Integer.toString(cantidadDeseadaInt));
                    cantidadDeseadaEnviar = cantidadDeseadaInt;
                }else if (cantidadDeseadaInt < cantidad && cantidadDeseadaInt >= 1){
                    rProducto.setEnabled(true);
                    mProducto.setEnabled(true);
                    cantidadDeseada.setText(Integer.toString(cantidadDeseadaInt));
                    cantidadDeseadaEnviar = cantidadDeseadaInt;
                }
            }
        });

        fl_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!idUser.isEmpty()){
                    String idProducto = idPro;
                    double precioProducto = price;
                    String imagen = image;
                    String nombre = name;
                    //agregar carrito firebase
                    Toast.makeText(getApplicationContext(),"Productos agregados!",Toast.LENGTH_SHORT).show();
                    agregarCarrito1item(idProducto,precioProducto,imagen,nombre);
                }else {
                    dialog();
                }
            }
        });

        String s = String.valueOf(ratingBar.getRating());
        textRating.setText(s);

        img = findViewById(R.id.big_image);
        back = findViewById(R.id.back2);
        k = findViewById(R.id.imagenViewCarritoDetalle);

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

        databaseReferencePuntaje = FirebaseDatabase.getInstance().getReference("PuntajeProducto");
        databaseReferencePuntaje.orderByChild("idProducto").equalTo(idPro).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    double ratingTotal = 0.0;
                    double rnting;
                    listPuntaje.removeAll(listPuntaje);
                    for(DataSnapshot oPuntaje : snapshot.getChildren()){
                        ModelPuntajeProducto puntajeProducto = oPuntaje.getValue(ModelPuntajeProducto.class);
                        String idPuntaje = oPuntaje.getKey();
                        String idProducto = puntajeProducto.getIdProducto();
                        String idUsuario = puntajeProducto.getIdUsuario();
                        double puntaje = puntajeProducto.getPuntajeProducto();
                        listPuntaje.add(new ModelPuntajeProducto(idPuntaje,idProducto,idUsuario,puntaje));
                    }
                    for (ModelPuntajeProducto modelPuntajeProducto : listPuntaje){
                        if (modelPuntajeProducto.getIdProducto().equals(idPro)) {
                            rnting = modelPuntajeProducto.getPuntajeProducto();
                            ratingTotal = ratingTotal + rnting;
                        }
                    }
                    int sizeArray = listPuntaje.size();
                    double rating = ratingTotal / sizeArray;
                    String s = Double.toString(rating);
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
                    listPuntajeProductoUsuario.removeAll(listPuntajeProductoUsuario);
                    databaseReferencePuntaje = FirebaseDatabase.getInstance().getReference("PuntajeProducto");
                    databaseReferencePuntaje.orderByChild("idUsuario").equalTo(getIdUser()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                for (DataSnapshot oPuntaje : snapshot.getChildren()) {
                                    ModelPuntajeProducto puntajeProducto = oPuntaje.getValue(ModelPuntajeProducto.class);
                                    String idPuntaje = oPuntaje.getKey();
                                    String idProducto = puntajeProducto.getIdProducto();
                                    String idUsuario = puntajeProducto.getIdUsuario();
                                    double puntaje = puntajeProducto.getPuntajeProducto();
                                    listPuntajeProductoUsuario.add(new ModelPuntajeProducto(idPuntaje, idProducto, idUsuario, puntaje));
                                }

                                for (ModelPuntajeProducto modelPuntajeProducto : listPuntajeProductoUsuario){
                                    if (!modelPuntajeProducto.getIdProducto().equals(idPro) && !modelPuntajeProducto.getIdUsuario().equals(idUser)) {
                                        bandera = false;
                                        break;
                                    }
                                }

                                databaseReferencePuntaje = FirebaseDatabase.getInstance().getReference("PuntajeProducto");
                                databaseReferencePuntaje.orderByChild("idProducto").equalTo(idPro).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            for (DataSnapshot oPuntaje : snapshot.getChildren()) {
                                                ModelPuntajeProducto puntajeProducto = oPuntaje.getValue(ModelPuntajeProducto.class);
                                                String idPuntaje = oPuntaje.getKey();
                                                String idProducto = puntajeProducto.getIdProducto();
                                                String idUsuario = puntajeProducto.getIdUsuario();
                                                double puntaje = puntajeProducto.getPuntajeProducto();
                                                listPuntajeProducto.add(new ModelPuntajeProducto(idPuntaje, idProducto, idUsuario, puntaje));
                                            }
                                            for (ModelPuntajeProducto modelPuntajeProducto : listPuntajeProducto){
                                                if (modelPuntajeProducto.getIdProducto().equals(idPro)){
                                                    idPuntajeQryProducto = modelPuntajeProducto.getIdPuntaje();
                                                    idUsuarioPuntajeQryProducto = modelPuntajeProducto.getIdPuntaje();
                                                    idProductoPuntajeQryProducto = modelPuntajeProducto.getIdProducto();
                                                }
                                            }

                                            for (ModelPuntajeProducto modelPuntajeProducto1 : listPuntajeProductoUsuario){
                                                if (modelPuntajeProducto1.getIdUsuario().equals(idUser)){
                                                    idPuntajeQryUsuario = modelPuntajeProducto1.getIdPuntaje();
                                                    idUsuarioPuntajeQryUsuario = modelPuntajeProducto1.getIdPuntaje();
                                                    idProductoPuntajeQryUsuario = modelPuntajeProducto1.getIdProducto();
                                                }
                                            }

                                            if (idPuntajeQryProducto.equals(idPuntajeQryUsuario)) {
                                                databaseReferencePuntaje = FirebaseDatabase.getInstance().getReference("PuntajeProducto");
                                                double puntaje = ratingBar.getRating();
                                                ModelPuntajeProducto modelPuntajeProducto1 = new ModelPuntajeProducto(idPro,idUser,puntaje);
                                                databaseReferencePuntaje.child(idPuntajeQryProducto).setValue(modelPuntajeProducto1);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }else{
                                databaseReferencePuntaje = FirebaseDatabase.getInstance().getReference("PuntajeProducto");
                                double puntaje = ratingBar.getRating();
                                try {
                                    ModelPuntajeProducto modelPuntajeProducto3 = new ModelPuntajeProducto(idPro,idUser,puntaje);
                                    String modelId = databaseReferencePuntaje.push().getKey();
                                    if(modelId != null){
                                        databaseReferencePuntaje.child(modelId).setValue(modelPuntajeProducto3);
                                    }
                                }catch (Exception e){
                                    e.getStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    if (!bandera){
                        databaseReferencePuntaje = FirebaseDatabase.getInstance().getReference("PuntajeProducto");
                        double puntaje = ratingBar.getRating();
                        try {
                            ModelPuntajeProducto modelPuntajeProducto3 = new ModelPuntajeProducto(idPro,idUser,puntaje);
                            String modelId = databaseReferencePuntaje.push().getKey();
                            if(modelId != null){
                                databaseReferencePuntaje.child(modelId).setValue(modelPuntajeProducto3);
                            }
                        }catch (Exception e){
                            e.getStackTrace();
                        }
                        bandera = true;
                    }
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
                if (!getIdUser().isEmpty()){
                    Intent in = new Intent(getApplicationContext(), CarritoActivity.class);
                    startActivity(in);
                }else{
                    dialog();
                }
            }
        });
    }

    private void dialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Importante");

        alertDialogBuilder
                .setMessage("Lo sentimos, no puede utilizar esta funcionalidad para hacerlo debe registrarse o iniciar sesión :(.")
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();

                    }
                }).create().show();
    }

    private void dialogMakt(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

        alertDialogBuilder.setTitle("Mi Dialogo");

        alertDialogBuilder
                .setMessage("Lo sentimos, no puede utilizar esta funcionalidad ingrese para hacerlo.")
                .setCancelable(false)
                .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        //Si la respuesta es afirmativa aquí agrega tu función a realizar.
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                }).create().show();
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

    public void agregarCarrito1item(String idProducto, Double precioProducto,String imagen, String nombre){
        databaseReferenceCarrito = FirebaseDatabase.getInstance().getReference("Carrito");
        databaseReferenceCarrito.orderByChild("idUsuarioCarrito").equalTo(getIdUser()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot oCarrito : snapshot.getChildren()){
                        ModelCarrito carrito = oCarrito.getValue(ModelCarrito.class);
                        String id = oCarrito.getKey();
                        setIdCarrito(id);
                    }

                    String idC = getIdCarrito();
                    databaseReferenceItemCarrito = FirebaseDatabase.getInstance().getReference("ItemCarrito");
                    try {
                        double subtotal = precioProducto *cantidadDeseadaEnviar;
                        ModelItemCarrito modelItemCarrito = new ModelItemCarrito(idC, idProducto, cantidadDeseadaEnviar,subtotal,precioProducto,imagen,nombre);
                        String modelIdItemCarrito = databaseReferenceItemCarrito.push().getKey();
                        if (modelIdItemCarrito != null) {
                            databaseReferenceItemCarrito.child(modelIdItemCarrito).setValue(modelItemCarrito);
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }

                }else{
                    databaseReferenceCarrito = FirebaseDatabase.getInstance().getReference("Carrito");
                    try {
                        ModelCarrito modelCarrito = new ModelCarrito(getIdUser(),0,0.0);
                        String modelId = databaseReferenceCarrito.push().getKey();
                        if(modelId != null){
                            databaseReferenceCarrito.child(modelId).setValue(modelCarrito);
                        }
                    }catch (Exception e){
                        e.getStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getIdProductoPuntajeQryUsuario() {
        return idProductoPuntajeQryUsuario;
    }

    public void setIdProductoPuntajeQryUsuario(String idProductoPuntajeQryUsuario) {
        this.idProductoPuntajeQryUsuario = idProductoPuntajeQryUsuario;
    }

    public String getIdUsuarioPuntajeQryUsuario() {
        return idUsuarioPuntajeQryUsuario;
    }

    public void setIdUsuarioPuntajeQryUsuario(String idUsuarioPuntajeQryUsuario) {
        this.idUsuarioPuntajeQryUsuario = idUsuarioPuntajeQryUsuario;
    }

    public String getIdProductoPuntajeQryProducto() {
        return idProductoPuntajeQryProducto;
    }

    public void setIdProductoPuntajeQryProducto(String idProductoPuntajeQryProducto) {
        this.idProductoPuntajeQryProducto = idProductoPuntajeQryProducto;
    }

    public String getIdUsuarioPuntajeQryProducto() {
        return idUsuarioPuntajeQryProducto;
    }

    public void setIdUsuarioPuntajeQryProducto(String idUsuarioPuntajeQryProducto) {
        this.idUsuarioPuntajeQryProducto = idUsuarioPuntajeQryProducto;
    }

    public String getIdPuntajeQryProducto() {
        return idPuntajeQryProducto;
    }

    public void setIdPuntajeQryProducto(String idPuntajeQryProducto) {
        this.idPuntajeQryProducto = idPuntajeQryProducto;
    }

    public String getIdPuntajeQryUsuario() {
        return idPuntajeQryUsuario;
    }

    public void setIdPuntajeQryUsuario(String idPuntajeQryUsuario) {
        this.idPuntajeQryUsuario = idPuntajeQryUsuario;
    }
}