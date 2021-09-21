package com.mv.fileWatchDog;

/*
 * E-MAILMAN
 * */

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Mailer 
{
	private String MAIL_SENDER = "viswanathan3141@gmail.com";
	private String MAIL_RECEIVER = "viswanathanmukundan@gmail.com";
	private String MAIL_HOST = "smtp.gmail.com";
	private int MAIL_PORT = 25;
	private String SENDER_PWD = "fmv603oc";
	
	void sendEmail(String subject, String body) throws MessagingException
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", this.MAIL_HOST);
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.user", this.MAIL_SENDER);
		//props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(MAIL_SENDER, SENDER_PWD);
			}
		});
		//Session session = Session.getInstance(props);
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
	
	public static void main(String[] args) throws MessagingException
	{
		String subject = "Reg. log readings.";
		String mailBody = "Unusual activity has been recorded in the log. Kindly note and take action. Regards, admin."; 
		Mailer mailer = new Mailer();
		mailer.sendEmail(subject, mailBody);
	}

}
