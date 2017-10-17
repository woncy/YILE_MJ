package game.scene.pdk.room.poker;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.mysql.fabric.xmlrpc.base.Array;

import game.scene.pdk.exception.ChuPaiException;
import game.scene.pdk.room.PdkSceneUser;
import mj.data.UserPlace;
import mj.data.poker.Poker;
import mj.data.poker.PokerUtil;
import mj.data.poker.pdk.PdkPai;
import mj.data.poker.pdk.PdkPoker;
import mj.data.poker.pdk.PdkPokerUtil;
import mj.data.poker.pdk.PdkUserOutPai;
import mj.data.poker.pdk.PdkUserPlace;
import mj.net.message.game.pdk.PdkGameChapterInfo;
import mj.net.message.game.pdk.PdkUserPlaceMsg;

/**
 * 
    * @ClassName: PokerChapter
    * @Description:牌局控制器
    * @author 13323659953@163.com
    * @date 2017年7月10日
    *
 */
public class PokerChapter {
	public static final int DEFAUL_USER_NUM = 3;// 默认4个人
	public static final int DEFAUL_PAI_NUM = 1;//默认一副牌
	public static final int HONGTAOSAN_INDEX = 10;// 红桃三索引
	
	
	public static Logger log = Logger.getLogger(PokerChapter.class);
	
	private int fistChupaiUserIndex;//第一个出牌人的位置
	private int currentChupaiUserIndex; //当前出牌人位置
	private int current;//  当场出牌人倒计时
	private Stack<PdkUserOutPai> outpais; // 所有打出牌的堆栈信息
	private PdkUserPlace[] users;
	boolean isStart;
	private int paiNum = -1;		//玩法为16或15张
	private int userNum = -1;		//此局的用户数量
	
	public PokerChapter(int userNum,int paiNum) {
		super();
		this.userNum = userNum;
		users = new PdkUserPlace[userNum];
		
		for (int i = 0; i < users.length; i++) {
			 users[i] = new PdkUserPlace(i);
			
		}
		
		this.paiNum = paiNum;
	}
	
	public void start(){
		fistChupaiUserIndex = 0;
		currentChupaiUserIndex = 0;
		
		List<PdkPoker> pokers = PdkPokerUtil.getPokersByNum(paiNum);
		PokerUtil.xiPai(pokers);
		
		for (int i = 0; i < paiNum; i++) {
			for (int j = 0; j < users.length; j++) {
				PdkPoker poker = pokers.get(i*users.length+j);
				
				/**
				 * 谁先拿到红桃三
				 */
				if(Poker.getIndexByPoker(poker) == HONGTAOSAN_INDEX){
					fistChupaiUserIndex = j;
					currentChupaiUserIndex = j;
				}
				
				users[j].addShouPai(poker);
			}
		}
		this.isStart = true;
	}
	
	public boolean chuPai(int chuPaiUserIndex,List<Integer> paiIndexs) throws ChuPaiException{
		ArrayList<Poker> pokers = PdkPokerUtil.getPokersByIndexs(paiIndexs);
		PdkPai nowPai = null;
		try{
			nowPai = new PdkPai(pokers);
		}catch (RuntimeException e) {
			log.error(e.getMessage());
			throw new ChuPaiException(ChuPaiException.MSG);
		}
		
		// 先检查有没有人报单;
		boolean isBaoDan = checkBaoDan();
		if(isBaoDan && paiIndexs.size()==1){
			users[chuPaiUserIndex].outBaoDanPai(pokers.get(0));
		}
		
		PdkPai prePai = outpais.peek().getOutPai();
		int comparePai = -2;
		try{
			comparePai = nowPai.compareTo(prePai);
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new ChuPaiException("pai.errorCompare");
		}
		if(comparePai<=0){
			return false;
		}
		users[chuPaiUserIndex].removeShouPai(pokers);
		PdkUserOutPai outPai = new PdkUserOutPai(chuPaiUserIndex, nowPai);
		outpais.add(outPai);							// 将打出的牌添加到堆栈
		users[chuPaiUserIndex].addOutPai(nowPai); 		//用户添加打出的牌
		boolean isWin = checkWin(chuPaiUserIndex);
		if(isWin){
			
		}
		return true;
	}
	
	private boolean checkBaoDan() {
		for (int i = 0; i < users.length; i++) {
			PdkUserPlace pdkUserPlace = users[i];
			if(pdkUserPlace.isBaoDan()){
				return true;
			}
			
		}
		return false;
	}

	/**
	 	* 
	    * @Title: goNextUser
	    * @Description: 去下一个用户
	    * @param     参数
	    * @return void    返回类型
	    * @throws
	 */
	public void goNextUser(){
		currentChupaiUserIndex ++;
		if(currentChupaiUserIndex == userNum){
			currentChupaiUserIndex = 0;
		}
	}

	/**
	 * 
	    * @Title: checkWin
	    * @Description: 检查出牌人是不是赢了
	    * @param @param chuPaiUserIndex    参数
	    * @return void    返回类型
	    * @throws
	 */
	private boolean checkWin(int chuPaiUserIndex) {
		return false;
	}

	
	
	// 以下是getter、setter
	public int getFistChupaiUserIndex() {
		return fistChupaiUserIndex;
	}
	
	public void setFistChupaiUserIndex(int fistChupaiUserIndex) {
		this.fistChupaiUserIndex = fistChupaiUserIndex;
	}
	
	public int getCurrentChupaiUserIndex() {
		return currentChupaiUserIndex;
	}
	
	public Stack<PdkUserOutPai> getOutpais() {
		return outpais;
	}
	public PdkUserPlace[] getUsers() {
		return users;
	}

	public PdkGameChapterInfo toMessage(PdkSceneUser user) {
		PdkGameChapterInfo info = new PdkGameChapterInfo();
		info.setCurrentChupaiUserIndex(currentChupaiUserIndex);
		info.setFistChupaiUserIndex(fistChupaiUserIndex);
		List<PdkUserPlaceMsg> userMsgs = new ArrayList<PdkUserPlaceMsg>();
		
		if(users!=null){
			for (int i = 0; i < users.length; i++) {
				PdkUserPlace userPlace = users[i];
				userMsgs.add(userPlace.toMessage(user.getLocationIndex()));
			}
		}
		
		info.setUserPlaces(userMsgs);
		return info;
	}
	
}
