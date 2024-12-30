//package user.exception;
//
//import java.rmi.RemoteException;
//import java.sql.SQLException;
//import javax.naming.NamingException;
//import user.exception.ejbModules.EJBHomeFactory;
//import user.exception.ejbModules.Order;
//import user.exception.ejbModules.OrderHome;
//
//
//public class WrappedException {
//  private static Long id = 0L;
//  public void module() throws EJBException {
//    try{
//      OrderHome orderHome = EJBHomeFactory.getInstance().getOrderHome();
//      Order order = orderHome.findByPrimaryKey((Integer) id);
//    }catch (NamingException ne){
//      throw new EJBException(ne);
//    } catch (SQLException se){
//      throw new EJBException(se);
//    }catch (RemoteException re){
//      throw new EJBException(re);
//    }
//  }
//
//}
