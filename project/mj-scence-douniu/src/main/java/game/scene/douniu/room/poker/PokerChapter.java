package game.scene.douniu.room.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.net.message.Message;

import Douniu.data.ComparePai;
import Douniu.data.DouniuConfig;
import Douniu.data.DouniuEndResult;
import Douniu.data.DouniuUserPlace;
import game.boss.poker.dao.entity.TbPkRoomDO;
import game.boss.poker.dao.entity.TbPkRoomUserDO;
import game.douniu.scene.msg.ChapterUserMsg;
import game.douniu.scene.msg.DouniuChapterEndMsg;
import game.douniu.scene.msg.DouniuRecordRoomMsg;
import game.douniu.scene.msg.DouniuRecordUserRoomMsg;
import game.scene.douniu.room.DouniuRoom;
import game.scene.douniu.room.DouniuRoomInfo;
import game.scene.douniu.room.DouniuSceneUser;
import game.scene.douniu.room.poker.rules.Rules;
import game.scene.douniu.services.DbService;
import mj.data.UserPlace;
import mj.data.douniu.AgariUtils;
import mj.data.douniu.ConvertUtil;
import mj.data.poker.douniu.DouniuPai;
import mj.data.poker.douniu.DouniuPaiType;
import mj.net.message.game.douniu.CompareResult;
import mj.net.message.game.douniu.DouniuBiCardRet;
import mj.net.message.game.douniu.DouniuChapterMsg;
import mj.net.message.game.douniu.DouniuFaPai;
import mj.net.message.game.douniu.DouniuFaPaiRet;
import mj.net.message.game.douniu.DouniuGameChapterEnd;
import mj.net.message.game.douniu.DouniuOnePai;
import mj.net.message.game.douniu.DouniuOutRet;
import mj.net.message.game.douniu.DouniuShu;
import mj.net.message.game.douniu.DouniuUserPlaceMsg;
import mj.net.message.game.douniu.DouniuZhuRet;
import mj.net.message.game.douniu.JingJiResult;
import mj.net.message.game.douniu.QiangZhuangRet;
import mj.net.message.game.douniu.ScoreInfo;
import mj.net.message.game.douniu.SyncDouniuTime;

/**
 * 一局纸牌的信息
 * @author zuoge85@gmail.com on 16/10/17.
 */
public class PokerChapter {

	// OUT:亮牌,OUT_OK:亮牌成功,X_NIU:小牛牛,Z_NIU:炸弹牛,N_NIU:牛牛,M_NIU:没牛
	private static final String OPT_XIAZHU = "OPT_XIAZHU";
	private static final String OPT_QIPAI = "OPT_QIPAI";
	private static final String OPT_GENZHU = "OPT_GENZHU";
	private static final String OPT_QIANGZHUANG = "OPT_QIANGZHUANG";
	private static final String OPT_LIANGPAI = "OPT_LIANGPAI";
	public static final int USER_NUMS = 6;
	protected static final Logger log = LoggerFactory
			.getLogger(PokerChapter.class);
	private DouniuUserPlace[] userPlaces;
	private ArrayList<ComparePai> comparePai; // 存取亮牌玩家牌的类型以及手牌
//	private ArrayList<DouniuUserPlace> douniuUserPlaces;
	private DouniuEndResult endResult;
	private DouniuRoom room;
	protected DouniuRoomInfo roomInfo;
	private DouniuPaiType douniuType;
	
	@Autowired
	private DbService dbService;
	/**
	 * 第X手 默认0开始
	 */
	private int shouIndex = 0;
	/**
	 * 操作开始时间
	 */
	private long operationTime = 0;
	/**
	 * 当前打牌的人
	 */
	private int currentIndex = 0;
	private DouniuFaPai douniuFaPai;
	private int diFen = 0; // 低分
	private int sumScore; // 总低分
	private int chapterNums;// 局数, 0开始
	private int quanIndex;// 圈index 0 ,1 ,2 ,3 , 4 ,5北 逆时针顺序？？？？
	private int zhuangIndex;// 庄index 轮庄，或者抢庄或者 ？？？？
	private boolean isStart = false;
	private DouniuGameChapterEnd gameChapterEnd;
	private boolean isBet; // 是否要押注
	private Rules rules;
	private PaiPool paiPool;
	private int winIndex = -1;//赢家位置
	private boolean isQiangzhuang ; //是抢庄还是随机庄
	private int qiangzhuangCount = 0 ;
	private int xiazhuCount = 0 ; 
	private int liangPaiCount = 0 ; 
	private int userCount = 0 ;
	private int totalZhu = 0 ;  // 桌面上所有的人下注的总数。
	private int lastJiaZhuIndex = -1 ; //最后一个加注人的位置。
	private int currentZhu = 0 ;// 当前的注数。
	private int readyCount = 0; //开始准备玩家的次数
	private int joinRoomCount = 0 ; //  加入房间的人数
	/*
	 * 构造方法创建函数
	 */
	public PokerChapter(DouniuRoom room, String rulesName,boolean isQiangZhuang, int userNum) {
		this.rules = Rules.createRules(rulesName, room.getConfig());
		this.paiPool = new PaiPool(this.rules);
		this.room = room;
		userPlaces = new DouniuUserPlace[userNum];
	     	this.isQiangzhuang = isQiangZhuang;
	     	for(int i=0; i<userPlaces.length; i++){
	     		userPlaces[i] = new DouniuUserPlace();
				userPlaces[i].setLocationIndex(i);	
				userPlaces[i].setUserStatus(-1);
	     	}	
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	private void clear() {
        paiPool.clear();
        for(DouniuUserPlace u : userPlaces) {
            u.clear();
        }
    }

    /**
     * 开始发牌
     */
	public void start() {
			this.rules.rest();
			isStart = true;
			paiPool.start();
			/*this.userCount = 0 ; 
			this.totalZhu = 0 ; 
			this.joinRoomCount=0;*/
		   for(int i = 0; i < readyCount; i++){
			 DouniuUserPlace userPlace = userPlaces[i];
			 userPlace.setQiangZhuangWeight(0);
			 if(!(userPlace.isOffLine()) && userPlace.getUserStatus() > -1){
				 this.userCount ++ ;
				 userPlace.setScore(0);
				 userPlace.setUserStatus(1);
				 userPlace.setWinlocationIndex(-1);
				 userPlace.setTotalZhu(0);
				 userPlace.setZhuNum(0);
				 paiPool.faPai(userPlace.getLocationIndex(), userPlace,rules.getInitFaPaiCount()); //开始发牌
			 	}	
			}
		  this.readyCount = 0;	
			/*
			 * 发牌完毕！
			 */ 
		   if(rules.isQingZhuang()){
			   if(isQiangzhuang){
					/*
					 * 随机庄的时候，进行随机生成。
					 */
					Random random = new Random();
					int randomZhuangIndex = random.nextInt(this.userCount);
					int validateUserCount = 0 ; 
					for(int i =0 ; i < USER_NUMS; i++){
						DouniuUserPlace  userPlace = userPlaces[i];
						if(userPlace.getUserStatus() == 1 ){
							if(randomZhuangIndex == validateUserCount){
								zhuangIndex = userPlace.getLocationIndex();
								break;
							}
							validateUserCount ++;
						 userPlace.setScore(0);
						 paiPool.faPai(userPlace.getLocationIndex(), userPlace,rules.getInitFaPaiCount()); //开始发牌
						 }	
						 userPlace.setZhuNum(0);
					}
					//发牌时判断随机庄，如果为true ,则为随机庄，发送随机庄的完位置， 否则继续下一步
					room.sendMessage(new QiangZhuangRet(zhuangIndex)); //发送给前段随机庄的位置
					/*
					 * 发牌完毕！
					 */ 
					 changeCurrentIndex(zhuangIndex);
				}
			  
		   }
	}

	private void changeCurrentIndex(int index) {
		this.currentIndex = index;
		shouIndex++;
	}

	public int getLeftChapterNums() {
		return room.getConfig().getInt(DouniuConfig.CHAPTER_MAX) - chapterNums;
	}
	/*
	 * 开始游戏发送信息
	 */
	public DouniuChapterMsg toMessage(int myLocationIndex) {
		DouniuChapterMsg m = new DouniuChapterMsg();
		m.setChapterNumsMax(this.room.getConfig().getInt(DouniuConfig.CHAPTER_MAX));
		m.setChapterNums(chapterNums);
		m.setQuanIndex(quanIndex);
		//m.setZhuangIndex(zhuangIndex);
		//for (int i = 0; i < userCount; i++) {  //这个地方有问题
			DouniuUserPlace userPlace = userPlaces[myLocationIndex]; 
			DouniuUserPlaceMsg msg= userPlace.toMessage(userPlace.getLocationIndex() == myLocationIndex, shouIndex);
			System.out.println("msg ++++ "+ msg.getShouPaiLen());
			m.addUserPlace(msg);
		//} 
		    m.setCurrentIndex(currentIndex);
		if (douniuFaPai != null && douniuFaPai.getIndex() == myLocationIndex) {
	//		m.setDouniuFaPai(douniuFaPai);
		}

		if (operationTime > 0) {
	//		m.setSyncDouniuTime(getSyncOptTime());  这个是同步的消息，前段遇BUG注销
		}
		if (gameChapterEnd != null) {
	// 		m.setDouniugameChapterEnd(gameChapterEnd);
		}
		
		return m;
	}
	public void startNext() {
		faPai();
		log.debug("发牌完毕！ {}", this);
	}

	/*
	 * 同步操作
	 */
	private SyncDouniuTime getSyncOptTime() {
		int leftTime = 0;
		if (operationTime > 0) {
			leftTime = (int) (rules.getShouTimeMillisecond() - (System
					.currentTimeMillis() - operationTime));
		}
		return new SyncDouniuTime(currentIndex, leftTime);
		
	}

	/*
	 * 获取斗牛规则
	 */
	public Rules getRules() {
		return rules;
	}
	public boolean isBet() { // 押注
		return isBet;
	}
	public void checkBet() {
		isBet = this.rules.getUserNum() >= 2;
	}

	/**
	 * 开始发牌的信息
	 * @param locationIndex
	 * @param opt
	 * @param pai
	 */

	public void faPai() {
		douniuFaPai = new DouniuFaPai();
		DouniuPai pai = paiPool.getFreePai();
		System.out.println(pai.getName());
		douniuFaPai.setIndex(currentIndex);
		DouniuUserPlace userPlace = userPlaces[currentIndex];
		/*while(true){
			if(userPlace.getShouPaiList().contains(pai)){
				pai=paiPool.getFreePai();  //加个条件重复牌的信息，继续发牌
				continue;
			}else{*/
				userPlace.changeFa(pai); 
				//根据情况发送消息，初始化不用，因为后面同步场景会同步操作到客户端
		        syncHidePai(pai);
				onOperationStart();
			/*	break;
			}
		}     */
	}
	
	 /**
     *   s对其他用户隐藏牌的同步方式
     */
	public void syncHidePai(DouniuPai ... pais) {
        if (pais.length > 0  ) {  
        	ArrayList<DouniuOnePai> list =new ArrayList<DouniuOnePai>(); 
            ArrayList<DouniuOnePai> list2 =new ArrayList<DouniuOnePai>();  
        	for(int i = 0 ; i < pais.length; i++){
        		DouniuPai pai = pais[i];
	        	DouniuOnePai onePai =new DouniuOnePai();	
	            onePai.setPokerNum(pai.getPokerNum().getNum());
	            onePai.setPokerSuit(pai.getPokerType().getSize()); 
	            onePai.setPokerValue(pai.getPokerValue()); 
	            list.add(onePai);
	            DouniuOnePai onePai2 =new DouniuOnePai();	
	            onePai2.setPokerNum(-1);
	            onePai2.setPokerSuit(-1); 
	            onePai2.setPokerValue(-1); 
	            list2.add(onePai2);
	          
        	}
        	for( DouniuOnePai one : list){
        		System.out.println("发第五张牌的时候的信息：： "+ one.getPokerNum());
        	}
        	
            room.sendMessage(currentIndex,
                     new DouniuFaPaiRet(currentIndex, list),
                     new DouniuFaPaiRet(currentIndex,  list2)
            );
        }
    }
	
    /**
     *  操作类型，下住 ，跟住， 弃牌,抢庄 .
     */
	public void Operation(DouniuShu msg, DouniuSceneUser user) {
		user.setStart(false); //初始化这个属性只在投票时用
		String opt = msg.getOpt();
		switch (opt) {
			case OPT_XIAZHU:// 下注
				xiaZHU(msg, user);
				break;
			case OPT_QIPAI: // 弃牌
				qiPai(msg, user);	
				break;
			case OPT_GENZHU:  // 跟注
				genZhu(msg, user);
				break;
			case OPT_QIANGZHUANG: // 抢庄
				qiangzhuang(msg, user);
				break;
			case OPT_LIANGPAI: // 亮牌
				liangPai(msg,user);
				break;
			default:
				break;
		}
	}
//	/*
//	 * 模式： 随机庄 返回随机庄位置
//	 */
//	private void suiJiZhuang(DouniuShu msg, DouniuSceneUser user){
//		
//	}
	
	
	/*
	 * 都亮牌以后将结果返回给前端。
	 */
		private void liangPai(DouniuShu msg,DouniuSceneUser user){
			this.liangPaiCount ++;			
			int totalCount = 0 ;   //庄家一局分 
			int winCountNum = 0;  //闲家一局分
			if(this.liangPaiCount >= this.userCount){   //这个判断有问题
				/*
				 * 比较五个人的手牌，将所有的情况都封装起来，返回给客户端。
				 * */
				DouniuUserPlace zhuangUserPlace = userPlaces[zhuangIndex];
				
				ArrayList<DouniuPai> shouPai = zhuangUserPlace.getShouPaiList();
				DouniuPaiType  zhuangNiuType =  AgariUtils.getNiuType(shouPai);
				  System.out.println("牛牛的类型int值：："+ zhuangNiuType.getIndex());
				DouniuGameChapterEnd chapterEnd = new DouniuGameChapterEnd();
				chapterEnd.setZhuangIndex(zhuangIndex);
				ArrayList <CompareResult> compareResultList =new ArrayList <CompareResult>();
				CompareResult  zhuangResult = new CompareResult();
				int res=zhuangNiuType.getIndex();
				zhuangResult.setPaiType(res);
				zhuangResult.setZhuang(true);
				zhuangResult.setLocationIndex(zhuangIndex);
				zhuangResult.setPaiList(ConvertUtil.convertToOnePai(shouPai));
				
				for(int i = 0 ; i < userPlaces.length ; i++){
					DouniuUserPlace userPlace = userPlaces[i];
					if(userPlace.getUserStatus() == 1  && i != zhuangIndex){
						/*
						 * 判断每个闲家属于牛几。
						 * 将结果封装起来。
						 */
						CompareResult  result = new CompareResult();
						result.setZhuang(false);
						result.setLocationIndex(userPlace.getLocationIndex());
						DouniuPaiType currentPaiType = AgariUtils.getNiuType(userPlace.getShouPaiList());
						       System.out.println("闲家的牌型：："+ currentPaiType.getName());
						result.setPaiType(currentPaiType.getIndex());						
						//如果他们都有牛的时候
						if(currentPaiType.getIndex() > zhuangNiuType.getIndex()){
							winCountNum = currentPaiType.getDoubleCount() *(userPlace.getZhuNum() * zhuangUserPlace.getZhuNum());
							score(userPlace.getLocationIndex() , winCountNum);  //闲家位置分
							score2(zhuangIndex, -winCountNum);  //庄家输的分
						}else if(currentPaiType.getIndex() == zhuangNiuType.getIndex()){
							//再比较一次
							if(AgariUtils.compareSuit(shouPai, userPlace.getShouPaiList()) > 0){
								winCountNum = currentPaiType.getDoubleCount() *(userPlace.getZhuNum() * zhuangUserPlace.getZhuNum());
								score(userPlace.getLocationIndex() , winCountNum); // 闲家赢的分 
								score2(zhuangIndex, -winCountNum);  //庄输的分
							}else{
								winCountNum = -zhuangNiuType.getDoubleCount() *(userPlace.getZhuNum() * zhuangUserPlace.getZhuNum());
								score(userPlace.getLocationIndex() , winCountNum); // 闲家输的分 
								score2(zhuangIndex, -winCountNum);  //庄家赢的分
							}
						}else{
							winCountNum = -zhuangNiuType.getDoubleCount() *(userPlace.getZhuNum() * zhuangUserPlace.getZhuNum());
							score(userPlace.getLocationIndex() , winCountNum); // 闲家输的分 
							score2(zhuangIndex, -winCountNum);  //庄家赢的分  
						 }
						int xScores= userPlaces[i].getScore();
						System.out.println("闲家的分数：：："+xScores);
						totalCount = totalCount + winCountNum;
					//	result.setWinCount(winCountNum);
						
						result.setWinCountNum(winCountNum);
						result.setScores(String.valueOf(xScores));
						
						result.setPaiList(ConvertUtil.convertToOnePai(userPlace.getShouPaiList()));		
						result.setCurrentCount(chapterNums);
						compareResultList.add(result);
//						userPlace.clear();
					}
				}
				zhuangResult.setWinCount(-totalCount);
				zhuangResult.setScores(String.valueOf(userPlaces[zhuangIndex].getScore())); //转化为String
				System.out.println("庄家的总分：："+userPlaces[zhuangIndex].getScore() );
				zhuangResult.setWinCountNum(-totalCount);  //存取一局庄家的分数
				zhuangResult.setCurrentCount(chapterNums); //存取当前局数
				compareResultList.add(zhuangResult);
				chapterEnd.setCompareResultList(compareResultList);
//				userPlaces[zhuangIndex].clear();
				/*
				 * 发送消息给全部的在线玩家。
				 * */
				room.sendMessage(chapterEnd);
	    	    roomEnd();  //房间结束
	    	   
			}
			
		}
		
		
		/**
		 * 计算总分
		 * @param index
		 * @param initScore
		 */
		public void score(int index, int initScore){
			int score =0;
			score +=initScore;
			userPlaces[index].setScore(score); //添加闲家的每局得分
		}
		public void score2(int zhuangIndex,int zhuangScore){
			int score =0;
			score += zhuangScore;
			userPlaces[zhuangIndex].setScore(score); //添加庄家的每局得分
		}
		

	//抢庄操作
	private void qiangzhuang(DouniuShu msg,DouniuSceneUser user) {
		
		if(!this.isQiangzhuang){ //抢庄为flase
			this.qiangzhuangCount ++;
			/*
			 * 将对应的位置上的用户设置抢庄的权限值。
			 */
			DouniuUserPlace[] userplace = this.getUserPlaces();
			for(int i = 0 ; i < userplace.length ; i++){
				if(userplace[i] != null && userplace[i].getUserStatus() == 1 
						&& userplace[i].getLocationIndex() == user.getLocationIndex()){
					userplace[i].setQiangZhuangWeight(msg.getZhuNum());
				}
			}
			//给所有的在线玩家发送消息.
//			if(msg.getFlage()){
				sendSynOptMsg(msg.getOpt(), msg.getZhuNum(), user.getLocationIndex());
//			}
			//如果都已经
				System.out.println("qiangzhuangCount"+qiangzhuangCount);
			if(this.qiangzhuangCount == this.userCount){
				//获得最大抢庄的数字
				int maxCount = 0 ; 
				//
				int maxNum = 1 ;
				for(int i =0 ;i < userplace.length ; i++){
					DouniuUserPlace userplaceTmp = userplace[i];
					if(userplaceTmp.getUserStatus() == 1){
						if(userplaceTmp.getQiangZhuangWeight() > maxNum){
							maxNum = userplaceTmp.getQiangZhuangWeight();
							maxCount = 1;
						}else if(userplaceTmp.getQiangZhuangWeight() == maxNum){
							maxCount ++;
						}
					}
				}
				//找到对应的记录，封装到list中。
				ArrayList <DouniuUserPlace>  maxCountList = new ArrayList<DouniuUserPlace>();
				for(int i =0 ;i < userplace.length ; i++){
					DouniuUserPlace userplaceTmp = userplace[i];
					if(userplaceTmp.getUserStatus() == 1 && userplaceTmp.getQiangZhuangWeight() == maxNum){
						maxCountList.add(userplaceTmp);
					}
				}
				if(maxCount == 1 && maxCountList.size() > 0 ){
					// 庄就是list 中的第一个数据。
					this.zhuangIndex = maxCountList.get(0).getLocationIndex();
					userplace[zhuangIndex].setZhuNum(maxCountList.get(0).getQiangZhuangWeight());
				}else{
					//给赢得最多的。
					Random  random = new Random();
					int zhuangIndexTmp  = random.nextInt(maxCount);
					int tmpZhuangIndex = -1 ; 
					tmpZhuangIndex = maxCountList.get(zhuangIndexTmp).getLocationIndex();
					this.zhuangIndex = tmpZhuangIndex;
					userplace[zhuangIndex].setZhuNum(maxCountList.get(zhuangIndexTmp).getQiangZhuangWeight());
				}
				room.sendMessage(new QiangZhuangRet(this.zhuangIndex));;
			}
		}
	}
	
	//发送同步消息。
	private void sendSynOptMsg(String opt_name , int value,int locationIndex ){
		room.sendMessage(locationIndex, 
				new DouniuZhuRet(opt_name,locationIndex, value), 
				new DouniuZhuRet(opt_name,locationIndex, value));
	}

	// 跟注的操作，暂时不用
	private void genZhu(DouniuShu shu, DouniuSceneUser user) {
		// TODO Auto-generated method stub
		DouniuUserPlace   currentUserPlace = userPlaces[user.getLocationIndex()];
		currentUserPlace.setZhuNum(currentZhu);
		this.xiazhuCount ++ ;
		sendSynOptMsg(shu.getOpt(), -1 , user.getLocationIndex());
		this.totalZhu += this.currentZhu;
		if(this.xiazhuCount >= this.userCount ){
			//查找下一个有效的用户
			int nextLocationIndex = getNextValidateUser();
			//如果下一个有效的用户的index 是lastJiaZhuIndex 的话。（说明需要发牌了）
			if(nextLocationIndex > - 1 && nextLocationIndex == lastJiaZhuIndex){
				//清理掉现在处理的数据。和下注相关的数据。
				modifyXiaZhuInfo();
				//如果跟注的时候，牌的数量已经是五的时候
				if(currentUserPlace.getShouPaiList().size() < 5){
					//发牌
					faPaiToAllUser();
				}else{
					sendMessageToNext(true, true);
				}
			}else{
				sendMessageToNext(false,false);
			}
		}else{
			if(currentUserPlace.getShouPaiList().size() < 5){
				sendMessageToNext(true,false);
			}else{
				sendMessageToNext(true, true);
			}
		}
	}
	// 弃牌的操作暂时不用
	private void qiPai(DouniuShu shu ,DouniuSceneUser user) {
		// TODO Auto-generated method stub
		this.userCount -- ;
		int currLocationindex = user.getLocationIndex();
		userPlaces[currLocationindex].setUserStatus(2);
		sendSynOptMsg(shu.getOpt(), shu.getZhuNum(), user.getLocationIndex());		
	}
	
	//斗牛操作：下注
	private void xiaZHU(DouniuShu shu, DouniuSceneUser user ) {
		if(rules.isQingZhuang()){
			this.xiazhuCount ++ ; 
			DouniuUserPlace userPlace =  userPlaces[user.getLocationIndex()];
			userPlace.setZhuNum(shu.getZhuNum());
			sendSynOptMsg(shu.getOpt(), shu.getZhuNum(), user.getLocationIndex());
			/*
			 * 判断是否都下注了，如果都下注了的话就开始开牌。
			 */
			System.out.println("下注的次数：："+xiazhuCount );
			System.out.println(" 用户数减一:;"+userCount);
		 	if(this.xiazhuCount == this.userCount -1 ){
				/*
				 * 开始结束游戏。
				 * 将游戏结果发送给页面端。
				 * 每发完一张牌以后都需要将当前的.
				 */
				faPaiToAllUser();
				
			}
		}else{
			//在竞技场的时候。
			this.xiazhuCount ++ ;
			DouniuUserPlace   currentUserPlace = userPlaces[user.getLocationIndex()];
			int xiazhu = shu.getZhuNum();
			this.lastJiaZhuIndex  = user.getLocationIndex();
			this.currentZhu += xiazhu;
			this.totalZhu += this.currentZhu;
			sendSynOptMsg(shu.getOpt(), shu.getZhuNum(), user.getLocationIndex());
			currentUserPlace.setZhuNum(xiazhu);
			if(this.xiazhuCount >= this.userCount ){
				//查找下一个有效的用户
				int nextLocationIndex = getNextValidateUser();
				//如果下一个有效的用户的index 是lastJiaZhuIndex 的话。（说明需要发牌了）
				if(nextLocationIndex > - 1 && nextLocationIndex == lastJiaZhuIndex){
					//发牌
					//清理掉现在处理的数据。和下注相关的数据。
					modifyXiaZhuInfo();
					if(currentUserPlace.getShouPaiList().size() < 5){
						faPaiToAllUser();
					}else{
						//自定给装
						changeCurrentIndex(zhuangIndex);
						sendMessageToNext(true,true);
					}
				}else{
					sendMessageToNext(false,false);
				}
			}else{
				if(currentUserPlace.getShouPaiList().size() < 5){
					sendMessageToNext(true,false);
				}else{
					sendMessageToNext(true,true);
				}
			}
		}
		
	}
	/*
	 * 将一圈的压住信息清除到汇总记录中去。
	 */
	private void modifyXiaZhuInfo(){
		this.currentZhu = 0 ; 
		for(int i= 0 ; i < USER_NUMS; i++ ){
			DouniuUserPlace userPlace = userPlaces[i];
			if(userPlace.getUserStatus() > 0 ){
				userPlace.setTotalZhu(userPlace.getTotalZhu() + userPlace.getZhuNum());
				userPlace.setZhuNum(0);
			}
		}
	}
	
	
	/*
	 * 给下一个没有弃牌的用户发消息，告诉他可以渲染一面信息。
	 */
	private int getNextValidateUser() {
		int nextLocationIndex = - 1; 
		for(int i =currentIndex ; i < currentIndex + USER_NUMS ; i++){
			int nextIndex = (i+1)%USER_NUMS;
			if(userPlaces[nextIndex].getUserStatus() == 1){
				nextLocationIndex = nextIndex;
				break;
			}
		}
		return nextLocationIndex;
	}
	
	public void sendMessageToNext(boolean isXiaZhu, boolean isCompare){
		int nextLocationIndex = getNextValidateUser();
		if(nextLocationIndex != -1 ){
			for(DouniuSceneUser userInfo : room.getRoomInfo().getUsers()){
				if(userInfo != null &&  userInfo.getLocationIndex() == nextLocationIndex){
					//指定给下一位。
					userInfo.sendMessage(new DouniuOutRet(isXiaZhu, isCompare));
					changeCurrentIndex(nextLocationIndex);
				}
			}
		}
	}
	
	/*
	 * 给玩家一人发送一张牌
	 */
	public void faPaiToAllUser(){
		for(int i = 0 ; i < this.userCount ; i++){
			startNext(); //发下一张牌
			int currentIndex = (i+1) % USER_NUMS;
			changeCurrentIndex(currentIndex);
		}		
	}

	/***
	 * @param locationIndex
	 * @param msg
	 * @return
	 */
	public void biCard(int index, DouniuSceneUser user) {
		
		DouniuUserPlace userPlace = userPlaces[user.getLocationIndex()];
		DouniuUserPlace userPlaceOther = userPlaces[index];
		DouniuPaiType userNiuType =  AgariUtils.getNiuType(userPlace.getShouPaiList());
		DouniuPaiType otherNiuType = AgariUtils.getNiuType(userPlaceOther.getShouPaiList());
		int ret =  0 ; 
		if(userNiuType == otherNiuType){
			ret = AgariUtils.compareSuit(userPlace.getShouPaiList(), userPlaceOther.getShouPaiList());
		}else{
			if(userNiuType.getIndex() > otherNiuType.getIndex()){
				ret = 1;
			}else{
				ret = -1;
			}
		}
		/*
		 * 同步赢家的信息.
		 */
		int  winIndex = ret == 1 ? user.getLocationIndex(): index;
		room.sendMessage(new DouniuBiCardRet(user.getLocationIndex(), index,
				winIndex));
		/*
		 * 同步输家的信息。
		 */
		DouniuUserPlace lostUserPlace = null ; 
		if(ret == 1){
			lostUserPlace = userPlaceOther;
		}else{
			lostUserPlace = userPlace;
		}
		lostUserPlace.setUserStatus(2);
		lostUserPlace.setWinlocationIndex(winIndex);
		int totalValidataUserCount = 0 ;
		int lastWinLocationIndex= -1; 
		for(int i = 0 ; i < USER_NUMS; i++){
			if(userPlaces[i].getUserStatus() == 1 ){
				totalValidataUserCount ++;
				lastWinLocationIndex = userPlaces[i].getLocationIndex();
			}
		}
		/*
		 * 表示游戏已经结束。
		 * */
		if(totalValidataUserCount == 1){
			//先封装所有用户的分数。
			ArrayList<ScoreInfo> scoreInfoList = new ArrayList<ScoreInfo>();
			for(int i =0 ; i < USER_NUMS ; i++){
				DouniuUserPlace userPlaceTmp = userPlaces[i];
				if(userPlaceTmp != null && userPlaceTmp.getUserStatus() > 0 ){
					ScoreInfo scoreInfo = new ScoreInfo();
					scoreInfo.setLocationIndex(userPlaceTmp.getLocationIndex());
					scoreInfo.setInitScore(userPlaceTmp.getScore());
					scoreInfo.setWinScore(userPlaceTmp.getZhuNum() + userPlaceTmp.getTotalZhu());
					scoreInfoList.add(scoreInfo);
				}
			}
			
			for(DouniuSceneUser sceneUser : this.roomInfo.getUsers()){
				int indexTmp = sceneUser.getLocationIndex() ;
				DouniuUserPlace userPlaceTmp = userPlaces[indexTmp];
				if(userPlaceTmp != null &&  userPlaceTmp.getUserStatus() > 0 ){
					JingJiResult jingJiResult = new JingJiResult();
					
					jingJiResult.setWinCount(userPlaceTmp.getTotalZhu() + userPlaceTmp.getZhuNum());
					jingJiResult.setLocationIndex(indexTmp);
					int winIndexTmp = userPlaceTmp.getWinlocationIndex();
					
					if(winIndexTmp >- 1){
						ArrayList<DouniuOnePai>  douniuOneList = ConvertUtil.convertToOnePai(userPlaces[winIndexTmp].getShouPaiList());
						jingJiResult.setWinnerPaiList(douniuOneList);
					}else{
						jingJiResult.setWinnerPaiList(null);
					}
					jingJiResult.setWinnerLocationIndex(winIndexTmp);
					jingJiResult.setPaiType(AgariUtils.getNiuType(userPlaceTmp.getShouPaiList()));
					jingJiResult.setScoreInfoList(scoreInfoList);
					if(indexTmp == lastWinLocationIndex){
						jingJiResult.setLastWinner(true);
					}else{
						jingJiResult.setLastWinner(false);
					}
					sceneUser.sendMessage(jingJiResult);
				}
			}
			//进行算分，将输家的分都从自己的分中减去。
			
		}
		
//		int subScore = 0;
//		int result = 0;
//		for (int i = 0; i < userPlaces.length; i++) {
//			DouniuUserPlace userPlace = userPlaces[user.getLocationIndex()];
//			if (i != user.getLocationIndex()) {
//				DouniuUserPlace userPlaceOther = userPlaces[i];
//				// DouniuUserPlace.comparePoker(userPlace.getShouPai(),
//				// userPlaceOther.getShouPai());
//				if (AgariUtils.hasNiu((userPlace.getShouPai()))
//						&& AgariUtils.hasNiu((userPlaceOther.getShouPai()))) {
//					// 比牌的结果 1 是比牌人大 -1 被比派人大
//					result = AgariUtils.compareNiu(userPlace.getShouPai(),
//							userPlaceOther.getShouPai());
//					return result;
//				} else if (AgariUtils.hasNiu(userPlace.getShouPai())) {
//					if (!AgariUtils.hasNiu(userPlaceOther.getShouPai())) {
//						return 1; // 判断如果比派人有牛 被比牌人无牛 返回1
//					}
//				} else if (AgariUtils.hasNiu(userPlaceOther.getShouPai())) {
//					if (!AgariUtils.hasNiu(userPlace.getShouPai())) {
//						return -1; // 判断如果比派人有牛 被比牌人无牛 返回1
//					}
//				} else {
//					// 如果两个玩家都没牛
//					int user1 = AgariUtils.getValuesModeTen(userPlace
//							.getShouPai());
//					int user2 = AgariUtils.getValuesModeTen(userPlaceOther
//							.getShouPai());
//					if (user1 > user2) {
//						return 1;
//					} else if (user1 < user2) {
//						return -1;
//					} else { // 如果相等比花色
//						result = AgariUtils.compareSuit(userPlace.getShouPai(),
//								userPlaceOther.getShouPai());
//						return result;
//					}
//				}
//
//			}
//			// 获取比牌人的牌的类型
//			DouniuPaiType userType = AgariUtils.getNiuType(userPlace
//					.getShouPai());
//			ComparePai comPai = new ComparePai();
//			comPai.setLocationIndex(user.getLocationIndex());
//			comPai.setPayType(userType);
//			comPai.setPaiList(userPlace.getShouPai());
//			comparePai.add(comPai);
//			// 判断当前斗牛房间的人数
//			if (getUserNum() > 2) {
//				int score = userPlaces[user.getLocationIndex()]
//						.getZhuNum(); // 每个用户的下住数
//
//				if (userType.equals(douniuType.WuNiu)
//						|| userType.equals(douniuType.NiuDing)
//						|| userType.equals(douniuType.NiuTwo)
//						|| userType.equals(douniuType.NiuThree)
//						|| userType.equals(douniuType.NiuFour)
//						|| userType.equals(douniuType.NiuFive)
//						|| userType.equals(douniuType.NiuSix)) {
//					subScore = -score; // 该用户为牛0 ---- 牛6 一倍
//				} else if (userType.equals(douniuType.NiuSeven)
//						|| userType.equals(douniuType.NiuEight)
//						|| userType.equals(douniuType.NiuNine)) {
//					subScore = -score * 2; // 该用户为牛7 ---9 二倍
//				} else if (userType.equals(douniuType.NiuNiu)) {
//					subScore = -score * 3; // 该用户为牛牛 三倍
//				} else if (userType.equals(douniuType.ZhaDanNiu)
//						|| userType.equals(douniuType.NiuFiveSmallNiu)
//						|| userType.equals(douniuType.WuHuaNiu)) {
//					subScore = -score * 4; // 该用户为炸牛 ，花牛 ， 五小牛 四倍
//				}
//			}
//			room.sendMessage(new DouniuDelScoreResult(user.getLocationIndex(),
//					subScore));
//		}
//		/**
//		 * 判断庄赢，还是农家赢
//		 * 
//		 * @param locationIndex
//		 * @param msg
//		 */
//		if (result == 1) {
//			winIndex = user.getLocationIndex();
//			ArrayList<DouniuPai> winPai = userPlaces[winIndex].getShouPai();
//			String userType = AgariUtils.getNiuType(winPai).toString();
//			if (user.getUserId() == roomInfo.getCreateUserId()) {
//				userPlaces[winIndex].endAddScore(subScore); // 赢家加分
//				room.sendMessage(new DouniuBiCardRet(winIndex, userType, true));
//
//			} else {
//				userPlaces[winIndex].endAddScore(subScore);// 赢家加分
//				room.sendMessage(new DouniuBiCardRet(winIndex, userType, false));
//			}
//			gameOver();
//		}
//		return result;
	}

	/**
	 * 一局结束
	 *
	 * @return 2017年7月12日
	 */
	public void gameOver() {
//		for (int i = 0; i < douniuUserPlaces.size(); i++) {
//			douniuUserPlaces.get(i).addShouPai(pai);
//		}
//		douniuUserPlaces.get(winIndex).addWinCount();
		roomEnd();
	}

	/**
	 * 房间结束
	 *
	 * @return 2017年7月11日
	 */
	public void roomEnd() {
	  // 	chapterNums++;
		DouniuChapterEndMsg endMsg = new DouniuChapterEndMsg();
		List<ChapterUserMsg> userChapters = new ArrayList<ChapterUserMsg>();
		int[] scores = new int[USER_NUMS]; 
		for(int i = 0 ; i < USER_NUMS; i++){
			scores[i] = 0 ;
		}
		
		for (int i = 0; i < USER_NUMS; i++) {
			
			DouniuUserPlace userPlace = userPlaces[i];
			if(userPlace== null || userPlace.getUserStatus() != 1){
				continue;
			}
			ChapterUserMsg userMsg = new ChapterUserMsg();
	         int[][] pais = new int[5][];
			for(int j=0; j<userPlace.getShouPaiList().size(); j++){
				DouniuPai pai = userPlace.getShouPaiList().get(j);

				 pais[j]= new int [3];
				 pais[j][0] = pai.getPokerType().getSize();
				 pais[j][1] = pai.getPokerNum().getNum();
				 pais[j][2] = pai.getPokerValue();
				 System.out.println("牌的数结束的存牌：：：：：：++"+ pais[j][2]);
			}	
			if(pais!=null){
				userMsg.setPais(pais);
			}
			int curChapterScore = userPlace.getScore();
			scores[i] = curChapterScore;
			userMsg.setScore(curChapterScore);
			userMsg.setUserId(userPlace.getUserId());
			userChapters.add(userMsg);	
			userPlace.clear();
		}

		endMsg.setChapterUserMsgs(userChapters);
		room.endChapter(endMsg ,this);
		clear();
//		room.checkDelRoom();	
		System.out.println("roomINfo::"+ chapterNums);
		this.userCount = 0 ; 
		this.liangPaiCount=0;
		this.totalZhu = 0 ; 	
		this.qiangzhuangCount=0;
		this.xiazhuCount=0; 
		this.currentIndex=0;
		this.shouIndex=0;  
	}

	public void sendMessage(int locationIndex, Message msg) {
		room.sendMessage(locationIndex, msg);
	}

	private void onOperationStart() {
		operationTime = System.currentTimeMillis();
		syncOptTime();
	}

	public void syncOptTime() {
		room.sendMessage(getSyncOptTime());
	}

	public DouniuUserPlace[] getUserPlaces() {
		return userPlaces;
	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	public void setUserPlaces(DouniuUserPlace[] userPlaces) {
		this.userPlaces = userPlaces;
	}

	public int getChapterNums() {
		return chapterNums;
	}

	public void setChapterNums(int chapterNums) {
		this.chapterNums = chapterNums;
	}

	/**
	 * 获取牌局内的玩家
	 *
	 * @return
	 * @return 2017年7月12日
	 */
	public int getUserNum() {
		int userNum = 0;
		for (int i = 0; i < userPlaces.length; i++) {
			if(userPlaces[i].getUserStatus() ==1){
				userNum++;
			}
		}
		return userNum;
	}

	/***
	 * 操作类型暂时不用
	 */
	public void faPaiRet(int locationIndex, String opt, int pai2) {
	        
	}

	public void updateUser(DouniuSceneUser sceneUser) {
		DouniuUserPlace userPlace = userPlaces[sceneUser.getLocationIndex()];
		userPlace.setUserId(sceneUser.getUserId());
		userPlace.setUserName(sceneUser.getUserName());
	}

	public int getReadyCount() {
		return readyCount;
	}

	public void setReadyCount(int readyCount) {
		this.readyCount = readyCount;
	}
	
	public void addReadyCount() {
		 readyCount++;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getJoinRoomCount() {
		return joinRoomCount;
	}

	public void setJoinRoomCount(int joinRoomCount) {
		this.joinRoomCount = joinRoomCount;
	}
	
	public void addJoinRoomCount() {
		 joinRoomCount ++;
	}
	public void addChapterNumsCount() {
		chapterNums ++;
	}
 /**
  * 总战绩
  */
	public DouniuRecordRoomMsg roomBalance() {
		DouniuRecordRoomMsg roomMsg = new DouniuRecordRoomMsg();
		List<DouniuRecordUserRoomMsg> userRoomMsgs = new ArrayList<>();
		for (int i = 0;i < currentIndex;i++) {
			if(userPlaces[i]==null){
				continue;
			}
			DouniuRecordUserRoomMsg userRoomMsg = new DouniuRecordUserRoomMsg();
			userRoomMsg.setEndScore(userPlaces[i].getScore());
			userRoomMsg.setWinCount(-1);
			userRoomMsg.setRoomId(room.getRoomInfo().getRoomId());
			userRoomMsg.setUserId(userPlaces[i].getUserId());
			userRoomMsgs.add(userRoomMsg);
		}
		roomMsg.setUserRoomMsgs(userRoomMsgs);
		return roomMsg;
	}
}
