package mail;

import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
public class SimpleMailMessage implements MailMessage{ // implements MailMessage

  private String to;
  private String from;
  private String subject;
  private String text;
  public void setText(String text) {
    this.text = text;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setFrom(String from) {
    this.from=from;
  }

  public void setTo(String to) {
    this.to = to;
  }
}
