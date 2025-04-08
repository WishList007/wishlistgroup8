package com.example.wishlist.model;

import java.util.ArrayList;
import java.util.List;

public class WishList {
    private int wishListId;
    private String wishListName;
    private String wishListDescription;
    private int userId;
    private List<WishListItem> wishListItems;

    public WishList(int wishListId, String wishListName, String wishListDescription, List<WishListItem> wishListItems) {
        this.wishListId = wishListId;
        this.wishListName = wishListName;
        this.wishListDescription = wishListDescription;
        this.wishListItems = wishListItems != null ? wishListItems : new ArrayList<>();
    }

    public WishList() {
        this.wishListItems = new ArrayList<>();
    }

    //----------setters----------
    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public void setWishListName(String wishListName) {
        this.wishListName = wishListName;
    }

    public void setWishListDescription(String wishListDescription) {
        this.wishListDescription = wishListDescription;
    }

    public void setWishListItems(List<WishListItem> wishListItems) {
        this.wishListItems = wishListItems != null ? wishListItems : new ArrayList<>();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //----------Getters-----------
    public int getWishListId() {
        return wishListId;
    }

    public String getWishListName() {
        return wishListName;
    }

    public String getWishListDescription() {
        return wishListDescription;
    }

    public List<WishListItem> getWishListItems() {
        return wishListItems != null ? wishListItems : new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }
}
