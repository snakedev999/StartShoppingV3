package com.goldenapps.startshopping.adapterss;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelProducto;
import com.goldenapps.startshopping.registros.EditProductoActivity;
import com.goldenapps.startshopping.ui.productoDetalle.DetalleActivity;

import java.util.ArrayList;

public class ProductoSettingsAdapter extends RecyclerView.Adapter<ProductoSettingsAdapter.ProductoSettingsViewHolder> {

    private Context context;
    ArrayList<ModelProducto> list;

    public ProductoSettingsAdapter(Context context, ArrayList<ModelProducto> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductoSettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_producto_item_settings,parent,false);
        return  new ProductoSettingsAdapter.ProductoSettingsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoSettingsViewHolder holder, int position) {
        ModelProducto user = list.get(position);
        holder.nombre.setText(user.getNombreProducto());
        Glide.with(context).load(user.getImagenProducto()).into(holder.imagenProducto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = holder.itemView.getContext();
                Intent i = new Intent(context.getApplicationContext(), EditProductoActivity.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductoSettingsViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        ImageView imagenProducto,imagenEditProducto;
        public ProductoSettingsViewHolder(@NonNull View itemView) {
            super(itemView);

            imagenProducto = itemView.findViewById(R.id.imageViewProductoSettings);
            nombre = itemView.findViewById(R.id.tv_nombreProductoEditSettings);
            imagenEditProducto = itemView.findViewById(R.id.imageView_productoEditSettings);
        }
    }
}
