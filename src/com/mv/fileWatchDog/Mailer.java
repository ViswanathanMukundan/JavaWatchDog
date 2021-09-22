package com.mv.fileWatchDog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

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
	private int MAIL_PORT = 587;
	private String SENDER_PWD = "fmv603oc";
	Properties props = new Properties();
	Session session;
	MimeMessage message;
	
	Mailer() throws AddressException, MessagingException
	{
		this.props.put("mail.smtp.host", this.MAIL_HOST);
		this.props.put("mail.smtp.port", this.MAIL_PORT);
		this.props.put("mail.smtp.user", this.MAIL_SENDER);
		this.props.put("mail.smtp.starttls.enable", "true");
		this.props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		this.props.put("mail.smtp.auth", "true");
		
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
		
		/*Socket socket = new Socket(this.MAIL_HOST, this.MAIL_PORT);
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		OutputStream os = socket.getOutputStream();
		SMTPWrite(br, os, "HELO " + this.MAIL_RECEIVER);
		SMTPWrite(br, os, "MAIL FROM: " + this.MAIL_SENDER);
		SMTPWrite(br, os, "DATA");
		SMTPWrite(br, os, body);
		System.err.println("Mail sent");*/
	}
	
	void SMTPWrite(BufferedReader br, OutputStream os, String data) throws IOException
	{
		br.readLine();
		os.write(data.getBytes());
	}

	public static void main(String[] args) throws MessagingException, UnknownHostException, IOException
	{
		String subject = "Reg. log readings.";
		String mailBody = "Unusual activity has been recorded in the log. Kindly note and take action. Regards, admin."; 
		Mailer mailer = new Mailer();
		mailer.sendEmail(subject, mailBody);
	}

}
