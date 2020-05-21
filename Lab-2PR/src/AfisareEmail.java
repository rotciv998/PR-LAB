import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class AfisareEmail{
   public static void main(String[] args) {
     // configurarile 
      String hostval = "pop.gmail.com";
      String mailStrProt = "pop3";
      String uname = "exemplu1998@gmail.com";
      String pwd = "Anulare1.";
    // Apelarea metodei checkMail pentru a verifica e-mailurile primite
      checkMail(hostval, mailStrProt, uname, pwd);
   }
   public static void checkMail(String hostval, String mailStrProt, String uname,String pwd) 
   {
      try {
      
      Properties propvals = new Properties();
      propvals.put("mail.pop3.host", hostval);
      propvals.put("mail.pop3.port", "995");
      propvals.put("mail.pop3.starttls.enable", "true");
      Session emailSessionObj = Session.getDefaultInstance(propvals);  
  // Creare obiectul POP3  șiconectare cu serverul
      Store storeObj = emailSessionObj.getStore("pop3s");
      storeObj.connect(hostval, uname, pwd);

      // creare emailFolderObj  si ssetarea lui doar pentru citire
      Folder emailFolderObj = storeObj.getFolder("INBOX");
      emailFolderObj.open(Folder.READ_ONLY);

      // capturam mesaj din folder si  printam  prin un  un ciclu
      Message[] messageobjs = emailFolderObj.getMessages(); 
 
      for (int i = 0, n = messageobjs.length; i < n; i++) {
         Message indvidualmsg = messageobjs[i];
         System.out.println("Printing individual messages");
         System.out.println("No# " + (i + 1));
         System.out.println("Email Subject: " + indvidualmsg.getSubject());
         System.out.println("Sender: " + indvidualmsg.getFrom()[0]);
         System.out.println("Content: " + indvidualmsg.getContent().toString());

      }
    
      emailFolderObj.close(false);
      storeObj.close();
      } catch (NoSuchProviderException exp) {
         exp.printStackTrace();
      } catch (MessagingException exp) {
         exp.printStackTrace();
      } catch (Exception exp) {
         exp.printStackTrace();
      }
   }
}