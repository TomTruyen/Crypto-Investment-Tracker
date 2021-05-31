package be.tomtruyen.cryptotracker.util.email;

import be.tomtruyen.cryptotracker.util.Utils;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Base64;
import java.util.Properties;

@Service
public class EmailService {
    private final static String fromMail = "noreply.cryptotracker@gmail.com";
    private final static String fromPassword = "Stawrejo9";

    public static void sendVerificationEmail(String toMail) {
        String subject = "[CryptoTracker] - Verify";

        String base64Email = Base64.getEncoder().encodeToString(toMail.getBytes());
        String verificationURL = "http://84.195.217.213/verify/" + base64Email;

        String content = "Please verify your email by clicking the link below:" +
                "<br />" +
                "<br />" +
                "<a href='" + verificationURL + "' target='_blank'>" + verificationURL + "</a>";

        sendEmail(toMail, subject, content, "Verification");
    }

    public static void sendResetPasswordEmail(String toMail) {
        String subject = "[CryptoTracker] - Reset Password";

        String base64Email = Base64.getEncoder().encodeToString(toMail.getBytes());
        String resetPasswordURL = "http://84.195.217.213/resetpassword/" + base64Email;

        String content = "Reset your password by clicking the link below:" + "<br />" + "<br />" + "<a href='" + resetPasswordURL +"'>" + resetPasswordURL + "</a>";

        sendEmail(toMail, subject, content, "Reset Password");
    }

    public static void sendPriceAlertEmail(String toMail, String ticker, double alertPrice, double price) {
        String subject = "[CryptoTracker] - Price Alert (" + ticker + ")";

        String content = String.format("%s has just reached your price alert of $%s. <br />The current price is $%s.", ticker, Utils.priceWithDecimal(alertPrice), Utils.priceWithDecimal(price));

        sendEmail(toMail, subject, content, "Price Alert");
    }

    private static void sendEmail(String toMail, String subject, String content, String type) {
        new Thread(() -> {
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

            try {
                MimeMessage message = new MimeMessage(session);

                message.setFrom(new InternetAddress(fromMail));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
                message.setSubject(subject);


                message.setContent(content, "text/html; charset=utf-8");

                System.out.printf("Sending email... [%s - %s]%n", type, Utils.getDateTime());

                Transport.send(message);

                System.out.printf("Email sent. [%s - %s]%n", type, Utils.getDateTime());
            } catch (MessagingException me) {
                me.printStackTrace();
            }
        }).start();

    }
}
