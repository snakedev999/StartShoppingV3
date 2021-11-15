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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelCategoria;
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

public class RegistroCategoriaActivity extends AppCompatActivity {

    TextView t;
    private ImageView imageCategoria,imagenRecuperada;
    private EditText nombreCategoria;
    private Button registrarCategoria,recuperarImagen;
    private Uri uri;
    private ActivityResultLauncher<String> mGetContent;
    private DatabaseReference data;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Categorias");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_categoria);

        imageCategoria = (ImageView) findViewById(R.id.imageCategoria);
        nombreCategoria = (EditText) findViewById(R.id.edt_nombreCategoria);
        registrarCategoria = (Button) findViewById(R.id.btn_registrarCategoria);
        recuperarImagen = (Button) findViewById(R.id.button5);
        imagenRecuperada = (ImageView) findViewById(R.id.imageView4);
        imageCategoria.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
        imagenRecuperada.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
        t = findViewById(R.id.textView4);
        data = FirebaseDatabase.getInstance().getReference();

        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageCategoria.setImageURI(result);
                uri = result;
            }
        });

        imageCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });

        registrarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri!=null){
                    uploadToFireBase(uri);
                    registrarCategoria.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(RegistroCategoriaActivity.this,"selecciona foto",Toast.LENGTH_SHORT).show();
                }
            }
        });

        recuperarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitarDatosFirebase();

            }
        });

    }

    private void solicitarDatosFirebase() {
        data.child("Categorias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for(final DataSnapshot obj : snapshot.getChildren()){

                    data.child("Categorias").child(obj.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ModelCategoria user = obj.getValue(ModelCategoria.class);
                            String nombre = user.getNombreCategoria();
                            String apellido = user.getImagenCategoria();

                            Glide.with(RegistroCategoriaActivity.this).load(apellido).into(imagenRecuperada);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void uploadToFireBase(Uri uri) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." +getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ModelCategoria modelCategoria = new ModelCategoria(nombreCategoria.getText().toString(),uri.toString());
                        String modelId = databaseReference.push().getKey();
                        databaseReference.child(modelId).setValue(modelCategoria);
                        limpiar();
                        registrarCategoria.setVisibility(View.VISIBLE);
                        Toast.makeText(RegistroCategoriaActivity.this,"Subida correctamente",Toast.LENGTH_SHORT).show();

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
                registrarCategoria.setVisibility(View.VISIBLE);
                Toast.makeText(RegistroCategoriaActivity.this,"Ha fallado la subida!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void limpiar(){
        nombreCategoria.setText("");
        imageCategoria.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
    }
}