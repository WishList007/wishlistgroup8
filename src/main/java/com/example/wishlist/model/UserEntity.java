package com.example.wishlist.model;
import java.util.List;

public class UserEntity {
    private String username;
    private String password;
    private String email;
    private int userId;
    private List<WishListItem> wishListItem;

    public UserEntity(){}

    public UserEntity(String username, String password, String email, int userId, List<WishListItem> wishListItem){
        this.username = username;
        this.password = password;
        this.email = email;
        this.userId = userId;
        this.wishListItem = wishListItem;
    }
    //------setters---(voids)-------
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public void setWishListItem(List<WishListItem> wishListItem) {
        this.wishListItem = wishListItem;
    }

    //------getters-----(datatypes)
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public int getUserId(){
        return userId;
    }
    public List<WishListItem>getWishListItem(){
        return wishListItem;
    }
    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userId=" + userId +
                '}';
    }
}
