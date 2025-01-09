package user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import user.domain.strategy.StatementStrategy;

public class JdbcContext {
  private DataSource dataSource;

  public void setDataSource(DataSource dataSource){
    this.dataSource = dataSource;
  }

  public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException{
    Connection c = null;
    PreparedStatement ps = null;

    try{
      c = this.dataSource.getConnection();
      ps = stmt.makePreparedStatement(c);
      ps.executeUpdate();
    }catch (SQLException e){
      throw e;
    } finally {
      if (ps != null) { try { ps.close();} catch(SQLException e){}}
      if ( c!= null ) { try { c.close();} catch (SQLException e){}}
    }
  }

  public void useTransactionJdbc() throws SQLException {
    Connection c = null;

    try{
      c = this.dataSource.getConnection();
      c.setAutoCommit(false); // 트랜잭션 시작
      PreparedStatement st1 = c.prepareStatement("update users ...");
      st1.executeUpdate();

      PreparedStatement st2 = c.prepareStatement("delete users ...");
      st2.executeUpdate();

      c.commit();
    }catch (Exception e){
      if (c!=null){
        c.rollback();
      }
    } finally {
      if ( c!= null ) { try { c.close();} catch (SQLException e){}}
    }
  }


  public void executeSql(final String query) throws SQLException{
    workWithStatementStrategy(
        new StatementStrategy() {
          @Override
          public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
            return c.prepareStatement(query);
          }
        }
    );
  }
}
