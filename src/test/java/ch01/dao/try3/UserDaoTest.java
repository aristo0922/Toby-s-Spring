package ch01.dao.try3;

import ch01.dao.try2.ConnectionMaker;
import ch01.dao.try2.DConnectionMaker;
import ch01.dao.try2.UserDao;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException,
            SQLException{
        ConnectionMaker connectionMaker = new DConnectionMaker();
        // connectionMaker 와 DAO 의 책임을 클라이언트한테 전가.
        UserDao dao = new UserDao(connectionMaker);
    }

}