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

    public void addItemToWishList(WishListItem item, int itemId) {
        wishListRepository.addItemToWishList(item, itemId);
    }

    public List<WishList> getAllWishLists(String username) {
        return wishListRepository.findAllWishLists(username);
    }

    public List<WishListItem> getItemById(int itemId) {
        return wishListRepository.findAllItemsId(itemId);
    }

    public List<WishListItem> findItem(int itemId) {
        return wishListRepository.findItem(itemId);
    }

    public List<UserEntity> getAllUsers() {
        return wishListRepository.findAllUsers();
    }

    public UserEntity getUserByUsername(String username) {
        return wishListRepository.findUserName(username);
    }

    public boolean deleteWishList(int wishListId) {
        return wishListRepository.deleteWishList(wishListId);
    }

    public boolean deleteItem(int itemId) {
        return wishListRepository.deleteItem(itemId);
    }

    public void updateItem(WishListItem item, int itemId) {
        wishListRepository.updateItem(item, itemId);
    }
}
