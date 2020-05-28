package com.example.sendmail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Gmail {

    MessageModel messageModel = null;
    List<MessageModel> messageModels;

    String recipient;
    String myEmail;
    String password;
    String subject;
    String textBody;

    Session session;

    public Gmail(String recipient, String myEmail, String password, String subject, String textBody) {
        this.recipient = recipient;
        this.myEmail = myEmail;
        this.password = password;
        this.subject = subject;
        this.textBody = textBody;
    }

    public void sendMail() throws MessagingException {

        System.out.println("Preparing to send message....");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        //Connect to the server
        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });

        final Message message = prepareMessage();
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
        sendMessage.start();
        System.out.println("Message sent successfully");
    }

    public Message prepareMessage() {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(textBody);
            return message;
        }
        catch (Exception e) {
            Logger.getLogger(Gmail.class.getName()).log(Level.SEVERE, null, e);
        }

        return null;
    }


    public List<MessageModel> ReciveMessages(String email, String password) throws IOException {

        String host = "imap.gmail.com";
        String provider  = "imaps";
        try
        {
            Properties properties = new Properties();

            //Connect to the server
            Session session = Session.getDefaultInstance(properties, null);
            Store store     = session.getStore(provider);
            store.connect(host, email, password);

            //open the inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // get a list of javamail messages as an array of messages
            Message[] messages = inbox.getMessages();

            System.out.println("messages.length---" + messages.length);

            messageModels = new ArrayList<>();

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                messageModel = new MessageModel();
                messageModel.setFrom(message.getFrom()[0].toString());
                messageModel.setMessage(message.getContent().toString());
                messageModel.setSubject(message.getSubject());

                messageModels.add(messageModel);

                System.out.println("ccccccccccccccccccccccccccccccccccc ====== " + messageModel.getFrom());

                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
                System.out.println("---------------------------------");
            }

            //close the inbox folder but do not
            //remove the messages from the server
            inbox.close(false);
            store.close();
        }
        catch (NoSuchProviderException nspe)
        {
            System.err.println("invalid provider name");
        }
        catch (MessagingException me)
        {
            System.err.println("messaging exception");
            me.printStackTrace();
        }

        return messageModels;
    }

}
