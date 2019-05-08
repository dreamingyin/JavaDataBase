package top.imooc.message;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class AttachmentMessage {

	public static MimeMessage generate() throws AddressException, MessagingException, FileNotFoundException, IOException {
		
		String from="2932895862@qq.com";//发件人邮箱地址
		String to="15216769172@163.com";	//收件人邮箱地址
		String subject="多附件邮件";
		String body="<a href=http://www.ecnu.edu.cn>"
				+"<h4>欢迎大家访问我们家的网站<h4></a></br>";
		
		//创建Session实例对象
		Session session=Session.getInstance(new Properties());
		//MimeMessage对象
		MimeMessage message=new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);
		message.setContent(body, "text/html;charset=gb2312");
		
		//创建各个代表邮件附件的正文和附件MimeBodyPart对象
		MimeBodyPart contentPart=createContent(body);
		MimeBodyPart attachPart1=createAttachment("E:\\SSMProject\\20190423151417.jpg");
		MimeBodyPart attachPart2=createAttachment("E:\\SSMProject\\redis.ppt");
		
		//创建用于组合邮件正文和附件的MimeMu
		MimeMultipart allMultipart=new MimeMultipart("mixed");
		allMultipart.addBodyPart(contentPart);
		allMultipart.addBodyPart(attachPart1);
		allMultipart.addBodyPart(attachPart2);
		
		//设置整个邮件最终组合为allMultipart
		message.setContent(allMultipart);
		message.writeTo(new FileOutputStream("E:\\SSMProject\\attachment.docx"));
		message.saveChanges();
		return message;
		
	}
	
	public static MimeBodyPart createContent(String body) throws MessagingException {
		
		MimeBodyPart htmlBodyPart=new MimeBodyPart();
		htmlBodyPart.setContent(body, "text/html;charset=gb2312");
		return htmlBodyPart;	
	}
	
	public static MimeBodyPart createAttachment(String filename) throws MessagingException {
		
		//创建并保存福建的MimeBodyPart对象，并加入附件内容和相应的信息
		MimeBodyPart attachPart=new MimeBodyPart();
		FileDataSource fds=new FileDataSource(filename);
		attachPart.setDataHandler(new DataHandler(fds));
		attachPart.setFileName(fds.getName());
		return attachPart;
	}
}
