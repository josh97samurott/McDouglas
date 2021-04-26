package app.modules.appmcdouglas.ui.shoppingcart;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.ui.payment.PaymentViewModel;

public class ShoppingCartFragment extends Fragment {

    private ShoppingCartViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(ShoppingCartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shoppingcart, container, false);
        final TextView textView = root.findViewById(R.id.txtTexto);
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}