package app.modules.appmcdouglas.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.modules.appmcdouglas.McDouglasActivity;
import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.models.Food;

public class HomeFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Query nameOrder;
    private ListView listFood;
    private List<Food> foods;
    private HomeViewModel homeViewModel;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Menu");
        nameOrder = databaseReference.orderByChild("name");
        foods = new ArrayList<>();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        listFood = root.findViewById(R.id.listFood);
        context = getContext();
        nameOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foods = new ArrayList<>();
                foods.removeAll(foods);
                for (DataSnapshot data : snapshot.getChildren()){
                    Food food = data.getValue(Food.class);
                    foods.add(food);
                    AdapterFood adapter = new AdapterFood(context, foods);
                    listFood.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}