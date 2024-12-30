package user.exception.ejbModules;

public class OrderHome {

  public Order findByPrimaryKey(Integer id){
    return new Order();
  }
}