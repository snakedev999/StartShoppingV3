package com.goldenapps.startshopping.ui.account;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapps.startshopping.DbHelper;
import com.goldenapps.startshopping.model.ModelAccount;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.goldenapps.startshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LoginFragment extends Fragment {

    private String idUsuarioSave;
    private DbHelper helper;
    private SQLiteDatabase db;


    private View loginFragment;
    private Button btnLogin,n;
    private TextView recoverPass;
    private EditText edtEmail,edtPass;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    ImageButton google,fb,email1;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginFragment = inflater.inflate(R.layout.fragment_login,container,false);

        ///Button n2 = (Button) loginFragment.findViewById(R.id.button);

        //Cambiar a boton!
        TextView signUp = (TextView) loginFragment.findViewById(R.id.signup);
        TextView NPass = (TextView) loginFragment.findViewById(R.id.cambiaPass);


        n = (Button) loginFragment.findViewById(R.id.btn_Login);
        ImageButton Ibtn = (ImageButton) loginFragment.findViewById(R.id.btnG);
        ImageButton Ibtn1 = (ImageButton) loginFragment.findViewById(R.id.btnF);
        ImageButton Ibtn2= (ImageButton) loginFragment.findViewById(R.id.btnE);

        google = loginFragment.findViewById(R.id.btnG);
        fb = loginFragment.findViewById(R.id.btnF);
        email1= loginFragment.findViewById(R.id.btnE);

        TextInputLayout emailLayout = loginFragment.findViewById(R.id.email1);
        TextInputLayout passLayout = loginFragment.findViewById(R.id.password1);

        edtEmail = (EditText) loginFragment.findViewById(R.id.email);
        edtPass = (EditText) loginFragment.findViewById(R.id.password);
        float v = 0;

        Ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object p=1;
                Toast.makeText(getActivity(), "Google", LENGTH_SHORT).show();
            }
        });

        Ibtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Facebook", LENGTH_SHORT).show();
            }
        });

        Ibtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Email", LENGTH_SHORT).show();
            }
        });

        NPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Contraseña Olvidada", LENGTH_SHORT).show();
            }
        });

        email1.setTranslationY(300);
        google.setTranslationY(300);
        fb.setTranslationY(300);

        emailLayout.setTranslationX(300);
        passLayout.setTranslationX(300);
        n.setTranslationX(300);


        email1.setAlpha(v);
        google.setAlpha(v);
        fb.setAlpha(v);
        emailLayout.setAlpha(v);
        passLayout.setAlpha(v);
        n.setAlpha(v);



        email1.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

        emailLayout.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        passLayout.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        n.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();

        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        return loginFragment;

    }

    private void loginUser(){
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();

        if(email.isEmpty()){
            edtEmail.setError("Correo requerido");
            edtEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Ingrese un correo válido");
            edtEmail.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            edtPass.setError("Contraseña requerida");
            edtPass.requestFocus();
            return;
        }

        if(pass.length() < 6){
            edtPass.setError("Minimo 6 caracteres");
            edtPass.requestFocus();
            return;
        }

        try {
            mAuth.signInWithEmailAndPassword(edtEmail.getText().toString().trim(),edtPass.getText().toString().trim())
                    .addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String userID = user.getUid();

                                //Si no está verificado el correo, se debera enviar un correo al usuario para aquello
                                if(user.isEmailVerified()){
                                    reference = FirebaseDatabase.getInstance().getReference("Usuarios");
                                    reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            registrarUsuario(userID);
                                            Intent mainLogin = new Intent(getActivity(), LoadingAccountActivity.class);
                                            mainLogin.putExtra("idUser", userID);
                                            startActivity(mainLogin);
                                            getActivity().finishAffinity();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                        }
                                    });
                                }else{
                                    Toast.makeText(getActivity().getApplicationContext(), "Revise su correo, Necesita verificación", LENGTH_SHORT).show();
                                    user.sendEmailVerification();
                                }
                            }else{
                                Toast.makeText(getActivity().getApplicationContext(),"Error al ingresar, verifique los datos ingresados", LENGTH_SHORT).show();
                            }
                        }
                    });
        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(), LENGTH_SHORT).show();
        }
    }

    private void registrarUsuario(String idUsuario){
        helper = new DbHelper(getActivity());
        db = helper.getWritableDatabase();
        int id = 1;
        String idU = idUsuario;

        ContentValues registroUsuario = new ContentValues();
        registroUsuario.put("id",id);
        registroUsuario.put("idUsuario",idU);

        Cursor fila = db.rawQuery("SELECT IDUSUARIO FROM USUARIO WHERE IDUSUARIO = '"+idU+"'",null);

        if(fila.moveToFirst()){
            setIdUsuarioSave(fila.getString(0));
        }else{
            db.insert("USUARIO",null,registroUsuario);
        }
        db.close();
    }

    public void act2(View View) {
        Intent act = new Intent(getActivity(), RegisterFragment.class);
        startActivity(act);
        getActivity().overridePendingTransition(R.anim.translate_left_side, 0);
    }

    public String getIdUsuarioSave() {
        return idUsuarioSave;
    }

    public void setIdUsuarioSave(String idUsuarioSave) {
        this.idUsuarioSave = idUsuarioSave;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private String tipoUsuario;


}