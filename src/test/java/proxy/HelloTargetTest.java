package proxy;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HelloTargetTest {
  String HELLO_RESULT = "Hello Toby";
  String HI_RESULT = "Hi Toby";
  String THANK_YOU_RESULT = "Thank You Toby";
  @Test
  public void simpleProxy(){
    Hello hello = new HelloUppercase(new HelloTarget());
    Assertions.assertEquals(HELLO_RESULT, hello.sayHello("Toby"));
    Assertions.assertEquals(HI_RESULT, hello.sayHi("Toby"));
    Assertions.assertEquals(THANK_YOU_RESULT, hello.sayThankYou("Toby"));
  }

  @Test
  public void createProxy(){
    Hello proxyHello = (Hello) Proxy.newProxyInstance(
      getClass().getClassLoader(),
      new Class[] { Hello.class },
      new UppercaseHandler(new HelloTarget())
    );
    Assertions.assertEquals(HELLO_RESULT.toUpperCase(), proxyHello.sayHello("Toby"));
    Assertions.assertEquals(HI_RESULT.toUpperCase(), proxyHello.sayHi("Toby"));
    Assertions.assertEquals(THANK_YOU_RESULT.toUpperCase(), proxyHello.sayThankYou("Toby"));
  }
}