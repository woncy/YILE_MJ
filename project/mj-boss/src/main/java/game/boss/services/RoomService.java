package game.boss.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.isnowfox.core.thread.FrameQueueContainer;
import com.isnowfox.util.UUID;

import game.boss.ServerRuntimeException;
import game.boss.dao.dao.RoomCheckIdPoolDao;
import game.boss.dao.dao.RoomDao;
import game.boss.dao.dao.RoomDetailDao;
import game.boss.dao.dao.RoomUserDao;
import game.boss.dao.dao.UserDao;
import game.boss.dao.dao.UserLinkRoomDao;
import game.boss.dao.entity.RoomCheckIdPoolDO;
import game.boss.dao.entity.RoomDO;
import game.boss.dao.entity.RoomDetailDO;
import game.boss.dao.entity.RoomUserDO;
import game.boss.dao.entity.UserDO;
import game.boss.dao.entity.UserLinkRoomDO;
import game.boss.model.Room;
import game.boss.model.User;
import game.boss.net.BossService;
import game.boss.type.RoomCheckIdState;
import game.scene.msg.ChapterEndMsg;
import game.scene.msg.ChapterStartMsg;
import game.scene.msg.CheckDelRoomMsg;
import game.scene.msg.RoomEndMsg;
import game.utils.DateUtils;
import mj.data.Config;
import mj.net.message.login.CreateRoom;
import mj.net.message.login.CreateRoomRet;
import mj.net.message.login.DelRoomRet;
import mj.net.message.login.ExitRoomRet;
import mj.net.message.login.JoinRoomRet;
import mj.net.message.login.OptionEntry;
import mj.net.message.login.Pay;
import mj.net.message.login.ProxyRoom;
import mj.net.message.login.ProxyRoomIF;
import mj.net.message.login.ProxyRoomList;
import mj.net.message.login.RoomHistory;
import mj.net.message.login.RoomHistoryListRet;
import mj.net.message.login.StartGame;
import mj.net.message.login.UserIF;
import mj.net.message.login.WangbangPlayBackRet;

/**
 * 鎴块棿鏈嶅姟
 * 涓�鑷存�х殑淇濊瘉鍦ㄤ簬,鍚屼竴涓敤鎴风殑閫昏緫鍙湪涓�涓嚎绋嬪唴,璇锋敞鎰�!
 * <p>
 * <b>鎴块棿CHECK_ID 鍘熷垯鏄�:<b/>
 * 鍏堟妸鏌ユ壘NO_USE 淇敼鎴怋UFFER,鐒跺悗鎻掑叆绾跨▼瀹夊叏闃熷垪
 * 鐒跺悗浣跨敤,浣跨敤鍚庝慨鏀筓SE
 * 浣跨敤缁撴潫淇敼NO_USE
 * 浠ヤ笂鍘熷垯淇濊瘉涓�鑷存��,
 * <p>
 * 浣嗘槸娉ㄦ剰 鏈嶅姟鍣ㄥ惎鍔ㄧ殑鏃跺�欐鏌� BUFFER鐨刬d,濡傛灉瀛樺湪鍏ㄩ儴淇敼鎴怤O_USE
 * 鍥犱负鍙兘鍑虹幇BUFFE瑁呯殑鏈娇鐢�
 *
 * @author zuoge85@gmail.com on 16/9/30.
 */
@Service
public class RoomService extends FrameQueueContainer implements BaseService {
    private static final Logger log = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private BossService bossService;
    @Autowired
    private AsyncDbService asyncDbService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoomUserDao roomUserDao;

    @Autowired
    private UserLinkRoomDao userLinkRoomDao;
    @Autowired
    private RoomCheckIdPoolDao roomCheckIdPoolDao;

    /**
     * 鎴块棿鏄犲皠
     */
    private HashMap<Integer, Room> joinUserRoomMap = new HashMap<>();

    /**
     * 鍒涘缓
     */
    private HashMap<Integer, Room> crateUserRoomMap = new HashMap<>();
    /**
     * 鎴块棿鏄犲皠
     */
    private HashMap<Integer, Room> roomMap = new HashMap<>();
    
    /**
     * 代理id,room
     */
    private HashMap<Integer, List<Integer>> proxyRoom = new HashMap<>();
    /**
     * 鎴块棿鏄犲皠
     */
    private HashMap<String, Room> checkIdRoomMap = new HashMap<>();

    @Autowired
    private RoomDetailDao roomDetailDao;
    
    public RoomService() {
        super(FRAME_TIME_SPAN, RUN_QUEUE_MAX);
    }

    @PostConstruct
    private void init() {
        String sql = "UPDATE " + RoomCheckIdPoolDO.Table.DB_TABLE_NAME +
                " SET `" + RoomCheckIdPoolDO.Table.STATE + "`=" + RoomCheckIdState.NO_USE.ordinal() +
                " WHERE `" + RoomCheckIdPoolDO.Table.STATE + "`=" + RoomCheckIdState.BUFFER.ordinal();

        log.debug("init:" + sql);
        roomCheckIdPoolDao.getJdbcTemplate().execute(
                sql
        );
        start();
    }
    
    public void proxyCreateRoom(CreateRoom msg,User user){
    	 run(() -> {
         	UserDO userDO = userDao.get(user.getUserId());
         	if(userDO.getLevel()<=0){
         		ProxyRoom pmsg = new ProxyRoom("您还不是代理，不能代开房间");
         		user.send(pmsg);
         		return;
         	}
         	msg.addOptions(new OptionEntry("isProxy","1"));
         	 Config config = new Config(msg);
             {
             	/**
             	 * 房卡不足时
             	 */
                   {
                       int max = config.getInt(Config.CHAPTER_MAX);
                       int gold = max / 8;
                       UserDO userDO2 = userDao.get(user.getUserId());
                       if (userDO2.getGold()< gold) {
                           sendNoGold(user);
                           sendCreateRoomRet(user, false, null);
                           return;
                       }else{
                    	   userDO2.setGold(userDO2.getGold()-gold);
                    	   userDao.update(userDO2);
                    	   user.send(new Pay(-gold));
                       }
                   }
             }
             //鐢熸垚roomCheckId
             String roomCheckId = getBufferId();
             int sceneId = bossService.getRandomSceneId();
             asyncDbService.excuete(user, () -> {
                 RoomDO room = checkUserCreateRoom(user);
                 room = new RoomDO();
                 room.setCreateUserId(user.getUserId());
                 room.setRoomCheckId(roomCheckId);
                 room.setSceneId(sceneId);
                 room.setStart(true);
                 room.setStartDate(new Date());
                 room.setUserMax(config.getInt(Config.USER_NUM));
                 room.setUuid(UUID.generateNoSep());
                 room.setConfig(config);
                 int isUpdate = roomCheckIdPoolDao.updatePartial(
                         Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.USE.ordinal()),
                         RoomCheckIdPoolDO.Table.ID, roomCheckId,
                         RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.BUFFER.ordinal()
                 );
                 if (isUpdate < 1) {
                     throw new RuntimeException("id浣跨敤閿欒!,id鐘舵�佷笉瀵�!" + roomCheckId);
                 }
                 long id = roomDao.insert(room);
                 room.setId((int) id);
                 
                 initRoomData(user, room);
                 List<Integer> list = proxyRoom.get(user.getUserId());
                 if(list == null){
                	 list = new ArrayList<Integer>();
                 }
                 list.add(room.getId());
                 proxyRoom.put(user.getUserId(), list);
                 user.send(new ProxyRoom(roomCheckId));
//                 sendCreateRoomSuccess(user);
//                 sendCreateRoomRet(user, true, room.getRoomCheckId());
             });
         });
    }

    /**
     * 鍒涘缓鎴块棿
     */
    public void createRoom(CreateRoom msg, User user) {
        run(() -> {
        	UserDO userDO = userDao.get(user.getUserId());
        	msg.addOptions(new OptionEntry("isProxy","0"));
        	Config config = new Config(msg);
            {
            	/**
            	 * 房卡不足时
            	 */
                  {
                      int max = config.getInt(Config.CHAPTER_MAX);
                      int gold = (int) Math.ceil(max / 8);
                      if (userDO.getGold()< gold) {
                          sendNoGold(user);
                          sendCreateRoomRet(user, false, null);
                          return;
                      }
                  }
                Room room = crateUserRoomMap.get(user.getUserId());
                if (room != null) {
                	closeRoom(room.getRoomDO(),user);
                }
            }
            //鐢熸垚roomCheckId
            String roomCheckId = getBufferId();
            int sceneId = bossService.getRandomSceneId();
            asyncDbService.excuete(user, () -> {
            	
                RoomDO room = checkUserCreateRoom(user);
                if (room != null&&"0".equals(room.getConfig().getString("isProxy"))) {
                	closeRoom(room, user);
                }
                
                room = new RoomDO();
                room.setCreateUserId(user.getUserId());
                room.setRoomCheckId(roomCheckId);
                room.setSceneId(sceneId);
                room.setStart(true);
                room.setStartDate(new Date());
                room.setUserMax(config.getInt(Config.USER_NUM));
                room.setUuid(UUID.generateNoSep());
                room.setConfig(config);
                int isUpdate = roomCheckIdPoolDao.updatePartial(
                        Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.USE.ordinal()),
                        RoomCheckIdPoolDO.Table.ID, roomCheckId,
                        RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.BUFFER.ordinal()
                );

                if (isUpdate < 1) {
                    throw new RuntimeException("id浣跨敤閿欒!,id鐘舵�佷笉瀵�!" + roomCheckId);
                }
                long id = roomDao.insert(room);
                room.setId((int) id);

                List<RoomDO> roomDOs = roomDao.find(100, RoomDO.Table.ROOM_CHECK_ID, room.getRoomCheckId(), RoomDO.Table.START, true);
                if (roomDOs.size() > 1) {
                	if(userDO.getLevel() == 0){
                        roomDao.del(room.getKey());
                        sendCreateRoomError(user);
                        sendCreateRoomRet(user, false, null);
                        return;
                	}
                }
                initRoomData(user, room);
                sendCreateRoomSuccess(user);
                sendCreateRoomRet(user, true, room.getRoomCheckId());
               
            });
        });
    }

    public void closeRoom(RoomDO roomDO,User user) {
		roomDO.setStart(false);
		roomDO.setEnd(true);
		roomDao.update(roomDO);
		checkIdRoomMap.remove(roomDO.getRoomCheckId());
		crateUserRoomMap.remove(user.getUserId());
		joinUserRoomMap.remove(user.getUserId());
		roomMap.remove(roomDO.getId());
	}

	private void sendCreateRoomRet(User user, boolean result, String roomCheckId) {
        user.send(new CreateRoomRet(result, roomCheckId));
    }

    /**
     * 杩涘叆鎴块棿
     * 濡傛灉宸茬粡杩涘叆鎴块棿,鍙互閲嶅杩涘叆
     */
    private void joinRoom(User user, Room room, Consumer<Boolean> callback) {
        if (frameThread != Thread.currentThread()) {
            throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤");
        }
        initRoomData(user, room);
        RoomDO roomDO = room.getRoomDO();
        user.setJoinHomeGatewaySuccess(false);
        user.setJoinHomeSceneSuccess(false);

        int userId = user.getUserId();
        int locationIndex = -1;
        int userNum = roomDO.getConfig().getInt(Config.USER_NUM);

        String userName = user.getUserDO().getName();
       if (userId ==roomDO.getUser0() ) {
            roomDO.setUser0(userId);
            roomDO.setUserName0(userName);
            locationIndex = 0;
        } else if (userId == roomDO.getUser1()) {
            roomDO.setUser1(userId);
            roomDO.setUserName1(userName);
            locationIndex = 1;
        } else if (userId == roomDO.getUser2()) {
            roomDO.setUser2(userId);
            roomDO.setUserName2(userName);
            locationIndex = 2;
        } else if (userId == roomDO.getUser3()) {
            roomDO.setUser3(userId);
            roomDO.setUserName3(userName);
            locationIndex = 3;
        } else if(roomDO.getUser0()<1){
        	roomDO.setUser0(userId);
            roomDO.setUserName0(userName);
            locationIndex = 0;
        }else if (roomDO.getUser1() < 1 && userNum>=2 ) {
            roomDO.setUser1(userId);
            roomDO.setUserName1(userName);
            locationIndex = 1;
        } else if (roomDO.getUser2() < 1 && userNum>=3) {
            roomDO.setUser2(userId);
            roomDO.setUserName2(userName);
            locationIndex = 2;
        } else if (roomDO.getUser3() < 1 && userNum>=4) {
            roomDO.setUser3(userId);
            roomDO.setUserName3(userName);
            locationIndex = 3;
        } else {
            sendRoomFull(user);
            callback.accept(false);
            return;
        }
        //
        int finalLocationIndex = locationIndex;
        asyncDbService.excuete(user, () -> {
            RoomUserDO roomUserDO = roomUserDao.get(userId);
            if (roomUserDO == null) {
                roomUserDO = new RoomUserDO();
            }
            roomUserDO.setUserId(userId);
            roomUserDO.setStartDate(new Date());
            roomUserDO.setRoomId(roomDO.getId());
            roomUserDO.setRoomCheckId(roomDO.getRoomCheckId());
            roomUserDO.setLocationIndex(finalLocationIndex);


            roomUserDao.replace(roomUserDO);
            roomDO.setVersion(roomUserDO.getVersion() + 1);
            roomDao.update(roomDO);
            user.setJoinRoomCallback(callback);
            joinRoomSuccess(user, room, roomUserDO);
        });
    }

    public void checkOffline(User user) {
        run(() -> {
            Room room = joinUserRoomMap.get(user.getUserId());
            if (room != null) {
                bossService.startOfflineScene(user, room.getRoomDO().getId(),room.getRoomDO().getSceneId(),(int) user.getSessionId());
            } else {
                log.info("鐜╁涓嶅湪鎴块棿锛屾棤娉曞彂閫佺绾挎秷鎭紒", user);
            }
        });
    }

    private void joinRoomSuccess(User user, Room room, RoomUserDO roomUserDO) {
        run(() -> {
            room.addUser(roomUserDO.getLocationIndex(), user.getUserDO());
            joinUserRoomMap.put(user.getUserId(), room);
            bossService.startJoinScene(user, room, roomUserDO, user.getSessionId());
        });
    }

    public void joinRoomGatewaySuccess(User user) {
        run(() -> {
            user.setJoinHomeGatewaySuccess(true);
            if (user.isJoinHomeGatewaySuccess() && user.isJoinHomeSceneSuccess()) {
                joinHomeSceneSuccess(user);
            }
        });
    }

    public void joinRoomSceneSuccess(int userId, boolean succcess) {
        userService.handlerUser(userId, user -> {
            run(() -> {
                if (succcess) {
                    user.setJoinHomeSceneSuccess(true);
                    if (user.isJoinHomeGatewaySuccess() && user.isJoinHomeSceneSuccess()) {
                        joinHomeSceneSuccess(user);
                    }
                } else {
                    //杩涘叆澶辫触
                    Consumer<Boolean> callback = user.getJoinRoomCallback();
                    callback.accept(false);
                    user.setJoinRoomCallback(null);
                }
            });
        });
    }

    private void joinHomeSceneSuccess(User user) {
        if (frameThread != Thread.currentThread()) {
            throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤");
        }
        Consumer<Boolean> callback = user.getJoinRoomCallback();
        if(callback!=null)
        	callback.accept(true);
        user.setJoinRoomCallback(null);

        user.send(new StartGame());
    }
    
    public void voteDelRoom(RoomEndMsg msg) {
    	int createUserId = msg.getCrateUserId();
    	int roomId = msg.getRoomId();
    	asyncDbService.excuete(msg.getCrateUserId(), () -> {
            RoomDO room = roomDao.get(roomId);
            if (room != null) {
                run(() -> {
                    bossService.startDelRoomScene(createUserId, room, room.getEnd());
                });
                //鍒ゆ柇鍦烘櫙
            } else {
               
            }
        });
	}

    public void delRoom(int userId, User user, boolean isEnd) {
        asyncDbService.excuete(userId, () -> {
            RoomDO room = roomDao.findObject(
                    RoomDO.Table.CREATE_USER_ID, userId,
                    RoomDO.Table.START, true
            );
            if (room != null) {
                run(() -> {
                    bossService.startDelRoomScene(userId, room, isEnd);
                });
                //鍒ゆ柇鍦烘櫙
            } else {
                log.error("鍏抽棴鎴块棿閿欒锛乧rateUserId:{}", userId);
                if (user != null && !isEnd) {
                    sendAlreadyDelRoom(user);
                    sendDelRoomRet(user);
                }
            }
        });
    }

    public void delRoomSceneSuccess(CheckDelRoomMsg msg) {
        userService.handlerUser(msg.getUserId(), user -> {
            run(() -> {
                if (msg.isResult()) {
                    bossService.startDelRoomGateway(msg.getInfos());
                    delRoomSuccess(msg.getUserId(), user, msg.isEnd(),msg.getRoomId());
                } else {
                    log.error("鎴块棿涓嶈兘鍏抽棴锛乧rateUserId:{}", msg.getUserId());
                    if (user != null && !msg.isEnd()) {
                        sendCannotDelRoom(user);
                    }
                }
            });
        });
    }

    public void delRoomGatewaySuccess(User user) {
        run(() -> {
//            delRoomSuccess(user);
        });
    }


    private void delRoomSuccess(final int userId, User user, boolean isEnd,int roomId) {
        if (frameThread != Thread.currentThread()) {
            throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤");
        }
        
        
        asyncDbService.excuete(userId, () -> {
            RoomUserDO roomUserDO = roomUserDao.findObject(RoomUserDO.Table.ROOM_ID,roomId);
         
            if (roomUserDO != null) {
                RoomDO room = roomDao.get(roomUserDO.getRoomId());
                
                if (room != null) {
                    run(() -> {
                    	int createUserId = room.getCreateUserId();
                    	List<Integer> list = proxyRoom.get(createUserId);
                    	if(list!=null){
                    		boolean b = list.remove(new Integer(room.getId()));
                    	}
                        room.setEndDate(new Date());
                        crateUserRoomMap.remove(userId);
                        roomMap.remove(room.getId());
                        checkIdRoomMap.remove(room.getRoomCheckId());
                        joinUserRoomMap.remove(room.getUser0());
                        joinUserRoomMap.remove(room.getUser1());
                        joinUserRoomMap.remove(room.getUser2());
                        joinUserRoomMap.remove(room.getUser3());
                        //鎴夸富鎵ｉ櫎鎴垮崱
                        Room roomObj = roomMap.get(room.getId());
                        if (roomObj == null || roomObj.isStart()) {
                            userService.minusGold(room.getCreateUserId(), room.getConfig());
                        }

                        asyncDbService.excuete(userId, () -> {
                            room.setStart(false);
                            room.setEnd(isEnd);
                            room.setVersion(room.getVersion() + 1);
                            roomDao.update(room);
                            //鎻掑叆璁板綍
                            List<UserLinkRoomDO> userLinkRoomDOList = new ArrayList<>();
                            for (int i = 0; i < 4; i++) {
                                try {
                                    int curUserId = (Integer) BeanUtils.getPropertyDescriptor(RoomDO.class, "User" + i).getReadMethod().invoke(room);
                                    if (curUserId > 0) {
                                        roomUserDao.del(new RoomUserDO.Key(curUserId));
                                    }
                                    if (room.getChapterNums() > 0) {
                                        UserLinkRoomDO link = new UserLinkRoomDO();
                                        link.setUserId(curUserId);
                                        link.setRoomId(room.getId());
                                        BeanUtils.copyProperties(room, link, "key");
                                        userLinkRoomDOList.add(link);
                                    }
                                } catch (Exception e) {
                                    throw new ServerRuntimeException(e);
                                }
                            }
                            if (userLinkRoomDOList.size() > 0) {
                                userLinkRoomDao.insert(userLinkRoomDOList);
                            }
                            //閲婃斁checkId
                            roomCheckIdPoolDao.updatePartial(
                                    Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.NO_USE.ordinal()),
                                    RoomCheckIdPoolDO.Table.ID, room.getRoomCheckId(),
                                    RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.BUFFER.ordinal()
                            );
                            if (user != null) {
                                sendDelRoomRet(user);
                            }
                        });
                    });
                } else {
                    if (user != null && !isEnd) {
                        sendAlreadyDelRoom(user);
                        sendDelRoomRet(user);
                    }
                }
            } else {
                if (user != null && !isEnd) {
                    sendAlreadyDelRoom(user);
                    sendDelRoomRet(user);
                }
            }
        });

    }

    /**
     * 鍏堥��鍑哄満鏅湇鍔″櫒锛屽鏋滄垚鍔熷湪閫�鍑虹綉鍏�
     */
    @SuppressWarnings("unused")
	public void exitRoom(User user) {

        asyncDbService.excuete(user, () -> {
            final int userId = user.getUserId();
            RoomUserDO roomUserDO = roomUserDao.get(userId);
            if (roomUserDO != null) {
                RoomDO room = roomDao.get(roomUserDO.getRoomId());
//                if(room.getCreateUserId() == userId ){
//                	//
//                	UserLinkRoomDO userLinkRoomDO = userLinkRoomDao.findObject(UserLinkRoomDO.Table.ROOM_ID, roomUserDO.getRoomId());
//                	if(userLinkRoomDO != null){
//                	int max = room.getConfig().getInt(room.getConfig().CHAPTER_MAX);
//                    int gold = (int) Math.ceil(max / 2);
//                	user.send(new Pay(-gold));
//                	}
//                }
                if (room != null) {
                    run(() -> {
                        bossService.startExitRoomScene(user, room);
                    });
                } else {
                    sendAlreadyExitRoom(user);
                    sendExitRoomRet(user);
                }
            } else {
                sendAlreadyExitRoom(user);
                sendExitRoomRet(user);
            }
        });
    
    }


    public void exitRoomSceneSuccess(int userId, int sceneId, boolean result,int roomid) {
        userService.handlerUser(userId, user -> {
            run(() -> {
                if (result) {
                    bossService.startExitRoomGateway(user, sceneId);
                    Room room = roomMap.get(roomid);
                    if(room!=null){
                    	Map<Integer, UserDO> map = room.getMap();
                    	for (int i = 0; i < 4; i++) {
							UserDO userDO = map.get(i);
							if(userDO!=null && userDO.getId()==userId){
								map.remove(i);
							}
						}
                    }
                } else {
                    sendCannotExitRoom(user);
                }
            });
        });
    }

    public void exitRoomGatewaySuccess(User user) {
        run(() -> {
            exitRoomSuccess(user);
        });
    }


    public void chapterStart(ChapterStartMsg msg) {
        run(() -> {
            Room room = roomMap.get(msg.getRoomId());
            if (room != null) {
                room.setStart(true);
            }
        });
    }
    public void chapterEnd(ChapterEndMsg msg) {
        run(() -> {
            Room room = roomMap.get(msg.getRoomId());
            if (room != null) {
                RoomDO roomDO = room.getRoomDO();
                msg.getUserScoreMap().forEach((userId, score) -> {
                    if (userId == roomDO.getUser0()) {
                        roomDO.setScore0(score);
                    } else if (userId == roomDO.getUser1()) {
                        roomDO.setScore1(score);
                    } else if (userId == roomDO.getUser2()) {
                        roomDO.setScore2(score);
                    } else if (userId == roomDO.getUser3()) {
                        roomDO.setScore3(score);
                    }
                });
                roomDO.setChapterNums(roomDO.getChapterNums() + 1);
                asyncDbService.excuete(() -> {
                    roomDao.update(roomDO);
                });
            }
        });
    }


    private void exitRoomSuccess(User user) {
        if (frameThread != Thread.currentThread()) {
            throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤");
        }

        int userId = user.getUserId();
        Room room = joinUserRoomMap.remove(user.getUserId());
        if (room != null) {
            RoomDO roomDO = room.getRoomDO();
            room.removeUser(userId);
            if (roomDO.getUser0() == userId) {
                roomDO.setUser0(0);
            } else if (roomDO.getUser1() == userId) {
                roomDO.setUser1(0);
            } else if (roomDO.getUser2() == userId) {
                roomDO.setUser2(0);
            } else if (roomDO.getUser3() == userId) {
                roomDO.setUser3(0);
            } else {
                sendAlreadyExitRoom(user);
                sendExitRoomRet(user);
                return;
            }

            asyncDbService.excuete(user, () -> {
                roomUserDao.del(new RoomUserDO.Key(userId));
                roomDO.setVersion(roomDO.getVersion() + 1);
                roomDao.update(roomDO);
                sendExitRoomRet(user);
            });
        } else {
            sendAlreadyExitRoom(user);
            sendExitRoomRet(user);
        }
    }


    public void joinRoom(String roomCheckId, User user) {
        run(() -> {
            if (frameThread != Thread.currentThread()) {
                throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤");
            }

            {
                Room room = checkIdRoomMap.get(roomCheckId);
                if (room != null) {
                    joinRoom(user, room, result -> {
                        sendJoinRoomRet(user, result);
                    });
                    return;
                }
            }
            asyncDbService.excuete(user, () -> {
                RoomDO roomDO = roomDao.findObject(
                        RoomDO.Table.ROOM_CHECK_ID, roomCheckId,
                        RoomDO.Table.START, true
                );

                //璇诲彇鍏ㄩ儴杩涘叆娓告垙鐨勭敤鎴蜂俊鎭�
                if (roomDO != null) {
                    Room room = new Room(roomDO);
                    for (int i = 0; i < 4; i++) {
                        try {
                            int userId = (Integer) BeanUtils.getPropertyDescriptor(RoomDO.class, "User" + i).getReadMethod().invoke(room.getRoomDO());
                            if (userId > -1) {
                                UserDO userDO = userDao.get(userId);
                                if (userDO != null) {
                                    room.addUser(i, userDO);
                                }
                            }
                        } catch (Exception e) {
                            throw new ServerRuntimeException(e);
                        }
                    }
                    run(() -> {
                        joinRoom(user, room, result -> {
                            sendJoinRoomRet(user, result);
                        });
                    });
                } else {
                    sendErrorRoomCheckId(user);
                    sendJoinRoomRet(user, false);
                }
            });
        });
    }

    private void sendJoinRoomRet(User user, boolean result) {
        user.send(new JoinRoomRet(result));
    }

    /**
     * 妫�鏌ョ敤鎴锋槸鍚﹁繘鍏ヤ簡鎴块棿,濡傛灉杩涘叆浜嗘埧闂�,閭ｄ箞灏濊瘯杩涘叆鎴块棿
     */
    public void checkUserRoom(User user, Consumer<String> callback) {
        asyncDbService.excuete(user, () -> {
        	Connection conn = null;
        	java.sql.PreparedStatement pst = null;
        	ResultSet rs = null;
			String querySql = "select id,room_check_id from room "
					+ "where (user0=? or user1=? or user2=? or user3=?) and start=? and end=? "
					+ "order by id desc";
			String updateSql = "update room set start=?, end=?,version=? where id=?";
        	try {
        		conn = roomDao.getDataSource().getConnection();
        		pst = conn.prepareStatement(querySql);
        		int i=1;
				pst.setInt(i++, user.getUserId());
				pst.setInt(i++, user.getUserId());
				pst.setInt(i++, user.getUserId());
				pst.setInt(i++, user.getUserId());
				pst.setBoolean(i++, true);
				pst.setBoolean(i++, false);
				rs = pst.executeQuery();
				String roomCheckId = null;
				if(rs!=null){
					int count = 0;
					while(rs.next()){
						int id = rs.getInt("id");
						RoomDO roomDO = roomDao.get(id);
						
						if(count==0 && roomDO!=null && roomDO.getStart()==true){
							roomCheckId = rs.getString("room_check_id");
						}else{
							if(user.getUserDO().getLevel()>0){
				        		break;
				        	}
							PreparedStatement pst2 = conn.prepareStatement(updateSql);
							pst2.setBoolean(1, false);
							pst2.setBoolean(2, true);
							pst2.setInt(3, 2);
							pst2.setInt(4, id);
							pst2.executeUpdate();
							pst2.close();
						}
						count++;
					}
				}
			    callback.accept(roomCheckId);
				
			} catch (SQLException e) {
				log.error(e.getMessage());
			}finally{
				if(rs!=null){
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(pst!=null){
					try {
						pst.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}if(conn!=null){
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
        	
        });
    }

    /**
     * 涓�鑷存�х殑淇濊瘉鍦ㄤ簬,鍚屼竴涓敤鎴风殑閫昏緫鍙湪涓�涓嚎绋嬪唴
     * !!!!!!!!!!!璇锋敞鎰�
     */
    private RoomDO checkUserCreateRoom(User user) {
        if (frameThread == Thread.currentThread()) {
            throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤,涓嶈兘鍐峳oom 绾跨▼浣跨敤");
        }
        RoomDO room = roomDao.findObject(
                RoomDO.Table.CREATE_USER_ID, user.getUserId(),
                RoomDO.Table.START, true
        );
        if (room != null) {
//            initRoomData(user, room);
            return room;
        }
        return null;
    }

    private void initRoomData(User user, RoomDO roomDO) {
        run(() -> {
            initRoomData(user, new Room(roomDO));
        });
    }

    private boolean initRoomData(User user, Room room) {
        if (frameThread != Thread.currentThread()) {
            throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤");
        }
        RoomDO roomDO = room.getRoomDO();
        if (!roomMap.containsKey(roomDO.getId())) {
            checkIdRoomMap.put(roomDO.getRoomCheckId(), room);
            int isProxy = room.getRoomDO().getConfig().getInt("isProxy");
            if(isProxy==0)
            	crateUserRoomMap.put(roomDO.getCreateUserId(), room);
            roomMap.put(roomDO.getId(), room);
            return true;
        }
        return false;
    }

    private LinkedBlockingQueue<String> idBuffer = new LinkedBlockingQueue<>();

    /**
     * 鐢熸垚涓�涓殢鏈篿d 涓嶉噸澶�!
     */
    private String getBufferId() {
        if (frameThread != Thread.currentThread()) {
            throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤");
        }
        String id = idBuffer.poll();
        if (id != null) {
            return id;
        }
        initCheckId();
        try {
            return idBuffer.take();
        } catch (InterruptedException e) {
            throw new ServerRuntimeException(e);
        }
    }

    private void freeId(String id) {
        idBuffer.offer(id);
    }

    private void initCheckId() {
        asyncDbService.excuete(() -> {
            //鏌ユ壘澶氫釜鏈娇鐢╥d,鐒跺悗缂撳瓨
            if (idBuffer.size() > INIT_ID_BUFFER) {
                return;
            }
            for (int l = 0; l < LOOP_NUMS; l++) {
                if (!idBuffer.isEmpty()) {
                    return;
                }

                List<RoomCheckIdPoolDO> roomCheckIdPoolDOs = roomCheckIdPoolDao.find(
                        100, RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.NO_USE.ordinal()
                );
                for (int i = 0; i < roomCheckIdPoolDOs.size(); i++) {
                    RoomCheckIdPoolDO idPoolDO = roomCheckIdPoolDOs.get(i);
                    int isUpdate = roomCheckIdPoolDao.updatePartial(
                            Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.BUFFER.ordinal()),
                            RoomCheckIdPoolDO.Table.ID, idPoolDO.getId(),
                            RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.NO_USE.ordinal()
                    );
                    if (isUpdate > 0) {
                        idBuffer.offer(idPoolDO.getId());
                    }
                }
                if (idBuffer.isEmpty()) {
                    RoomCheckIdPoolDO item = new RoomCheckIdPoolDO();
                    for (int i = 0; i < INIT_ID_NUMS; ) {
                        item.setId(RandomStringUtils.randomNumeric(CHECK_ROOM_ID_LEN));
                        item.setState(RoomCheckIdState.BUFFER.ordinal());
                        try {
                            roomCheckIdPoolDao.insert(item);
                            i++;
                            idBuffer.offer(item.getId());
                        } catch (DuplicateKeyException ignored) {

                        }
                    }
                }
            }
            throw new ServerRuntimeException("id 鍒濆鍖栦弗閲嶉棶棰�!!!!瓒呰繃閲嶅娆℃暟涓婄嚎,闇�瑕佷汉宸ヤ粙鍏�");
        });
    }


    private void sendAlreadyCreateRoom(User user) {
        user.noticeError("room.alreadyCreateRoom");
    }

    private void sendNoGold(User user) {
        user.noticeError("room.noGold");
    }

    private void sendCreateRoomSuccess(User user) {
        user.noticeError("room.createRoomSuccess");
    }

//    private void sendAlreadyJoinRoom(User user) {
//        user.noticeError("room.alreadyJoinRoom");
//    }

    private void sendErrorRoomCheckId(User user) {
        user.noticeError("room.errorRoomCheckId");
    }

    private void sendRoomFull(User user) {
        user.noticeError("room.full");
    }

    private void sendCreateRoomError(User user) {
        user.noticeError("room.createRoomError");
    }


    private void sendAlreadyExitRoom(User user) {
        user.noticeError("room.alreadyExitRoom");
    }

    private void sendAlreadyDelRoom(User user) {
        user.noticeError("room.alreadyDelRoom");
    }


    private void sendCannotExitRoom(User user) {
        user.noticeError("room.cannotExitRoom");
    }

    private void sendCannotDelRoom(User user) {
        user.send(new DelRoomRet(false));
        user.noticeError("room.cannotDelRoom");
    }

    private void sendExitRoomRet(User user) {
        user.send(new ExitRoomRet());
    }

    private void sendDelRoomRet(User user) {
        user.send(new DelRoomRet(true));
    }

    @Override
    protected void threadMethod(int frameCount, long time, long passedTime) {
    }

    @Override
    protected void errorHandler(Throwable e) {
        log.error("涓ラ噸寮傚父", e);
    }

    public void requestHistory(User user) {
        asyncDbService.excuete(user, () -> {
            List<UserLinkRoomDO> list = userLinkRoomDao.find(20, UserLinkRoomDO.Table.USER_ID, user.getUserId());
            ArrayList<RoomHistory> collect = list.stream().map(r -> {
                RoomHistory h = new RoomHistory();
                h.setChapterNums(r.getChapterNums());
                h.setRoomCheckId(r.getRoomCheckId());
                h.setStartDate(DateUtils.format(r.getStartDate()));
                h.setUserNames(new String[]{
                        r.getUserName0(), r.getUserName1(), r.getUserName2(), r.getUserName3()
                });
                h.setScores(new int[]{
                        r.getScore0(), r.getScore1(), r.getScore2(), r.getScore3()
                });
                return h;
            }).collect(Collectors.toCollection(ArrayList::new));

            RoomHistoryListRet msg = new RoomHistoryListRet();
            msg.setList(collect);
            user.send(msg);
        });
    }

    /**
     * 获取回访记录
     */
  	public void playBack(User user ,String checkRoomId, String chapterIndex) {
  		List<RoomDetailDO> roomDetailDo=roomDetailDao.getByRoomCheckId(checkRoomId, chapterIndex);  
  		if(roomDetailDo != null && roomDetailDo.size() >0 ){
  			RoomDetailDO  roomDetailDO = roomDetailDo.get(0);
  			String  actionDetail = roomDetailDO.getActionDetail();	
  			WangbangPlayBackRet backRet = new WangbangPlayBackRet();
  			backRet.setRecordDetail(actionDetail);
  			user.send(backRet);
  		}
  	}

	public void queryProxyRoom(User user) {
		List<Integer> list = proxyRoom.get(user.getUserId());
		
		if(list!=null){
			List<ProxyRoomIF> infos = new ArrayList<ProxyRoomIF>();
			for (int i = 0; i < list.size(); i++) {
				Integer roomId = list.get(i);
				Room room = roomMap.get(roomId);
				if(room == null){
					boolean b = list.remove(roomId);
					continue;
				}
				ProxyRoomIF roomIF = new ProxyRoomIF();
				roomIF.setRoomNo(room.getRoomDO().getRoomCheckId());
				List<UserIF> users = new ArrayList<UserIF>();
				Map<Integer, UserDO> map = room.getMap();
				for (int j = 0; j < 4; j++) {
					UserDO userDO = map.get(j);
					if(userDO!=null){
						UserIF userIF = new UserIF();
						userIF.setHeadUrl(userDO.getAvatar());
						userIF.setName(userDO.getName());
						users.add(userIF);
					}
				}
				roomIF.setUsers(users);
				infos.add(roomIF);
			}
			ProxyRoomList msg = new ProxyRoomList();
			msg.setInfos(infos);
			user.send(msg);
		}else{
			ProxyRoomList msg = new ProxyRoomList();
			List<ProxyRoomIF> infos = new ArrayList<ProxyRoomIF>();
			msg.setInfos(infos);
			user.send(msg);
		}
		
	}

	

	

}
