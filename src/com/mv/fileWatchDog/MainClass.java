package com.mv.fileWatchDog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;

import javax.mail.MessagingException;

public class MainClass {
	
	public static void main(String[] args) throws MessagingException, UnknownHostException, IOException
	{
		String filePath = "C:\\Users\\Vishy\\desktop\\javaSample.txt";
		FileWatcher watchdog = new FileWatcher("C:\\Users\\Vishy\\desktop\\javaSample.txt");
		Mailer mailMan = new Mailer();
		while(true)
		{
			if(watchdog.isFileUpdated(filePath))
			{
				System.err.println("File updated");
				watchdog.writer.addEntry("File updated at::: " + new java.text.SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(new Date(watchdog.getTimeStamp())));
				mailMan.sendEmail("Reg. log readings.", "Unusual activity has been recorded in the log.\nTime of recording::: " + new java.text.SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(new Date(watchdog.getTimeStamp())) + ".\n Kindly note and take action.\n Regards, admin.");
			}
		}
	}

}
