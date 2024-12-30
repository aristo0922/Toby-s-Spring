package user.exception.ejbModules;

public class EJBHomeFactory {
  private static EJBHomeFactory instance = new EJBHomeFactory();
  private EJBHomeFactory(){}

  public static EJBHomeFactory getInstance(){
    return instance;
  }

  public OrderHome getOrderHome(){
    return new OrderHome();
  }
}
