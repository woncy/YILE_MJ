package game.douniu.scene.msg;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;

public class ChapterUserMsg {

	private int userId;
	private int winnerId = -1;
	private int score;
	private int[][] pais;

	public void encode(ByteBuf out) {
		out.writeInt(userId);
		out.writeInt(winnerId);
		out.writeInt(score);

		if (pais == null)
			out.writeInt(-1);
		else {
			int len = pais.length;
			out.writeInt(len);
			for (int i = 0; i < pais.length; i++) {
				int [] j = pais[i];
				if(j == null){
					out.writeInt(-1);
				}else{
					out.writeInt(j.length);
					for(int k = 0 ; k < j.length; k++){
						out.writeInt(j[k]);
					}
				}
				
			}
		}
	}

	public void decode(ByteBuf in) {
		this.userId = in.readInt();
		this.winnerId = in.readInt();
		this.score = in.readInt();
		int len = in.readInt();
		if (len == -1) {
			pais = null;
		} else {
			pais = new int[5][];
			for (int i = 0; i < pais.length; i++) {
				int pailenth = in.readInt();
				pais[i] = new int[3];
				for(int j = 0 ; j < pailenth;j++){
					pais[i][j] = in.readInt();
				}
			}
		}
	}

	public int[][] getPais() {
		return pais;
	}

	public void setPais(int[][] pais) {
		this.pais = pais;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(int winnerId) {
		this.winnerId = winnerId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "ChapterUserMsg [userId=" + userId + ", winnerId=" + winnerId
				+ ", score=" + score + ", pais=" + Arrays.toString(pais) + "]";
	}
 
	
}
