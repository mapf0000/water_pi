package water_pi;

import java.io.IOException;

import water_pi.utils.Config;

public class Core {
	public static void main(String[] args) throws InterruptedException, IOException {
		Config myConfig = new Config();
		myConfig.readPropValues();
		
		//enter main loop
		while(true){
			//check mail
			System.out.println("reading mail");
			Mail.readGmail();
			//execute commands
			
			//waiting interval
			Thread.sleep(60000); //1000msec = 1 sec
		}
	}
}
