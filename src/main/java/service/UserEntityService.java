package service;

import java.util.List;
import java.util.Optional;

public interface UserEntityService {
    UserEntity createUserAccount(UserEntity uEntity) throws ErrorMessage;
    void deleteUser(int userId) throws ErrorMessage;
    void updateUser(int userId, UserEntity uEntity) throws ErrorMessage;
    UserEntity getUserById(int userId) throws ErrorMessage;
    WishlistItem addItem(int userId, int wishListId) throws ErrorMessage;
    WishlistItem deleteItem(int userId, int wishListId) throws ErrorMessage;
    Optional<UserEntity> findByEmail(String email);
    List<WishlistItem> getAllItems(int userId) throws ErrorMessage;
}
