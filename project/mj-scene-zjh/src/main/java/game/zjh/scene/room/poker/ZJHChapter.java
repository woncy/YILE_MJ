package game.zjh.scene.room.poker;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isnowfox.core.net.message.Message;

import ZJH.data.ZJHConfig;
import game.zjh.scene.room.ZJHRoom;
import game.zjh.scene.room.ZJHSceneUser;
import game.zjh.scene.rules.Rules;
import game.zjh.scene.zjh.PokerPool;
import mj.data.ZJHUserPlace;
import mj.data.poker.Poker;
import mj.data.poker.zjh.ZJHPai;
import mj.data.poker.zjh.ZJHPaiType;
import mj.net.message.game.SyncOptTime;
import mj.net.message.game.zjh.AddZhuResult;
import mj.net.message.game.zjh.ComparePokerResult;
import mj.net.message.game.zjh.DelScoreResult;
import mj.net.message.game.zjh.FaInfo;
import mj.net.message.game.zjh.GenZhuResult;
import mj.net.message.game.zjh.OperationNext;
import mj.net.message.game.zjh.QiPokerResult;
import mj.net.message.game.zjh.RoomInfoPoker;
import mj.net.message.game.zjh.SelectPokerResult;
import mj.net.message.game.zjh.ZJHChapterMsg;
import mj.net.message.game.zjh.ZJHOperation;

public class ZJHChapter {
	 public static final int USER_NUMS = 4;
	    protected static final Logger log = LoggerFactory.getLogger(ZJHChapter.class);
	    //COMPARE-比牌  LIMP-跟注  FILL-加注 GUO-弃牌  FA-发牌  KAN-看牌
	    private static final String OPT_COMPARE = "COMPARE";
	    private static final String OPT_LIMP = "LIMP";
	    private static final String OPT_FILL = "FILL";
	    private static final String OPT_GUO = "GUO";
	    private static final String OPT_FA = "FA";
	    private static final String OPT_KAN = "KAN";
	    
	    private int sumScore;//总底分
	    private final Rules rules;
	    
	    private final PokerPool pokerPool;
	    
	    private final ZJHRoom room;
	    
	    private int chapterNums;//当前局数, 0开始
	    
	    //private  ZJHUserPlace[] zjhUserPlace;
	    private  ArrayList<ZJHUserPlace> zjhUserPlace;
	    
	    private int zhuangIndex;
	    
	    private int lun;//轮数
	    
	    //當前操作人
	    private int currentIndex = 0;
	    private boolean isStart;
	    private int maxZhu = 5;//当前最大分值
	    private int diFen = 5;//底分
	    private int zhuNo = 5;//不翻倍的底分
	    private int i = -1;
	    private int winIndex = -1;//赢家位置
	    /**
	     * 操作开始时间
	     */
	    private long operationTime = 0;
	    
		public ZJHChapter(ZJHRoom room, String rulesName) {
			i++;
	        this.rules = Rules.createRules(rulesName, room.getConfig());
	        this.pokerPool = new PokerPool(this.rules);
	        this.room = room;
	        zjhUserPlace = new ArrayList<ZJHUserPlace>();
	        zhuangIndex = 0;
	        zjhUserPlace.add(new ZJHUserPlace());
	        zjhUserPlace.get(i).setLocationIndex(i);
	        zjhUserPlace.get(i).setScore(room.getConfig().getInt(ZJHConfig.CHUSHIFEN));;
	    }
		
		public void start(){
			 	this.rules.rest();
		       // gameChapterEnd = null;
		        isStart = true;
		        pokerPool.start();
		        diFen = room.getConfig().getInt(ZJHConfig.DANZHU);
		        for (int i = 0; i < zjhUserPlace.size(); i++) {
		        	if(!(zjhUserPlace.get(i).isOffLine())){
		        		for (int j = 0; j < 3; j++) {
			        		zjhUserPlace.get(i).addShouPai(pokerPool.getFreePoker());
			        		zjhUserPlace.get(i).setZhu(diFen);
			        		zjhUserPlace.get(i).setKan(false);
						}
		        		zjhUserPlace.get(i).addScore(diFen);
		        		sumScore += diFen;
		        	}
		        	
		        }
		        changeCurrentIndex(zhuangIndex);
		}
	/**
	 * 去下一玩家
	 *
	 *@return
	 * 2017年7月5日
	 */
	private void goNext() {
		        int next = (currentIndex + 1) % zjhUserPlace.size();
		        changeCurrentIndex(next);
	}
	
	private void changeCurrentIndex(int index) {
	        this.currentIndex = index;
	        if(getUserNum() == 1){
	        	winIndex = currentIndex; 
	        	gameOver();
	        }else{
	        	if(currentIndex == 0){
	        		lun++;
	        	}
	        	if(zjhUserPlace.get(currentIndex).isOffLine()){//玩家掉线就弃牌
	        		ZJHSceneUser sceneUser = new ZJHSceneUser();
	        		sceneUser.setLocationIndex(currentIndex);
	        		qiPai(sceneUser);
	        	}
	        }
	      
	      
//			if(userPlaces[index].isOffLine()){
//				try {
//					Thread.sleep(1000);
//					faPai(true, false);
//					Thread.sleep(1000);
//					ArrayList<Pai> shouPai = userPlaces[index].getShouPai();
//					tuoguanChuPai(currentIndex, userPlaces[index].getLocationIndex(), shouPai.get(shouPai.size()-1));
//				} catch (InterruptedException e) {
//					// TODO 自动生成的 catch 块
//					e.printStackTrace();
//				}
//				
//			}
	    }
	

	public void updateUser(ZJHSceneUser sceneUser) {
    	ZJHUserPlace userPlace = zjhUserPlace.get(sceneUser.getLocationIndex());
        userPlace.setUserId(sceneUser.getUserId());
        userPlace.setUserName(sceneUser.getUserName());
    }

    public ZJHChapterMsg toMessage(int myLocationIndex) {
    	ZJHChapterMsg m = new ZJHChapterMsg();
        m.setChapterNumsMax(this.room.getConfig().getInt(ZJHConfig.CHAPTERMAX));
        m.setChapterNums(chapterNums);
        m.setZhuangIndex(zhuangIndex);
        return m;
    }
		
		/**
		 * 剩余局数
		 *@return
		 *@return
		 * 2017年7月4日
		 */
		public int getSurChapter(){
			return room.getConfig().getInt(ZJHConfig.CHAPTER_MAX) - chapterNums;
		}
		public int getChapterNums(){
			return chapterNums;
		}
		
		public void setChapterNums(int chapterNums) {
			this.chapterNums = chapterNums;
		}

		public Rules getRules() {
			return rules;
		}

		public PokerPool getPokerPool() {
			return pokerPool;
		}

		public ZJHRoom getRoom() {
			return room;
		}

		public ArrayList<ZJHUserPlace> getZjhUserPlace() {
			return zjhUserPlace;
		}

		public void setZjhUserPlace(ArrayList<ZJHUserPlace> zjhUserPlace) {
			this.zjhUserPlace = zjhUserPlace;
		}

		public int getZhuangIndex() {
			return zhuangIndex;
		}

		public void setZhuangIndex(int zhuangIndex) {
			this.zhuangIndex = zhuangIndex;
		}

		public int getLun() {
			return lun;
		}

		public void setLun(int lun) {
			this.lun = lun;
		}

		public int getCurrentIndex() {
			return currentIndex;
		}

		public void setCurrentIndex(int currentIndex) {
			this.currentIndex = currentIndex;
		}

		public boolean isStart() {
			return isStart;
		}

		public void setStart(boolean isStart) {
			this.isStart = isStart;
		}

		public long getOperationTime() {
			return operationTime;
		}

		public void setOperationTime(long operationTime) {
			this.operationTime = operationTime;
		}

		private void onOperationStart() {
	        operationTime = System.currentTimeMillis();
	        //syncOptTime();
	    }
		 public void syncOptTime() {
		        room.sendMessage(getSyncOptTime());
		    }
		 
		  private SyncOptTime getSyncOptTime() {
		        int leftTime = 0;
		        if (operationTime > 0) {
		            leftTime = (int) (
		                    rules.getShouTimeMillisecond() - (System.currentTimeMillis() - operationTime)
		            );
		        }
		        return new SyncOptTime(currentIndex, leftTime);
		    }
		  
		public void startNext(ZJHSceneUser user) {
		        faPai(true,user);
		        log.debug("发牌完毕！{}", this);
		}
		/**
		 * 发牌
		 *@param isSendMessage
		 *@param user
		 *@return
		 * 2017年7月11日
		 */
		@SuppressWarnings("null")
		private void faPai(boolean isSendMessage,ZJHSceneUser user) {
			FaInfo faInfo = new FaInfo();
			int[] indexs = new int[zjhUserPlace.size()];
			if(isSendMessage){
				for (int i = 0; i < zjhUserPlace.size(); i++) {
					if(!zjhUserPlace.get(i).isOffLine()){
						int locationIndex = zjhUserPlace.get(i).getLocationIndex();
						indexs[i] = locationIndex;
					}
				}
				faInfo.setUserNum(indexs.length);
				faInfo.setUserIndex(indexs);
				faInfo.setZhuangIndex(zhuangIndex);
				sendMessage(currentIndex,faInfo);
				sendMessage(currentIndex,new RoomInfoPoker(indexs, diFen));
				
				sendMessage(currentIndex,new OperationNext(currentIndex, maxZhu));
			}
			  onOperationStart();//开始計時
		}
		 public void sendMessage(int locationIndex, Message msg) {
		        room.sendMessage(locationIndex, msg);
		    }
		 /**
		  * 操作
		  *@param msg
		  *@param user
		  *@return
		  * 2017年7月7日
		  */
		public void Operation(ZJHOperation msg, ZJHSceneUser user) {
			
				switch (msg.getOpt()) {
				case 0:
					selectPai(user.getLocationIndex());
					break;
				case 1:
					qiPai(user);	
					break;
				case 2:
					genZhu(user);
					break;
				default:
					break;
				}
			
			
		}
		/**
		 * 弃牌
		 *@param locationIndex
		 *@return
		 * 2017年7月7日
		 */
		public void qiPai(ZJHSceneUser user) {
			//ArrayList<Poker> shouPaiList = zjhUserPlace[user.getLocationIndex()].getShouPaiList();
			if(user.getLocationIndex() != currentIndex){
				zjhUserPlace.get(user.getLocationIndex()).setQi(true);
				room.sendMessage(new QiPokerResult(user.getLocationIndex()));
			}else{
				zjhUserPlace.get(user.getLocationIndex()).setQi(true);
				room.sendMessage(new QiPokerResult(user.getLocationIndex()));
				goNext();
				room.sendMessage(new OperationNext(currentIndex,zhuNo));
			}
//			int[] shouPaiNum = ZJHUserPlace.getShouPaiNum(shouPaiList);
//			room.oneChapter(this,user,shouPaiNum);
			
		}
		/**
		 * 看牌
		 *@param locationIndex
		 *@return
		 * 2017年7月7日
		 */
		public void selectPai(int locationIndex) {
			if(lun >10 ){
				throw new RuntimeException("严重错误，轮数达到上限");
			}
			zjhUserPlace.get(locationIndex).setKan(true);
			String pokerType = zjhUserPlace.get(locationIndex).getPokerType().getName();
			ArrayList<Poker> shouPaiList = zjhUserPlace.get(locationIndex).getShouPaiList();
			int[] shouPaiNum = ZJHUserPlace.getShouPaiNum(shouPaiList);
			room.sendMessage(locationIndex,new SelectPokerResult(shouPaiNum,pokerType,locationIndex),null);
		}
		

		/**
		 * 检测当前所有有看牌的玩家
		 *@return
		 *@return
		 * 2017年7月10日
		 */
		public boolean getIsKan(int curIndex){
			boolean isKan = false;
			for (int i = zhuangIndex; i <= curIndex; i++) {
				 isKan = zjhUserPlace.get(i).isKan();	
			}
			return isKan;
		}
		/**
		 * 跟注
		 *@param user
		 *@return
		 * 2017年7月7日
		 */
		public void genZhu(ZJHSceneUser user) {
			if(lun >10 ){
				throw new RuntimeException("严重错误，轮数达到上限");
			}
			if(zjhUserPlace.get(user.getLocationIndex()).getScore() <= 0){
				throw new RuntimeException("严重错误，跟注失败，当前玩家分值为0");
			}
			int MaxZhu = user.getRoom().getConfig().getInt(ZJHConfig.DANZHU)*5;
//			if(!(user.getLocationIndex() == zhuangIndex && lun == 1)){
//				boolean isKan = getIsKan(user.getLocationIndex());
//					if(isKan == true){
//						zhuNo = maxZhu;
//						maxZhu = maxZhu*2;
//					}
//			}
			boolean kan = zjhUserPlace.get(user.getLocationIndex()).isKan();
			if(kan){
				maxZhu = zhuNo*2;
			}
			sumScore += maxZhu;
			zjhUserPlace.get(user.getLocationIndex()).genZhu(maxZhu,MaxZhu);
			currentIndex = user.getLocationIndex();
			room.sendMessage(new GenZhuResult(user.getLocationIndex()));
			room.sendMessage(new DelScoreResult(user.getLocationIndex(),maxZhu));
			goNext();
			room.sendMessage(new OperationNext(currentIndex,zhuNo));
			
		}
		/**
		 * 加注
		 *@param user
		 *@param zhu
		 *@return
		 * 2017年7月10日
		 */
		@SuppressWarnings("unused")
		public void jiaZhu(ZJHSceneUser user,int zhu){
			maxZhu = zhu;
			if(lun >10 ){
				throw new RuntimeException("严重错误，轮数达到上限");
			}
			if(zhu<maxZhu){
				throw new RuntimeException("严重错误，加注失败，加注数小于当前注数！maxZhu:" + maxZhu + ",zhu:" + zhu);
			}
			if(zjhUserPlace.get(user.getLocationIndex()).getScore() <= 0){
				throw new RuntimeException("严重错误，加注失败，当前玩家分值为0");
			}
			int MaxZhu = 0;
			if(zjhUserPlace.get(user.getLocationIndex()).isKan()){//是否看牌
			 MaxZhu = user.getRoom().getConfig().getInt(ZJHConfig.DANZHU)*5*2;
			 zhuNo = maxZhu/2;
			}else{
				MaxZhu = user.getRoom().getConfig().getInt(ZJHConfig.DANZHU)*5;
				zhuNo = maxZhu;
			}
			sumScore += maxZhu;
			zjhUserPlace.get(user.getLocationIndex()).jiaZhu(maxZhu,MaxZhu);
			currentIndex = user.getLocationIndex();
			room.sendMessage(new AddZhuResult(user.getLocationIndex()));
			room.sendMessage(new DelScoreResult(user.getLocationIndex(),maxZhu));
			goNext();
			room.sendMessage(new OperationNext(currentIndex,zhuNo));
		}
		/**
		 * 获取牌局内没有弃牌，输和出局的玩家
		 *@return
		 *@return
		 * 2017年7月12日
		 */
		public int getUserNum(){
			int userNum= 0;
			for (int i = 0; i < zjhUserPlace.size(); i++) {
				if(!(zjhUserPlace.get(i).isShu() && zjhUserPlace.get(i).isQi())){
					userNum++;
				}
			}
			return userNum;
		}
		
		/**
		 * 比牌
		 *@param user
		 *@param index
		 *@return
		 * 2017年7月10日
		 */
		public void comparePoker(ZJHSceneUser user,int index){
			
			int subScore = 0;
			
			
			ArrayList<Poker> otherPokers = zjhUserPlace.get(index).getShouPaiList();//被比牌玩家
			ZJHPai zjhPai = zjhUserPlace.get(index).getAllPai().get(chapterNums);
			int result = zjhPai.compareWeight(otherPokers);//比较牌型
			
			if(getUserNum() > 2){
				subScore = zjhUserPlace.get(user.getLocationIndex()).subScore(maxZhu);
			}else{
				 subScore = zjhUserPlace.get(user.getLocationIndex()).subScore(maxZhu*2);
			}
			room.sendMessage(new DelScoreResult(user.getLocationIndex(),subScore));
			//出局
			if(result > 1){
				 zjhUserPlace.get(index).setShu(true);
				 room.sendMessage(new ComparePokerResult(user.getLocationIndex(), index));
				 winIndex = user.getLocationIndex();
			}else{
				zjhUserPlace.get(user.getLocationIndex()).setShu(true);;
				room.sendMessage(new ComparePokerResult(index, user.getLocationIndex()));
				 winIndex = index;
			}
			
			boolean zero = scoreIsZero();
			if(zero || getUserNum() <= 2){//房间结束
				gameOver();
				}
			}
		
		/**
		 *一局结束
		 *@return
		 * 2017年7月12日
		 */
		public void gameOver(){
			zjhUserPlace.get(winIndex).endAddScore(sumScore);//赢家加分
			for (int i = 0; i < zjhUserPlace.size(); i++) {
				zjhUserPlace.get(i).addZJHPai();
			}
			zjhUserPlace.get(winIndex).addWinCount();
			roomEnd();
		}
		
		
		/**
		 * 检查是否有玩家分数为0
		 *@return
		 *@return
		 * 2017年7月11日
		 */
		public boolean scoreIsZero(){
			for (int i = 0; i < zjhUserPlace.size(); i++) {
				if(zjhUserPlace.get(i).getScore() == 0){
					return true;
				}
			}
			return false;
		}
		/**
		 * 房间结束
		 *
		 *@return
		 * 2017年7月11日
		 */
		public void roomEnd(){
			chapterNums++;
			room.setUserPlases(zjhUserPlace);
			room.endChapter(this);
			
		}
		
		public int getSumScore() {
			return sumScore;
		}

		public void setSumScore(int sumScore) {
			this.sumScore = sumScore;
		}

		public int getMaxZhu() {
			return maxZhu;
		}

		public void setMaxZhu(int maxZhu) {
			this.maxZhu = maxZhu;
		}

		
}
