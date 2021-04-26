package app.modules.appmcdouglas.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import app.modules.appmcdouglas.McDouglasActivity;
import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.models.Food;

public class HomeFragment extends Fragment {

    private ListView listFood;
    private List<Food> foods;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listFood = root.findViewById(R.id.listFood);
        foods = homeViewModel.getListFood();
        AdapterFood adapter = new AdapterFood( (McDouglasActivity) getContext(), foods);
        return root;
    }
}