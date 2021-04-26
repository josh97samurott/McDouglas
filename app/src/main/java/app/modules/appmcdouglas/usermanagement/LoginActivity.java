package app.modules.appmcdouglas.usermanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.modules.appmcdouglas.McDouglasActivity;
import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.models.User;

public class LoginActivity extends AppCompatActivity {
    //Campos de formulario
    private EditText user;
    private EditText password;
    //Botones de formulario
    private Button signInGoogle;
    private Button login;
    private Button register;

    //Variable de conexión a Firebase Auth
    private FirebaseAuth mAuth;

    //Variables de conexión a Firebase Auth con Google
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializando variables
        mAuth = FirebaseAuth.getInstance();
        user = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);
        signInGoogle = findViewById(R.id.btnLoginGoogle);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);
        ////////////////////////////

        //Inicio de sesión normal
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToApp();
            }
        });
        ////////////////////////

        //Inicio de sesión con google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
        ///////////////////////////////

        //Enviar a registro
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });
        //////////////////////////////
    }

    //Comprobación de inicio de la APP - Comprobando inicio de sesión
    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void updateUI(FirebaseUser user){
        if(user != null){
            Intent i = new Intent(LoginActivity.this, McDouglasActivity.class);
            startActivity(i);
        }
    }
    //////////////////////////////////////////////////////////////////

    //Inicio de sesión normal
    public void loginToApp(){
        mAuth.signInWithEmailAndPassword(user.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), R.string.login_succesfull, Toast.LENGTH_LONG).show();
                            updateUI(mAuth.getCurrentUser());
                        }
                        else{
                            Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }
    /////////////////////////////

    //Inicio de sesión con google
    public void signInWithGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(getApplicationContext(), R.string.login_succesfull, Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            registerUser(user);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }
    ///////////////////////////////

    //Registro de cuenta de usuario
    public void registerAccount(){
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    public void registerUser(FirebaseUser user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Usuarios").child(user.getUid()).exists()){

                }
                else{
                    User userprofile = new User(user.getUid(), user.getEmail(),
                            user.getDisplayName(), user.getDisplayName(), user.getPhoneNumber());
                    myRef.child("Usuarios").child(user.getUid()).setValue(userprofile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.addListenerForSingleValueEvent(valueEventListener);
    }
    ///////////////////////////////

}