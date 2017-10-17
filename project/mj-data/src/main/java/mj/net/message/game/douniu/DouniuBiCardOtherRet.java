package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 将比牌的结果返回给其他玩家
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuBiCardOtherRet extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 17;
	
	private int[] pai;
	/**
	 * 牛牛
	 */
	private boolean niuNiu;
	/**
	 * 小牛牛
	 */
	private boolean xNiuNiu;
	/**
	 * 炸弹牛
	 */
	private boolean zNiuNiu;
	/**
	 * 没牛
	 */
	private boolean mNiuNiu;
	/**
	 * ture: 主动比牌胜  false: 被比牌人胜
	 */
	private boolean biCard;
	
	public DouniuBiCardOtherRet(){
		
	}
	
	public DouniuBiCardOtherRet(int[] pai, boolean niuNiu, boolean xNiuNiu, boolean zNiuNiu, boolean mNiuNiu, boolean biCard){
		this.pai = pai;
		this.niuNiu = niuNiu;
		this.xNiuNiu = xNiuNiu;
		this.zNiuNiu = zNiuNiu;
		this.mNiuNiu = mNiuNiu;
		this.biCard = biCard;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		pai = in.readIntArray();
		niuNiu = in.readBoolean();
		xNiuNiu = in.readBoolean();
		zNiuNiu = in.readBoolean();
		mNiuNiu = in.readBoolean();
		biCard = in.readBoolean();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeIntArray(getPai());
		out.writeBoolean(getNiuNiu());
		out.writeBoolean(getXNiuNiu());
		out.writeBoolean(getZNiuNiu());
		out.writeBoolean(getMNiuNiu());
		out.writeBoolean(getBiCard());
	}

	public int[] getPai() {
		return pai;
	}
	
	public void setPai(int[] pai) {
		this.pai = pai;
	}

	/**
	 * 牛牛
	 */
	public boolean getNiuNiu() {
		return niuNiu;
	}
	
	/**
	 * 牛牛
	 */
	public void setNiuNiu(boolean niuNiu) {
		this.niuNiu = niuNiu;
	}

	/**
	 * 小牛牛
	 */
	public boolean getXNiuNiu() {
		return xNiuNiu;
	}
	
	/**
	 * 小牛牛
	 */
	public void setXNiuNiu(boolean xNiuNiu) {
		this.xNiuNiu = xNiuNiu;
	}

	/**
	 * 炸弹牛
	 */
	public boolean getZNiuNiu() {
		return zNiuNiu;
	}
	
	/**
	 * 炸弹牛
	 */
	public void setZNiuNiu(boolean zNiuNiu) {
		this.zNiuNiu = zNiuNiu;
	}

	/**
	 * 没牛
	 */
	public boolean getMNiuNiu() {
		return mNiuNiu;
	}
	
	/**
	 * 没牛
	 */
	public void setMNiuNiu(boolean mNiuNiu) {
		this.mNiuNiu = mNiuNiu;
	}

	/**
	 * ture: 主动比牌胜  false: 被比牌人胜
	 */
	public boolean getBiCard() {
		return biCard;
	}
	
	/**
	 * ture: 主动比牌胜  false: 被比牌人胜
	 */
	public void setBiCard(boolean biCard) {
		this.biCard = biCard;
	}

	
	@Override
	public String toString() {
		return "DouNiuBiCardRet [pai=" + pai + ",niuNiu=" + niuNiu + ",xNiuNiu=" + xNiuNiu + ",zNiuNiu=" + zNiuNiu + ",mNiuNiu=" + mNiuNiu + ",biCard=" + biCard + ", ]";
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
