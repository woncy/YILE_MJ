package mj.net.message.game.douniu;

import java.io.IOException;
import java.util.ArrayList;
import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
import mj.data.poker.douniu.DouniuPaiType;
/**
 * 
 * @author 13323659953@163.com
 * liuyihui
 */
public class JingJiResult extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 39;
	
	private int winCount; 
	
	private int locationIndex ; 
	
	private int winnerLocationIndex = -1 ;  
	
	private DouniuPaiType paiType ;//自己的牌的类型。
	
	private boolean isLastWinner;
	
	private ArrayList<DouniuOnePai>  winnerPaiList; 
	
	private ArrayList<ScoreInfo> scoreInfoList;
	
	public ArrayList<ScoreInfo> getScoreInfoList() {
		return scoreInfoList;
	}

	public void setScoreInfoList(ArrayList<ScoreInfo> scoreInfoList) {
		this.scoreInfoList = scoreInfoList;
	}

	public int getWinnerLocationIndex() {
		return winnerLocationIndex;
	}

	public void setWinnerLocationIndex(int winnerLocationIndex) {
		this.winnerLocationIndex = winnerLocationIndex;
	}

	public ArrayList<DouniuOnePai> getWinnerPaiList() {
		return winnerPaiList;
	}

	public void setWinnerPaiList(ArrayList<DouniuOnePai> winnerPaiList) {
		this.winnerPaiList = winnerPaiList;
	}

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	public boolean isLastWinner() {
		return isLastWinner;
	}

	public void setLastWinner(boolean isLastWinner) {
		this.isLastWinner = isLastWinner;
	}

	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		winCount = in.readInt();
		locationIndex = in.readInt();
		winnerLocationIndex = in.readInt();
		int index = in.readInt();
		paiType = DouniuPaiType.fromIndex(index);
		isLastWinner = in.readBoolean();
		int paiCount = in.readInt();
		if(paiCount == -1){
			winnerPaiList = null;
		}else{
			winnerPaiList = new ArrayList<DouniuOnePai>();
			for(int i = 0 ;i < paiCount; i++ ){
				DouniuOnePai  onePai =new DouniuOnePai();
				onePai.decode(in);
				winnerPaiList.add(onePai);
			}
			
		}
		int scoreInfoLen = in.readInt();
		if(scoreInfoLen == -1){
			scoreInfoList = null;
		}else{
			scoreInfoList = new  ArrayList<ScoreInfo>();
			for(int  i = 0 ; i < scoreInfoLen ; i++){
				ScoreInfo  scoreInfo = new ScoreInfo();
				scoreInfo.decode(in);
			}
		}
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(winCount);
		out.writeInt(locationIndex);
		out.writeInt(winnerLocationIndex);
		out.writeInt(paiType.getIndex());
		out.writeBoolean(isLastWinner);
		if(winnerPaiList == null ){
			out.writeInt(-1);
		}else{
			out.writeInt(winnerPaiList.size());
			for(int i = 0 ; i < winnerPaiList.size() ; i++){
				DouniuOnePai   douNiuPai = winnerPaiList.get(i);
				douNiuPai.encode(out);
			}
		}
		if(scoreInfoList == null || scoreInfoList.size() == 0 ){
			out.writeInt(-1);
		}else{
			out.writeInt(scoreInfoList.size() );
			for(int i = 0 ; i< scoreInfoList.size() ; i++){
				ScoreInfo scoreInfo = scoreInfoList.get(i);
				scoreInfo.encode(out);
			}
		}
	}

	public DouniuPaiType getPaiType() {
		return paiType;
	}

	public void setPaiType(DouniuPaiType paiType) {
		this.paiType = paiType;
	}

	public int getWinCount() {
		return winCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

	public JingJiResult(){
		
	}
	
	
	
	@Override
	public String toString() {
		return "JingJiResult [ ]";
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
