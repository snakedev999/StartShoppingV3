package com.goldenapps.startshopping.registros;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelCategoria;
import com.goldenapps.startshopping.model.ModelProducto;
import com.goldenapps.startshopping.model.ModelRegion;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RegistroProductoActivity extends AppCompatActivity {

    private String idCategoria,categoriaSeleccionada,fechaRegistroProducto;
    private EditText edtNombre,edtDescripcion,edtCantidad,edtPrecio;
    private Uri uri;
    private ImageView imageProducto;
    private Button registrarProducto;
    private Spinner oSpinnerCategoria;
    private ActivityResultLauncher<String> mGetContent;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Productos");
    private DatabaseReference databaseReferenceCategoria = FirebaseDatabase.getInstance().getReference();
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registro_producto);

        oSpinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoriaProducto);
        edtNombre = (EditText) findViewById(R.id.edt_nombreProducto);
        edtDescripcion = (EditText) findViewById(R.id.edt_descripcionProducto);
        edtCantidad = (EditText) findViewById(R.id.edt_cantidadProducto);
        edtPrecio = (EditText) findViewById(R.id.edt_precioProducto);
        imageProducto = (ImageView) findViewById(R.id.imageProducto);

        registrarProducto = (Button) findViewById(R.id.btn_registrarProducto);

        imageProducto.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);

        loadCategoria();
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageProducto.setImageURI(result);
                uri = result;
            }
        });

        imageProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });

        registrarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri!=null){
                    uploadToFireBase(uri);
                    registrarProducto.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(RegistroProductoActivity.this,"selecciona foto",Toast.LENGTH_SHORT).show();
                }
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
                            categoriaSeleccionada = adapterView.getItemAtPosition(i).toString();
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

    private void uploadToFireBase(Uri uri) {
        String nombre = edtNombre.getText().toString();
        String descripcion = edtDescripcion.getText().toString();
        int cantidad = Integer.parseInt(edtCantidad.getText().toString());
        double precio = Double.parseDouble(edtPrecio.getText().toString());

        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." +getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
                        fechaRegistroProducto = simpleDateFormat.format(calendar.getTime());

                        ModelProducto modelProducto = new ModelProducto(idCategoria,nombre,descripcion,fechaRegistroProducto,cantidad,precio,uri.toString());
                        String modelId = databaseReference.push().getKey();
                        databaseReference.child(modelId).setValue(modelProducto);
                        limpiar();
                        registrarProducto.setVisibility(View.VISIBLE);
                        Toast.makeText(RegistroProductoActivity.this,"Subida correctamente",Toast.LENGTH_SHORT).show();

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
                registrarProducto.setVisibility(View.VISIBLE);
                Toast.makeText(RegistroProductoActivity.this,"Ha fallado la subida!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void limpiar(){
        edtPrecio.setText("");
        edtCantidad.setText("");
        edtDescripcion.setText("");
        edtNombre.setText("");
        imageProducto.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
    }

}