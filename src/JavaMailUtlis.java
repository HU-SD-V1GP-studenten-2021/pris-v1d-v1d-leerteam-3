import java.io.IOException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class JavaMailUtlis {

    public static void sendEmail(String recepient) throws Exception{

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String username = "jens.rijks123@gmail.com";
        String password = "Starter123";


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = prepareMessage(session, username, recepient);

        Transport.send(message);
        System.out.println("Message sent suc6ful");
    }

    private static Message prepareMessage(Session session, String username, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("My email niffo");
            message.setText("Hallo meneer de gappie");
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//        //Start our mail message
//        MimeMessage msg = new MimeMessage(session);
//        try {
//            msg.setFrom(new InternetAddress(fromEmail));
//            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
//            msg.setSubject("Hoi");
//            msg.setText("asdasdasdasdaSdasdasdasd");
//            Transport.send(msg);
//            System.out.println("Sent message");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }




}
