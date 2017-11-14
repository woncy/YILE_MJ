package mj.net.message.game.douniu;

import java.io.IOException;
import java.util.Arrays;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class SeatUserInfo extends AbstractMessage {
	private static final int TYPE = 2;
	private static final int ID = 7;
	private int index;
	private int userId;
	private int[] pais=new int[]{-2,-2,-2,-2,-2};
	private int paiType=-1; //0 无牛 9牛九 10 牛牛  11四花牛 12五花牛 13 四炸 14 五小牛
	private boolean isKaiPai;//是否开牌了
	private int zhu;//当前注数
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.pais = in.readIntArray();
		this.paiType = in.readInt();
		this.isKaiPai = in.readBoolean();
		this.zhu = in.readInt();
		this.index = in.readInt();
		this.userId = in.readInt();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeIntArray(this.pais);
		out.writeInt(paiType);
		out.writeBoolean(isKaiPai);
		out.writeInt(zhu);
		out.writeInt(index);
		out.writeInt(userId);
		
		
	}
	
	
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public boolean isKaiPai() {
		return isKaiPai;
	}
	public void setKaiPai(boolean isKaiPai) {
		this.isKaiPai = isKaiPai;
	}
	public int getZhu() {
		return zhu;
	}
	public void setZhu(int zhu) {
		this.zhu = zhu;
	}
	public int[] getPais() {
		return pais;
	}
	public void setPais(int[] pais) {
		this.pais = pais;
	}
	public int getPaiType() {
		return paiType;
	}
	public void setPaiType(int paiType) {
		this.paiType = paiType;
	}
	@Override
	public int getMessageType() {
		// TODO 自动生成的方法存根
		return TYPE;
	}
	@Override
	public int getMessageId() {
		// TODO 自动生成的方法存根
		return ID;
	}
	@Override
	public String toString() {
		return "SeatUserInfo [index=" + index + ", userId=" + userId + ", pais=" + Arrays.toString(pais) + ", paiType="
				+ paiType + ", isKaiPai=" + isKaiPai + ", zhu=" + zhu + "]";
	}
	
}
