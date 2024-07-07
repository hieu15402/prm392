package com.example.high_tech_shop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.high_tech_shop.R;
import com.example.high_tech_shop.entity.Cart;
import com.example.high_tech_shop.entity.CartItem;
import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.repositories.CartItemRepository;
import com.example.high_tech_shop.repositories.CartRepository;
import com.example.high_tech_shop.repositories.ProductRepository;
import com.example.high_tech_shop.user.CartActivity;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    List<CartItem> cartItems;
    Context context;
    ProductRepository productRepository;
    CartItemRepository cartItemRepository;
    CartRepository cartRepository;
    Cart cart;
    public CartAdapter(List<CartItem> cartItems, Context context, Cart cart) {
        this.cartItems = cartItems;
        this.context = context;
        this.cart = cart;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart_item,parent,false);
        context = parent.getContext();
        productRepository = new ProductRepository(context);
        cartItemRepository = new CartItemRepository(context);
        cartRepository = new CartRepository(context);
        double total1 = cart.getTotalPrice()+cart.getTotalPrice()/10;
        ((CartActivity) context).update("$"+cart.getTotalPrice(),"$"+total1,"$"+cart.getTotalPrice()/10);
        return new CartAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Product product = productRepository.get(cartItems.get(position).getProductId());

        holder.txtTitle.setText(product.getName());
        holder.count.setText(String.valueOf(cartItems.get(position).getQuantity()));
        holder.txtPrice.setText("$"+ product.getPrice());
        holder.totalPrice.setText("$"+ cartItems.get(position).getPrice());
        int drawableResourceId = holder.itemView.getResources().getIdentifier(product.getCoverImage()
                ,"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(context)
                //.load(products.get(position).getCoverImage())
                .load(drawableResourceId)
                //.placeholder(drawableResourceId)
                .transform(new GranularRoundedCorners(30,30,30,30))
                .error(R.drawable.ic_launcher_background)
                .into(holder.pic);
        int adapterPosition = holder.getAdapterPosition();
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("NotifyDataSetChanged")
            public void onClick(View v) {
                CartItem cartItem = cartItems.get(adapterPosition);
                if(cartItem.getQuantity() == 1){
                    cartItemRepository.delete(cartItems.get(adapterPosition));
                    cartItems.remove(adapterPosition);
                    cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());
                    cartRepository.update(cart);
                    double total1 = cart.getTotalPrice()+cart.getTotalPrice()/10;
                    ((CartActivity) context).update("$"+cart.getTotalPrice(),"$"+total1,"$"+cart.getTotalPrice()/10);
                    notifyDataSetChanged();
                    return;
                }
                cartItem.setQuantity(cartItem.getQuantity()-1);
                cartItem.setPrice(cartItem.getPrice()-product.getPrice());
                cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());
                cartItemRepository.update(cartItem);
                cartRepository.update(cart);
                double total1 = cart.getTotalPrice()+cart.getTotalPrice()/10;
                ((CartActivity) context).update("$"+cart.getTotalPrice(),"$"+total1,"$"+cart.getTotalPrice()/10);
                notifyDataSetChanged();
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            @SuppressLint("NotifyDataSetChanged")
            public void onClick(View v) {
                CartItem cartItem = cartItems.get(adapterPosition);
                cartItem.setQuantity(cartItem.getQuantity()+1);
                cartItem.setPrice(cartItem.getPrice()+product.getPrice());
                cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
                cartItemRepository.update(cartItem);
                cartRepository.update(cart);
                double total1 = cart.getTotalPrice()+cart.getTotalPrice()/10;
                ((CartActivity) context).update("$"+cart.getTotalPrice(),"$"+total1,"$"+cart.getTotalPrice()/10);
                notifyDataSetChanged();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                cart.setTotalPrice(cart.getTotalPrice() - cartItems.get(adapterPosition).getPrice());
                cartItemRepository.delete(cartItems.get(adapterPosition));
                cartItems.remove(adapterPosition);
                cartRepository.update(cart);
                double total1 = cart.getTotalPrice()+cart.getTotalPrice()/10;
                ((CartActivity) context).update("$"+cart.getTotalPrice(),"$"+total1,"$"+cart.getTotalPrice()/10);
                notifyDataSetChanged();
                Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtPrice, totalPrice,minus, add,count;
        ImageView pic,delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.textView30);
            txtPrice = itemView.findViewById(R.id.textView32);
            minus = itemView.findViewById(R.id.textView36);
            add = itemView.findViewById(R.id.textView33);
            totalPrice = itemView.findViewById(R.id.textView34);
            count = itemView.findViewById(R.id.etCount);
            pic = itemView.findViewById(R.id.imageView11);
            delete = itemView.findViewById(R.id.imageView16);
        }
    }
}
