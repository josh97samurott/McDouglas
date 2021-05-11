package app.modules.appmcdouglas.ui.payment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.models.CreditCart;
import app.modules.appmcdouglas.models.User;

public class BillPaymentFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Query nameOrder;
    private TextView txtDate;
    private TextView txtTotal;
    private TextView txtAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bill_payment, container, false);
        txtDate = root.findViewById(R.id.txtDate);
        txtTotal = root.findViewById(R.id.txtTotal);
        txtAddress = root.findViewById(R.id.txtAddress);
        Bundle data = getArguments();
        txtTotal.setText(getResources().getString(R.string.bill_total) + " $" + data.getString("total"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        txtDate.setText(getResources().getString(R.string.bill_date) + " " + dateFormat.format(date));

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
                    txtAddress.setText(getResources().getString(R.string.bill_address) + " " + creditCart.getAddress().toString());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return root;
    }
}