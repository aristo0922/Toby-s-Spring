package springbook.learningtest.template;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorTest {

  Calculator calculator;
  String numFilepath;

  @BeforeEach
  public void setUp() throws Exception{
    this.calculator = new Calculator();
    this.numFilepath = getClass().getResource("/numbers.txt").getPath();
  }

  @Test
  public void sumOfNumbers() throws IOException{
    Assertions.assertNotNull(numFilepath);
    Assertions.assertEquals(10, calculator.calcSum(this.numFilepath));
  }

  @Test
  public void multiplyOfNumbers() throws IOException{
    Assertions.assertEquals(24, calculator.calcMultiply(this.numFilepath));
  }
}