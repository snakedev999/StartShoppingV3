package com.goldenapps.startshopping.adapterss;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.goldenapps.startshopping.activity.DetailActivity;
import com.goldenapps.startshopping.model.ItemList;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelCategoria;
import com.goldenapps.startshopping.model.ModelProducto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    Context context;
    ArrayList<ModelCategoria> list; //OriginalItems?
    private List<ModelCategoria> items;
    private List<ModelCategoria> originalItems;


    public RecyclerAdapter(Context context, ArrayList<ModelCategoria> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_view, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerHolder holder, final int position) {
        ModelCategoria categoria = list.get(position);
        holder.tvTitulo.setText(categoria.getNombreCategoria());
        Glide.with(context).load(categoria.getImagenCategoria()).into(holder.imgItem);


       /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.itemClick(item);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("itemDetail", item);
                holder.itemView.getContext().startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*
    public void filter(final String strSearch) {
        if (strSearch.length() == 0) {
            items.clear();
            items.addAll(originalItems);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                items.clear();
                List<ItemList> collect = originalItems.stream()
                        .filter(i -> i.getTitulo().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                items.addAll(collect);
            }
            else {
                items.clear();
                for (ItemList i : originalItems) {
                    if (i.getTitulo().toLowerCase().contains(strSearch)) {
                        items.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }*/


    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView tvTitulo;

        public RecyclerHolder(@NonNull View itemView_1) {
            super(itemView_1);
            imgItem = itemView.findViewById(R.id.imgIcon);
            tvTitulo = itemView.findViewById(R.id.nameCat);
        }
    }

    public interface RecyclerItemClick {
        void itemClick(ItemList item);
    }
}
