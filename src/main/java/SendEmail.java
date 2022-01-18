import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    public static void main(String[] args) {
        Config config = ConfigFactory.load();

        String to = config.getString("to");
        String from = config.getString("from");
        String host = config.getString("host");

        String username = config.getString("username");
        String password = config.getString("password");

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.user", username);
        properties.setProperty("mail.smtp.password", password);
        properties.setProperty("mail.smtp.auth", "true");

        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(
            properties,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Проверка отправки сообщений из JAVA");
            message.setText("Текст письма");

            Transport.send(message);
            System.out.println("Email Sent successfully....");

        } catch (
            MessagingException mex) {
            mex.printStackTrace();
        }

    }

}
