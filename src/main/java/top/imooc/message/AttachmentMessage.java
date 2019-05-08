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
		
		String from="2932895862@qq.com";//�����������ַ
		String to="15216769172@163.com";	//�ռ��������ַ
		String subject="�฽���ʼ�";
		String body="<a href=http://www.ecnu.edu.cn>"
				+"<h4>��ӭ��ҷ������Ǽҵ���վ<h4></a></br>";
		
		//����Sessionʵ������
		Session session=Session.getInstance(new Properties());
		//MimeMessage����
		MimeMessage message=new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);
		message.setContent(body, "text/html;charset=gb2312");
		
		//�������������ʼ����������ĺ͸���MimeBodyPart����
		MimeBodyPart contentPart=createContent(body);
		MimeBodyPart attachPart1=createAttachment("E:\\SSMProject\\20190423151417.jpg");
		MimeBodyPart attachPart2=createAttachment("E:\\SSMProject\\redis.ppt");
		
		//������������ʼ����ĺ͸�����MimeMu
		MimeMultipart allMultipart=new MimeMultipart("mixed");
		allMultipart.addBodyPart(contentPart);
		allMultipart.addBodyPart(attachPart1);
		allMultipart.addBodyPart(attachPart2);
		
		//���������ʼ��������ΪallMultipart
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
		
		//���������渣����MimeBodyPart���󣬲����븽�����ݺ���Ӧ����Ϣ
		MimeBodyPart attachPart=new MimeBodyPart();
		FileDataSource fds=new FileDataSource(filename);
		attachPart.setDataHandler(new DataHandler(fds));
		attachPart.setFileName(fds.getName());
		return attachPart;
	}
}
