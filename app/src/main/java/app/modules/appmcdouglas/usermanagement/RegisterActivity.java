package app.modules.appmcdouglas.usermanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.models.User;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private Button login;
    private EditText email;
    private EditText password;
    private EditText name;
    private EditText lastname;
    private EditText phone;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        name = findViewById(R.id.editName);
        lastname = findViewById(R.id.editLastname);
        phone = findViewById(R.id.editPhone);


        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        register = findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });
    }

    public void goToLogin(){
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public void registerAccount(){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), R.string.register_status_ok, Toast.LENGTH_LONG).show();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference();
                            User user = new User(mAuth.getUid(), email.getText().toString(),
                                    name.getText().toString(), lastname.getText().toString(), phone.getText().toString());
                            myRef.child("Usuarios").child(mAuth.getUid()).setValue(user);
                            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), R.string.register_status_failed, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}