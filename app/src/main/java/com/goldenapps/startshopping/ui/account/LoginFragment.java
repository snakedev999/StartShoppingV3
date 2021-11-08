package com.goldenapps.startshopping.ui.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapps.startshopping.model.ModelAccount;
import com.goldenapps.startshopping.ui.menu.MenuActivity;
import com.goldenapps.startshopping.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginFragment extends Fragment {

    private View loginFragment;
    private Button btnLogin;
    private TextView recoverPass;
    private EditText edtEmail,edtPass;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String tipo = "tipoUsuario";

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    private String tipoUsuario;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loginFragment = inflater.inflate(R.layout.fragment_login,container,false);

        Button n2 = (Button) loginFragment.findViewById(R.id.button);

        initialize();

        return loginFragment;

    }

    private void initialize(){
        try {
            mAuth = FirebaseAuth.getInstance();

            edtEmail = (EditText) loginFragment.findViewById(R.id.edt_email);
            edtPass = (EditText) loginFragment.findViewById(R.id.edt_pass);

            Button n = (Button) loginFragment.findViewById(R.id.btn_Login);

            n.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                                            Toast.makeText(getActivity().getApplicationContext(), "Revise su correo, Necesita verificación",Toast.LENGTH_SHORT).show();
                                            user.sendEmailVerification();
                                        }
                                    }else{
                                        Toast.makeText(getActivity().getApplicationContext(),"Error al ingresar, verifique los datos ingresados",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });

        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}