package app.modules.appmcdouglas.usermanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import app.modules.appmcdouglas.R;


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
        edtNewpassword = root.findViewById(R.id.edt)

        return root;
    }
}