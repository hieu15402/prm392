package com.example.high_tech_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.user.FilterProductActivity;

import java.util.List;

public class FilterProductAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;

    public FilterProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_filter_product_item, null);
            holder.productImg = convertView.findViewById(R.id.imageView21);
            holder.addImg = convertView.findViewById(R.id.imageView22);
            holder.nameTxt = convertView.findViewById(R.id.textView39);
            holder.priceTxt = convertView.findViewById(R.id.textView40);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = products.get(position);
        holder.nameTxt.setText(product.getName());
        holder.priceTxt.setText("$"+product.getPrice());

        int drawableResourceId = context.getResources().getIdentifier(product.getCoverImage(), "drawable", context.getPackageName());
        Glide.with(context)
                //.load(products.get(position).getCoverImage())
                .load(drawableResourceId)
                //.placeholder(drawableResourceId)
                .transform(new GranularRoundedCorners(30,30,30,30))
                .error(R.drawable.ic_launcher_background)
                .into(holder.productImg);
        holder.addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FilterProductActivity) context).addToCart(product);
            }
        });
        return convertView;

    }
    private static class ViewHolder {
        ImageView productImg, addImg;
        TextView nameTxt, priceTxt;
    }
}
