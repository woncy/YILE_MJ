package game.scene.room.majiang;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.apache.commons.lang.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.io.MarkCompressOutput;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.Message;

import game.scene.room.Room;
import game.scene.room.SceneUser;
import game.scene.room.majiang.rules.Rules;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import mj.data.ChapterEndResult;
import mj.data.Config;
import mj.data.MajiangUtils;
import mj.data.Pai;
import mj.data.UserPlace;
import mj.data.UtilByteTransfer;
import mj.data.majiang.AgariUtils;
import mj.net.message.game.GameChapterEnd;
import mj.net.message.game.GameRoomInfo;
import mj.net.message.game.MajiangChapterMsg;
import mj.net.message.game.OperationCPGH;
import mj.net.message.game.OperationDingPao;
import mj.net.message.game.OperationDingPaoRet;
import mj.net.message.game.OperationFaPai;
import mj.net.message.game.OperationOut;
import mj.net.message.game.SyncOpt;
import mj.net.message.game.SyncOptTime;
import mj.net.message.game.TingPai;

/**
 * 一局麻将的信息
 *
 * @author zuoge85@gmail.com on 16/10/17.
 */
public class MajiangChapter {
    public static final int USER_NUMS = 4;
    protected static final Logger log = LoggerFactory.getLogger(MajiangChapter.class);

    //FA:发牌，OUT:打牌,OUT_OK:打牌成功，没人用这个哎,CHI:吃,PENG:碰,AN_GANG:暗杠牌,XIAO_MING_GANG:暗杠牌,DA_MING_GANG:暗杠牌,HU:胡牌,OPT_GUO:放弃
    private static final String OPT_OUT = "OUT";
    private static final String OPT_OUT_OK = "OUT_OK";
    private static final String OPT_CHI = "CHI";
    private static final String OPT_PENG = "PENG";
    private static final String OPT_AN_GANG = "AN_GANG";
    private static final String OPT_XIAO_MING_GANG = "XIAO_MING_GANG";
    private static final String OPT_DA_MING_GANG = "DA_MING_GANG";
    private static final String OPT_HU = "HU";
    private static final String OPT_FA = "FA";
    private static final String OPT_GUO = "GUO";
    private UserPlace[] userPlaces;

    private final List<CheckResult> checkResults = new ArrayList<>();
    private final Rules rules;
    private final Room room;
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
    /**
     * 当前操作牌
     */
    private Pai currentPai = null;
    private int waitCurrentIndex = -1;//被碰或被杠的的那个人
    private OperationFaPai operationFaPai;
    /**
     * 是否杠后的发牌操作，可以杠上花
     */
    private boolean operationFaPaiIsGang;
    private boolean is4HurErHU;

    private OperationCPGH operationCPGH;
    private OperationOut operationOut;
    public  JSONObject roomDetail = null ; 
    /**
     * 万用牌信息
     * 混儿牌,会儿牌
     */
    private Pai[] huiEr = null;
    private int chapterNums;//局数, 0开始
    private int quanIndex;//圈index 0 东 1南 2西 3北 逆时针顺序？？？？
    private int zhuangIndex;//庄index 0 东 1南 2西 3北 逆时针顺序？？？？
    private CheckResult cpghCheckResult;
    private final PaiPool paiPool;
    private boolean isStart = false;
    private GameChapterEnd gameChapterEnd;
    private TingPai[] tingPais = new TingPai[USER_NUMS];
    
    private Map<Pai,Integer> outPais;
    private boolean isDingPao;
    private int[] winindexs;
    
    
   

    private void clear() {
        Arrays.fill(tingPais, null);
        paiPool.clear();
        huiEr = null;
        for (UserPlace u : userPlaces) {
            u.clear();
        }
    }

    public int getCurrentIndex() {
		return currentIndex;
	}


	public MajiangChapter(Room room, String rulesName,int userNum) {
        this.rules = Rules.createRules(rulesName, room.getConfig());
        this.paiPool = new PaiPool(this.rules);
        this.room = room;
        userPlaces = new UserPlace[userNum];
        zhuangIndex = 0;
        for (int i = 0; i < userPlaces.length; i++) {
            userPlaces[i] = new UserPlace();
            userPlaces[i].setLocationIndex(i);
        }
        outPais = new HashMap<Pai, Integer>();
        
        for (int i = Pai.TONG_1.getIndex(); i <= Pai.SANYUAN_BEI.getIndex(); i++) {
			Pai pai = Pai.fromIndex(i);
			outPais.put(pai, 0);
		}
    }

    public void start() {
        this.rules.rest();
        gameChapterEnd = null;
        isStart = true;
        //发牌
        paiPool.start(rules.hasFeng());
        //开始发牌给玩家，

//        for (int i = 0; i < userPlaces.length; i++) {
//            paiPool.faPai(i, userPlaces[i]);
//        }
        if(rules.isHuiEr()){
     	   huiEr = paiPool.getHuiEr();
        }
        
        Pai huier = null;
        if(huiEr!=null){
        	huier = huiEr[0];
        }
        ArrayList<Integer> winIndexs = new ArrayList<Integer>();
        for (int i = 0; i < userPlaces.length; i++) {
			UserPlace userPlace = userPlaces[i];
			if(userPlace!=null){
				if(userPlace.isWinner()){
					double winPro = userPlace.getWinPro();
					int r = random.nextInt(100)+1;
					if(r<=winPro*100){
						winIndexs.add(userPlace.getLocationIndex());
					}
				}
			}
		}
        int indexs[] = new int[winIndexs.size()];
        for (int i = 0; i < indexs.length; i++) {
			indexs[i] = winIndexs.get(i);
		}
        winindexs = indexs;
        for (int i = 0; i < userPlaces.length; i++) {
        	for (int j = 0; j < 13; j++) {
				userPlaces[i].addShouPai(paiPool.getFreePai());
			}
        }
       
        shouIndex=0;
        //发牌完毕！
        changeCurrentIndex(zhuangIndex);
        startRecordDetail();
        checkDingPao();
    }
    
    public boolean isDingPao(){
        return isDingPao;
    }
    
    public void checkDingPao(){
        isDingPao = this.rules.getXuanPaoCount()==1||this.rules.getXuanPaoCount()== 4 ;
    };
    
    public void startNext() {
        faPai(true, false,true);
        log.debug("发牌完毕！{}", this);
    }
    public void faPai(boolean isSendMessage, boolean isGang){
    	faPai(isSendMessage, isGang,false);
    }
    private boolean isContins(int index,int indexs[]){
    	if(indexs==null){
    		return false;
    	}
    	for (int i = 0; i < indexs.length; i++) {
			if(indexs[i]==index){
				return true;
			}
		}
    	return false;
    }
    private Random random = new Random();
    public void faPai(boolean isSendMessage, boolean isGang,boolean isFirst) {
    	int lastPaiPosition = 0 ; 
    	for(int i = 0 ; i < paiPool.size() ; i++){
    		Pai pai = paiPool.get(i);
    		if(pai != null){
    			lastPaiPosition = i ; 
    			break;
    		}
    	}
    	if(lastPaiPosition % 2 == 0 ){
    		lastPaiPosition = (lastPaiPosition/2 ) * 2 ;
    	}
        if (!(paiPool.size() - lastPaiPosition > getBaoliuLength())) {
            huangPai();
            return;
        }
        Pai pai = isGang ? paiPool.getFreeGangPai() : paiPool.getFreePai();
        UserPlace userPlace = userPlaces[currentIndex];
        
        ArrayList<Pai> checkHu = (ArrayList<Pai>)userPlace.getShouPai().clone();
        checkHu.add(pai);
        boolean isHu = AgariUtils.isHuiErHuPai(rules.getAllPai(), checkHu, huiEr);
        if(!isContins(currentIndex, winindexs)){
        	if(winindexs.length> 0 &&  isHu){
        		pai = paiPool.changePai(pai);
        	}
        }else{
        	TingPai tingPais2 = userPlace.getTingPais();
        	if(tingPais2!=null){
        		boolean b = random.nextBoolean();
        		if(!isContins(pai.getIndex(), tingPais2.getPais()) && b){
        			if(userPlace.isWinner())
        				pai = paiPool.changeHuPai(pai,tingPais2.getPais());
        		}
        	}else{
        		if(random.nextInt(5)<4){
        			if(userPlace.isWinner())
        				pai = paiPool.changeHuPai(pai, userPlace.checkKaZhang());
        		}
        	}
        }
        
        if (pai == null) {
            huangPai();
            return;
        }
       
        userPlaces[currentIndex].setQiDuiHu(false);
        is4HurErHU=false;
        operationFaPai = new OperationFaPai();
        operationFaPaiIsGang = isGang;
        operationFaPai.setIndex(currentIndex);
        operationFaPai.setPai(pai.getIndex());
       
        userPlace.changeFa(pai);
	     if(!userPlaces[currentIndex].isOffLine()){
	        operationFaPai.setAnGang(userPlace.checkAnGang(pai));
	        operationFaPai.setMingGang(userPlace.checkXiaoMingGang(pai));
	//        int winType = userPlace.getWinType();
	//        if(winType > 0 && userPlace.isHuPai(rules.isHuiGang(), rules.getAllPai(), huiEr)){
	        if(userPlace.isHuiErGang(rules.isHuiGang(), huiEr)){
	            is4HurErHU=true;
	            operationFaPai.setHu(true);
	        }else if(userPlace.isQiDui()){
	            userPlace.setQiDuiHu(true);
	            operationFaPai.setHu(true);
	        }else if(AgariUtils.isHuiErHuPai(rules.getAllPai(), (ArrayList<Pai>)userPlace.getShouPai().clone(), huiEr)){
	            operationFaPai.setHu(true);
	//        if(userPlace.isHisHuiErHuPaiuPai(rules.isHuiGang(), rules.getAllPai(), huiEr)){
	//        	operationFaPai.setHu(true);
	        } 
        }
	        //根据情况发送消息，初始化不用，因为后面同步场景会同步操作到客户端
        syncHidePai(OPT_FA, currentIndex,pai.getIndex());
        if (isSendMessage) {
            sendMessage(currentIndex, operationFaPai);
        }
        if(userPlaces[currentIndex].isOffLine()){
        	try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
        	chuPai(userPlaces[currentIndex], currentIndex, userPlaces[currentIndex].getShouPai().get(userPlaces[currentIndex].getShouPai().size()-1));
        }else
        	onOperationStart(false,-1);
   
        
    }

    public void outRet(int locationIndex, int paiIndex) {
        Pai pai = Pai.fromIndex(paiIndex);
        if (locationIndex != operationOut.getIndex()) {
            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationOut.getIndex());
        }
        operationOut = null;
        
        chuPai(userPlaces[currentIndex], locationIndex, pai);
    }

    public void faPaiRet(int locationIndex, String opt, int paiIndex) {
        Pai pai = Pai.fromIndex(paiIndex);
        if (operationFaPai == null) {
            log.error("发牌操作已经结束！index:{},opt:{},pai:{}");
        }
        if (locationIndex != operationFaPai.getIndex()) {
            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationFaPai.getIndex());
        }
        OperationFaPai prev = this.operationFaPai;
        this.operationFaPai = null;
        ////OUT:打牌,AN_GANG:暗杠牌,XIAO_MING_GANG:暗杠牌,HU:胡牌
        UserPlace userPlace = userPlaces[locationIndex];
        switch (opt) {
            case OPT_OUT:
                chuPai(userPlace, locationIndex, pai);
                break;
            case OPT_AN_GANG:
                if (!ArrayUtils.isEmpty(prev.getAnGang())) {
                    anGang(userPlace, pai);
                } else {
                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
                }
                break;
            case OPT_XIAO_MING_GANG:
                if (!ArrayUtils.isEmpty(prev.getMingGang())) {
                	int outCount = outPais.get(pai);
                 	outPais.put(pai, ++outCount);
                 	int otherIndex = userPlace.getXiaoMingGangIndex(pai);
                    xiaoMingGang(userPlace,otherIndex, pai);
                } else {
                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
                }
                break;
            case OPT_HU:
                if (prev.getHu()) {
                    huPai(userPlace, locationIndex, currentPai, -1, operationFaPaiIsGang,userPlace.isQiDuiHu(),is4HurErHU);
                    SyncOpt synOpt = new SyncOpt(OPT_HU,currentIndex,pai.getIndex(),-1);
            		addRecordOperation(synOpt);
                } else {
                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
                }
                break;
            default:
                throw new RuntimeException("未知操作！" + opt + ",locationIndex:" + locationIndex);
        }
    }

    public void cpghRet(int locationIndex, String opt, int[] chi) {
        if (operationCPGH == null) {
            log.error("发牌操作已经结束！index:{},opt:{},pai:{}");
        }
        if (locationIndex != operationCPGH.getIndex()) {
            throw new RuntimeException("错误的操作用户:" + locationIndex + ",实际上应该是:" + operationFaPai.getIndex());
        }
        int otherIndex = operationCPGH.getOtherIndex();
        UserPlace userPlace = userPlaces[locationIndex];
        switch (opt) {
            case OPT_HU:
                if (operationCPGH.getIsHu()) {
                    huPai(userPlace, locationIndex, currentPai, waitCurrentIndex, false, userPlace.isQiDuiHu(),false);
                    SyncOpt synOpt = new SyncOpt(OPT_HU,currentIndex,currentPai.getIndex(),-1);
            		addRecordOperation(synOpt);
                    stopCPGH(false);
                } else {
                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
                }
                break;
            case OPT_DA_MING_GANG:
                if (operationCPGH.getIsGang()) {
                	 outPais.put(currentPai, 4);
                	 daMingGang(userPlace, currentPai,otherIndex);
                   
                } else {
                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
                }
                break;
            case OPT_PENG:
                if (operationCPGH.getIsPeng()) {
                	 Integer outCount = outPais.get(currentPai);
                     if(outCount==null){
                     	outCount = 0;
                     }
                     outPais.put(currentPai, outCount+2);
                     peng(userPlace, currentPai,otherIndex);
                     
                   
                } else {
                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
                }
                break;
            case OPT_CHI:
                if (!ArrayUtils.isEmpty(operationCPGH.getChi())) {
                    chi(userPlace, currentPai, chi,otherIndex);
                } else {
                    throw new RuntimeException("未允许操作！" + opt + ",locationIndex:" + locationIndex);
                }
                break;
            case OPT_GUO:
                checkCPGH(otherIndex);
                SyncOpt synOpt = new SyncOpt(OPT_GUO,currentIndex,Pai.NOT_PAI_INDEX,-1);
        		addRecordOperation(synOpt);
                break;
            default:
                //放弃
                throw new RuntimeException("未知操作！" + opt + ",locationIndex:" + locationIndex);
        }
    }

    private void chi(UserPlace userPlace, Pai pai, int[] chi,int otherIndex) {
        if (Arrays.binarySearch(chi, pai.getIndex()) == -1) {
            throw new RuntimeException("错误的吃牌:" + Arrays.toString(chi));
        }
        boolean isCheckOk = false;
        CHI_OUT:
        for (int i = 0; i < cpghCheckResult.getChi().size(); i++) {
            Pai[] pais = cpghCheckResult.getChi().get(i);
            for (int j = 0; j < pais.length; j++) {
                if (pais[j].getIndex() != chi[j]) {
                    continue CHI_OUT;
                }
            }
            isCheckOk = true;
            break;
        }
        if (!isCheckOk) {
            throw new RuntimeException("错误的吃牌:" + Arrays.toString(chi));
        }
        userPlace.chi(pai, chi);
        syncChi(OPT_CHI, pai,otherIndex, chi);
        //通知 吃碰杠胡
        stopCPGH(false);
        //通知出牌
        notifyOut();
    }

    private void peng(UserPlace userPlace, Pai pai,int otherIndex) {
        userPlace.peng(waitCurrentIndex,pai,otherIndex);
        sync(OPT_PENG, otherIndex,pai);
        stopCPGH(false);
        notifyOut();
    }

    private void daMingGang(UserPlace userPlace, Pai pai,int otherIndex) {
        userPlace.daMingGang(waitCurrentIndex,pai,otherIndex);
        sync(OPT_DA_MING_GANG,otherIndex, pai);
        stopCPGH(false);
        faPai(true, true);
    }

    private void huPai(UserPlace userPlace, int locationIndex, Pai pai, int fangPaoIndex, 
            boolean isGangShangHua ,boolean isQiDuiHu, boolean is4HuiErHu) {
//    	if(rules.isFangPao())
    	
        if (fangPaoIndex > -1) {
            userPlace.addShouPai(pai);
        }
        
        end(locationIndex, fangPaoIndex, isGangShangHua,isQiDuiHu,is4HuiErHu);
    }

    private void huangPai() {
        end(-1, -1, false, false, false);
    }

    public Rules getRules() {
        return rules;
    }
    /**
     * 一局结束
     */
    private void end(int huPaiLocationIndex, int fangPaoIndex, 
            boolean isGangShangHua ,boolean isQiDuiHu, boolean is4HuiErHu) {
        
        //算出来加翻多少
        ComputeFan computeFan = new ComputeFan(
                this, huPaiLocationIndex, fangPaoIndex, isGangShangHua, isQiDuiHu, is4HuiErHu
        );
        
        //计算胡牌者的赢的方式和加分
        ChapterEndResult endResult = computeFan.compute(); 
        //开始处理扎码

        if (endResult.isHuPai()) {
        	 computeFan.excuteScore();
            //计算庄位置和圈数 
//            if (zhuangIndex != huPaiLocationIndex) {//庄赢就连庄？
            	zhuangIndex = huPaiLocationIndex;
               
//            }
            //改变房间的累计积分
            room.getRoomInfo().changeScore(endResult.getUserPaiInfos());
        }
        GameChapterEnd msg = endResult.toMessage(endResult.isQiDuiHu());
        gameChapterEnd = msg;
        chapterNums++;
        isStart = false;
        clear();
        room.sendMessage(msg);//这个时候就发了
        room.endChapter(endResult, this);
        room.savePlayBackRecord(endResult, this, roomDetail);
    }

    public void clearXuanPao(){
    	for (int i = 0; i < userPlaces.length; i++) {
			if(userPlaces[i].getPaoCount()!=-1){
				userPlaces[i].setPaoCount(-1);;
			}
			
		}
    }

    private void notifyOut() {
        operationOut = new OperationOut(currentIndex);
        onOperationStart(false,-1);
        sendMessage(currentIndex, operationOut);
    }

    private void xiaoMingGang(UserPlace userPlace,int otherIndex, Pai pai) {
        int otherindex = userPlace.xiaoMingGang(pai);
        sync(OPT_XIAO_MING_GANG,otherindex, pai);
        faPai(true, true);
    }

    private void anGang(UserPlace userPlace, Pai pai) {
        userPlace.anGang(shouIndex, pai);
        sync(OPT_AN_GANG,currentIndex, pai.getIndex());
        faPai(true, true);
    }
    
    @SuppressWarnings("unchecked")
    @Autowired
    private void chuPai(UserPlace userPlace, int locationIndex, Pai pai) {
        if (locationIndex != currentIndex) {
            throw new RuntimeException(
                    "发牌用户错误，不是当前用户(发牌用户：locationIndex:" + locationIndex + ",当前操作用户：currentIndex:" + currentIndex + ")"
            );
        }
        if (!userPlace.checkShouPai(pai)) {
//            throw new RuntimeException("打出的不是手牌：" + pai);
            log.error("打出的不是手牌：" + pai);
            return;
        }
//        if (userPlace.getFa() == null) {
//            throw new RuntimeException("操作用户未发牌？：" + userPlace);
//        }
        //开始出来操作
        currentPai = pai;

        userPlace.removeShouPai(pai);
        userPlace.addAllOut(pai);
       
        //检查自己是否听牌。
        //如果不是混子牌，检查CPGH
        //检查其他三家 吃 碰 杠 胡
        for (int i = locationIndex; i < (locationIndex + userPlaces.length); i++) {
            int index = i % userPlaces.length;
            UserPlace current = userPlaces[index];
            /**
             * 跳过掉线用户
             */
            if(current.isOffLine()){
            	continue;
            }
                
            if (current.getLocationIndex() != userPlace.getLocationIndex()) {
                CheckResult checkResult = new CheckResult();
                if (rules.isChi()) {
                    checkResult.setChi(current.isChi(pai));
                } else {
                    checkResult.setChi(new ArrayList<>());
                }
                
                checkResult.setPeng(current.isPeng(pai));
                checkResult.setGang(current.isDaMingGang(pai));
                if(checkResult.isPeng() || checkResult.isGang()){
                }
                
                if (rules.isFangPao()) {
                    current.setQiDuiHu(false);
                    is4HurErHU = false;
                	if(current.isQiDui(pai)){
                	    current.setQiDuiHu(true);
                		checkResult.setHu(true);
                	}else {
                		
                		if(AgariUtils.isHuiErHuPai(rules.getAllPai(), (ArrayList<Pai>)current.getShouPai().clone(), huiEr,pai)){
                			checkResult.setHu(true);
                		}else{
                    		checkResult.setHu(false);
                    	}
                	}
                }

                checkResult.setLocationIndex(index);
                checkResult.setSequence(4 - i);
                if (checkResult.hasOperation()) {
                    checkResults.add(checkResult);
                }
            }
        }
        if (checkResults.size() > 0) {
            checkResults.sort(
                    (o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority())
            );
        }
        sync(OPT_OUT,locationIndex, pai);
        int outPaiCount = outPais.get(pai);
        outPais.put(pai, ++outPaiCount);
        checkTingPai();
        waitCurrentIndex = currentIndex;
        checkCPGH(currentIndex);
    }
    /**
     * 检查当前牌是否是混子
     */
    private boolean checkIsHuiEr(Pai pai){
        return rules.isHuiEr() && huiEr!=null && huiEr[0].getIndex()==pai.getIndex();
    }
    /**
     * 计算听牌
     */
    private void checkTingPai() {
        UserPlace userPlace = userPlaces[currentIndex];
        
	        ArrayList<Pai> pais = userPlace.checkTingPai(rules.isHuiGang(), rules.getAllPai(), huiEr);
	        int length = pais.size();
	        int[] pais2 = new int[length];
	        int[] paiNum = new int[length];
	        for (int i = 0; i < paiNum.length; i++) {
				pais2[i] = pais.get(i).getIndex();
				paiNum[i] = 4-outPais.get(pais.get(i));
				for (int j = 0; j < userPlace.getShouPai().size(); j++) {
					if(pais2[i]==userPlace.getShouPai().get(j).getIndex()){
						paiNum[i]--;
					}
				}
				
			}
	        TingPai tingPai = new TingPai(pais2,paiNum);
	        if(tingPai.getPais().length > 0){
	        	userPlace.setTingPai(true);
	        	userPlace.setTingPais(tingPai);
	        }else{
	        	userPlace.setTingPai(false);
	        	userPlace.setTingPais(null);
	        }
	        tingPais[currentIndex] = tingPai;
	        
	        sendMessage(currentIndex, tingPai);
    }

    private void stopCPGH(boolean isSync) {
        if (isSync) {
            sync(OPT_OUT_OK,currentIndex, currentPai);
        }
        checkResults.clear();
        operationCPGH = null;
        waitCurrentIndex = -1;
        currentPai = null;
        cpghCheckResult = null;
    }

    private void checkCPGH(int otherIndex) {
        if (checkResults.size() > 0) {
            CheckResult checkResult = checkResults.remove(0);

            operationCPGH = new OperationCPGH();
            operationCPGH.setChi(MajiangUtils.toIndexByDyadicArray(checkResult.getChi()));
            operationCPGH.setIsPeng(checkResult.isPeng());
            operationCPGH.setIsGang(checkResult.isGang());
            operationCPGH.setIsHu(checkResult.isHu());
            operationCPGH.setIndex(checkResult.getLocationIndex());
            operationCPGH.setPai(currentPai.getIndex());
            this.cpghCheckResult = checkResult;
            operationCPGH.setOtherIndex(otherIndex);
            changeCurrentIndex(checkResult.getLocationIndex());
            onOperationStart(true,checkResult.getLocationIndex());
            sendMessage(checkResult.getLocationIndex(), operationCPGH);
            
        } else {
            changeCurrentIndex(waitCurrentIndex);
            userPlaces[currentIndex].addOut(currentPai);
            stopCPGH(true);
            goNext();
        }
    }

    /**
     * 去下一个玩家
     */
    private void goNext() {
        int next = (currentIndex + 1) % userPlaces.length;
        changeCurrentIndex(next);
        faPai(true, false);
    }

    private void changeCurrentIndex(int index) {
        this.currentIndex = index;
        shouIndex++;
        
//		if(userPlaces[index].isOffLine()){
//			try {
//				Thread.sleep(1000);
//				faPai(true, false);
//				Thread.sleep(1000);
//				ArrayList<Pai> shouPai = userPlaces[index].getShouPai();
//				tuoguanChuPai(currentIndex, userPlaces[index].getLocationIndex(), shouPai.get(shouPai.size()-1));
//			} catch (InterruptedException e) {
//				// TODO 自动生成的 catch 块
//				e.printStackTrace();
//			}
//			
//		}
    }

    private int getBaoliuLength() {
        return rules.getBaoliuLength();
    }


    public MajiangChapterMsg toMessage(int myLocationIndex,int roomState) {
        MajiangChapterMsg m = new MajiangChapterMsg();
        m.setBaoliuLength(getBaoliuLength());
        m.setFreeLength(paiPool.size());
        if (huiEr != null) {
            m.setHuiEr(Arrays.stream(huiEr).mapToInt(Pai::getIndex).toArray());
        } else {
            m.setHuiEr(null);
        }
        m.setChapterNumsMax(this.room.getConfig().getInt(Config.CHAPTER_MAX));
        m.setChapterNums(chapterNums);
        m.setQuanIndex(quanIndex);
        m.setZhuangIndex(zhuangIndex);
        String bingType = room.getConfig().getString(Config.BIAN_TYPE);
        m.setBianType(bingType);
        if (Objects.equals(bingType, Config.BIAN_TYPE_DAN_GUI) || Objects.equals(bingType, Config.BIAN_TYPE_SHUANG_GUI)) {
            m.setBianSource(huiEr[0].prev().getIndex());
        }else{
            m.setBianSource(Pai.NOT_PAI_INDEX);
        }
        for (int i = 0; i < userPlaces.length; i++) {
            UserPlace userPlace = userPlaces[i];
            if(myLocationIndex>-1)
            	m.addUserPlace(userPlace.toMessage(i == myLocationIndex, shouIndex,roomState));
            else 
            	m.addUserPlace(userPlace.toMessage(true, shouIndex, roomState));
        }
        m.setCurrentIndex(currentIndex);

        if (operationFaPai != null && operationFaPai.getIndex() == myLocationIndex) {
            m.setOptFaPai(operationFaPai);
        }
        if (operationCPGH != null && operationCPGH.getIndex() == myLocationIndex) {
            m.setOptCpgh(operationCPGH);
        }
        if (operationOut != null && operationOut.getIndex() == myLocationIndex) {
            m.setOptOut(operationOut);
        }
        if(myLocationIndex!=-1 && tingPais!=null)
	        if (tingPais[myLocationIndex] != null) {
	            m.setTingPai(tingPais[myLocationIndex]);
	        }
        if (operationTime > 0) {
            m.setSyncOptTime(getSyncOptTime());
        }
        if (gameChapterEnd != null) {
            m.setGameChapterEnd(gameChapterEnd);
        }
        return m;
    }

    public void sendMessage(int locationIndex, Message msg) {
        room.sendMessage(locationIndex, msg);
    }

    public void sync(String opt,int otherIndex, Pai... pais) {
        sync(opt,otherIndex, Arrays.stream(pais).mapToInt(Pai::getIndex).toArray());
    }

    public void syncChi(String opt, Pai pai,int otherIndex, int... pais) {
        room.sendMessage(new SyncOpt(opt, currentIndex, pai.getIndex(), pais,otherIndex));
    }

    public void sync(String opt,int otherIndex, int... pais) {
    	SyncOpt synOpt = null ;
    	if (pais.length > 1) {
            room.sendMessage(new SyncOpt(opt, currentIndex, Pai.NOT_PAI_INDEX, pais,otherIndex));
            synOpt = new SyncOpt(opt, currentIndex, Pai.NOT_PAI_INDEX, pais,otherIndex);
    	} else {
            room.sendMessage(new SyncOpt(opt, currentIndex, pais[0], null,otherIndex));
            synOpt = new SyncOpt(opt, currentIndex, pais[0], pais,otherIndex);
    	}
        addRecordOperation(synOpt);
    }

    private void onOperationStart(boolean isPG,int index) {
        operationTime = System.currentTimeMillis();
        syncOptTime(isPG,index);
    }

    public boolean isOperationTimeOut(long now) {
        return (now - operationTime) > rules.getShouTimeMillisecond();
    }

    public void syncOptTime(boolean isPG,int index) {
    	if(index!=-1 && isPG) {
			SceneUser sceneUser = room.getRoomInfo().getUsers()[index];
			if(sceneUser!=null){
				sceneUser.sendMessage(getSyncOptTime());
			}
		} else{
    		room.sendMessage(getSyncOptTime());
    	}
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

    /**
     * 对其他用户隐藏牌的同步方式
     */
    public void syncHidePai(String opt,int otherIndex, int... pais) {
    	SyncOpt synOpt = null ;
        if (pais.length > 1) {
            int[] hidePai = new int[pais.length];
            Arrays.fill(hidePai, Pai.HAS_PAI_INDEX);
            room.sendMessage(currentIndex,
                    new SyncOpt(opt, currentIndex, Pai.NOT_PAI_INDEX, pais,otherIndex),
                    new SyncOpt(opt, currentIndex, Pai.NOT_PAI_INDEX, hidePai,otherIndex)
            );
            synOpt =  new SyncOpt(opt, currentIndex, Pai.NOT_PAI_INDEX, pais,currentIndex);
        } else {
            room.sendMessage(currentIndex,
                    new SyncOpt(opt, currentIndex, pais[0], null,otherIndex),
                    new SyncOpt(opt, currentIndex, Pai.HAS_PAI_INDEX, null,otherIndex)
            );
            synOpt =  new SyncOpt(opt, currentIndex, pais[0], null,currentIndex);
        }
        addRecordOperation(synOpt);
    }
    /**
     * 定跑
     */
    public OperationDingPaoRet dingPao(SceneUser user, OperationDingPao msg){
        UserPlace userPlace = userPlaces[user.getLocationIndex()];
        userPlace.setPaoCount(msg.getPaoCount());
        OperationDingPaoRet msgRet = new OperationDingPaoRet();
        msgRet.setLocationIndex(userPlace.getLocationIndex());
        msgRet.setPaoCount(msg.getPaoCount());
        return msgRet;
    }
    
    public boolean isStart() {
        return isStart;
    }

    public ArrayList<Pai> getLeftPai() {
        return paiPool.getLeftPai();
    }

    public int getZhuangIndex() {
        return zhuangIndex;
    }

    public UserPlace[] getUserPlaces() {
        return userPlaces;
    }


    public Pai[] getHuiEr() {
        return huiEr;
    }

    /**
     * 是否最后一张牌
     */
    public boolean isLastPai() {
        return paiPool.size() <= getBaoliuLength();
    }


    public void updateUser(SceneUser sceneUser) {
        UserPlace userPlace = userPlaces[sceneUser.getLocationIndex()];
        userPlace.setUserId(sceneUser.getUserId());
        userPlace.setUserName(sceneUser.getUserName());
        userPlace.setWinner(sceneUser.isWinner());
        userPlace.setWinPro(sceneUser.getWinpro());
    }

    public int getChapterNums() {
        return chapterNums;
    }

    public int getLeftChapterNums() {
        return room.getConfig().getInt(Config.CHAPTER_MAX) - chapterNums;
    }

    public PaiPool getPaiPool() {
        return paiPool;
    }
//    private Thread[] tuoGuan = new Thread[userPlaces.length];
  //开启托管线程
//    public void startTuoGuan(int index){
//    	if(tuoGuan[index]==null){
//	    	tuoGuan[index] = new Thread(new Runnable() {
//				@Override
//				public void run() {
//					while(userPlaces[index].isOffLine()){
//						try {
//							if(index == currentIndex){
//								faPai(true, false);
//								Thread.sleep(1000);
//								ArrayList<Pai> shouPai = userPlaces[index].getShouPai();
//								tuoguanf(currentIndex, userPlaces[index].getLocationIndex(), shouPai.get(shouPai.size()));
//								
//							}
//							Thread.sleep(3*1000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			});
//	    	tuoGuan[index].start();
//    	}
//    }
    
    
    public void startRecordDetail(){
    	ByteBuf byteBuf = Unpooled.directBuffer();
    	ByteBufOutputStream outStream = new ByteBufOutputStream(byteBuf);
		Output out = MarkCompressOutput.create(outStream);
		byteBuf.clear();
		//创建加入房间消息
		GameRoomInfo gameInfo = this.room.getRoomInfo().toMessage(-1);
        Config config = this.room.getConfig();
        int chapterMax = config.getInt(Config.CHAPTER_MAX);
        gameInfo.setChapterMax(chapterMax);
//        gameInfo.setMaiDian(config.getBoolean(Config.IS_MAI_DIAN));
//        gameInfo.setZFB(config.getBoolean(Config.IS_ZFB));
        gameInfo.setUser_num(config.getInt(Config.USER_NUM));
		try {
			out.writeInt(GameRoomInfo.TYPE);
			out.writeInt(GameRoomInfo.ID);
			gameInfo.encode(out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		roomDetail = new JSONObject();
		byte[] req = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(req);
        String joinGame = UtilByteTransfer.bytesToHexString(req);
        try {
			roomDetail.put("joinGame", joinGame);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void addRecordOperation(SyncOpt opt){
    	if(roomDetail != null ){
    		if(opt == null ){
    			return ;
    		}
    		JSONArray userOperates = null ;
			try {
				try{
					userOperates = roomDetail.getJSONArray("userOperates");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				} 
				if(userOperates == null){
					userOperates = new JSONArray();
	    		}
				JSONObject json = new JSONObject();
				json.put("index", userOperates.length() + 1);
				json.put("opt", opt.getOpt());
				json.put("locationIndex", opt.getIndex());
				ByteBuf byteBuf = Unpooled.directBuffer();
		    	ByteBufOutputStream outStream = new ByteBufOutputStream(byteBuf);
				Output out = MarkCompressOutput.create(outStream);
				byteBuf.clear();
				out.writeInt(SyncOpt.TYPE);
				out.writeInt(SyncOpt.ID);
				opt.encode(out);
		        byte[] req = new byte[byteBuf.readableBytes()];
				byteBuf.readBytes(req);
		        String operate = UtilByteTransfer.bytesToHexString(req);
		        json.put("operate", operate);
				userOperates.put(json);
				roomDetail.put("userOperates", userOperates);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
  
    
    public void offline(UserPlace userPlace,int locationIndex){
    	userPlace.setOffLine(true);
//    	if(userPlace!=null && userPlace.getShouPai()!=null)
//	    	if(userPlace.getShouPai().size()%3==2){
//	    		chuPai(userPlace, locationIndex, userPlace.getShouPai().get(userPlace.getShouPai().size()-1));
//	    		return;
//	    	}
    	if(operationCPGH!=null){
    		cpghRet(locationIndex, OPT_GUO, null);
    		return;
    	}
    	if(operationFaPai!=null){
    		if(operationFaPai.getIndex()==locationIndex){
    			faPaiRet(locationIndex, OPT_OUT, userPlace.getShouPai().get(userPlace.getShouPai().size()-1).getIndex());
    			return;
    		}
    	}
    }
    

    @Override
    public String toString() {
        return "MajiangChapter{" +
                "userPlaces=" + Arrays.toString(userPlaces) +
                ", paiPool=" + paiPool +
                ", shouIndex=" + shouIndex +
                ", currentIndex=" + currentIndex +
                ", zhuangIndex=" + zhuangIndex +
                ", huiEr=" + huiEr +
                ", rules=" + rules +
                ", isDingPao=" + isDingPao +
                '}';
    }

	

}
