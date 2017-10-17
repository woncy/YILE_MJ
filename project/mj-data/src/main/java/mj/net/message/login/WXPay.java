package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
    * @ClassName: WXPay
    * @Description:   微信支付所需提交上来的参数
    * @author 13323659953@163.com
    * @date 2017年6月12日
    *
    */
public class WXPay extends AbstractMessage{
	public static final int TYPE			 = 7;
	public static final int ID				 = 27;
	
	/**
	 * 支付 
	 */
	
	private int goldCount;
	private int payType;
	
	 
	public WXPay() {
		super();
	}
	
	public WXPay(int goldCount,int payType) {
		super();
		this.goldCount = goldCount;
		this.payType = payType;
	}

	

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		goldCount = in.readInt();
		payType = in.readInt();
	}

	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param arg0
	    * @throws IOException
	    * @throws ProtocolException
	    * @see com.isnowfox.core.net.message.Message#encode(com.isnowfox.core.io.Output)
	    */
	    
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(goldCount);
		out.writeInt(payType);
	}
	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @return
	    * @see com.isnowfox.core.net.message.Message#getMessageId()
	    */
	    
	@Override
	public int getMessageId() {
		return ID;
	}

	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @return
	    * @see com.isnowfox.core.net.message.Message#getMessageType()
	    */
	    
	@Override
	public int getMessageType() {
		return TYPE;
	}


	public int getGoldCount() {
		return goldCount;
	}
	


	public int getPayType() {
		return payType;
	}


	public void setPayType(int payType) {
		this.payType = payType;
	}


	public void setGoldCount(int goldCount) {
		this.goldCount = goldCount;
	}

	@Override
	public String toString() {
		return "WXPay [goldCount=" + goldCount + ", payType=" + payType + "]";
	}


	
	
}

