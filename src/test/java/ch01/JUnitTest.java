package ch01;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JUnitTest {
  static JUnitTest testObject;
  static Set<JUnitTest> testObjects = new HashSet<>();

  @Test void test1(){
    Assertions.assertNotEquals(this, testObject);
    Assertions.assertTrue(!testObjects.contains(this));
    testObjects.add(this);
    testObject = this;
  }
  @Test void test2(){
    Assertions.assertNotEquals(this, testObject);
    Assertions.assertTrue(!testObjects.contains(this));
    testObjects.add(this);
    testObject = this;
  }
  @Test void test3(){
    Assertions.assertNotEquals(this, testObject);
    Assertions.assertTrue(!testObjects.contains(this));
    testObjects.add(this);
    testObject = this;
  }
}
