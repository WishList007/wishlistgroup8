package model;

import java.util.List;

public class WishList {
    private int wishListId;
    private String wishListName;
    private String wishListDescription;
    private List<WishListItem> wishListItems;

    public WishList(int wishListId, String wishListName,String wishListDescription,List<WishListItem>wishListItems){
        this.wishListId = wishListId;
        this.wishListName = wishListName;
        this.wishListDescription = wishListDescription;
        this.wishListItems = wishListItems;
    }
    public WishList(){
    }
    //----------setters----------
    public void setWishListId(int wishListId){
        this.wishListId = wishListId;
    }
    public void setWishListName(String wishListName){
        this.wishListName = wishListName;
    }
    public void setWishListDescription(String wishListDescription){
        this.wishListDescription = wishListDescription;
    }
    public void setWishListItems(List<WishListItem>wishListItems){
        this.wishListItems = wishListItems;
    }
    //----------Getters-----------
    public int getWishListId(){
        return wishListId;
    }
    public String getWishListName(){
        return wishListName;
    }
    public String getWishListDescription(){
        return wishListDescription;
    }
    public List<WishListItem> getWishListItems(){
        return wishListItems;

    }

}
