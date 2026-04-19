package com.tracies.model;

import java.io.Serializable;
import java.util.ArrayList;

public class favoritelistModel implements Serializable {

    String description;
    int extraLarge;
    String image;
    int large;
    int medium;
    int price;
    int small;
    String title;
    String itemID;
    String categoryID;
    boolean wait;
    boolean fav;
    int totalitem;
    boolean selected;


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getTotalitem() {
        return totalitem;
    }

    public void setTotalitem(int totalitem) {
        this.totalitem = totalitem;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public favoritelistModel(){

    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    ArrayList<String> Listimages = new ArrayList<>();

    public ArrayList<String> getListimages() {
        return Listimages;
    }

    public void setListimages(ArrayList<String> listimages) {
        Listimages = listimages;
    }

    public favoritelistModel(String description, int extraLarge, String image, int large, int medium, String price, int small, String title, String itemID, String categoriesId) {
        this.description = description;
        this.extraLarge = extraLarge;

        this.large = large;
        this.medium = medium;

        this.small = small;
        this.title = title;
        this.itemID = itemID;
        this.categoryID = categoriesId;

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

    public int getExtraLarge() {
        return extraLarge;
    }

    public void setExtraLarge(int extraLarge) {
        this.extraLarge = extraLarge;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }


}
