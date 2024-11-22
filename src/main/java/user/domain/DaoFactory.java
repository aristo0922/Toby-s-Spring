package user.domain;

import java.sql.SQLException;

public class DaoFactory {

  public UserDao userDao(){
    ConnectionMaker connectionMaker = new DConnectionMaker();
    UserDao userDao = new UserDao(connectionMaker);
    return userDao;
  }
}
