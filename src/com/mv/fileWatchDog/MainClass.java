package com.mv.fileWatchDog;

/*
MAIN CLASS TO MAKE USE OF THE OTHER UTILITY CLASSES.
*/

import java.io.FileNotFoundException;
import java.util.Date;
import javax.mail.MessagingException;

public class MainClass {
	
	public static void main(String[] args) throws FileNotFoundException, MessagingException
	{
		String filePath = "PATH OF THE FILE TO BE MONITORED"; //INSERT THE FULLY QUALIFIED PATH OF THE FILE INTO THIS VARIABLE
		FileWatcher watchdog = new FileWatcher(filePath);  //CALLING THE FILEWATCHER CLASS WITH THE FILEPATH AS CONSTRUCTOR ARGUMENT
		Mailer mailMan = new Mailer();
		while(true) //PUTTING THE IMPLEMENTATION UNDER AN INFINITE WHILE-LOOP FOR NOW. FURTHER IMPROVEMENTS MIGHT INCLUDE A MORE EFFICIENT IMPLEMENTATION, (MAYBE AS A BACKGROUND THREAD)
		{
			if(watchdog.isFileUpdated(filePath))
			{
				System.err.println("File updated");
				watchdog.writer.addEntry("File updated at::: " + new java.text.SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(new Date(watchdog.getTimeStamp())));
				mailMan.sendEmail("MAIL SUBJECT", "MAIL BODY"); //PASS EMAIL SUBJECT AND EMAIL BODY AS PARAMETERS. USE '\n' TO DENOTE LINE BREAKS.
			}
		}
	}

}
