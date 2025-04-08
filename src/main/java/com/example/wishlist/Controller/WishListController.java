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
import com.example.wishlist.model.WishListItem;
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

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute("user") UserEntity user, Model model) {
        try {
            wishListService.getUserByUsername(user.getUsername());
            model.addAttribute("error", "Username already exists");
            return "signup";
        } catch (Exception e) {
            try {
                int newUserId = wishListService.getMaxUserId() + 1;
                user.setUserId(newUserId);
                
                wishListService.addUser(user);
                return "redirect:/login";
            } catch (Exception ex) {
                model.addAttribute("error", "Error creating account: " + ex.getMessage());
                return "signup";
            }
        }
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
            
            return "redirect:/wishlist/" + username;
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create wishlist: " + e.getMessage());
            model.addAttribute("username", username);
            return "add-wishlist";
        }
    }

    @PostMapping("/wishlist/delete")
    public String deleteWishlist(@RequestParam int wishListId, @RequestParam String username, Model model) {
        try {
            boolean deleted = wishListService.deleteWishList(wishListId);
            if (!deleted) {
                throw new Exception("Wishlist not found");
            }
            return "redirect:/wishlist/" + username;
        } catch (Exception e) {
            return "redirect:/wishlist/" + username;
        }
    }

    @GetMapping("/wishlist/wish/add/{wishListId}")
    public String showAddWishForm(@PathVariable int wishListId, @RequestParam String username, Model model) {
        try {
            // Get wishlist name for display
            List<WishList> wishlists = wishListService.getAllWishLists(username);
            String wishListName = wishlists.stream()
                .filter(w -> w.getWishListId() == wishListId)
                .findFirst()
                .map(WishList::getWishListName)
                .orElse("Unknown Wishlist");

            model.addAttribute("wishListId", wishListId);
            model.addAttribute("username", username);
            model.addAttribute("wishListName", wishListName);
            return "add-wish";
        } catch (Exception e) {
            return "redirect:/wishlist/" + username;
        }
    }

    @PostMapping("/wishlist/wish/add")
    public String addWishToList(@RequestParam String itemName,
                               @RequestParam(required = false) String itemDescription,
                               @RequestParam(required = false) Double itemPrice,
                               @RequestParam(required = false) String itemLink,
                               @RequestParam int wishListId,
                               @RequestParam String username,
                               Model model) {
        try {
            WishListItem item = new WishListItem();
            item.setItemName(itemName);
            item.setItemDescription(itemDescription);
            if (itemPrice != null) {
                item.setItemPrice(itemPrice);
            }
            if (itemLink != null && !itemLink.trim().isEmpty()) {
                item.setItemLink(itemLink);
            }
            
            wishListService.addItemToWishList(item, wishListId);
            return "redirect:/wishlist/" + username;
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add wish: " + e.getMessage());
            model.addAttribute("wishListId", wishListId);
            model.addAttribute("username", username);
            return "add-wish";
        }
    }

    @GetMapping("/wishlist/{username}")
    public String viewUserWishlists(@PathVariable String username, Model model) {
        try {
            wishListService.getUserByUsername(username);
            
            List<WishList> wishLists;
            try {
                wishLists = wishListService.getAllWishLists(username);
            } catch (Exception e) {
                wishLists = new ArrayList<>();
            }
            
            model.addAttribute("wishLists", wishLists);
            model.addAttribute("username", username);
            return "view-wishlists";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/wishlist/view/{wishListId}")
    public String viewWishlist(@PathVariable int wishListId, @RequestParam String username, Model model) {
        System.out.println("Viewing wishlist with ID: " + wishListId + " for user: " + username);
        try {
            // Get the specific wishlist
            List<WishList> wishlists = wishListService.getAllWishLists(username);
            System.out.println("Found " + wishlists.size() + " wishlists for user");
            
            WishList wishlist = wishlists.stream()
                .filter(w -> w.getWishListId() == wishListId)
                .findFirst()
                .orElseThrow(() -> new Exception("Wishlist not found"));
            
            System.out.println("Found wishlist: " + wishlist.getWishListName());
            System.out.println("Items in wishlist: " + (wishlist.getWishListItems() != null ? wishlist.getWishListItems().size() : "null"));
            
            model.addAttribute("wishlist", wishlist);
            model.addAttribute("username", username);
            return "view-wishlist";
        } catch (Exception e) {
            System.err.println("Error viewing wishlist: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/wishlist/" + username;
        }
    }
}
