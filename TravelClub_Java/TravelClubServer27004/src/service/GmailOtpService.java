package service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class GmailOtpService {

    private static final String FROM_EMAIL = "elichadaihatunga@gmail.com";
    private static final String APP_PASSWORD = "jxrmpboeotkfhexn";

    public static boolean sendOtp(String toEmail, String otp) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Travel Club - Your OTP Code");
            message.setText(
                "Dear Travel Club Member,\n\n" +
                "Your One-Time Password (OTP) is:\n\n" +
                "    " + otp + "\n\n" +
                "This code expires in 5 minutes.\n" +
                "Do not share this code with anyone.\n\n" +
                "Travel Club Management System"
            );

            Transport.send(message);
            System.out.println("[Email] OTP sent successfully to " + toEmail);
            return true;

        } catch (Exception e) {
            System.err.println("[Email] Failed to send OTP to " + toEmail + ": " + e.getMessage());
            return false;
        }
    }
}