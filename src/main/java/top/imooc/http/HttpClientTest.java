package top.imooc.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {

	public static void main(String[] args) {
		CloseableHttpClient httpClient=HttpClients.createDefault();
		RequestConfig requestConfig=RequestConfig.custom()
				.setConnectTimeout(5000) //�������ӳ�ʱ
				.setConnectionRequestTimeout(8000) //��������ʱ��
				.setSocketTimeout(5000)
				.setRedirectsEnabled(true) //�����ض���
				.build();
		HttpGet httpGet=new HttpGet("http://www.baidu.com");
		httpGet.setConfig(requestConfig);
		String strResult="";
		try {
			HttpResponse httpResponse=httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode()==200) {
				strResult=EntityUtils.toString(httpResponse.getEntity(),"utf-8"); //��÷��ؽ��
				System.out.println(strResult);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generssated catch block
				e.printStackTrace();
			}
		}
	}
}
