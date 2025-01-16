package ch01;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import user.domain.DaoForTest;
import user.domain.Level;
import user.domain.User;
import user.domain.UserDao;
import user.domain.UserDaoJdbc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DaoForTest.class})
@DirtiesContext
public class ExceptionLearning {

  @Autowired UserDao dao;
  @Autowired DataSource dataSource;
  User user1;


  @Before
  public void setup() {
    user1 = new User("dkfud2121", "장아령", "springno1", "dkfud2121@naver.com",Level.BASIC, 0, 0);
  }

  @Test
  public void sqlExceptionTranslate(){
    dao.deleteAll();

    try{
      dao.add(user1);
      dao.add(user1);
    }catch(DuplicateKeyException ex){
      SQLException sqlEx = (SQLException) ex.getRootCause();
      SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

//      Assertions.assertThrows(DuplicateKeyException.class,
//          (Executable) set.translate(null, null, sqlEx));
//      Assertions.assertEquals(DuplicateKeyException.class, set.translate(null, null, sqlEx));
      Assertions.assertInstanceOf(DuplicateKeyException.class, set.translate(null, null, sqlEx));
    }
  }

}
