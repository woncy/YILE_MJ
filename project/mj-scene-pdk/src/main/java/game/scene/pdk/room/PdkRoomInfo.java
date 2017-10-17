package game.scene.pdk.room;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.github.davidmoten.grumpy.core.Position;

import game.scene.pdk.room.poker.PokerChapter;
import mj.data.UserPaiInfo;
import mj.net.message.game.GameRoomInfo;
import mj.net.message.game.GameUserInfo;
import mj.net.message.game.pdk.PdkGameUserInfo;

/**
 * 
    * @ClassName: RoomInfo
    * @Description: 房间信息
    * @author 13323659953@163.com
    * @date 2017年6月28日
    *
 */
public class PdkRoomInfo {
    private short sceneId;
    
    private int roomId; //房间Id
  
    private int crrentUserNum; // 当前用户数量
    private int userMaxNum; //此房间用户最大数量
    private int currentChapterNum;//当前局数
    private int create_user_id;
    
    private String uuid;

    private String roomCheckId;

    private final PdkSceneUser[] users;
    private PokerChapter chapter;


    public PdkRoomInfo(PdkRoom room,int userNum,int paiNum) {
    	chapter = new PokerChapter(userNum,paiNum);
        users =  new PdkSceneUser[userNum];
        for (int i = 0; i < users.length; i++) {
        	users[i] = new PdkSceneUser() ;
			
		}
    }
    
    public int getCreate_user_id() {
		return create_user_id;
	}



	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}



	public int getCrrentUserNum() {
		return crrentUserNum;
	}

	public void setCrrentUserNum(int crrentUserNum) {
		this.crrentUserNum = crrentUserNum;
	}

	public int getUserMaxNum() {
		return userMaxNum;
	}

	public void setUserMaxNum(int userMaxNum) {
		this.userMaxNum = userMaxNum;
	}

	public int getCurrentChapterNum() {
		return currentChapterNum;
	}

	public void setCurrentChapterNum(int currentChapterNum) {
		this.currentChapterNum = currentChapterNum;
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
	public void excuteDistanceKm(GameUserInfo msg, PdkSceneUser curUser) {
        if (curUser.getLongitude() == 0 || curUser.getLatitude() == 0) {
            return;
        }
        for (int i = 0; i < users.length; i++) {
            PdkSceneUser u = users[i];
            if (u != null && u.getLocationIndex() != msg.getLocationIndex()) {
                String distance = "";
                if (u.getLongitude() != 0 && u.getLatitude() != 0) {
                    distance = String.format(
                            "%.3f公里",
                            distanceKm(curUser.getLatitude(), curUser.getLongitude(), u.getLatitude(), u.getLongitude())
                    );
                } else {
                    distance = "位置未知";
                }
                Method writeMethod = BeanUtils.getPropertyDescriptor(GameUserInfo.class, "user" + i + "Distance").getWriteMethod();

                try {
                    writeMethod.invoke(msg, distance);
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

    public void updateUserInfo(PdkSceneUser sceneUser) {
    	int userIndex = sceneUser.getLocationIndex();
    	PdkSceneUser user = users[userIndex];
    	if(user==null){
    		users[userIndex] = sceneUser;
    	}else{
    		if(users[userIndex].getUserId()!=sceneUser.getUserId()){
    			users[userIndex] = sceneUser;
    		}
    	}
    }

    public PdkSceneUser getUserInfo(int locationIndex) {
		return users[locationIndex];
    }


    public boolean removeUserInfo(int userId) {
    	for(int i=0;i<users.length;i++){
    		if(users[i].getUserId()==userId){
    			users[i]=null;
    			return true;
    		}
    	}
		return false;
    }

    public PdkSceneUser[] getUsers() {
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

    public PokerChapter getChapter() {
		return chapter;
	}

	@Override
    public String toString() {
        return "RoomInfo{" +
                ", sceneId=" + sceneId +
                ", roomId=" + roomId +
                ", uuid='" + uuid + '\'' +
                ", roomCheckId='" + roomCheckId + '\'' +
                '}';
    }

    public boolean isFull() {
        for (int i = 0; i < users.length; i++) {
            PdkSceneUser u = users[i];
            if (u == null || !u.isJoinGame()) {
                return false;
            }
        }
        return true;
    }

    public PdkSceneUser getUserInfoByUserId(int userId) {
        for (int i = 0; i < users.length; i++) {
            PdkSceneUser u = users[i];
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

	public List<PdkGameUserInfo> getUserInfosMsg() {
		List<PdkGameUserInfo> users = new ArrayList<PdkGameUserInfo>();
		for (int i = 0; i < this.users.length; i++) {
			PdkSceneUser pdkSceneUser = this.users[i];
			users.add(pdkSceneUser.toMessage());
		}
		return users;
	}

}
