package user.domain.test;

import java.sql.SQLException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.domain.DaoFactory;
import user.domain.UserDaoJdbc;
import user.domain.connectionMaker.CountingConnectionMaker;

public class UserDaoConnectionMaker {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        DaoFactory.class);
    UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);

    CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
    System.out.println("Connection counter: " + ccm.getCounter());
  }
}
