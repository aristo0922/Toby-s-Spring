package user.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDao {
  private SimpleConnectionMaker simpleConnectionMaker;

  public UserDao(){
    simpleConnectionMaker = new SimpleConnectionMaker();
  }

  public void add(User user) throws ClassNotFoundException, SQLException {
    Connection c = simpleConnectionMaker.makeNewConnection();
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

  public User get(String id) throws ClassNotFoundException, SQLException{
    Connection c = simpleConnectionMaker.makeNewConnection();
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

  // 구현 코드 제거, 추상 메소드로 변경
  // 구현은 서브 클래스가 담당
  public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
