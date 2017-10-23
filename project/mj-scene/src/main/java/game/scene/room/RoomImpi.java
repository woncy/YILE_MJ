package game.scene.room;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.io.MarkCompressOutput;
import com.isnowfox.core.io.Output;

import game.boss.dao.dao.RoomResultDao;
import game.boss.dao.entity.RoomResultDO;
import game.scene.msg.ChapterEndMsg;
import game.scene.msg.ChapterStartMsg;
import game.scene.msg.RoomEndMsg;
import game.scene.net.BossClient;
import game.scene.room.majiang.MajiangChapter;
import game.scene.services.DbService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import mj.data.ChapterEndResult;
import mj.data.Config;
import mj.data.UserPaiInfo;
import mj.data.UserPlace;
import mj.data.UtilByteTransfer;
import mj.net.message.game.Chat;
import mj.net.message.game.ChatRet;
import mj.net.message.game.GameChapterEnd;
import mj.net.message.game.GameChapterStartRet;
import mj.net.message.game.GameExitUser;
import mj.net.message.game.GameRoomInfo;
import mj.net.message.game.GameUserInfo;
import mj.net.message.game.OperationCPGHRet;
import mj.net.message.game.OperationDingPao;
import mj.net.message.game.OperationDingPaoRet;
import mj.net.message.game.OperationFaPaiRet;
import mj.net.message.game.OperationOutRet;
import mj.net.message.game.Ready;
import mj.net.message.game.ShowPaoRet;
import mj.net.message.game.StaticsResultRet;
import mj.net.message.game.UserOffline;
import mj.net.message.game.Voice;
import mj.net.message.game.VoteDelSelect;
import mj.net.message.game.VoteDelSelectRet;
import mj.net.message.game.VoteDelStart;

/**
 * @author zuoge85@gmail.com on 16/10/18.
 */
public class RoomImpi extends Room {
    private long prevTime = 0;
    private long startTime = 0;
    

    @Autowired
    private DbService dbService;
    @Autowired
    private BossClient bossClient;
    
    @Autowired
    private RoomResultDao roomResultDao;


    public RoomImpi(RoomAsyncService roomAsyncService, Config config) {
        super(roomAsyncService);
        this.config = config;
    }

    @Override
    protected void frame() {
        long now = System.currentTimeMillis();
        if (prevTime == 0) {
            prevTime = now;
        }
//      log.info("frame:{}", now - prevTime);
        innerFrame(now);
        prevTime = now;
    }


    private void innerFrame(long now) {
        //开始游戏逻辑！
        if (roomInfo.isStart()) {
//            MajiangChapter chapter = roomInfo.getChapter();
//            if(chapter.isOperationTimeOut(now)){
//                //超时，强制出票
//                chapter.forceChuPai();
//            }
        }
    }

    public void join(SceneUser joinSceneUser, List<SceneUser> sceneUsers, Consumer<Boolean> callback) {
        run(() -> {
            if (isEnd()) {
                callback.accept(false);
            } else {
            	
                for (SceneUser sceneUser : sceneUsers) {
                	dbService.updateSceneUser(sceneUser);
                    roomInfo.updateUserInfo(sceneUser);
                }
                //更新 网管和session
                SceneUser sceneUser = roomInfo.getUserInfo(joinSceneUser.getLocationIndex());
                
                sceneUser.setOnline(true);
                sceneUser.setGatewayId(joinSceneUser.getGatewayId());
                sceneUser.setSessionId(joinSceneUser.getSessionId());
                callback.accept(true);
            }
        });
    }

    @Override
    public void start() {
        super.start();
        startTime = System.currentTimeMillis();
    }

    public void exitRoom(int userId, Consumer<Boolean> callback) {
        run(() -> {
            if (roomInfo.isChapterStart()) {
                callback.accept(false);
            } else {
                callback.accept(true);
                if (roomInfo.removeUserInfo(userId)) {
                    sendMessage(new GameExitUser(userId));
                }
            }
        });
    }

    public void offline(int userId) {
        run(() -> {
            SceneUser user = getRoomInfo().getUserInfoByUserId(userId);
            user.setOnline(false);
            user.setJoinGame(false);
            sendMessage(new UserOffline(user.getLocationIndex()), user);
            /**
             * 如果用户掉线了，就开启托管
             */
//            MajiangChapter chapter = roomInfo.getChapter();
//            for (int i = 0; i < chapter.getUserPlaces().length; i++) {
//				if(chapter.getUserPlaces()[i].getLocationIndex()==user.getLocationIndex()){
//					chapter.getUserPlaces()[i].setOffLine(true);
//				}
//			}
            
          
        });
    }

    public void delRoom(int userId, Consumer<Boolean> callback) {
        run(() -> {
//            if (roomInfo.isChapterStart()) {
//                callback.accept(false);
//            } else {
                callback.accept(true);
//            }
        });
    }

    /**
     * 玩家进入游戏
     */
    public void joinGame(SceneUser sceneUser) {
        checkThread();
        
        sceneUser.setJoinGame(true);
        MajiangChapter chapter = roomInfo.getChapter();
        
        for(int i=0;i<chapter.getUserPlaces().length;i++){
        	if(chapter.getUserPlaces()[i].getLocationIndex()==sceneUser.getLocationIndex()){
        		chapter.getUserPlaces()[i].setOffLine(false);
        	}
        }
        //同步游戏信息到客户端
        GameRoomInfo gameInfo = roomInfo.toMessage(sceneUser.getLocationIndex());
        Config config = sceneUser.getRoom().getConfig();
        int chapterMax = config.getInt(Config.CHAPTER_MAX);
        gameInfo.setChapterMax(chapterMax);
        gameInfo.setHuiEr(config.getBoolean(Config.IS_HUIER));
        gameInfo.setGangDaiPao(config.getBoolean(Config.IS_GANG_DAI_PAO));
        gameInfo.setDaiZiPai(config.getBoolean(Config.IS_DAI_ZI_PAI));
        gameInfo.setQiDuiFanBei(config.getBoolean(Config.IS_QI_DUI_FAN_BEI));
        gameInfo.setZhuangJiaDi(config.getBoolean(Config.IS_ZHUANG_JIA_DI));
        gameInfo.setGangKaiFan(config.getBoolean(Config.IS_GANG_KAI_FAN_BEI));
        gameInfo.setXuanPaoCount(config.getInt(Config.XUAN_PAO_COUNT));
        gameInfo.setFangPao(config.getBoolean(Config.IS_FANG_PAO));
        sceneUser.getRoom().getRoomInfo().getChapter().checkDingPao();
        gameInfo.setCurrentChapterPao(sceneUser.getRoom().getRoomInfo().getChapter().isDingPao());
        gameInfo.setUser_num(config.getInt(Config.USER_NUM));
        sceneUser.sendMessage(gameInfo);
        //通知其他玩家
        GameUserInfo msg = sceneUser.toMessage();
        roomInfo.excuteDistanceKm(msg, sceneUser);
        //计算距离
        sendMessage(msg, sceneUser);
        
        StaticsResultRet endResult = roomInfo.getEndResult();
        if(endResult!=null){
        	sceneUser.sendMessage(endResult);
        }
        if(roomInfo.getState()==1){
        	UserPlace[] userPlaces = roomInfo.getChapter().getUserPlaces();
        	boolean[] dingPaoChache = roomInfo.getDingPaoChache();
        	for (int i = 0; i < dingPaoChache.length; i++) {
				boolean b = dingPaoChache[i];
				if(b){
					if(userPlaces[i]!=null){
						sceneUser.sendMessage(new OperationDingPaoRet(i, userPlaces[i].getPaoCount()));
					}
				}
				
			}
        }
        
        if(roomInfo.isNeedShowPao(sceneUser.getLocationIndex())){
        	sceneUser.sendMessage(new ShowPaoRet());
        	
        }
        
    }
    
    /**
     * 玩家进入游戏
     */
    public void chatSendToAllUser(Chat msg, SceneUser sceneUser) {
        checkThread();
        ChatRet msgRet = new ChatRet();
        msgRet.setChatContent(msg.getChatContent());
        msgRet.setUserindex(sceneUser.getLocationIndex());
        msgRet.setIndex(msg.getIndex());
        sendMessage(msgRet, null);
    }


    public void faPaiRet(SceneUser user, OperationFaPaiRet msg) {
        checkThread();
        MajiangChapter chapter = roomInfo.getChapter();
        chapter.faPaiRet(user.getLocationIndex(), msg.getOpt(), msg.getPai());
    }

    public void outRet(SceneUser user, OperationOutRet msg) {
        checkThread();
        MajiangChapter chapter = roomInfo.getChapter();
        chapter.outRet(user.getLocationIndex(), msg.getPai());
    }

    public void cpghRet(SceneUser user, OperationCPGHRet msg) {
        checkThread();
        MajiangChapter chapter = roomInfo.getChapter();
        chapter.cpghRet(user.getLocationIndex(), msg.getOpt(), msg.getChi());
    }
    
    /**
     * 定跑
     */
    public void dingPaoRet(SceneUser user, OperationDingPao msg) {
        checkThread();
        MajiangChapter chapter = roomInfo.getChapter();
        roomInfo.addDingPaoCount();
        int dingPaoCount = roomInfo.getDingPaoCount();
        
        if(chapter.isDingPao()){
        	boolean[] dingPaoChache = roomInfo.getDingPaoChache();
        	dingPaoChache[user.getLocationIndex()] = true;
        	OperationDingPaoRet msgRet = chapter.dingPao(user,msg);
        	roomInfo.setState(1);
        	sendMessage(msgRet);
        }
        /**
         * 如果四个人都定跑了，就开始游戏
         */
       if(dingPaoCount==roomInfo.getUsers().length){
        	chapterStart(roomInfo.getUsers()[0]);
       }
        /*
         * 判断四个人是否都定跑了。
         * 1,2 开始游戏
         */
    }
    
    public void ready(SceneUser user){
    	roomInfo.ready(user);
    	if(roomInfo.allReady()){
    		chapterStart(roomInfo.getUsers()[0]);
    	}else{
    		sendMessage(new Ready(user.getLocationIndex()));
    	}
    }

	public void chapterStart(SceneUser user) {
		checkThread();
		
		bossClient.writeAndFlush(new ChapterStartMsg());
        if (user.getUserId() == roomInfo.getUsers()[0].getUserId()) {
        	roomInfo.getChapter().checkDingPao();
        	boolean isDingPao = roomInfo.getChapter().isDingPao();
    		if(isDingPao && roomInfo.getDingPaoCount()<roomInfo.getChapter().getRules().getUserNum()){
    			if(roomInfo.getChapter().getRules().getXuanPaoCount()==4){
    				if(roomInfo.getChapter().getChapterNums()%4==0){
    					roomInfo.setState(1);
    					sendMessage(new ShowPaoRet(),null);
        				return;
    				}
    			}else{
    				roomInfo.setState(1);
    				sendMessage(new ShowPaoRet(),null);
    				return;
    			}
    		}
            
            if (roomInfo.isFull()) {
                if (!roomInfo.isChapterStart()) {
                	roomInfo.setEndResult(null);
                	roomInfo.setState(2);
                    roomInfo.setStart(true);
                    roomInfo.clearDingPaoChache();
                    MajiangChapter chapter = roomInfo.getChapter();
                    chapter.start();
                    roomInfo.setChapterStart(true);
                    for (SceneUser u : roomInfo.getUsers()) {
                        if (u != null && u.isJoinGame()) {
                            u.sendMessage(chapter.toMessage(u.getLocationIndex(),roomInfo.getState()));
                        }
                    }
                    chapter.startNext();
                } else {
                    user.sendMessage(new GameChapterStartRet());
                    //开始的时候，如果是混牌的时候，设置最后保留的长度
                    log.error("已经开始了！user:{},room:{}", user.getUserId(), roomInfo);
                }
            } else {
                user.sendMessage(new GameChapterStartRet());
                user.noticeError("room.notEnoughUser");
            }
        } else {
            log.error("非创建者不能开始！user:{},room:{}", user.getUserId(), roomInfo);
            user.sendMessage(new GameChapterStartRet());
        }
        
        if(this.getConfig().getBoolean(Config.IS_HUIER)){
        	this.roomInfo.chapter.getRules().setBaoliuLength(0);
        }
    }


    @Override
    public void endChapter(ChapterEndResult endResult, MajiangChapter majiangChapter) {
        checkThread();
        roomInfo.setState(0);
        roomInfo.clearDingPao();
        roomInfo.setChapterStart(false);
        RoomInfo roomInfo = getRoomInfo();
        roomInfo.clearReady();
        RoomResultDO result = dbService.save(endResult, roomInfo.getRoomId(), roomInfo.getRoomCheckId(), roomInfo.getSceneId());
        //如果是第一局的时候,直接的扣掉user gold
        if(majiangChapter.getChapterNums() == 1 && !roomInfo.isProxy()){
        	dbService.delGold(roomInfo.getRoomId(), roomInfo.getCreateUserId());
        }
        ChapterEndMsg msg = new ChapterEndMsg();
        msg.setRoomId(roomInfo.getRoomId());
        Map<Integer, Integer> map = Arrays.stream(roomInfo.getUsers()).collect(
                Collectors.toMap(SceneUser::getUserId, SceneUser::getScore)
        );
        msg.setUserScoreMap(map);

        bossClient.writeAndFlush(msg);

        if (majiangChapter.getChapterNums() >= config.getInt(Config.CHAPTER_MAX)) {
            //房间结束
            this.setEnd(true);
            RoomEndMsg m = new RoomEndMsg();
            m.setCrateUserId(getRoomInfo().getCreateUserId());
            m.setRoomId(getRoomInfo().getRoomId());
            bossClient.writeAndFlush(m);
            try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            
          //显示统计结果！
           sendStaticsResultToAllUser();
        }
    }


    @Override
    public void voteDelStart(VoteDelStart msg, SceneUser user) {
        checkThread();
        SceneUser[] users = roomInfo.getUsers();
        boolean isDirectDel = true;
        roomInfo.clearVoteDel();
        for (SceneUser u :users) {
            if(u!=null && u.getUserId()!= user.getUserId() && u.isOnline()){
                isDirectDel = false;
                break;
            }
        }
        if(isDirectDel){
        	sendStaticsResultToAllUser();
            RoomEndMsg m = new RoomEndMsg();
            m.setCrateUserId(getRoomInfo().getCreateUserId());
            m.setRoomId(getRoomInfo().getRoomId());
            bossClient.writeAndFlush(m);
        }else{
            roomInfo.voteDel(user);
            sendMessage(new VoteDelSelect(user.getUserId(), user.getUserName()), user);
        }
    }
    @Override
    public void voteDelSelect(VoteDelSelectRet msg, SceneUser user) {
        checkThread();
        if (msg.getResult()) {
            if (roomInfo.voteDel(user)) {
            	sendStaticsResultToAllUser();
                RoomEndMsg m = new RoomEndMsg();
                m.setCrateUserId(getRoomInfo().getCreateUserId());
                m.setRoomId(getRoomInfo().getRoomId());
                bossClient.writeAndFlush(m);
            }
        }
    }
 
    /**
     * 将统计消息发送给所有的用户。
     */
    public void sendStaticsResultToAllUser() {
        checkThread();
        
        int roomId =  getRoomInfo().getRoomId();
        List<RoomResultDO> roomResultDOs =  roomResultDao.find(24,RoomResultDO.Table.ROOM_ID,roomId);
        StaticsResultRet staticsResultRet =  new StaticsResultRet() ;
        staticsResultRet.setLocationIndex0(0);
        staticsResultRet.setLocationIndex1(1);
        staticsResultRet.setLocationIndex2(2);
        staticsResultRet.setLocationIndex3(3);
        
        for(RoomResultDO roomResultDO:roomResultDOs){
            int fangPaoIndex = roomResultDO.getFangPaoIndex();
            int huPaiIndex = roomResultDO.getHuPaiIndex();
            //判断放炮
            switch (fangPaoIndex) {
            //这种情况有流局和自摸两种情况
            case -1:
                switch (huPaiIndex) {
                case -1:
                    //流局不用管
                    break;
                case 0:
                   staticsResultRet.setZimo0(staticsResultRet.getZimo0()+1);
                    break;
                case 1:
                	staticsResultRet.setZimo1(staticsResultRet.getZimo1()+1);
                    break;
                case 2:
                	staticsResultRet.setZimo2(staticsResultRet.getZimo2()+1);
                    break;
                case 3:
                	staticsResultRet.setZimo3(staticsResultRet.getZimo3()+1);
                    break;
                default:
                    break;
                }
                break;
            case 0:
                staticsResultRet.setFangpao0(staticsResultRet.getFangpao0()+1);
                break;
            case 1:
                staticsResultRet.setFangpao1(staticsResultRet.getFangpao1()+1);
                break;
            case 2:
                staticsResultRet.setFangpao2(staticsResultRet.getFangpao2()+1);
                break;
            case 3:
                staticsResultRet.setFangpao3(staticsResultRet.getFangpao3()+1);
                break;
            default:
                break;
            }
            
            if(fangPaoIndex>-1){
                switch (huPaiIndex) {
                case 0:
                    staticsResultRet.setJiepao0(staticsResultRet.getJiepao0()+1);
                    break;
                case 1:
                    staticsResultRet.setJiepao1(staticsResultRet.getJiepao1()+1);
                    break;
                case 2:
                    staticsResultRet.setJiepao2(staticsResultRet.getJiepao2()+1);
                    break;
                case 3:
                    staticsResultRet.setJiepao3(staticsResultRet.getJiepao3()+1);
                    break;

                default:
                    break;
                }
            }
            
            UserPaiInfo[] userPaiInfos=roomResultDO.getUserPaiInfos();
            for(UserPaiInfo userPaiInfo:userPaiInfos){
                switch (userPaiInfo.getLocationIndex()) {
                case 0:
                    staticsResultRet.setAngang0(staticsResultRet.getAngang0() + userPaiInfo.getAnGang().size());
                    staticsResultRet.setMinggang0(staticsResultRet.getMinggang0()+userPaiInfo.getDaMingGang().size()+userPaiInfo.getXiaoMingGang().size());
                    break;
                case 1:
                    staticsResultRet.setAngang1(staticsResultRet.getAngang1() + userPaiInfo.getAnGang().size());
                    staticsResultRet.setMinggang1(staticsResultRet.getMinggang1()+userPaiInfo.getDaMingGang().size()+userPaiInfo.getXiaoMingGang().size());
                    break;
                case 2:
                    staticsResultRet.setAngang2(staticsResultRet.getAngang2() + userPaiInfo.getAnGang().size());
                    staticsResultRet.setMinggang2(staticsResultRet.getMinggang2()+userPaiInfo.getDaMingGang().size()+userPaiInfo.getXiaoMingGang().size());
                    break;
                case 3:
                    staticsResultRet.setAngang3(staticsResultRet.getAngang3() + userPaiInfo.getAnGang().size());
                    staticsResultRet.setMinggang3(staticsResultRet.getMinggang3()+userPaiInfo.getDaMingGang().size()+userPaiInfo.getXiaoMingGang().size());
                    break;
                default:
                    break;
                }
            }
            staticsResultRet.setScore0( staticsResultRet.getScore0() +  roomResultDO.getScore0());
            staticsResultRet.setScore1(staticsResultRet.getScore1() + roomResultDO.getScore1() );
            staticsResultRet.setScore2(staticsResultRet.getScore2() + roomResultDO.getScore2() );
            staticsResultRet.setScore3(staticsResultRet.getScore3() + roomResultDO.getScore3() );
        }
       
        
        roomInfo.setEndResult(staticsResultRet);
        sendMessage(staticsResultRet);
    }
    
    @Override
    public void savePlayBackRecord(ChapterEndResult endResult, MajiangChapter majiangChapter, JSONObject record){
    	String checkRoomId = getRoomInfo().getRoomCheckId();
    	int roomId = getRoomInfo().getRoomId();
    	String chapterIndex = majiangChapter.getChapterNums()+"";
    	try {
			if(record != null){
		        GameChapterEnd gameChapterEnd = endResult.toMessage(endResult.isQiDuiHu());
		        ByteBuf byteBuf = Unpooled.directBuffer();
		    	ByteBufOutputStream outStream = new ByteBufOutputStream(byteBuf);
				Output out = MarkCompressOutput.create(outStream);
				
				byteBuf.clear();
				out.writeInt(GameChapterEnd.TYPE);
				out.writeInt(GameChapterEnd.ID);
		        gameChapterEnd.encode(out);
		        byte[] req = new byte[byteBuf.readableBytes()];
				byteBuf.readBytes(req);
		        String gameChapterByteStr = UtilByteTransfer.bytesToHexString(req);
		        record.put("gameChapterEnd", gameChapterByteStr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	dbService.saveRoomRecord(roomId, record, checkRoomId, chapterIndex);
    }

	public void voice(Voice msg) {
		sendMessage(msg);
	}
}
