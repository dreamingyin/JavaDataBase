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
		
		String from="26232895862@qq.com";//�����������ַ
		String to="15216769172@163.com";	//�ռ��������ַ
		
		String subject="TextMessage";//�ʼ�����
		String body="��������һ������ʼ�";
		
		//����session����
		Session session=Session.getInstance(new Properties());
		//����MimeMessage
		MimeMessage message=new MimeMessage(session);
		//���÷�����
		message.setFrom(new InternetAddress(from));
		//����������
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		
		//���÷�������
		message.setSentDate(new Date());
		//�����ʼ�����
		message.setSubject(subject);
		//�����ʼ�����
		message.setText(body);
		//������Ϣ�������ʼ�
		message.saveChanges();
		
		//���԰��ʼ�Ҳ�������ļ���
		message.writeTo(new FileOutputStream("E:\\SSMProject\\message.docx"));
		return message;
	}
}
