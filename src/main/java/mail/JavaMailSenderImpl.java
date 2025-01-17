package mail;

public class JavaMailSenderImpl {// implements MailSender

  String host;
  public void setHost(String host){
    this.host = host;
  }

  public void send(SimpleMailMessage mailMessage) {
    System.out.println("[Message Receive]");
    System.out.println(mailMessage);
  }
}
