package mj.net.message.game.douniu;

import java.io.IOException;
import java.util.Arrays;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class UserPkResult extends AbstractMessage{
	private static final int TYPE = 2;
	private static final int ID = 13;
	private int index;
	private int score;
	private int[] pai;
	private int paiType;
	private boolean pkResult;	//true赢，false输
	private int zhu;			//下注倍数
	
	public void decode(Input in) throws IOException, ProtocolException{
		this.index = in.readInt();
		this.score = in.readInt();
		this.pai = in.readIntArray();
		this.paiType = in.readInt();
		this.pkResult = in.readBoolean();
		this.zhu = in.readInt();
		
	}
	
	public void encode(Output out) throws IOException{
		out.writeInt(index);
		out.writeInt(score);
		out.writeIntArray(pai);
		out.writeInt(paiType);
		out.writeBoolean(pkResult);
		out.writeInt(zhu);
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int[] getPai() {
		return pai;
	}
	public void setPai(int[] pai) {
		this.pai = pai;
	}
	public int getPaiType() {
		return paiType;
	}
	public void setPaiType(int paiType) {
		this.paiType = paiType;
	}
	public boolean isPkResult() {
		return pkResult;
	}
	public void setPkResult(boolean pkResult) {
		this.pkResult = pkResult;
	}
	public int getZhu() {
		return zhu;
	}
	public void setZhu(int zhu) {
		this.zhu = zhu;
	}
	
	@Override
	public String toString() {
		return "UserPkResult [index=" + index + ", score=" + score + ", pai=" + Arrays.toString(pai) + ", paiType="
				+ paiType + ", pkResult=" + pkResult + ", zhu=" + zhu + "]";
	}

	@Override
	public int getMessageId() {
		// TODO 自动生成的方法存根
		return ID;
	}

	@Override
	public int getMessageType() {
		// TODO 自动生成的方法存根
		return TYPE;
	}
	
	
	
	
}
