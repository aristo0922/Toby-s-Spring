package user.domain;


import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//import user.exception.DuplicateUserIdException;

@Component
public class UserDaoJdbc implements UserDao {

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  private JdbcTemplate jdbcTemplate;

  private RowMapper<User> userMapper = new RowMapper<User>() {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User();
      user.setId(rs.getString("id"));
      user.setName(rs.getString("name"));
      user.setPassword(rs.getString("password"));
      user.setLevel(Level.valueOf(rs.getInt("level")));
      user.setLogin(rs.getInt("login"));
      user.setRecommend(rs.getInt("recommend"));
      return user;
    }
  };

  public void add(final User user) throws DuplicateKeyException {
    try {
      this.jdbcTemplate.update(
          "insert into users(id, name, password, Level, Login, Recommend) values(?,?,?,?,?,?)",
          user.getId(),
          user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(),
          user.getRecommend());
    } catch (DuplicateKeyException e) {
      throw new DuplicateKeyException("[message] key error", e);
    } catch (DataAccessException e) {
      Throwable cause = e.getCause();
      throw new RuntimeException(e); //예외 포장
    }
  }

  public User get(String id) {
    return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id},
        this.userMapper);
  }

  public void deleteAll() {
    this.jdbcTemplate.update("delete from users");
  }


  public int getCount() {
    return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
  }

  public List<User> getAll() {
    return this.jdbcTemplate.query("select * from users order by id",
        this.userMapper);
  }


  @Test
  public void testRowMapper() throws SQLException {
    // Mocking ResultSet
    ResultSet mockResultSet = mock(ResultSet.class);
    when(mockResultSet.getString("id")).thenReturn("testId");
    when(mockResultSet.getString("name")).thenReturn("Test User");
    when(mockResultSet.getString("password")).thenReturn("password123");
    when(mockResultSet.getInt("level")).thenReturn(1); // BASIC
    when(mockResultSet.getInt("login")).thenReturn(10);
    when(mockResultSet.getInt("recommend")).thenReturn(5);

    RowMapper<User> rowMapper = userMapper;
    User user = rowMapper.mapRow(mockResultSet, 1);

    assertEquals("testId", user.getId());
    assertEquals("Test User", user.getName());
    assertEquals("password123", user.getPassword());
    assertEquals(Level.BASIC, user.getLevel());
    assertEquals(10, user.getLogin());
    assertEquals(5, user.getRecommend());
  }
}
