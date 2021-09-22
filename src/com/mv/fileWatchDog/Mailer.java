package com.mv.fileWatchDog;

/*
 * CLASS TO SEND A MAIL TO SPECIFIC RECIPIENTS. THIS CLASS MAKES USE OF THE JAVAMAIL API.
 THIS CLASS REQUIRES ADDITIONAL JAR FILES, javax.mail.jar, smtp.jar, activation.jar
 TO-DO: 
  -> ADD MULTIPLE RECEIVERS.
  -> WRAP WITHIN THREAD
 * */

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Mailer 
{
	private String MAIL_SENDER = "SENDER'S EMAIL ID HERE";  // ADD THE SENDER'S EMAIL ID TO THIS VARIABLE
	private String MAIL_RECEIVER = "RECEIVER'S EMAIL ID HERE"; // ADD THE RECEIVER'S EMAIL ID TO THIS VARIABLE
	private String MAIL_HOST = "smtp.gmail.com"; //ADD ANY SMTP HOST IF CONFIGURED
	private int MAIL_PORT = 587; //ADD THE PORT, DEPENDING ON THE PROTOCOL/SERVER USED
	private String SENDER_PWD = "SENDER'S EMAIL PASSWORD";  //SENDER'S EMAIL PASSWORD, REQUIRED FOR AUTHENTICATION
	
	void sendEmail(String subject, String body) throws MessagingException
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", this.MAIL_HOST);
		props.put("mail.smtp.port", this.MAIL_PORT);
		props.put("mail.smtp.user", this.MAIL_SENDER);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(MAIL_SENDER, SENDER_PWD);
			}
		});
		//session.setDebug(true);
		try
		{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.MAIL_SENDER));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.MAIL_RECEIVER));
			message.setSubject(subject);
			message.setText(body);
			
			Transport.send(message);
			System.err.println("Sent");
		}
		catch(MessagingException e)
		{
			e.printStackTrace();
		}
	}
	
	/*public static void main(String[] args) throws MessagingException	//TEST 
	{
		String subject = "Reg. log readings.";  //SUBJECT OF THE EMAIL. CAN BE ANYTHING
		String mailBody = "Unusual activity has been recorded in the log. Kindly note and take action. Regards, admin."; //BODY OF THE EMAIL.
		Mailer mailer = new Mailer();
		mailer.sendEmail(subject, mailBody);
	}*/

}
