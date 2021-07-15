/*@author Avijit Debnath*/

package obps.util.notifications;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.InternetAddress;

public class ServiceEmail 
{
    String PORT = "25";
    String HOST_NAME = "164.100.14.95";

    public void sendEmail(String fromemail,String password,String toemail,String subject,String message) throws MessagingException 
    {
        Properties props = new Properties();
        props.setProperty("mail.user", fromemail);
        props.setProperty("mail.password", password);
        props.put("mail.debug", "false");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.starttls.enable", true);
        final javax.mail.Session session = javax.mail.Session.getInstance(props);
        MimeMessage message1 = new MimeMessage(session);

        message1.setFrom(new InternetAddress(fromemail));
        message1.addRecipient(Message.RecipientType.TO,new InternetAddress(toemail));
        message1.setSubject(subject);

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(message);
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        message1.setContent(multipart);
        Transport.send(message1);
        
        props = null;
        message1 = null;
        messageBodyPart = null;
        multipart = null;
    }
}
