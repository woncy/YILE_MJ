package game.scene.pdk.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.isnowfox.core.net.message.Message;
import com.isnowfox.core.thread.FrameQueueContainer;

import game.boss.SceneUserInfo;
import game.scene.msg.CheckDelRoomMsg;
import game.scene.msg.CheckExitRoomMsg;
import game.scene.msg.CheckJoinRoomMsg;
import game.scene.msg.CheckJoinRoomRetMsg;
import game.scene.msg.CheckOfflineRoomMsg;
import game.scene.msg.UserInfo;
import game.scene.pdk.net.BossClient;
import mj.data.Config;
import mj.data.PdkConfig;
import mj.net.handler.MessageHandler;
import mj.net.message.game.GameDelRoom;

/**
 * @author zuoge85@gmail.com on 16/9/30.
 */
@Service
public class PdkRoomService extends FrameQueueContainer implements ApplicationContextAware {
    private static final int FRAME_TIME_SPAN = 33;
    private static final int RUN_QUEUE_MAX = 1024;
    private static final int DEFAULT_USER_NUM = 4;

    private static final Logger log = LoggerFactory.getLogger(PdkRoomService.class);
    @Value("${sceneId}")
    private int sceneId;
    private RoomAsyncService roomAsyncService;
    private ApplicationContext applicationContext;
    
    private HashMap<Integer, PdkRoomImpi> map = new HashMap<>();
    private HashMap<Integer, PdkSceneUser> sceneUserMap = new HashMap<>();
    /**
     * 通过id 映射到map
     * id 是 gatewayId<<32|sessionId
     */
    private HashMap<Long, PdkRoom> gatewayIdUnionSessionIdMap = new HashMap<>();

    /**
     * 通过id 映射到map
     * id 是 gatewayId<<32|sessionId
     */
    private HashMap<Long, PdkSceneUser> gatewayIdUnionSessionIdUserMap = new HashMap<>();

    public PdkRoomService() {
        super(FRAME_TIME_SPAN, RUN_QUEUE_MAX);
        start();
    }

    @Autowired
    private BossClient bossClient;


    public void setRoomAsyncService(RoomAsyncService roomAsyncService) {
        this.roomAsyncService = roomAsyncService;
    }


    public void checkOfflineRoom(CheckOfflineRoomMsg msg) {
    	
    	Runnable checkRoomThread = new Runnable() {
			@Override
			public void run() {
				PdkRoomImpi room = map.get(msg.getRoomId());
	            if (room == null) {
	                log.info("房间已经不存在了");
	                return;
	            }
	            room.offline(msg.getUserId());
				
			}
		};
        run(checkRoomThread);
    }

    public void checkJoinRoom(CheckJoinRoomMsg msg) {
        //创建房间
        run(() -> {
        	
            PdkRoomImpi room = map.get(msg.getRoomId());
            if (room == null) {
            	
                PdkConfig config = new PdkConfig(msg.getOptions());
                room = new PdkRoomImpi(roomAsyncService, config);
                int userNum = 0;
                try{
                	userNum = config.getInt(Config.USER_NUM);
	                if(userNum<2){
	                	userNum = DEFAULT_USER_NUM;
	                }
                }catch(Exception e){
                	userNum = DEFAULT_USER_NUM;
                }
                
                PdkRoomInfo roomInfo = new PdkRoomInfo(room, userNum,config.getInt(""));
                roomInfo.setCreate_user_id(msg.getCreateUserId());
                room.setRoomInfo(roomInfo);
                room.start();
                applicationContext.getAutowireCapableBeanFactory().autowireBean(room);
                map.put(msg.getRoomId(), room);
            }
            //进入房间成功
            PdkSceneUser joinSceneUser = null;

            List<PdkSceneUser> sceneUsers = new ArrayList<>();
            
            for (UserInfo userInfo : msg.getUserInfos()) {
                PdkSceneUser sceneUser = sceneUserMap.get(userInfo.getUserId());
                
                if (sceneUser == null) {
                    sceneUser = new PdkSceneUser();
                    sceneUser.setJoinGame(false);
                   applicationContext.getAutowireCapableBeanFactory().autowireBean(sceneUser);
                }
                
                sceneUser.setRoom(room);
                sceneUser.setLocationIndex(userInfo.getLocationIndex());
                sceneUser.setUserName(userInfo.getUserName());
                sceneUser.setAvatar(userInfo.getAvatar());
                sceneUser.setSex(userInfo.getSex());
                sceneUser.setGold(userInfo.getGold());
                sceneUser.setUserId(userInfo.getUserId());
                sceneUser.setIp(userInfo.getIp());
                sceneUser.setLongitude(userInfo.getLongitude());
                sceneUser.setLatitude(userInfo.getLatitude());
                sceneUser.setScore(userInfo.getScore());
                if (userInfo.getUserId() == msg.getJoinUserId()) {
                    sceneUser.setSessionId(msg.getJoinSessionId());
                    sceneUser.setGatewayId(msg.getJoinGatewayId());
                    joinSceneUser = sceneUser;
                }
                sceneUsers.add(sceneUser);
                sceneUserMap.put(userInfo.getUserId(), sceneUser);
            }
            long gatewayIdUnionSessionId = getGatewayIdUnionSessionId(msg.getJoinSessionId(), msg.getJoinGatewayId());
            gatewayIdUnionSessionIdMap.put(gatewayIdUnionSessionId, room);
            gatewayIdUnionSessionIdUserMap.put(gatewayIdUnionSessionId, joinSceneUser);
            room.join(joinSceneUser, sceneUsers, (r) -> {
                //进入房间成功
                CheckJoinRoomRetMsg ret = new CheckJoinRoomRetMsg();
                ret.setRoomId(msg.getRoomId());
                ret.setJoinUserId(msg.getJoinUserId());
                ret.setJoinGatewayId(msg.getJoinGatewayId());
                ret.setJoinSessionId(msg.getJoinSessionId());
                ret.setSucccess(r);
                bossClient.writeAndFlush(ret);
            });
        });
    }

    public void checkExitRoom(CheckExitRoomMsg msg) {
        run(() -> {
            PdkRoomImpi room = map.get(msg.getRoomId());
            if (room == null) {
                return;
            }

            room.exitRoom(msg.getUserId(), (ret) -> {
                run(() -> {
                    if (ret) {
                        long gatewayIdUnionSessionId = getGatewayIdUnionSessionId(msg.getJoinSessionId(), msg.getJoinGatewayId());
                        gatewayIdUnionSessionIdMap.remove(gatewayIdUnionSessionId);
                        gatewayIdUnionSessionIdUserMap.remove(gatewayIdUnionSessionId);
                    }
                    msg.setResult(ret);
                    bossClient.writeAndFlush(msg);
                });
            });
        });
    }

    public void checkDelRoom(CheckDelRoomMsg msg) {
        run(() -> {
            PdkRoomImpi room = map.get(msg.getRoomId());
            if (room == null) {
                return;
            }
            room.delRoom(msg.getUserId(), (ret) -> {
                //进入房间成功
                run(() -> {
                    List<SceneUserInfo> userInfos = new ArrayList<SceneUserInfo>();
                    if (ret) {
                        PdkRoomInfo roomInfo = room.getRoomInfo();
                        for (PdkSceneUser u : roomInfo.getUsers()) {
                            if (u != null) {
                                long gatewayIdUnionSessionId = getGatewayIdUnionSessionId(u.getSessionId(), u.getGatewayId());
                                gatewayIdUnionSessionIdMap.remove(gatewayIdUnionSessionId);
                                gatewayIdUnionSessionIdUserMap.remove(gatewayIdUnionSessionId);
                                u.sendMessage(new GameDelRoom(room.isEnd(),room.isStart()));
                                if(room.isEnd()){
//                                    u.noticeError("room.endRoom");
                                }else{
//                                    u.noticeError("room.delRoom");
                                }
                                userInfos.add(new SceneUserInfo((short) u.getSessionId(), u.getGatewayId(), u.getUserId()));
                            }
                        }
                        map.remove(msg.getRoomId()); 
                        room.close();
                        room.setRoomInfo(null);
                        msg.setInfos(userInfos);
                    }
                    msg.setResult(ret);
                    bossClient.writeAndFlush(msg);
                });
            });
        });
    }

    public void handler(MessageHandler handler, Message message, short sessionId, short gatewayId) {
        run(() -> {
            long gatewayIdUnionSessionId = getGatewayIdUnionSessionId(sessionId, gatewayId);
            PdkRoom room = gatewayIdUnionSessionIdMap.get(gatewayIdUnionSessionId);
            if (room == null) {
                throw new RuntimeException(String.format(
                        "未正确进入Room,消息不能执行! Message:%s,sessionId:%s,gatewayId:%s",
                        message, sessionId, gatewayId
                ));
            }
            PdkSceneUser sceneUser = gatewayIdUnionSessionIdUserMap.get(gatewayIdUnionSessionId);
            if (sceneUser == null) {
                throw new RuntimeException(String.format(
                        "玩家未进入房间?,消息不能执行! Room:%s, Message:%s,sessionId:%s,gatewayId:%s",
                        room, message, sessionId, gatewayId
                ));
            }
            room.handler(handler, message, sceneUser);
        });
    }

    @Override
    protected void threadMethod(int frameCount, long time, long passedTime) {

    }

    private long getGatewayIdUnionSessionId(int gatewayId, int sessionId) {
        return (long) gatewayId << 32 | (long) sessionId;
    }

    @Override
    protected void errorHandler(Throwable t) {
        log.error("严重异常", t);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
