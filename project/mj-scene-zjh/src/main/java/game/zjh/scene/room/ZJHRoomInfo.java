package game.zjh.scene.room;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.BeanUtils;

//import com.github.davidmoten.grumpy.core.Position;

import game.zjh.scene.room.poker.ZJHChapter;
import mj.data.UserPaiInfo;
import mj.net.message.game.GameUserInfo;
import mj.net.message.game.zjh.GameRoomInfoT;
import mj.net.message.game.zjh.GameUserinfo2;

public class ZJHRoomInfo {
	private short sceneId;
    /**
     * 牌局id
     */
    private int roomId;
    /**
     * 创建用户id
     */
    private int createUserId;
    /**
     * 用户数
     */
    private int userMax;
    /**
     * 牌局uuid
     */
    private String uuid;

    /**
     * 房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同
     */
    private String roomCheckId;
    //开始
    private boolean chapterStart;
    
    private boolean start;
    private ZJHChapter chapter;
    

    //private final ZJHSceneUser[] zjhUsers;
    
    private final ArrayList<ZJHSceneUser> zjhUsers;
    
    public ZJHRoomInfo(ZJHRoom room,String rulesName) {
    	chapter = new ZJHChapter(room, rulesName);
    	zjhUsers =  new ArrayList<ZJHSceneUser>();
    }

    public GameRoomInfoT toMessage(int myLocationIndex) {
        GameRoomInfoT g = new GameRoomInfoT();
        g.setStart(start);
        g.setRoomCheckId(roomCheckId);
        g.setChapterNums(chapter.getChapterNums());
        for (ZJHSceneUser u : zjhUsers) {
            if (u != null) {
            	GameUserinfo2 sceneUser = u.toMessage();
                excuteDistanceKm(sceneUser, u);
                g.addSceneUser(sceneUser);
            }
        }
//        if (isStart()) {
//            if (chapter.isStart()) {
//                g.setZjhChapter(chapter.toMessage(myLocationIndex));
//            }
//        }
        return g;
    }
    /**
     * 计算距离
     *@param sceneUser
     *@param curUser
     *@return
     * 2017年7月4日
     */
    public void excuteDistanceKm(GameUserinfo2 gameRoomInfoT, ZJHSceneUser curUser) {
        if (curUser.getLongitude() == 0 || curUser.getLatitude() == 0) {
            return;
        }
        for (int i = 0; i < zjhUsers.size(); i++) {
        	ZJHSceneUser u = zjhUsers.get(i);
            if (u != null && u.getLocationIndex() != gameRoomInfoT.getLocationIndex()) {
                String distance = "";
                if (u.getLongitude() != 0 && u.getLatitude() != 0) {
                    distance = String.format(
                            "%.3f公里",
                            distanceKm(curUser.getLatitude(), curUser.getLongitude(), u.getLatitude(), u.getLongitude())
                    );
                } else {
                    distance = "位置未知";
                }
                Method writeMethod = BeanUtils.getPropertyDescriptor(GameUserinfo2.class, "user" + i + "Distance").getWriteMethod();

                try {
                    writeMethod.invoke(gameRoomInfoT, distance);
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
//        return new Position(latitude0, longitude0).getDistanceToKm(new Position(latitude1, longitude1));
    	return 0;
    }
    /**
     * 修改userInfo
     *@param sceneUser
     *@return
     * 2017年7月4日
     */
    public void updateUserInfo(ZJHSceneUser sceneUser) {
        if (zjhUsers == null || zjhUsers.size() == 0) {
        	zjhUsers.add(sceneUser);
        } else {
        	ZJHSceneUser oldUser = zjhUsers.get(sceneUser.getLocationIndex());
            oldUser.setAvatar(sceneUser.getAvatar());
            oldUser.setLocationIndex(sceneUser.getLocationIndex());
            oldUser.setSex(sceneUser.getSex());
            oldUser.setUserId(sceneUser.getUserId());
        }
        chapter.updateUser(sceneUser);
    }
    
    public ZJHSceneUser getUserInfo(int locationIndex) {
        return zjhUsers.get(locationIndex);
    }
    /**
     * 删除userInfo
     *@param userId
     *@return
     *@return
     * 2017年7月4日
     */
    public boolean removeUserInfo(int userId) {
        for (int i = 0; i < zjhUsers.size(); i++) {
        	ZJHSceneUser u = zjhUsers.get(i);
            if (u != null && u.getUserId() == userId) {
            	zjhUsers.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<ZJHSceneUser> getzjhUsers() {
        return zjhUsers;
    }
    /**
     * 改变房间积分  未完成
     *@param userPaiInfos
     *@return
     * 2017年7月4日
     */
    public void changeScore(UserPaiInfo[] userPaiInfos) {
        for (int i = 0; i < zjhUsers.size(); i++) {
        	ZJHSceneUser user = zjhUsers.get(i);
            UserPaiInfo userPaiInfo = userPaiInfos[i];
            user.addScore(userPaiInfo.getScore());
           
        }
//        if (fangPaoIndex == -1) {
//            for (int i = 0; i < users.length; i++) {
//                if (i != huPaiLocationIndex) {
//                    users[i].removeScore(scoreNums);
//                }
//            }
//            users[huPaiLocationIndex].addScore(scoreNums * 3);
//        } else {
//            users[fangPaoIndex].removeScore(scoreNums);
//            users[huPaiLocationIndex].addScore(scoreNums);
//        }
    }
    
    public boolean isFull() {
        for (int i = 0; i < zjhUsers.size(); i++) {
            ZJHSceneUser u = zjhUsers.get(i);
            if (u == null || !u.isJoinGame()) {
                return false;
            }
        }
        return true;
    }
    
    public ZJHSceneUser getUserInfoByUserId(int userId) {
        for (int i = 0; i < zjhUsers.size(); i++) {
        	ZJHSceneUser u = zjhUsers.get(i);
            if (u != null && u.getUserId() == userId) {
                return u;
            }
        }
        return null;
    }
    public boolean isChapterStart() {
        return chapterStart;
    }

    public void setChapterStart(boolean chapterStart) {
        this.chapterStart = chapterStart;
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

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public int getUserMax() {
		return userMax;
	}

	public void setUserMax(int userMax) {
		this.userMax = userMax;
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

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	

	public ZJHChapter getChapter() {
		return chapter;
	}

	public void setChapter(ZJHChapter chapter) {
		this.chapter = chapter;
	}

	@Override
	public String toString() {
		return "ZJHRoomInfo [sceneId=" + sceneId + ", roomId=" + roomId + ", createUserId=" + createUserId
				+ ", userMax=" + userMax + ", uuid=" + uuid + ", roomCheckId=" + roomCheckId + ", chapterStart="
				+ chapterStart + ", start=" + start + ", chapter=" + chapter + ", zjhUsers=" + zjhUsers + "]";
	}

	
    
    
}
