package com.example.wishlist.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.wishlist.model.UserEntity;
import com.example.wishlist.model.WishList;
import com.example.wishlist.model.WishListItem;

@SpringBootTest
@Sql(
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
    scripts = {"classpath:h2init.sql"}
)
@Transactional
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    // -------------------- User Tests --------------------

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        List<UserEntity> users = wishListRepository.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("ChristianV")));
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("SofieM")));
    }

    @Test
    void getUserByUsername_ShouldReturnCorrectUser() {
        UserEntity user = wishListRepository.getUserByUsername("ChristianV");
        assertNotNull(user);
        assertEquals("christian.vinther@example.dk", user.getEmail());
    }

    @Test
    void getUserById_ShouldReturnCorrectUser() {
        UserEntity user = wishListRepository.getUserById(1);
        assertNotNull(user);
        assertEquals("ChristianV", user.getUsername());
    }

    @Test
    void addUser_ShouldCreateNewUser() {
        UserEntity newUser = new UserEntity();
        newUser.setUserId(3);
        newUser.setUsername("MortenH");
        newUser.setEmail("morten.hansen@example.dk");
        newUser.setPassword("password789");

        wishListRepository.addUser(newUser);

        UserEntity retrievedUser = wishListRepository.getUserByUsername("MortenH");
        assertNotNull(retrievedUser);
        assertEquals("morten.hansen@example.dk", retrievedUser.getEmail());
    }

    // -------------------- WishList Tests --------------------

    @Test
    void getWishListsByUsername_ShouldReturnCorrectWishlists() {
        List<WishList> wishlists = wishListRepository.getWishListsByUsername("ChristianV");
        assertEquals(2, wishlists.size());
        assertTrue(wishlists.stream().anyMatch(w -> w.getWishListName().equals("Fødselsdag Ønskeliste")));
        assertTrue(wishlists.stream().anyMatch(w -> w.getWishListName().equals("Jul Ønskeliste")));
    }

    @Test
    void getWishListById_ShouldReturnCorrectWishlist() {
        WishList wishlist = wishListRepository.getWishListById(1);
        assertNotNull(wishlist);
        assertEquals("Fødselsdag Ønskeliste", wishlist.getWishListName());
        assertEquals(2, wishlist.getWishListItems().size());
    }

    @Test
    void addWishList_ShouldCreateNewWishlist() {
        WishList newWishList = new WishList();
        newWishList.setWishListName("Dimission Ønskeliste");

        wishListRepository.addWishList(newWishList, "ChristianV");

        List<WishList> wishlists = wishListRepository.getWishListsByUsername("ChristianV");
        assertTrue(wishlists.stream().anyMatch(w -> w.getWishListName().equals("Dimission Ønskeliste")));
    }

    @Test
    void deleteWishList_ShouldRemoveWishlistAndItems() {
        boolean deleted = wishListRepository.deleteWishList(1);
        assertTrue(deleted);

        WishList deletedWishlist = wishListRepository.getWishListById(1);
        assertNull(deletedWishlist);

        List<WishListItem> items = wishListRepository.getItemsByWishListId(1);
        assertTrue(items.isEmpty());
    }

    // -------------------- Item Tests --------------------

    @Test
    void getItemsByWishListId_ShouldReturnCorrectItems() {
        List<WishListItem> items = wishListRepository.getItemsByWishListId(1);
        assertEquals(2, items.size());
        assertTrue(items.stream().anyMatch(i -> i.getItemName().equals("Nike Air Force 1")));
        assertTrue(items.stream().anyMatch(i -> i.getItemName().equals("Monstera Plante")));
    }

    @Test
    void getItemById_ShouldReturnCorrectItem() {
        WishListItem item = wishListRepository.getItemById(1);
        assertNotNull(item);
        assertEquals("Nike Air Force 1", item.getItemName());
        assertEquals(899.99, item.getItemPrice());
    }

    @Test
    void addItemToWishList_ShouldCreateNewItem() {
        WishListItem newItem = new WishListItem();
        newItem.setItemName("Georg Jensen Vase");
        newItem.setItemDescription("Sølv vase, 20 cm høj");
        newItem.setItemPrice(1499.99);
        newItem.setItemLink("https://example.dk/georg-jensen");

        wishListRepository.addItemToWishList(newItem, 1);

        List<WishListItem> items = wishListRepository.getItemsByWishListId(1);
        assertTrue(items.stream().anyMatch(i -> i.getItemName().equals("Georg Jensen Vase")));
    }

    @Test
    void updateItem_ShouldModifyExistingItem() {
        WishListItem item = wishListRepository.getItemById(1);
        item.setItemPrice(799.99);
        item.setItemDescription("Hvide sneakers i str. 43");

        wishListRepository.updateItem(item);

        WishListItem updatedItem = wishListRepository.getItemById(1);
        assertEquals(799.99, updatedItem.getItemPrice());
        assertEquals("Hvide sneakers i str. 43", updatedItem.getItemDescription());
    }

    @Test
    void deleteItem_ShouldRemoveItem() {
        boolean deleted = wishListRepository.deleteItem(1);
        assertTrue(deleted);

        WishListItem deletedItem = wishListRepository.getItemById(1);
        assertNull(deletedItem);
    }

    // -------------------- Relationship Tests --------------------

    @Test
    void userWishListRelationship_ShouldBeCorrect() {
        // Add new wishlist to user
        WishList newWishList = new WishList();
        newWishList.setWishListName("Konfirmation Ønskeliste");
        wishListRepository.addWishList(newWishList, "ChristianV");

        // Verify user has the new wishlist
        List<WishList> wishlists = wishListRepository.getWishListsByUsername("ChristianV");
        assertTrue(wishlists.stream().anyMatch(w -> w.getWishListName().equals("Konfirmation Ønskeliste")));
    }

    @Test
    void wishListItemRelationship_ShouldBeCorrect() {
        // Add new item to wishlist
        WishListItem newItem = new WishListItem();
        newItem.setItemName("Bang & Olufsen Højtaler");
        newItem.setItemDescription("Trådløs højtaler i sort");
        newItem.setItemPrice(2999.99);
        wishListRepository.addItemToWishList(newItem, 1);

        // Verify wishlist has the new item
        List<WishListItem> items = wishListRepository.getItemsByWishListId(1);
        assertTrue(items.stream().anyMatch(i -> i.getItemName().equals("Bang & Olufsen Højtaler")));

        wishListRepository.deleteWishList(1);
        items = wishListRepository.getItemsByWishListId(1);
        assertTrue(items.isEmpty());
    }
}
