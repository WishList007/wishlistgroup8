package com.example.wishlist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.wishlist.model.UserEntity;
import com.example.wishlist.model.WishList;
import com.example.wishlist.model.WishListItem;
import com.example.wishlist.repository.WishListRepository;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void addUser(UserEntity user) {
        wishListRepository.addUser(user);
    }

    public int getMaxUserId() {
        return wishListRepository.getMaxUserId();
    }

    public void addWishList(WishList wishList, String username) {
        wishListRepository.addWishList(wishList, username);
    }

    public void addItemToWishList(WishListItem item, int wishListId) {
        wishListRepository.addItemToWishList(item, wishListId);
    }

    public List<WishList> getAllWishLists(String username) {
        return wishListRepository.getWishListsByUsername(username);
    }

    public WishListItem getItemById(int itemId) {
        return wishListRepository.getItemById(itemId);
    }

    public List<UserEntity> getAllUsers() {
        return wishListRepository.getAllUsers();
    }

    public UserEntity getUserByUsername(String username) {
        return wishListRepository.getUserByUsername(username);
    }

    public UserEntity getUserById(int userId) {
        return wishListRepository.getUserById(userId);
    }

    public String getUsernameForWishlist(int wishListId) {
        WishList wishlist = wishListRepository.getWishListById(wishListId);
        if (wishlist == null) {
            return null;
        }
        UserEntity user = getUserById(wishlist.getUserId());
        if (user == null) {
            return null;
        }
        return user.getUsername();
    }

    public boolean deleteWishList(int wishListId) {
        return wishListRepository.deleteWishList(wishListId);
    }

    public boolean deleteItem(int itemId) {
        return wishListRepository.deleteItem(itemId);
    }

    public void updateItem(WishListItem item) {
        wishListRepository.updateItem(item);
    }

    public List<WishListItem> getItemsByWishListId(int wishListId) {
        return wishListRepository.getItemsByWishListId(wishListId);
    }
}
