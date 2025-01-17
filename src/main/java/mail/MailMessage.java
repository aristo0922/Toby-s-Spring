package mail;

public interface MailMessage {
  void setTo(String to);
  void setFrom(String from);
  void setSubject(String subject);
  void setText(String text);
}
