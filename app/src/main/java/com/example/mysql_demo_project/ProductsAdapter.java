package com.example.mysql_demo_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.mysql_demo_project.utils.PriceUtils;

import java.util.ArrayList;

public class ProductsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> products;
    private LayoutInflater inflater;
    public int selectingId = 0;

    public ProductsAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return products.get(i).id;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.activity_product_item, viewGroup, false);
        }

        Product product = products.get(index);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvPrice = view.findViewById(R.id.tvPrice);

        tvName.setText(product.name);
        tvPrice.setText(PriceUtils.formatPrice(product.price));

        int backgroundColorId = (product.id == selectingId) ? R.color.colorItemSelected : android.R.color.transparent;

        view.setBackgroundColor(ContextCompat.getColor(context, backgroundColorId));

        return view;
    }
}
