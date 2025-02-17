package ch01.dao.try3;

import java.util.List;
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
import user.domain.Level;
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
        User user = new User("1", "user", "123456", "dkfud2121@averc.com",Level.BASIC, 0, 0);
        dao.add(user);
    }

    private User user1;
    private User user2;
    private User user3;
    @BeforeEach
    void setUsers(){
        user1 = new User("villain", "Musk", "villains", "dkfud2121@naver.com",Level.BASIC, 0, 0);
        user2 = new User("xdinary", "heroes", "villains", "dkfud2121@naver.com",Level.BASIC, 0, 0);
        user3 = new User("hello", "world", "TEST_ME", "dkfud2121@naver.com",Level.BASIC, 0, 0);
    }

    @Test
    public void getAll() throws SQLException {
        dao.deleteAll();
        List<User> users = dao.getAll();
        Assertions.assertEquals(0, users.size());

        dao.add(user1);
        List<User> users1 = dao.getAll();
        for (User user : users1) {
            System.out.println("user id = " + user.getId());
            System.out.println("user name = " + user.getName());
            System.out.println("user password = " + user.getPassword());
        }
        Assertions.assertEquals(1, users1.size());
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        Assertions.assertEquals(2, users2.size());
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        Assertions.assertEquals(3, users3.size());
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    private void checkSameUser(User user1, User user2){
        Assertions.assertEquals(user2.getId(), user1.getId());
        Assertions.assertEquals(user2.getName(), user1.getName());
        Assertions.assertEquals(user2.getPassword(), user1.getPassword());
    }

    @Test
    void getEmptyList() throws SQLException{
        dao.deleteAll();

        List<User> users0 = dao.getAll();
        Assertions.assertEquals(0, users0.size());
    }
}

