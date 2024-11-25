package user.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import user.domain.connectionMaker.ConnectionMaker;
import user.domain.connectionMaker.CountingConnectionMaker;
import user.domain.connectionMaker.DConnectionMaker;

@Configuration
public class CountingDaoFactory {

  @Bean
  public UserDao userDao() {
    return new UserDao(connectionMaker());
  }

  @Bean
  public ConnectionMaker connectionMaker() {
    return new CountingConnectionMaker(realConnectionMaker());
  }

  @Bean
  public ConnectionMaker realConnectionMaker() {
    return new DConnectionMaker();
  }
}
