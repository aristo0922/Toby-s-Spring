package user.domain;

import javax.sql.DataSource;
import mail.DummyMailSender;
import mail.MailMessage;
import mail.MailSender;
import mail.SimpleMailMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import user.domain.connectionMaker.ConnectionMaker;
import user.domain.connectionMaker.DConnectionMaker;
import user.service.UserServiceImpl;

@Configuration
public class DaoForTest {

  @Bean
  public UserDaoJdbc userDao(){
    UserDaoJdbc userDao = new UserDaoJdbc();
    userDao.setDataSource(dataSource());
    return userDao;
  }

  @Bean
  public UserLevelUpgradePolicy policy(){
    return new UserLevelUpgradePolicyImpl();
  }

  @Bean
  public DataSource dataSource(){
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
    dataSource.setUrl("jdbc:mysql://localhost/toby_spring");
    dataSource.setUsername("root");
    dataSource.setPassword("0000");
    return dataSource;
  }

  @Bean
  public ConnectionMaker connectionMaker() {
    return new DConnectionMaker();
  }

  @Bean
  public TransactionManager transactionManager(){
    PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
    return transactionManager;
  }

  @Bean
  public UserServiceImpl userService(){
    UserServiceImpl userService = new UserServiceImpl();
    userService.setUserDao(userDao());
    userService.setUserLevelUpgradePolicy(policy());
    userService.setMailSender(mailSender("mail.server.com"));
    return userService;
  }

  @Bean
  public MailSender mailSender(String host){
    return new DummyMailSender();
  }

  @Bean
  public MailMessage mailMessage(){
    return new SimpleMailMessage();
  }
}
