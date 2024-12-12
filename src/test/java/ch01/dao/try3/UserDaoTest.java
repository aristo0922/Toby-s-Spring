package ch01.dao.try3;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import user.domain.User;
import user.domain.UserDao;
import user.domain.connectionMaker.ConnectionMaker;
import user.domain.connectionMaker.DConnectionMaker;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
class UserDaoTest {
    UserDao dao;

    @Before
    public void setUp(){
        this.dao = new UserDao();

        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb", "root", "0000", true);
        dao.setDataSource(dataSource);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        Assertions.assertEquals(dao.getCount(), 0);

        dao.get("unknown_id");
    }
}
