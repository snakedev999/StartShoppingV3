package com.goldenapps.startshopping.ui.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.goldenapps.startshopping.R;
import com.goldenapps.startshopping.model.ModelAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    private String tipoUsuario = "usuario";
    private String id;
    private Button btnRegister1;
    private EditText editTextEmail,editTextName,editTextPass,editTextPassConfirm;
    private FirebaseAuth mAuth;

    ImageButton google,fb,email1, btnOjo, btnOjo2;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) view.findViewById(R.id.editTextAuthEmail);
        editTextName = (EditText) view.findViewById(R.id.editTextAuthName);
        editTextPass = (EditText) view.findViewById(R.id.editTextAuthPass);
        editTextPassConfirm =(EditText) view.findViewById(R.id.editTextAuthPassConfirm);
        btnRegister1 = (Button) view.findViewById(R.id.btnRegister);


        ImageButton Ibtn = (ImageButton) view.findViewById(R.id.btnG);
        ImageButton Ibtn1 = (ImageButton) view.findViewById(R.id.btnF);
        ImageButton Ibtn2= (ImageButton) view.findViewById(R.id.btnE);
        ImageButton btnOjo= (ImageButton) view.findViewById(R.id.btnOjo);
        ImageButton btnOjo2= (ImageButton) view.findViewById(R.id.btnOjo2);


        Ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Google", Toast.LENGTH_SHORT).show();
            }
        });

        Ibtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Facebook", Toast.LENGTH_SHORT).show();
            }
        });

        Ibtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Email", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        btnOjo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnOjo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        google = view.findViewById(R.id.btnG);
        fb = view.findViewById(R.id.btnF);
        email1= view.findViewById(R.id.btnE);
        float v=0;

        email1.setTranslationY(300);
        google.setTranslationY(300);
        fb.setTranslationY(300);
        tabLayout.setTranslationY(300);

        editTextEmail.setTranslationX(300);
        editTextName.setTranslationX(300);
        editTextPass.setTranslationX(300);
        editTextPassConfirm.setTranslationX(300);
        btnRegister1.setTranslationX(300);

        email1.setAlpha(v);
        google.setAlpha(v);
        fb.setAlpha(v);
        tabLayout.setAlpha(v);
        editTextEmail.setAlpha(v);
        editTextName.setAlpha(v);
        editTextPass.setAlpha(v);
        editTextPassConfirm.setAlpha(v);
        btnRegister1.setAlpha(v);


        email1.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

        editTextEmail.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
        editTextName.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        editTextPass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        editTextPassConfirm.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        btnRegister1.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();

        // Inflate the layout for this fragment
        return view;
    }

    private void VerPass(){
        editTextPass.
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String pass = editTextPass.getText().toString().trim();
        String passConfirm = editTextPassConfirm.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Correo requerido");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Ingrese un correo válido");
            editTextEmail.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            editTextPass.setError("Contraseña requerida");
            editTextPass.requestFocus();
            return;
        }

        if(passConfirm.isEmpty()){
            editTextPassConfirm.setError("Contraseña requerida");
            editTextPassConfirm.requestFocus();
            return;
        }

        if(pass.length() < 6){
            editTextPass.setError("Minimo 6 caracteres");
            editTextPass.requestFocus();
            return;
        }

        if(passConfirm.length() < 6){
            editTextPassConfirm.setError("Minimo 6 caracteres");
            editTextPassConfirm.requestFocus();
            return;
        }

        if(pass.equals(passConfirm)){
            mAuth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            id = mAuth.getUid();

                            if(task.isSuccessful()){
                                ModelAccount user = new ModelAccount(id, email,name,pass,tipoUsuario);

                                FirebaseDatabase.getInstance().getReference("Usuarios")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                            user1.sendEmailVerification();
                                            limpia();
                                            Toast.makeText(getActivity(),"Registrado correctamente, verifique su correo",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getActivity(),"Error al registrar, intentalo nuevamente",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(getActivity(),"Error al registrar, intentalo nuevamente",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            editTextPass.setError("No coincide la contraseña");
            editTextPass.requestFocus();
            editTextPassConfirm.setError("No coincide la contraseña");
            editTextPassConfirm.requestFocus();
            return;
        }
    }


    private void limpia(){
        editTextEmail.setText("");
        editTextName.setText("");
        editTextPass.setText("");
        editTextPassConfirm.setText("");
    }

    public void cambio(View View) {
        Intent info = new Intent(getActivity(),LoginFragment.class);
        startActivity(info);
        getActivity().overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
    }

    public void onBackPressed() {
        super.getActivity().onBackPressed();
        getActivity().overridePendingTransition(R.anim.zoom_in, 0);
    }
}