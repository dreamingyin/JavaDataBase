package top.imooc.mail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import top.imooc.message.AttachmentMessage;


public class MailClientSend {

	private Session session;
	private Transport transport;
	private String username="2932895862@qq.com";
	private String password="xieyrzkvzyojdebh";
	private String smtpServer="smtp.qq.com";
	
	public void init() throws NoSuchProviderException {
		
		//设置属性
		Properties props=new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.class", "com.sun.mail.stmp,SMTPtransport");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.host", smtpServer);
		final String smtpPort = "465";
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", "true");//SMTP服务器需要核实身份
		
		//创建session对象
		session=Session.getDefaultInstance(props,new Authenticator() {
			
			public PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(username, password);	
			}
		});
		
		session.setDebug(true); //输出跟踪日志
		
		//创建transport对象
		transport=session.getTransport();
	}
	
	
	public void sendMessage() throws MessagingException, FileNotFoundException, IOException {
		//普通文本邮件
		//Message msg=TextMessage.generate();
		//打印Html邮件
		//Message msg=HtmlMessage.generate();
		//添加多附件邮件
		Message msg=AttachmentMessage.generate();
		transport.connect();
		transport.sendMessage(msg, msg.getAllRecipients());
		System.out.println("打印邮件成功发送");
	}
	
	public void close() throws MessagingException {
		transport.close();
	}
	
	public static void main(String[] args) throws Exception {
		
		MailClientSend send=new MailClientSend();
		send.init();
		send.sendMessage();
		send.close();
	}
	
}
