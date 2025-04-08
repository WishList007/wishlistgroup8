package com.example.wishlist.model;

public class WishListItem {
private int itemId;
private String itemName;
private String itemDescription;
private double itemPrice;
private String itemLink;
private int wishListId;

public WishListItem(int itemId, String itemName, String itemDescription, double itemPrice, String itemLink, int wishListId){
    this.itemId = itemId;
    this.itemName = itemName;
    this.itemDescription = itemDescription;
    this.itemPrice = itemPrice;
    this.itemLink = itemLink;
    this.wishListId = wishListId;
}
public WishListItem(){

}
//--------Getters----------
    public int getItemId(){
        return itemId;
    }
    public String getItemName(){
        return itemName;
    }
    public String getItemDescription(){
        return itemDescription;
    }
    public double getItemPrice(){
        return itemPrice;
    }
    public String getItemLink(){
        return itemLink;
    }
    public int getWishListId() {
        return wishListId;
    }
    //-----------------Setters----------
    public void setItemId(int itemId){
        this.itemId = itemId;
    }
    public void setItemName (String itemName){
        this.itemName = itemName;
    }
    public void setItemDescription (String itemDescription){
        this.itemDescription = itemDescription;
    }
    public void setItemPrice (double itemPrice){
        this.itemPrice = itemPrice;
    }
    public void setItemLink(String itemLink){
        this.itemLink = itemLink;
    }
    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }
}
