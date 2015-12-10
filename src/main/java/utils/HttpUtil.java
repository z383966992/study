package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	private static final int SIZE = 1024 * 1024;// 字符缓冲大小
	private static transient final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	private static MultiThreadedHttpConnectionManager httpClientManager = new MultiThreadedHttpConnectionManager();

	static {
		httpClientManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = httpClientManager.getParams();
		params.setStaleCheckingEnabled(true);
		params.setMaxTotalConnections(2000);
		params.setDefaultMaxConnectionsPerHost(80);
		params.setConnectionTimeout(200000);
		params.setSoTimeout(180000);
	}

	public static HttpClient getHttpClient() {
		return new HttpClient(httpClientManager);
	}

	/**
	 * 用get方式请求url。取回的结果按行保存为list的元素
	 * 
	 * @param strUrl
	 * @return
	 * @throws IOException
	 */
	public static List<String> URLGet(String strUrl) throws IOException {
		List<String> result = new ArrayList();
		URL url = new URL(strUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setFollowRedirects(true);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()), SIZE);
		while (true) {
			String line = in.readLine();
			if (line == null) {
				break;
			} else {
				result.add(line);
			}
		}
		in.close();
		return (result);
	}

	/**
	 * post方法请求url。返回结果同get请求方式。返回list是因为有个支付接口是以换行符分割的，这样好处理。
	 * 
	 * @param strUrl
	 *            String
	 * @param content
	 *            String post的参数内容
	 * @throws IOException
	 * @return List
	 */
	public static List<String> URLPost(String strUrl, String content, String charset) throws IOException {
		URL url = new URL(strUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setAllowUserInteraction(false);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
		BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		bout.write(content);
		bout.flush();
		bout.close();
		BufferedReader bin = new BufferedReader(new InputStreamReader(con.getInputStream()), SIZE);
		List<String> result = new ArrayList();
		while (true) {
			String line = bin.readLine();
			if (line == null) {
				break;
			} else {
				result.add(line);
			}
		}
		con = null;
		return (result);
	}

	// 暂时不用.这个可以实现传递cookie和接收要设置的cookie的值
	public static List<String> connectApi(String apiUrl, String content, String cookie) throws IOException {
		List<String> ret = new ArrayList();
		StringBuffer result = new StringBuffer();
		URL url = new URL(apiUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setAllowUserInteraction(false);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Cookie", cookie);
		con.setRequestProperty("Accept-Language", "zh-cn");
		con.setRequestProperty("Connection", "close");
		con.setRequestProperty("Content-Length", Integer.toString(content.length()));
		// con.setConnectTimeout(5); //设置1s超时。 设置超时后经常访问出错，暂时取消
		BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		bout.write(content);
		bout.flush();
		bout.close();
		BufferedReader bin = new BufferedReader(new InputStreamReader(con.getInputStream()), SIZE);
		String line;
		while ((line = bin.readLine()) != null) {
			result.append(line);

		}

		ret.add(result.toString());
		Map<String, List<String>> map = con.getHeaderFields();
		List<String> setCookie = map.get("Set-Cookie");
		if (setCookie != null) {
			ret.addAll(setCookie);
		}
		System.out.println(ret.toString());
		return ret;
	}

	public static String post(String apiUrl, String content, String cookie) throws IOException {
		StringBuffer result = new StringBuffer();
		URL url = new URL(apiUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(10000);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setAllowUserInteraction(false);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Cookie", cookie);
		con.setRequestProperty("Content-Length", Integer.toString(content.length()));
		BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
		bout.write(content);
		bout.flush();
		bout.close();
		BufferedReader bin = new BufferedReader(new InputStreamReader(con.getInputStream()), SIZE);
		String line;
		while ((line = bin.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}

	public static String post(String apiUrl, String content) throws IOException {
		return post(apiUrl, content, "");
	}

	public static String post(String url, Map<String, String> params, Map<String, String> headers) {
		if (url == null || url == null) {
			return null;
		}
		try {
			NameValuePair[] paramPair = new NameValuePair[params.size()];
			int i = 0;
			for (String k : params.keySet()) {
				NameValuePair nvp = new NameValuePair();
				nvp.setName(k);
				nvp.setValue(params.get(k));
				paramPair[i] = nvp;
				i++;
			}

			URL _url = new URL(url);
			HostConfiguration config = new HostConfiguration();
			int port = _url.getPort() > 0 ? _url.getPort() : 80;
			config.setHost(_url.getHost(), port, _url.getProtocol());

			PostMethod post = new PostMethod(url);
			if (headers != null) {
				for (String k : headers.keySet()) {
					post.setRequestHeader(k, headers.get(k));
				}
			}

			if (paramPair != null && paramPair.length > 0) {
				post.setRequestBody(paramPair);
			}

			int result = getHttpClient().executeMethod(config, post);

			log.debug("HttpClient.executeMethod returns result = [{}]", result);

			if (result != 200) {
				log.error("wrong HttpClient.executeMethod post method !");
			}

			return getResponseAsString(post);
		} catch (Exception exp) {
			exp.printStackTrace();
			log.error("error:" + exp.getMessage());
		}
		return null;
	}

	public static String getResponseAsString(HttpMethodBase method) {

		StringBuilder sb = new StringBuilder();
		BufferedReader in;
		try {
			InputStream is = method.getResponseBodyAsStream();
			in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("error:" + e.getMessage());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			log.error("error:" + ioe.getMessage());
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		HttpUtil util = new HttpUtil();
		System.out.println(util.URLGet("http://www.baidu.com"));
		
	}

}
