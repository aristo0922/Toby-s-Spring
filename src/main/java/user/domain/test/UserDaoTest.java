package user.domain.test;

import java.sql.SQLException;
import user.domain.DaoFactory;
import user.domain.UserDao;
import user.domain.connectionMaker.ConnectionMaker;
import user.domain.connectionMaker.DConnectionMaker;

public class UserDaoTest {

  public static void main(String[] args) throws ClassNotFoundException, SQLException{
    ConnectionMaker connectionMaker = new DConnectionMaker();
    UserDao dao = new DaoFactory().userDao();
  }

}
