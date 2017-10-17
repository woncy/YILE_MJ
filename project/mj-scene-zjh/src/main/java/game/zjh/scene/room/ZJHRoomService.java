package game.zjh.scene.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.isnowfox.core.net.message.Message;
import com.isnowfox.core.thread.FrameQueueContainer;

import ZJH.data.ZJHConfig;
import game.boss.SceneUserInfo;
import game.boss.ZJH.ZJHSceneUserInfo;
import game.zjh.scene.msg.CheckDelZJHRoomMsg;
import game.zjh.scene.msg.CheckJoinZJHRoomMsg;
import game.zjh.scene.msg.CheckJoinZJHRoomResult;
import game.zjh.scene.msg.ZJHUserInfo;
import game.zjh.scene.net.BossClient;
import mj.net.handler.MessageHandler;
import mj.net.message.game.GameDelRoom;

public class ZJHRoomService extends FrameQueueContainer implements ApplicationContextAware{

	public ZJHRoomService(int frameTimeSpan, int queueMax) {
		super(frameTimeSpan, queueMax);
		// TODO Auto-generated constructor stub
	}
	private static final int FRAME_TIME_SPAN = 33;
    private static final int RUN_QUEUE_MAX = 1024;
    private static final int DEFAULT_USER_NUM = 4;

    private static final Logger log = LoggerFactory.getLogger(ZJHRoomService.class);

    @Value("${ZJHsceneId}")
    private int sceneId;

    @Value("${rulesName}")
    private String rulesName;

    private ZJHRoomAsyncService ZJHRoomAsyncService;
//     
//     
//     
     private ApplicationContext applicationContext;
     private HashMap<Integer, ZJHRoomImpl> map = new HashMap<>();
     private HashMap<Integer, ZJHSceneUser> ZJHsceneUserMap = new HashMap<>();
     /**
      * 通过id 映射到map
     * id 是 gatewayId<<32|sessionId
      */
     private HashMap<Long, ZJHRoom> gatewayIdUnionSessionIdMap = new HashMap<>();
 
     /**
      * 通过id 映射到map
      * id 是 gatewayId<<32|sessionId
      */
     private HashMap<Long, ZJHSceneUser> gatewayIdUnionSessionIdUserMap = new HashMap<>();
 
    public ZJHRoomService() {
        super(FRAME_TIME_SPAN, RUN_QUEUE_MAX);
        start();
    }
 
     @Autowired
     private BossClient bossClient;
 
 	 @SuppressWarnings("rawtypes")
	public void handler(MessageHandler handler, Message message, short sessionId, short gatewayId) {
 	        run(() -> {
 	            long gatewayIdUnionSessionId = getGatewayIdUnionSessionId(sessionId, gatewayId);
 	            ZJHRoom room = gatewayIdUnionSessionIdMap.get(gatewayIdUnionSessionId);
 	            if (room == null) {
 	                throw new RuntimeException(String.format(
 	                        "未正确进入Room,消息不能执行! Message:%s,sessionId:%s,gatewayId:%s",
 	                        message, sessionId, gatewayId
 	                ));
 	            }
 	            ZJHSceneUser sceneUser = gatewayIdUnionSessionIdUserMap.get(gatewayIdUnionSessionId);
 	            if (sceneUser == null) {
 	                throw new RuntimeException(String.format(
 	                        "玩家未进入房间?,消息不能执行! Room:%s, Message:%s,sessionId:%s,gatewayId:%s",
 	                        room, message, sessionId, gatewayId
 	                ));
 	            }
 	            room.handler(handler, message, sceneUser);
 	        });
 	    }
	
 	  

	
	//加入房間
	public void checkJoinZJHRoom(CheckJoinZJHRoomMsg msg) {
		 run(() -> {
	            ZJHRoomImpl room = map.get(msg.getRoomId());
	            //创建房间
	            if (room == null) {
	            	
	                ZJHConfig config = new ZJHConfig(msg.getOptions());
	                room = new ZJHRoomImpl(ZJHRoomAsyncService, config);
	//                int userNum = config.getInt(ZJHConfig.USERNUM);
//	                try{
//	                	userNum = config.getInt(Config.USER_NUM);
//		                if(userNum<2){
//		                	userNum = DEFAULT_USER_NUM;
//		                }
//	                }catch(Exception e){
//	                	userNum = DEFAULT_USER_NUM;
//	                }
	                
	                ZJHRoomInfo roomInfo = new ZJHRoomInfo(room, rulesName);
	                BeanUtils.copyProperties(msg, roomInfo);
	                room.setRoomInfo(roomInfo);
	                room.start();
	                applicationContext.getAutowireCapableBeanFactory().autowireBean(room);
	                map.put(msg.getRoomId(), room);
	            }
	            //进入房间成功
	            ZJHSceneUser joinSceneUser = null;
	            
	            List<ZJHSceneUser> sceneUsers = new ArrayList<>();
	            for (ZJHUserInfo userInfo : msg.getZJHuserInfos()) {
	            	
	            	ZJHSceneUser sceneUser = ZJHsceneUserMap.get(userInfo.getUserId());
	                if (sceneUser == null) {
	                    sceneUser = new ZJHSceneUser();
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
	                sceneUser.setScore(userInfo.getScore());
	                sceneUser.setLongitude(userInfo.getLongitude());
	                sceneUser.setLatitude(userInfo.getLatitude());
	                if (userInfo.getUserId() == msg.getJoinUserId()) {
	                    sceneUser.setSessionId(msg.getJoinSessionId());
	                    sceneUser.setGatewayId(msg.getJoinGatewayId());
	                    joinSceneUser = sceneUser;
	                }
	                
	                sceneUsers.add(sceneUser);
	                
	                ZJHsceneUserMap.put(userInfo.getUserId(), sceneUser);
	            }


	            long gatewayIdUnionSessionId = getGatewayIdUnionSessionId(msg.getJoinSessionId(), msg.getJoinGatewayId());
	            gatewayIdUnionSessionIdMap.put(gatewayIdUnionSessionId, room);
	            gatewayIdUnionSessionIdUserMap.put(gatewayIdUnionSessionId, joinSceneUser);
	            room.join(joinSceneUser, sceneUsers, (r) -> {
	                //进入房间成功
	            	CheckJoinZJHRoomResult ret = new CheckJoinZJHRoomResult();
	                ret.setRoomId(msg.getRoomId());
	                ret.setJoinUserId(msg.getJoinUserId());
	                ret.setJoinGatewayId(msg.getJoinGatewayId());
	                ret.setJoinSessionId(msg.getJoinSessionId());
	                ret.setSucccess(r);
	                bossClient.writeAndFlush(ret);
	            });
	        });
		
	}
	@Resource
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}

	@Override
	protected void errorHandler(Throwable t) {
		log.error("严重异常", t);
		
	}

	private long getGatewayIdUnionSessionId(int gatewayId, int sessionId) {
	        return (long) gatewayId << 32 | (long) sessionId;
	    }
	@Override
	protected void threadMethod(int arg0, long arg1, long arg3) {
		
	}

    public void setZJHRoomAsyncService(ZJHRoomAsyncService roomAsyncService) {
        this.ZJHRoomAsyncService = roomAsyncService;
    }



    //删除房间
	public void checkDelRoom(CheckDelZJHRoomMsg msg) {
		 run(() -> {
	            ZJHRoomImpl room = map.get(msg.getRoomId());
	            if (room == null) {
	                return;
	            }
	            room.delRoom(msg.getUserId(), (ret) -> {
	                //进入房间成功
	                run(() -> {
	                    List<ZJHSceneUserInfo> userInfos = new ArrayList<ZJHSceneUserInfo>();
	                    if (ret) {
	                        ZJHRoomInfo roomInfo = room.getRoomInfo();
	                        for (ZJHSceneUser u : roomInfo.getzjhUsers()) {
	                            if (u != null) {
	                                long gatewayIdUnionSessionId = getGatewayIdUnionSessionId(u.getSessionId(), u.getGatewayId());
	                                gatewayIdUnionSessionIdMap.remove(gatewayIdUnionSessionId);
	                                gatewayIdUnionSessionIdUserMap.remove(gatewayIdUnionSessionId);
	                                u.sendMessage(new GameDelRoom(room.isEnd(),room.isStart()));
	                                if(room.isEnd()){
//	                                    u.noticeError("room.endRoom");
	                                }else{
//	                                    u.noticeError("room.delRoom");
	                                }
	                                userInfos.add(new ZJHSceneUserInfo((short) u.getSessionId(), u.getGatewayId(), u.getUserId()));
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

	
}
