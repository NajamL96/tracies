package com.tracies.model;

public class FavoriteModel {
    String categoryID = "";
    String productID = "";

    public FavoriteModel(String categoryID, String productID) {
        this.categoryID = categoryID;
        this.productID = productID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getProductID() {
        return productID;
    }
}
