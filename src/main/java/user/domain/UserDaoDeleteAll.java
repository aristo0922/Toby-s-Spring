package user.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll extends UserDaoJdbc {

  protected PreparedStatement makeStatement(Connection c) throws SQLException{
    PreparedStatement ps = c.prepareStatement("delete from users");
    return ps;
  }
}
