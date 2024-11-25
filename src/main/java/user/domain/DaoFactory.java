package user.domain;

import java.sql.Connection;
import org.springframework.context.annotation.Bean;
import user.domain.connectionMaker.ConnectionMaker;
import user.domain.connectionMaker.CountingConnectionMaker;
import user.domain.connectionMaker.DConnectionMaker;

public class DaoFactory {
  ConnectionMaker connectionMaker;

  @Bean
  public UserDao userDao(){
    UserDao userDao = new UserDao(connectionMaker);
    userDao.setConnectionMaker(connectionMaker());
    return userDao;
  }

  @Bean
  public ConnectionMaker connectionMaker() {
    return new DConnectionMaker();
  }

  public void setConnectionMaker(ConnectionMaker connectionMaker) {
    this.connectionMaker = connectionMaker;
  }
}
