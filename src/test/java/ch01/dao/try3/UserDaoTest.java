package ch01.dao.try3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import user.dao.JdbcContext;
import user.domain.DaoForTest;
import user.domain.User;
import user.domain.UserDao;

import javax.sql.DataSource;
import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoForTest.class})
@DirtiesContext
public class UserDaoTest {
    @Autowired
    private ApplicationContext context;

    private DataSource dataSource;
    private JdbcContext jdbcContext;

    @Autowired
    private UserDao dao;

    @BeforeEach
    public void setUp() {
        dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb", "root", "0000", true);

        jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(dataSource);

        dao.setDataSource(dataSource);
//        dao.setJdbcContext(jdbcContext);
    }

    @Test
    public void getUserFailure() throws SQLException, ClassNotFoundException {

        Assertions.assertNotNull(context);
        Assertions.assertNotNull(dao);
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(jdbcContext);

        dao.deleteAll();
        Assertions.assertEquals(0, dao.getCount());

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> dao.get("unknown_id"));
    }

    @Test
    public void createUser() throws SQLException, ClassNotFoundException {
        Assertions.assertNotNull(context);
        Assertions.assertNotNull(dao);
        Assertions.assertNotNull(dataSource);
        dao.deleteAll();
        User user = new User("1", "user@naver.com", "123456");
        dao.add(user);
    }
}

