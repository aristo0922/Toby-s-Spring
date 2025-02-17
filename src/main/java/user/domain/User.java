package user.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class User {

  String id;
  String name;
  String password;

  String email;
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

  public void setEmail(String email){
    this.email = email;
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

  public void upgradeLevel(){
    Level nextLevel = this.level.nextLevel();
    if (nextLevel == null) throw new IllegalStateException(this.level+"은 업그레이드가 불가합니다.");
    else this.level = nextLevel;
  }

  public String getEmail() {
    return email;
  }
}
