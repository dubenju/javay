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
 * telnet smtp.gmail.com  25
 * telnet smtp.gmail.com 465 SSL
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

  // private String mailHost = "";
  //private String senderUsername = "";
  //private String senderPassword = "";

  private Properties properties = new Properties();

  /**
   * 初始化方法.
   */
  public JavaMail(boolean debug) {
    // InputStream in = JavaMail.class.getResourceAsStream("../../../conf/MailServer.properties");
    InputStream in = null;
    try {
      // Step1
      System.out.println("\n 1st ===> setup Mail Server Properties..");
      in = new FileInputStream("./conf/MailServer.properties");
      properties.load(in);
      // this.mailHost = properties.getProperty("mail.smtp.host");
      // this.senderUsername = properties.getProperty("mail.sender.username");
      // this.senderPassword = properties.getProperty("mail.sender.password");
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Mail Server Properties have been setup successfully..");


    // Step1
    System.out.println("\n 1st ===> setup Mail Server Properties..");
    session = Session.getDefaultInstance(properties, null);
    // session = Session.getInstance(properties);
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

      /**
       * 邮件是既可以被发送也可以被受到。.JavaMail使用了两个不同的类来完成这两个功能：Transport 和 Store。
       * Transport 是用来发送信息的，而Store用来收信。对于这的教程我们只需要用到Transport对象。
       */
      Transport transport = null;
	  try {
      // 发件人
      //InternetAddress from = new InternetAddress(sender_username);
      // 下面这个是设置发送人的Nick name
      InternetAddress from = new InternetAddress(MimeUtility.encodeWord("幻影") + " <" + properties.getProperty("mail.sender.username") + ">");
      // 收件人
      InternetAddress to = new InternetAddress(receiveUser);

      // message.setFrom(InternetAddress.parse("<mail>"));
      message.setFrom(from);
      message.setRecipient(Message.RecipientType.TO, to);//还可以有CC、BCC
      // 邮件主题
      message.setSubject(subject);
      String content = sendHtml.toString();
      // 邮件内容,也可以使纯文本"text/plain"
      message.setContent(content, "text/html;charset=UTF-8");
      // 保存邮件
      // message.saveChanges();
      System.out.println("Mail Session has been created successfully..");



      transport = session.getTransport("smtp");
      // transport = session.getTransport("smtps"); *
      System.out.println("user:" + properties.getProperty("mail.sender.username") + ",pwd:" + properties.getProperty("mail.sender.password"));
      // smtp验证，就是你用来发邮件的邮箱用户名密码
      transport.connect(properties.getProperty("mail.smtp.host"), Integer.parseInt(properties.getProperty("mail.smtp.port")), properties.getProperty("mail.sender.username"), properties.getProperty("mail.sender.password"));
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
    // se.doSendHtmlEmail("邮件主题", "邮件内容", "dbjcalc@163.com");
    se.doSendHtmlEmail("邮件主题", "邮件内容", "dbjcalc@gmail.com");
  }

  private class MyAuth extends Authenticator {
	    protected PasswordAuthentication getPasswordAuthentication() {
	      return new PasswordAuthentication( properties.getProperty("mail.sender.username"), properties.getProperty("mail.sender.password"));
	    }
	  }
}

/*
telnet mail.domain.ext 25

－－－－－－－－－－－
$telnet smtp.163.com 25
Trying 123.125.50.138...
telnet: connect to address 123.125.50.138: Operation timed out
Trying 123.125.50.132...
telnet: connect to address 123.125.50.132: Operation timed out
Trying 123.125.50.133...
telnet: connect to address 123.125.50.133: Operation timed out
Trying 123.125.50.134...
telnet: connect to address 123.125.50.134: Operation timed out
Trying 123.125.50.135...
telnet: connect to address 123.125.50.135: Operation timed out
telnet: Unable to connect to remote host
$

$telnet smtp.gmail.com 25
Trying 2404:6800:4008:c07::6c...
telnet: connect to address 2404:6800:4008:c07::6c: Connection refused
Trying 64.233.188.109...
telnet: connect to address 64.233.188.109: Operation timed out
Trying 64.233.188.108...
telnet: connect to address 64.233.188.108: Operation timed out
telnet: Unable to connect to remote host
$
$telnet smtp.sina.com 25
Trying 202.108.6.242...
telnet: connect to address 202.108.6.242: Operation timed out
telnet: Unable to connect to remote host
$
－－－－－－－－－－－

HELO local.domain.name 
MAIL FROM: mail@domain.ext
RCPT TO: mail@otherdomain.ext
DATA
QUIT

SMTP是Simple Mail Transfer Protocol的简写。
邮件是日常工作、生活中不能缺少的一个工具，下面是邮件收发的流程。
邮件的发送，主要是通过SMTP协议来实现的。SMTP协议最早在RFC 821（1982年）中定义，最后更新是在RFC 5321（2008年）中，更新中包含了扩展SMTP（ESMTP）。
在平时的程序开发中经常会有发送邮件的这种需求，所以免不了要对邮件发送服务器的可用性进行测试。下面是整理的命令，用来在命令行下测试SMTP服务器，进行邮件发送等操作，相信对于加深SMTP的理解可以起到促进作用。
SMTP默认使用25端口，我们可以使用telnet工具进行测试。
1、连接SMTP服务器，查看是否存活。
     >telnet smtp.sina.com 25
     >Connected to mail.sina.com.
     >Escape character is '^]'.
     >220 smtp ready
     在telnet下转义符是Ctrol+]，如果想退出，按转义符后输入quit，就可以退回到命令行了。

2、用户登陆
     连接到服务器后，使用AUTH LOGIN命令进行用户登陆（SMTP命令不区分大小写）
     >auth login
     >334 VXNlcm5hbWU6
     >c2VydmljZUBoZWVwLmNx
     >334 UGFzc3dvcmQ6
     >xxxxxxxx
     >235 go ahead

     235返回码表明登陆验证成功，用户可以进行后续的操作了。用户邮箱和密码是经过Base64编码的，这个与服务器的安全特性相关，属于可配置项。

3、发送邮件。
     用户身份验证通过后，执行下面的命令进行邮件的发送。
     >235 #2.0.0 OK Authenticated
     >MAIL FROM: yunpan001@sina.com
     >250 sender <yunpan001@sina.com> ok
     >RCPT to: cocowool@gmail.com
     >250 recipient <cocowool@gmail.com> ok
     >DATA
     >354 go ahead
     >Subject: Hi smtp mail
     >hello mail
     >.
     >250 ok:  Message 1763097690 accepted

SMTP命令列表
HELO
客户端为标识自己的身份而发送的命令（通常带域名）
EHLO
使服务器可以表明自己支持扩展简单邮件传输协议 (ESMTP) 命令。

MAIL FROM
标识邮件的发件人；以 MAIL FROM: 的形式使用。

RCPT TO
标识邮件的收件人；以 RCPT TO: 的形式使用。

TURN
允许客户端和服务器交换角色，并在相反的方向发送邮件，而不必建立新的连接。

ATRN
ATRN (Authenticated TURN) 命令可以选择将一个或多个域作为参数。如果该会话已通过身份验证，则ATRN 命令一定会被拒绝。

SIZE
提供一种使 SMTP 服务器可以指出所支持的最大邮件大小的机制。兼容的服务器必须提供大小范围，以指出可以接受的最大邮件大小。客户端发送的邮件不应大于服务器所指出的这一大小。

ETRN
SMTP 的扩展。SMTP 服务器可以发送 ETRN 以请求另一台服务器发送它所拥有的任何电子邮件。

PIPELINING
提供发送命令流（而无需在每个命令之后都等待响应）的能力。

CHUNKING
替换 DATA 命令的 ESMTP 命令。该命令使 SMTP 主机不必持续地扫描数据的末尾，它发送带参数的 BDAT 命令，该参数包含邮件的总字节数。接收方服务器计算邮件的字节数，如果邮件大小等于 BDAT 命令发送的值时，则该服务器假定它收到了全部的邮件数据。

DATA
客户端发送的、用于启动邮件内容传输的命令。

DSN
启用传递状态通知的 ESMTP 命令。

RSET
使整个邮件的处理无效，并重置缓冲区。

VRFY
确认在邮件传递过程中可以使用邮箱；例如，vrfy ted 确认在本地服务器上驻留 Ted 的邮箱。该命令在 Exchange 实现中默认关闭。

HELP
返回 SMTP 服务所支持的命令列表。

QUIT
终止会话。

SMTP命令响应码
211  System status, or system help reply 
214  Help message (Information on how to use the receiver or the meaning of a particular non-standard command; this reply is useful only to the human user) 
220  <domain> Service ready 
221  <domain> Service closing transmission channel 
250  Requested mail action okay, completed 
251  User not local; will forward to <forward-path> (See Section 3.4) 
252  Cannot VRFY user, but will accept message and attempt delivery (See Section 3.5.3) 
354  Start mail input; end with <CRLF>.<CRLF> 
421  <domain> Service not available, closing transmission channel (This may be a reply to any command if the service knows it must shut down) 
450  Requested mail action not taken: mailbox unavailable (e.g., mailbox busy or temporarily blocked for policy reasons) 
451  Requested action aborted: local error in processing 
452  Requested action not taken: insufficient system storage 
455  Server unable to accommodate parameters 
500  Syntax error, command unrecognized (This may include errors such as command line too long) 
501  Syntax error in parameters or arguments502  Command not implemented (see Section 4.2.4)

503  Bad sequence of commands 
504  Command parameter not implemented 
550  Requested action not taken: mailbox unavailable (e.g., mailbox not found, no access, or command rejected for policy reasons)

551  User not local; please try <forward-path> (See Section 3.4) 
552  Requested mail action aborted: exceeded storage allocation 
553  Requested action not taken: mailbox name not allowed (e.g.,mailbox syntax incorrect) 
554  Transaction failed (Or, in the case of a connection-opening response, "No SMTP service here") 
555  MAIL FROM/RCPT TO parameters not recognized or not implemente


Gmail的smtp端口465和587

Gmail支持smtp转发和pop3接收.
POP3服务器地址： pop.gmail.com 端口：995 支持SSL
SMTP服务器地址： smtp.gmail.com 端口：465 或者 587 支持SSL（TSL）
 
465端口是SSL/TLS通讯协议的 内容一开始就被保护起来了 是看不到原文的。
587端口是STARTTLS协议的 属于TLS通讯协议 只是他是在STARTTLS命令执行后才对之后的原文进行保护的。

windows命令提示行下
>telnet smtp.gmail.com 587

220 mx.google.com ESMTP d1sm2045736tid.14
EHLO AO
250-mx.google.com at your service, [58.22.135.6]
250-SIZE 35651584
250-8BITMIME
250-STARTTLS
250-ENHANCEDSTATUSCODES
250 PIPELINING
AUTH LOGIN
530 5.7.0 Must issue a STARTTLS command first. d1sm2045736tid.14 （看到没第2次命令，输入AUTH他就提示“Must issue a STARTTLS command first.”）
STARTTLS
220 2.0.0 Ready to start TLS
.....这里开始邮件内容的通讯就被保护起来了
QUIT



telnet smtp.gmail.com 587
HELO localhost
RCPT TO:<dbjcalc@gmail.com>
STARTTLS

openssl使用SMTP从gmail发邮件
echo "user@gmail.com" | openssl enc -e -base64
aaaaa
echo "pwd" | openssl enc -e -base64
bbbbb

openssl s_client -starttls smtp -crlf -connect smtp.gmail.com:587
EHLO localhost
AUTH LOGIN
user:aaaaa
pwd:bbbbb
mail from:<user@gmail.com>
rcpt to:<user@163.com>
data
subject:test
this is a test
SEND OK?
.

QUIT

echo "user@163.com" | openssl enc -e -base64
aaaaa
echo "pwd" | openssl enc -e -base64
bbbbb

openssl s_client -starttls smtp -crlf -connect smtp.163.com:587
CONNECTED(00000003)
didn't found starttls in server response, try anyway...
140735123649360:error:140790E5:SSL routines:ssl23_write:ssl handshake failure:s23_lib.c:177:
---
*/
