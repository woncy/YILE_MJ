package mj.net.message.game.douniu;

import java.io.IOException;
import java.util.ArrayList;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 玩家自己的发牌消息
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuFaPaiRet extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 11; //玩家自己的发牌信息
	
	private int index;
	private ArrayList<DouniuOnePai> onePai; //一张牌的信息
	
	
	public DouniuFaPaiRet(){
		
	}

	public DouniuFaPaiRet(int index, ArrayList<DouniuOnePai> onePai) {
		super();
		this.index = index;
		this.onePai = onePai;
	}



	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		index= in.readInt();
		 int onePaiLength =in.readInt();
		  if(onePaiLength == -1){
			  onePai = null;
		  }else{
			  onePai= new java.util.ArrayList<DouniuOnePai>(onePaiLength);
	         for(int i =0; i< onePaiLength; i++){
	        	 DouniuOnePai onePaiItem =new DouniuOnePai();
	        	 onePaiItem.decode(in);
	        	 onePai.add(onePaiItem);
	         }
		  }
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(getIndex());
		if(onePai == null){
			out.writeInt(-1);
		}else{
			java.util.ArrayList<DouniuOnePai> onePaiList= getOnePai();
			 int shouPaiLength =onePaiList.size();
			 out.writeInt(shouPaiLength);
			 for(DouniuOnePai onePaiItem : onePaiList){
				 onePaiItem.encode(out);
			 }
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	

	public ArrayList<DouniuOnePai> getOnePai() {
		return onePai;
	}

	public void setOnePai(ArrayList<DouniuOnePai> onePai) {
		this.onePai = onePai;
	}

	
	
	@Override
	public String toString() {
		return "DouniuFaPaiRet [index=" + index + ",  onePai="
				+ onePai + "]";
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
