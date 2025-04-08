package com.example.wishlist.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.wishlist.model.UserEntity;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.WishListService;

@Controller
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("/")
    public String homePage() {
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserEntity user, Model model) {
        try {
            UserEntity existingUser = wishListService.getUserByUsername(user.getUsername());
            if (existingUser.getPassword().equals(user.getPassword())) {
                return "redirect:/wishlist/add/" + existingUser.getUsername();
            } else {
                model.addAttribute("error", "Incorrect password");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "User not found");
            return "login";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login";
    }

    @GetMapping("/wishlist/add/{username}")
    public String showAddWishlistForm(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        return "add-wishlist";
    }

    @PostMapping("/wishlist/add")
    public String addWishlist(@RequestParam String username,
                            @RequestParam String listName,
                            @RequestParam(required = false) String description,
                            Model model) {
        try {
            WishList wishList = new WishList();
            wishList.setWishListName(listName);
            wishList.setWishListDescription(description);
            
            wishListService.addWishList(wishList, username);
            
            // Redirect to view-wishlists page after successful creation
            return "redirect:/wishlist/" + username;
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create wishlist: " + e.getMessage());
            model.addAttribute("username", username);
            return "add-wishlist";
        }
    }

    @GetMapping("/wishlist/{username}")
    public String viewUserWishlists(@PathVariable String username, Model model) {
        try {
            // First verify the user exists
            wishListService.getUserByUsername(username);
            
            // Get wishlists (might be empty)
            List<WishList> wishLists;
            try {
                wishLists = wishListService.getAllWishLists(username);
            } catch (Exception e) {
                // If there's an error getting wishlists, initialize an empty list
                wishLists = new ArrayList<>();
            }
            
            model.addAttribute("wishLists", wishLists);
            model.addAttribute("username", username);
            return "view-wishlists";
        } catch (Exception e) {
            // If user doesn't exist
            return "redirect:/login";
        }
    }
}
