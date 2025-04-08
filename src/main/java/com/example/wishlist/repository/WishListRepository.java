package com.example.wishlist.repository;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.wishlist.model.UserEntity;
import com.example.wishlist.model.WishList;
import com.example.wishlist.model.WishListItem;


@Repository
public class WishListRepository {
    private JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//--------------find/rowMappers/SELECT Operations----------
    public List<WishListItem> findAllItemsId(int itemId) {
        String sql = "SELECT * FROM items WHERE itemId = ?";
        RowMapper<WishListItem> rowMapper = new BeanPropertyRowMapper<>(WishListItem.class);
        return jdbcTemplate.query(sql, rowMapper, itemId);
    }
    public List<WishListItem> findItem(int itemName) {
        String sql = "SELECT * FROM items WHERE itemId = ?";
        RowMapper<WishListItem> rowMapper = new BeanPropertyRowMapper<>(WishListItem.class);
        return jdbcTemplate.query(sql, rowMapper, itemName);
    }

    public List<UserEntity> findAllUsers() {
        String sql = "SELECT * FROM users";
        RowMapper<UserEntity> rowMapper = new BeanPropertyRowMapper<>(UserEntity.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public UserEntity findUserName(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        RowMapper<UserEntity> rowMapper = new BeanPropertyRowMapper<>(UserEntity.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }

    public List<WishList>findAllWishLists(String username){
        String sql ="SELECT * FROM wishList WHERE userId =(SELECT userId FROM users WHERE username = ?)";
        RowMapper<WishList>rowMapper = new BeanPropertyRowMapper<>(WishList.class);
        List<WishList> wishlists = jdbcTemplate.query(sql,rowMapper,username);
        
        // Load items for each wishlist
        for (WishList wishlist : wishlists) {
            wishlist.setWishListItems(findItemsByWishListId(wishlist.getWishListId()));
        }
        return wishlists;
    }

    public List<WishListItem> findItemsByWishListId(int wishListId) {
        String sql = "SELECT * FROM items WHERE wishListId = ?";
        RowMapper<WishListItem> rowMapper = new BeanPropertyRowMapper<>(WishListItem.class);
        return jdbcTemplate.query(sql, rowMapper, wishListId);
    }
    //-------------Delete
public boolean deleteItem(int itemId){
        String sql ="DELETE FROM items WHERE itemId =?";
        return jdbcTemplate.update(sql,itemId)>0;
}
public boolean deleteWishList(int wishListId){
    // First delete all items in the wishlist
    String deleteItemsSql = "DELETE FROM items WHERE wishListId = ?";
    jdbcTemplate.update(deleteItemsSql, wishListId);
    
    // Then delete the wishlist itself
    String deleteWishListSql = "DELETE FROM wishList WHERE wishListId = ?";
    return jdbcTemplate.update(deleteWishListSql, wishListId) > 0;
}
    //-------------Add /sql update/ insert into operations---------
    public void updateItem(WishListItem wishListItem,int itemId){
        String sql="UPDATE items SET itemName =?, itemDescription =?, itemPrice=?, itemLink=? WHERE itemId=?";
        jdbcTemplate.update(sql,wishListItem.getItemId(),wishListItem.getItemName(),wishListItem.getItemDescription(),wishListItem.getItemPrice(),wishListItem.getItemLink());
    }
    //-------------------
    public void addUser(UserEntity userEntity) {
        String sql = "INSERT INTO users (userId, username, email, password) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, userEntity.getUserId(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword());
    }

    public int getMaxUserId() {
        String sql = "SELECT MAX(userId) FROM users";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public void addWishList(WishList wishList, String username){
        String sql = "INSERT INTO wishList(wishListName,userId) VALUES(?,(SELECT userId FROM users WHERE username = ?))";
        jdbcTemplate.update(sql,wishList.getWishListName(),username);
    }
    public void addItemToWishList(WishListItem wishListItem, int wishListId) {
        String sql = "INSERT INTO items (itemName, itemDescription, itemPrice, itemLink, wishListId) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            wishListItem.getItemName(),
            wishListItem.getItemDescription(),
            wishListItem.getItemPrice(),
            wishListItem.getItemLink(),
            wishListId
        );
    }

}
