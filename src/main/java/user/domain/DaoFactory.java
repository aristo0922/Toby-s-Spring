package user.domain;

import user.domain.connectionMaker.ConnectionMaker;
import user.domain.connectionMaker.DConnectionMaker;

public class DaoFactory {

  public UserDao userDao(){
    ConnectionMaker connectionMaker = new DConnectionMaker();
    UserDao userDao = new UserDao(connectionMaker);
    return userDao;
  }
}
