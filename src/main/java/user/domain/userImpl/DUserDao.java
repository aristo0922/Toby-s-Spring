package user.domain.userImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import user.domain.UserDaoJdbc;

public class DUserDao extends UserDaoJdbc {

  public Connection getConnection() throws ClassNotFoundException, SQLException {
    // D 사에서 사용하는 DB connection 생성 코드
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection c = DriverManager.getConnection("jdbc:mysql://localhost/toby_spring", "root", "0000");
    return c;
  }
}
