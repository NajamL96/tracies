package com.tracies.model;

public class viewAllModel {

    String description;
    String image;
    String price;
    String title;
    String categoryID;
    String ItemID;



    public viewAllModel(){

    }

    public viewAllModel(String description, String image, String price, String title, String categoryID, String itemID) {
        this.description = description;
        this.image = image;
        this.price = price;
        this.title = title;
        this.categoryID = categoryID;
        ItemID = itemID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
