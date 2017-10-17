package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 服务器返回下注的结果
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuZhuRet extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 13;
	
	private String opt;
	/**
	 * 用户的位置
	 */
	private int index;
	
	/**
	 * 下住的数量
	 */
	private int zhuNum;
	
	
	private int userZhu;  //用户现在的注数
	
	private int totalZhu;  //注池中所有的总注数。
	
	
	public int getUserZhu() {
		return userZhu;
	}

	public void setUserZhu(int userZhu) {
		this.userZhu = userZhu;
	}

	public int getTotalZhu() {
		return totalZhu;
	}

	public void setTotalZhu(int totalZhu) {
		this.totalZhu = totalZhu;
	}

	public DouniuZhuRet(){
		
	}
	
	public DouniuZhuRet(String opt, int index,int zhuNum){
		this.opt = opt;
		this.index = index;
		this.zhuNum = zhuNum;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		opt = in.readString();
		index = in.readInt();
		zhuNum = in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(getOpt());
		out.writeInt(getIndex());
		out.writeInt(getZhuNum());
	}

	public String getOpt() {
		return opt;
	}
	
	public void setOpt(String opt) {
		this.opt = opt;
	}

	/**
	 * 用户的位置
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * 用户的位置
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	
	@Override
	public String toString() {
		return "DouniuZhuRet [opt=" + opt + ",index=" + index +  ",zhuNum=" + zhuNum +", ]";
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
