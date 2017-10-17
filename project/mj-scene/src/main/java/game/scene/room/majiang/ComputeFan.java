package game.scene.room.majiang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import game.scene.room.majiang.rules.Rules;
import mj.data.BaseFanType;
import mj.data.ChapterEndResult;
import mj.data.FanInfo;
import mj.data.FanResult;
import mj.data.JiaFanType;
import mj.data.Pai;
import mj.data.PaiType;
import mj.data.UserPaiInfo;
import mj.data.UserPlace;

/**
 * @author zuoge85@gmail.com on 2017/1/18.
 */
public class ComputeFan {
    private MajiangChapter chapter;
    private ChapterEndResult endResult;

    /**
     * 构造方法，主要是用来初始化 chapter 和 endResult
     */
    @SuppressWarnings("unchecked")
    public ComputeFan(MajiangChapter chapter,
                      int huPaiIndex, int fangPaoIndex, 
                      boolean isGangShangHua ,boolean isQiDuiHu, boolean is4HuiErHu) {
        this.chapter = chapter;
        endResult = new ChapterEndResult();
        endResult.setHuPai(huPaiIndex != -1);
        endResult.setZhuangIndex(chapter.getZhuangIndex());
        endResult.setHuPaiIndex(huPaiIndex);
        
//        if(huPaiIndex > -1){
//	        int winType = chapter.getUserPlaces()[huPaiIndex].getWinType();
//	        endResult.setEndType(winType);
//        }
        
        boolean isZiMo = fangPaoIndex == -1;
        endResult.setZiMo(isZiMo);
        
        endResult.setGangShangHua(isGangShangHua);
        endResult.setHuiErHu(is4HuiErHu);
        endResult.setQiDuiHu(isQiDuiHu);
        if(huPaiIndex!=-1)
        	endResult.setHuPaiUserId(chapter.getUserPlaces()[huPaiIndex].getUserId());//胡牌人的ID
        endResult.setFangPaoIndex(fangPaoIndex);
        endResult.setLastPai(chapter.isLastPai());
        endResult.setLeft((ArrayList<Pai>) chapter.getLeftPai().clone());
        endResult.setHuiEr(chapter.getHuiEr());
        endResult.setUserPaiInfos(
                Stream.of(chapter.getUserPlaces()).map(u -> new UserPaiInfo(
                        chapter.getRules().getAllPai(), chapter.getHuiEr(), u,
                        u.getLocationIndex() == huPaiIndex,
                        u.getLocationIndex() == chapter.getZhuangIndex(),
                        isZiMo
                )).toArray(UserPaiInfo[]::new)
        );
    }

    public ChapterEndResult compute() {
        //判断是不是流局了
        if (endResult.isHuPai()) {
            UserPlace[] userPlaces = chapter.getUserPlaces();

            UserPlace userPlace = userPlaces[endResult.getHuPaiIndex()];

            UserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();

            computeUser(userPaiInfos[endResult.getHuPaiIndex()], userPlace);
        }

        return endResult;
    }

    private void computeUser(UserPaiInfo userPaiInfo, UserPlace userPlace) {
        FanResult[] fanResults = userPaiInfo.getFanResults();
        if (userPaiInfo.getFanResults() != null) {
            for (FanResult result : userPaiInfo.getFanResults()) {
                computeFanResult(result, endResult, userPlace, userPaiInfo);
            }
            Optional<FanResult> max = Arrays.stream(fanResults).max(Comparator.comparingInt(FanResult::getFan));
            userPaiInfo.setMaxFanResult(max.orElseGet(() -> null));
        }
    }

    /**
     * 计算胡得分
     */
    public void excuteScore(){
        //把需要用到的数据线定义出来
        boolean isZiMo=endResult.isZiMo();
        //胡牌人位置
        int huPaiIndex = endResult.getHuPaiIndex();
        //放炮人位置
        int fangPaoIndex=endResult.getFangPaoIndex();
        
        //底分
        int diFen = endResult.getFanNums();
        
        
        //庄家位置
        int zhuangIndex = chapter.getZhuangIndex();
        
        //是不是庄加底
        boolean isZhuangJiaDi = chapter.getRules().isZhuangJiaDi();
        
        boolean isGangShangHua = endResult.isGangShangHua();
        boolean isGangKaiFanBei=chapter.getRules().isGangKaiFanBei();
        
        
        boolean isHuiHu = endResult.isHuiErHu(); 
        boolean isQiDuiHu = endResult.isQiDuiHu();
        boolean isQiDuiFanBei = chapter.getRules().isQiDuiFanBei();
        //是否杠带跑
        boolean isGangDaiPao = chapter.getRules().isGangDaiPao();
        
        UserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();
        
        //获取胡牌的User 原来的代码
//        UserPaiInfo huPaiUser = null;
        
        //底分初始值 郑州麻将
        int difen = 1;
        
        //翻倍次数初始值 郑州麻将
        int fanBeiCount = 0;
        
        /**
         * 杠开翻倍且杠上花胡
         */
        if(isGangShangHua&& isGangKaiFanBei){
        	fanBeiCount++;
        }
        /**
         * 七对翻倍且七对胡
         */
        if (isQiDuiFanBei&&isQiDuiHu) {
			fanBeiCount++;
		}
        /**
         * 四混儿胡
         */
        if(isHuiHu){
        	fanBeiCount++;
        }
        UserScore[] userScores = new UserScore[userPaiInfos.length];
        int winer_arr_index = -1;
        for (int i = 0; i < userPaiInfos.length; i++) {
			UserPaiInfo userPaiInfo = userPaiInfos[i];
			userScores[i] = new UserScore();
			userScores[i].setIndex(userPaiInfo.getLocationIndex());
			/**
			 * 记录胡牌人在数组中的位置
			 */
			if(userPaiInfo.getLocationIndex()==huPaiIndex){
				winer_arr_index = i;
				userScores[i].setFanbeiCount(fanBeiCount);
			}
			userScores[i].setPaofen(userPaiInfo.getPaoCount());
			if(zhuangIndex==userPaiInfo.getLocationIndex() && isZhuangJiaDi){
				userScores[i].setDifen(difen+1);
			}else
				userScores[i].setDifen(difen);
        }
       
      int winnerScore = 0;
	   for (int i = 0; i < userScores.length; i++) {
		   UserScore userScore = userScores[i];	
		   int df = userScore.getDifen();
		   if(df<userScores[winer_arr_index].getDifen()){
			   df = userScores[winer_arr_index].getDifen();
		   }
		   if(i!=winer_arr_index){
			   if(isZiMo){
				     int s1 = (df+userScore.getPaofen()+userScores[winer_arr_index].getPaofen())*(1<<fanBeiCount);
				     winnerScore +=s1;
				     userScore.setHuScore(0-s1);
			   }else{
				   if(userScore.getIndex() == fangPaoIndex){
					   winnerScore += (df+userScore.getPaofen()+userScores[winer_arr_index].getPaofen())*(1<<fanBeiCount);
					   userScore.setHuScore(0-winnerScore);
				   }
			   }
		   }
		   
	   }
	   userScores[winer_arr_index].setHuScore(winnerScore);
	   /**
	    * 计算杠
	    */
	   for (int i = 0; i < userPaiInfos.length; i++) {
			UserPaiInfo userPaiInfo = userPaiInfos[i];
			UserScore current = userScores[i];
			int anGangCount = userPaiInfo.getAnGang().size();
			/**
			 * 大明杠结算
			 */
			for (Map.Entry<Integer, Pai> entry:userPaiInfo.getDaMingGang()) {
				UserScore userScore = userScores[entry.getKey()];
				int gangScore = 0;
				if(isGangDaiPao){
					int df = userScore.getDifen();
					if(df<current.getDifen()){
						df = current.getDifen();
					}
					gangScore = gangScore+df+current.getPaofen()+userScore.getPaofen();
					
				}else{
					gangScore = 1;
				}
				current.setGangScore(current.getGangScore()+gangScore);
				userScore.setGangScore(userScore.getGangScore()-gangScore);
			}
			/**
			 * 小明杠结算
			 */
			for (Map.Entry<Integer, Pai> entry:userPaiInfo.getXiaoMingGang()) {
				UserScore userScore = userScores[entry.getKey()];
				int gangScore = 0;
				if(isGangDaiPao){
					int df = userScore.getDifen();
					if(df<current.getDifen()){
						df = current.getDifen();
					}
					gangScore = gangScore+df+current.getPaofen()+userScore.getPaofen();
					
				}else{
					gangScore = 1;
				}
				current.setGangScore(current.getGangScore()+gangScore);
				userScore.setGangScore(userScore.getGangScore()-gangScore);
			}
			/**
			 * 
			 * 暗杠结算
			 */
			for (int j = 0; j < anGangCount; j++) {
				
				for (int j2 = 0; j2 < userScores.length; j2++) {
					UserScore userScore2 = userScores[j2];
					if(j2!=i){
						int gangScore = 0;
						if(isGangDaiPao){
							int df = userScore2.getDifen();
							if(df<current.getDifen()){
								df = current.getDifen();
							}
							gangScore = gangScore+df+current.getPaofen()+userScore2.getPaofen();
							
						}else{
							gangScore = 1;
						}
						current.setGangScore(current.getGangScore()+gangScore);
						userScore2.setGangScore(userScore2.getGangScore()-gangScore);
					}
					
				}
			}
	   }
	   
	  for (int i = 0; i < userScores.length; i++) {
		UserScore userScore = userScores[i];
		int score = userScore.getGangScore()+userScore.getHuScore();
		int gangScore = userScore.getGangScore();
		userPaiInfos[i].setScore(score);
		userPaiInfos[i].setGuaFengXiaYu(gangScore);
		userPaiInfos[i].setFan(fanBeiCount);
	}
	   
    
        
        
        
        	//原来的代码
        
//        if(huPaiUser==null){
//            return ;
//        }
//        
//        int fanNums=1;
//        //杠开翻倍
//        if(isGangShangHua && isGangKaiFanBei){
//            fanNums*=2;
//        }
//        //混子胡翻倍
//        if(isHuiHu && isZiMo){
//            fanNums *= 2;
//        }
//        //七对胡底分翻倍
//        if(isQiDuiFanBei && isQiDuiHu){
//            fanNums*=2;
//        }
//        
//        //如果庄胡并且庄加底，就加一分底分
//        if(isZhuangJiaDi && zhuangIndex==huPaiIndex )
//        {
//            diFen +=1;
//        }
//        
//        for(UserPaiInfo userPaiInfo:userPaiInfos){
//            //感觉这个没啥用
//            userPaiInfo.setFan(fanNums);
//            int df=diFen;
//            if(isZiMo){
//                if(userPaiInfo.getLocationIndex() != huPaiIndex){
//                	//这里来判断如果不是庄胡的，计算庄家输分时庄加底的情况
//                    int zhuangjiashuFan = 0 ; 
//                	if(zhuangIndex==userPaiInfo.getLocationIndex() && isZhuangJiaDi){
//                		zhuangjiashuFan = 1;
//                    }
//                	int score=(userPaiInfo.getPaoCount()+huPaiUser.getPaoCount() + df + zhuangjiashuFan)*fanNums;
//                    userPaiInfo.setScore(userPaiInfo.getScore()-score);
//                    huPaiUser.setScore(huPaiUser.getScore()+score);
//                }
//            }else{
//                if(userPaiInfo.getLocationIndex() == fangPaoIndex){
//                    int score=(userPaiInfo.getPaoCount()+huPaiUser.getPaoCount()+df)*fanNums;
//                    userPaiInfo.setScore(userPaiInfo.getScore()-score);
//                    huPaiUser.setScore(huPaiUser.getScore()+score);
//                }
//            }
//            //开始算杠得分
//            int gangDiFen=1;
//            //如果庄胡并且庄加底，就加一分底分
//            if(zhuangIndex==userPaiInfo.getLocationIndex() && isZhuangJiaDi){
//                gangDiFen +=1;
//            }
//            //计算暗杠得分
//            int anGangCount = userPaiInfo.getAnGang().size();
//            for(UserPaiInfo upi:userPaiInfos){
//                if(userPaiInfo.getLocationIndex() != upi.getLocationIndex()){
//                    int gdf=gangDiFen;
//                    //判断是否庄加底
//                    if(zhuangIndex==upi.getLocationIndex() && isZhuangJiaDi){
//                        gdf +=1;
//                    }
//                    //判断杠带跑
//                    int gangPaoCount=0;
//                    if(isGangDaiPao){
//                        gangPaoCount = userPaiInfo.getPaoCount()+upi.getPaoCount();
//                    }
//                    
//                    int anGangScore=(gangPaoCount+gdf)*fanNums*anGangCount;
//                    if(anGangCount>0){
//                        upi.setScore(upi.getScore()-anGangScore);
//                        upi.setGuaFengXiaYu(upi.getGuaFengXiaYu()-anGangScore);
//                        userPaiInfo.setScore(userPaiInfo.getScore()+anGangScore);
//                        userPaiInfo.setGuaFengXiaYu(userPaiInfo.getGuaFengXiaYu()+anGangScore);
//                    }
//                }
//            }
//            //计算明杠得分
//            ArrayList<Map.Entry<Integer, Pai>> mingAangList=new ArrayList<>();
//            mingAangList.addAll(userPaiInfo.getDaMingGang());
//            mingAangList.addAll(userPaiInfo.getXiaoMingGang());
//            
//            for(Map.Entry<Integer, Pai> mapE:mingAangList){
//                for(UserPaiInfo upi:userPaiInfos){
//                    if(mapE.getKey().intValue() == upi.getLocationIndex()){
//                        int gdf=gangDiFen;
//                        //判断是否庄加底
//                        if(zhuangIndex==upi.getLocationIndex() && isZhuangJiaDi){
//                            gdf +=1;
//                        }
//                        //判断杠带跑
//                        int gangPaoCount=0;
//                        if(isGangDaiPao){
//                            gangPaoCount = userPaiInfo.getPaoCount()+upi.getPaoCount();
//                        }
//                        
//                        int mingGangScore=(gangPaoCount+gdf)*fanNums;
//                        if(mingGangScore>0){
//                            upi.setScore(upi.getScore()-mingGangScore);
//                            upi.setGuaFengXiaYu(upi.getGuaFengXiaYu()-mingGangScore);
//                            userPaiInfo.setScore(userPaiInfo.getScore()+mingGangScore);
//                            userPaiInfo.setGuaFengXiaYu(userPaiInfo.getGuaFengXiaYu()+mingGangScore);
//                        }
//                    }
//                }
//            }
//        }
    }
    /**
     * 计算杠得分
     */
    public void computeGuaFengXiaYu() {
        boolean isGangDaiPao = chapter.getRules().isGangDaiPao();
        
//    	FanInfo jiaFanInfo = chapter.getRules().getJiaFanMap().get(JiaFanType.JIA_AN_GANG);
        UserPaiInfo[] userPaiInfos = endResult.getUserPaiInfos();
        for (int i = 0; i < userPaiInfos.length; i++) {
            //这个地方，就是如果听牌
//        	if(chapter.getUserPlaces()[i].isTingPai()){
        		UserPaiInfo userPaiInfo = userPaiInfos[i];
                int score = userPaiInfo.getAnGang().size()
                        + userPaiInfo.getDaMingGang().size()
                        + userPaiInfo.getXiaoMingGang().size();

                for (int j = 0; j < userPaiInfos.length; j++) {
                    UserPaiInfo ohterUserPaiInfo = userPaiInfos[j];
                    if (i != j) {
                        ohterUserPaiInfo.setScore(ohterUserPaiInfo.getScore() - score);
                        ohterUserPaiInfo.setGuaFengXiaYu(ohterUserPaiInfo.getGuaFengXiaYu() - score);
                        userPaiInfo.setScore(userPaiInfo.getScore() + score);
                        userPaiInfo.setGuaFengXiaYu(userPaiInfo.getGuaFengXiaYu() + score);
                    }
                }
//        	}
        }
    }

    private void computeFanResult(FanResult fanResult, ChapterEndResult chapteResult, UserPlace userPlace, UserPaiInfo userPaiInfo) {
        BaseFanType baseFanType;
        
        if (userPaiInfo.isZiMo()) {
            baseFanType = BaseFanType.ZI_MO;
        } else {
            baseFanType = BaseFanType.DIAN_PAO;
        }
        FanInfo baseFanInfo = chapter.getRules().getBaseFanMap().get(baseFanType);
        fanResult.setFan(baseFanInfo.getScore());
        fanResult.setFanString(baseFanInfo.getName());
        fanResult.setBaseFanType(baseFanType);
        /*
         * 加番计算
         * 先计算杠上面的数据
         */
//        int winType = chapteResult.getEndType();
//        FanInfo jiaFanInfo = null ; 
//        switch(winType){
//        	case 1: 
//        		jiaFanInfo  = chapter.getRules().getJiaFanMap().get(JiaFanType.QUEYILU_HU);
//        		
//        		break;
//        	case 2: 
//        		jiaFanInfo  = chapter.getRules().getJiaFanMap().get(JiaFanType.QUEERLU_HU);
//        		
//        		break;
//        	case 3: 
//        		jiaFanInfo  = chapter.getRules().getJiaFanMap().get(JiaFanType.QUESANLU_HU);
//        		
//        		break;
//        	case 4: 
//        		jiaFanInfo  = chapter.getRules().getJiaFanMap().get(JiaFanType.QINGYISE_HU);
//        		
//        		break;
//        }
//        
//        int fanNum = 0 ; 
//        if(jiaFanInfo != null){
//        	fanNum = jiaFanInfo.getScore(); 
//        }
//        
//        for(int i = 0 ; i < fanNum ; i++){
//        	 fanResult.setBaseFanType(baseFanType);
//        }
       
//        StringBuilder sb = new StringBuilder();
//        int fan = baseFanInfo.getScore() * fanNum;
//        sb.append(baseFanInfo.getName() + "*" + jiaFanInfo.getName());
        
//        JiaFanType jiaFan = JiaFanType.JIA_AN_GANG;
//        int nums = jiaFan.compute(fanResult, chapteResult, userPlace, userPaiInfo);
//        for (int i = 0; i < nums; i++) {
//        	for(int j = 0 ; j< fanNum ; j++){
//        		fanResult.getJiaFans().add(jiaFan);
//        	}
//            fan += jiaFanInfo.getScore() * fanNum;
//        }
//        if(nums > 0 ){
//        	 sb.append("+" +  "暗杠*" + jiaFanInfo.getName());
//        }
//        
//        jiaFan = JiaFanType.JIA_MING_GANG;
//        nums = jiaFan.compute(fanResult, chapteResult, userPlace, userPaiInfo);
//        for (int i = 0; i < nums; i++) {
//        	for(int j = 0 ; j< fanNum ; j++){
//        		fanResult.getJiaFans().add(jiaFan);
//        	}
//            fan += jiaFanInfo.getScore() * fanNum;
//        }
//        
//        if(nums > 0 ){
//       	 	sb.append("+" +  "明杠*" + jiaFanInfo.getName());
//        }
//        fanResult.setFan(fan);
//        fanResult.setFanString(sb.toString());
    }

    private int computeJiaFan(JiaFanType jiaFan) {
        FanInfo fanInfo = chapter.getRules().getJiaFanMap().get(jiaFan);
        return fanInfo == null ? 0 : fanInfo.getScore();
    }

    public int zaMa() {
        return zaMaScore();
    }

    private int zaMaScore() {
        Rules rules = chapter.getRules();
        PaiPool paiPool = chapter.getPaiPool();
        if (rules.isZaMa()) {

            endResult.setZaMaType(rules.getZaMa());
            int zaMa = rules.getZaMa();
            if (zaMa == -1) {

                Pai freePai = paiPool.getFreePai();
                int zamaScore = zaMaYIMa(freePai);
                endResult.setZaMaPai(new int[]{freePai.getIndex()});
                endResult.setZaMaFan(zamaScore);
                return zamaScore;
            } else {
                int zamaScore = 0;
                List<Pai> zaMaPai = new ArrayList<>();
                for (int i = 0; i < zaMa; i++) {
                    Pai freePai = paiPool.getFreePai();
                    if (freePai != null) {
                        if (freePai.getDian() == 1 ||
                                freePai.getDian() == 5 ||
                                freePai.getDian() == 9 || freePai.equals(Pai.SANYUAN_ZHONG)) {
                            zaMaPai.add(freePai);
                            zamaScore += 2;
                        }
                    }
                }
                endResult.setZaMaPai(zaMaPai.stream().mapToInt(Pai::getIndex).toArray());
                endResult.setZaMaFan(zamaScore);
                return zamaScore;
            }
        }
        return 0;
    }

    private int zaMaYIMa(Pai freePai) {
        if (freePai != null) {
            //  //一码全中一万   一筒    一条   就是一番一直到九万  九筒   九条  是9番      中  发   白是10番    最高10番
            if (PaiType.SANYUAN.equals(freePai.getType())) {
                return 10;
            } else {
//                if (freePai.getDian() == 1) {
//                    return 10;
//                }
                return freePai.getDian();
            }
        }
        return 0;
    }
    
}
