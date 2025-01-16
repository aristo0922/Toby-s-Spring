package user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import user.domain.Level;
import user.domain.User;
import user.domain.UserDao;
import user.domain.UserLevelUpgradePolicy;
import javax.mail.Session;

@Service
public class UserService {

  UserDao userDao;
  private PlatformTransactionManager transactionManager;
  public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
  public static final int MIN_RECCOMEND_FOR_GOLD = 50;
  private UserLevelUpgradePolicy policy;

  public void setTransactionManager(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  @Autowired
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Autowired
  public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy policy) {
    this.policy = policy;
  }

  public void upgradeLevels() throws SQLException {
    TransactionStatus status = this.transactionManager.getTransaction(
        new DefaultTransactionDefinition());

    try {
      List<User> users = userDao.getAll();
      for (User user : users) {
        if (policy.canUpgradeLevel(user)) {
          upgradeLevel(user);
        }
      }
      this.transactionManager.commit(status);
    } catch (RuntimeException e) {
      this.transactionManager.rollback(status);
      throw e;
    }
  }

  protected void upgradeLevel(User user) {
    policy.upgradeLevel(user);
    userDao.update(user);
    sendUpgradeEmail(user);
  }
  /** 메일 HOST **/
  private static final String HOST = "smtp.naver.com";
  /** 메일 PORT **/
  private static final String PORT = "465";
  /** 메일 ID **/
  private static final String MAIL_ID = "dkfud2121@naver.com";
  /** 메일 PW **/
  private static String MAIL_PW ;
  private void sendUpgradeEmail(User user){
    Properties props = new Properties();
    // SMTP 발송 Properties 설정
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.host", HOST);
    props.put("mail.smtp.port", PORT);
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.ssl.trust", HOST);
    props.put("mail.smtp.auth", "true");


//    Session s = Session.getInstance(props, null);

    // SMTP Session 생성
    Session s = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
      protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(MAIL_ID, MAIL_PW);
      }
    });

    MimeMessage message = new MimeMessage(s);

    try{
      message.setFrom(new InternetAddress(MAIL_ID));
      if (user.getEmail() == null) {
        System.out.println("CANNOT GET EMAIL!!]");
      }
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
      message.setSubject("[TOBY SPRING] Upgrade 안내");
      message.setText("사용자 등급이 "+user.getLevel().name() + "로 업그레이드 되었습니다.");
      Transport.send(message);
    } catch (AddressException e) {
      throw new RuntimeException(e);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

  public void add(User user) {
    if (user.getLevel() == null) {
      user.setLevel(Level.BASIC);
    }
    userDao.add(user);
  }
}
