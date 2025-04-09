package com.example.wishlist.controller;

// JUnit and Mockito imports
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.wishlist.model.UserEntity;
import com.example.wishlist.model.WishList;
import com.example.wishlist.model.WishListItem;
import com.example.wishlist.service.WishListService;

@WebMvcTest(WishListController.class)
class WishListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishListService wishListService;

    private UserEntity testUser;
    private WishList testWishList;
    private WishListItem testWishListItem;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setUserId(1);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");

        testWishList = new WishList();
        testWishList.setWishListId(1);
        testWishList.setWishListName("Test Wishlist");
        testWishList.setWishListDescription("Test Description");

        testWishListItem = new WishListItem();
        testWishListItem.setItemId(1);
        testWishListItem.setItemName("Test Item");
        testWishListItem.setItemDescription("Test Item Description");
        testWishListItem.setItemPrice(99.99);
        testWishListItem.setWishListId(1);
    }

    @Test
    void homePageShouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void loginPageShouldDisplayLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void successfulLoginShouldRedirectToWishlist() throws Exception {
        when(wishListService.getUserByUsername("testuser")).thenReturn(testUser);

        mockMvc.perform(post("/login")
                .param("username", "testuser")
                .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/testuser"));
    }

    @Test
    void signupPageShouldDisplaySignupForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void successfulSignupShouldRedirectToLogin() throws Exception {
        when(wishListService.getMaxUserId()).thenReturn(0);
        when(wishListService.getUserByUsername("testuser")).thenReturn(null);

        mockMvc.perform(post("/signup")
                .param("username", "testuser")
                .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void viewUserWishlistsShouldDisplayWishlists() throws Exception {
        List<WishList> wishLists = Arrays.asList(testWishList);
        when(wishListService.getUserByUsername("testuser")).thenReturn(testUser);
        when(wishListService.getAllWishLists("testuser")).thenReturn(wishLists);

        mockMvc.perform(get("/wishlist/testuser")
                .sessionAttr("username", "testuser"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-wishlists"))
                .andExpect(model().attribute("wishLists", wishLists));
    }

    @Test
    void logoutShouldInvalidateSessionAndRedirectToLogin() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void showAddWishlistFormShouldDisplayForm() throws Exception {
        when(wishListService.getAllWishLists("testuser")).thenReturn(Arrays.asList(testWishList));

        mockMvc.perform(get("/wishlist/add/testuser")
                .sessionAttr("username", "testuser"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-wishlist"))
                .andExpect(model().attributeExists("wishLists"))
                .andExpect(model().attributeExists("username"));
    }

    @Test
    void addWishlistShouldCreateNewWishlist() throws Exception {
        when(wishListService.getAllWishLists("testuser")).thenReturn(Arrays.asList(testWishList));
        when(wishListService.getUsernameForWishlist(1)).thenReturn("testuser");

        mockMvc.perform(post("/wishlist/add")
                .param("username", "testuser")
                .param("listName", "New Wishlist")
                .param("description", "New Description")
                .sessionAttr("username", "testuser"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/view/1?username=testuser"));

        verify(wishListService, times(1)).addWishList(any(WishList.class), eq("testuser"));
    }

    @Test
    void deleteWishlistShouldRemoveWishlist() throws Exception {
        when(wishListService.getUsernameForWishlist(1)).thenReturn("testuser");

        mockMvc.perform(post("/wishlist/delete")
                .param("wishListId", "1")
                .param("username", "testuser")
                .sessionAttr("username", "testuser"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/testuser"));

        verify(wishListService, times(1)).deleteWishList(1);
    }

    @Test
    void showAddWishFormShouldDisplayForm() throws Exception {
        when(wishListService.getUsernameForWishlist(1)).thenReturn("testuser");
        when(wishListService.getAllWishLists("testuser")).thenReturn(Arrays.asList(testWishList));

        mockMvc.perform(get("/wishlist/wish/add/1")
                .param("username", "testuser")
                .sessionAttr("username", "testuser"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-wish"))
                .andExpect(model().attributeExists("wishListId"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("wishListName"));
    }

    @Test
    void addWishToListShouldCreateNewWish() throws Exception {
        when(wishListService.getUsernameForWishlist(1)).thenReturn("testuser");

        mockMvc.perform(post("/wishlist/wish/add")
                .param("itemName", "New Item")
                .param("itemDescription", "New Item Description")
                .param("itemPrice", "99.99")
                .param("itemLink", "https://example.com")
                .param("wishListId", "1")
                .param("username", "testuser")
                .sessionAttr("username", "testuser"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist/view/1?username=testuser"));

        verify(wishListService, times(1)).addItemToWishList(any(WishListItem.class), eq(1));
    }

    @Test
    void viewWishlistShouldDisplayWishlist() throws Exception {
        when(wishListService.getUsernameForWishlist(1)).thenReturn("testuser");
        when(wishListService.getAllWishLists("testuser")).thenReturn(Arrays.asList(testWishList));

        mockMvc.perform(get("/wishlist/view/1")
                .param("username", "testuser")
                .sessionAttr("username", "testuser"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-wishlist"))
                .andExpect(model().attributeExists("wishlist"))
                .andExpect(model().attributeExists("username"));
    }

    @Test
    void unauthorizedAccessShouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/wishlist/view/1")
                .param("username", "testuser"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
