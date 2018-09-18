package com.chuangyu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * http请求
 * @author: lsjie     
 * @date: 2018年6月20日 上午11:33:25   
 *
 */
public class HttpRequest {
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
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
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
    /**
     * 递归解析districts提取出省市区对应的数据(districts为空就会停止)
     * @param str
     * @return
     */
    public static void getDistricts(String str){
    	JSONArray result = JSONArray.fromObject(str);
    	for (int i = 0; i < result.size(); i++) {
    		//提取出adcode
            String adcode = result.getJSONObject(i).getString("adcode");
            System.out.println("adcode:" + adcode);
            //提取出center
            String center = result.getJSONObject(i).getString("center");
            System.out.println("center:" + center);
            //提取出citycode
            String citycode = result.getJSONObject(i).getString("citycode");
            System.out.println("citycode:" + citycode);
            //提取出level
            String level = result.getJSONObject(i).getString("level");
            System.out.println("level:" + level);
    		//提取出name
            String name = result.getJSONObject(i).getString("name");
            System.out.println("name:" + name);
            System.out.println("-------------------------------------------------");
            //提取出districts判断是否继续解析
            String districts = result.getJSONObject(i).getString("districts");
            if(!"[]".equals(districts)){
            	getDistricts(districts);
            }
    	}
    }
    
    public static void main(String[] args) {
    	//http://lbs.amap.com/api/webservice/guide/api/district/?
    	//高德地图key
    	String key = "0e4d047baf36ac1d9e9565102506ed30";
    	
    	/**
    	 * 规则：只支持单个关键词语搜索关键词支持：行政区名称、citycode、adcode  例如，在subdistrict=2，搜索省份（例如山东），能够显示市（例如济南），区（例如历下区）
    	 */
    	String keywords = "中华人民共和国";
    	 
    	/**规则：设置显示下级行政区级数（行政区级别包括：国家、省/直辖市、市、区/县4个级别）
		 *可选值：0、1、2、3
		 *0：不返回下级行政区；
		 *1：返回下一级行政区；
		 *2：返回下两级行政区；
		 *3：返回下三级行政区；
		 */
    	String subdistrict = "3";
    	
        //发送 GET 请求
        String s=HttpRequest.sendGet("http://restapi.amap.com/v3/config/district", "key="+key+"&keywords="+keywords+"&subdistrict="+subdistrict+"&extensions=base");
        
        //将str转换成JSONObject  
        JSONObject jsonObject = JSONObject.fromObject(s);  
    	System.out.println(jsonObject);  
    	//提取出status 值为0或1，0表示失败；1表示成功
        String status = jsonObject.getString("status");
        String fStatus = "1";
        if(fStatus.equals(status)){
        	getDistricts(jsonObject.getString("districts"));
        }
        
        //发送 POST 请求
//        String sr=HttpRequest.sendPost("http://localhost:6144/Home/RequestPostString", "key=123&v=456");
//        System.out.println(sr);
    }
}
