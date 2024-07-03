package com.example.high_tech_shop.common;

import android.content.Context;
import android.widget.Toast;

import androidx.room.TypeConverters;

import com.example.high_tech_shop.entity.Category;
import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.entity.Role;
import com.example.high_tech_shop.entity.User;
import com.example.high_tech_shop.entity.UserAddress;
import com.example.high_tech_shop.repositories.CategoryRepository;
import com.example.high_tech_shop.repositories.ProductRepository;
import com.example.high_tech_shop.repositories.UserAddressRepository;
import com.example.high_tech_shop.repositories.UserRepository;

import java.util.Arrays;


public class DataCommon {
    public static void initData(Context context) {
        insertUser(context);
        insertProduct(context);
        insertCategory(context);
        insertUserAddress(context);
    }
    public static void removeData(Context context) {
        ProductRepository productRepository = new ProductRepository(context);
        UserRepository userRepository = new UserRepository(context);
        CategoryRepository categoryRepository = new CategoryRepository(context);
        UserAddressRepository userAddressRepository = new UserAddressRepository(context);
        productRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        userAddressRepository.deleteAll();
    }

    private static void insertUser(Context context){
        User user_1 = new User(1, "admin@gmail.com", "1234567890", "image.png", "10/04/2002", true, "12345", "admin", Role.ADMIN);
        User user_2 = new User(2, "shipper@gmail.com", "1234567890", "image.png", "10/04/2002", true, "12345", "shipper", Role.SHIPPER);
        User user_3 = new User(3, "user1@gmail.com", "1234567890", "image.png", "10/04/2002", true, "12345", "user1", Role.USER);
        User user_4 = new User(4, "user2@gmail.com", "1234567890", "image.png", "10/04/2002", true, "12345", "user2", Role.USER);
        User user_5 = new User(5, "user3@gmail.com", "1234567890", "image.png", "10/04/2002", true, "12345", "user3", Role.USER);

        UserRepository userRepository = new UserRepository(context);
        userRepository.insert(Arrays.asList(user_1, user_2, user_3, user_4, user_5));
    }
    private static void insertProduct(Context context) {
        Product product_1 = new Product(1, "Product 1", "Description 1",500,100, "pic1", true, 1);
        Product product_2 = new Product(2, "Product 2", "Description 2",500,100, "pic1", true, 1);
        Product product_3 = new Product(3, "Product 3", "Description 3",500,100, "pic1", true, 1);
        Product product_4 = new Product(4, "Product 4", "Description 4",500,100, "pic1", true, 1);
        Product product_5 = new Product(5, "Product 5", "Description 5",500,100, "pic1", true, 1);
        Product product_6 = new Product(6, "Product 6", "Description 6",500,100, "pic2", true, 2);
        Product product_7 = new Product(7, "Product 7", "Description 7",500,100, "pic2", true, 2);
        Product product_8 = new Product(8, "Product 8", "Description 8",500,100, "pic2", true, 2);
        Product product_9 = new Product(9, "Product 9", "Description 9",500,100, "pic2", true, 2);
        Product product_10 = new Product(10, "Product 10", "Description 10",500,100, "pic2", true, 2);

        Product product_11 = new Product(11, "Product 11", "Description 11",500,100, "pic3", true, 3);
        Product product_12 = new Product(12, "Product 12", "Description 12",500,100, "pic3", true, 3);
        Product product_13 = new Product(13, "Product 13", "Description 13",500,100, "pic3", true, 3);
        Product product_14 = new Product(14, "Product 14", "Description 14",500,100, "pic3", true, 3);
        Product product_15 = new Product(15, "Product 15", "Description 15",500,100, "pic3", true, 3);
        Product product_16 = new Product(16, "Product 16", "Description 16",500,100, "pic1", true, 4);
        Product product_17 = new Product(17, "Product 17", "Description 17",500,100, "pic2", true, 4);
        Product product_18 = new Product(18, "Product 18", "Description 18",500,100, "pic3", true, 4);
        Product product_19 = new Product(19, "Product 19", "Description 19",500,100, "pic2", true, 4);
        Product product_20 = new Product(20, "Product 20", "Description 20",500,100, "pic3", true, 4);

        ProductRepository productRepository = new ProductRepository(context);
        productRepository.insert(Arrays.asList(
                product_1, product_2, product_3, product_4, product_5,
                product_6, product_7, product_8, product_9, product_10,
                product_11, product_12, product_13, product_14, product_15,
                product_16, product_17, product_18, product_19, product_20
        ));
    }

    private static void insertCategory(Context context){
        Category category_1 = new Category(1, "Category 1");
        Category category_2 = new Category(2, "Category 2");
        Category category_3 = new Category(3, "Category 3");
        Category category_4 = new Category(4, "Category 4");

        CategoryRepository categoryRepository = new CategoryRepository(context);
        categoryRepository.insert(Arrays.asList(category_1, category_2, category_3, category_4));
    }

    private static void insertUserAddress(Context context){
        UserAddress userAddress_1 = new UserAddress(1, 1, "Long An", "Huyện Tân Thạnh", "Huyện Tân Thạnh", "Thị Trấn Tân Thạnh",true);
        UserAddress userAddress_2 = new UserAddress(2, 1, "Province 2", "Country 2", "12345", "City 2",false);
        UserAddress userAddress_3 = new UserAddress(3, 2, "Province 3", "Country 3", "12345", "City 3",true);
        UserAddress userAddress_4 = new UserAddress(4, 2, "Province 4", "Country 4", "12345", "City 4",false);
        UserAddress userAddress_5 = new UserAddress(5, 3, "Long An", "Huyện Tân Thạnh", "Huyện Tân Thạnh", "Thị Trấn Tân Thạnh",true);
        UserAddress userAddress_6 = new UserAddress(6, 3, "Province 6", "Country 6", "12345", "City 6",false);
        UserAddress userAddress_7 = new UserAddress(7, 4, "Province 7", "Country 7", "12345", "City 7",true);
        UserAddress userAddress_8 = new UserAddress(8, 4, "Province 8", "Country 8", "12345", "City 8",false);
        UserAddress userAddress_9 = new UserAddress(9, 5, "Province 9", "Country 9", "12345", "City 9",true);
        UserAddress userAddress_10 = new UserAddress(10, 5, "Province 10", "Country 10", "12345", "City 10",false);

        UserAddressRepository userAddressRepository = new UserAddressRepository(context);
        userAddressRepository.insert(Arrays.asList(userAddress_1, userAddress_2, userAddress_3, userAddress_4, userAddress_5, userAddress_6, userAddress_7, userAddress_8, userAddress_9, userAddress_10));
    }


}
