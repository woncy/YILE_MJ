package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 
    * @ClassName: Record
    * @Description: 显示总胜利次数的地方，跟在LoginRet之后发送给客户端
    * @author 13323659953@163.com
    * @date 2017年7月14日
    *
 */
public class Record extends AbstractMessage{
	public static final int TYPE = 7;
	public static final int ID = 35;
	
	private int majiangNum; //麻将总局数
	private int majiangWinCount;	//麻将赢的次数
	
	private int pokerNum;	//扑克总局数，暂时未用，可不用处理，但须解码
	private int pokerWinCount;	//扑克赢的总局数，暂时未用，可不用处理，但须解码
	
	
	
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		majiangNum = in.readInt();
		majiangWinCount = in.readInt();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(majiangNum);
		out.writeInt(majiangWinCount);
		out.writeInt(pokerNum);
		out.writeInt(pokerWinCount);
	}
	
	
	
	
	public int getMajiangNum() {
		return majiangNum;
	}
	public void setMajiangNum(int majiangNum) {
		this.majiangNum = majiangNum;
	}
	public int getMajiangWinCount() {
		return majiangWinCount;
	}
	public void setMajiangWinCount(int majiangWinCount) {
		this.majiangWinCount = majiangWinCount;
	}
	public int getPokerNum() {
		return pokerNum;
	}
	public void setPokerNum(int pokerNum) {
		this.pokerNum = pokerNum;
	}
	public int getPokerWinCount() {
		return pokerWinCount;
	}
	public void setPokerWinCount(int pokerWinCount) {
		this.pokerWinCount = pokerWinCount;
	}
	@Override
	public int getMessageId() {
		return ID;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}
	@Override
	public String toString() {
		return "Record [majiangNum=" + majiangNum + ", majiangWinCount=" + majiangWinCount + ", pokerNum=" + pokerNum
				+ ", pokerWinCount=" + pokerWinCount + "]";
	}
	
	
}
