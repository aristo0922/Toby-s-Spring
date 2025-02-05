package springbook.learningtest.template;

import java.lang.reflect.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReflectionTest {

  @Test
  public void invokeMethod() throws Exception{
    String name = "String";

    Assertions.assertEquals(6, name.length());

    Method lengthMethod = String.class.getMethod("length");
    Assertions.assertEquals(6, (Integer)lengthMethod.invoke(name));

    Assertions.assertEquals('S', name.charAt(0));

    Method charAtMethod = String.class.getMethod("charAt", int.class);
    Assertions.assertEquals('S', (Character) charAtMethod.invoke(name, 0));
  }

}
