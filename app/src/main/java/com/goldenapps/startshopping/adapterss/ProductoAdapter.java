package com.goldenapps.startshopping.adapterss;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelCarrito;
import com.goldenapps.startshopping.model.ModelComuna;
import com.goldenapps.startshopping.model.ModelItemCarrito;
import com.goldenapps.startshopping.model.ModelProducto;
import com.goldenapps.startshopping.model.ModelRegion;
import com.goldenapps.startshopping.model.ModelTallaProducto;
import com.goldenapps.startshopping.registros.RegistroComunaActivity;
import com.goldenapps.startshopping.registros.RegistroRegionActivity;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.goldenapps.startshopping.ui.productoDetalle.DetalleActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    DbHelper helper;
    SQLiteDatabase db;
    String idUser = "";
    String idCarrito;
    String tallaProducto;
    String idProductoTalla = "";
    int estadoFav = 0;

    Context context;
    ArrayList<ModelProducto> list;

    private DatabaseReference databaseReferenceCarrito;
    private DatabaseReference databaseReferenceItemCarrito;
    DatabaseReference databaseReferenceTalla = FirebaseDatabase.getInstance().getReference("TallaProducto");


    public ProductoAdapter(Context context, ArrayList<ModelProducto> list) {
        this.context = context;
        this.list = list;
        consultaUsuario(1);
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_producto_item_layout,parent,false);
        return  new ProductoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        ModelProducto user = list.get(position);
        holder.precio.setText("$"+Double.toString(user.getPrecioProducto()));
        holder.nombre.setText(user.getNombreProducto());
        Glide.with(context).load(user.getImagenProducto()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check(user.getIdProducto());
                context = holder.itemView.getContext();

                Intent i=new Intent(context.getApplicationContext(), DetalleActivity.class);
                i.putExtra("idPro",user.getIdProducto());
                i.putExtra("name", user.getNombreProducto());
                i.putExtra("descrip", user.getDescripcionProducto());
                i.putExtra("cantidad", user.getCantidadProducto());
                i.putExtra("price", user.getPrecioProducto());
                i.putExtra("image", user.getImagenProducto());


                if (user.getIdProducto().equals(getIdProductoTalla())){
                    i.putExtra("talla",getTallaProducto());
                }else{
                    i.putExtra("talla","");
                }
                context.startActivity(i);

            }
        });

        holder.imageCartProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!idUser.isEmpty()){
                    String idProducto = user.getIdProducto();
                    double precioProducto = user.getPrecioProducto();
                    String imagen = user.getImagenProducto();
                    String nombre = user.getNombreProducto();
                    //agregar carrito firebase
                    agregarCarrito1item(idProducto,precioProducto,imagen,nombre);
                }else {
                    Toast.makeText(context, "Necesita loguearse primero", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.imagenFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper = new DbHelper(context);
                db = helper.getWritableDatabase();
                String idU = getIdUser();
                String f = user.getIdProducto();
                ContentValues registroFav = new ContentValues();
                registroFav.put("idProducto",f);
                registroFav.put("idUsuario",idU);

                Cursor fila = db.rawQuery("SELECT FAVPRODUCTO WHERE IDPRODUCTO = '"+f+"'",null);

                if(fila.moveToFirst()){
                    setEstadoFav(1);
                }else{
                    db.insert("FAVPRODUCTO",null,registroFav);
                }
                db.close();
            }
        });
    }

    private void check(String id){
        databaseReferenceTalla.orderByChild("idProducto").equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    setTallaProductoVacio("");
                    for(DataSnapshot oCarrito : snapshot.getChildren()){
                        ModelTallaProducto tallaProducto = oCarrito.getValue(ModelTallaProducto.class);
                        String id = oCarrito.getKey();
                        setIdProductoTalla(tallaProducto.getIdProducto());
                        setTallaProducto(tallaProducto.getTalla()+" - ");
                    }
                }else{
                    setTallaProductoVacio("");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder{

        TextView precio, nombre;
        ImageView image,imageCartProducto, imagenFavorito;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);

            precio = itemView.findViewById(R.id.tvlastName);
            nombre = itemView.findViewById(R.id.tvage);
            image = itemView.findViewById(R.id.imageView5);
            imageCartProducto = itemView.findViewById(R.id.imageCarritoAdd_productoView);
            imagenFavorito = itemView.findViewById(R.id.imagenFav);

        }
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
                        double subtotal = precioProducto *1;
                        ModelItemCarrito modelItemCarrito = new ModelItemCarrito(idC, idProducto, 1,subtotal,precioProducto,imagen,nombre);
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

    private void registrarFav(String idProductoFav){

    }

    private void consultaProductoFav(String idProductoFav){
        helper = new DbHelper(context.getApplicationContext());
        db = helper.getWritableDatabase();
        Cursor fila = db.rawQuery("SELECT IDPRODUCTO FROM FAVPRODUCTO WHERE IDPRODUCTO = '"+idProductoFav+"'",null);

        if(fila.moveToFirst()){
            setEstadoFav(1);
        }else{
            setEstadoFav(0);
        }
        db.close();
    }

    private void eliminarDatos(String idProductoFav){
        helper = new DbHelper(context.getApplicationContext());
        db = helper.getWritableDatabase();

        int cantidad_filas;

        cantidad_filas = db.delete("FAVPRODUCTO","IDPRODUCTO = '"+idProductoFav+"'",null);
        if(cantidad_filas == 1){
        }else{
        }
        db.close();
    }

    private void consultaUsuario(int id){
        helper = new DbHelper(context.getApplicationContext());
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

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getTallaProducto() {
        return tallaProducto;
    }

    public void setTallaProducto(String tallaProducto) {
        this.tallaProducto = this.tallaProducto + tallaProducto;
    }

    public void setTallaProductoVacio(String tallaProducto) {
        this.tallaProducto = tallaProducto;
    }

    public String getIdProductoTalla() {
        return idProductoTalla;
    }

    public void setIdProductoTalla(String idProductoTalla) {
        this.idProductoTalla = idProductoTalla;
    }

    public int getEstadoFav() {
        return estadoFav;
    }

    public void setEstadoFav(int estadoFav) {
        this.estadoFav = estadoFav;
    }
}
