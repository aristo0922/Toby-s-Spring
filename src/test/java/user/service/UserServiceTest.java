package user.service;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import user.domain.DaoForTest;
import user.domain.Level;
import user.domain.User;
import user.domain.UserDao;
import user.domain.UserDaoJdbc;

@ExtendWith(SpringExtension.class) // Spring 테스트 컨텍스트 통합
@ContextConfiguration(classes = {DaoForTest.class})
@DirtiesContext
class UserServiceTest {

  List<User> users;

  @Autowired
  private UserDao userDao;
  @Autowired
  UserService userService;

  @BeforeEach
  public void setUp(){
    users = Arrays.asList(
        new User("Gunil", "구건일", "드럼", Level.BASIC, 49, 0),
        new User("Justice", "김정수", "키보드", Level.BASIC, 50, 0),
        new User("Gaon", "곽지석", "리드기타", Level.SILVER, 60, 29),
        new User("Junhan", "한형준", "기타", Level.SILVER, 60, 30),
        new User("O.de", "오승민", "신스", Level.GOLD, 49, 30),
        new User("Juyeon", "이주연", "베이스", Level.GOLD, 100, 100)
    );
  }

  @Test
  public void bean(){
    Assertions.assertNotNull(this.userService);
    Assertions.assertNotNull(this.userDao);
  }

  @Test
  public void upgradeLevels(){
    userDao.deleteAll();
    Assertions.assertNotNull(this.userDao);

    for (User user: users) userDao.add(user);

    userService.upgradeLevels();
    checkLevel(users.get(0), Level.BASIC);
    checkLevel(users.get(1), Level.SILVER);
    checkLevel(users.get(2), Level.SILVER);
    checkLevel(users.get(3), Level.GOLD);
    checkLevel(users.get(4), Level.GOLD);
    checkLevel(users.get(5), Level.GOLD);
  }

  private void checkLevel(User user, Level expectedLevel){
    User userUpdate = userDao.get(user.getId());
    Assertions.assertEquals(expectedLevel, userUpdate.getLevel());
  }

  @Test
  public void add(){
    userDao.deleteAll();

    User userWithLevel = users.get(4);
    User userWithoutLevel = users.get(5);
    userWithoutLevel.setLevel(null);

    userService.add(userWithLevel);
    userService.add(userWithoutLevel);

    User userWithLevelRead = userDao.get(userWithLevel.getId());
    User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

    Assertions.assertEquals(userWithLevel.getLevel(), userWithLevelRead.getLevel());
    Assertions.assertEquals(Level.BASIC, userWithoutLevelRead.getLevel());
  }
}