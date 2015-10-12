package water_pi;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
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
    
    public Mail(String ownerEmail, String user, String pw, String imapHost, String imapPort, String smtpHost, String smtpPort){
    	this.ownerEmail = ownerEmail;
    	this.user = user;
    	this.pw = pw;
    	setupProperties(imapHost, imapPort, smtpHost, smtpPort);
    }
    
    private void setupProperties(String imapHost, String imapPort, String smtpHost, String smtpPort){
    	//setup mailserver properties
    	mailServerProperties = System.getProperties();
		mailServerProperties.setProperty("mail.imaps.host", imapHost);
		mailServerProperties.setProperty("mail.imaps.port", imapPort);
		mailServerProperties.setProperty("mail.store.protocol", "imaps");
		mailServerProperties.setProperty("mail.smtp.ssl.trust", smtpHost);
		mailServerProperties.setProperty("mail.smtp.port", smtpPort);
		mailServerProperties.setProperty("mail.smtp.auth", "true");
		mailServerProperties.setProperty("mail.smtp.starttls.enable", "true");
    }
    
	public void generateAndSendEmail(String subject, String message) throws AddressException, MessagingException {
		//get session
		Session mailSession = Session.getDefaultInstance(mailServerProperties, null);
		
		//create mail
		MimeMessage mailMessage = new MimeMessage(mailSession);
		mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("ownerEmail"));
		//add subject
		mailMessage.setSubject(subject);
		//add message text
		mailMessage.setContent(message, "text/html");

		//get transport and send mail
		Transport transport = mailSession.getTransport("smtp");

		// Enter your correct gmail UserID and Password (XXXApp Shah@gmail.com)
		transport.connect("smtp.gmail.com", user, pw);
		transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
		transport.close();
	}
	
	public void readGmail() throws MessagingException, IOException {
		//get session
		Session mailSession = Session.getDefaultInstance(mailServerProperties,null);

		//connect to store
		Store store = mailSession.getStore("imaps");
		store.connect(user, pw);
		
		//open inbox
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE); // open folder only to read&write
		
		//get messages
		Message message[] = folder.getMessages();
		for (int i = 0; i < message.length; i++) {
			Address[] froms = message[i].getFrom();
			String fromAdress = ((InternetAddress) froms[0]).getAddress();
			//check for emails from owner, ignore other messages
			if (fromAdress.equals(ownerEmail)){
				// print subjects of all mails in the inbox
				System.out.println("New message from owner: " + fromAdress);
				System.out.println("Subject: " + message[i].getSubject());
				System.out.println("Message: " + message[i].getContent());
			}

			//set delete flag for messages
			message[i].setFlag(Flags.Flag.DELETED, true);
		}
		// close connections
		// expunges the folder to remove messages which are marked deleted
		folder.close(true);
		store.close();

	}

}
