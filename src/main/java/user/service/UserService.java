package user.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.domain.Level;
import user.domain.User;
import user.domain.UserDao;

@Service
public class UserService {

  UserDao userDao;

  @Autowired
  public void setUserDao(UserDao userDao){
    this.userDao = userDao;
  }

  public void upgradeLevels(){
    List<User> users = userDao.getAll();
    for(User user: users) {
      Boolean changed = null;
      if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
        user.setLevel(Level.SILVER);
        changed = true;
      } else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
        user.setLevel(Level.GOLD);
        changed = true;
      } else if (user.getLevel() == Level.GOLD) {
        changed = false;
      } else {
        changed = false; // 굳이 필요한 코드일까?
      }
      if (changed) {
        userDao.update(user);
      }
    }
  }
}
