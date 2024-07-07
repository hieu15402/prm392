package com.example.high_tech_shop.repositories;

import android.content.Context;

import com.example.high_tech_shop.dao.CartItemDAO;
import com.example.high_tech_shop.entity.CartItem;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.util.List;

public class CartItemRepository {

    private CartItemDAO cartItemDAO;
    public CartItemRepository(Context context) {
        this.cartItemDAO = this.cartItemDAO = HighTechShopRoomDatabase.getInstance(context).cartItemDAO();
    }
    public void insert(List<CartItem> cartItem) {
        this.cartItemDAO.insertCartItem(cartItem);
    }
    public void insert(CartItem cartItem) {
        this.cartItemDAO.insertCartItem(cartItem);
    }
    public void delete(CartItem cartItem) {
        this.cartItemDAO.deleteCartItem(cartItem);
    }
    public void deleteAll() {
        this.cartItemDAO.deleteAll();
    }
    public void update(CartItem cartItem){
        cartItemDAO.updateCartItem(cartItem);
    }
    public List<CartItem> getAll(){
        return cartItemDAO.getCarts();
    }
    public CartItem getCartItemByCartAndProductId(int cartId, int productId) {
        return cartItemDAO.getCartItemByCartAndProductId(cartId,productId);
    }
    public List<CartItem> getCartItemByCartId(int cartId){
        return cartItemDAO.getCartItemByCartId(cartId);
    }
}
