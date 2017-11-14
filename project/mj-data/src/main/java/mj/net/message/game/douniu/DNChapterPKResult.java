package mj.net.message.game.douniu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

import mj.data.MessageUtil;

public class DNChapterPKResult extends AbstractMessage {
	
	private static final int TYPE = 2;
	private static final int ID = 13;
	private int zhuangIndex;
	private int[] pai;	//庄家手牌
	private int paiType;	//庄家手牌类型
	private int socre;		//庄家最终得分
	List<UserPkResult> userResults = new ArrayList<UserPkResult>();
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.zhuangIndex = in.readInt();
		this.pai = in.readIntArray();
		this.paiType = in.readInt();
		this.socre = in.readInt();
		MessageUtil.decodeList(in, userResults, UserPkResult.class);
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(zhuangIndex);
		out.writeIntArray(pai);
		out.writeInt(paiType);
		out.writeInt(socre);
		MessageUtil.encodeList(out, userResults);
	}

	public int getZhuangIndex() {
		return zhuangIndex;
	}

	public void setZhuangIndex(int zhuangIndex) {
		this.zhuangIndex = zhuangIndex;
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

	public int getSocre() {
		return socre;
	}

	public void setSocre(int socre) {
		this.socre = socre;
	}

	public List<UserPkResult> getUserResults() {
		return userResults;
	}

	public void setUserResults(List<UserPkResult> userResults) {
		this.userResults = userResults;
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
		return "DNChapterPKResult [zhuangIndex=" + zhuangIndex + ", pai=" + Arrays.toString(pai) + ", paiType="
				+ paiType + ", socre=" + socre + ", userResults=" + userResults + "]";
	}

	
}
