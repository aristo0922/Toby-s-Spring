package user.domain.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import user.domain.DaoForTest;
import user.domain.Level;
import user.domain.User;
import user.domain.UserDao;
import user.domain.UserDaoJdbc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DaoForTest.class})
@DirtiesContext
public class UserDaoTest {

  @Autowired
  private ApplicationContext context;

  @Autowired
  private UserDao dao;

  @Autowired
  SimpleDriverDataSource dataSource;

  private User user1;
  private User user2;
  private User user3;


  @Before
  public void setup() {
    this.dao = context.getBean("userDao", UserDaoJdbc.class);

    user1 = new User("dkfud2121", "장아령", "springno1", Level.BASIC, 11, 60);
    user2 = new User("Ryan Lee", "라이언", "springno2", Level.SILVER, 55, 10);
    user3 = new User("villains", "빌런즈", "springno3", Level.GOLD, 100, 40);
  }

  private void checkSameUser(User user1, User user2){
    Assertions.assertEquals(user1.getId(), user2.getId());
    Assertions.assertEquals(user1.getName(), user2.getName());
    Assertions.assertEquals(user1.getPassword(), user2.getPassword());
    Assertions.assertEquals(user1.getLevel(), user2.getLevel());
    Assertions.assertEquals(user1.getLogin(), user2.getLogin());
    Assertions.assertEquals(user1.getRecommend(), user2.getRecommend());
  }

  @Test
  public void addAndGet() throws SQLException, ClassNotFoundException {
    dao.deleteAll();
    Assertions.assertEquals(dao.getCount(), 0);

    dao.add(user1);
    dao.add(user2);
    Assertions.assertEquals(dao.getCount(), 2);

    User userget1 = dao.get(user1.getId());
    checkSameUser(userget1, user1);

    User userget2 = dao.get(user2.getId());
    checkSameUser(userget2, user2);
  }

  @Test
  public void count() throws SQLException, ClassNotFoundException {
    dao.deleteAll();
    Assertions.assertEquals(dao.getCount(), 0);

    dao.add(user1);
    Assertions.assertEquals(dao.getCount(), 1);

    dao.add(user2);
    Assertions.assertEquals(dao.getCount(), 2);

    dao.add(user3);
    Assertions.assertEquals(dao.getCount(), 3);
  }

  @Test(expected = EmptyResultDataAccessException.class)
  public void getUserFailure() throws SQLException, ClassNotFoundException {
    dao.deleteAll();
    Assertions.assertEquals(dao.getCount(), 0);

    dao.get("unknown_id");
  }

  @Test(expected = DuplicateKeyException.class)
  public void duplicateKey(){
    dao.deleteAll();
    dao.add(user1);
    dao.add(user1);
  }
}
