package game.boss.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import game.boss.dao.dao.UserDao;
import game.boss.dao.entity.UserDO;
import game.boss.poker.dao.PokerUserDao;
import game.boss.poker.dao.TbPkRoomDao;
import game.boss.poker.dao.TbPkRoomUserDao;
import game.boss.poker.dao.entiy.TbPkRoomDO;
import game.boss.poker.dao.entiy.TbPkRoomUserDO;
import game.boss.poker.dao.entiy.UserDO2;
import game.scene.msg.CheckJoinRoomMsg;
import game.scene.msg.UserInfo;
/**
 * 
    * @ClassName: PdkRoom
    * @Description: 房间简单实体
    * @author 13323659953@163.com
    * @date 2017年6月30日
    *
 */
public class PdkRoom {
	private TbPkRoomDO roomDO;
	private Map<Integer, UserDO2> map = new HashMap<>();
    private boolean start;
    private int location = -1;
    public void addUser(UserDO2 user) {
        this.map.put(++location, user);
    }
    
    public void removeUser(int userId) {
        map.entrySet().removeIf(entry -> entry.getValue().getId() == userId);
    }
    
	public TbPkRoomDO getRoomDO() {
		return roomDO;
	}
	public void setRoomDO(TbPkRoomDO roomDO) {
		this.roomDO = roomDO;
	}
	public Map<Integer, UserDO2> getMap() {
		return map;
	}
	public void setMap(Map<Integer, UserDO2> map) {
		this.map = map;
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	
	public CheckJoinRoomMsg toMsg(User user){

        final CheckJoinRoomMsg m = new CheckJoinRoomMsg();
        TbPkRoomDO roomDO = new TbPkRoomDao().get(this.roomDO.getId());
        m.setUuid(roomDO.getUuid());
        m.setUserMax(roomDO.getUserMax());
        m.setCreateUserId(roomDO.getCreate_user().getId());
        m.setRoomCheckId(roomDO.getRoomCheckId());
        m.setRoomId(roomDO.getId());
        
        TbPkRoomUserDao roomUser =  new TbPkRoomUserDao();
        m.setUser0(0);
        m.setUser1(0);
        m.setUser2(0);
        m.setUser3(0);
        m.setScore0(0);
        m.setScore1(0);
        m.setScore2(0);
        m.setScore3(0);
        m.setJoinGatewayId(user.getGatewayId());
        m.setJoinSessionId(user.getSessionId());
        m.setJoinUserId(user.getUserId());
        
        m.setOptions(roomDO.getPokerConfig().getOptions());
        ArrayList<UserInfo> userInfos = new ArrayList<UserInfo>();
        
        Set<Map.Entry<Integer, UserDO2>> entrySet = map.entrySet();
        Iterator<Map.Entry<Integer, UserDO2>> it = entrySet.iterator();
        while(it.hasNext()){
        	Map.Entry<Integer, UserDO2> e = it.next();
            UserDO2 userDO = e.getValue();
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userDO.getId());
            userInfo.setLocationIndex(e.getKey());
            userInfo.setSessionId(user.getSessionId());
            userInfo.setGatewayId((short) user.getGatewayId());
            Integer socre = roomUser.getByUserAndRoom(userDO, roomDO).getEndScore();
            if(socre==null){
            	socre = 0;
            }
            userInfo.setScore(socre);
            userInfo.setIp(userDO.getIp());
            userInfo.setLongitude(userDO.getLongitude());
            userInfo.setLatitude(userDO.getLatitude());
            userInfo.setUserName(userDO.getName());
            userInfo.setAvatar(userDO.getAvatar());
            userInfo.setSex(userDO.getSex());
            userInfo.setGold(userDO.getGold());
            userInfos.add(userInfo);
        }
        m.setUserInfos(userInfos);
        return m;
    }
    
    
}
