package ch01.dao.try3;

import java.sql.SQLException;
import user.domain.UserDao;
import user.domain.connectionMaker.ConnectionMaker;
import user.domain.connectionMaker.DConnectionMaker;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException,
            SQLException{
//        ConnectionMaker connectionMaker = new DConnectionMaker();
        // connectionMaker 와 DAO 의 책임을 클라이언트한테 전가.
        UserDao dao = new UserDao();
    }

}