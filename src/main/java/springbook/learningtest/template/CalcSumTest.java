package springbook.learningtest.template;

import java.io.IOException;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalcSumTest {

  Calculator calculator;
  String numFilepath;

  @BeforeEach
  public void setUp(){
    this.calculator = new Calculator();
    this.numFilepath = getClass().getResource("/numbers.txt").getPath();
  }

  @Test
  public void sumOfNumbers() throws IOException {
    Assertions.assertEquals(10, calculator.calcSum(this.numFilepath));
  }

  @Test
  public void multiplyOfNumbers() throws IOException{
    Assertions.assertEquals(24, calculator.calcMultiply(this.numFilepath));
  }
}
