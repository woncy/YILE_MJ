package game.scene.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import game.boss.SceneUserInfo;
import game.douniu.scene.msg.ChapterUserMsg;
import game.douniu.scene.msg.CheckExitRoom2Msg;
import game.douniu.scene.msg.ChapterEnd2Msg;
import game.douniu.scene.msg.ChapterStart2Msg;
import game.douniu.scene.msg.DelRoom2Msg;
import game.scene.net.BossClient;
import game.scene.room.RoomInfo.STATE;
import mj.data.Config;
import mj.net.message.game.UserOffline;
import mj.net.message.game.Voice;
import mj.net.message.game.VoteDelSelectRet;
import mj.net.message.game.VoteDelStart;
import mj.net.message.game.douniu.DNChapterPKResult;
import mj.net.message.game.douniu.DNDismissUserResult;
import mj.net.message.game.douniu.DNDismissVote;
import mj.net.message.game.douniu.DNGameExitUser;
import mj.net.message.game.douniu.DNGameReady;
import mj.net.message.game.douniu.DNGameStart;
import mj.net.message.game.douniu.DNGameZhuang;
import mj.net.message.game.douniu.DNQiangZhuang;
import mj.net.message.game.douniu.DNShowXiaZhu;
import mj.net.message.game.douniu.DNXiaZhu;
import mj.net.message.game.douniu.RoomResult;
import mj.net.message.game.douniu.UserPkResult;
import mj.net.message.game.douniu.UserRoomResult;

/**
 * @author zuoge85@gmail.com on 16/10/18.
 */
public class RoomImpi extends Room {
    private long prevTime = 0;
    private long startTime = 0;
    
    private Timer qiangzhuangTimer[] = new Timer[5];
    private Timer xuanBeiTimer[] = new Timer[5];
    private Timer pkTimer[] = new Timer[5];
    
    @Autowired
    private BossClient bossClient;
    
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
        
    }

    public void join(SceneUser joinSceneUser, List<SceneUser> sceneUsers, boolean isFirst,Consumer<Boolean> callback) {
        run(() -> {
            if (isEnd()) {
                callback.accept(false);
            } else {
            	
                for (SceneUser sceneUser : sceneUsers) {
                //	dbService.updateSceneUser(sceneUser);
                    roomInfo.updateUserInfo(sceneUser,isFirst);
                }
                //更新 网管和session
                SceneUser sceneUser = roomInfo.getUserInfo(joinSceneUser.getLocationIndex());
                sceneUser.setOnline(true);
                sceneUser.setJoinGame(true);
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
        		roomInfo.exitRoom(userId);
                callback.accept(true);
//                sendMessage(new GameExitUser(userId));
        });
    }

    public void offline(int userId) {
        run(() -> {
            SceneUser user = getRoomInfo().getUserInfoByUserId(userId);
            user.setOnline(false);
            user.setJoinGame(false);
            sendMessage(new UserOffline(user.getLocationIndex()), user);
          
        });
    }

    public void delRoom( Consumer<Boolean> callback) {
        run(() -> {
            callback.accept(true);
        });
    }
	public void voice(Voice msg) {
		sendMessage(msg);
	}
	public void joinGame(SceneUser user) {
		if(roomInfo.getState()==STATE.FIRSTSTART){
			if(user.getLocationIndex()>0){
				roomInfo.setState(STATE.READYING);
			}
		}
		user.sendMessage(roomInfo.toMessage(user.getLocationIndex()));
		sendMessage(user.toMessage(), user);
	}
	
	@Override
	public void voteDelStart(VoteDelStart msg, SceneUser user) {
		
	}
	@Override
	public void voteDelSelect(VoteDelSelectRet msg, SceneUser user) {
		
	}
	public void executeQiangZhuangTimer(){
		SceneUser[] users = roomInfo.getUsers();
		for (int i = 0; i < users.length; i++) {
			SceneUser sceneUser = users[i];
			if(sceneUser!=null){
				qiangzhuangTimer[sceneUser.getLocationIndex()] = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						qiangzhuang(new DNQiangZhuang(sceneUser.getLocationIndex(), false), sceneUser);
					}
				};
				qiangzhuangTimer[sceneUser.getLocationIndex()].schedule(task, 16*1000);
			}
		}
	}	
	public void executeXuanBeiTimer(){
		SceneUser[] users = roomInfo.getUsers();
		for (int i = 0; i < users.length; i++) {
			SceneUser user = users[i];
			if(user!=null){
				xuanBeiTimer[user.getLocationIndex()] = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						xiazhu(new DNXiaZhu(user.getLocationIndex(), 1), user);
					}
				};
				xuanBeiTimer[user.getLocationIndex()].schedule(task, 16*1000);
			}
			
			
		}
	}
	public void executePkTimer(){
		SceneUser[] users = roomInfo.getUsers();
		for (int i = 0; i < users.length; i++) {
			SceneUser user = users[i];
			if(user!=null){
				pkTimer[user.getLocationIndex()] = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						kaipai(user);
					}
				};
				pkTimer[user.getLocationIndex()].schedule(task, 16*1000);
			}
			
			
		}
	}
	private void gameStartToBoss(){
		ChapterStart2Msg startMsg = new ChapterStart2Msg();
		startMsg.setRoomId(roomInfo.getRoomId());
		startMsg.setNum(roomInfo.getCurrentChapterNum());
		bossClient.writeAndFlush(startMsg);
	}
	public void gameStart(SceneUser user) {
		if(user.getLocationIndex()==0){
			boolean aa = true;
			if(roomInfo.getCurrentChapterNum()==0){
				aa = roomInfo.isAllReady2();
			}
			if(aa){
				ready(new DNGameReady(0),user);
				if(roomInfo.isAllReady()){
//				roomInfo.addCurrentChapter();
					roomInfo.setBeforeFirstStart(false);
					String zhuang = config.getString("zhuang");
					if(zhuang!=null&&"lunzhuang".equals(zhuang)){
						int userNum=0;
						SceneUser[] users = roomInfo.getUsers();
						for (int i = 0; i < users.length; i++) {
							SceneUser sceneUser = users[i];
							if(sceneUser!=null){
								userNum++;
							}
						}
						int chapterNum = roomInfo.getCurrentChapterNum();
						int zhuangIndex = 0;
						if(userNum!=0){
							zhuangIndex = (chapterNum-1)%userNum;
						}
						System.out.println("用户数量:"+userNum+"局数："+chapterNum+"庄位置："+zhuangIndex);
						for (int i = 0; i < users.length; i++) {
							SceneUser sceneUser = users[i];
							if(sceneUser!=null){
								sendMessage(new DNGameStart(false));
								if(sceneUser.getLocationIndex()==zhuangIndex){
									qiangzhuang(new DNQiangZhuang(sceneUser.getLocationIndex(), true), sceneUser);
								}else{
									qiangzhuang(new DNQiangZhuang(sceneUser.getLocationIndex(), false), sceneUser);
								}
							}
						}
					}else if(zhuang!=null&&"KPQZ".equals(zhuang)){
						roomInfo.gameStartClear();
						roomInfo.getChapter().startGame(zhuang,true);
						sendMessage(new DNGameStart(true));
						executeQiangZhuangTimer();
					}else{
						sendMessage(new DNGameStart(true));
						executeQiangZhuangTimer(); 
					}
					gameStartToBoss();
				}
			}
//			}else{
//				sendMessage(new DNGameStart(false)); 
//			}
		}
	}
	public void ready(DNGameReady msg,SceneUser user) {
		if(roomInfo.getState()!=STATE.READYING){
			return;
		}
		msg.setIndex(user.getLocationIndex());
		roomInfo.ready(user);
		sendMessage(msg,null);
		if(roomInfo.isAllReady()){
			roomInfo.setState(STATE.QIANG_ZHUANG);
			roomInfo.addCurrentChapter();
			gameStart(roomInfo.getUsers()[0]);
		}
	}
	public void qiangzhuang(DNQiangZhuang msg, SceneUser user) {
		if(roomInfo.getState()!=STATE.QIANG_ZHUANG){
			return;
		}
		if(qiangzhuangTimer[user.getLocationIndex()]!=null){
			qiangzhuangTimer[user.getLocationIndex()].cancel();
			qiangzhuangTimer[user.getLocationIndex()] = null;
		}
		boolean qiang = msg.isQiang();
		msg.setIndex(user.getLocationIndex());
		sendMessage(msg,null);
		boolean isall = roomInfo.qingZhuang(user);
		if(isall){
			roomInfo.getChapter().clearSeats();
		}
		int zhuangIndex = roomInfo.getChapter().qiangZhuang(user.getLocationIndex(),isall,qiang);
		if(zhuangIndex>-1){
			sendMessage(new DNGameZhuang(zhuangIndex),null);
			chapterStartXiaZhu();
		}
		
	}
	private void chapterStartXiaZhu() {
		roomInfo.setState(STATE.XUAN_BEI);
		sendMessage(new DNShowXiaZhu(),roomInfo.getUserInfo(roomInfo.getChapter().getZhuangIndex()));
		executeXuanBeiTimer();

	}
	public void kaipai(SceneUser user) {
		if(roomInfo.getState()!=STATE.PKING){
			return;
		}
		if(pkTimer[user.getLocationIndex()]!=null){
			pkTimer[user.getLocationIndex()].cancel();
			pkTimer[user.getLocationIndex()]=null;
		}
		boolean isAll = roomInfo.getChapter().kaiPai(user);
		
		if(isAll){
			chapterEnd();
		}
	}
	private void chapterEnd() {
		DNChapterPKResult result = roomInfo.getChapter().pk();
		sendMessage(result, null);
		roomInfo.setState(STATE.READYING);
		chapterEndToBoss(result);
		roomInfo.addPkResult(result);
		if(roomInfo.getCurrentChapterNum()>=roomInfo.getMaxChapterNum()){
			delRoom();
		}
	}
	
	private void showRoomResult(){
		List<UserRoomResult> userResults = new ArrayList<UserRoomResult>();
		for (int i = 0; i < roomInfo.getUsers().length; i++) {
			SceneUser sceneUser = roomInfo.getUsers()[i];
			if(sceneUser!=null){
				UserRoomResult result = roomInfo.getUserStates()[sceneUser.getLocationIndex()].toMessage();
				result.setLocationIndex(sceneUser.getLocationIndex());
				userResults.add(result);
			}
		}
		RoomResult roomResult = new RoomResult();
		roomResult.setResults(userResults);
		sendMessage(roomResult,null);
	}
	
	
	private void delRoom(){
		showRoomResult();
		List<SceneUserInfo> infos = new ArrayList<SceneUserInfo>();
		for (int i = 0; i < roomInfo.getUsers().length; i++) {
			SceneUser sceneUser = roomInfo.getUsers()[i];
			if(sceneUser!=null){
				SceneUserInfo userInfo = new SceneUserInfo();
				userInfo.setGatewayId(sceneUser.getGatewayId());
				userInfo.setSessionId((short)sceneUser.getSessionId());
				userInfo.setScore(sceneUser.getScore());
				userInfo.setUserId(sceneUser.getUserId());
				infos.add(userInfo);
				sceneUser.setScore(0);
			}
		}
		
		DelRoom2Msg msg = new DelRoom2Msg();
		msg.setCreateUserId(roomInfo.getCreateUserId());
		msg.setInfos(infos);
		msg.setRoomId(roomInfo.getRoomId());
		msg.setSceneId(roomInfo.getSceneId());
		bossClient.writeAndFlush(msg);
	}

	private void chapterEndToBoss(DNChapterPKResult result) {
		ChapterEnd2Msg endMsg = new ChapterEnd2Msg();
		endMsg.setRoomId(roomInfo.getRoomId());
		endMsg.setNum(roomInfo.getCurrentChapterNum());
		endMsg.setZhuangIndex(result.getZhuangIndex());
		endMsg.setZhuangUserId(roomInfo.getChapter().getSeats()[result.getZhuangIndex()].getUserid());
		List<ChapterUserMsg> userResults = new ArrayList<ChapterUserMsg>();
		ChapterUserMsg zhuangmsg = new ChapterUserMsg();
		zhuangmsg.setLocationIndex(result.getZhuangIndex());
		zhuangmsg.setPais(result.getPai());
		zhuangmsg.setPaiType(result.getPaiType());
		zhuangmsg.setScore(result.getSocre());
		zhuangmsg.setUserId(roomInfo.getUsers()[result.getZhuangIndex()].getUserId());
		userResults.add(zhuangmsg);
		List<UserPkResult> userPkResults = result.getUserResults();
		if(userPkResults!=null){
			for (int i = 0; i < userPkResults.size(); i++) {
				UserPkResult userPkResult = userPkResults.get(i);
				ChapterUserMsg usermsg = new ChapterUserMsg();
				usermsg.setLocationIndex(userPkResult.getIndex());
				usermsg.setPais(userPkResult.getPai());
				usermsg.setPaiType(userPkResult.getPaiType());
				usermsg.setScore(userPkResult.getScore());
				usermsg.setUserId(roomInfo.getUsers()[userPkResult.getIndex()].getUserId());
				userResults.add(usermsg);
			}
		}
		endMsg.setChapterUserMsgs(userResults);
		bossClient.writeAndFlush(endMsg);
	}
	
	public void exitUser(DNGameExitUser msg, SceneUser user){
		if(roomInfo.isBeforeFirstStart()){
			exitUserToBoss(user);
			roomInfo.exitRoom(user.getUserId());
			checkDelRoom();
			sendMessage(new DNGameExitUser(user.getLocationIndex()));
		}
	}

	private void exitUserToBoss(SceneUser user) {
		CheckExitRoom2Msg msg = new CheckExitRoom2Msg();
		msg.setJoinGatewayId(user.getGatewayId());
		msg.setSceneId(user.getSessionId());
		msg.setRoomId(roomInfo.getRoomId());
		msg.setSceneId(roomInfo.getSceneId());
		msg.setUserId(user.getUserId());
		bossClient.writeAndFlush(msg);
	}

	public void xiazhu(DNXiaZhu msg, SceneUser user) {
		if(roomInfo.getState()!=STATE.XUAN_BEI){
			return;
		}
		if(xuanBeiTimer[user.getLocationIndex()]!=null){
			xuanBeiTimer[user.getLocationIndex()].cancel();
			xuanBeiTimer[user.getLocationIndex()] = null;
		}
		roomInfo.getChapter().saveZhu(msg.getZhu(),user.getLocationIndex());
		msg.setIndex(user.getLocationIndex());
		sendMessage(msg,null);
		
		boolean isAll = roomInfo.getChapter().xiazhu(msg,user);
		if(isAll){
			roomInfo.gameStartClear();
			String str = config.getString("zhuang");
			roomInfo.getChapter().startGame(str,false);
			roomInfo.setState(STATE.PKING);
			executePkTimer();
		}
	}
	private Timer voteDelRoomTimer[]  = new Timer[5];
	public void voteDelRoom(DNDismissVote msg, SceneUser user) {
		roomInfo.startVoteDelRoom();
		msg.setIndex(user.getLocationIndex());
		sendMessage(msg,null);
		executeVoteDelTimer();
	}
	private void executeVoteDelTimer() {
		for (int i = 0; i < roomInfo.getUsers().length; i++) {
			SceneUser u = roomInfo.getUsers()[i];
			if(u!=null){
				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						voteDelRoomUserResult(new DNDismissUserResult(u.getLocationIndex(), true), u);
					}
				};
				timer.schedule(task, 30*1000);
				voteDelRoomTimer[i] = timer;
			}
		}
	}

	public void voteDelRoomUserResult(DNDismissUserResult msg, SceneUser user) {
		if(voteDelRoomTimer[user.getLocationIndex()]!=null){
			voteDelRoomTimer[user.getLocationIndex()].cancel();
			voteDelRoomTimer[user.getLocationIndex()] = null;
		}
		if(roomInfo.isVoteTime()){
			msg.setIndex(user.getLocationIndex());
			sendMessage(msg,null);
			boolean result = roomInfo.voteUserResult(user, msg);
			if(result){
				delRoom();
				roomInfo.endVoteDelRoom();
				for (int i = 0; i < voteDelRoomTimer.length; i++) {
					Timer timer = voteDelRoomTimer[i];
					if(timer!=null){
						timer.cancel();
						voteDelRoomTimer[i]=null;
					}
					
				}
			}else{
				if(roomInfo.isAllCommitVoteDel()){
					roomInfo.endVoteDelRoom();
					for (int i = 0; i < voteDelRoomTimer.length; i++) {
						Timer timer = voteDelRoomTimer[i];
						if(timer!=null){
							timer.cancel();
							voteDelRoomTimer[i]=null;
						}
						
					}
				}
			}
		}
		
	}
	public void checkDelRoom() {
		SceneUser[] users = roomInfo.getUsers();
		int userNum = 0;
		for (int i = 0; i < users.length; i++) {
			SceneUser sceneUser = users[i];
			if(sceneUser!=null){
				userNum++;
			}
		}
		if(userNum==0){
			DelRoom2Msg msg = new DelRoom2Msg();
			msg.setCreateUserId(roomInfo.getCreateUserId());
			msg.setInfos(null);
			msg.setRoomId(roomInfo.getRoomId());
			msg.setSceneId(roomInfo.getSceneId());
			bossClient.writeAndFlush(msg);
		}
	}
		
}
