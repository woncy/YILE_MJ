package Douniu.data;

import mj.data.poker.douniu.DouniuPai;
import mj.data.poker.douniu.DouniuPaiType;
import java.util.ArrayList;


public class ComparePai {
	
	private int locationIndex;
	
	private ArrayList<DouniuPai> paiList;
	
	private DouniuPaiType payType;
	
	private boolean isWinner;

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	public ArrayList<DouniuPai> getPaiList() {
		return paiList;
	}

	public void setPaiList(ArrayList<DouniuPai> paiList) {
		this.paiList = paiList;
	}

	public DouniuPaiType getPayType() {
		return payType;
	}

	public void setPayType(DouniuPaiType payType) {
		this.payType = payType;
	}

	public boolean isWinner() {
		return isWinner;
	}

	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

}
