package user.exception.bank;

import java.math.BigDecimal;

public class Service {
  private static Account account ;

  public static void main(String[] args) {
    account = new Account();
    Service serivce = new Service();
    serivce.deposit(1000);
  }

  public void deposit(int amount){
    try{
      BigDecimal balance = account.withdraw(amount);
      System.out.println(balance);
      // 정상 처리 결과 출력
    } catch (InsufficientBalanceException e){
      BigDecimal availFunds = e.getAvailFunds();
      System.out.println("[한도 초과] availFunds = " + availFunds);
      // 잔고 부족 안내 메시지를 준비하고 출력
    }
  }
  static class InsufficientBalanceException extends RuntimeException{

    InsufficientBalanceException(){
      super();
    }

    BigDecimal getAvailFunds(){
      return BigDecimal.valueOf(account.getAccount());
    }
  }

  static class Account{
    private BigDecimal money;

    private Account(){
      this.money = BigDecimal.valueOf(0);
    }

    BigDecimal withdraw(int amount){
      int result = Integer.valueOf(String.valueOf(money)) - amount;
      if (result < 0){
        throw new InsufficientBalanceException();
      }
      this.money = BigDecimal.valueOf(result);
      return money;
    }

    public int getAccount(){
      return Integer.parseInt(String.valueOf(money));
    }
  }

}
