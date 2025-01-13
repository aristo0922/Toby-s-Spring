package user.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import user.domain.Level;
import user.domain.User;
import user.domain.UserDao;
import user.domain.UserLevelUpgradePolicy;

@Service
public class UserService {

  UserDao userDao;
  private PlatformTransactionManager transactionManager;
  public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
  public static final int MIN_RECCOMEND_FOR_GOLD = 50;
  private UserLevelUpgradePolicy policy;

  public void setTransactionManager(PlatformTransactionManager transactionManager){
    this.transactionManager = transactionManager;
  }

  @Autowired
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Autowired
  public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy policy) {
    this.policy = policy;
  }

  public void upgradeLevels() throws SQLException {
    TransactionStatus status = this.transactionManager.getTransaction(
        new DefaultTransactionDefinition());

    try {
      List<User> users = userDao.getAll();
      for (User user : users) {
        if (policy.canUpgradeLevel(user)) {
          upgradeLevel(user);
        }
      }
      this.transactionManager.commit(status);
    } catch (RuntimeException e) {
      this.transactionManager.rollback(status);
      throw e;
    }
  }

  protected void upgradeLevel(User user) {
    policy.upgradeLevel(user);
    userDao.update(user);
  }

  public void add(User user) {
    if (user.getLevel() == null) {
      user.setLevel(Level.BASIC);
    }
    userDao.add(user);
  }
}
