package javay.mail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * telnet smtp.gmail.com 25 SSL
 * telnet smtp.gmail.com 465  SSL
 * telnet smtp.gmail.com 587 TLS
 * @author dubenju
 *
 */
public class JavaMail {
  /**
   * Message对象将存储我们实际发送的电子邮件信息.
   * Message对象被作为一个MimeMessage对象来创建并且需要知道应当选择哪一个JavaMail session。
   */
  private MimeMessage message;

  /**
   * Session类代表JavaMail中的一个邮件会话。
   * 每一个基于JavaMail的应用程序至少有一个Session（可以有任意多的Session）。
   * JavaMail需要Properties来创建一个session对象。
   * 寻找"mail.smtp.host"  属性值就是发送邮件的主机
   * 寻找"mail.smtp.auth"  身份验证，目前免费邮件服务器都需要这一项
   */
  private Session session;

  /**
   * 邮件是既可以被发送也可以被受到。.JavaMail使用了两个不同的类来完成这两个功能：Transport 和 Store。 
   * Transport 是用来发送信息的，而Store用来收信。对于这的教程我们只需要用到Transport对象。
   */
  private Transport transport;
  
  private String mailHost = "";
  private String senderUsername = "";
  private String senderPassword = "";

  private Properties properties = new Properties();

  /**
   * 初始化方法.
   */
  public JavaMail(boolean debug) {
    // InputStream in = JavaMail.class.getResourceAsStream("../../../conf/MailServer.properties");
    InputStream in = null;
    try {
      in = new FileInputStream("./conf/MailServer.properties");
      properties.load(in);
      this.mailHost = properties.getProperty("mail.smtp.host");
      this.senderUsername = properties.getProperty("mail.sender.username");
      this.senderPassword = properties.getProperty("mail.sender.password");
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    // session = Session.getInstance(properties);
    session = Session.getDefaultInstance(properties);
    // Session session = Session.getInstance( properties, new MyAuth() );
    // Authenticator auth = new SMTPAuthenticator();
    // session = Session.getInstance( properties, new SendMailUsingAuthentication() );
    session.setDebug(debug); // 开启后有调试信息
    message = new MimeMessage(session);
  }

  /**
   * 发送邮件.
   * 
   * @param subject 邮件主题
   * @param sendHtml 邮件内容
   * @param receiveUser 收件人地址
   */
  public void doSendHtmlEmail(String subject, String sendHtml, String receiveUser) {
    try {
      // 发件人
      //InternetAddress from = new InternetAddress(sender_username);
      // 下面这个是设置发送人的Nick name
      InternetAddress from = new InternetAddress(MimeUtility.encodeWord("幻影")
          + " <" + senderUsername + ">");
      message.setFrom(from);
      // message.setFrom(InternetAddress.parse("<mail>"));

      // 收件人
      InternetAddress to = new InternetAddress(receiveUser);
      message.setRecipient(Message.RecipientType.TO, to);//还可以有CC、BCC

      // 邮件主题
      message.setSubject(subject);

      String content = sendHtml.toString();
      // 邮件内容,也可以使纯文本"text/plain"
      message.setContent(content, "text/html;charset=UTF-8");

      // 保存邮件
      message.saveChanges();

      // transport = session.getTransport("smtp");
      transport = session.getTransport("smtps");
      System.out.println("user:" + senderUsername + ",pwd:" + senderPassword);
      // smtp验证，就是你用来发邮件的邮箱用户名密码
      transport.connect(mailHost, Integer.parseInt(properties.getProperty("mail.smtp.port")), senderUsername, senderPassword);
      // 发送
      transport.sendMessage(message, message.getAllRecipients());
      
      //System.out.println("send success!");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (transport != null) {
        try {
          transport.close();
        } catch (MessagingException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void main(String[] args) {
    JavaMail se = new JavaMail(true);
    se.doSendHtmlEmail("邮件主题", "邮件内容", "dbjcalc@163.com");
  }
  
  private class MyAuth extends Authenticator {
	    protected PasswordAuthentication getPasswordAuthentication() {
	      return new PasswordAuthentication( senderUsername, senderPassword);
	    }
	  }
}
