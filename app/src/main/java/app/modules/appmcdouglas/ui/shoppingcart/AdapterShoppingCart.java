package app.modules.appmcdouglas.ui.shoppingcart;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.models.Food;
import app.modules.appmcdouglas.models.ShoppingCart;

public class AdapterShoppingCart extends ArrayAdapter<Food> {

    List<Food> listfood;
    private Context context;

    public AdapterShoppingCart(@NonNull Context context, @NonNull List<Food> listfood){
        super(context, R.layout.shoppingcart_layout, listfood);
        this.context = context;
        this.listfood = listfood;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowview = null;

        if(view == null){
            rowview = layoutInflater.inflate(R.layout.shoppingcart_layout, null);
        }
        else{
            rowview = view;
        }

        Button btnDelShoppingCart = rowview.findViewById(R.id.btnDelShopping);

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

        btnDelShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Shoppingcart");
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                databaseReference.child(listfood.get(position).getKeyshoppingcart()).removeValue();
                Toast.makeText(getContext(), R.string.success_del_to_shoppingcart, Toast.LENGTH_LONG).show();
                listfood.remove(position);
            }
        });


        return rowview;
    }
}
