package app.modules.appmcdouglas.usermanagement;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.models.CreditCart;
import app.modules.appmcdouglas.models.User;


public class UserFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Query nameOrder;
    private EditText edtEmail;
    private EditText edtName;
    private EditText edtLastname;
    private EditText edtOldpassword;
    private EditText edtNewpassword;
    private EditText edtConfirmpassword;
    private EditText edtPhone;
    private TextView dangerUser;
    private Button btnSave;
    private String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        key = "";
        View root =  inflater.inflate(R.layout.fragment_user, container, false);
        edtEmail = root.findViewById(R.id.edtEmail);
        edtName = root.findViewById(R.id.edtName);
        edtLastname = root.findViewById(R.id.edtLastname);
        edtOldpassword = root.findViewById(R.id.edtPassword);
        edtNewpassword = root.findViewById(R.id.edtChangePassword);
        edtConfirmpassword = root.findViewById(R.id.edtConfirmpassword);
        edtPhone = root.findViewById(R.id.edtPhone);
        dangerUser = root.findViewById(R.id.txtDangerUser);
        btnSave = root.findViewById(R.id.btnSuccessUser);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("Usuarios");
        nameOrder = databaseReference.orderByChild("key").equalTo(user.getUid());

        edtEmail.setFocusable(false);
        edtEmail.setEnabled(false);
        edtEmail.setCursorVisible(false);
        edtEmail.setKeyListener(null);
        edtEmail.setBackgroundColor(Color.TRANSPARENT);

        nameOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    User usermodel = data.getValue(User.class);
                    edtEmail.setText(usermodel.getEmail());
                    edtName.setText(usermodel.getName());
                    edtLastname.setText(usermodel.getLastname());
                    edtPhone.setText(usermodel.getPhone());
                    key = data.getKey();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString().isEmpty() ||
                edtLastname.getText().toString().isEmpty() ||
                edtPhone.getText().toString().isEmpty()){
                    dangerUser.setText(getResources().getString(R.string.user_danger_field));
                }
                else{
                    //Insertando credit card a la base de datos
                    databaseReference = firebaseDatabase.getReference("Usuarios");
                    User usermodel = new User(user.getUid(),
                            edtEmail.getText().toString(),
                            edtName.getText().toString(),
                            edtLastname.getText().toString(),
                            edtPhone.getText().toString());

                    databaseReference.child(user.getUid()).setValue(usermodel);
                    dangerUser.setText("");
                    Toast.makeText(getContext(), R.string.user_confirm_save, Toast.LENGTH_LONG).show();
                }

                if(edtOldpassword.getText().toString().isEmpty() == false &&
                edtNewpassword.getText().toString().isEmpty() == false &&
                edtConfirmpassword.getText().toString().isEmpty() == false){
                    if(edtNewpassword.getText().toString().equals(edtConfirmpassword.getText().toString())){

                        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), edtOldpassword.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            user.updatePassword(edtNewpassword.getText().toString());
                                            Toast.makeText(getContext(), R.string.user_confirm_save_password, Toast.LENGTH_LONG).show();
                                            dangerUser.setText("");
                                        }
                                        else{
                                            dangerUser.setText(getResources().getString(R.string.user_paswword));
                                        }
                                    }
                                });

                    }
                    else{
                        dangerUser.setText(getResources().getString(R.string.user_danger_password_confirm));
                    }
                }
            }
        });




        return root;
    }
}