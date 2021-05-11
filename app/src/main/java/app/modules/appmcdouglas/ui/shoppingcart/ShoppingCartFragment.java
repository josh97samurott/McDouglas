package app.modules.appmcdouglas.ui.shoppingcart;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.models.Food;
import app.modules.appmcdouglas.models.ShoppingCart;
import app.modules.appmcdouglas.ui.payment.PaymentDetailsFragment;

public class ShoppingCartFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Query nameOrder;
    private ListView listShoppingcart;
    private TextView totalShoppingcart;
    private Button btnShoppingcart;
    private List<Food> foodList;
    private List<ShoppingCart> shoppingCartList;
    private Double total;
    private Double totalToSet;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        total = 0.00;
        totalToSet = 0.00;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Shoppingcart");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        nameOrder = databaseReference.orderByChild("keyuser").equalTo(user.getUid());
        View root = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
        foodList = new ArrayList<>();
        shoppingCartList = new ArrayList<>();
        listShoppingcart = root.findViewById(R.id.listShoppingCart);
        totalShoppingcart = root.findViewById(R.id.txtTotal);
        btnShoppingcart = root.findViewById(R.id.btnShopping);
        context = getContext();
        nameOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shoppingCartList = new ArrayList<>();
                shoppingCartList.removeAll(shoppingCartList);
                for (DataSnapshot data : snapshot.getChildren()){
                    ShoppingCart shoppingCart = data.getValue(ShoppingCart.class);
                    shoppingCart.setKey(data.getKey());
                    shoppingCartList.add(shoppingCart);
                }

                databaseReference = firebaseDatabase.getReference("Menu");
                nameOrder = databaseReference.orderByChild("name");

                nameOrder.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                        foodList = new ArrayList<>();
                        foodList.removeAll(foodList);
                        int num = 0;
                        for(ShoppingCart shoppingCart2 : shoppingCartList){
                            Food food = snapshot2.child(shoppingCart2.getKeyfood()).getValue(Food.class);
                            food.setKeyshoppingcart(shoppingCart2.getKey());
                            foodList.add(food);
                            AdapterShoppingCart adapterShoppingCart = new AdapterShoppingCart(context, foodList);
                            listShoppingcart.setAdapter(adapterShoppingCart);
                            total = total + Double.parseDouble(food.getPrice());
                            totalShoppingcart.setText("Total: $" + total.toString());
                            totalToSet = total;
                            num++;
                        }
                        if(num == 0){
                            totalShoppingcart.setText("Total: $" + total.toString());
                            btnShoppingcart.setVisibility(View.INVISIBLE);
                        }
                        total = 0.00;
                        listShoppingcart.invalidateViews();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnShoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle datosEnviar = new Bundle();
                datosEnviar.putDouble("total", totalToSet);
                Fragment fragment = new PaymentDetailsFragment();
                fragment.setArguments(datosEnviar);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return root;
    }



}