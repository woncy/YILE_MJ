package game.boss.model;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

/**
    * @ClassName: AliPayConfig
    * @Description: 支付宝支付参数封装
    * @author 13323659953@163.com
    * @date 2017年6月17日
    *
    */
@Component
public class AliPayConfig {
	
	public static final String PRODUCTCODE = "QUICK_MSECURITY_PAY";
	public static final String FORMAT = "json";
	public static final String CHARSET = "UTF-8";
	public static final String SIGN_TYPE = "RSA2";
	
	private String alipay_Url;
	private String app_id;
	private String app_private_key;
	private String app_public_key;
	private String partner_id;
	private String body;
	private String subject;
	private String timeoutExpress;
	private String notifyurl;
	
	public String getAlipay_Url() {
		return alipay_Url;
	}
	public void setAlipay_Url(String alipay_Url) {
		this.alipay_Url = alipay_Url;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getApp_private_key() {
		return app_private_key;
	}
	public void setApp_private_key(String app_private_key) {
		this.app_private_key = app_private_key;
	}
	public String getApp_public_key() {
		return app_public_key;
	}
	public void setApp_public_key(String app_public_key) {
		this.app_public_key = app_public_key;
	}
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public String getBody() {
		try {
			String bbody = new String(body.getBytes("GBK"),"UTF-8");
			return bbody;
		} catch (UnsupportedEncodingException e) {
			return body;
		}
	}
	public void setBody(String body) {
		this.body = body;
		
	}
	public String getSubject() {
		try {
			String sub = new String(subject.getBytes("GBK"),"UTF-8");
			return sub;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
		
		
	}
	public String getTimeoutExpress() {
		return timeoutExpress;
	}
	public void setTimeoutExpress(String timeoutExpress) {
		this.timeoutExpress = timeoutExpress;
	}
	public String getNotifyurl() {
		return notifyurl;
	}
	public void setNotifyurl(String notifyurl) {
		this.notifyurl = notifyurl;
	}
	@Override
	public String toString() {
		return "AliPayConfig [alipay_Url=" + alipay_Url + ", app_id=" + app_id + ", app_private_key=" + app_private_key
				+ ", app_public_key=" + app_public_key + ", partner_id=" + partner_id + ", body=" + body + ", subject="
				+ subject + ", timeoutExpress=" + timeoutExpress + ", notifyurl=" + notifyurl + "]";
	}
	
	
	
}

