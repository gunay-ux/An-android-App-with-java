package com.example.camefridge;

public class FavoriteProduct {
    private int product_id;
    private String product_name;
    private int product_limit;



    public FavoriteProduct(int product_id, String product_name, int product_limit) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_limit = product_limit;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_limit(int product_limit) {
        this.product_limit = product_limit;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getProduct_limit() {
        return product_limit;
    }
}
