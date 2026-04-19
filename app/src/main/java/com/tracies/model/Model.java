package com.tracies.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable {

    String description;
    String image;
    int price;
    String name;
    String title;
    String categoryID;
    String ItemID;
    String headline;
    int totalitem;
    int small;
    int extraLarge;
    int medium;
    int large;

    boolean selected;
    String type;
    boolean fav;
    boolean wait;
    String address;

    public Model(){

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalitem() {
        return totalitem;
    }

    public void setTotalitem(int totalitem) {
        this.totalitem = totalitem;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    ArrayList<String> Listimages = new ArrayList<>();

    public ArrayList<String> getListimages() {
        return Listimages;
    }

    public void setListimages(ArrayList<String> listimages) {
        Listimages = listimages;
    }



    public int getSmall() {
        return small;
    }

    public void setSmall(int small) {
        this.small = small;
    }

    public int getExtraLarge() {
        return extraLarge;
    }

    public void setExtraLarge(int extraLarge) {
        this.extraLarge = extraLarge;
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

    public Model(String headline) {
        this.headline = headline;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
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

    public String getDescription() {
        return description;
    }



    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public void setTitle(String title) {
        this.title = title;
    }
}
