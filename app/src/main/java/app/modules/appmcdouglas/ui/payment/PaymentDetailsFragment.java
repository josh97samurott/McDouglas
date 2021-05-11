package app.modules.appmcdouglas.ui.payment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.models.CreditCart;
import app.modules.appmcdouglas.models.Food;
import app.modules.appmcdouglas.models.ShoppingCart;
import app.modules.appmcdouglas.ui.shoppingcart.AdapterShoppingCart;

public class PaymentDetailsFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Query nameOrder;
    private TextView txtTotal;
    private EditText edtNumCard;
    private EditText edtDateCard;
    private EditText edtNameCard;
    private EditText edtCodeCard;
    private EditText edtAddress;
    private TextView dangerNum;
    private TextView dangerDate;
    private TextView dangerCode;
    private TextView dangerName;
    private TextView dangerAddress;
    private Button btnSuccessShopping;
    private String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        key = "";
        Bundle data = getArguments();
        Double total = data.getDouble("total");
        View root = inflater.inflate(R.layout.fragment_payment_details, container, false);
        txtTotal = root.findViewById(R.id.txtTotal);
        txtTotal.setText("Total a comprar: $"+ total.toString());
        edtNumCard = root.findViewById(R.id.edtNumCard);
        edtDateCard = root.findViewById(R.id.edtDateCard);
        edtNameCard = root.findViewById(R.id.edtNameCard);
        edtCodeCard = root.findViewById(R.id.edtCodeCard);
        edtAddress = root.findViewById(R.id.edtAddress);
        dangerNum = root.findViewById(R.id.txtDangerNumCard);
        dangerDate = root.findViewById(R.id.txtDangerDateCard);
        dangerCode = root.findViewById(R.id.txtDangerCodeCard);
        dangerName = root.findViewById(R.id.txtDangerNameCard);
        dangerAddress = root.findViewById(R.id.txtDangerAddress);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = firebaseDatabase.getReference("Creditcard");
        nameOrder = databaseReference.orderByChild("keyuser").equalTo(user.getUid());
        nameOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    CreditCart creditCart = data.getValue(CreditCart.class);
                    edtNumCard.setText(creditCart.getNumcard());
                    edtAddress.setText(creditCart.getAddress());
                    edtCodeCard.setText(creditCart.getCarcode());
                    edtDateCard.setText(creditCart.getDuedate());
                    edtNameCard.setText(creditCart.getCardholder());
                    key = data.getKey();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btnSuccessShopping = root.findViewById(R.id.btnSuccessShopping);
        btnSuccessShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringToValidate = edtNumCard.getText().toString();
                String pattern = "^[0-9]{16}$";
                if(Pattern.compile(pattern).matcher(stringToValidate).matches()){
                    dangerNum.setText("");

                    stringToValidate = edtDateCard.getText().toString();
                    pattern = "^[0-9]{2}/[0-9]{2}$";
                    if(Pattern.compile(pattern).matcher(stringToValidate).matches()){
                        dangerDate.setText("");

                        stringToValidate = edtNameCard.getText().toString();
                        if(stringToValidate.isEmpty() == false){
                            dangerName.setText("");

                            stringToValidate = edtCodeCard.getText().toString();
                            pattern = "^[0-9]{3}$";
                            if(Pattern.compile(pattern).matcher(stringToValidate).matches()){
                                dangerCode.setText("");

                                stringToValidate = edtAddress.getText().toString();
                                if(stringToValidate.isEmpty() == false){
                                    dangerAddress.setText("");

                                    //Insertando credit card a la base de datos
                                    databaseReference = firebaseDatabase.getReference("Creditcard");
                                    CreditCart creditCart = new CreditCart(user.getUid(),
                                            edtNumCard.getText().toString(),
                                            edtDateCard.getText().toString(),
                                            edtNameCard.getText().toString(),
                                            edtCodeCard.getText().toString(),
                                            edtAddress.getText().toString());

                                    if(key.isEmpty()){
                                        databaseReference.push().setValue(creditCart);
                                    }
                                    else{
                                        databaseReference.child(key).setValue(creditCart);
                                    }


                                    databaseReference = firebaseDatabase.getReference("Shoppingcart");
                                    nameOrder = databaseReference.orderByChild("keyuser").equalTo(user.getUid());
                                    nameOrder.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot data : snapshot.getChildren()){
                                                data.getRef().removeValue();

                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    Toast.makeText(getContext(), R.string.payment_success_purchase, Toast.LENGTH_LONG).show();
                                    Bundle datosEnviar = new Bundle();
                                    datosEnviar.putString("total", total.toString());
                                    Fragment fragment = new BillPaymentFragment();
                                    fragment.setArguments(datosEnviar);
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    root.findViewById(R.id.fragment_payment_details).setVisibility(View.INVISIBLE);
                                }
                                else{
                                    dangerAddress.setText(getResources().getString(R.string.payment_danger_address));
                                }
                            }
                            else{
                                dangerCode.setText(getResources().getString(R.string.payment_danger_code));
                            }
                        }
                        else {
                            dangerName.setText(getResources().getString(R.string.payment_name_card));
                        }
                    }
                    else{
                        dangerDate.setText(getResources().getString(R.string.payment_danger_date));
                    }


                }
                else{
                    dangerNum.setText(getResources().getString(R.string.payment_danger_num));
                }

            }
        });


        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}