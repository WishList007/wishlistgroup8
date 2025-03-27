package model;

import java.util.List;

public class UserEntity {
    private String username;
    private String password;
    private int userId;
    private List<WishListItem> wishListItem;

    public UserEntity(String username, String password, int userId, List<WishListItem> wishListItem){
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.wishListItem = wishListItem;
    }
    public UserEntity(){
    }
    //------setters---(voids)-------
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
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
    private int getUserId(){
        return userId;
    }
    private List<WishListItem>getWishListItem(){
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
