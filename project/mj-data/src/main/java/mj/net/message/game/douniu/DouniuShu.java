package mj.net.message.game.douniu;

import java.io.IOException;
import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 返回其他用户的下注数量
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuShu extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 12;
	
	/*
	 * opt_qiangzhuang , opt_xiazhu, opt_liangpai
	 */
	private String opt;
//	/**
//	 * 返回结果
//	 */
//	private boolean flage;
	/**
	 * 下住的数量
	 */
	private int zhuNum;
	
	public DouniuShu(){
		
	}
	
	public DouniuShu(String opt ,int zhuNum){
		this.opt = opt;
//		this.flage = flage;
		this.zhuNum = zhuNum;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		opt = in.readString();
//		flage = in.readBoolean();
		zhuNum = in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(getOpt());
//		out.writeBoolean(getFlage());
		out.writeInt(getZhuNum());
	}

	public String getOpt() {
		return opt;
	}
	
	public void setOpt(String opt) {
		this.opt = opt;
	}

//	/**
//	 * 返回结果
//	 */
//	public boolean getFlage() {
//		return flage;
//	}
//	
//	/**
//	 * 返回结果
//	 */
//	public void setFlage(boolean flage) {
//		this.flage = flage;
//	}

	
	@Override
	public String toString() {
		return "DouniuShu [opt=" + opt  + ",zhuNum=" + zhuNum +", ]";
	}
	
	public int getZhuNum() {
		return zhuNum;
	}

	public void setZhuNum(int zhuNum) {
		this.zhuNum = zhuNum;
	}
	
	@Override
	public final int getMessageType() {
		return TYPE;
	}

	@Override
	public final int getMessageId() {
		return ID;
	}
}
