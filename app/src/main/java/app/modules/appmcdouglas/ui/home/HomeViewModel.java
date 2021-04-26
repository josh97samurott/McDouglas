package app.modules.appmcdouglas.ui.home;

import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.modules.appmcdouglas.models.Food;

public class HomeViewModel extends ViewModel {

    public FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference = firebaseDatabase.getReference("Menu");
    public Query nameOrder = databaseReference.orderByChild("name");
    private List<Food> listfood;

    public HomeViewModel() {
        listfood = new ArrayList<>();
    }

    public List<Food> getListFood() {
        nameOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listfood.removeAll(listfood);
                for (DataSnapshot data : snapshot.getChildren()){
                    Food food = data.getValue(Food.class);
                    food.setKey(data.getKey());
                    listfood.add(food);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return listfood;
    }
}