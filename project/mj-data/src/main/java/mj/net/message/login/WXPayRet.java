package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 
    * @ClassName: WXPayRet
    * @Description: 微信支付返回消息，包含前端必要参数prepayid,sign;
    * @author 13323659953@163.com
    * @date 2017年6月12日
    *
 */
public class WXPayRet extends AbstractMessage{
	public static final int TYPE			 = 7;
	public static final int ID				 = 28;
	
	private boolean result; //结果

	private String appid; //appId
	private String noncestr; //随机字符串
	private String myPackage; //package=Sign=WXPay;
	private String partnerid; //商户号
	private String prepayid; //prepay_id;
	private String timestamp; //时间戳
	private String sign;
	
	
	/**
	 * 创建一个新的实例 WXPayRet.
	 *
	 */

	public WXPayRet() {
	}
	
	public WXPayRet(boolean result){
		this.result = result;
	}
	    
	public WXPayRet(boolean result, String appid, String noncestr, String myPackage, String partnerid, String prepayid,
			String timestamp,String sign) {
		super();
		this.result = result;
		this.appid = appid;
		this.noncestr = noncestr;
		this.myPackage = myPackage;
		this.partnerid = partnerid;
		this.prepayid = prepayid;
		this.timestamp = timestamp;
		this.sign = sign;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.result = in.readBoolean();
		this.appid = in.readString();
		this.noncestr = in.readString();
		this.myPackage = in.readString();
		this.partnerid = in.readString();
		this.prepayid = in.readString();
		this.timestamp = in.readString();
		this.sign = in.readString();
	}
	
	    
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeBoolean(result);
		out.writeString(appid);
		out.writeString(noncestr);
		out.writeString(myPackage);
		out.writeString(partnerid);
		out.writeString(prepayid);
		out.writeString(timestamp);
		out.writeString(sign);
	}
	
	@Override
	public int getMessageId() {
		return ID;
	}
	
	    
	@Override
	public int getMessageType() {
		return TYPE;
	}


	public boolean isResult() {
		return result;
	}


	public void setResult(boolean result) {
		this.result = result;
	}


	public String getAppid() {
		return appid;
	}


	public void setAppid(String appid) {
		this.appid = appid;
	}


	public String getNoncestr() {
		return noncestr;
	}


	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}


	public String getMyPackage() {
		return myPackage;
	}


	public void setMyPackage(String myPackage) {
		this.myPackage = myPackage;
	}


	public String getPartnerid() {
		return partnerid;
	}


	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}


	public String getPrepayid() {
		return prepayid;
	}


	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "WXPayRet [result=" + result + ", appid=" + appid + ", noncestr=" + noncestr + ", myPackage=" + myPackage
				+ ", partnerid=" + partnerid + ", prepayid=" + prepayid + ", timestamp=" + timestamp + ", sign=" + sign
				+ "]";
	}
	
	
	
	
	

}
