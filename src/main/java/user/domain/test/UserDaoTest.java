package user.domain.test;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import user.domain.DaoFactory;
import user.domain.DaoForTest;
import user.domain.User;
import user.domain.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DaoForTest.class})
@DirtiesContext
public class UserDaoTest {
  @Autowired
  private UserDao dao;

  @Autowired
  private ApplicationContext context;

  @Before
  public void setup(){
//    DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb", "root", "0000", true);
//    dao.setDataSource(dataSource);
  }

  @Test
  public void addAndGet() throws SQLException, ClassNotFoundException{
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

  @Test(expected = EmptyResultDataAccessException.class)
  public void getUserFailure() throws SQLException, ClassNotFoundException{
    dao.deleteAll();
    Assertions.assertEquals(dao.getCount(), 0);

    dao.get("unknown_id");
  }
}
