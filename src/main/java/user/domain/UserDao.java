package user.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class UserDao {

  @Autowired
  private DataSource dataSource;

  @Bean
  public UserDao userDao(){
    UserDao userDao = new UserDao();
    userDao.setDataSource(dataSource);
    return userDao;
  }

  public void setDataSource(DataSource dataSource){
    this.dataSource = dataSource;
  }

  public void add(User user) throws ClassNotFoundException, SQLException {
    Connection c = dataSource.getConnection();
    PreparedStatement ps = c.prepareStatement(
        "insert into users(id, name, password) values(?, ?, ?)"
    );

    ps.setString(1, user.getId());
    ps.setString(2, user.getName());
    ps.setString(3, user.getPassword());

    ps.executeUpdate();

    ps.close();
    c.close();
  }

  public User get(String id) throws ClassNotFoundException, SQLException {
    Connection c = dataSource.getConnection();
    PreparedStatement ps = c.prepareStatement(
        "select * from users where id = ?"
    );
    ps.setString(1, id);
    ResultSet rs = ps.executeQuery();
    rs.next();

    User user = new User();
    user.setId(rs.getString("id"));
    user.setName(rs.getString("name"));
    user.setPassword(rs.getString("password"));

    rs.close();
    ps.close();
    c.close();

    return user;
  }
}
