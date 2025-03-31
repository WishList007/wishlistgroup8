package service;
import exception.ErrorMessage;
import model.Admin;
import model.UserEntity;
import model.WishListItem;

import java.util.List;
public interface AdminService {

List<UserEntity>getUsers() throws ErrorMessage;
Admin adminRegister(Admin admin)throws ErrorMessage;
WishListItem addWishListItem(WishListItem wishLstItem)throws ErrorMessage;
void deleteUser(int userId)throws ErrorMessage;
void deleteWishListItem(int wishListItemId)throws ErrorMessage;
List<WishListItem>getAllWishListItems()throws ErrorMessage;
void updateWishList(int wishListId, WishListItem wishListItem)throws ErrorMessage;
Admin findAdminByMail(String email) throws ErrorMessage;
}
