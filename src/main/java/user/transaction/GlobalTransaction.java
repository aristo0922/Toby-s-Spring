package user.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class GlobalTransaction {
  private DataSource dataSource;
  private final static String USER_TX_JNDI_NAME="";
//  public void useJTA() throws NamingException, SQLException {
//    InitialContext ctx = new InitialContext();
//    UserTransaction tx = (UserTransaction) ctx.lookup(USER_TX_JNDI_NAME);
//
//    tx.begin();
//    Connection c = dataSource.getConnection();
//
//    try{
//      tx.commit();
//    }catch (Exception e){
//      tx.rollback();
//      throw e;
//    } finally {
//      c.close();
//    }
//  }

}
