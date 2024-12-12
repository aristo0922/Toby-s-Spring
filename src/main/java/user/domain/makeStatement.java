package user.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class makeStatement {
  abstract protected PreparedStatement makeStatement(Connection c) throws SQLException;
}
