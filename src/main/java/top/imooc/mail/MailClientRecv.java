package top.imooc.mail;
import java.util.Date;
import java.util.Properties;
 
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
 
/**
 * ����������һ������ı���ͼƬ�������ĸ����ʼ���ps�������QQ�˺����붼�������ǵģ�
 * JavaMail �汾: 1.6.0
 * JDK �汾: JDK 1.7 ���ϣ����룩
 */
public class MailClientRecv {
 
    // �����˵� ���� �� ���루�滻Ϊ�Լ�����������룩
    public static String myEmailAccount = "2932895862@qq.com";
    public static String myEmailPassword = "xieyrzkvzyojdebh";//������벻��QQ��������������룬Ҫ��֤�ġ��Լ��ٶ�
 
    // ����������� SMTP ��������ַ, ����׼ȷ, ��ͬ�ʼ���������ַ��ͬ, һ���ʽΪ: smtp.xxx.com
    // qq����� SMTP ��������ַΪ: smtp.qq.com
    public static String myEmailSMTPHost = "smtp.qq.com";
 
    // �ռ������䣨�滻Ϊ�Լ�֪������Ч���䣩
    public static String receiveMailAccount = "15216769172@163.com";
 
    public static void main(String[] args) throws Exception {
        // 1. ������������, ���������ʼ��������Ĳ�������
        Properties props = new Properties();                    // ��������
        props.setProperty("mail.transport.protocol", "smtp");   // ʹ�õ�Э�飨JavaMail�淶Ҫ��
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // �����˵������ SMTP ��������ַ
        props.setProperty("mail.smtp.auth", "true");            // ��Ҫ������֤
 
        // ���� SSL ����, �Լ�����ϸ�ķ��Ͳ����뿴��һƪ: ���� JavaMail �� Java �ʼ����ͣ����ʼ�����
        //QQ����˿������������԰ٶȡ�
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
         
        // 2. �������ô����Ự����, ���ں��ʼ�����������
        Session session = Session.getInstance(props);
        // ����Ϊdebugģʽ, ���Բ鿴��ϸ�ķ��� log
        session.setDebug(true);
 
        // 3. ����һ���ʼ�
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount);
 
        // Ҳ���Ա��ֵ����ز鿴
        // message.writeTo(file_out_put_stream);
 
        // 4. ���� Session ��ȡ�ʼ��������
        Transport transport = session.getTransport();
 
        // 5. ʹ�� �����˺� �� ���� �����ʼ�������
        //    ������֤����������� message �еķ���������һ�£����򱨴�
        transport.connect(myEmailAccount, myEmailPassword);
 
        // 6. �����ʼ�, �������е��ռ���ַ, message.getAllRecipients() ��ȡ�������ڴ����ʼ�����ʱ��ӵ������ռ���, ������, ������
        transport.sendMessage(message, message.getAllRecipients());
 
        // 7. �ر�����
        transport.close();
    }
 
     
    /**����һ���ʼ��ʼ����ı�+ͼƬ+������*/
      
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        // 1. �����ʼ�����
        MimeMessage message = new MimeMessage(session);
 
        // 2. From: ������
        message.setFrom(new InternetAddress(sendMail, "�ҵĲ����ʼ�_�������ǳ�", "UTF-8"));
 
        // 3. To: �ռ��ˣ��������Ӷ���ռ��ˡ����͡����ͣ�
        message.addRecipient(RecipientType.TO, new InternetAddress(receiveMail, "�ҵĲ����ʼ�_�ռ����ǳ�", "UTF-8"));
 
        // 4. Subject: �ʼ�����
        message.setSubject("Nignx �ʼ�", "UTF-8");
 
        /*
         * �������ʼ����ݵĴ���:
         */
 
        // 5. ����ͼƬ���ڵ㡱
        MimeBodyPart image = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("E:\\SSMProject\\20190423151417.jpg")); // ��ȡ�����ļ�
        image.setDataHandler(dh);                   // ��ͼƬ������ӵ����ڵ㡱
        image.setContentID("image_fairy_tail");     // Ϊ���ڵ㡱����һ��Ψһ��ţ����ı����ڵ㡱�����ø�ID��
 
        // 6. �����ı����ڵ㡱
        MimeBodyPart text = new MimeBodyPart();
        //    �������ͼƬ�ķ�ʽ�ǽ�����ͼƬ�������ʼ�������, ʵ����Ҳ������ http ���ӵ���ʽ�������ͼƬ
        text.setContent("����һ��ͼƬ<br/><img src='cid:image_fairy_tail'/>", "text/html;charset=UTF-8");
 
        // 7. ���ı�+ͼƬ������ �ı� �� ͼƬ ���ڵ㡱�Ĺ�ϵ���� �ı� �� ͼƬ ���ڵ㡱�ϳ�һ����ϡ��ڵ㡱��
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related");    // ������ϵ
 
        // 8. �� �ı�+ͼƬ �Ļ�ϡ��ڵ㡱��װ��һ����ͨ���ڵ㡱
        //    ������ӵ��ʼ��� Content ���ɶ�� BodyPart ��ɵ� Multipart, ����������Ҫ���� BodyPart,
        //    ����� mm_text_image ���� BodyPart, ����Ҫ�� mm_text_image ��װ��һ�� BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);
 
        // 9. �����������ڵ㡱
        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource("E:\\SSMProject\\day24-Nginx.docx"));  // ��ȡ�����ļ�
        attachment.setDataHandler(dh2);                                             // ������������ӵ����ڵ㡱
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));              // ���ø������ļ�������Ҫ���룩
 
        // 10. ���ã��ı�+ͼƬ���� ���� �Ĺ�ϵ���ϳ�һ����Ļ�ϡ��ڵ㡱 / Multipart ��
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment);     // ����ж�����������Դ������������
        mm.setSubType("mixed");         // ��Ϲ�ϵ
 
        // 11. ���������ʼ��Ĺ�ϵ�������յĻ�ϡ��ڵ㡱��Ϊ�ʼ���������ӵ��ʼ�����
        message.setContent(mm);
 
        // 12. ���÷���ʱ��
        message.setSentDate(new Date());
 
        // 13. �����������������
        message.saveChanges();
 
        return message;
    }
 
}