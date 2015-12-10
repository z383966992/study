//package utils;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//import net.sf.json.JSONObject;
//
//import org.apache.commons.httpclient.HostConfiguration;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpMethodBase;
//import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.cookie.CookiePolicy;
//import org.apache.commons.httpclient.methods.GetMethod;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class HttpClientUtil {
//
//
//	private static transient final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
//
//	private static MultiThreadedHttpConnectionManager httpClientManager = new MultiThreadedHttpConnectionManager();
//
//	static {
//		httpClientManager = new MultiThreadedHttpConnectionManager();
//		HttpConnectionManagerParams params = httpClientManager.getParams();
//		params.setStaleCheckingEnabled(true);
//		params.setMaxTotalConnections(200);
//		params.setDefaultMaxConnectionsPerHost(80);
//		params.setConnectionTimeout(10000);
//		params.setSoTimeout(13000);		
//	}
//
//	public static HttpClient getHttpClient() {
//		return new HttpClient(httpClientManager);
//	}
//
//	public static boolean booleanGet(String url) {
//		GetMethod method = null;
//		try {
//			HostConfiguration config = new HostConfiguration();
//			URL _url = new URL(url);
//			int port = _url.getPort() > 0 ? _url.getPort() : 80;
//			config.setHost(_url.getHost(), port, _url.getProtocol());
//			method = new GetMethod(url);
//			getHttpClient().executeMethod(config, method);
//			int statusCode = method.getStatusCode();
//			if (statusCode == 200) {
//				return true;
//			}
//		} catch (Exception exp) {
//			log.error("error:" + exp.getMessage());
//			exp.printStackTrace();
//		} finally {
//			if (method != null) {
//				method.releaseConnection();
//			}
//		}
//		return false;
//	}
//
//	public static String get(String url) {
//		GetMethod method = null; 
//		try {
//			HostConfiguration config = new HostConfiguration();
//			URL _url = new URL(url);
//			int port = _url.getPort() > 0 ? _url.getPort() : 80;
//			config.setHost(_url.getHost(), port, _url.getProtocol());
//			method = new GetMethod(url);
//			getHttpClient().executeMethod(config, method);
//			int statusCode = method.getStatusCode();
//			if (statusCode != 200) {
//				log.error("statusCode:" + statusCode + ", URL:" + url);
//			}
//			return getResponseAsString(method);
//		} catch (Exception exp) {
//			log.error("error:" + exp.getMessage() +", URL:" + url);
//			exp.printStackTrace();
//		} finally {
//			if (method != null) {
//				method.releaseConnection();
//			}
//		}
//		return null;
//	}
//
//	public static String post(String url, Map<String, String> params, Map<String, String> headers) {
//		if (url == null || url == null) {
//			return null;
//		}
//		PostMethod method = new PostMethod(url);
//		method.getParams().setParameter("http.protocol.cookie-policy",
//				CookiePolicy.BROWSER_COMPATIBILITY);
//		try {
//			NameValuePair[] paramPair = new NameValuePair[params.size()];
//			int i = 0;
//			for (String k : params.keySet()) {
//				NameValuePair nvp = new NameValuePair();
//				nvp.setName(k);
//				nvp.setValue(params.get(k));
//				paramPair[i] = nvp;
//				i++;
//			}
//
//			URL _url = new URL(url);
//			HostConfiguration config = new HostConfiguration();
//			int port = _url.getPort() > 0 ? _url.getPort() : 80;
//			config.setHost(_url.getHost(), port, _url.getProtocol());
//
//						
//			if(headers != null){
//				for (String k : headers.keySet()) {		
//					method.setRequestHeader(k, headers.get(k)); 
//				}
//			}		
//			
//			if (paramPair != null && paramPair.length > 0) {
//				method.setRequestBody(paramPair);
//			}
//
//			int result = getHttpClient().executeMethod(config, method);
//
//			log.debug("HttpClient.executeMethod returns result = [{}]", result);
//
//			if (result != 200) {
//				log.error("wrong HttpClient.executeMethod post method !" +", URL:" + url);
//			}
//
//			return getResponseAsString(method);
//		} catch (Exception exp) {
//			exp.printStackTrace();
//			log.error("error:" + exp.getMessage() +", URL:" + url);
//		}
//		finally {
//			if (method != null) {
//				method.releaseConnection();
//			}
//		}
//		return null;
//	}
//
//	public static String getResponseAsString(HttpMethodBase method) {
//
//		StringBuilder sb = new StringBuilder();
//		BufferedReader in;
//		try {
//			InputStream is = method.getResponseBodyAsStream();
//			in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//			String line = null;
//			while ((line = in.readLine()) != null) {
//				sb.append(line);
//				sb.append("\n");
//			}
//			if(sb.length() > 0){
//				return sb.substring(0, sb.length()-1);
//			}			
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			log.error("error:" + e.getMessage() + ", Query String:" +method.getQueryString());
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//			log.error("error:" + ioe.getMessage() + ", Query String:" +method.getQueryString());
//		}
//		return null;
//	}
//}
