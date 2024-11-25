package user.domain;

import java.sql.SQLException;

public class UserDaoTest {

  public static void main(String[] args) throws ClassNotFoundException, SQLException{
    ConnectionMaker connectionMaker = new DConnectionMaker();
    UserDao dao = new DaoFactory().userDao();
  }

}
