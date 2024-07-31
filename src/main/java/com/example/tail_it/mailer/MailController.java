package com.example.tail_it.mailer;

import java.io.File;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import javax.activation.*;

public class MailController {

    public static void sendMail(String to, String cname, String sub, String emailBody) {
        String from = "rishabhsaggar927@gmail.com";

        final String username = "rishabhsaggar927@gmail.com";
        final String password = "ndgl zouy eufz uzhs";

        String host = "smtp.gmail.com";

        Properties prop = new Properties();

        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", "587");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(sub);
            message.setText(emailBody);

            Transport.send(message);

            System.out.println("Mail Sent !!");
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }


    }


}
