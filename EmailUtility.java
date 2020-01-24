package com.psd2.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Class Description - This class provides mechanism to mail the test report.
 * 
 * @author alok_kumar
 * @version 1.0
 * 
 */
public class EmailUtility {
	
	static boolean connectStatus = false;
	
	public static boolean sendMail(String from, String[] to, String password, String attachment) throws InterruptedException{
		boolean result = false;
		Session session;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try{
			session = emailHandle(from,password);
			for(int i=0; i<3; i++){
				if(connectStatus){

					// Create a default MimeMessage object.
					MimeMessage message = new MimeMessage(session);

					// Set From: header field of the header.
					message.setFrom(new InternetAddress(from));
					
					InternetAddress[] recipientAddress = new InternetAddress[to.length];
					int counter = 0;
					for(String recipient : to ){ 
						recipientAddress[counter] = new InternetAddress(recipient.trim());
						counter++;
					}					
					// Set To: header field of the header.
					message.setRecipients(Message.RecipientType.TO, recipientAddress);
					
					// Set Subject: header field
					message.setSubject("Test Execution Report - " +dateFormat.format(date));

					// Create the message part 
					BodyPart messageBodyPart = new MimeBodyPart();

					// Fill the message
					messageBodyPart.setText("This is message body");
		         
					// Create a multipar message
					Multipart multipart = new MimeMultipart();

					// Set text message part
					multipart.addBodyPart(messageBodyPart);

					// Part two is attachment
					messageBodyPart = new MimeBodyPart();
					String filename = attachment;
					DataSource source = new FileDataSource(filename);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(filename);
					multipart.addBodyPart(messageBodyPart);

					// Send the complete message parts
		        	 message.setContent(multipart );

		        	 // Send message
		        	 Transport.send(message);

		        	 System.out.println("Sent message successfully....");
		         
		        	return true;		        	
				}
				else{
					System.out.println("Trying to reconnect....");
					Thread.sleep(10000);
					session = emailHandle(from,password);
				}
			}
		}catch (MessagingException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}
	
	/**
	 * This method returns handle of the Store.
	 * 
	 */
	private static Session emailHandle(final String user, final String password){
		
		Session session = null;
		try{
			System.out.println("Connecting to the host...");
			
			String host = "smtp.office365.com";

			// Get system properties
			Properties properties = System.getProperties();
	    
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host",host);  
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.user", user);
			properties.put("mail.smtp.password", password);
			
			Authenticator authenticator = new Authenticator () {
		          public PasswordAuthentication getPasswordAuthentication(){
		              return new PasswordAuthentication(user,password);//userid and password for "from" email address 
		          }
			};
			session = Session.getDefaultInstance(properties,authenticator);
			System.out.println("Host is connected successfully");
			connectStatus = true;
		}		
		catch (Exception e)	{
			System.err.println(e.getMessage());
		}
		return session;
	}
		
	
	public static void main(String args[]) throws InterruptedException{
		 String[] to = {"alok2089@gmail.com"};

	      // Sender's email ID needs to be mentioned
	      final String from = "alok.c.kumar@capgemini.com";
	      String pass = "";
	      String attach = "";
	      
		boolean bool = sendMail(from, to, pass, attach );
		System.out.println(bool);
		
	}

}
