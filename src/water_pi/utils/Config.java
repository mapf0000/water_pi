package water_pi.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class Config {
	InputStream inputStream;
	
	//config variables
	String userEmail = "";
	double wateringDuration = 0;

	//getters and setters
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
			String propFileName = "config.properties";
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
			String userEmail = prop.getProperty("userEmail");
			String wateringDuration = prop.getProperty("wateringDuration");
 
			//result = "Company List = " + company1 + ", " + company2 + ", " + company3;
			//System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}

}
