package user.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.domain.Level;
import user.domain.User;
import user.domain.UserDao;
import user.domain.UserLevelUpgradePolicy;

@Service
public class UserService {

  UserDao userDao;

  public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
  public static final int MIN_RECCOMEND_FOR_GOLD = 50;
  private UserLevelUpgradePolicy policy;

  @Autowired
  public void setUserDao(UserDao userDao){
    this.userDao = userDao;
  }

  @Autowired
  public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy policy){
    this.policy = policy;
  }

  public void upgradeLevels(){
    List<User> users = userDao.getAll();
    for(User user: users) {
      if (policy.canUpgradeLevel(user)) {
        upgradeLevel(user);
      }
    }
  }

  protected void upgradeLevel(User user){
    policy.upgradeLevel(user);
    userDao.update(user);
  }

  public void add(User user){
    if (user.getLevel() == null) user.setLevel(Level.BASIC);
    userDao.add(user);
  }
}
