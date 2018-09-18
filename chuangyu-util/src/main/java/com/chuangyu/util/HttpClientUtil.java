package com.chuangyu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;


/**
 *  httpClient的工具类，用于接口调用
 *   
 * @author: rqwang     
 * @date: 2018年6月20日 上午11:34:33   
 *
 */
public class HttpClientUtil {

	/**
	 * 发送post请求
	 * 
	 * @param url
	 * @param params
	 *            没有参数则传入null
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> params) throws IOException {
		// 创建http客户对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 定义一个访问url后返回的结果对象
		CloseableHttpResponse response = null;
		// 创建HttpGet对象，如不携带参数可以直接传入url创建对象
		HttpPost post = new HttpPost(url);
		// 从结果对象中获取的内容
		String content = null;

		// 设置请求头，为浏览器访问
		post.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36");

		// 设置表单项，对于的是一个添加方法，添加需要的属性
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
		}

		// 设置表单项
		post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

		try {
			// 访问这个url，并携带参数，获取结果对象
			response = client.execute(post);

			// 从结果对象中获取返回的内容
			content = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			if (response != null) {
				response.close();
			}
			client.close();
		}
		return content;
	}

	/**
	 * 发送post请求,最后转换参数为json格式
	 * 
	 * @param url
	 * @param params
	 *            没有参数则传入null
	 * @return
	 * @throws IOException
	 */
	public static String jsonPost(String url, Map<String, Object> params) throws IOException {
		// 创建http客户对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 定义一个访问url后返回的结果对象
		CloseableHttpResponse response = null;
		// 创建HttpGet对象，如不携带参数可以直接传入url创建对象
		HttpPost post = new HttpPost(url);
		// 从结果对象中获取的内容
		String content = null;

		// 设置请求头，为浏览器访问
		post.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36");

		// 设置表单项，对于的是一个添加方法，添加需要的属性
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		// 设置表单项
		post.setEntity(new StringEntity(JSONObject.toJSON(params).toString(), "UTF-8"));
		// post.setEntity(new StringEntity("{\"action_name\":
		// \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\":
		// 123}}","UTF-8"));

		try {
			// 访问这个url，并携带参数，获取结果对象
			response = client.execute(post);

			// 从结果对象中获取返回的内容
			content = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			if (response != null) {
				response.close();
			}
			client.close();
		}
		return content;
	}

	/**
	 * get方式调用接口
	 * 
	 * @param url
	 * @param params
	 *            没有参数则传入null
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static String get(String url, Map<String, String> params) throws URISyntaxException, IOException {
		// 创建http客户对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 定义一个访问url后返回的结果对象
		CloseableHttpResponse response = null;
		// 从结果对象中获取的内容
		String content = null;

		// GET方法如果要携带参数先创建URIBuilder对象，然后设置参数，如果不携带可以忽略这步骤
		URIBuilder builder = new URIBuilder(url);
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				builder.setParameter(key, params.get(key));
			}
		}

		// 创建HttpGet对象，如不携带参数可以直接传入url创建对象
		HttpGet get = new HttpGet(builder.build());

		try {
			// 访问这个url，并携带参数，获取结果对象
			response = client.execute(get);
			// 从结果对象中获取返回的内容
			content = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();

			// 关闭连接
		} finally {
			if (response != null) {
				response.close();
			}
			client.close();
		}
		return content;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, Map<String, String> param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 设置相应请求时间
			conn.setConnectTimeout(30000);
			// 设置读取超时时间
			conn.setReadTimeout(30000);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			if (param != null && param.size() > 0) {
				String paramStr = "";
				for (String key : param.keySet()) {
					paramStr += "&" + key + "=" + param.get(key);
				}
				paramStr = paramStr.substring(1);
				out.print(paramStr);
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println(e);
			return "发送 POST 请求出现异常！";
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static InputStream getFile(String url) throws IOException {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		URL realUrl = new URL(url);
		// 打开和URL之间的连接
		URLConnection conn = realUrl.openConnection();
		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		// 设置相应请求时间
		conn.setConnectTimeout(30000);
		// 设置读取超时时间
		conn.setReadTimeout(30000);
		// 获取URLConnection对象对应的输出流
		out = new PrintWriter(conn.getOutputStream());
		// flush输出流的缓冲
		out.flush();
		// 定义BufferedReader输入流来读取URL的响应
		return conn.getInputStream();
	}
}
