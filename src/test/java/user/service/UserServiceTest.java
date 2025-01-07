package user.service;

import static user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static user.service.UserService.MIN_RECCOMEND_FOR_GOLD;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import user.domain.DaoForTest;
import user.domain.Level;
import user.domain.User;
import user.domain.UserDao;

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
        new User("Gunil", "구건일", "드럼", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
        new User("Justice", "김정수", "키보드", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0),
        new User("Gaon", "곽지석", "리드기타", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD-1),
        new User("Junhan", "한형준", "기타", Level.SILVER, 60, MIN_RECCOMEND_FOR_GOLD),
        new User("O.de", "오승민", "신스", Level.GOLD, 100, Integer.MAX_VALUE),
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
    checkkLevelUpgrade(users.get(0), false);
    checkkLevelUpgrade(users.get(1), true);
    checkkLevelUpgrade(users.get(2), false);
    checkkLevelUpgrade(users.get(3), true);
    checkkLevelUpgrade(users.get(4), false);
    checkkLevelUpgrade(users.get(5), false);
  }

  private void checkkLevelUpgrade(User user, boolean upgraded){
    User userUpdate = userDao.get(user.getId());
    if (upgraded){
      Assertions.assertEquals(user.getLevel().nextLevel(), userUpdate.getLevel());
    }else{
      Assertions.assertEquals(user.getLevel(), userUpdate.getLevel());
    }
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