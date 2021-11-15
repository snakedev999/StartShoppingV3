package com.goldenapps.startshopping.registros;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelCategoria;
import com.goldenapps.startshopping.model.ModelProducto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistroProductoActivity extends AppCompatActivity {

    private EditText edtMarca,edtNombre,edtPrecio;
    private ImageView imageProducto;
    private Button registrarProducto;
    private Uri uri;
    private ActivityResultLauncher<String> mGetContent;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Productos");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_producto);

        edtMarca = (EditText) findViewById(R.id.edt_marcaProducto);
        edtNombre = (EditText) findViewById(R.id.edt_nombreProducto);
        edtPrecio = (EditText) findViewById(R.id.edt_precioProducto);
        imageProducto = (ImageView) findViewById(R.id.imageProducto);
        registrarProducto = (Button) findViewById(R.id.btn_registrarProducto);

        imageProducto.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);

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

    private void uploadToFireBase(Uri uri) {
        String marca = edtMarca.getText().toString();
        String nombre = edtNombre.getText().toString();
        String precio = edtPrecio.getText().toString();

        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." +getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ModelProducto modelProducto = new ModelProducto(marca,nombre,precio,uri.toString());
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
        edtNombre.setText("");
        edtPrecio.setText("");
        edtMarca.setText("");
        imageProducto.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
    }
}