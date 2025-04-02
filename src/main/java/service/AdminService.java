package service;
import exception.ErrorMessage;
import model.Admin;
import model.UserEntity;
import model.WishListItem;

import java.util.List;
import java.util.Optional;

public interface AdminService {

List<UserEntity>getUsers() throws ErrorMessage;

Admin adminRegister(Admin admin)throws ErrorMessage;

WishListItem addWishListItem(WishListItem wishLstItem)throws ErrorMessage;

void deleteUser(int userId)throws ErrorMessage;

void deleteWishListItem(int wishListItemId)throws ErrorMessage;

List<WishListItem> getWishListItems()throws ErrorMessage;

void updateWishListItem(int wishListId, WishListItem wishListItem)throws ErrorMessage;

Optional<Admin> findAdminByMail(String email) throws ErrorMessage;
}
