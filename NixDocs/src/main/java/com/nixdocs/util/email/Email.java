package com.nixdocs.util.email;

import com.nixdocs.util.environment.EnvironmentConfig;
import com.nixdocs.util.templateEngine.ThymeleafUtil;

import org.thymeleaf.context.WebContext;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Email {
    final static private String email_address = EnvironmentConfig.getEmailAddress();
    final static private String email_password = EnvironmentConfig.getEmailPassword();
    final static private String email_host = EnvironmentConfig.getEmailHost();

    public static void SendHTMLEmail(String receiver,String email_subject, WebContext email_context,String templateName){
        Properties properties = getProperties();
        try {
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email_address,email_password);
                }
            });
            InternetAddress address = new InternetAddress(receiver);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email_address));
            message.setRecipient(Message.RecipientType.TO,address);
            message.setSubject(email_subject);
            message.setContent(ThymeleafUtil.getParsedTemplate(templateName,email_context),"text/html");
            Transport.send(message);
        }catch (MessagingException e){
            throw new RuntimeException(e);
        }
    }

    private static Properties getProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host",email_host);
        properties.put("mail.smtp.port","587");
        return properties;
    }

}
