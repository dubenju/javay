package javay.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    public static void main(String[] args) {
        // Recipient's email ID needs to be mentioned.
        String to = "testuser@xy.com";
        to = "benju.du@beyondsoft.com";
        to = "dubenju@126.com";
        // Sender's email ID needs to be mentioned
        String from = "testuser2@xy.com";
        // Assuming you are sending email from localhost
        String host = "192.168.11.2";
        String username = "testuser2";
        String password = "testpwd";

        // Get system properties
        Properties props = new Properties();

        // Setup mail server
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", 25);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.store.protocol", "pop3");
//        props.put("mail.user", "testuser@192.168.11.2");
//        props.put("mail.password", "testpwd");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        try {
           // Create a default MimeMessage object.
           MimeMessage message = new MimeMessage(session);

           // Set From: header field of the header.
           message.setFrom(new InternetAddress(from));

           // Set To: header field of the header.
           message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

           // Set Subject: header field
           message.setSubject("This is the Subject Line!");

           // Now set the actual message
           message.setText("This is actual message");

           // Send message
//           Session mailSession = Session.getInstance(props, null);
           Transport transport = session.getTransport("smtp");
           transport.connect((String) props.get("mail.smtp.host"),
                   (Integer) props.get("mail.smtp.port"), username, password);
//           transport.sendMessage(message, message.getRecipients(javax.mail.Message.RecipientType.TO));
           transport.sendMessage(message,  message.getAllRecipients());

           transport.close();

           System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
           mex.printStackTrace();
        }
    }

}
