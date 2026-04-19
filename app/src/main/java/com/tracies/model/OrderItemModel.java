package com.tracies.model;

import java.util.ArrayList;

public class OrderItemModel {

    String categoryID;
    String ItemID;
    String image;
    String price;
    String title;

    ArrayList<String> Listimages = new ArrayList<>();

    public ArrayList<String> getListimages() {
        return Listimages;
    }

    public void setListimages(ArrayList<String> listimages) {
        Listimages = listimages;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
