package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 用户积分信息
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class ScoreInfo extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 38;
	/**
	 * 位置
	 */
	private int locationIndex;
	
	private int initScore;
	
	private int winScore;
	
	public ScoreInfo(){
		
	}
	
	public ScoreInfo(int locationIndex, int initScore, int winScore){
		this.locationIndex = locationIndex;
		this.initScore = initScore;
		this.winScore = winScore;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		locationIndex = in.readInt();
		initScore = in.readInt();
		winScore =in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(getLocationIndex());
		out.writeInt(getInitScore());
		out.writeInt(getWinScore());
	}
	
	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	public int getInitScore() {
		return initScore;
	}

	public void setInitScore(int initScore) {
		this.initScore = initScore;
	}

	public int getWinScore() {
		return winScore;
	}

	public void setWinScore(int winScore) {
		this.winScore = winScore;
	}

	@Override
	public String toString() {
		return "ScoreInfo [locationIndex=" + locationIndex + ",initScore=" + initScore + ",winScore=" + winScore  + " ]";
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
