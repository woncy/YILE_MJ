package game.boss.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


/**
    * @ClassName: WXPayUtil
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
    * @date 2017年6月12日
    *
    */
public class WXPayUtil {
	public static String getNonceStr(){
		return UUID.randomUUID().toString().substring(0,32).replace("-", "0");
	}
	/**
	 * 
	    * @Title: getTrade_no
	    * @Description: 按日期生成随机订单号
	    * @param @return    订单号
	    * @return String    返回类型
	    * @throws
	 */
	public static String getTrade_no(){
		String res = "";
		long nowTime = System.currentTimeMillis();
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(nowTime);
		String time = format.format(date);
		
		UUID uuid = UUID.randomUUID();
		res+=time;
		res+=uuid;
		res = res.substring(0,32);
		res = res.replace("-", String.valueOf(new Random().nextInt(10)));
		return res;
	}
	/**
	 * 
	    * @Title: getSign
	    * @Description: 生成签名
	    * @param @param buffer
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public final static String getSign(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 
	    * @Title: testPost
	    * @Description: 获取微信的统一订单
	    * @param @param urlStr
	    * @param @param xmlInfo
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String httpPost(String url, String entity) {
		 try {  
	        	String result = "";
	            URL url2 = new URL(url);  
	            URLConnection con = url2.openConnection();  
	           
	            con.setDoOutput(true);  
//	            con.setRequestProperty("Pragma:", "no-cache");  
	            con.setRequestProperty("Cache-Control", "no-cache");  
	            con.setRequestProperty("Content-Type", "text/xml");  
	  
	            OutputStreamWriter out = new OutputStreamWriter(con  
	                    .getOutputStream());      
	            out.write(new String(entity.getBytes("UTF-8")));  
	            out.flush();  
	            out.close();  
	            BufferedReader br = new BufferedReader(new InputStreamReader(con  
	                    .getInputStream()));  
	            String line = "";  
	            for (line = br.readLine(); line != null; line = br.readLine()) {  
	                result+=line; 
	            }  
	            return new String(result.getBytes(),"utf-8");
	        } catch (MalformedURLException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return null;
	}
	
	public static Map<String,String> decodeXml(String content) {

		Map<String, String> map = new HashMap<String, String>();
        try {
       	 SAXReader reader = new SAXReader();
       	 Document document = reader.read(new StringReader(content));
       	 Element root = document.getRootElement();
    		List<Element> elementList = root.elements();
    		for (Element e : elementList)
    			map.put(e.getName(), e.getText());
        } catch (Exception e ) {
	      }
		        return map;

    }
}

