package com.example.wishlist.Controller;
import com.example.wishlist.exception.ErrorMessage;
import com.example.wishlist.model.Admin;
import com.example.wishlist.model.UserEntity;
import com.example.wishlist.model.WishListItem;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.wishlist.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/logIn")
    public ResponseEntity<String>userHandlerLogIn(Authentication auth)throws ErrorMessage {
        if(auth == null){
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        }
        Admin admin = adminService.findAdminByMail(auth.name()).orElseThrow(() -> new ErrorMessage());
        return new ResponseEntity<>(admin.getAdminEmail()+"Log in approved",HttpStatus.ACCEPTED);
    }
    //ADMIN REGISTER------------
    @PostMapping("/adminregister")
    @ResponseBody
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        try {
            Admin admin1 = adminService.adminRegister(admin);
            return new ResponseEntity<>(admin1, HttpStatus.CREATED);
        } catch (ErrorMessage e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //------GET USERS
    @GetMapping("/userlist")
    public ResponseEntity<List<UserEntity>>getAllUsers(){
        try {
            List<UserEntity> allUsers = adminService.getUsers();
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }catch (ErrorMessage e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //----------DELETE USER
    @DeleteMapping("/deleteuser/{userId}")
    public ResponseEntity<Void> removeUser(@PathVariable int userId){
        try {
            adminService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ErrorMessage e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //----- get all wishlist items
    @GetMapping("/allwishlistitems")
    public ResponseEntity<List<WishListItem>>getAllWishListItems(){
        try {
            List<WishListItem> items = adminService.getWishListItems();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (ErrorMessage e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //--------CREATE WISHLIST item
    @PostMapping("/createwishlistitem")
    public ResponseEntity<WishListItem>createItem(@RequestBody WishListItem wishListItem){
        try {
            WishListItem itemCreated = adminService.addWishListItem(wishListItem);
            return new ResponseEntity<>(itemCreated, HttpStatus.CREATED);
        } catch (ErrorMessage e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //--------UPDATE WISHLIST item
    @PutMapping("/updatewishlistitem/{wishlistId}")
    public ResponseEntity<Void> updateItem(@PathVariable int wishlistId, @RequestBody WishListItem wishlistItem) {
        try {
            adminService.updateWishListItem(wishlistId, wishlistItem);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ErrorMessage e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //--------- delete wish list item
    @DeleteMapping("/deletewishlistitem/{wishlistId}")
    public ResponseEntity<Void> deleteItem(@PathVariable int wishListItemId) {
        try {
            adminService.deleteWishListItem(wishListItemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ErrorMessage e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
