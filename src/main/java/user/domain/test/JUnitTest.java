package user.domain.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class JUnitTest {

  static JUnitTest testObject;

  @Test
  public void test1() {
    assertThat(this, is(not(sameInstance(testObject))));
    testObject = this;
  }

  @Test
  public void test2() {
    assertThat(this, is(not(sameInstance(testObject))));
    testObject = this;
  }

  @Test
  public void test3() {
    assertThat(this, is(not(sameInstance(testObject))));
    testObject = this;
  }
}
