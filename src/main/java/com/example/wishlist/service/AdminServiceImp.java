package com.example.wishlist.service;

import com.example.wishlist.exception.ErrorMessage;
import com.example.wishlist.model.Admin;
import com.example.wishlist.model.UserEntity;
import com.example.wishlist.model.WishListItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImp implements AdminService {

    @Override
    public List<UserEntity> getUsers() throws ErrorMessage {
        // TODO: Implement user retrieval logic
        throw new ErrorMessage("Not implemented yet");
    }

    @Override
    public Admin adminRegister(Admin admin) throws ErrorMessage {
        // TODO: Implement admin registration logic
        throw new ErrorMessage("Not implemented yet");
    }

    @Override
    public WishListItem addWishListItem(WishListItem wishLstItem) throws ErrorMessage {
        // TODO: Implement wishlist item addition logic
        throw new ErrorMessage("Not implemented yet");
    }

    @Override
    public void deleteUser(int userId) throws ErrorMessage {
        // TODO: Implement user deletion logic
        throw new ErrorMessage("Not implemented yet");
    }

    @Override
    public void deleteWishListItem(int wishListItemId) throws ErrorMessage {
        // TODO: Implement wishlist item deletion logic
        throw new ErrorMessage("Not implemented yet");
    }

    @Override
    public List<WishListItem> getWishListItems() throws ErrorMessage {
        // TODO: Implement wishlist items retrieval logic
        throw new ErrorMessage("Not implemented yet");
    }

    @Override
    public void updateWishListItem(int wishListId, WishListItem wishListItem) throws ErrorMessage {
        // TODO: Implement wishlist item update logic
        throw new ErrorMessage("Not implemented yet");
    }

    @Override
    public Optional<Admin> findAdminByMail(String email) throws ErrorMessage {
        // TODO: Implement admin lookup by email logic
        throw new ErrorMessage("Not implemented yet");
    }
}
