package game.scene.douniu.room;

import com.isnowfox.core.net.message.Message;
import com.isnowfox.core.thread.FrameQueueContainer;

import Douniu.data.DouniuConfig;
import game.boss.douniu.DouniuSceneUserInfo;
import game.douniu.scene.msg.CheckDelDouniuRoomMsg;
import game.douniu.scene.msg.CheckExitDouniuRoomMsg;
import game.douniu.scene.msg.CheckJoinDouniuRoomMsg;
import game.douniu.scene.msg.CheckJoinDouniuRoomResult;
import game.douniu.scene.msg.DouniuUserInfo;
import game.scene.douniu.net.BossClient;
import game.scene.msg.CheckOfflineRoomMsg;
import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DouniuGameDelRoom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author zuoge85@gmail.com on 16/9/30.
 */
public class DouniuRoomService extends FrameQueueContainer implements ApplicationContextAware {
    private static final int FRAME_TIME_SPAN = 33;
    private static final int RUN_QUEUE_MAX = 1024;
    private static final int DEFAULT_USER_NUM = 6;

    private static final Logger log = LoggerFactory.getLogger(DouniuRoomService.class);
    @Value("${sceneId}")
    private int sceneId;
    
    @Value("${rulesName}")
    private String rulesName;
    
    private RoomAsyncService roomAsyncService;
    private ApplicationContext applicationContext;
    
    private HashMap<Integer, DouniuRoomImpl> map = new HashMap<>();
    private HashMap<Integer, DouniuSceneUser> sceneUserMap = new HashMap<>();
    /**
     * 通过id 映射到map
     * id 是 gatewayId<<32|sessionId
     */
    private HashMap<Long, DouniuRoom> gatewayIdUnionSessionIdMap = new HashMap<>();

    /**
     * 通过id 映射到map
     * id 是 gatewayId<<32|sessionId
     */
    private HashMap<Long, DouniuSceneUser> gatewayIdUnionSessionIdUserMap = new HashMap<>();

    public DouniuRoomService() {
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
				DouniuRoomImpl room = map.get(msg.getRoomId());
	            if (room == null) {
	                log.info("房间已经不存在了");
	                return;
	            }
	            room.offline(msg.getUserId());
				
			}
		};
        run(checkRoomThread);
    }

    public void checkJoinRoom(CheckJoinDouniuRoomMsg msg) {
    	
        //创建房间
        run(() -> {
        	
            DouniuRoomImpl room = map.get(msg.getRoomId());
            if (room == null) {
            	
            	DouniuConfig config = new DouniuConfig(msg.getOptions());
                room = new DouniuRoomImpl(roomAsyncService, config);
                /*int userNum = 0;
                try{
                	
                	userNum +=1;
	               
                }catch(Exception e){
         
                }
               // int userNum =0;
               //这里修改为动态i
*/                boolean isQiangzhuang = config.getBoolean(DouniuConfig.MOSHI);
                DouniuRoomInfo roomInfo = new DouniuRoomInfo(room,rulesName,isQiangzhuang, 6);
                BeanUtils.copyProperties(msg, roomInfo);
                room.setRoomInfo(roomInfo);
                room.start();
                applicationContext.getAutowireCapableBeanFactory().autowireBean(room);
                map.put(msg.getRoomId(), room);
            }
            //进入房间成功
            DouniuSceneUser joinSceneUser = null;

            List<DouniuSceneUser> sceneUsers = new ArrayList<>();
            for (DouniuUserInfo userInfo : msg.getDouniuUserInfos()) {
                DouniuSceneUser sceneUser = sceneUserMap.get(userInfo.getUserId());
                if (sceneUser == null) {
                    sceneUser = new DouniuSceneUser();
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
            	CheckJoinDouniuRoomResult ret = new CheckJoinDouniuRoomResult();
                ret.setRoomId(msg.getRoomId());
                ret.setJoinSessionId(msg.getJoinSessionId());
                ret.setJoinGatewayId(msg.getJoinGatewayId());
                ret.setJoinUserId(msg.getJoinUserId());  
                ret.setSucccess(r);
                bossClient.writeAndFlush(ret);
            });
        });
    }

    public void checkExitRoom(CheckExitDouniuRoomMsg msg) {
        run(() -> {
            DouniuRoomImpl room = map.get(msg.getRoomId());
            if (room == null) {
                return;
            }

            room.exitRoom(msg.getUserId(), (ret) -> {
                run(() -> {
                    if (ret) {
                        long gatewayIdUnionSessionId = getGatewayIdUnionSessionId(msg.getJoinSessionId(), msg.getJoinGatewayId());
                       /* DouniuSceneUser user=gatewayIdUnionSessionIdUserMap.get(gatewayIdUnionSessionId);
                        user.setJoinGame(false);*/
                        gatewayIdUnionSessionIdMap.remove(gatewayIdUnionSessionId);
                        gatewayIdUnionSessionIdUserMap.remove(gatewayIdUnionSessionId);
                    }
                    msg.setResult(ret);
                    bossClient.writeAndFlush(msg);
                });
            });
        });
    }

    public void checkDelRoom(CheckDelDouniuRoomMsg msg) {
        run(() -> {
            DouniuRoomImpl room = map.get(msg.getRoomId());
            if (room == null) {
                return;
            }
            room.delRoom(msg.getUserId(),(ret) -> {
                //进入房间成功
                run(() -> {
                    List<DouniuSceneUserInfo> userInfos = new ArrayList<DouniuSceneUserInfo>();
                    if (ret) {
                    	room.endRoom();
                        DouniuRoomInfo roomInfo = room.getRoomInfo();
                        for (DouniuSceneUser u : roomInfo.getUsers()) {
                            if (u != null) {
                                long gatewayIdUnionSessionId = getGatewayIdUnionSessionId(u.getSessionId(), u.getGatewayId());
                                gatewayIdUnionSessionIdMap.remove(gatewayIdUnionSessionId);
                                gatewayIdUnionSessionIdUserMap.remove(gatewayIdUnionSessionId);
                            //    u.sendMessage(new DouniuGameDelRoom(room.isEnd(),true));
                                userInfos.add(new DouniuSceneUserInfo((short) u.getSessionId(), u.getGatewayId(), u.getUserId()));
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
            DouniuRoom room = gatewayIdUnionSessionIdMap.get(gatewayIdUnionSessionId);
            if (room == null) {
                throw new RuntimeException(String.format(
                        "未正确进入Room,消息不能执行! Message:%s,sessionId:%s,gatewayId:%s",
                        message, sessionId, gatewayId
                ));
            }
            DouniuSceneUser sceneUser = gatewayIdUnionSessionIdUserMap.get(gatewayIdUnionSessionId);
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
