package user.domain;


import com.mysql.cj.exceptions.MysqlErrorNumbers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import user.exception.DuplicateUserIdException;

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
      return user;
    }
  };


  public void add(final User user) throws DuplicateUserIdException {
    try {
      this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getId(),
          user.getName(), user.getPassword());
    } catch (DataAccessException e) {
      Throwable cause = e.getCause();
      if (cause instanceof SQLException) {
        SQLException sqlException = (SQLException) cause;
        if (sqlException.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
          throw new DuplicateUserIdException(sqlException); // 예외 전환
        }
      }
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
}
