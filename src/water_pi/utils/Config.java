package water_pi.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class Config {
	final String PROPFILENAME = "config.properties";
	InputStream inputStream;
	
	//config variables
	String ownerEmail;
	String mailAccount;
	String mailPW;
	String smtpHost;
	String smtpPort;
	String imapHost;
	String imapPort;
	double wateringDuration = 0;

	//getters and setters
	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setUserEmail(String userEmail) {
		this.ownerEmail = userEmail;
	}
	
	public String getMailAccount() {
		return mailAccount;
	}

	public String getMailPW() {
		return mailPW;
	}
	
	public String getSmtpHost() {
		return smtpHost;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public String getImapHost() {
		return imapHost;
	}

	public String getImapPort() {
		return imapPort;
	}

	public double getWateringDuration() {
		return wateringDuration;
	}

	public void setWateringDuration(double wateringDuration) {
		this.wateringDuration = wateringDuration;
	}
	
	public void readPropValues() throws IOException {
		try {
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(PROPFILENAME);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + PROPFILENAME + "' not found in the classpath");
			}
 
			// get and save property values
			ownerEmail = prop.getProperty("ownerEmail");
			mailAccount = prop.getProperty("mailAccount");
			mailPW = prop.getProperty("mailPW");
			smtpHost = prop.getProperty("smtpHost");
			smtpPort = prop.getProperty("smtpPort");
			imapHost = prop.getProperty("imapHost");
			imapPort = prop.getProperty("imapPort");
			wateringDuration = Double.parseDouble(prop.getProperty("wateringDuration"));

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}

}
