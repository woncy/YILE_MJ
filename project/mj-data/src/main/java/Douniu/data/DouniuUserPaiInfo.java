package Douniu.data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang.ArrayUtils;

import mj.data.FanResult;
import mj.data.Pai;
import mj.data.UserPlace;
import mj.data.majiang.AgariUtils;
import mj.data.poker.douniu.DouniuPai;
import mj.data.poker.douniu.DouniuPaiType;

public class DouniuUserPaiInfo {
     private ArrayList<DouniuPai> shuoPai= new ArrayList<>();
     
     /**
      * niuniu
      */
     private ArrayList<Map.Entry<Integer, DouniuPai>> niuNiu= new ArrayList<>();
     
     /**
      * 牛几 niuNum
      */
     private ArrayList<Map.Entry<Integer, DouniuPai>> niuNum= new ArrayList<>();
     /**
      * 小牛牛
      */
     private ArrayList<Map.Entry<Integer, DouniuPai>> xiaoNiu= new ArrayList<>();
     /**
      * 炸弹牛
      */
     private ArrayList<Map.Entry<Integer, DouniuPai>> zhaNiu =new ArrayList<>();
     /**
      * 没牛
      */
     private ArrayList<Map.Entry<Integer, DouniuPai>> meiNiu=new ArrayList<>();
      
     private ArrayList<DouniuPai> out= new ArrayList<>();
     private int locationIndex; //玩家位置
    
     private DouniuPaiType fanResults;
     private FanResult maxFanResult;
     private boolean isWin; //是否赢家
     public boolean isWin() {
		return isWin;
	}


	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}


	public boolean isZhuang() {
		return isZhuang;
	}


	public void setZhuang(boolean isZhuang) {
		this.isZhuang = isZhuang;
	}


	private boolean isZhuang; //是否是庄
     private int  endType; //牌的类型
     
     private int userId;
     private String userName;
     
     private int score; //总分
     private int fan;  //翻的倍数
    
     
	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public DouniuUserPaiInfo() {
		super();
	}


	public int getLocationIndex() {
		return locationIndex;
	}


	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}
	
	
	
	
	    @SuppressWarnings("unchecked")
		public DouniuUserPaiInfo(ArrayList<DouniuPai> allPai, DouniuUserPlace[] userPlaces, DouniuUserPlace userPlace,
	            boolean isWin, boolean isZhuang) {
	    	    shuoPai = (ArrayList<DouniuPai>) userPlace.getShouPaiList().clone();
		        locationIndex = userPlace.getLocationIndex();
	            this.isWin = isWin;
		        this.isZhuang = isZhuang;
		        this.userName = userPlace.getUserName();
		        this.userId = userPlace.getUserId();
//		        shouPai.
	 }


		public void setMaxFanResult(FanResult orElseGet) {
			// TODO Auto-generated method stub
			
		}


		public FanResult[] getFanResults() {
			// TODO Auto-generated method stub
			return null;
		}


}