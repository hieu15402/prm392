package com.example.high_tech_shop.entity.relationship;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.high_tech_shop.entity.Category;
import com.example.high_tech_shop.entity.Product;

public class ProductWithCategory {
    @Embedded
    private Product product;

    @Relation(
            parentColumn = "categoryId",
            entityColumn = "id"
    )
    private Category category;
}
