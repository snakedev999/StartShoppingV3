package com.goldenapps.startshopping.adapterss;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    Context context;
    ArrayList<ModelProducto> list;
    ArrayList<ModelCarrito> carritos;
    TextView textView;
    String n;
    private DatabaseReference databaseReferenceCarrito;
    private DatabaseReference databaseReferenceItemCarrito;


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
        holder.precio.setText(Double.toString(user.getPrecioProducto()));
        holder.nombre.setText(user.getNombreProducto());
        Glide.with(context).load(user.getImagenProducto()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context = holder.itemView.getContext();

                Intent i=new Intent(context.getApplicationContext(), DetalleActivity.class);
                i.putExtra("name", user.getNombreProducto());
                i.putExtra("descrip", user.getDescripcionProducto());
                i.putExtra("cantidad", user.getCantidadProducto());
                i.putExtra("price", user.getPrecioProducto());
                i.putExtra("image", user.getImagenProducto());

                context.startActivity(i);

            }
        });
        holder.imageCartProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!idUser.equals("")){
                    String idProducto = user.getIdProducto();
                    double precioProducto = user.getPrecioProducto();
                    //agregar carrito firebase
                    f(idProducto,precioProducto);
                }else {
                    Toast.makeText(context, "Necesita loguearse primero", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder{

        TextView marca, precio, nombre;
        ImageView image,imageCartProducto;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);

            marca = itemView.findViewById(R.id.tvfirstName);
            precio = itemView.findViewById(R.id.tvlastName);
            nombre = itemView.findViewById(R.id.tvage);
            image = itemView.findViewById(R.id.imageView5);
            imageCartProducto = itemView.findViewById(R.id.imageCarritoAdd_productoView);
        }
    }

    public void f(String idProducto, Double precioProducto){
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
                        ModelItemCarrito modelItemCarrito = new ModelItemCarrito(idC, idProducto, 1, precioProducto);
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
                        ModelCarrito modelCarrito = new ModelCarrito(getIdUser(),0.0);
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
}
