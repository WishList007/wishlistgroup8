package repository;

import model.UserEntity;
import model.WishList;
import model.WishListItem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
// hey

import java.util.List;


@Repository
public class WishListRepository {
    private JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//--------------find/rowMappers/SELECT Operations----------
    public List<WishListItem> findAllItemsId(int itemId) {
        String sql = "SELECT * FROM items WHERE itemId = ?";
        RowMapper<WishListItem> rowMapper = new BeanPropertyRowMapper<>(WishListItem.class);
        return jdbcTemplate.query(sql, rowMapper, itemId);
    }
    public List<WishListItem> findItem(int itemName) {
        String sql = "SELECT * FROM items WHERE itemId = ?";
        RowMapper<WishListItem> rowMapper = new BeanPropertyRowMapper<>(WishListItem.class);
        return jdbcTemplate.query(sql, rowMapper, itemName);
    }

    public List<UserEntity> findAllUsers() {
        String sql = "SELECT * FROM users";
        RowMapper<UserEntity> rowMapper = new BeanPropertyRowMapper<>(UserEntity.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public UserEntity findUserName(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        RowMapper<UserEntity> rowMapper = new BeanPropertyRowMapper<>(UserEntity.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, username);
    }

    public List<WishList>findAllWishLists(String username){
        String sql ="SELECT * FROM wishList WHERE userId =(SELECT userId FROM users WHERE username = ?)";
        RowMapper<WishList>rowMapper = new BeanPropertyRowMapper<>(WishList.class);
        return jdbcTemplate.query(sql,rowMapper,username);
    }
    //-------------Delete
public boolean deleteItem(int itemId){
        String sql ="DELETE FROM items WHERE itemId =?";
        return jdbcTemplate.update(sql,itemId)>0;
}
public boolean deleteWishList(int wishListId){
    String sql ="DELETE FROM WishList WHERE itemId =?";
    return jdbcTemplate.update(sql,wishListId)>0;
}
    //-------------Add /sql update/ insert into operations---------
    public void updateItem(WishListItem wishListItem,int itemId){
        String sql="UPDATE items SET itemName =?, itemDescription =?, itemPrice=?, itemLink=? WHERE itemId=?";
        jdbcTemplate.update(sql,wishListItem.getItemId(),wishListItem.getItemName(),wishListItem.getItemDescription(),wishListItem.getItemPrice(),wishListItem.getItemLink());
    }
    //-------------------
    public void addUser(UserEntity userEntity) {
        String sql = "INSERT INTO users FROM users (userId,username,email,password) = VALUES(?,?,?,?)";
        jdbcTemplate.update(sql, userEntity.getUserId(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword());
    }
    public void addWishList(WishList wishList, String username){
        String sql = "INSERT INTO wishList(wishListName,userId) VALUES(?,(SELECT userId FROM users WHERE username = ?))";
        jdbcTemplate.update(sql,wishList.getWishListName(),username);
    }
    public void addItemToWishList(WishListItem wishListItem, int itemId){
        String sql = "INSERT INTO items(itemId,itemName,itemDescription,itemPrice,itemLink)VALUES(?,?,?,?)";
        jdbcTemplate.update(sql, wishListItem.getItemId(),wishListItem.getItemName(),wishListItem.getItemDescription(),wishListItem.getItemPrice(),wishListItem.getItemLink());
    }

}
