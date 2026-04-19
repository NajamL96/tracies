package com.tracies.model;

import com.tracies.users;

import java.io.Serializable;
import java.util.ArrayList;

public class myordermodel implements Serializable {

    String description;
    int extraLarge;
    String image;
    int large;
    int medium;
    int price;
    int small;
    String title;
    String categoryID;
    String ItemID;
    String status = "";
    double totalPrice = 0;
    String timeStamp;
    String address;

    public myordermodel(){

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    ArrayList<Cart_Model>   Mdata = new ArrayList<>();
    ArrayList<users>   address_data = new ArrayList<>();



    public myordermodel(String description, int extraLarge, String image, int large, int medium,
                         int small, String title, String categoryID, String itemID) {
        this.description = description;
        this.extraLarge = extraLarge;
        this.image = image;
        this.large = large;
        this.medium = medium;

        this.small = small;
        this.title = title;
        this.categoryID = categoryID;
        this.ItemID = itemID;
    }

    public ArrayList<users> getAddress_data() {
        return address_data;
    }

    public void setAddress_data(ArrayList<users> address_data) {
        this.address_data = address_data;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<Cart_Model> getMdata() {
        return Mdata;
    }

    public void setMdata(ArrayList<Cart_Model> mdata) {
        Mdata = mdata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExtraLarge() {
        return extraLarge;
    }

    public void setExtraLarge(int extraLarge) {
        this.extraLarge = extraLarge;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLarge() {
        return large;
    }

    public void setLarge(int large) {
        this.large = large;
    }

    public int getMedium() {
        return medium;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public int getSmall() {
        return small;
    }

    public void setSmall(int small) {
        this.small = small;
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
