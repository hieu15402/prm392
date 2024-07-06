package com.example.high_tech_shop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.entity.User;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;
    private Context context;
    private User user;
    private OnItemClickListener onItemClickListener;

    public ProductAdapter(List<Product> products, Context context, User user) {
        this.products = products;
        this.context = context;
        this.user = user;
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_popular_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = products.get(position);
        holder.titleTxt.setText(product.getName());
        holder.feeTxt.setText("$" + product.getPrice());
        holder.unitInStockTxt.setText("Unit in Stock: " + product.getUnitInStock());

        String coverImageBase64 = product.getCoverImage();
        if (coverImageBase64 != null && !coverImageBase64.isEmpty()) {
            Bitmap bitmap = convertStringToBitmap(coverImageBase64);
            if (bitmap != null) {
                Glide.with(context)
                        .load(bitmap)
                        .error(R.drawable.ic_plus_sign)
                        .transform(new GranularRoundedCorners(30, 30, 0, 0))
                        .into(holder.pic);
            } else {
                holder.pic.setImageResource(R.drawable.ic_plus_sign);
            }
        } else {
            holder.pic.setImageResource(R.drawable.ic_plus_sign);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, feeTxt, unitInStockTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            feeTxt = itemView.findViewById(R.id.textView31);
            unitInStockTxt = itemView.findViewById(R.id.unitInStockTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }

    private Bitmap convertStringToBitmap(String str) {
        try {
            byte[] byteArray = Base64.decode(str, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
