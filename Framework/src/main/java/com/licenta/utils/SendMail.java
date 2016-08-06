package com.licenta.utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * @author Lucian CONDESCU
 */
public class SendMail {

    private static String username;
    private static String password;
    private static String smtpHost;
    private static String smtpPort;

    public static void initMail(String username, String password, String smtpHost,String smtpPort) {
        SendMail.username = username;
        SendMail.password = password;
        SendMail.smtpHost = smtpHost;
        SendMail.smtpPort = smtpPort;
    }

    public static boolean sendMail(String to, String subject, String message, String attachment) {

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", smtpHost);
        properties.setProperty("mail.smtp.port", smtpPort);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.user", username);
        properties.setProperty("mail.smtp.password", password);
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties,
                new Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }});

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(message);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachment);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(source.getName());
            multipart.addBodyPart(messageBodyPart);
            mimeMessage.setContent(multipart);

            Transport.send(mimeMessage);
            System.out.println("Sent message successfully....");
            return true;
        }catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }

}
