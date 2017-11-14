package game.douniu.scene.msg;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;

public class ChapterUserMsg {

	private int userId;
	private int locationIndex;
	private int score;
	private int [] pais;
	private int paiType;
	
	
	
	public ChapterUserMsg() {
		super();
	}

	public ChapterUserMsg(int userId, int locationIndex, int score, int[] pais, int paiType) {
		super();
		this.userId = userId;
		this.locationIndex = locationIndex;
		this.score = score;
		this.pais = pais;
		this.paiType = paiType;
	}

	public void encode(ByteBuf out) {
		out.writeInt(userId);
		out.writeInt(locationIndex);
		out.writeInt(score);
		int len = -1;
		if(pais!=null){
			len = pais.length;
		}
		out.writeInt(len);
		for (int i = 0; i < len; i++) {
			out.writeInt(pais[i]);
		}
		out.writeInt(paiType);
	}

	public void decode(ByteBuf in) {
		this.userId = in.readInt();
		this.locationIndex = in.readInt();
		this.score = in.readInt();
		int len = in.readInt();
		if(len>-1){
			this.pais = new int[len];
			for (int i = 0; i < pais.length; i++) {
				 pais[i] = in.readInt();
				
			}
		}
		this.paiType = in.readInt();
		
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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
	public String toString() {
		return "ChapterUserMsg [userId=" + userId + ", locationIndex=" + locationIndex + ", score=" + score + ", pais="
				+ Arrays.toString(pais) + ", paiType=" + paiType + "]";
	}
	
	

	
 
	
}
