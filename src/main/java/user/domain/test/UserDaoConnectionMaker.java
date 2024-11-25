package user.domain.test;

import java.sql.SQLException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.domain.CountingDaoFactory;
import user.domain.UserDao;
import user.domain.connectionMaker.CountingConnectionMaker;

public class UserDaoConnectionMaker {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        CountingDaoFactory.class);
    UserDao dao = context.getBean("userDao", UserDao.class);

    CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
    System.out.println("Connection counter: " + ccm.getCounter());
  }
}
