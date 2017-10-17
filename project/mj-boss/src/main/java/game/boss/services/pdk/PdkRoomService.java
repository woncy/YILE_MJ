package game.boss.services.pdk;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.isnowfox.core.thread.FrameQueueContainer;

import game.boss.ServerRuntimeException;
import game.boss.dao.dao.RoomCheckIdPoolDao;
import game.boss.dao.dao.RoomUserDao;
import game.boss.dao.entity.RoomCheckIdPoolDO;
import game.boss.dao.entity.RoomDO;
import game.boss.dao.entity.UserDO;
import game.boss.model.PdkRoom;
import game.boss.model.Room;
import game.boss.model.User;
import game.boss.net.BossService;
import game.boss.poker.dao.PokerUserDao;
import game.boss.poker.dao.TbPkRoomDao;
import game.boss.poker.dao.TbPkRoomUserDao;
import game.boss.poker.dao.entiy.TbPkRoomDO;
import game.boss.poker.dao.entiy.TbPkRoomUserDO;
import game.boss.poker.dao.entiy.UserDO2;
import game.boss.services.AsyncDbService;
import game.boss.services.BaseService;
import game.boss.type.RoomCheckIdState;
import mj.data.Config;
import mj.data.PdkConfig;
import mj.data.Version;
import mj.net.message.login.JoinRoomRet;
import mj.net.message.room.pdk.CreatePdkRoom;
import mj.net.message.room.pdk.CreatePdkRoomRet;
import mj.net.message.room.pdk.JoinPdkRoomRet;
/**
 * 
	* @ClassName: PdkRoomService
	* @Description: 跑的快房间业务逻辑处理，主要同步映射和数据库
	* @author 13323659953@163.com
	* @date 2017年7月4日
	*
 */
@Service
public class PdkRoomService extends FrameQueueContainer implements BaseService  {
	public static final int SCENE_Id = 1002;
	
	
	public static final Logger log = Logger.getLogger(PdkRoomService.class);
	
	private HashMap<Integer, PdkRoom> createUserRoomMap = new HashMap<>();//创建者，房间映射
	
	private HashMap<Integer,PdkRoom> rooms = new HashMap<Integer, PdkRoom>();//roomId映射，所有活动着的房间
	
	private HashMap<String, PdkRoom> checkIdRoomMap = new HashMap<>(); //房间号绑定room

	private PokerUserDao pokerUserDao = new PokerUserDao();
	private TbPkRoomUserDao roomUserDao = new TbPkRoomUserDao();
	private HashMap<Integer, PdkRoom> joinUserMap = new HashMap<Integer, PdkRoom>();
	
	@Autowired
	private TbPkRoomDao roomDao;//房间持久化层
	@Autowired
	private AsyncDbService asyncDbService;//异步线程
	@Autowired
    private RoomCheckIdPoolDao roomCheckIdPoolDao;  //房间号池
	@Autowired
	private BossService bossService; 
	public PdkRoomService() {
		this(FRAME_TIME_SPAN,RUN_QUEUE_MAX);
	}
	public PdkRoomService(int frameTimeSpan, int queueMax) {
		super(frameTimeSpan, queueMax);
	}
	
	
	@PostConstruct
	private void init(){
		start();
	}
	
	/**
	 * 
	    * @Title: createRoom
	    * @Description: 创建房间
	    * @param @param msg
	    * @param @param user    参数
	    * @return void    返回类型
	    * @throws
	 */
	public void createRoom(CreatePdkRoom msg, User user) {
	        run(
	        ()->{
//	        	/**
//	        	 * 检查房卡
//	        	 */
	        	Config config = new PdkConfig(msg.getOptions());
	        	int id = user.getUserId();
	            UserDO2 user2 = pokerUserDao.get(id);
//	            	
	           
//	            /**
//	             * 检查map
//	             */
	            PdkRoom room = createUserRoomMap.get(user.getUserId());
	          
	            if(room!=null){
	            	user.noticeError("room.alreadyCreateRoom");
	            	user.send(new CreatePdkRoomRet(false, null));
	            	return;
	            } else{
	            	/**
	            	 * map为空，创建房间
	            	 */
	            	 String roomCheckId = getBufferId();
	                // int sceneId = bossService.getRandomSceneId();
	                 int sceneId = SCENE_Id;
	                 TbPkRoomDO roomDo = new TbPkRoomDO();
	                 roomDo.setConfig(config);
	                 roomDo.setCreate_user(user2);
	                 roomDo.setEnd(false);
	                 roomDo.setRoomCheckId(roomCheckId);
	                 roomDo.setSceneId(sceneId);
	                 roomDo.setStart(true);
	                 roomDo.setUserMax(config.getInt(Config.USER_NUM));
	                 roomDo.setVersion(Version.version);
	                 roomDo.setUuid(java.util.UUID.randomUUID().toString());
	                 roomDo.setStartDate(new Date());
	                 //异步同步到数据库
	                 asyncDbService.excuete(()->{
	                	 roomDao.save(roomDo);
	                 });
	                 PdkRoom pdkRoom = new PdkRoom();
                 	pdkRoom.setRoomDO(roomDo);
                 	pdkRoom.setStart(false);
                
                	
                	 log.debug("create Room  to db done....roomDo{}:"+roomDo);
                	checkIdRoomMap.put(roomCheckId, pdkRoom);
                	createUserRoomMap.put(user.getUserId(), pdkRoom);
                	rooms.put(roomDo.getId(), pdkRoom);
                	user.noticeError("room.createRoomSuccess");
                	user.send(new CreatePdkRoomRet(true,roomCheckId));
            	 
	                
	            }
	        }
	        );
	    }
	
	/**
	 * 
	    * @Title: joinRoom
	    * @Description: 用户加入房间
	    * @param @param roomCheckId
	    * @param @param user    参数
	    * @return void    返回类型
	    * @throws
	 */
	public void joinRoom(String roomCheckId,User user){
		run(()->{
			if (frameThread != Thread.currentThread()) {
                throw new ServerRuntimeException("帧不同步");
            }
			
			/**
			 * 映射中存在room,  加入用户到映射中
			 */
				{
	                PdkRoom room = checkIdRoomMap.get(roomCheckId);
	                if (room != null) {
	                    user.setJoinHomeGatewaySuccess(false);
	                    user.setJoinHomeSceneSuccess(false);
	                    room.addUser(pokerUserDao.get(user.getUserId()));;
	                    /**
	                     * 添加数据到数据库
	                     */
	                    userJoinRoomToDb(room, user,result->{
	                    	user.send(new JoinPdkRoomRet(result));
	                    });
	                    return;
	                }
			 	}     
	                asyncDbService.excuete(user, () -> {
//	                    RoomDO roomDO = roomDao.findObject(
//	                            RoomDO.Table.ROOM_CHECK_ID, roomCheckId,
//	                            RoomDO.Table.START, true
//	                    );
	                	
	                	List<TbPkRoomDO> roomDOs = roomDao.getRoomsByRoomCheckId(roomCheckId, true);
	                	if(roomDOs==null || roomDOs.size()<=0){
	                		user.noticeError("room.errorRoomCheckId");
	                		user.send(new JoinRoomRet(false));
	                	}else {
	                		TbPkRoomDO roomDO = roomDOs.get(0);
	                        PdkRoom pdkroom = new PdkRoom();
	                        pdkroom.setRoomDO(roomDO);
	                        pdkroom.addUser(pokerUserDao.get(user.getUserId()));
	                       
	                        run(() -> {
	                            userJoinRoomToDb(pdkroom, user, result -> {
	                                user.send(new JoinPdkRoomRet(result));;
	                            });
	                        });
	                		
	                    } 
	                });
	            });
	                
	}
	
	
	private void userJoinRoomToDb(PdkRoom pdkRoom,User user,Consumer<Boolean> callback){
		//**声明此时还没有同步到gateWay和scene
		user.setJoinHomeGatewaySuccess(false);
        user.setJoinHomeSceneSuccess(false);
        /**
         * 更新数据库
         */
        TbPkRoomDO room = roomDao.get(pdkRoom.getRoomDO().getId());
		UserDO2 userDo = pokerUserDao.get(user.getUserId());
		TbPkRoomUserDO room_user = new TbPkRoomUserDO();
		if(room.getCreate_user().getId()==user.getUserId()){
			room_user.setLocationIndex(0);
		}else{
			int size = room.getCreate_user().getRooms().size();
			if(size >= room.getPokerConfig().getInt(Config.USER_NUM)){
				user.noticeError("room.full");
				callback.accept(false);
	            return;
			}
			room_user.setLocationIndex(size);
		}
		room_user.setRoom(room);
		room_user.setUser(userDo);
			//更新数据到数据库中
		roomUserDao.saveOrUpdate(room_user);
		pdkRoom.addUser(userDo);
		joinUserMap.put(userDo.getId(), pdkRoom);
		//同步信息到scene服务器
//		bossService.startJoinScene(user, pdkRoom, room_user, user.getSessionId());
		
		callback.accept(true);
	
	}

	@Override
	protected void threadMethod(int arg0, long arg1, long arg3) {
		
	}
	
	/**
	 * 
	    * @Title: checkUserCreateRoom
	    * @Description: 检查数据库用户和房间的记录是否存在
	    * @param @param user
	    * @param @return    参数
	    * @return TbPkRoomDO    返回类型
	    * @throws
	 */
	private TbPkRoomDO checkUserCreateRoom(User user) {
        if (frameThread == Thread.currentThread()) {
            throw new ServerRuntimeException("帧不同步");
        }
        
        List<TbPkRoomUserDO> rooms = pokerUserDao.get(user.getUserId()).getRooms();
        
        if (rooms != null && rooms.size()>0) {
//            initRoomData(user, room);
            return rooms.get(0).getRoom();
        }
        return null;
    }

	@Override
	protected void errorHandler(Throwable arg0) {
		
	}
	
	
	
	
	private LinkedBlockingQueue<String> idBuffer = new LinkedBlockingQueue<>();

    private String getBufferId() {
        if (frameThread != Thread.currentThread()) {
            throw new ServerRuntimeException("帧不同步");
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
	
	
}
