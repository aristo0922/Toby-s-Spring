package user.domain.connectionMaker;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {
  public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
