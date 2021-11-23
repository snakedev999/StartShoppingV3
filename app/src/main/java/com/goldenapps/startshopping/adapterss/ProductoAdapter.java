package com.goldenapps.startshopping.adapterss;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelProducto;
import com.goldenapps.startshopping.ui.productoDetalle.DetalleActivity;

import java.util.ArrayList;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    Context context;
    ArrayList<ModelProducto> list;


    public ProductoAdapter(Context context, ArrayList<ModelProducto> list) {
        this.context = context;
        this.list = list;
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder{

        TextView marca, precio, nombre;
        ImageView image;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);

            marca = itemView.findViewById(R.id.tvfirstName);
            precio = itemView.findViewById(R.id.tvlastName);
            nombre = itemView.findViewById(R.id.tvage);
            image = itemView.findViewById(R.id.imageView5);

        }
    }

}
