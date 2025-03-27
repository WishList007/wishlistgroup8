package repository;

import model.UserEntity;
import model.WishListItem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class WishListRepository {
    private JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<WishListItem> findAllItemsId(int itemId){
        String sql = "SELECT * FROM items WHERE itemId = ?";
        RowMapper<WishListItem> rowMapper = new BeanPropertyRowMapper<>(WishListItem.class);
        return jdbcTemplate.query(sql,rowMapper,itemId);
    }
public List <UserEntity>findAllUsers() {
    String sql = "SELECT * FROM users";
    RowMapper<UserEntity> rowMapper = new BeanPropertyRowMapper<>(UserEntity.class);
    return jdbcTemplate.query(sql, rowMapper);
}
public UserEntity findUserName(String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        RowMapper<UserEntity> rowMapper = new BeanPropertyRowMapper<>(UserEntity.class);
        return jdbcTemplate.queryForObject(sql,rowMapper,username);
}

}
