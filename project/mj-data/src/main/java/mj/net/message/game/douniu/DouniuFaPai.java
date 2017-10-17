package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 发牌
 * FA:发牌，OUT:亮牌,OUT_OK:亮牌成功，没人用这个哎,X_NIU:小牛牛,Z_NIU:炸弹牛,N_NIU:牛牛,M_NIU:没牛
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuFaPai extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 10;
	
	/**
	 * 位置
	 */
	private int index;
	private int pai;

	
	public DouniuFaPai(){
		
	}
	
	public DouniuFaPai(int index, int pai){
		this.index = index;
		this.pai = pai;
		/*this.xNiuNiu = xNiuNiu;
		this.zNiuNiu = zNiuNiu;
		this.mNiuNiu = mNiuNiu;*/
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		index = in.readInt();
		pai = in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(getIndex());
		out.writeInt(getPai());
	
	}

	/**
	 * 位置
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * 位置
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	public int getPai() {
		return pai;
	}
	
	public void setPai(int pai) {
		this.pai = pai;
	}

	
//	public boolean getXNiuNiu() {
//		return xNiuNiu;
//	}
//	
//	public void setXNiuNiu(boolean xNiuNiu) {
//		this.xNiuNiu = xNiuNiu;
//	}
//
//	public boolean getZNiuNiu() {
//		return zNiuNiu;
//	}
//	
//	public void setZNiuNiu(boolean zNiuNiu) {
//		this.zNiuNiu = zNiuNiu;
//	}
//
//	public boolean getMNiuNiu() {
//		return mNiuNiu;
//	}
//	
//	public void setMNiuNiu(boolean mNiuNiu) {
//		this.mNiuNiu = mNiuNiu;
//	}

	
	

	@Override
	public String toString() {
		return "DouNiuFaPai [index=" + index + ",pai=" + pai +", ]";
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
