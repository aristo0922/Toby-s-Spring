package user.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class User {

  String id;
  String name;
  String password;

  Level level;
  int login;
  int recommend;


  public User() {  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setId(String id) {
    this.id = id;
  }
  public void setLevel(Level level) {
    this.level = level;
  }

  public void setLogin(int login) {
    this.login = login;
  }

  public void setRecommend(int recommend) {
    this.recommend = recommend;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public Level getLevel() {
    return level;
  }

  public int getLogin() { return login; }

  public int getRecommend() {
    return recommend;
  }
}
