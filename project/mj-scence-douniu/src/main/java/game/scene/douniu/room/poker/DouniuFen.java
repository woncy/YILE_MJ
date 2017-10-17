package game.scene.douniu.room.poker;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import mj.data.ChapterEndResult;
import mj.data.FanResult;
import mj.data.UserPaiInfo;
import mj.data.UserPlace;
import mj.data.poker.douniu.DouniuPaiType;
import Douniu.data.DouniuEndResult;
import Douniu.data.DouniuUserPaiInfo;
import Douniu.data.DouniuUserPlace;
import game.scene.douniu.room.DouniuSceneUser;

public class DouniuFen {/*
    private PokerChapter pokerChapter;
    private DouniuEndResult endResult;
    private DouniuPaiType douniuPaiType;	//赢牌玩家的牌型
    
	public DouniuFen(PokerChapter pokerChapter, DouniuPaiType douniuPaiType, int winIndex) {
		super();
		this.pokerChapter = pokerChapter;
	//	 pokerChapter.getUserPlaces();
		endResult =new DouniuEndResult();
		endResult.setIndexWin(winIndex);
		endResult.setZhuangIndex(pokerChapter.getCurrentIndex());
		endResult.setUserPaiInfos(
		//		 (DouniuUserPaiInfo[]) Stream.of(pokerChapter.getUserPlaces()).map(u -> new DouniuUserPaiInfo(
	                		pokerChapter.getRules().getAllPai(), pokerChapter.getUserPlaces(), u,
	                        u.getLocationIndex() == winIndex,
	                        u.getLocationIndex() == pokerChapter.getCurrentIndex()         
	                )).toArray(DouniuUserPaiInfo[]::new)
	        );
	}
	public DouniuEndResult compute() {
        
        if (endResult.isWin()) {
            DouniuUserPlace[] userPlaces = pokerChapter.getUserPlaces();

            DouniuUserPlace userPlace = userPlaces[endResult.getIndexWin()];

            DouniuUserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();

            computeUser(userPaiInfos[endResult.getIndexWin()], userPlace);
        }

        return endResult;
    } 
	private void computeUser(DouniuUserPaiInfo userPaiInfos, DouniuUserPlace userPlace) {
        FanResult[] fanResults = userPaiInfos.getFanResults();
        if (userPaiInfos.getFanResults() != null) {
            for (FanResult result : userPaiInfos.getFanResults()) {
                computeFanResult(result, endResult, userPlace, userPaiInfos);
            }
            Optional<FanResult> max = Arrays.stream(fanResults).max(Comparator.comparingInt(FanResult::getFan));
            userPaiInfos.setMaxFanResult(max.orElseGet(() -> null));
        }
    }
	private void computeFanResult(FanResult result, DouniuEndResult endResult2,
			DouniuUserPlace userPlace, DouniuUserPaiInfo userPaiInfos) {
		
		
	}
	
	*//**
	 * 计算分数
	 *//*
	public void excuteScore() {
		
		DouniuPaiType paiType = null;	//赢牌的类型
		DouniuUserPlace[] userPlaces = pokerChapter.getUserPlaces();	//获取玩家的UserPlace数组	
		DouniuUserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();	//获取用户牌的信息数组
		int winCount = 0;
		
		int k = -1;
		//1、使用循环从DouniuUserPlace中分别取出每个人的下注数
		//2、根据赢牌的类型获取赢牌的倍数
		//3、判断当前用户是赢牌还是输牌，如果是输则置为负数
		//4、使用连加操作直接计算庄家的得分。
		for (int i = 0; i < userPlaces.length; i++) {
			
			DouniuUserPaiInfo douniuUserPaiInfo = userPaiInfos[i];
			boolean isZhuang = douniuUserPaiInfo.isZhuang();
			
			if (!isZhuang) {
				DouniuUserPlace douniuUserPlace = userPlaces[i];
				
				int zhuNum = douniuUserPlace.getZhuNum();	//获取当前玩家的下注数	
				int fanType = douniuPaiType.getDoubleCount();	//赢家牌型的倍数
				
				boolean isWin = douniuUserPaiInfo.isWin();	//获取当前玩家是不是赢家
				
				//计算用户得分
				if (isWin) {
					//是赢家
					int s = zhuNum * fanType;
					winCount += -s;
					
					douniuUserPaiInfo.setScore(s);
					
				} else {
					//不是赢家
					int s = - (zhuNum * fanType);
					winCount += -s;
					
					douniuUserPaiInfo.setScore(s);
				}
			} else {
				k = i;
			}
		}
		DouniuUserPaiInfo douniuUserPaiInfo = userPaiInfos[k];
		douniuUserPaiInfo.setScore(winCount);
	}
*/}
