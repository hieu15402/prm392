package com.example.high_tech_shop.entity.relationship;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.high_tech_shop.entity.Product;
import com.example.high_tech_shop.entity.ProductSubImage;

import java.util.List;

public class ProductWithSubImages {
    @Embedded
    private Product product;

    @Relation(
            parentColumn = "id",
            entityColumn = "productId"
    )
    private List<ProductSubImage> productSubImages;
}
