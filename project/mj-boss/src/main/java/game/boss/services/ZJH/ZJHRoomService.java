package game.boss.services.ZJH;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

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

import ZJH.data.ZJHConfig;
import game.boss.ServerRuntimeException;
import game.boss.dao.dao.RoomCheckIdPoolDao;
import game.boss.dao.dao.UserLinkRoomDao;
import game.boss.dao.entity.RoomCheckIdPoolDO;
import game.boss.dao.entity.UserLinkRoomDO;
import game.boss.model.Room;
import game.boss.model.User;
import game.boss.model.ZJHRoom;
import game.boss.net.BossService;
import game.boss.poker.dao.PokerUserDao;
import game.boss.poker.dao.TbPkChapterDao;
import game.boss.poker.dao.TbPkRoomDao;
import game.boss.poker.dao.TbPkRoomUserDao;
import game.boss.poker.dao.TbPkUserChapterDao;
import game.boss.poker.dao.entiy.TbPkChapterDO;
import game.boss.poker.dao.entiy.TbPkRoomDO;
import game.boss.poker.dao.entiy.TbPkRoomUserDO;
import game.boss.poker.dao.entiy.TbPkUserChapterDO;
import game.boss.poker.dao.entiy.UserDO2;
import game.boss.services.AsyncDbService;
import game.boss.services.BaseService;
import game.boss.services.RoomService;
import game.boss.services.UserService;
import game.boss.type.RoomCheckIdState;
import game.zjh.scene.msg.CheckDelZJHRoomMsg;
import game.zjh.scene.msg.RoomOverMsg;
import game.zjh.scene.msg.ZJHChapterStartMsg;
import mj.data.Config;
import mj.data.Version;
import mj.net.message.game.zjh.ReadyGame;
import mj.net.message.game.zjh.ZJHRoomHistory;
import mj.net.message.game.zjh.ZJHRoomHistoryList;
import mj.net.message.login.DelRoomRet;
import mj.net.message.login.zjh.CreateZJHRoom;
import mj.net.message.login.zjh.CreateZJHRoomResult;
import mj.net.message.login.zjh.DelZJHRoomResult;
import mj.net.message.login.zjh.JoinZJHRoomResult;
@Service
public class ZJHRoomService extends FrameQueueContainer implements BaseService  {
	
	 private static final Logger log = LoggerFactory.getLogger(RoomService.class);
	 
	private Map<Integer, ZJHRoom> joinZJHroomMap = new HashMap<Integer, ZJHRoom>();//加入房间者映射
	
	private HashMap<Integer, ZJHRoom> crateUserZJHRoomMap = new HashMap<>();//创建者房间映射
	 /**
     * roomId与房间映射
     */
    private HashMap<Integer, ZJHRoom> roomMap = new HashMap<>();
    /**
     * 位置与房间id映射
     */
    private HashMap<Integer, Integer> locationRoomId = new HashMap<Integer, Integer>();
    /**
     * 房间号与房间映射
     */
    private HashMap<String, ZJHRoom> checkIdRoomMap = new HashMap<>();
    
    

    @Autowired
    private BossService bossService;
	@Autowired
	private TbPkRoomDao roomDao;
	
	 @Autowired
	 private UserService userService;
	@Autowired
	private TbPkRoomUserDao roomUserDao;
	@Autowired
	private TbPkChapterDao chapterDao;
	@Autowired
	private TbPkUserChapterDao userChapterDao;
	
	
	private PokerUserDao userDao = new PokerUserDao();
	@Autowired
	private AsyncDbService asyncDbService;
	@Autowired
	private RoomCheckIdPoolDao roomCheckIdPoolDao;
	@Autowired
    private UserLinkRoomDao userLinkRoomDao;
	
		public ZJHRoomService() {
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
	 public void createRoom(CreateZJHRoom msg, User user) {
		  run(() -> {
	        	 UserDO2 user2 = userDao.get(user.getUserId());
	        	 Config config = new ZJHConfig(msg);
	            {
	            	/**
	            	 * 房卡不足时
	            	 */
	                  {
	                      int max = config.getInt(ZJHConfig.CHAPTERMAX);
	                      int gold = (int) Math.ceil(max / 10);
	                      if (user2.getGold()< gold) {
	                          sendNoGold(user);
	                          sendCreateRoomResult(user, false, null);
	                          return;
	                      }
	                  }
	                ZJHRoom room = crateUserZJHRoomMap.get(user.getUserId());
	                if (room != null) {
	                    sendAlreadyCreateRoom(user);
	                    sendCreateRoomResult(user, false, null);
	                    return;
	                }
	            }
	            String roomCheckId = getBufferId();
	            int sceneId = bossService.getRandomZJHSceneId();
	            asyncDbService.excuete(user, () -> {
	                TbPkRoomDO room = checkUserCreateRoom(user2);
	                if (room == null) {
	                    room = new TbPkRoomDO();
	                    room.setCreate_user(userDao.get(user.getUserId()));
	                    room.setRoomCheckId(roomCheckId);
	                    room.setSceneId(sceneId);
	                    room.setStart(true);
	                    room.setStartDate(new Date());
	                    room.setUserMax(config.getInt(ZJHConfig.USERNUM));
	                    room.setUuid(UUID.generateNoSep());
	                    room.setConfig(config);
	                    room.setChapterNums(config.getInt(ZJHConfig.CHAPTERMAX));
	                    room.setInitScore(config.getInt(ZJHConfig.CHUSHIFEN));
	                    room.setVersion(Version.version);
	                    int isUpdate = roomCheckIdPoolDao.updatePartial(
	                            Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.USE.ordinal()),
	                            RoomCheckIdPoolDO.Table.ID, roomCheckId,
	                            RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.BUFFER.ordinal()
	                    );
	                    if (isUpdate < 1) {
	                        throw new RuntimeException("id使用错误！id状态不正确" + roomCheckId);
	                    }
	                    roomDao.save(room);
	                    List<TbPkRoomDO> rooms = roomDao.getRoomsByRoomCheckId(roomCheckId, true);
	                    if(rooms.size()>1){
	                    	roomDao.revome((int)room.getId());
	                         sendCreateZJHRoomError(user);
	                         sendCreateRoomResult(user, false, null);
	                         throw new RuntimeException("严重错误!roomCheckId 使用错误!,创建房间失败" + rooms);
	                    }else{
	                    	 initRoomData(user, room);
	                         sendCreateRoomSuccess(user);
	                         sendCreateRoomResult(user, true, room.getRoomCheckId());
	                    }
	                } else {
	                    freeId(roomCheckId);
	                    sendAlreadyCreateRoom(user);
	                    sendCreateRoomResult(user, false, null);
	                }
	            });
				
	        });
	    }
	 //创建房间错误
	 private void sendCreateZJHRoomError(User user) {
		 user.noticeError("room.createRoomError");
	}
	/**
	  * 加入房间
	  *@param roomCheckId
	  *@param user
	  *@return
	  * 2017年6月30日
	  */
	 public void joinZJHroom(String roomCheckId, User user) {
		 run(() -> {
	            if (frameThread != Thread.currentThread()) {
	                throw new ServerRuntimeException("帧不同步");
	            }
	            {
	                ZJHRoom ZJHroom = checkIdRoomMap.get(roomCheckId);
	                if (ZJHroom != null) {
	                    joinRoom(user, ZJHroom, result -> {
	                        sendJoinRoomRet(user, result);
	                    });
	                    return;
	                }
	            }
	            asyncDbService.excuete(user, () -> {
	            	//根据房间号获取房间池
	                List<TbPkRoomDO> roomsByRoomCheckId = roomDao.getRoomsByRoomCheckId(roomCheckId, true);
	                TbPkRoomDO tbkpRoomDO = roomsByRoomCheckId.get(0);
	                if(tbkpRoomDO != null ){
	                	ZJHRoom ZJHroom = new ZJHRoom(tbkpRoomDO);
	                	//获取房间对应的用户
	                	List<TbPkRoomUserDO> TbPkRoomUserDO = ZJHroom.getTbPkRoomDO().getRoomUsers();
	                	for (int i = 0; i < TbPkRoomUserDO.size(); i++) {
	                		try{
	                			Integer userId = TbPkRoomUserDO.get(i).getUser().getId();
	                		if( userId> -1){
	                			UserDO2 userDO2 = userDao.get(userId);
	                			if(userDO2 != null){
	                				ZJHroom.addUser(i, TbPkRoomUserDO.get(i).getUser());
	                			}
	                		}
	                		}catch(Exception e){
	                			throw new ServerRuntimeException(e);
	                		}
	                	}
	                	run(() -> {
	                        joinRoom(user, ZJHroom, result -> {
	                            sendJoinRoomRet(user, result);
	                        });
	                    });
	                	
	                }else{
	                	sendErrorRoomCheckId(user);
	                    sendJoinRoomRet(user, false);
	                }
	            });
	        });
		}
	private void sendErrorRoomCheckId(User user) {
		 user.noticeError("room.errorRoomCheckId");		
	}
	 //加入房间结果
	private void sendJoinRoomRet(User user, Boolean result) {
		 user.send(new JoinZJHRoomResult(result));
	}
	/**
	 * 加入房间
	 *@param user
	 *@param zJHroom
	 *@param callback
	 *@return
	 * 2017年6月30日
	 */
	private void joinRoom(User user, ZJHRoom zJHroom, Consumer<Boolean> callback) {
		 if (frameThread != Thread.currentThread()) {
	            throw new ServerRuntimeException("只能在指定的线程调用");
	        }
		 TbPkRoomDO tbPkRoomDO = zJHroom.getTbPkRoomDO();
		 user.setJoinHomeGatewaySuccess(false);
		 user.setJoinHomeSceneSuccess(false);
		 Config config = tbPkRoomDO.getPokerConfig();
			Integer locationIndex = -1;
			List<TbPkRoomUserDO> roomUsers = tbPkRoomDO.getRoomUsers();
			if(roomUsers == null || (roomUsers.size()-1) < config.getInt(ZJHConfig.USERNUM)){
				String name = user.getUserDO().getName();
		        int userId = user.getUserId();
		        if(userId == tbPkRoomDO.getUser().getId()){
		        	locationIndex = 0;
		        	locationRoomId.put(tbPkRoomDO.getId(), 0);
		        }else {
		        	locationIndex = locationRoomId.get(tbPkRoomDO.getId())+1;
		        	locationRoomId.put(tbPkRoomDO.getId(), locationIndex);
		        }
		        asyncDbService.excuete(user, () ->{ 
		        	TbPkRoomUserDO roomUserDo = new TbPkRoomUserDO();
		        	UserDO2 userDO2 = new UserDO2();
		        	userDO2.setId(userId);
		        	roomUserDo.setUser(userDO2);
		        	roomUserDo.setRoom(tbPkRoomDO);
		        	roomUserDo.setLocationIndex(locationRoomId.get(tbPkRoomDO.getId()));
			        roomUserDao.save(roomUserDo);
			        user.setJoinRoomCallback(callback);
		            joinRoomSuccess(user, zJHroom, roomUserDo);
		        });
			}else if((tbPkRoomDO.getRoomUsers().size()-1) >= config.getInt(ZJHConfig.USERNUM)){
	        	//房间满了
	        	sendRoomFull(user);
	        	callback.accept(false);
	        	 return;
	        }
	}
	//房间满了
	private void sendRoomFull(User user) {
		 user.noticeError("room.full");
	}
	private LinkedBlockingQueue<String> idBuffer = new LinkedBlockingQueue<>();
	    /**
	     * 生成一个随机的check——id
	     */
	    private String getBufferId() {
	        if (frameThread != Thread.currentThread()) {
	            throw new ServerRuntimeException("只能在指定的线程调用");
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
	            //查找多个未使用id,然后缓存
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
	            throw new ServerRuntimeException("id  初始化呢严重问题！！ 超出重复上线！！需要人工介入");
	        });
	    }
	    /**
	     * 查看玩家是否有已經開始的房間
	     * 
	     */
	    private TbPkRoomDO checkUserCreateRoom(UserDO2 user) {
	        if (frameThread == Thread.currentThread()) {
	            throw new ServerRuntimeException("只能在指定的线程调用,不能再room 线程使用");
	        }
	        List<TbPkRoomUserDO> rooms = user.getRooms();
	        for (TbPkRoomUserDO roomUser:rooms) {
				if(roomUser.getRoom().isStart()){
					return roomUser.getRoom();
				}
			}
	        return null;
	    }
	    /**
	     * create_uesr映射map
	     *@param user
	     *@param TbPkRoomDO
	     *@return
	     * 2017年6月29日
	     */
		  private void initRoomData(User user, TbPkRoomDO TbPkRoomDO) {
		        run(() -> {
		            initRoomData(user, new ZJHRoom(TbPkRoomDO));
		        });
		    }
		    private boolean initRoomData(User user, ZJHRoom room) {
		        if (frameThread != Thread.currentThread()) {
		            throw new ServerRuntimeException("只在指定的线程中调用");
		        }
		        TbPkRoomDO roomDO = room.getTbPkRoomDO();
		        if (!roomMap.containsKey(roomDO.getId())) {
		            checkIdRoomMap.put(roomDO.getRoomCheckId(), room);
		            crateUserZJHRoomMap.put(roomDO.getCreate_user().getId(), room);
		            roomMap.put(roomDO.getId(), room);
		            
		            return true;
		        }
		        return false;
		    }
		    
		//加入房间成功    
		private void joinRoomSuccess(User user, ZJHRoom zJHroom, TbPkRoomUserDO roomUserDo2){
			 run(() -> {
				 
				 zJHroom.addUser(roomUserDo2.getLocationIndex(), roomUserDo2.getUser());
				 joinZJHroomMap.put(user.getUserId(), zJHroom);
//		            bossService.startJoinZJHScene(user, zJHroom, roomUserDo2, user.getSessionId());
		        });
		}
		//已经创建房间
		private void sendAlreadyCreateRoom(User user) {
			user.noticeError("user.AlreadyCreateRoom");
		}
		//创建房间结果
		private void sendCreateRoomResult(User user, boolean result, String checkId) {
			user.send(new CreateZJHRoomResult(result,checkId));
			
		}
		//創建房間成功
		private void sendCreateRoomSuccess(User user) {
	        user.noticeError("room.createRoomSuccess");
	    }
		//房卡不足
		private void sendNoGold(User user) {
			user.noticeError("noGold");
			
		}
		  public void joinRoomGatewaySuccess(User user) {
		        run(() -> {
		            user.setJoinHomeGatewaySuccess(true);
		            if (user.isJoinHomeGatewaySuccess() && user.isJoinHomeSceneSuccess()) {
		                joinHomeSceneSuccess(user);
		            }
		            
		        });
		    }
		 


		private void joinHomeSceneSuccess(User user) {
		        if (frameThread != Thread.currentThread()) {
		            throw new ServerRuntimeException("只在指定线程中调用");
		        }
		        Consumer<Boolean> callback = user.getJoinRoomCallback();
		        callback.accept(true);
		        user.setJoinRoomCallback(null);
		        user.send(new ReadyGame());
		    }
		@Override
		protected void errorHandler(Throwable e) {
			log.error("error",e);
		}

		@Override
		protected void threadMethod(int arg0, long arg1, long arg2) {
			
		}
		/**
		 * 加入房间成功
		 * @param userId
		 * @param succcess
		 */
		public void joinRoomSceneSuccess(int userId, boolean succcess) {
		        userService.handlerUser(userId, user -> {
		            run(() -> {
		                if (succcess) {
		                    user.setJoinHomeSceneSuccess(true);
		                    if (user.isJoinHomeGatewaySuccess() && user.isJoinHomeSceneSuccess()) {
		                        joinHomeSceneSuccess(user);
		                    }
		                } else {
		                    Consumer<Boolean> callback = user.getJoinRoomCallback();
		                    callback.accept(false);
		                    user.setJoinRoomCallback(null);
		                }
		            });
		        });
		    }

		 /**
		  * 删除房间
		  * @param crateUserId
		  * @param user
		  * @param isEnd
		  */
		public void delRoom(int getCrateUserId, User user, boolean isEnd) {
			 asyncDbService.excuete(getCrateUserId, () -> {
				 		TbPkRoomDO tbpoRoom = roomDao.getByCreateUserId(getCrateUserId);
				 		if(tbpoRoom == null){
				 			 run(() -> {
//				                    bossService.startDelZJHRoomScene(getCrateUserId, tbpoRoom, isEnd);
				                });
				 		}else {
			                log.error("关闭房间错误crateUserId:{}", getCrateUserId);
			                if (user != null && !isEnd) {
			                    sendAlreadyDelRoom(user);
			                    sendDelRoomRet(user);
			                }
			            }
			 });
			 
		}
		private void sendAlreadyDelRoom(User user) {
	        user.noticeError("room.alreadyDelRoom");
	    }

	    private void sendDelRoomRet(User user) {
	        user.send(new DelZJHRoomResult(true));
	    }

	    /**
	     * 删除房间成功
	     * @param delMsg
	     */
		public void delRoomSceneSuccess(CheckDelZJHRoomMsg delMsg) {
			userService.handlerUser(delMsg.getUserId(), user -> {
	            run(() -> {
	                if (delMsg.isResult()) {
	                    bossService.startZJHDelRoomGateway(delMsg.getInfos());
	                    delRoomSuccess(delMsg.getUserId(), user, delMsg.isEnd());
	                } else {
	                    log.error("房间不能关闭：crateUserId:{}", delMsg.getUserId());
	                    if (user != null && !delMsg.isEnd()) {
	                        sendCannotDelRoom(user);
	                    }
	                }
	            });
	        });
			
		}


		private void sendCannotDelRoom(User user) {
        user.send(new DelZJHRoomResult(false));
        user.noticeError("room.cannotDelRoom");
		}
		 public void delRoomGatewaySuccess(User user) {
		        run(() -> {
//		            delRoomSuccess(user);
		        });
		    }


		@SuppressWarnings("unused")
		private void delRoomSuccess(final int userId, User user, boolean end) {
			if (frameThread != Thread.currentThread()) {
	            throw new ServerRuntimeException("只在指定的线程调用");
	        }
			asyncDbService.excuete(userId, () -> {
				UserDO2 userDO = new UserDO2();
				userDO.setId(userId);
				TbPkRoomUserDO roomUser = roomUserDao.getByUserAndRoom(userDO, null);
				List<TbPkRoomUserDO> listByUserAndRoom = roomUserDao.getListByUserAndRoom(null,roomUser.getRoom());
				if(roomUser != null){
					TbPkRoomDO tbPkRoomDO = roomDao.get(roomUser.getRoom().getId());
					if(tbPkRoomDO != null){
						 run(() -> {
							 tbPkRoomDO.setEndDate(new Date());
							 crateUserZJHRoomMap.remove(userId);
		                     roomMap.remove(tbPkRoomDO.getId());
		                     checkIdRoomMap.remove(tbPkRoomDO.getRoomCheckId());
		                     for (int i = 0; i < listByUserAndRoom.size(); i++) {
		                    	 joinZJHroomMap.remove(listByUserAndRoom.get(i).getUser().getId());
							}
		                     //扣除房主房卡
		                     ZJHRoom zjhRoom = roomMap.get(tbPkRoomDO.getId());
		                     if(zjhRoom != null || zjhRoom.isStart()){
		                    	 userService.minusGold(tbPkRoomDO.getCreate_user().getId(), tbPkRoomDO.getPokerConfig());
		                     }
		                     
		                     asyncDbService.excuete(userId, () -> {
		                    	 tbPkRoomDO.setStart(false);
		                    	 tbPkRoomDO.setEnd(end);
		                    	 tbPkRoomDO.setVersion(tbPkRoomDO.getVersion() + 1);
		                         roomDao.update(tbPkRoomDO);
		                         //插入记录
		                         List<UserLinkRoomDO> userLinkRoomDOList = new ArrayList<>();
		                         for (int i = 0; i < listByUserAndRoom.size(); i++) {
		                        	 try {
										if(listByUserAndRoom.get(i).getUser().getId()>0){
											roomUserDao.revome(listByUserAndRoom.get(i).getRoom().getId());
										}
										if (tbPkRoomDO.getChapterNums() > 0) {
	                                        UserLinkRoomDO link = new UserLinkRoomDO();
	                                        link.setUserId(listByUserAndRoom.get(i).getUser().getId());
	                                        link.setRoomId(listByUserAndRoom.get(i).getRoom().getId());
	                                        BeanUtils.copyProperties(tbPkRoomDO, link, "key");
	                                        userLinkRoomDOList.add(link);
	                                    }
		                        	 } catch (Exception e) {
		                        	 }
								}
		                         if (userLinkRoomDOList.size() > 0) {
		                                userLinkRoomDao.insert(userLinkRoomDOList);
		                            }
		                       //释放checkId
		                            roomCheckIdPoolDao.updatePartial(
		                                    Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.NO_USE.ordinal()),
		                                    RoomCheckIdPoolDO.Table.ID, tbPkRoomDO.getRoomCheckId(),
		                                    RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.BUFFER.ordinal()
		                            );
		                            if (user != null) {
		                                sendDelRoomRet(user);
		                            }
		                     });
						 });
					}else {
	                    if (user != null && !end) {
	                        sendAlreadyDelRoom(user);
	                        sendDelRoomRet(user);
	                    }
	                }
				} else {
	                if (user != null && !end) {
	                    sendAlreadyDelRoom(user);
	                    sendDelRoomRet(user);
	                }
	            }
			});
		}

		
		public void chapterStart(ZJHChapterStartMsg startMsg) {
			 run(() -> {
		             ZJHRoom zjhRoom = roomMap.get(startMsg.getRoomId());
		            if (zjhRoom != null) {
		            	zjhRoom.setStart(true);
		            }
		        });
		}

		/**
		 * 查询总战绩
		 * @param user
		 */
		public void getHistoryList(User user) {
			UserDO2 userDO2 = new UserDO2();
			userDO2 = userDao.get(user.getUserId());
			ArrayList<ZJHRoomHistory> roomHistorys = new ArrayList<ZJHRoomHistory>();
			List<TbPkRoomUserDO> userRooms = userDO2.getRooms();//玩家的所有房间
			if(userRooms != null){
				for (int i = 0; i < userRooms.size(); i++) {
					TbPkRoomUserDO tbPkRoomUserDO = userRooms.get(i);
					List<TbPkRoomUserDO> roomUsers = tbPkRoomUserDO.getRoom().getRoomUsers();//房间内所有玩家
					List<TbPkChapterDO> byRoom = chapterDao.getByRoom(tbPkRoomUserDO.getRoom());//局数
					if(roomUsers != null){
						for (int j = 0; j < roomUsers.size(); j++) {
							Integer endScore = roomUsers.get(j).getEndScore();
							UserDO2 user2 = roomUsers.get(j).getUser();
							String userName = user2.getName();
							int userId = user2.getId();
							ZJHRoomHistory roomHistory = new ZJHRoomHistory();
							roomHistory.setroomCheckId(tbPkRoomUserDO.getRoom().getRoomCheckId());
							roomHistory.setUserId(userId);
							roomHistory.setStandardCount(tbPkRoomUserDO.getRoom().getChapterNums());
							roomHistory.setScore(endScore - tbPkRoomUserDO.getRoom().getInitScore());
							roomHistory.setWinCount(roomUsers.get(i).getWinCount());
							roomHistory.setFactCount(byRoom.size());
							roomHistorys.add(roomHistory);
							roomHistory.setUserName(userName);
						}
					}
					
				}
			}
			ZJHRoomHistoryList historyList = new ZJHRoomHistoryList();
			historyList.setRoomHistorys(roomHistorys);
			user.send(historyList);
		}
		/**
		 * 获取每一局的战绩
		 * @param user
		 * @param roomId
		 */
		public void getEveryHistory(User user,String roomCheckId){
			List<TbPkRoomDO> roomsByRoomCheckId = roomDao.getRoomsByRoomCheckId(roomCheckId, true);
			if(roomsByRoomCheckId != null){
				TbPkRoomDO tbPkRoomDO = roomsByRoomCheckId.get(0);
				Integer chapterNums = tbPkRoomDO.getChapterNums();//总局数
				List<TbPkChapterDO> byRoom = chapterDao.getByRoom(tbPkRoomDO);
				for (int i = 0; i < byRoom.size(); i++) {
					TbPkChapterDO chapterDO = byRoom.get(i);
					Integer num = chapterDO.getNum();//当前局数
					List<TbPkUserChapterDO> byChapter = userChapterDao.getByChapter(chapterDO);
					for (int j = 0; j < byChapter.size(); j++) {
						TbPkUserChapterDO UserChapterDO = byChapter.get(i);
						Integer score = UserChapterDO.getScore();//每局结算后的分数
						int userId = UserChapterDO.getUser().getId();
						String userName = UserChapterDO.getUser().getName();
					}
				}
			}
			
			
		}
		
}
