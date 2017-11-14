package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

public class UserRoomResult {
	private int locationIndex;
	private int winCount;
	private int faildCount;
	private int niuniuCount;
	private int zhadanCount;
	private int wuHuaNiuCount;
	private int siHuaNiuCount;
	private int wuXiaoNiuCount;
	private int score;
	public UserRoomResult() {
		super();
	}
	
	public void encode(Output out) throws IOException{
		out.writeInt(this.locationIndex);
		out.writeInt(winCount);
		out.writeInt(faildCount);
		out.writeInt(niuniuCount);
		out.writeInt(zhadanCount);
		out.writeInt(wuHuaNiuCount);
		out.writeInt(siHuaNiuCount);
		out.writeInt(wuXiaoNiuCount);
		out.writeInt(score);
	}
	public void decode(Input in) throws IOException, ProtocolException{
		this.locationIndex = in.readInt();
		this.winCount = in.readInt();
		this.faildCount = in.readInt();
		this.niuniuCount = in.readInt();
		this.zhadanCount = in.readInt();
		this.wuHuaNiuCount = in.readInt();
		this.siHuaNiuCount = in.readInt();
		this.wuXiaoNiuCount = in.readInt();
		this.score = in.readInt();
	}
	public int getLocationIndex() {
		return locationIndex;
	}
	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}
	public int getWinCount() {
		return winCount;
	}
	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}
	public int getFaildCount() {
		return faildCount;
	}
	public void setFaildCount(int faildCount) {
		this.faildCount = faildCount;
	}
	public int getNiuniuCount() {
		return niuniuCount;
	}
	public void setNiuniuCount(int niuniuCount) {
		this.niuniuCount = niuniuCount;
	}
	public int getZhadanCount() {
		return zhadanCount;
	}
	public void setZhadanCount(int zhadanCount) {
		this.zhadanCount = zhadanCount;
	}
	public int getWuHuaNiuCount() {
		return wuHuaNiuCount;
	}
	public void setWuHuaNiuCount(int wuHuaNiuCount) {
		this.wuHuaNiuCount = wuHuaNiuCount;
	}
	public int getSiHuaNiuCount() {
		return siHuaNiuCount;
	}
	public void setSiHuaNiuCount(int siHuaNiuCount) {
		this.siHuaNiuCount = siHuaNiuCount;
	}
	public int getWuXiaoNiuCount() {
		return wuXiaoNiuCount;
	}
	public void setWuXiaoNiuCount(int wuXiaoNiuCount) {
		this.wuXiaoNiuCount = wuXiaoNiuCount;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "UserRoomResult [locationIndex=" + locationIndex + ", winCount=" + winCount + ", faildCount="
				+ faildCount + ", niuniuCount=" + niuniuCount + ", zhadanCount=" + zhadanCount + ", wuHuaNiuCount="
				+ wuHuaNiuCount + ", siHuaNiuCount=" + siHuaNiuCount + ", siXiaoNiuCount=" + wuXiaoNiuCount + ", score="
				+ score + "]";
	}
	
	
	
}
