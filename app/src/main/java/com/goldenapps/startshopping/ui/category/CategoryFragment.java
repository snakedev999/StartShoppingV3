package com.goldenapps.startshopping.ui.category;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.adapterss.ProductoAdapter;
import com.goldenapps.startshopping.adapterss.RecyclerAdapter;
import com.goldenapps.startshopping.model.ModelCategoria;
import com.goldenapps.startshopping.model.ModelProducto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference database;
    RecyclerAdapter recyclerAdapter;
    ArrayList<ModelCategoria> list;

    public CategoryFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView = view.findViewById(R.id.rvLista);
        database = FirebaseDatabase.getInstance().getReference("Categorias");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // Inflate the layout for this fragment

        list = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(getContext(),list);
        recyclerView.setAdapter(recyclerAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.removeAll(list);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    ModelCategoria categoria = dataSnapshot.getValue(ModelCategoria.class);
                    list.add(categoria);


                }
                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }



}


