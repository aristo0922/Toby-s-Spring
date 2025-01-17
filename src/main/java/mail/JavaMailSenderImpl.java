package mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JavaMailSenderImpl implements MailSender {// implements MailSender

  private final String host;

  @Override
  public void send(MailMessage message) {
    System.out.println("[Message Receive]");
    System.out.println(message);
  }
}
