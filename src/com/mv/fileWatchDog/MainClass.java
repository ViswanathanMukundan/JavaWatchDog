package com.mv.fileWatchDog;

import java.io.FileNotFoundException;
import java.util.Date;

import javax.mail.MessagingException;

public class MainClass {
	
	public static void main(String[] args) throws FileNotFoundException, MessagingException
	{
		String filePath = "PATH OF THE FILE TO BE MONITORED";
		FileWatcher watchdog = new FileWatcher(filePath);
		Mailer mailMan = new Mailer();
		while(true)
		{
			if(watchdog.isFileUpdated(filePath))
			{
				System.err.println("File updated");
				watchdog.writer.addEntry("File updated at::: " + new java.text.SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(new Date(watchdog.getTimeStamp())));
				//mailMan.sendEmail("Reg. log readings.", "Unusual activity has been recorded in the log. Time of recording::: " + new java.text.SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(new Date(watchdog.getTimeStamp())) + ". Kindly note and take action. Regards, admin.");
				mailMan.sendEmail("MAIL SUBJECT", "MAIL BODY");
			}
		}
	}

}
