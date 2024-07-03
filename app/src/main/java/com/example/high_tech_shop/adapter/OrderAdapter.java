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
import com.example.high_tech_shop.entity.OrderItem;
import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.repositories.ProductRepository;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private final Context context;
    private final List<OrderItem> list;
    private final ProductRepository productRepository;
    public OrderAdapter(Context context, List<OrderItem> list) {
        this.context = context;
        this.list = list;
        productRepository = new ProductRepository(context);
    }

    @Override
    public int getCount() {
        return list.size();
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
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_order_item, null);
            holder.productImg = convertView.findViewById(R.id.imageView27);
            holder.txtTitle = convertView.findViewById(R.id.textView41);
            holder.txtPrice = convertView.findViewById(R.id.textView42);
            holder.totalPrice = convertView.findViewById(R.id.textView43);
            holder.txtQuantity = convertView.findViewById(R.id.etQuantity);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderItem item = list.get(position);
        Product product = productRepository.get(item.getProductId());

        holder.txtTitle.setText(product.getName());
        holder.txtPrice.setText("$"+product.getPrice());
        holder.totalPrice.setText("$"+item.getPrice());
        holder.txtQuantity.setText(""+item.getQuantity());

        int drawableResourceId = context.getResources().getIdentifier(product.getCoverImage(), "drawable", context.getPackageName());
        Glide.with(context)
                .load(drawableResourceId)
                .transform(new GranularRoundedCorners(30,30,30,30))
                .error(R.drawable.ic_launcher_background)
                .into(holder.productImg);

        return convertView;
    }
    private static class ViewHolder {
        ImageView productImg;
        TextView txtTitle, txtPrice, totalPrice, txtQuantity;
    }
}
