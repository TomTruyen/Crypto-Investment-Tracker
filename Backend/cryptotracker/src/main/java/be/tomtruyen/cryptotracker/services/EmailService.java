package be.tomtruyen.cryptotracker.services;

import com.sun.mail.util.BASE64EncoderStream;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Base64;
import java.util.Properties;

public class EmailService {
    private final static String fromMail = "noreply.cryptotracker@gmail.com";
    private final static String fromPassword = "Stawrejo9";

    static void sendEmail(String toMail) {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromMail, fromPassword);
            }
        });

        //session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromMail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
            message.setSubject("[CryptoTracker] - Verify email");

            String base64Email = Base64.getEncoder().encodeToString(toMail.getBytes());
            String verificationURL = "http://localhost:8889/verify?e=" + base64Email;

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Please verify your email by clicking the link below:");
            stringBuilder.append("<br />");
            stringBuilder.append("<br />");
            stringBuilder.append("<a href='").append(verificationURL).append("' target='_blank'>").append(verificationURL).append("</a>");

            message.setContent(stringBuilder.toString(), "text/html; charset=utf-8");

            System.out.println("Sending email...");

            Transport.send(message);

            System.out.println("Email sent.");
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
