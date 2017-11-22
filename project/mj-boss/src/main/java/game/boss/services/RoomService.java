package game.boss.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.isnowfox.core.thread.FrameQueueContainer;

import game.boss.SceneUserInfo;
import game.boss.ServerRuntimeException;
import game.boss.dao.dao.Room2Dao;
import game.boss.dao.dao.RoomChapterDao;
import game.boss.dao.dao.RoomCheckIdPoolDao;
import game.boss.dao.dao.RoomDao;
import game.boss.dao.dao.RoomDetailDao;
import game.boss.dao.dao.RoomUserDao;
import game.boss.dao.dao.UserDao;
import game.boss.dao.dao.UserLinkRoomDao;
import game.boss.dao.entity.Room2DO;
import game.boss.dao.entity.RoomChapterDO;
import game.boss.dao.entity.RoomCheckIdPoolDO;
import game.boss.dao.entity.RoomDO;
import game.boss.dao.entity.RoomDetailDO;
import game.boss.dao.entity.RoomUserDO;
import game.boss.dao.entity.UserDO;
import game.boss.dao.entity.UserLinkRoomDO;
import game.boss.model.Room2;
import game.boss.model.Room2.TYPE;
import game.boss.model.User;
import game.boss.net.BossService;
import game.boss.type.RoomCheckIdState;
import game.douniu.scene.msg.ChapterEnd2Msg;
import game.douniu.scene.msg.ChapterStart2Msg;
import game.douniu.scene.msg.ChapterUserMsg;
import game.douniu.scene.msg.CheckExitRoom2Msg;
import game.douniu.scene.msg.DelRoom2Msg;
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
import mj.net.message.login.UserIF;
import mj.net.message.login.WangbangPlayBackRet;
import mj.net.message.login.douniu.JoinRoomResult;

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
    @Autowired
    private Room2Dao room2Dao;
    /**
     * roomid,room2
     */
    private HashMap<Integer, Room2> room2Map = new HashMap<Integer, Room2>();

    /**
     * createuserid,room2
     */
    private HashMap<Integer, Room2> createUserRoom2Map = new HashMap<Integer, Room2>();

    private HashMap<String, Room2> checkIdRoom2Map = new HashMap<>();
    private HashMap<Integer, Room2> joinUserRoomMap = new HashMap<>();
    
    
    /**
     * 鎴块棿鏄犲皠
     */

    /**
     * 鍒涘缓
     */
    /**
     * 鎴块棿鏄犲皠
     */
    
    /**
     * 代理id,room
     */
    private HashMap<Integer, List<Integer>> proxyRoom = new HashMap<>();
    /**
     * 鎴块棿鏄犲皠
     */
    private HashMap<String, Room2> checkIdRoomMap = new HashMap<>();

    @Autowired
    private RoomDetailDao roomDetailDao;
    @Autowired
    RoomChapterDao roomChapterDao;
    
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
    
    public void createRoom2(CreateRoom msg,User user,int sceneId){
//    	UserDO userDO = userDao.get(user.getUserId());
    	msg.addOptions(new OptionEntry("isProxy","0"));
    	Config config = new Config(msg);
    	{
        	/**
        	 * 房卡不足时
        	 */
//              int max = config.getInt(Config.CHAPTER_MAX);
//              int gold = (int) Math.ceil(max / 8);
//              if (userDO.getGold()< gold) {
//                  sendNoGold(user); 
//                  sendCreateRoomRet(user, false, null);
//                  return;
//              }
    	}
         asyncDbService.excuete(user, () -> {
        	Room2 room2 = createUserRoom2Map.get(user.getUserId());
        	if(room2!=null){
        		closeRoom2(room2,user);
        	}
    		String roomCheckId = getBufferId();
    		Room2DO room2do = new Room2DO();
    		room2do.setRoomCheckId(roomCheckId);
    		room2do.setConfig(config);
    		room2do.setCreateUserId(user.getUserId());
    		room2do.setEnd(false);
    		room2do.setStart(true);
    		room2do.setSceneId(sceneId);
    		room2do.setStartDate(new Date());
    		room2do.setUserMax(5);
    		room2 = new Room2(room2do);
    		room2.setStart(false);
    		int roomId = (int) room2Dao.insert(room2do);
    		room2.setRoomId(roomId);
    		if(sceneId==1003){
    			room2.setType(TYPE.DN);
    		}else if(sceneId==1000){
    			room2.setType(TYPE.MJ);
    		}
    		room2Map.put(roomId, room2);
    		checkIdRoom2Map.put(roomCheckId,room2);
    		createUserRoom2Map.put(user.getUserId(), room2);
    		user.send(new CreateRoomRet(true,room2do.getRoomCheckId()));
        	
         });
    }
    /**
     * 未完善
    * @Title: closeRoom2 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param room2
    * @param @param user    设定文件 
    * @return void    返回类型 
    * @throws
     */
    private void closeRoom2(Room2 room2,User user){
    	try{
    	createUserRoom2Map.remove(user.getUserId());
    	room2Map.remove(room2.getRoomId());
    	checkIdRoom2Map.remove(room2.getRoomDo().getRoomCheckId());
    	Room2DO roomDo = room2.getRoomDo();
    	roomDo.setStart(false);
    	roomDo.setEnd(true);
    	roomDo.setEndDate(new Date());
    	run(()->{
    		room2Dao.update(roomDo);
    		roomCheckIdPoolDao.updatePartial(
                    Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.NO_USE.ordinal()),
                    RoomCheckIdPoolDO.Table.ID, roomDo.getRoomCheckId(),
                    RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.BUFFER.ordinal()
            );
    	});
    	}catch (Exception e) {
			log.error("释放房间 时出错，房间号["+room2.getRoomDo().getRoomCheckId()+"],房间id["+room2.getRoomId()+"]");
			log.error("释放房间出错原因:["+e.getMessage()+"]");
		}
    }
    
    public void joinRoom2(String roomNO,User user){
    	run(()->{
    		Room2 room2 = checkIdRoom2Map.get(roomNO);
    		if(room2!=null){
    			if(room2.isStart()){
    				boolean  has = false;
    				int id = room2.getRoomId();
    				List<RoomUserDO> find = roomUserDao.find(5,RoomUserDO.Table.ROOM_ID,id);
    				if(find!=null){
    					for (int i = 0; i < find.size(); i++) {
    						if(find.get(i).getUserId()==user.getUserId()&&find.get(i).getLocationIndex()>=0){
    							has = true;
    						}
    						
    					}
    				}
    				if(!has){
    					user.send(new JoinRoomResult(false, "该房间已经开始了"));
    					return;
    				}
    			}
    			final TYPE type = room2.getType();
    			joinDNRoomDb(room2, user,result->{
    				if(result){
    					
    					switch (type) {
						case MJ:
							user.send(new JoinRoomResult(true,"MJ"));
							break;
						case DN:
							user.send(new JoinRoomResult(true,"DN"));
							break;
						default:
							break;
						}
    					
    				}
    			});
    		}else{
    			Room2DO roomDo = room2Dao.findObject(Room2DO.Table.ROOM_CHECK_ID,roomNO);
    			if(roomDo==null){
    				user.send(new JoinRoomResult(false, "不存在该房间"));
    				return;
    			}
    			if(roomDo.getEnd()){
    				user.send(new JoinRoomResult(false, "该房间已经结束了"));
    				return;
    			}
    			room2 = new Room2(roomDo);
    			room2.setRoomId(roomDo.getId());
//    			room2.setStart(true);
    			List<RoomUserDO> users = roomUserDao.find(10,RoomUserDO.Table.ROOM_ID,roomDo.getId());
    			for (int i = 0; i < users.size(); i++) {
					RoomUserDO roomUserDO = users.get(i);
					if(roomUserDO.getLocationIndex() >= 0 && roomUserDO.getState()<3 && roomUserDO.getState()>0){
						if(roomUserDO.getUserId()==user.getUserId())
							room2.addUser(user);
						else
							room2.addUser(userDao.get(roomUserDO.getUserId()));
					}
					
				}
    			final TYPE type = room2.getType();
    			joinDNRoomDb(room2, user,result->{
    				if(result){
    					switch (type) {
						case MJ:
							user.send(new JoinRoomResult(true,"MJ"));
							break;
						case DN:
							user.send(new JoinRoomResult(true,"DN"));
							break;
						default:
							break;
    					}
    				}
    			});
    			
    		}
    		
    	});
    }
    private void joinDNRoomDb(Room2 room2,User user,Consumer<Boolean> callback){
    	Room2DO roomDO = room2.getRoomDo();
    	int userId = user.getUserId();
    	user.setJoinHomeGatewaySuccess(false);
        user.setJoinHomeSceneSuccess(false);
        int locationIndex = room2.addUser(user);
        if(locationIndex==-1){
        	user.send(new JoinRoomResult(false,"该房间的人已经满了"));
        	callback.accept(false);
        	return;
        }
        RoomUserDO roomUserDO = roomUserDao.get(userId);
        if (roomUserDO == null) {
            roomUserDO = new RoomUserDO();
        }
        roomUserDO.setUserId(userId);
        roomUserDO.setStartDate(new Date());
        roomUserDO.setRoomId(room2.getRoomId());
        roomUserDO.setRoomCheckId(roomDO.getRoomCheckId());
        roomUserDO.setLocationIndex(locationIndex);
        user.setJoinRoomCallback(callback);
        joinUserRoomMap.put(user.getUserId(), room2);
        roomUserDao.replace(roomUserDO);
        bossService.startJoinScene2(user, room2, user.getSessionId());
        
    }
    

    public void proxyCreateRoom(CreateRoom msg,User user,int sceneId){
    	 run(() -> {
    		String typeName = msg.getProfile();
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
                       int gold = 0;
                       if("DK".equals(typeName)){
                    	   gold = max / 8;
                       }else{
                    	   gold = max / 20;
                       }
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
             asyncDbService.excuete(user, () -> {
                 Room2DO room = checkUserCreateRoom(user);
                 room = new Room2DO();
                 room.setCreateUserId(user.getUserId());
                 room.setRoomCheckId(roomCheckId);
                 room.setSceneId(sceneId);
                 room.setStart(true);
                 room.setStartDate(new Date());
                 room.setUserMax(config.getInt(Config.USER_NUM));
                 room.setConfig(config);
                 int isUpdate = roomCheckIdPoolDao.updatePartial(
                         Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.USE.ordinal()),
                         RoomCheckIdPoolDO.Table.ID, roomCheckId,
                         RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.BUFFER.ordinal()
                 );
                 if (isUpdate < 1) {
                     throw new RuntimeException("id浣跨敤閿欒!,id鐘舵�佷笉瀵�!" + roomCheckId);
                 }
                 long id = room2Dao.insert(room);
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
    



    
	private void sendCreateRoomRet(User user, boolean result, String roomCheckId) {
        user.send(new CreateRoomRet(result, roomCheckId));
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
                    if(callback!=null)
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
    }


    public void exitRoomSceneSuccess(int userId, int sceneId, boolean result,int roomid) {
//        userService.handlerUser(userId, user -> {
//            run(() -> {
//                if (result) {
//                    bossService.startExitRoomGateway(user, sceneId);
//                    Room2 room = room2Map.get(roomid);
//                    if(room!=null){
//                    	Map<Integer, UserDO> map = room.getMap();
//                    	for (int i = 0; i < 4; i++) {
//							UserDO userDO = map.get(i);
//							if(userDO!=null && userDO.getId()==userId){
//								map.remove(i);
//							}
//						}
//                    }
//                } else {
//                    sendCannotExitRoom(user);
//                }
//            });
//        });
    }

    public void exitRoomGatewaySuccess(User user) {
        run(() -> {
            exitRoomSuccess(user);
        });
    }





    private void exitRoomSuccess(User user) {
//        if (frameThread != Thread.currentThread()) {
//            throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤");
//        }
//
//        int userId = user.getUserId();
//        Room room = joinUserRoomMap.remove(user.getUserId());
//        if (room != null) {
//            RoomDO roomDO = room.getRoomDO();
//            room.removeUser(userId);
//            if (roomDO.getUser0() == userId) {
//                roomDO.setUser0(0);
//            } else if (roomDO.getUser1() == userId) {
//                roomDO.setUser1(0);
//            } else if (roomDO.getUser2() == userId) {
//                roomDO.setUser2(0);
//            } else if (roomDO.getUser3() == userId) {
//                roomDO.setUser3(0);
//            } else {
//                sendAlreadyExitRoom(user);
//                sendExitRoomRet(user);
//                return;
//            }
//
//            asyncDbService.excuete(user, () -> {
//                roomUserDao.del(new RoomUserDO.Key(userId));
//                roomDO.setVersion(roomDO.getVersion() + 1);
//                roomDao.update(roomDO);
//                sendExitRoomRet(user);
//            });
//        } else {
//            sendAlreadyExitRoom(user);
//            sendExitRoomRet(user);
//        }
    }

//
//    public void joinRoom(String roomCheckId, User user) {
//        run(() -> {
//            if (frameThread != Thread.currentThread()) {
//                throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤");
//            }
//
//            {
//                Room2 room = checkIdRoomMap.get(roomCheckId);
//                if (room != null) {
//                    joinDNRoomDb( room,user, result -> {
//                        sendJoinRoomRet(user, result);
//                    });
//                    return;
//                }
//            }
//            asyncDbService.excuete(user, () -> {
//                Room2DO roomDO = room2Dao.findObject(
//                        RoomDO.Table.ROOM_CHECK_ID, roomCheckId,
//                        RoomDO.Table.START, true
//                );
//
//                //璇诲彇鍏ㄩ儴杩涘叆娓告垙鐨勭敤鎴蜂俊鎭�
//                if (roomDO != null) {
//                    Room2 room = new Room2(roomDO);
//                    for (int i = 0; i < 4; i++) {
//                        try {
//                            int userId = (Integer) BeanUtils.getPropertyDescriptor(RoomDO.class, "User" + i).getReadMethod().invoke(room.getRoomDO());
//                            if (userId > -1) {
//                                UserDO userDO = userDao.get(userId);
//                                if (userDO != null) {
//                                    room.addUser(i, userDO);
//                                }
//                            }
//                        } catch (Exception e) {
//                            throw new ServerRuntimeException(e);
//                        }
//                    }
//                    run(() -> {
//                    	joinDNRoomDb( room,user, result -> {
//                            sendJoinRoomRet(user, result);
//                        });
//                    });
//                } else {
//                    sendErrorRoomCheckId(user);
//                    sendJoinRoomRet(user, false);
//                }
//            });
//        });
//    }

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
//			String querySql = "select id,room_check_id from room "
//					+ "where (user0=? or user1=? or user2=? or user3=?) and start=? and end=? "
//					+ "order by "+RoomUserDO.Table.START_DATE;
        	String querySql = "SELECT room2.id AS id,room2.room_check_id AS room_check_id "
        			+ "FROM room2 INNER JOIN room_user "
        			+ "ON room2.id = room_user.room_id "
        			+ "WHERE room2.start = TRUE AND room2.end = FALSE AND room_user.user_id=? and room_user.location_index>=0 "
        			+ " order by room2.start_date desc";
			String updateSql = "update room set start=?, end=?,version=? where id=?";
        	try {
        		conn = room2Dao.getDataSource().getConnection();
        		pst = conn.prepareStatement(querySql);
        		int i=1;
				pst.setInt(1, user.getUserId());
//				pst.setInt(i++, user.getUserId());
//				pst.setInt(i++, user.getUserId());
//				pst.setInt(i++, user.getUserId());
//				pst.setBoolean(i++, true);
//				pst.setBoolean(i++, false);
				rs = pst.executeQuery();
				String roomCheckId = null;
				if(rs!=null){
					int count = 0;
					while(rs.next()){
						int id = rs.getInt("id");
						Room2DO roomDO = room2Dao.get(id);
						
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
    private Room2DO checkUserCreateRoom(User user) {
        if (frameThread == Thread.currentThread()) {
            throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤,涓嶈兘鍐峳oom 绾跨▼浣跨敤");
        }
        Room2DO room = room2Dao.findObject(
                Room2DO.Table.CREATE_USER_ID, user.getUserId(),
                Room2DO.Table.START, true
        );
        if (room != null) {
//            initRoomData(user, room);
            return room;
        }
        return null;
    }

    private void initRoomData(User user, Room2DO roomDO) {
        run(() -> {
            initRoomData(user, new Room2(roomDO));
        });
    }

    private boolean initRoomData(User user, Room2 room) {
        if (frameThread != Thread.currentThread()) {
            throw new ServerRuntimeException("鍙兘鍦ㄦ寚瀹氱殑绾跨▼璋冪敤");
        }
        Room2DO roomDO = room.getRoomDo();
        if (!room2Map.containsKey(roomDO.getId())) {
            checkIdRoomMap.put(roomDO.getRoomCheckId(), room);
            int isProxy = room.getRoomDo().getConfig().getInt("isProxy");
            if(isProxy==0)
            	createUserRoom2Map.put(roomDO.getCreateUserId(), room);
            room2Map.put(roomDO.getId(), room);
            return true;
        }
        return false;
    }

    private LinkedBlockingQueue<String> idBuffer = new LinkedBlockingQueue<>();

    /**
     * 鐢熸垚涓�涓殢鏈篿d 涓嶉噸澶�!
     */
    private String getBufferId() {

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

    private void sendNoGold(User user) {
        user.noticeError("room.noGold");
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
  		List<RoomDetailDO> roomDetailDo=roomDetailDao.find(24,RoomDetailDO.Table.CHECK_ROOM_ID,checkRoomId, 
  				RoomDetailDO.Table.CHEPTER_INDEX,chapterIndex);  
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
				Room2 room = room2Map.get(roomId);
				if(room == null){
					boolean b = list.remove(roomId);
					continue;
				}
				ProxyRoomIF roomIF = new ProxyRoomIF();
				roomIF.setRoomNo(room.getRoomDo().getRoomCheckId());
				List<UserIF> users = new ArrayList<UserIF>();
				UserDO[] map = room.getUsers();
				if(map!=null){
					for (int j = 0; j < map.length; j++) {
						UserDO userDO = map[j];
						if(userDO!=null){
							UserIF userIF = new UserIF();
							userIF.setHeadUrl(userDO.getAvatar());
							userIF.setName(userDO.getName());
							users.add(userIF);
						}
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
	
	/**
	 * 
	* @Title: douniuChapterStart 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param msg    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void douniuChapterStart(ChapterStart2Msg msg) {
		Room2 room2 = room2Map.get(msg.getRoomId());
		room2.setStart(true);
		UserDO[] users = room2.getUsers();
		if(users!=null){
			for (int i = 0; i < users.length; i++) {
				UserDO userDO = users[i];
				if(userDO==null){
					continue;
				}
				RoomChapterDO chapterDO = new RoomChapterDO();
				chapterDO.setChapterNum(msg.getNum());
				chapterDO.setRoomId(msg.getRoomId());
				chapterDO.setStartDate(new Date());
				chapterDO.setUserId(userDO.getId());
				chapterDO.setState(1);
				chapterDO.setUserIndex(i);
				chapterDO.setUserName(userDO.getName());
				roomChapterDao.insert(chapterDO);
			}
		}
		
	}



	public void douniuChapterEnd(ChapterEnd2Msg msg) {
		int roomId = msg.getRoomId();
		int num = msg.getNum();
		int zhuangIndex = msg.getZhuangIndex();
		List<ChapterUserMsg> chapterUserMsgs = msg.getChapterUserMsgs();
		if(chapterUserMsgs!=null){
			for (ChapterUserMsg chapterUserMsg : chapterUserMsgs) {
				int locationIndex = chapterUserMsg.getLocationIndex();
				int userId = chapterUserMsg.getUserId();
				int[] pais = chapterUserMsg.getPais();
				int paiType = chapterUserMsg.getPaiType();
				int score = chapterUserMsg.getScore();
				RoomChapterDO findObject = roomChapterDao.findObject(RoomChapterDO.Table.ROOM_ID,roomId,
																		RoomChapterDO.Table.CHAPTER_NUM,num,
																			RoomChapterDO.Table.USER_ID,userId);
				findObject.setEndDate(new Date());
				findObject.setScore(score);
				findObject.setPais(Arrays.toString(pais) + ";paiType="+paiType);
				findObject.setState(2);
				findObject.setIsZhuang(locationIndex==zhuangIndex);
				roomChapterDao.update(findObject);
				
			}
		}
	}



	public void douniuDelRoom(DelRoom2Msg msg) {
		List<SceneUserInfo> infos = msg.getInfos();
		int roomId = msg.getRoomId();
		int sceneId = msg.getSceneId();
		if(infos!=null){
			for (int i = 0; i < infos.size(); i++) {
				SceneUserInfo sceneUserInfo = infos.get(i);
				Room2 room2 = room2Map.get(msg.getRoomId());
				String checkId = room2.getRoomDo().getRoomCheckId();
				createUserRoom2Map.remove(msg.getCreateUserId());
				checkIdRoom2Map.remove(checkId);
				int userId = sceneUserInfo.getUserId();
				int score = sceneUserInfo.getScore();
				joinUserRoomMap.remove(userId);
				RoomUserDO findObject = roomUserDao.findObject(RoomUserDO.Table.ROOM_ID,roomId,
										RoomUserDO.Table.USER_ID,userId);
				findObject.setEndDate(new Date());
				findObject.setScore(score);
				findObject.setState(5);
				roomUserDao.update(findObject);
			}
		}
		Room2DO room2do = room2Dao.get(roomId);
		room2do.setStart(false);
		room2do.setEnd(true);
		room2do.setEndDate(new Date());
		room2Dao.update(room2do);
		
		bossService.delRoomToScene(sceneId,roomId);
		bossService.startDelRoomGateway(msg.getInfos());
		
	}
	 public void checkOffline(User user) {
	        run(() -> {
	            Room2 room = joinUserRoomMap.get(user.getUserId());
	            if (room != null) {
	                bossService.startOfflineScene(user, room.getRoomId(),room.getRoomDo().getSceneId(), user.getSessionId());
	            } else {
	                log.info("鐜╁涓嶅湪鎴块棿锛屾棤娉曞彂閫佺绾挎秷鎭紒", user);
	            }
	        });
	    }


	public void douniuExitRoom(CheckExitRoom2Msg msg) {
		userService.handlerUser(msg.getUserId(), (user)->{
			bossService.startExitRoomGateway(user, msg.getSceneId());
			Room2 room2 = room2Map.get(msg.getRoomId());
			UserDO userDO = user.getUserDO();
			if(userDO==null){
				userDO = userDao.get(user.getUserId());
			}
			room2.removeUser(userDO);
			user.send(new ExitRoomRet());
		});
		RoomUserDO findObject = roomUserDao.findObject(RoomUserDO.Table.USER_ID,msg.getUserId(),
								RoomUserDO.Table.ROOM_ID,msg.getRoomId());
		findObject.setEndDate(new Date());
		findObject.setScore(msg.getScore());
		findObject.setState(3);
		findObject.setLocationIndex(-1);
		roomUserDao.update(findObject);
		joinUserRoomMap.remove(msg.getUserId());
		bossService.startDNExitRoomScene(msg);
		
	}

	public void delRoomGatewaySuccess(User user) {
			
	}

	

	

}
