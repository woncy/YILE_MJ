package game.scene.douniu.room;

import game.scene.douniu.room.poker.PokerChapter;

import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;

import Douniu.data.DouniuConfig;

import com.github.davidmoten.grumpy.core.Position;

import mj.data.UserPaiInfo;
import mj.net.message.game.douniu.DouniuGameRoomInfo;
import mj.net.message.game.douniu.DouniuGameUserInfo;
import mj.net.message.game.douniu.DouniuVoteDelSelectRet;

/**
 * 
    * @ClassName: RoomInfo
    * @Description: 房间信息
    * @author 13323659953@163.com
    * @date 2017年6月28日
    *
 */
public class DouniuRoomInfo {
    private short sceneId;
    
    private int roomId; //房间Id
  
    private int userNum; // 当前用户数量
    private int userMaxNum = 6; //此房间用户最大数量
    private boolean start;
    private boolean chapterStart;
    //牌局UUid
    private String uuid;
    /**
     * 创建用户id
     */
    private int createUserId;
    /**
    * 房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同
    */
    private String roomCheckId;
    private int currentChapterNum; //当期局数  
    private boolean[] votedel; //投票数

    private final DouniuSceneUser[] users;
    protected final PokerChapter chapter;

    public DouniuRoomInfo(DouniuRoom room,String rulesName, boolean isQiangZhuang , int userNum) {
    	chapter =new PokerChapter(room, rulesName, isQiangZhuang, userNum);
        users =  new DouniuSceneUser[userNum];
        votedel = new boolean[userNum];
        currentChapterNum = 0;
    }
    
    /**
     * 斗牛创建房间同步消息
     * @param myLocationIndex
     * @return
     */
    public DouniuGameRoomInfo toMessage(int myLocationIndex) {
    	DouniuGameRoomInfo g = new DouniuGameRoomInfo();
         
		   g.setStart(start);
           g.setRoomCheckId(roomCheckId);
		   g.setCreateUserId(createUserId);
           g.setLeftChapterNums(chapter.getChapterNums());
           for (DouniuSceneUser u : users) {
        	   if (u != null) {
                  DouniuGameUserInfo sceneUser = u.toMessage();
                  excuteDistanceKm(sceneUser, u);
                  g.addSceneUser(sceneUser);
            }
         }
         if (isStart()) {
        //   if (chapter.isStart()) {
                g.setChapter(chapter.toMessage(myLocationIndex));
        //     }
         }
         return g;
       
    }

    
    /**
     * 
        * @Title: excuteDistanceKm
        * @Description: 计算用户之间的距离
        * @param @param msg
        * @param @param curUser    参数
        * @return void    返回类型
        * @throws
     */
	public void excuteDistanceKm(DouniuGameUserInfo sceneUser, DouniuSceneUser curUser) {
        if (curUser.getLongitude() == 0 || curUser.getLatitude() == 0) {
            return;
        }
        for (int i = 0; i < users.length; i++) {
            DouniuSceneUser u = users[i];
            if (u != null && u.getLocationIndex() != sceneUser.getLocationIndex()) {
                String distance = "";
                if (u.getLongitude() != 0 && u.getLatitude() != 0) {
                    distance = String.format(
                            "%.3f公里",
                            distanceKm(curUser.getLatitude(), curUser.getLongitude(), u.getLatitude(), u.getLongitude())
                    );
                } else {
                    distance = "位置未知";
                }
                Method writeMethod = BeanUtils.getPropertyDescriptor(DouniuGameUserInfo.class, "user" + i + "Distance").getWriteMethod();

                try {
                    writeMethod.invoke(sceneUser, distance);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * @return 返回距离，单位千米！
     */
    public static double distanceKm(double latitude0, double longitude0, double latitude1, double longitude1) {
        return new Position(latitude0, longitude0).getDistanceToKm(new Position(latitude1, longitude1));
    }

    public void updateUserInfo(DouniuSceneUser sceneUser) {
    	 DouniuSceneUser oldUser = users[sceneUser.getLocationIndex()];
         if (oldUser == null) {
             users[sceneUser.getLocationIndex()] = sceneUser;
         } else {
             oldUser.setAvatar(sceneUser.getAvatar());
             oldUser.setLocationIndex(sceneUser.getLocationIndex());
             oldUser.setSex(sceneUser.getSex());
             oldUser.setUserId(sceneUser.getUserId());
         }
         chapter.updateUser(sceneUser);
    }

    public DouniuSceneUser getUserInfo(int locationIndex) {
    	return users[locationIndex];
    }


    public boolean removeUserInfo(int userId) {
    	for (int i = 0; i < users.length; i++) {
            DouniuSceneUser u = users[i];
            if (u != null && u.getUserId() == userId) {
                users[i] = null;
                return true;
            }
        }
		return false;
    }

    public DouniuSceneUser[] getUsers() {
        return users;
    }
    public void changeScore(UserPaiInfo[] userPaiInfos) {
       
    }

    public short getSceneId() {
        return sceneId;
    }

    public void setSceneId(short sceneId) {
        this.sceneId = sceneId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * 当前局数加加
     * @return
     */
    
    public void addChapterNum(){
    	currentChapterNum++;
    }
    public int getCurrentChapterNum() {
		return currentChapterNum;
	}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getRoomCheckId() {
        return roomCheckId;
    }

    public void setRoomCheckId(String roomCheckId) {
        this.roomCheckId = roomCheckId;
    }
    public void setStart(boolean start) {
    	this.start = start;
    }

   

    public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public void setChapterStart(boolean chapterStart) {
		this.chapterStart = chapterStart;
	}

	public PokerChapter getChapter() {
		return chapter;
	}

	@Override
    public String toString() {
        return "DouniuRoomInfo{" +
                ", sceneId=" + sceneId +
                ", roomId=" + roomId +
                ", uuid='" + uuid + '\'' +
                ", roomCheckId='" + roomCheckId + '\'' +
                '}';
    }
	
	/*
	 * 现在咱们是二个人或者大于两个人的时候就可以开始。
	 * 必须得是六个人才能开始。
	 */
    public boolean isFull() {
    	int totalNum = 0 ;
        for (int i = 0; i < users.length; i++) {
            DouniuSceneUser u = users[i];
            if (u == null || !u.isJoinGame()) {
                continue;
            }else{
            	totalNum ++; 
            }
        }
        boolean flage = false;
        if(totalNum >= 2){   //测试发牌先判断一个人
        	flage = true;
        }
        return flage;
    }

    public DouniuSceneUser getUserInfoByUserId(int userId) {
        for (int i = 0; i < users.length; i++) {
            DouniuSceneUser u = users[i];
            if (u != null && u.getUserId() == userId) {
                return u;
            }
        }
        return null;
    }
    /**
     * 
        * @Title: isStart
        * @Description: 房间里游戏是否是开始状态
        * @param @return    参数
        * @return boolean    返回类型
        * @throws
     */
	public boolean isStart() {
	
		return false;
	}

	public boolean isEnd() {
		return false;
	}
/**
 * 开始游戏
 * @return
 */
	public boolean isChapterStart() {
		 return chapterStart;
	}

	/**
	 * 判断当前玩家是否在线
	 * @return
	 */
public int getCurrentOnlineUser() {
	int onlineNum = 0;
	
	for (int i = 0; i < users.length; i++) {
		if(users[i]!=null && users[i].isStart()){
			onlineNum++;
		}
	}
	return onlineNum;
}

	public int votedel(DouniuVoteDelSelectRet msg, DouniuSceneUser user) {
		int count = 0;
		if(msg.getResult()){
			
			for (int i = 0; i < votedel.length; i++) {
				if(i==user.getLocationIndex())
					votedel[i] = true;
				
				if(votedel[i]){
					count++;
				}
			}
			
		}
		return count;
	}




}
