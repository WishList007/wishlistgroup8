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

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserEntity user, Model model) {
        try {
            UserEntity existingUser = wishListService.getUserByUsername(user.getUsername());
            if (existingUser.getPassword().equals(user.getPassword())) {
                return "redirect:/wishlist/add/" + existingUser.getUsername();
            }
            model.addAttribute("error", "Incorrect password");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "User not found");
            return "login";
        }
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
                user.setUserId(wishListService.getMaxUserId() + 1);
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
        try {
            List<WishList> wishLists = wishListService.getAllWishLists(username);
            model.addAttribute("wishLists", wishLists);
        } catch (Exception e) {
            model.addAttribute("wishLists", new ArrayList<>());
        }
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
    public String deleteWishlist(@RequestParam int wishListId, @RequestParam String username) {
        wishListService.deleteWishList(wishListId);
        return "redirect:/wishlist/" + username;
    }

    @GetMapping("/wishlist/wish/add/{wishListId}")
    public String showAddWishForm(@PathVariable int wishListId, @RequestParam String username, Model model) {
        try {
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
            List<WishList> wishLists = wishListService.getAllWishLists(username);
            model.addAttribute("wishLists", wishLists);
            model.addAttribute("username", username);
            return "view-wishlists";
        } catch (Exception e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/wishlist/view/{wishListId}")
    public String viewWishlist(@PathVariable int wishListId, @RequestParam String username, Model model) {
        try {
            List<WishList> wishlists = wishListService.getAllWishLists(username);
            WishList wishlist = wishlists.stream()
                .filter(w -> w.getWishListId() == wishListId)
                .findFirst()
                .orElseThrow(() -> new Exception("Wishlist not found"));
            
            model.addAttribute("wishlist", wishlist);
            model.addAttribute("username", username);
            return "view-wishlist";
        } catch (Exception e) {
            return "redirect:/wishlist/" + username;
        }
    }

    @GetMapping("/wishlist/wish/edit/{itemId}")
    public String showEditWishForm(@PathVariable int itemId, 
                                 @RequestParam(required = false) String username, 
                                 Model model) {
        WishListItem item = wishListService.getWishListItemById(itemId);
        if (item == null) {
            return "redirect:/error";
        }
        model.addAttribute("item", item);
        model.addAttribute("username", username);
        return "edit-wish";
    }

    @PostMapping("/wishlist/wish/edit")
    public String editWish(@ModelAttribute WishListItem item, 
                          @RequestParam("wishListId") int wishListId,
                          @RequestParam("username") String username) {
        wishListService.updateWishListItem(item);
        return "redirect:/wishlist/view/" + wishListId + "?username=" + username;
    }

    @PostMapping("/wishlist/wish/delete/{itemId}")
    public String deleteWish(@PathVariable int itemId, 
                           @RequestParam("wishListId") int wishListId,
                           @RequestParam("username") String username) {
        wishListService.deleteWishListItem(itemId);
        return "redirect:/wishlist/view/" + wishListId + "?username=" + username;
    }
}
