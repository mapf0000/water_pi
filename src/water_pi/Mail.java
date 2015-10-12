package water_pi;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	private String ownerEmail="";
	private String user="";
	private String pw="";
	private Properties mailServerProperties;
	private Session getMailSession;
	private MimeMessage mailMessage;
    private String receivingHost;
    
    public Mail(String ownerEmail, String user, String pw){
    	this.ownerEmail = ownerEmail;
    	this.user = user;
    	this.pw = pw;
    }
    
	public void generateAndSendEmail() throws AddressException, MessagingException {
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");

		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		mailMessage = new MimeMessage(getMailSession);
		mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("ownerEmail"));
		// generateMailMessage.addRecipient(Message.RecipientType.CC, new
		// InternetAddress("test2@crunchify.com"));
		mailMessage.setSubject("Greetings from Crunchify..");
		String emailBody = "Test email by Crunchify.com JavaMail API example. "+ "<br><br> Regards, <br>Crunchify Admin";
		mailMessage.setContent(emailBody, "text/html");
		System.out.println("Mail Session has been created successfully..");

		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");

		// Enter your correct gmail UserID and Password (XXXApp Shah@gmail.com)
		transport.connect("smtp.gmail.com", user, pw);
		transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
		transport.close();
	}
	
	public void readGmail(){
		 
        /*this will print subject of all messages in the inbox of sender@gmail.com*/
 
        receivingHost="imap.gmail.com";//for imap protocol
 
        Properties props2=System.getProperties();
 
        props2.setProperty("mail.store.protocol", "imaps");
        // I used imaps protocol here
 
        Session session2=Session.getDefaultInstance(props2, null);
 
            try {
 
                    Store store=session2.getStore("imaps");
 
                    store.connect(receivingHost, user, pw);
 
                    Folder folder=store.getFolder("INBOX");//get inbox
 
                    folder.open(Folder.READ_WRITE);//open folder only to read
 
                    Message message[]=folder.getMessages();
 
                    for(int i=0;i<message.length;i++){
                        //print subjects of all mails in the inbox
                        System.out.println(message[i].getSubject());
                        Address[] froms = message[i].getFrom();
                        String fromAdress = ((InternetAddress) froms[0]).getAddress();
                        if(fromAdress.equals(""));
 
                       //Set Delete Flag
             	       message[i].setFlag(Flags.Flag.DELETED, true);
                    }
                    //close connections
                    // expunges the folder to remove messages which are marked deleted
                    folder.close(true);
                    store.close();
            } 
            catch (Exception e) {
                    System.out.println(e.toString());
            }
    }

}
