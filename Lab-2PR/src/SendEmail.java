

/**
 *
 * @author victo
 */
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
   public static void main(String[] args) {
       
       // posta receptor 
      String to = "rotciv998@gmail.com";
       // posta  expediere 
      String from = "exemplu1998@gmail.com";
      final String username = "exemplu1998@gmail.com";
      final String password = "Anulare1.";
      String host = "smtp.gmail.com" ;
      

      Properties props = new Properties(); // obiectul propietes 
      props.put("mail.smtp.auth", "true"); // autorizarea true
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host); //host pe care trimitem 
      props.put("mail.smtp.port", "587");

      //crea obiectul Sesiune.
      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
	   }
         });

      try {
	   // Create obiect MimeMessage .
	   Message message = new MimeMessage(session);
	
	   message.setFrom(new InternetAddress(from));
	
	   message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(to));
	
	   // subiectul
	   message.setSubject("Lab 2 PR");
	
	   // Mesajul 
	   message.setText("Mesaj de verificare" );

	   // expediere mesaj 
	   Transport.send(message);

	   System.out.println("Mesaj expediat cu succes");

      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
   }
}