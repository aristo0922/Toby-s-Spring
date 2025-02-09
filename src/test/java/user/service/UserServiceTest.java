package user.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static user.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static user.service.UserServiceImpl.MIN_RECCOMEND_FOR_GOLD;

import java.lang.reflect.Proxy;
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
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import springbook.learningtest.template.factoryBean.MessageFactoryBean;
import user.domain.DaoFactory;
import user.domain.DaoForTest;
import user.domain.Level;
import user.domain.User;
import user.domain.UserDao;
import user.domain.UserDaoJdbc;
import user.domain.UserLevelUpgradePolicy;
import user.proxy.TransactionHandler;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoForTest.class})
@DirtiesContext
class UserServiceTest {

  private List<User> users;
  @Autowired  private UserDao userDao;
  @Autowired private UserServiceTx userService;
  @Autowired private UserServiceImpl userServiceImpl;

  @Autowired  private MailSender mailSender;
  @Autowired private DataSource dataSource;
  @Autowired private UserLevelUpgradePolicy policy;
  @Autowired private PlatformTransactionManager transactionManager;


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
  public void upgradeLevels() {
    UserServiceImpl userServiceImpl = new UserServiceImpl();

    MockUserDao mockUserDao = new MockUserDao(this.users);
    userServiceImpl.setUserDao(mockUserDao);

    MockMailSender mockMailSender = new MockMailSender();
    userServiceImpl.setMailSender(mockMailSender);
    userServiceImpl.setUserLevelUpgradePolicy(policy);
    userServiceImpl.upgradeLevels();

    List<User> updated = mockUserDao.getUpdated();
    Assertions.assertEquals(2, updated.size());

    checkUserAndLevel(updated.get(0), "Justice", Level.SILVER);
    checkUserAndLevel(updated.get(1), "Junhan", Level.GOLD);
  }

  @Test
  public void mockUpgradeLevels() throws Exception{
    UserServiceImpl userService1 = new UserServiceImpl();
    UserDao mockUserDao = mock(UserDao.class);
    when(mockUserDao.getAll()).thenReturn(this.users);
    userServiceImpl.setUserDao(mockUserDao);

    MailSender mockMailSender = mock(MailSender.class);
    userService1.setMailSender(mockMailSender);

    userServiceImpl.upgradeLevels();

    verify(mockUserDao, times(2)).update(any(User.class));
    verify(mockUserDao, times(2)).update(any(User.class));
    verify(mockUserDao).update(users.get(1));
    Assertions.assertEquals(Level.SILVER, users.get(1).getLevel());
    verify(mockUserDao).update(users.get(3));
    Assertions.assertEquals(Level.GOLD, users.get(3).getLevel());

//    ArgumentCaptor<SimpleMailMessage> mailMessageArg = ArgumentCaptor.forClass(SimpleMailMessage.class);
//    verify(mockMailSender, times(2)).send(mailMessageArg.capture());
//    List<SimpleMailMessage> mailMessages = mailMessageArg.getAllValues();
  }

  private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel){
    Assertions.assertEquals(expectedId, updated.getId());
    Assertions.assertEquals(expectedLevel, updated.getLevel());
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
  public void upgradeAllOrNothing() {
    TestUserService testUserService = new TestUserService(users.get(3).getId());
    testUserService.setUserDao(userDao);
    testUserService.setMailSender(mailSender);
    testUserService.setUserLevelUpgradePolicy(this.policy);

    TransactionHandler txHandler = new TransactionHandler();
    txHandler.setTarget(testUserService);
    txHandler.setTransactionManager(transactionManager);
    txHandler.setPattern("upgradeLevels");

    UserService txUserService = (UserService) Proxy.newProxyInstance(
        getClass().getClassLoader(),
        new Class[] { UserService.class },
        txHandler
    );
//    UserServiceTx txUserService = new UserServiceTx();
//    txUserService.setTransactionManager(transactionManager);
//    txUserService.setUserService(testUserService);

    userDao.deleteAll();
    for (User user : users) {
      userDao.add(user);
    }

    try {
      txUserService.upgradeLevels();
      fail("TestUserServiceException expected");
    } catch (TestUserServiceException e) {

    }
    checkLevelUpgrade(users.get(1), false);
  }

  static class TestUserService extends UserServiceImpl {

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


    @Override
    public void send(MailMessage message) {
      requests.add(message.getTo());
    }
  }

  private static class MailException extends Exception {

  }

  static class MockUserDao implements UserDao{

    private List<User> users;
    private List<User> updated = new ArrayList();

    private MockUserDao(List<User> users){
      this.users = users;
    }

    public List<User> getUpdated(){
      return this.updated;
    }

    @Override
    public List<User> getAll(){
      return this.users;
    }

    @Override
    public void update(User user) {
      updated.add(user);
    }

    @Override
    public void add(User user) {
      throw new UnsupportedOperationException();
    }

    @Override
    public User get(String id) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
      throw new UnsupportedOperationException();
    }

    @Override
    public int getCount() {
      throw new UnsupportedOperationException();
    }
  }
}