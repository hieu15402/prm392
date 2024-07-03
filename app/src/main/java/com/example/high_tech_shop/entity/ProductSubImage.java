package com.example.high_tech_shop.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class ProductSubImage {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String url;
    private int productId;

    public ProductSubImage(int id, String url, int productId) {
        this.id = id;
        this.url = url;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
