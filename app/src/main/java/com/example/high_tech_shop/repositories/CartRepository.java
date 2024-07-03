package com.example.high_tech_shop.repositories;

import android.content.Context;

import com.example.high_tech_shop.dao.CartDAO;
import com.example.high_tech_shop.entity.Cart;
import com.example.high_tech_shop.room.HighTechShopRoomDatabase;

import java.util.List;

public class CartRepository {

    private CartDAO cartDAO;

    public CartRepository(Context context) {
        this.cartDAO = HighTechShopRoomDatabase.getInstance(context).cartDAO();
    }

    public void insertAll(List<Cart> carts) {
        cartDAO.insertCart(carts);
    }
    public Cart getCartByUserIdTrue(int userId) {
        return cartDAO.getCartByUserIdTrue(userId);
    }
    public void insert(Cart cart){
        cartDAO.insert(cart);
    }
    public void update(Cart cart){
        cartDAO.update(cart);
    }
    public void updateAllCartsStatusToFalse(int userId){
        cartDAO.updateAllCartsStatusToFalse(userId);
    }
    public List<Cart> getAll(){
        return cartDAO.getAll();
    }
    public void delete(Cart cart){cartDAO.delete(cart);}
    public void deleteAll(){cartDAO.deleteAll();}

}
