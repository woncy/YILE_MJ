package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
    * @ClassName: WXPayQuery
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
    * @date 2017年6月14日
    *
    */

public class WXPayQuery extends AbstractMessage{

	public static final int TYPE			 = 7;
	public static final int ID				 = 30;
	
	private String out_trade_no;
	
	    
	public WXPayQuery() {
		super();
		
	}
	public WXPayQuery(String out_trade_no) {
		super();
		this.out_trade_no = out_trade_no;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}



	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		out_trade_no = in.readString();
	}


	    
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(out_trade_no);
	}


	    
	@Override
	public int getMessageType() {
		return TYPE;
	}

	
	 
	    
	@Override
	public int getMessageId() {
		return ID;
	}

}

