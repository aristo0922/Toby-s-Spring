package user.domain.test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class JUnitTest {

  static JUnitTest testObject;
  static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();

  @Test
  public void test1() {
    assertThat(testObjects, not(hasItem(this)));
    testObjects.add(this);
  }

  @Test
  public void test2() {
    assertThat(testObjects, not(hasItem(this)));
    testObjects.add(this);
  }

  @Test
  public void test3() {
    assertThat(testObjects, not(hasItem(this)));
    testObjects.add(this);
  }
}
