package water_pi;

import java.io.IOException;

import javax.mail.MessagingException;

import water_pi.utils.Config;

public class Core {
	public static void main(String[] args) throws InterruptedException, IOException, MessagingException {
		//read config
		Config myConfig = new Config();
		myConfig.readPropValues();
		
		//setup mailer
		Mail myMail = new Mail(myConfig.getOwnerEmail(), 
				myConfig.getMailAccount(), 
				myConfig.getMailPW(),
				myConfig.getImapHost(),
				myConfig.getImapPort(),
				myConfig.getSmtpHost(),
				myConfig.getSmtpPort()
				);
		
		//enter main loop
		while(true){
			//check mail
			System.out.println("==> reading mail");
			myMail.readGmail();
			System.out.println("==> mails read");
			//execute commands
			
			//waiting interval
			Thread.sleep(60000); //1000msec = 1 sec
		}
	}
}
