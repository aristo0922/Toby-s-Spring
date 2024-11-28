package user.domain.test;

import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.domain.DaoFactory;
import user.domain.User;
import user.domain.UserDao;

public class UserDaoTest {

  @Test
  public void addAndGet() throws SQLException, ClassNotFoundException{
    ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
    UserDao dao = context.getBean("userDao", UserDao.class);

    User user1 = new User("dkfud2121", "장아령", "springno1");
    User user2 = new User("Ryan Lee", "라이언", "springno2");
    User user3 = new User("villains", "빌런즈", "springno3");

    dao.deleteAll();
    Assertions.assertEquals(dao.getCount(), 0);

    dao.add(user1);
    dao.add(user2);
    Assertions.assertEquals(dao.getCount(), 2);

    User userget1 = dao.get(user1.getId());
    Assertions.assertEquals(userget1.getName(), userget1.getName());
    Assertions.assertEquals(userget1.getPassword(), userget1.getPassword());

    User userget2 = dao.get(user2.getId());
    Assertions.assertEquals(userget2.getName(), userget2.getName());
    Assertions.assertEquals(userget2.getPassword(), userget2.getPassword());
  }

  @Test
  public void count() throws SQLException, ClassNotFoundException{
    ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

    UserDao dao = context.getBean("userDao", UserDao.class);
    User user1 = new User("dkfud2121", "장아령", "springno1");
    User user2 = new User("Ryan Lee", "라이언", "springno2");
    User user3 = new User("villains", "빌런즈", "springno3");

    dao.deleteAll();
    Assertions.assertEquals(dao.getCount(), 0);

    dao.add(user1);
    Assertions.assertEquals(dao.getCount(),1);

    dao.add(user2);
    Assertions.assertEquals(dao.getCount(), 2);

    dao.add(user3);
    Assertions.assertEquals(dao.getCount(), 3);
  }
}
