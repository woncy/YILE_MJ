package game.zjh.scene.room;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import ZJH.data.ZJHConfig;
import game.scene.msg.RoomEndMsg;
import game.scene.services.ZJHDbService;
import game.zjh.scene.msg.RoomOverMsg;
import game.zjh.scene.msg.ZJHChapterStartMsg;
import game.zjh.scene.net.BossClient;
import game.zjh.scene.room.poker.ZJHChapter;
import mj.net.message.game.zjh.AddZhu;
import mj.net.message.game.zjh.ComparePoker;
import mj.net.message.game.zjh.GameRoomInfoT;
import mj.net.message.game.zjh.GameUserinfo2;
import mj.net.message.game.zjh.VoteDelZJHSelect;
import mj.net.message.game.zjh.VoteDelZJHSelectResult;
import mj.net.message.game.zjh.ZJHOperation;

public class ZJHRoomImpl extends ZJHRoom{
	 private long prevTime = 0;
	 private long startTime = 0;
	 
	 @Autowired
	 private BossClient bossClient;
	 @Autowired
	 private ZJHDbService dbService;
	
	public ZJHRoomImpl(ZJHRoomAsyncService roomAsyncService, ZJHConfig config) {
		super(roomAsyncService);
		this.config = config;
	}

	@Override
	protected void frame() {
		  long now = System.currentTimeMillis();
	        if (prevTime == 0) {
	            prevTime = now;
	        }
//	      log.info("frame:{}", now - prevTime);
	        innerFrame(now);
	        prevTime = now;
		
	}

    private void innerFrame(long now) {
        //开始游戏逻辑！
//        if (zjhroomInfo.isStart()) {
//            MajiangChapter chapter = roomInfo.getChapter();
//            if(chapter.isOperationTimeOut(now)){
//                //超时，强制出票
//                chapter.forceChuPai();
//            }
//        }
    }
    public void join(ZJHSceneUser joinSceneUser, List<ZJHSceneUser> sceneUsers, Consumer<Boolean> callback) {
        run(() -> {
            if (isEnd()) {
                callback.accept(false);
            } else {
                for (ZJHSceneUser sceneUser : sceneUsers) {
                	zjhRoomInfo.updateUserInfo(sceneUser);
                }
                //更新 网管和session
                ZJHSceneUser sceneUser = zjhRoomInfo.getUserInfo(joinSceneUser.getLocationIndex());

                sceneUser.setOnline(true);
                sceneUser.setGatewayId(joinSceneUser.getGatewayId());
                sceneUser.setSessionId(joinSceneUser.getSessionId());

                callback.accept(true);
            }
        });
    }
    /**
     * 玩家进入游戏
     */
    public void joinZJHGame(ZJHSceneUser sceneUser) {
        checkThread();
        sceneUser.setJoinGame(true);
        ZJHChapter chapter = zjhRoomInfo.getChapter();
        for(int i=0;i<chapter.getZjhUserPlace().size();i++){
        	if(chapter.getZjhUserPlace().get(i).getLocationIndex()==sceneUser.getLocationIndex()){
        		chapter.getZjhUserPlace().get(i).setOffLine(false);
        	}
        }
        //同步游戏信息到客户端
        GameRoomInfoT gameInfo = zjhRoomInfo.toMessage(sceneUser.getLocationIndex());
        ZJHConfig config = sceneUser.getRoom().getConfig();
       
        int chapterMax = config.getInt("{"+ZJHConfig.CHAPTERMAX);
        gameInfo.setMoShi(config.getBoolean(ZJHConfig.MOSHI));
        gameInfo.setChapterMax(chapterMax);
        gameInfo.setRoomCheckId(sceneUser.getRoom().getRoomInfo().getRoomCheckId());
        gameInfo.setDaZhu(config.getInt(ZJHConfig.DANZHU));
        gameInfo.setInitScore(config.getInt(ZJHConfig.CHUSHIFEN));
        gameInfo.setUserNum(config.getInt(ZJHConfig.USERNUM));//玩家数量  需要去掉
        gameInfo.setChapterNums(gameInfo.getChapterNum());
        gameInfo.setStart(true);
        sceneUser.sendMessage(gameInfo);
        //通知其他玩家
        GameUserinfo2 msg = sceneUser.toMessage();
        zjhRoomInfo.excuteDistanceKm(msg, sceneUser);
        //计算距离
        sendMessage(msg, sceneUser);
    }
    
    /**
     * 开始游戏
     *@param user
     *@return
     * 2017年7月7日
     */
    public void gameStart(ZJHSceneUser user) {
    	checkThread();
		bossClient.writeAndFlush(new ZJHChapterStartMsg());
        if (user.getUserId() == zjhRoomInfo.getCreateUserId()) {
            if (zjhRoomInfo.isFull()) {//房間人數不足
                if (!zjhRoomInfo.isChapterStart()) {
                	zjhRoomInfo.setStart(true);
                	ZJHChapter chapter = zjhRoomInfo.getChapter();
                    chapter.start();
                    zjhRoomInfo.setChapterStart(true);
//                    for (ZJHSceneUser u : zjhRoomInfo.getZjhUsers()) {
//                        if (u != null && u.isJoinGame()) {
//                            u.sendMessage(chapter.toMessage(u.getLocationIndex()));
//                        }
//                    }
                    chapter.startNext(user);
                } else {
                    //user.sendMessage(new GameChapterStartRet());
                    log.error("已经开始了！user:{},room:{}", user.getUserId(), zjhRoomInfo);
                }
            } else {
//               // user.sendMessage(new GameChapterStartRet());
//                //user.noticeError("room.notEnoughUser,fa");
            	log.error("玩家");
            }
        } else {
            log.error("非创建者不能开始！user:{},room:{}", user.getUserId(), zjhRoomInfo);
           // user.sendMessage(new GameChapterStartRet());
        }
        
      
	}
    /**
     * 房间结束
     */
	@Override
	public void endChapter(ZJHChapter zjhChapter) {
		 checkThread();
		 zjhRoomInfo.setChapterStart(false);
		 ZJHRoomInfo zjhRoomInfo = getZjhRoomInfo();
		int chapterNums = zjhRoomInfo.getChapter().getChapterNums();//当前局数
		if(chapterNums == 1){//第一局扣除房卡
			dbService.delGold(zjhRoomInfo.getRoomId(), zjhRoomInfo.getCreateUserId());
		}
		if(chapterNums == zjhChapter.getRoom().getConfig().getInt(ZJHConfig.CHAPTERMAX)){
			dbService.save(zjhChapter.getZjhUserPlace(),zjhRoomInfo.getRoomId());
			this.setEnd(true);
			RoomOverMsg msg = new RoomOverMsg();
			msg.setRoomId(getZjhRoomInfo().getRoomId());
			msg.setCrateUserId(getZjhRoomInfo().getCreateUserId());
			bossClient.writeAndFlush(msg);
		}
	}
	/**
	 * 房间投票结果
	 */
	@Override
	public void VoteResult(VoteDelZJHSelectResult msg, ZJHSceneUser user) {
		checkThread();
        if (msg.getResult()) {
            Integer count = voteDelInfo.get(msg.getUserId());
            if (count != null) {
                voteDelInfo.put(msg.getUserId(), count + 1);
            }
            if (count >= 0) {
            	RoomOverMsg m = new RoomOverMsg();
                m.setCrateUserId(getRoomInfo().getCreateUserId());
                m.setRoomId(getRoomInfo().getRoomId());
                bossClient.writeAndFlush(m);
            }
        }
	}
	/**
	 * 发起投票
	 */
	@Override
	public void VoteStart(mj.net.message.game.zjh.VoteStart msg, ZJHSceneUser user) {
		checkThread();
		ArrayList<ZJHSceneUser> getzjhUsers = zjhRoomInfo.getzjhUsers();
		boolean isDirectDel = true;
        for (ZJHSceneUser u :getzjhUsers) {
            if(u!=null && u.getUserId()!=user.getUserId() && u.isOnline()){
                isDirectDel = false;
                break;
            }
        }
        if(isDirectDel){
        	RoomOverMsg m = new RoomOverMsg();
            m.setCrateUserId(getRoomInfo().getCreateUserId());
            m.setRoomId(getRoomInfo().getRoomId());
            bossClient.writeAndFlush(m);
        }else{
            voteDelInfo.put(user.getUserId(), 0);
            sendMessage(new VoteDelZJHSelect(user.getUserId(), user.getUserName()), user);
        }
        
	}
	/**
	 * 操作
	 *@param msg
	 *@param user
	 *@return
	 * 2017年7月7日
	 */

	public void Operation(ZJHOperation msg, ZJHSceneUser user) {
		 	checkThread();
	         ZJHChapter chapter = zjhRoomInfo.getChapter();
	        chapter.Operation(msg, user);
	}
	/**
	 * 比牌
	 *@param msg
	 *@param user
	 *@return
	 * 2017年7月11日
	 */
	public void comparPoker(ComparePoker msg, ZJHSceneUser user) {
		checkThread();
		ZJHChapter chapter = zjhRoomInfo.getChapter();
		chapter.comparePoker(user, msg.getIndex());
	}
	/**
	 * 比牌
	 *@param msg
	 *@param user
	 *@return
	 * 2017年7月11日
	 */
	public void addZhu(AddZhu msg, ZJHSceneUser user) {
		checkThread();
		ZJHChapter chapter = zjhRoomInfo.getChapter();
		chapter.jiaZhu(user, msg.getZhu());
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
	
	
	
	
//	/**
//	 * 弃牌保存数据
//	 */
//	@Override
//	public void oneChapter(ZJHChapter zjhChapter,ZJHSceneUser user, int[] pokerNum) {
//		TbPkChapterDO chapter = new TbPkChapterDO();
//		TbPkRoomDO room = new TbPkRoomDO();
//		UserDO2 userDo = new UserDO2();
//		userDo.setId(user.getUserId());
//		TbPkUserChapterDO userChapter = new TbPkUserChapterDO();
//		int score = zjhChapter.getZjhUserPlace()[user.getLocationIndex()].getScore();
//		int roomId = user.getRoom().getRoomInfo().getRoomId();
//		int chapterNums = zjhChapter.getChapterNums();
//		String pokeNums="";
//		for (int i = 0; i < pokerNum.length; i++) {
//			pokeNums = pokerNum[i]+"";
//		}
//		userChapter.setPais(pokeNums);
//		userChapter.setUser(userDo);
//		userChapter.setScore(score);
//		room.setId(roomId);
//		chapter.setNum(chapterNums);
//		chapter.setRoom(room);
//		dbService.saveOneChapter(chapter,userChapter);
//	}
//	

	

}
