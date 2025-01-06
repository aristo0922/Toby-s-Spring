package user.domain;

import java.util.List;

public interface UserDao {
  public void update(User user1);

  void add(User user);

  User get(String id);

  List<User> getAll();

  void deleteAll();

  int getCount();
}
