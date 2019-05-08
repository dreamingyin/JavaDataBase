package top.imooc.message;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TextMessage {

	public static MimeMessage generate() throws AddressException, MessagingException, FileNotFoundException, IOException {
		
		String from="26232895862@qq.com";//发件人邮箱地址
		String to="15216769172@163.com";	//收件人邮箱地址
		
		String subject="TextMessage";//邮件主题
		String body="您好这是一封测试邮件";
		
		//创建session对象
		Session session=Session.getInstance(new Properties());
		//创建MimeMessage
		MimeMessage message=new MimeMessage(session);
		//设置发信人
		message.setFrom(new InternetAddress(from));
		//设置收信人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		
		//设置发送日期
		message.setSentDate(new Date());
		//设置邮件主题
		message.setSubject(subject);
		//设置邮件正文
		message.setText(body);
		//保存信息并生成邮件
		message.saveChanges();
		
		//可以把邮件也到本地文件中
		message.writeTo(new FileOutputStream("E:\\SSMProject\\message.docx"));
		return message;
	}
}
