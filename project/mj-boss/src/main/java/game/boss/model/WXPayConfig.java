package game.boss.model;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

	/**
    * @ClassName: WXPayConfig
    * @Description: 微信支付所需要的参数
    * @author 13323659953@163.com
    * @date 2017年6月12日
    *
    */
@Component
public class WXPayConfig {
	//统一下单url
	private String payUrl;
	//appid
	private String appid;
	//商户号
	private String mch_id;
	//支付场景提示信息
	private String body;
	//回调地址
	private String notify_url;
	//支付场景类型  默认为APP
	private String trade_type;
	//支付秘钥
	private String key;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}


	public String getBody() {
		String bbody = "";
		try {
			bbody = new String(body.getBytes("GBK"),"UTF-8");
			return bbody;
		} catch (UnsupportedEncodingException e) {
			return body;
		}
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	@Override
	public String toString() {
		return "WXPayConfig [payUrl=" + payUrl + ", appid=" + appid + ", mch_id=" + mch_id + ", body=" + body
				+ ", notify_url=" + notify_url + ", trade_type=" + trade_type + ", key=" + key + "]";
	}
	
	
}

