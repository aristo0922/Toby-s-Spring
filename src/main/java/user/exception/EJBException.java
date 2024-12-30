package user.exception;

import javax.naming.NamingException;

public class EJBException extends Throwable {

  public EJBException(NamingException ne) {
    super(ne);
  }
}
