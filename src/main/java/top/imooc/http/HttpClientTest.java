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
				.setConnectTimeout(5000) //设置连接超时
				.setConnectionRequestTimeout(8000) //连接请求时间
				.setSocketTimeout(5000)
				.setRedirectsEnabled(true) //允许重定向
				.build();
		HttpGet httpGet=new HttpGet("http://www.baidu.com");
		httpGet.setConfig(requestConfig);
		String strResult="";
		try {
			HttpResponse httpResponse=httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode()==200) {
				strResult=EntityUtils.toString(httpResponse.getEntity(),"utf-8"); //获得返回结果
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
