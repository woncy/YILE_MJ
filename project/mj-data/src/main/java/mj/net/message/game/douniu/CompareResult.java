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
public class CompareResult extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 36;
	
	private int winCount; 
	
	private boolean isZhuang;
	
	private int locationIndex ; 
	
	private ArrayList<DouniuOnePai>  paiList; 
	
	private int paiType ;
	
	private int  winCountNum;
	
	private String  scores;  // 总分
	
	private int  currentCount; //当前局数
    public CompareResult(){
		
	}

	public CompareResult(int winCount, boolean isZhuang, int locationIndex,
			ArrayList<DouniuOnePai> paiList, int paiType, int winCountNum,
			String scores, int currentCount) {
		super();
		this.winCount = winCount;
		this.isZhuang = isZhuang;
		this.locationIndex = locationIndex;
		this.paiList = paiList;
		this.paiType = paiType;
		this.winCountNum = winCountNum;
		this.scores = scores;
		this.currentCount = currentCount;
	}

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	

	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		winCount = in.readInt();
		isZhuang =in.readBoolean();
		locationIndex = in.readInt();
		paiType =in.readInt();
		int paiCount = in.readInt();
		if(paiCount == -1){
			paiList = null;
		}else{
			paiList = new ArrayList<DouniuOnePai>(paiCount);
			for(int i =0; i< paiCount; i++){
				DouniuOnePai onePaiItem =new DouniuOnePai();
	        	 onePaiItem.decode(in);
	        	 paiList.add(onePaiItem);
			}
		}
		winCountNum =in.readInt();
		scores=in.readString();
		currentCount=in.readInt();
	}
	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(winCount);
		out.writeBoolean(isZhuang);
		out.writeInt(locationIndex);
		out.writeInt(paiType);
	
		if(paiList == null ){
			out.writeInt(-1);
		}else{
			java.util.ArrayList<DouniuOnePai> onePaiList= getPaiList();
			 int shouPaiLength =onePaiList.size();
			 out.writeInt(shouPaiLength);
			 for(DouniuOnePai onePaiItem : onePaiList){
				 onePaiItem.encode(out);
			 }
		}
		out.writeInt(getWinCountNum());
		out.writeString(getScores());
		out.writeInt(getCurrentCount());
	}
	
	public ArrayList<DouniuOnePai> getPaiList() {
		return paiList;
	}

	public void setPaiList(ArrayList<DouniuOnePai> paiList) {
		this.paiList = paiList;
	}

	
//	public DouniuPaiType getPaiType() {
//		return paiType;
//	}
//
//	public void setPaiType(DouniuPaiType paiType) {
//		this.paiType = paiType;
//	}

	public int getPaiType() {
		return paiType;
	}



	public void setPaiType(int paiType) {
		this.paiType = paiType;
	}

	
	public String getScores() {
		return scores;
	}

	public void setScores(String scores) {
		this.scores = scores;
	}

	public int getWinCount() {
		return winCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

	public boolean isZhuang() {
		return isZhuang;
	}

	public void setZhuang(boolean isZhuang) {
		this.isZhuang = isZhuang;
	}

	
	
	public int getCurrentCount() {
		return currentCount;
	}


	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}


	public int getWinCountNum() {
		return winCountNum;
	}

	public void setWinCountNum(int winCountNum) {
		this.winCountNum = winCountNum;
	}

	
	@Override
	public String toString() {
		return "CompareResult [winCount=" + winCount + ", isZhuang=" + isZhuang
				+ ", locationIndex=" + locationIndex + ", paiList=" + paiList
				+ ", paiType=" + paiType + ", winCountNum=" + winCountNum
				+ ", scores=" + scores + ", currentCount=" + currentCount + "]";
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
