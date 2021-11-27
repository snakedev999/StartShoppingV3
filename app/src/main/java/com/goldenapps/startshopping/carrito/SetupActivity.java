package com.goldenapps.startshopping.carrito;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.ui.UsuarioFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private EditText name, fono, region, ciudad, nameCalle, Ncalle;
    private Button guardar;
    private CircleImageView imagen;
    private FirebaseAuth auth;
    private DatabaseReference UserRef;
    private ProgressDialog dialog;
    private String CurrentUserId;
    private static int GaleryPick = 1;
    private StorageReference UserImagenPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_setup);

        auth=FirebaseAuth.getInstance();
        CurrentUserId = auth.getCurrentUser().getUid();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        dialog = new ProgressDialog(this);
        //guardar docuementos o fotos
        UserImagenPerfil = FirebaseStorage.getInstance().getReference().child("Perfil");
        name = (EditText)findViewById(R.id.nameUser);
        fono = (EditText)findViewById(R.id.fono);
        region = (EditText)findViewById(R.id.region);
        ciudad = (EditText)findViewById(R.id.city);
        nameCalle = (EditText)findViewById(R.id.adress);
        Ncalle = (EditText)findViewById(R.id.Ncalle);
        imagen = (CircleImageView) findViewById(R.id.setup_logo);
        guardar = (Button)findViewById(R.id.sgte); 
        
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarInfoSetup();
            }
        });

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/");
                startActivityForResult(intent,GaleryPick);
            }
        });

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    if (snapshot.hasChild("imagen")){
                    String imagestr = snapshot.child("imagen").getValue().toString();
                        Picasso.get().load(imagestr).placeholder(R.drawable.ic_baseline_person1_24).into(imagen);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void GuardarInfoSetup() {
    }

}
