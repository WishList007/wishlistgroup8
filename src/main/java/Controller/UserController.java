package Controller;
import exception.ErrorMessage;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UserEntityService;

import java.util.List;

@RestController
@RequestMapping("/api/wishList")
public class UserController {
   
    @Autowired
    private UserEntityService userEntityService;

    //user log in--------
    @PostMapping("logIn")
    public ResponseEntity<String>userHandlerLogIn(Authentication auth)throws ErrorMessage {
        UserEntity uEntity = userEntityService.findByEmail(auth.getName()).get();
        return new ResponseEntity<>(userEntity.getEmail() + "log in approved", HttpStatus.ACCEPTED);
    }
    
    // Account creation
    @PostMapping("/")
    public ResponseEntity<UserEntity> createUserAccount(@RequestBody UserEntity uEntity){
        try{
            UserEntity userAccountCreated = userEntityService.createUserAccount(uEntity);
            return new ResponseEntity<>(userAccountCreated, HttpStatus.CREATED);
        }catch(ErrorMessage e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //---USER ACCOUNT UPDATE
    @PutMapping("/{userId}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable int userId, @RequestBody UserEntity uEntity) {
        try {
            userEntityService.updateUserAccount(userId, uEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ErrorMessage e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //User acount deletion
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        try {
            userEntityService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ErrorMessage e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //Find user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable int userId) {
        try {
            UserEntity uEntity = userEntityService.getUserById(userId);
            return new ResponseEntity<>(uEntity, HttpStatus.OK);
        } catch (ErorrMessage e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    //----VIEW WISHLIST ITEMS
    @GetMapping("/getlist/{userId}")
    public ResponseEntity <?> getList(@PathVariable int userId){
        try {
            List<WishListItem> viewWishList = userEntityService.getAllItems(userId);
            return new ResponseEntity<List<WishListItem>>(viewWishList, HttpStatus.ACCEPTED);

        }catch(ErrorMessage e) {
            return new ResponseEntity<String>("Item ID not found", HttpStatus.BAD_GATEWAY);

        }
    }

    //--- CREATE WISHLISTITEM
    @PostMapping("/{userId}/{wishListId}")
    public ResponseEntity<WishListItem> addItem(@PathVariable int userId, @PathVariable int wishListId) {
        try {
            WishListItem wishItem = userEntityService.addItem(userId, wishListId);
            return new ResponseEntity<>(wishItem, HttpStatus.OK);
        } catch (ErrorMessage e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //--DELETE WISHLISTiTEM
    @DeleteMapping("/{userId}/{wishListId}")
    public ResponseEntity<WishListItem> deleteItem(@PathVariable int userId, @PathVariable int wishListId) {
        try {
            WishListItem wishItem = userEntityService.deleteItem(userId, wishListId);
            return new ResponseEntity<>(wishItem, HttpStatus.OK);
        } catch (ErrorMessage e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
    
    
    
}


