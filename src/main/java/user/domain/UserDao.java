package user.domain;

import java.sql.Connection;
import java.util.List;

public interface UserDao {
  void update(User user1);

  void add(User user);

  User get(String id);

  List<User> getAll();

  void deleteAll();

  int getCount();
}
