package top.imooc.message;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class HtmlMessage {

	public static MimeMessage generate() throws AddressException, MessagingException {
		
		String from="26232895862@qq.com";//发件人邮箱地址
		String to="15216769172@163.com";	//收件人邮箱地址
		String subject="Html邮件";
		String body="<a href=http://www.ecnu.edu.cn>"
				+"<h4>欢迎大家访问我们家的网站<h4></a></br>";
		//创建Session实例对象
		Session session=Session.getInstance(new Properties());
		//创建MimeMessage对象
		MimeMessage message=new MimeMessage(session);
		//设置发件人
		message.setFrom(new InternetAddress(from)); 
		//设置收件人
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		//设置主题
		message.setSubject(subject);
		//设置发送日期
		message.setSentDate(new Date());
		//设置邮件正文内容
		message.setContent(body, "text/html;charset=gb2312");
		message.saveChanges();
		return message;
		
	}
	
}
