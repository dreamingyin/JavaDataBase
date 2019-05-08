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
		
		String from="26232895862@qq.com";//�����������ַ
		String to="15216769172@163.com";	//�ռ��������ַ
		String subject="Html�ʼ�";
		String body="<a href=http://www.ecnu.edu.cn>"
				+"<h4>��ӭ��ҷ������Ǽҵ���վ<h4></a></br>";
		//����Sessionʵ������
		Session session=Session.getInstance(new Properties());
		//����MimeMessage����
		MimeMessage message=new MimeMessage(session);
		//���÷�����
		message.setFrom(new InternetAddress(from)); 
		//�����ռ���
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		//��������
		message.setSubject(subject);
		//���÷�������
		message.setSentDate(new Date());
		//�����ʼ���������
		message.setContent(body, "text/html;charset=gb2312");
		message.saveChanges();
		return message;
		
	}
	
}
