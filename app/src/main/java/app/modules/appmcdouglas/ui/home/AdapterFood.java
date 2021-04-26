package app.modules.appmcdouglas.ui.home;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

import app.modules.appmcdouglas.R;
import app.modules.appmcdouglas.models.Food;

public class AdapterFood extends ArrayAdapter<Food> {

    List<Food> listfood;
    private Activity context;

    public AdapterFood(@NonNull Activity context, @NonNull List<Food> listfood){
        super(context, R.layout.food_layout, listfood);
        this.context = context;
        this.listfood = listfood;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview = null;

        if(view == null){
            rowview = layoutInflater.inflate(R.layout.food_layout, null);
        }
        else{
            rowview = view;
        }

        TextView txtKeyFood = rowview.findViewById(R.id.keyfood);
        TextView txtNameFood = rowview.findViewById(R.id.namefood);
        TextView txtCategoryFood = rowview.findViewById(R.id.categoryfood);
        TextView txtDescriptionFood = rowview.findViewById(R.id.descriptionfood);
        TextView txtPriceFood = rowview.findViewById(R.id.pricefood);

        txtKeyFood.setText(listfood.get(position).getKey());
        txtNameFood.setText(Html.fromHtml(listfood.get(position).getName()));
        txtCategoryFood.setText(Html.fromHtml(listfood.get(position).getCategory()));
        txtDescriptionFood.setText(Html.fromHtml(listfood.get(position).getDescription()));
        txtPriceFood.setText(Html.fromHtml(listfood.get(position).getPrice()));

        return rowview;
    }
}
