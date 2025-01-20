package user.service;

import static org.junit.jupiter.api.Assertions.fail;
import static user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static user.service.UserService.MIN_RECCOMEND_FOR_GOLD;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import mail.MailMessage;
import mail.MailSender;
import mail.SimpleMailMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import user.domain.DaoForTest;
import user.domain.Level;
import user.domain.User;
import user.domain.UserDao;
import user.domain.UserLevelUpgradePolicy;

@ExtendWith(SpringExtension.class) // Spring 테스트 컨텍스트 통합
@ContextConfiguration(classes = {DaoForTest.class})
@DirtiesContext
class UserServiceTest {

  List<User> users;

  @Autowired
  private MailSender mailSender;

  @Autowired
  private UserDao userDao;
  @Autowired
  UserService userService;

  @Autowired
  DataSource dataSource;

  @Autowired
  UserLevelUpgradePolicy policy;

  @Autowired
  PlatformTransactionManager transactionManager;


  @BeforeEach
  public void setUp() {
    users = Arrays.asList(
        new User("Gunil", "구건일", "드럼", "dkfud2121@naver.com", Level.BASIC,
            MIN_LOGCOUNT_FOR_SILVER - 1, 0),
        new User("Justice", "김정수", "키보드", "dkfud2121@naver.com", Level.BASIC,
            MIN_LOGCOUNT_FOR_SILVER, 0),
        new User("Gaon", "곽지석", "리드기타", "dkfud2121@naver.com", Level.SILVER, 60,
            MIN_RECCOMEND_FOR_GOLD - 1),
        new User("Junhan", "한형준", "기타", "dkfud2121@naver.com", Level.SILVER, 60,
            MIN_RECCOMEND_FOR_GOLD),
        new User("O.de", "오승민", "신스", "dkfud2121@naver.com", Level.GOLD, 100, Integer.MAX_VALUE),
        new User("Juyeon", "이주연", "베이스", "dkfud2121@naver.com", Level.GOLD, 100, 100)
    );
  }

  @Test
  public void bean() {
    Assertions.assertNotNull(this.userService);
    Assertions.assertNotNull(this.userDao);
  }

  @Test
  public void upgradeLevels() throws SQLException {
    userDao.deleteAll();
    Assertions.assertNotNull(this.userDao);

    for (User user : users) {
      userDao.add(user);
    }

    userService.setTransactionManager(transactionManager);

    MockMailSender mockMailSender = new MockMailSender();
    userService.setMailSender(mockMailSender);
    userService.upgradeLevels();

    checkLevelUpgrade(users.get(0), false);
    checkLevelUpgrade(users.get(1), true);
    checkLevelUpgrade(users.get(2), false);
    checkLevelUpgrade(users.get(3), true);
    checkLevelUpgrade(users.get(4), false);
    checkLevelUpgrade(users.get(5), false);

    List<String> request = mockMailSender.getRequests();
    Assertions.assertEquals(2, request.size());
  }

  private void checkLevelUpgrade(User user, boolean upgraded) {
    User userUpdate = userDao.get(user.getId());
    if (upgraded) {
      Assertions.assertEquals(user.getLevel().nextLevel(), userUpdate.getLevel());
    } else {
      Assertions.assertEquals(user.getLevel(), userUpdate.getLevel());
    }
  }

  @Test
  public void add() {
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

  @Test
  public void upgradeAllOrNothing() throws Exception {
    UserService testUserService = new TestUserService(users.get(3).getId());
    testUserService.setUserDao(this.userDao);
    testUserService.setTransactionManager(transactionManager);
    testUserService.setUserLevelUpgradePolicy(this.policy);
    testUserService.setMailSender(mailSender);
    userDao.deleteAll();
    for (User user : users) {
      userDao.add(user);
    }

    try {
      testUserService.upgradeLevels();
      fail("TestUserServiceException expected");
    } catch (TestUserServiceException e) {

    }

    checkLevelUpgrade(users.get(1), false);

  }

  static class TestUserService extends UserService {

    private String id;

    private TestUserService(String id) {
      this.id = id;
    }

    protected void upgradeLevel(User user) {
      if (user.getId().equals(this.id)) {
        throw new TestUserServiceException();
      }
      super.upgradeLevel(user);
    }
  }

  static class TestUserServiceException extends RuntimeException {

  }

  static class MockMailSender implements MailSender{
    private List<String> requests = new ArrayList<String>();

    public List<String> getRequests(){
      return requests;
    }

    public void send(SimpleMailMessage mailMessage) throws MailException{
      requests.add(mailMessage.getTo());
    }

    public void send(SimpleMailMessage[] mailMessage) throws MailException{

    }

    @Override
    public void send(MailMessage message) {
      requests.add(message.getTo());
    }
  }

  private static class MailException extends Exception {

  }
}