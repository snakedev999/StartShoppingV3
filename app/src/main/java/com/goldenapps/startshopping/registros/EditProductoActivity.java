package com.goldenapps.startshopping.registros;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelCategoria;
import com.goldenapps.startshopping.model.ModelProducto;
import com.goldenapps.startshopping.model.ModelTallaProducto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProductoActivity extends AppCompatActivity {

    private String idCategoria,nombre, imagen,descrip,idProducto,fechaRegistroProducto,categoria;
    private int cantidad;
    private Double price;
    private Uri uri;
    private TextView proName,proDesc,proPrice,proCantidad;
    private ImageView proImage;
    private Button guardarEditProducto;
    private ActivityResultLauncher<String> mGetContent;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Productos");
    private DatabaseReference databaseReferenceCategoria = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Spinner oSpinnerCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_producto);

        Intent i2 = getIntent();
        idProducto = i2.getStringExtra("id1");
        nombre = i2.getStringExtra("name1");
        imagen = i2.getStringExtra("image1");
        descrip = i2.getStringExtra("descrip1");
        cantidad = i2.getIntExtra("cantidad1",0);
        price = i2.getDoubleExtra("price1",0.0);
        categoria = i2.getStringExtra("categoria");

        proName = findViewById(R.id.edt_nombreEditProducto);
        proDesc = findViewById(R.id.edt_descripcionEditProducto);
        proPrice = findViewById(R.id.edt_precioEditProducto);
        proCantidad = findViewById(R.id.edt_cantidadEditProducto);
        proImage = findViewById(R.id.imageEditProducto);
        guardarEditProducto = findViewById(R.id.btn_registrarEditProducto);
        oSpinnerCategoria = findViewById(R.id.spinnerCategoriaEditProducto);

        loadCategoria();

        proName.setText(nombre);
        proDesc.setText(descrip);
        proCantidad.setText(Integer.toString(cantidad));
        proPrice.setText(Double.toString(price));
        Glide.with(getApplicationContext()).load(imagen).into(proImage);

        guardarEditProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri!=null){
                    uploadToFireBase(uri);
                }else{
                    Toast.makeText(EditProductoActivity.this,"selecciona foto",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                proImage.setImageURI(result);
                uri = result;
            }
        });

        proImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });
    }

    private void uploadToFireBase(Uri uri) {
        String nombre = proName.getText().toString();
        String descripcion = proDesc.getText().toString();
        int cantidad = Integer.parseInt(proCantidad.getText().toString());
        double precio = Double.parseDouble(proPrice.getText().toString());

        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." +getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        fechaRegistroProducto = simpleDateFormat.format(calendar.getTime());
                        ModelProducto modelProducto = new ModelProducto(idCategoria,nombre,descripcion,fechaRegistroProducto,cantidad,precio,uri.toString(),false);
                        databaseReference.child(idProducto).setValue(modelProducto);

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void loadCategoria(){
        final List<ModelCategoria> categorias = new ArrayList<>();
        databaseReferenceCategoria.child("Categorias").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot oCategoria : snapshot.getChildren()){
                        ModelCategoria categoria = oCategoria.getValue(ModelCategoria.class);
                        String id = oCategoria.getKey();
                        String nombre = categoria.getNombreCategoria();
                        categorias.add(new ModelCategoria(id,nombre,false));
                    }

                    ArrayAdapter<ModelCategoria> categoriaArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line,categorias);
                    oSpinnerCategoria.setAdapter(categoriaArrayAdapter);
                    oSpinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            idCategoria = categorias.get(i).getIdCategoria();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(uri));
    }
}