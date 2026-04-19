package com.tracies.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart_Model implements Serializable {

    String description;
    String image;
    int price;
    String title;
    String itemID;
    String categoryID;
    boolean selected;
    int totalPrice;
    String status;
    int totalitem;
    String size;
    int small;
    int medium;
    int large;
    int extraLarge;
    String timeStamp;
    String address;
    String CartID;

    public Cart_Model(){

    }


    ArrayList<Cart_Model> cart = new ArrayList<>();
    ArrayList<Cart_Model> wholeSaleCart = new ArrayList<>();

    public ArrayList<Cart_Model> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Cart_Model> cart) {
        this.cart = cart;
    }

    public ArrayList<Cart_Model> getWholeSaleCart() {
        return wholeSaleCart;
    }

    public void setWholeSaleCart(ArrayList<Cart_Model> wholeSaleCart) {
        this.wholeSaleCart = wholeSaleCart;
    }

    public String getCartID() {
        return CartID;
    }

    public void setCartID(String cartID) {
        CartID = cartID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSmall() {
        return small;
    }

    public void setSmall(int small) {
        this.small = small;
    }

    public int getMedium() {
        return medium;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public int getLarge() {
        return large;
    }

    public void setLarge(int large) {
        this.large = large;
    }

    public int getExtraLarge() {
        return extraLarge;
    }

    public void setExtraLarge(int extraLarge) {
        this.extraLarge = extraLarge;
    }

    public int getTotalitem() {
        return totalitem;
    }

    public void setTotalitem(int totalitem) {
        this.totalitem = totalitem;
    }

    ArrayList<String> Listimages = new ArrayList<>();

    public ArrayList<String> getListimages() {
        return Listimages;
    }

    public void setListimages(ArrayList<String> listimages) {
        Listimages = listimages;
    }


    public Cart_Model(boolean selected) {
        this.selected = selected;
    }

    public Cart_Model(String description, int extraLarge, String image, int large, int medium, String price, int small, String title, String itemID, String categoriesId) {
        this.description = description;
        this.image = image;
        this.title = title;
        this.itemID = itemID;
        this.categoryID = categoryID;


    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
}
