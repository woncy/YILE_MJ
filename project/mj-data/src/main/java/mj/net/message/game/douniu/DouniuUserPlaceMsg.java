package mj.net.message.game.douniu;

import java.io.IOException;
import java.util.ArrayList;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 一局斗牛的消息
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuUserPlaceMsg extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 27;
	
	/**
	 * 用户牌的信息
	 */
	private  java.util.ArrayList<DouniuOnePai> shouPai;
	/**
	 * 给其他用户显示牌的长度
	 */
	private int shouPaiLen;
	
	public DouniuUserPlaceMsg(){
		
	}
	
	public DouniuUserPlaceMsg(ArrayList<DouniuOnePai> shouPai, int shouPaiLen) {
		super();
		this.shouPai = shouPai;
		this.shouPaiLen = shouPaiLen;
	}



	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		 int shouPaiLength =in.readInt();
		  if(shouPaiLength == -1){
			  shouPai = null;
		  }else{
			 shouPai= new java.util.ArrayList<DouniuOnePai>(shouPaiLength);
	         for(int i =0; i< shouPaiLength; i++){
	        	 DouniuOnePai onePaiItem =new DouniuOnePai();
	        	 onePaiItem.decode(in);
	        	 shouPai.add(onePaiItem);
	         }
		  }
		shouPaiLen = in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		if(shouPai == null){
			out.writeInt(-1);
		}else{
			java.util.ArrayList<DouniuOnePai> onePaiList= getShouPai();
			 int shouPaiLength =onePaiList.size();
			 out.writeInt(shouPaiLength);
			 for(DouniuOnePai onePaiItem : onePaiList){
				 onePaiItem.encode(out);
			 }
		}
		out.writeInt(getShouPaiLen());
	}

	
   /**
    * 手牌的信息
    * @return
    */
	public java.util.ArrayList<DouniuOnePai> getShouPai() {
		return shouPai;
	}

	public void setShouPai(java.util.ArrayList<DouniuOnePai> shouPai) {
		this.shouPai = shouPai;
	}

	/**
	 * 给其他用户显示牌的长度
	 */
	public int getShouPaiLen() {
		return shouPaiLen;
	}
	
	/**
	 * 给其他用户显示牌的长度
	 */
	public void setShouPaiLen(int shouPaiLen) {
		this.shouPaiLen = shouPaiLen;
	}

	@Override
	public String toString() {
		return "DouniuUserPlaceMsg [shouPai=" + shouPai + ", shouPaiLen="
				+ shouPaiLen + "]";
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
