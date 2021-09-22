package com.mv.fileWatchDog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

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

	
	Mailer() throws AddressException, MessagingException
	{
		Properties props = new Properties();
		props.put("mail.smtp.host", this.MAIL_HOST);
		props.put("mail.smtp.port", this.MAIL_PORT);
		props.put("mail.smtp.user", this.MAIL_SENDER);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");

		
		this.session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(MAIL_SENDER, SENDER_PWD);
			}
		});
		
		this.message = new MimeMessage(this.session);
		message.setFrom(new InternetAddress(this.MAIL_SENDER));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.MAIL_RECEIVER));
	}
	
	void sendEmail(String subject, String body) throws MessagingException, UnknownHostException, IOException
	{
		try
		{
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
	

}
