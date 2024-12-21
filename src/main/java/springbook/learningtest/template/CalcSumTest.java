package springbook.learningtest.template;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalcSumTest {

  @Test
  public void sumOfNumbers() throws IOException {
    Calculator calculator = new Calculator();
    Integer sum = calculator.calcSum(
        getClass().getResource("/numbers.txt").getPath());
    Assertions.assertEquals(10, sum);
  }
}
