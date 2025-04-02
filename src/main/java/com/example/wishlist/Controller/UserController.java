package com.example.wishlist.Controller;

import com.example.wishlist.model.UserEntity;
import com.example.wishlist.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.wishlist.repository.WishListRepository;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private WishListRepository wishListRepository;

    @GetMapping("/")
    public String homePage() {
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String loginPage(Model model) {
        System.out.println("✅ Reached login page controller.");
        model.addAttribute("user", new UserEntity());
        return "login"; // tells Thymeleaf to load login.html
    }



    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserEntity user, Model model) {
        try {
            UserEntity existingUser = wishListRepository.findUserName(user.getUsername());
            if (existingUser.getPassword().equals(user.getPassword())) {
                return "redirect:/wishlist/" + existingUser.getUsername();
            } else {
                model.addAttribute("error", "Incorrect password");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "User not found");
            return "login";
        }
    }

    @GetMapping("/wishlist/{username}")
    public String viewUserWishlists(@PathVariable String username, Model model) {
        List<WishList> wishLists = wishListRepository.findAllWishLists(username);
        model.addAttribute("wishLists", wishLists);
        model.addAttribute("username", username);
        return "view-wishlists";
    }
}
