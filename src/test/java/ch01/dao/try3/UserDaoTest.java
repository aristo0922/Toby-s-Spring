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
import user.domain.DaoForTest;
import user.domain.UserDao;

import javax.sql.DataSource;
import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoForTest.class})
@DirtiesContext
public class UserDaoTest {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserDao dao;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/testdb", "root", "0000", true);
        dao.setDataSource(dataSource);
        System.out.println("setup");
    }

    @Test
    public void getUserFailure() throws SQLException {
        dao.deleteAll();
        Assertions.assertEquals(dao.getCount(), 0);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> dao.get("unknown_id"));
    }
}

