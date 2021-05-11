package app.modules.appmcdouglas.ui.home;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.models.Food;
import app.modules.appmcdouglas.models.ShoppingCart;

public class AdapterFood extends ArrayAdapter<Food> {

    List<Food> listfood;
    private Context context;

    public AdapterFood(@NonNull Context context, @NonNull List<Food> listfood){
        super(context, R.layout.food_layout, listfood);
        this.context = context;
        this.listfood = listfood;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = null;

        if(view == null){
            rowview = layoutInflater.inflate(R.layout.food_layout, null);
        }
        else{
            rowview = view;
        }

        Button btnAddShoppingCart = rowview.findViewById(R.id.btnAddShopping);

        TextView txtKeyFood = rowview.findViewById(R.id.keyfood);
        TextView txtNameFood = rowview.findViewById(R.id.namefood);
        TextView txtCategoryFood = rowview.findViewById(R.id.categoryfood);
        TextView txtDescriptionFood = rowview.findViewById(R.id.descriptionfood);
        TextView txtPriceFood = rowview.findViewById(R.id.pricefood);
        ImageView imageFood = rowview.findViewById(R.id.imagefood);

        txtKeyFood.setText(listfood.get(position).getKey());
        txtNameFood.setText(Html.fromHtml(listfood.get(position).getName()));
        txtCategoryFood.setText(Html.fromHtml(listfood.get(position).getCategory()));
        txtDescriptionFood.setText(Html.fromHtml(listfood.get(position).getDescription()));
        txtPriceFood.setText(Html.fromHtml("$"+listfood.get(position).getPrice()));
        Picasso.get().load(listfood.get(position).getTokenimg()).into(imageFood);

        btnAddShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Shoppingcart");
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                ShoppingCart shoppingCart = new ShoppingCart(user.getUid(), listfood.get(position).getKey());
                databaseReference.push().setValue(shoppingCart);
                Toast.makeText(getContext(), R.string.success_add_to_shoppingcart, Toast.LENGTH_LONG).show();
            }
        });

        return rowview;
    }
}
