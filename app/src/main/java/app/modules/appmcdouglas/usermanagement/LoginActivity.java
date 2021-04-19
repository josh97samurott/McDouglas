package app.modules.appmcdouglas.usermanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import app.modules.appmcdouglas.McDouglasActivity;
import app.modules.appmcdouglas.R;

public class LoginActivity extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private Button signInGoogle;
    private Button login;
    private Button register;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        M
        user = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);
        signInGoogle = findViewById(R.id.btnLoginGoogle);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);

        //Inicio de sesión normal
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToApp();
            }
        });

        //Inicio de sesión con google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });

        //Enviar a registro
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser user){

    }

    //Inicio de sesión normal
    public void loginToApp(){
        mAuth.signInWithEmailAndPassword(user.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), R.string.login_succesfull, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(LoginActivity.this, McDouglasActivity.class);
                            i.putExtra("key", mAuth.getUid());
                            i.putExtra("email", user.getText().toString());
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }



    //Inicio de sesión con google
    public void signInWithGoogle(){
        Intent signInIntent = mGoogleSignInClient.
    }

    //Registro de cuenta de usuario
    public void registerAccount(){
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

}