package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 一张牌的信息
 * @author Administrator
 *
 */
public class DouniuOnePai extends AbstractMessage {
	
	public static final int TYPE			 = 5;
	public static final int ID				 = 33;
	
	
    //显示的时候的值
	private int pokerNum;
	
	//花色
	private int pokerSuit;

	//对应的斗牛中的值
	private int pokerValue;
	
	
	public DouniuOnePai() {
		super();
	}

	public DouniuOnePai(int pokerNum, int pokerSuit, int pokerValue) {
		super();
		this.pokerNum = pokerNum;
		this.pokerSuit = pokerSuit;
		this.pokerValue = pokerValue;
	}
    
	public int getPokerNum() {
		return pokerNum;
	}

	public void setPokerNum(int pokerNum) {
		this.pokerNum = pokerNum;
	}

	public int getPokerSuit() {
		return pokerSuit;
	}

	public void setPokerSuit(int pokerSuit) {
		this.pokerSuit = pokerSuit;
	}

	public int getPokerValue() {
		return pokerValue;
	}

	public void setPokerValue(int pokerValue) {
		this.pokerValue = pokerValue;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		
		pokerNum = in.readInt();
		pokerSuit = in.readInt();
		pokerValue = in.readInt();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(getPokerNum());
		out.writeInt(getPokerSuit());
		out.writeInt(getPokerValue());
	}

	@Override
	public int getMessageId() {
		return TYPE;
	}

	@Override
	public int getMessageType() {
		return ID;
	}

}
