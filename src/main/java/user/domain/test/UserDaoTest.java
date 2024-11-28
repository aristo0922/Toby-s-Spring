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

    dao.deleteAll();
    Assertions.assertEquals(dao.getCount(), 0);

    User user = new User();
    user.setId("hello12");
    user.setName("XH");
    user.setPassword("Villains");
    dao.add(user);
    Assertions.assertEquals(dao.getCount(), 1);

    User user2 = dao.get(user.getId());

    Assertions.assertEquals(user2.getName(), user.getName());
    Assertions.assertEquals(user2.getPassword(), user.getPassword());
  }

}
