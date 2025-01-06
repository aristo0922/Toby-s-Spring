package user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.domain.UserDao;

@Service
public class UserService {

  UserDao userDao;

  @Autowired
  public void setUserDao(UserDao userDao){
    this.userDao = userDao;
  }
}
