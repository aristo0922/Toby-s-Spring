package user.domain;

public class User {

  String id;
  String name;
  String password;

  Level level;
  int login;
  int recommand;

  public Level getLevel(){
    return level;
  }

  public void setLevel(Level level) {
    this.level = level;
  }

  public User() {
  }

  public User(String id, String name, String password) {
    this.id = id;
    this.name = name;
    this.password = password;
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

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int size() {
    return 0;
  }
}
