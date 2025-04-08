package com.example.wishlist.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.wishlist.model.UserEntity;
import com.example.wishlist.model.WishList;
import com.example.wishlist.model.WishListItem;

@Repository
public class WishListRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // -------------------- READ OPERATIONS --------------------

    public List<UserEntity> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            UserEntity user = new UserEntity();
            user.setUserId(rs.getInt("userId"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        });
    }

    public UserEntity getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                UserEntity user = new UserEntity();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public UserEntity getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE userId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                UserEntity user = new UserEntity();
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<WishList> getWishListsByUsername(String username) {
        String sql = "SELECT * FROM wishList WHERE userId = (SELECT userId FROM users WHERE username = ?)";
        List<WishList> wishlists = jdbcTemplate.query(sql, (rs, rowNum) -> {
            WishList wishlist = new WishList();
            wishlist.setWishListId(rs.getInt("wishListId"));
            wishlist.setWishListName(rs.getString("wishListName"));
            wishlist.setUserId(rs.getInt("userId"));
            return wishlist;
        }, username);
        
        // Load items for each wishlist
        for (WishList wishlist : wishlists) {
            wishlist.setWishListItems(getItemsByWishListId(wishlist.getWishListId()));
        }
        return wishlists;
    }

    public WishList getWishListById(int wishListId) {
        String sql = "SELECT * FROM wishList WHERE wishListId = ?";
        try {
            WishList wishlist = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                WishList wl = new WishList();
                wl.setWishListId(rs.getInt("wishListId"));
                wl.setWishListName(rs.getString("wishListName"));
                wl.setUserId(rs.getInt("userId"));
                return wl;
            }, wishListId);
            
            if (wishlist != null) {
                // Load associated items
                wishlist.setWishListItems(getItemsByWishListId(wishListId));
            }
            return wishlist;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<WishListItem> getItemsByWishListId(int wishListId) {
        String sql = "SELECT * FROM items WHERE wishListId = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            WishListItem item = new WishListItem();
            item.setItemId(rs.getInt("itemId"));
            item.setItemName(rs.getString("itemName"));
            item.setItemDescription(rs.getString("itemDescription"));
            item.setItemPrice(rs.getDouble("itemPrice"));
            item.setItemLink(rs.getString("itemLink"));
            item.setWishListId(rs.getInt("wishListId"));
            return item;
        }, wishListId);
    }

    public WishListItem getItemById(int itemId) {
        String sql = "SELECT * FROM items WHERE itemId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                WishListItem item = new WishListItem();
                item.setItemId(rs.getInt("itemId"));
                item.setItemName(rs.getString("itemName"));
                item.setItemDescription(rs.getString("itemDescription"));
                item.setItemPrice(rs.getDouble("itemPrice"));
                item.setItemLink(rs.getString("itemLink"));
                item.setWishListId(rs.getInt("wishListId"));
                return item;
            }, itemId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int getMaxUserId() {
        String sql = "SELECT MAX(userId) FROM users";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // -------------------- CREATE OPERATIONS --------------------
    
    public void addUser(UserEntity userEntity) {
        String sql = "INSERT INTO users (userId, username, email, password) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            userEntity.getUserId(), 
            userEntity.getUsername(), 
            userEntity.getEmail(), 
            userEntity.getPassword()
        );
    }

    public void addWishList(WishList wishList, String username) {
        String sql = "INSERT INTO wishList (wishListName, userId) VALUES (?, (SELECT userId FROM users WHERE username = ?))";
        jdbcTemplate.update(sql, wishList.getWishListName(), username);
    }

    public void addItemToWishList(WishListItem item, int wishListId) {
        String sql = "INSERT INTO items (itemName, itemDescription, itemPrice, itemLink, wishListId) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            item.getItemName(),
            item.getItemDescription(),
            item.getItemPrice(),
            item.getItemLink(),
            wishListId
        );
    }

    // -------------------- UPDATE OPERATIONS --------------------

    public void updateItem(WishListItem item) {
        String sql = "UPDATE items SET itemName = ?, itemDescription = ?, itemPrice = ?, itemLink = ? WHERE itemId = ?";
        jdbcTemplate.update(sql, 
            item.getItemName(), 
            item.getItemDescription(), 
            item.getItemPrice(), 
            item.getItemLink(), 
            item.getItemId()
        );
    }

    // -------------------- DELETE OPERATIONS --------------------

    public boolean deleteItem(int itemId) {
        String sql = "DELETE FROM items WHERE itemId = ?";
        return jdbcTemplate.update(sql, itemId) > 0;
    }

    public boolean deleteWishList(int wishListId) {
        // First delete all items in the wishlist
        String deleteItemsSql = "DELETE FROM items WHERE wishListId = ?";
        jdbcTemplate.update(deleteItemsSql, wishListId);
        
        // Then delete the wishlist itself
        String deleteWishListSql = "DELETE FROM wishList WHERE wishListId = ?";
        return jdbcTemplate.update(deleteWishListSql, wishListId) > 0;
    }
}
