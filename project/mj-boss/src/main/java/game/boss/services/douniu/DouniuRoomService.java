package game.boss.services.douniu;

import java.util.ArrayList;
import java.util.Arrays;
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

import Douniu.data.DouniuConfig;
import Douniu.data.DouniuUtils;
import game.boss.ServerRuntimeException;
import game.boss.dao.dao.RoomCheckIdPoolDao;
import game.boss.dao.dao.RoomUserDao;
import game.boss.dao.dao.UserDao;
import game.boss.dao.dao.UserLinkRoomDao;
import game.boss.dao.entity.RoomCheckIdPoolDO;
import game.boss.dao.entity.RoomDO;
import game.boss.dao.entity.RoomUserDO;
import game.boss.dao.entity.UserLinkRoomDO;
import game.boss.model.User;
import game.boss.model.DouniuRoom;
import game.boss.net.BossService;
import game.boss.poker.dao.dao.TbPkChapterDao;
import game.boss.poker.dao.dao.TbPkChapterUserDao;
//import game.boss.poker.dao.PokerUserDao;
import game.boss.poker.dao.dao.TbPkRoomDao;
import game.boss.poker.dao.dao.TbPkRoomUserDao;
import game.boss.poker.dao.entity.TbPkChapterDO;
import game.boss.poker.dao.entity.TbPkChapterUserDO;
import game.boss.poker.dao.entity.TbPkRoomDO;
import game.boss.poker.dao.entity.TbPkRoomUserDO;
import game.boss.poker.dao.entiy.UserDO2;
import game.boss.dao.entity.UserDO;
import game.boss.services.AsyncDbService;
import game.boss.services.BaseService;
import game.boss.services.UserService;
import game.boss.type.RoomCheckIdState;
import game.douniu.scene.msg.ChapterUserMsg;
import game.douniu.scene.msg.CheckDelDouniuRoomMsg;
import game.douniu.scene.msg.DouniuChapterEndMsg;
import game.douniu.scene.msg.DouniuChapterStartMsg;
import game.douniu.scene.msg.DouniuRecordRoomMsg;
import game.douniu.scene.msg.DouniuRecordUserRoomMsg;
import game.scene.msg.ChapterEndMsg;
import mj.data.Config;
import mj.data.Version;
import mj.net.message.game.douniu.ReadyGame;
import mj.net.message.login.douniu.CreateDouniuRoom;
import mj.net.message.login.douniu.CreateDouniuRoomResult;
import mj.net.message.login.douniu.DeDouniuRoomResult;
import mj.net.message.login.douniu.DouniuRoomHistory;
import mj.net.message.login.douniu.DouniuRoomHistoryListRet;
import mj.net.message.login.douniu.ExitDouniuRoom;
import mj.net.message.login.douniu.ExitDouniuRoomResult;
import mj.net.message.login.douniu.JoinDouniuRoomResult;

@Service
public class DouniuRoomService extends FrameQueueContainer implements BaseService {

	private static final Logger log = LoggerFactory.getLogger(DouniuRoomService.class);

	private Map<Integer, DouniuRoom> joinDouniuroomMap = new HashMap<Integer, DouniuRoom>();// 加入房间者映射

	private HashMap<Integer, DouniuRoom> crateUserDouniuRoomMap = new HashMap<>();// 创建者房间映射
	/**
	 * roomId与房间映射
	 */
	private HashMap<Integer, DouniuRoom> roomMap = new HashMap<>();
	/**
	 * 位置与房间id映射
	 */
	private HashMap<Integer, Integer> locationRoomId = new HashMap<Integer, Integer>();
	/**
	 * 房间号与房间映射
	 */
	private HashMap<String, DouniuRoom> checkIdRoomMap = new HashMap<>();

	@Autowired
	private BossService bossService;
	
	private UserDao pokerUserDao = new UserDao();

	@Autowired
	private TbPkRoomDao roomDao;
	@Autowired
	private TbPkRoomUserDao roomUserDao;
	@Autowired
	private TbPkChapterDao chapterDao;
	@Autowired
	private TbPkChapterUserDao chapterUserDao;
	/*@Autowired
	private TbPkRoomDO roomDO;*/
	@Autowired
	private UserDao userDao;
	@Autowired
	private AsyncDbService asyncDbService;
	@Autowired
	private RoomCheckIdPoolDao roomCheckIdPoolDao;
    @Autowired
    private UserLinkRoomDao userLinkRoomDao;
	
	public DouniuRoomService() {
		super(FRAME_TIME_SPAN, RUN_QUEUE_MAX);
	}

	@PostConstruct
	private void init() {
		String sql = "UPDATE " + RoomCheckIdPoolDO.Table.DB_TABLE_NAME + " SET `" + RoomCheckIdPoolDO.Table.STATE + "`="
				+ RoomCheckIdState.NO_USE.ordinal() + " WHERE `" + RoomCheckIdPoolDO.Table.STATE + "`="
				+ RoomCheckIdState.BUFFER.ordinal();

		log.debug("init:" + sql);
		roomCheckIdPoolDao.getJdbcTemplate().execute(sql);
		start();
	}

	public void createRoom(CreateDouniuRoom msg, User user) {
		run(() -> {
			/**
			 * 检查房卡
			 */
			UserDO userDO = userDao.get(user.getUserId());
			Config config = new DouniuConfig(msg.getOptions());

			 {
                int max = config.getInt(DouniuConfig.CHAPTER_MAX);
                int gold = (int) Math.ceil(max /15);
                if (userDao.get(user.getUserId()).getGold()< gold) {
                    sendNoGold(user);
                    user.send(new CreateDouniuRoomResult(false, null));
                    return;
                }
            }
			/**
			 * 检查map
			 */
			DouniuRoom room = crateUserDouniuRoomMap.get(user.getUserId());
			if (room != null) {
				user.noticeError("room.alreadyCreateRoom");
				user.send(new CreateDouniuRoomResult(false, null));
				return;
			} else {
				String roomCheckId =getBufferId();
				int sceneId = bossService.getRandomDouniuSceneId();
				asyncDbService.excuete(user, () -> {
					
					TbPkRoomDO roomDo = new TbPkRoomDO();
					roomDo.setCreateUserId(user.getUserId());
					roomDo.setRoomCheckId(roomCheckId);
					roomDo.setSceneId(sceneId);
					roomDo.setStart(true);
					roomDo.setEnd(false);
					roomDo.setStartDate(new Date());
					roomDo.setUserMax(config.getInt(Config.USER_NUM));
					roomDo.setUuid(java.util.UUID.randomUUID().toString());
					roomDo.setChapterNums(config.getInt(Config.CHAPTER_MAX));
					roomDo.setConfig(config);
//					roomDo.setVersion(Version.version);

					int isUpdate = roomCheckIdPoolDao.updatePartial(
							Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.USE.ordinal()),
							RoomCheckIdPoolDO.Table.ID, roomCheckId, RoomCheckIdPoolDO.Table.STATE,
							RoomCheckIdState.BUFFER.ordinal());

					if (isUpdate < 1) {
						log.error("严重异常  更新房间异常房间id" + roomCheckId);
						throw new RuntimeException("更新房间异常房间id" + roomCheckId);
					}
					
					// 同步到数据库中
					long id = roomDao.insert(roomDo);
					roomDo.setId((int) id);

					 List<TbPkRoomDO> roomDOs = roomDao.find(2, TbPkRoomDO.Table.ROOM_CHECK_ID, roomDo.getRoomCheckId(), TbPkRoomDO.Table.START, true);
	                    if (roomDOs.size() > 1) {
	                    	if(userDO.getLevel() == 0){
		                        roomDao.del(roomDo.getKey());
		                        user.noticeError("room.createRoomError");
		                        user.send(new CreateDouniuRoomResult(false, roomCheckId));
	                    	}
	                    } else {
	                        initRoomData(user, roomDo,userDO.getLevel());
	                        user.noticeError("room.createRoomSuccess");
	                        user.send(new CreateDouniuRoomResult(true, roomDo.getRoomCheckId()));
	                    }
					DouniuRoom douniuRoom = new DouniuRoom();
					douniuRoom.setTbPkRoomDO(roomDo);
					douniuRoom.setStart(false);		
					log.debug("create Room  to db done....roomDo{}:" + roomDo);
					checkIdRoomMap.put(roomCheckId, douniuRoom);
					crateUserDouniuRoomMap.put(user.getUserId(), douniuRoom);
					roomMap.put(roomDo.getId(), douniuRoom);
					user.noticeError("room.createRoomSuccess");
					user.send(new CreateDouniuRoomResult(true, roomCheckId));
				});
				
			}
		});
	}

	// 创建房间错误
	private void sendCreateDouniuRoomError(User user) {
		user.noticeError("room.createRoomError");
	}

	/**
	 * 加入房间
	 *
	 * @param roomCheckId
	 * @param user
	 * @return 2017年6月30日
	 */
	public void joinDouniuroom(String roomCheckId, User user) {
		run(() -> {
			if (frameThread != Thread.currentThread()) {
				throw new ServerRuntimeException("帧不同步");
			}
			
			{
				/**
				 * 检查是否再次房间
				 */
				
				DouniuRoom douniuroom = checkIdRoomMap.get(roomCheckId);
				if (douniuroom != null) {
					 System.out.print("用户ID：： "+user.getUserId()+"----");
					user.setJoinHomeGatewaySuccess(false);
					user.setJoinHomeSceneSuccess(false);
					
				//	douniuroom.addUser(pokerUserDao.get(user.getUserId()));
						joinRoom(user, douniuroom, result -> {
						user.send(new JoinDouniuRoomResult(result));
					});
						return;
				}
			}
			asyncDbService.excuete(user, () -> {
				TbPkRoomDO roomDO = roomDao.findObject(TbPkRoomDO.Table.ROOM_CHECK_ID, roomCheckId,TbPkRoomDO.Table.START, true);
				if (roomDO != null) {
					List<TbPkRoomUserDO> roomUsers = roomUserDao.find(10, TbPkRoomUserDO.Table.ROOM_ID, roomDO.getId());
					DouniuRoom room = new DouniuRoom(roomDO,roomUserDao);
					if (roomUsers != null) {
						for (int i = 0; i < roomUsers.size(); i++) {
							int userId = roomUsers.get(i).getUserId();
							try {
								if (userId > -1 && roomUsers.get(i).getLocationIndex() > -1) {
									UserDO userDO = userDao.get(userId);
									if (userDO != null) {
										room.addUser(userDO);
									}
								}
							} catch (Exception e) {
								throw new ServerRuntimeException(e);
							}
						}
					}
					run(() -> {
						joinRoom(user, room, result -> {
							user.send(new JoinDouniuRoomResult(result));
						});
					});
				} else {
					sendErrorRoomCheckId(user);
					sendJoinRoomRet(user, false);
				}
				
			});
		});

	}

	private void sendErrorRoomCheckId(User user) {
		user.noticeError("room.errorRoomCheckId");
	}

	// 加入房间结果
	private void sendJoinRoomRet(User user, Boolean result) {
		user.send(new JoinDouniuRoomResult(result));

	}

	/**
	 * 加入房间
	 *
	 * @param user
	 * @param Douniuroom
	 * @param callback
	 * @return 2017年6月30日
	 */
	private void joinRoom(User user, DouniuRoom douniuroom, Consumer<Boolean> callback) {
		if (frameThread != Thread.currentThread()) {
			throw new ServerRuntimeException("只能在线程中调用");
		}
		initRoomData(user, douniuroom);
		  boolean isControl = false;
		// **声明此时还没有同步到gateWay和scene
		user.setJoinHomeGatewaySuccess(false);
		user.setJoinHomeSceneSuccess(false);
	    TbPkRoomDO roomDo  =douniuroom.getTbPkRoomDO();
	    int userId =user.getUserId();
	    int locationIndex=-1;
	    String userName=user.getUserDO().getName();
	    int userNum=6;
	    Map<Integer,UserDO> userLocationMap = douniuroom.getMap();
        if(!isControl){
	        boolean isAdd =false;
	        for (int i = 0; i < userNum; i++) {
	        	UserDO mapUser = userLocationMap.get(i);
	        	if(mapUser!=null && mapUser.getId() == userId){
	        		isAdd = true;
	        		break;
	        	}else if(mapUser==null) {
	        		locationIndex = i;
	        		douniuroom.addUser(userDao.get(userId));
	        		isAdd = true;
	        		break;
	        	}
			}
	        if(!isAdd){
	        	user.noticeError("room.full");
	            callback.accept(false);
	            return;
	        }
        }else{
        	douniuroom.setControl(userDao.get(userId));
        }
        //
        TbPkRoomUserDO roomUserDO = roomUserDao.get(userId);
        if (roomUserDO == null) {
            roomUserDO = new TbPkRoomUserDO();
        }
        roomUserDO.setUserId(userId);
        roomUserDO.setRoomId(roomDo.getId());
        roomUserDO.setLocationIndex(locationIndex);
        roomUserDO.setJoinDate(new Date());
        roomUserDao.replace(roomUserDO);
        roomDao.update(roomDo);
        user.setJoinRoomCallback(callback);
		// 同步信息到scene服务器
        joinDouniuroomMap.put(user.getUserId(), douniuroom);
		bossService.startJoinDouniuScene(user, douniuroom, roomUserDO, user.getSessionId());
		callback.accept(true);
	}

	//
	// //加入房间成功
	// private void joinRoomSuccess(User user, DouniuRoom douniuRoom,
	// TbPkRoomUserDO roomUserDo){ run(() -> {
	//
	// douniuRoom.addUser(roomUserDo.getUser());
	// joinDouniuroomMap.put(user.getUserId(), douniuRoom);
	// bossService.startJoinDouniuScene(user, douniuRoom, roomUserDo,
	// user.getSessionId()); }); }
	// //房间满了
	// private void sendRoomFull(User user) {
	// user.noticeError("room.full");
	// }

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
			// 查找多个未使用id,然后缓存
			if (idBuffer.size() > INIT_ID_BUFFER) {
				return;
			}
			for (int l = 0; l < LOOP_NUMS; l++) {
				if (!idBuffer.isEmpty()) {
					return;
				}

				List<RoomCheckIdPoolDO> roomCheckIdPoolDOs = roomCheckIdPoolDao.find(100, RoomCheckIdPoolDO.Table.STATE,
						RoomCheckIdState.NO_USE.ordinal());
				for (int i = 0; i < roomCheckIdPoolDOs.size(); i++) {
					RoomCheckIdPoolDO idPoolDO = roomCheckIdPoolDOs.get(i);
					int isUpdate = roomCheckIdPoolDao.updatePartial(
							Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.BUFFER.ordinal()),
							RoomCheckIdPoolDO.Table.ID, idPoolDO.getId(), RoomCheckIdPoolDO.Table.STATE,
							RoomCheckIdState.NO_USE.ordinal());
					if (isUpdate > 0) {
						idBuffer.offer(idPoolDO.getId());
					}
				}
				if (idBuffer.isEmpty()) {
					RoomCheckIdPoolDO item = new RoomCheckIdPoolDO();
					for (int i = 0; i < INIT_ID_NUMS;) {
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
	private TbPkRoomDO checkUserCreateRoom(User user) {
		if (frameThread == Thread.currentThread()) {
			throw new ServerRuntimeException("只能在指定的线程调用,不能再room 线程使用");
		}
		TbPkRoomDO room = roomDao.findObject(TbPkRoomDO.Table.CREATE_USER_ID, user.getUserId(), TbPkRoomDO.Table.START,true);
		return room;
	}

	/**
	 * create_uesr映射map
	 *
	 * @param user
	 * @param TbPkRoomDO
	 * @return 2017年6月29日
	 */
	private void initRoomData(User user, TbPkRoomDO TbPkRoomDO, int level) {
		run(() -> {
			initRoomData(user, new DouniuRoom(TbPkRoomDO));
		});
	}

	private boolean initRoomData(User user, DouniuRoom room) {
		if (frameThread != Thread.currentThread()) {
			throw new ServerRuntimeException("只在指定的线程中调用");
		}
		TbPkRoomDO roomDO = room.getTbPkRoomDO();
		if (!roomMap.containsKey(roomDO.getId())) {
			checkIdRoomMap.put(roomDO.getRoomCheckId(), room);
			crateUserDouniuRoomMap.put(roomDO.getCreateUserId(), room);
			roomMap.put(roomDO.getId(), room);

			return true;
		}
		return false;
	}

	/*
	 * //加入房间成功 private void joinRoomSuccess(User user, DouniuRoom Douniuroom,
	 * TbPkRoomUserDO roomUserDo2){ run(() -> {
	 * Douniuroom.addUser(roomUserDo2.getLocationIndex(),
	 * roomUserDo2.getUser()); .put(user.getUserId(),
	 * Douniuroom); bossService.startJoinDouniuScene(user, Douniuroom,
	 * roomUserDo2, user.getSessionId()); }); }
	 */
	// 已经创建房间
	private void sendAlreadyCreateRoom(User user) {
		user.noticeError("user.AlreadyCreateRoom");
	}

	// 创建房间结果
	private void sendCreateRoomResult(User user, boolean result, String checkId) {
		user.send(new CreateDouniuRoomResult(result, checkId));

	}

	// 創建房間成功
	private void sendCreateRoomSuccess(User user) {
		user.noticeError("room.createRoomSuccess");
	}

	// 房卡不足
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
		
		user.setJoinRoomCallback(null);
		//user.send(new ReadyGame());
	}

	@Override
	protected void errorHandler(Throwable e) {
		log.error("error", e);
	}

	@Override
	protected void threadMethod(int arg0, long arg1, long arg2) {

	}

	/**
	 * 加入房间成功
	 * 
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
	 * 退出斗牛房间
	 * 
	 * @qixianghui
	 */
	// ------------------------------------------------------
	@Autowired
	private UserService userService;
	@Autowired
	private TbPkRoomDao douniuRoomDao;
	@Autowired
	private TbPkRoomUserDao douniuRoomUserDao;
/*	@Autowired
	private TbPkRoomDO userLinkRoomDao;*/

	/**
	 * 
	 * 先鿿出场景服务器，如果成功在逿出网兿
	 */

	public void exitDouniuRoom(ExitDouniuRoom msg, User user) {
		asyncDbService.excuete(user, () -> {
	    int userId = user.getUserId();
	    int roomId = Integer.parseInt(msg.getRoomId());
	   
	 /* TbPkRoomUserDO roomUserDO = roomUserDao.findObject(TbPkRoomUserDO.Table.ROOM_ID,roomId,
	            		TbPkRoomUserDO.Table.USER_ID,userId);*/
	    TbPkRoomUserDO roomUserDO = roomUserDao.findObject(TbPkRoomUserDO.Table.USER_ID,userId); 
			if (roomUserDO != null) {
				TbPkRoomDO room = douniuRoomDao.get(roomUserDO.getRoomId());				
				if (room != null) {
					run(() -> {
						bossService.startExitRoomScene(user, room);
					});
			
			} else {
				sendAlreadyExitRoom(user);
				sendExitRoomRet(user);
			}  
			}else {
                sendAlreadyExitRoom(user);
                sendExitRoomRet(user);
            }
		});
	}

	public void exitDouniuRoomSceneSuccess(int userId, int sceneId, boolean result) {
		userService.handlerUser(userId, user -> {
			run(() -> {
				if (result) {
					bossService.douniuStartExitRoomGateway(user, sceneId);
				} else {
					sendCannotExitRoom(user);
				}
			});
		});
	}

	public void exitDouniuRoomGatewaySuccess(User user) {
		run(() -> {
			exitDouniuRoomSuccess(user);
		});
	}

	public void chapterStart(DouniuChapterStartMsg msg) {
//		run(() -> {
			DouniuRoom douNiuRoom = roomMap.get(msg.getRoomId());
			if (douNiuRoom != null) {
				douNiuRoom.setStart(true);
				TbPkChapterDO chapterDO = new TbPkChapterDO();
                chapterDO.setNum(msg.getNum());
                chapterDO.setRoomId(msg.getRoomId());
                chapterDO.setStartDate(new Date());
                chapterDao.insert(chapterDO);
			}
//		});
	}

 /**
  * 斗牛一局结束
  * @param msg
  */
	public void chapterEnd(DouniuChapterEndMsg msg) {
		run(() -> {	
			System.out.println("当前局数：：：："+ msg.getNum());
			TbPkChapterDO chapterDO = chapterDao.findObject(TbPkChapterDO.Table.NUM, msg.getNum(),  //这有问题null
					TbPkChapterDO.Table.ROOM_ID, msg.getRoomId());
			DouniuRoom room = roomMap.get(chapterDO.getRoomId());
			chapterDO.setEndDate(new Date());
			List<TbPkChapterUserDO> userChapterDOs = new ArrayList<TbPkChapterUserDO>();
			List<ChapterUserMsg> chapterUserMsgs = msg.getChapterUserMsgs();
			for (int i = 0; i < chapterUserMsgs.size(); i++) {
				ChapterUserMsg userMsg = chapterUserMsgs.get(i);
				TbPkChapterUserDO userChapterDO = new TbPkChapterUserDO();
				userChapterDO.setChapterId(chapterDO.getId());
				userChapterDO.setPais(DouniuUtils.ConvertString(userMsg.getPais()));
				userChapterDO.setScore(userMsg.getScore());
				userChapterDO.setUserId(userMsg.getUserId());
				userChapterDOs.add(userChapterDO);
				room.addScore(userChapterDO.getUserId(), userMsg.getScore());
			}
			asyncDbService.excuete(() -> {
				chapterDao.update(chapterDO);
				chapterUserDao.insert(userChapterDOs);
			});
		});
	}

	@SuppressWarnings("unused")
	private void exitDouniuRoomSuccess(User user) {
		if (frameThread != Thread.currentThread()) {
			throw new ServerRuntimeException("只能在指定的线程调用");
		}

		int userId = user.getUserId();

		DouniuRoom douNiuRoom = joinDouniuroomMap.remove(user.getUserId());

		if (douNiuRoom != null) {
			TbPkRoomDO roomDO = douNiuRoom.getTbPkRoomDO();
			TbPkRoomUserDO roomUserDO = roomUserDao.findObject(TbPkRoomUserDO.Table.ROOM_ID, roomDO.getId(),
					TbPkRoomUserDO.Table.USER_ID, user.getUserId());
			int score = douNiuRoom.getScoreByUserId(userId);
			douNiuRoom.removeUser(userId);
			roomUserDO.setLocationIndex(-1);
			roomUserDO.setEndScore(score);

			asyncDbService.excuete(user, () -> {
				roomDO.setVersion(roomDO.getVersion() + 1);
				roomUserDao.update(roomUserDO);
				sendExitRoomRet(user);
			});

		} else {
			sendAlreadyExitRoom(user);
			sendExitRoomRet(user);
		}
	}

	private void sendCannotExitRoom(User user) {
		user.noticeError("room.cannotExitRoom");
	}

	private void sendAlreadyExitRoom(User user) {
		user.noticeError("room.alreadyExitRoom");
	}

	private void sendExitRoomRet(User user) {
		user.send(new ExitDouniuRoomResult(user.getUserId()));
	}

	// --------------------------------------------------------------------------------------------------
	/**
	 * 删除房间
	 *
	 */
	public void delRoom(int crateUserId,User user, boolean isEnd) {
		asyncDbService.excuete(crateUserId, () -> {
          System.out.println("删除房间的ID：：："+crateUserId);
			TbPkRoomDO tbpoRoom = roomDao.findObject(TbPkRoomDO.Table.CREATE_USER_ID, crateUserId,
					TbPkRoomDO.Table.START, true);
			if (tbpoRoom != null) {
				run(() -> {
					bossService.startDouniuDelRoomScene(crateUserId, tbpoRoom, isEnd);
				});
			} else {
				log.error("关闭房间错误crateUserId:{}", crateUserId);
				if (user != null && !isEnd) {
					sendAlreadyDelRoom(user);
					sendDelRoomRet(user);
				}
			}
		});
	
		 
	}

	private void sendDelRoomRet(User user) {
		user.noticeError("room.alreadyDelRoom");
	}

	private void sendAlreadyDelRoom(User user) {
		user.send(new DeDouniuRoomResult(true));
	}

	/**
	 * 删除房间成功
	 * 
	 * @param delMsg
	 */
	public void delRoomSceneSuccess(CheckDelDouniuRoomMsg delMsg) {
		userService.handlerUser(delMsg.getUserId(), user -> {
			run(() -> {
				if (delMsg.isResult()) {
					bossService.startDouniuDelRoomGateway(delMsg.getInfos());
//					delRoomSuccess(delMsg.getUserId(), user, delMsg.isEnd());
					delRoomSuccess(delMsg.getRoomId());
					sendAlreadyDelRoom(user);  //删除房间成功
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
		user.send(new DeDouniuRoomResult(false));
		user.noticeError("room.cannotDelRoom");
	}

	public void delRoomGatewaySuccess(User user) {
		   run(() -> {
//	            delRoomSuccess(user);
	        });
	}

	private void delRoomSuccess(int roomId) {
		if (frameThread != Thread.currentThread()) {
			throw new ServerRuntimeException("只在指定的线程调用");
		}
		
		asyncDbService.excuete(() -> {
			List<TbPkRoomUserDO> roomUserDOs = roomUserDao.find(10, TbPkRoomUserDO.Table.ROOM_ID, roomId);
			if (roomUserDOs != null) {
				TbPkRoomDO roomDO = roomDao.get(roomId);
				// 扣除房主房卡
				
				DouniuRoom douniuRoom = roomMap.get(roomDO.getId());
				if (douniuRoom != null || douniuRoom.isStart()) {
					userService.minusGoldDouniu(roomDO.getCreateUserId());
				}
				if (roomDO != null) {
					run(() -> {
						crateUserDouniuRoomMap.remove(roomDO.getCreateUserId());
						checkIdRoomMap.remove(roomDO.getRoomCheckId());
						for (int i = 0; i < roomUserDOs.size(); i++) {
							joinDouniuroomMap.remove(roomUserDOs.get(i).getId());
						}
						DouniuRoom room = roomMap.remove(roomDO.getId());
						/**
						 * 更新roomUser表
						 */
						room = null;
						asyncDbService.excuete(() -> {
							// 閲婃斁checkId
							roomCheckIdPoolDao.updatePartial(
									Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE,
											RoomCheckIdState.NO_USE.ordinal()),
									RoomCheckIdPoolDO.Table.ID, roomDO.getRoomCheckId(), RoomCheckIdPoolDO.Table.STATE,
									RoomCheckIdState.BUFFER.ordinal());
						});
					});
				}
			}
		});

	}
  /**
   * 总战绩保存
   * @param roomMsg
   */
	public void balanceRoom(DouniuRecordRoomMsg msg) {
    	run(() -> {
    		List<DouniuRecordUserRoomMsg> userRoomMsgs = msg.getUserRoomMsgs();
    		
    		for(int i = 0;i < userRoomMsgs.size();i++){
    			DouniuRecordUserRoomMsg userRoomMsg = userRoomMsgs.get(i);
    			TbPkRoomUserDO roomUserDO = roomUserDao.findObject(TbPkRoomUserDO.Table.ROOM_ID,userRoomMsg.getRoomId(),
    					TbPkRoomUserDO.Table.USER_ID,userRoomMsg.getUserId());
    			
    			roomUserDO.setEndScore(userRoomMsg.getEndScore());
    			roomUserDO.setWinCount(userRoomMsg.getWinCount());
    			asyncDbService.excuete(()->{
    				roomUserDao.update(roomUserDO);
    			});	
    		} 
    		asyncDbService.excuete(()->{
    			TbPkRoomDO roomDO = roomDao.get(msg.getRoomId());
    			roomDO.setEndDate(new Date());
    			roomDO.setEnd(true);
    			roomDO.setStart(false);
    			roomDao.update(roomDO);
    		});
    		
    	});
    }
  
	/**
	 * 斗牛战绩查询历史
	 * @param user
	 */
    public void requestHistory(User user) {
        asyncDbService.excuete(user, () -> {
        	 List<TbPkRoomUserDO> list = roomUserDao.find(20, TbPkRoomUserDO.Table.USER_ID, user.getUserId());  
             ArrayList<DouniuRoomHistory> collect = list.stream().map(r -> {
               List<TbPkRoomUserDO> roomUser = roomUserDao.find(5,TbPkRoomUserDO.Table.ROOM_ID, r.getRoomId());
               TbPkRoomDO room = roomDao.get(r.getRoomId());
               DouniuRoomHistory h = new DouniuRoomHistory();
                 h.setChapterNums(room.getChapterNums());
                 h.setRoomCheckId(room.getRoomCheckId());
                 h.setStartDate(DouniuUtils.format(room.getStartDate()));
                 String[] names = new String[roomUser.size()];
                 int[] scores = new int[roomUser.size()];
                 for (int i = 0; i < names.length; i++) {
           names[i] = userDao.get(r.getUserId()).getName();
           if(r.getEndScore()!=null){
             scores[i] = r.getEndScore();
           }else{
             scores[i] = 0;
           }
         }
                 h.setUserNames(names);
                 h.setScores(scores);
                 return h;
             }).collect(Collectors.toCollection(ArrayList::new));

             DouniuRoomHistoryListRet msg = new DouniuRoomHistoryListRet();
             msg.setList(collect);
             user.send(msg);  
        });
    }
	
//	@SuppressWarnings("unused")
//	private void delRoomSuccess(final int userId, User user, boolean end) {
//		if (frameThread != Thread.currentThread()) {
//			throw new ServerRuntimeException("只在指定的线程调用");
//		}
//		asyncDbService.excuete(userId, () -> {
//			UserDO userDO = new UserDO();
//			userDO.setId(userId);
//			TbPkRoomUserDO roomUser = roomUserDao.getByUserAndRoom(userDO, null);
//			List<TbPkRoomUserDO> listByUserAndRoom = roomUserDao.getListByUserAndRoom(null, roomUser.getRoom());
//			if (roomUser != null) {
//				TbPkRoomDO tbPkRoomDO = roomDao.get(roomUser.getRoom().getId());
//				if (tbPkRoomDO != null) {
//					run(() -> {
//						tbPkRoomDO.setEndDate(new Date());
//						crateUserDouniuRoomMap.remove(userId);
//						roomMap.remove(tbPkRoomDO.getId());
//						checkIdRoomMap.remove(tbPkRoomDO.getRoomCheckId());
//						for (int i = 0; i < listByUserAndRoom.size(); i++) {
//							joinDouniuroomMap.remove(listByUserAndRoom.get(i).getUser().getId());
//						}
//						// 扣除房主房卡
//						DouniuRoom douniuRoom = roomMap.get(tbPkRoomDO.getId());
//						if (douniuRoom != null || douniuRoom.isStart()) {
//							userService.minusGold(tbPkRoomDO.getCreateUserId(), tbPkRoomDO.getPokerConfig());
//						}
//
//						asyncDbService.excuete(userId, () -> {
//							tbPkRoomDO.setStart(false);
//							tbPkRoomDO.setEnd(end);
//							tbPkRoomDO.setVersion(tbPkRoomDO.getVersion() + 1);
//							roomDao.update(tbPkRoomDO);
//							// 插入记录
//							List<UserLinkRoomDO> userLinkRoomDOList = new ArrayList<>();
//							for (int i = 0; i < listByUserAndRoom.size(); i++) {
//								try {
//									if (listByUserAndRoom.get(i).getUser().getId() > 0) {
//										roomUserDao.revome(listByUserAndRoom.get(i).getRoom().getId());
//									}
//									if (tbPkRoomDO.getChapterNums() > 0) {
//										UserLinkRoomDO link = new UserLinkRoomDO();
//										link.setUserId(listByUserAndRoom.get(i).getUser().getId());
//										link.setRoomId(listByUserAndRoom.get(i).getRoom().getId());
//										BeanUtils.copyProperties(tbPkRoomDO, link, "key");
//										userLinkRoomDOList.add(link);
//									}
//								} catch (Exception e) {
//								}
//							}
//							if (userLinkRoomDOList.size() > 0) {
//								userLinkRoomDao.insert(userLinkRoomDOList);
//							}
//							// 释放checkId
//							roomCheckIdPoolDao.updatePartial(
//									Collections.singletonMap(RoomCheckIdPoolDO.Table.STATE,
//											RoomCheckIdState.NO_USE.ordinal()),
//									RoomCheckIdPoolDO.Table.ID, tbPkRoomDO.getRoomCheckId(),
//									RoomCheckIdPoolDO.Table.STATE, RoomCheckIdState.BUFFER.ordinal());
//							if (user != null) {
//								sendDelRoomRet(user);
//							}
//						});
//					});
//				} else {
//					if (user != null && !end) {
//						sendAlreadyDelRoom(user);
//						sendDelRoomRet(user);
//					}
//				}
//			} else {
//				if (user != null && !end) {
//					sendAlreadyDelRoom(user);
//					sendDelRoomRet(user);
//				}
//			}
//		});
//	}

}
