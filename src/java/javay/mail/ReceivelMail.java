package javay.mail;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class ReceivelMail {

    public static void main(String[] args) {
        String host = "192.168.11.2";
        String username = "testuser";
        String usepwd = "testpwd";
        
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "110");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.store.protocol", "pop3");
        
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        try {
            Store store = session.getStore("pop3");
            store.connect(host, username, usepwd);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();
            for (int i = 0; i < messages.length; i ++) {
                System.out.println(i + ":" + messages[i].getFrom()[0] + "\t" + messages[i].getSubject());
            }
            folder.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
