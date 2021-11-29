package com.goldenapps.startshopping.adapterss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelCarrito;
import com.goldenapps.startshopping.model.ModelItemCarrito;
import com.goldenapps.startshopping.model.ModelProducto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>{

    private Context context;
    private ArrayList<ModelItemCarrito> listItems;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ItemCarrito");
    private DatabaseReference databaseReferenceProducto = FirebaseDatabase.getInstance().getReference("Productos");
    private String imagen;
    private ArrayList<ModelProducto> list;

    public CarritoAdapter(Context context, ArrayList<ModelItemCarrito> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_item_carrito,parent,false);
        return  new CarritoAdapter.CarritoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        ModelItemCarrito user = listItems.get(position);
        holder.nombre.setText(user.getNombre());
        holder.cantidad.setText("Cantidad: "+Integer.toString(user.getCantidadItem()));
        holder.subTotal.setText("SubTotal: $"+Double.toString(user.getSubTotalItem()));
        Glide.with(context).load(user.getImagen()).into(holder.imagenProductoCarrito);

        holder.imageDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(user.getIdItemCarrito()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class CarritoViewHolder extends RecyclerView.ViewHolder {
        TextView nombre,cantidad,subTotal;
        ImageView imagenProductoCarrito,imageDeleteItem;

        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.tv_nombreProductoInCarrito);
            cantidad = itemView.findViewById(R.id.tv_cantidadProductoInCarrito);
            subTotal = itemView.findViewById(R.id.tv_subTotalInCarrito);
            imagenProductoCarrito = itemView.findViewById(R.id.imagenProductoInCarrito);
            imageDeleteItem = itemView.findViewById(R.id.deleteProductoInCarrito);

        }
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
