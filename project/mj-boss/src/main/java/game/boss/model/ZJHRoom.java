package game.boss.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import game.boss.dao.entity.UserDO;
import game.boss.poker.dao.TbPkRoomDao;
import game.boss.poker.dao.TbPkRoomUserDao;
import game.boss.poker.dao.entiy.TbPkRoomDO;
import game.boss.poker.dao.entiy.TbPkRoomUserDO;
import game.boss.poker.dao.entiy.UserDO2;
import game.zjh.scene.msg.CheckJoinZJHRoomMsg;
import game.zjh.scene.msg.ZJHUserInfo;

public class ZJHRoom {
	private TbPkRoomDO TbPkRoomDO;
	private Map<Integer, UserDO2> map = new HashMap<>();
	private boolean start;
	
	 public ZJHRoom(TbPkRoomDO TbPkRoomDO) {
	        this.TbPkRoomDO = TbPkRoomDO;
	    }
	    public void removeUser(int userId) {
	        map.entrySet().removeIf(entry -> entry.getValue().getId() == userId);
	    }

	    public TbPkRoomDO getTbPkRoomDO() {
	        return TbPkRoomDO;
	    }
	    public void setTbPkRoomDO(TbPkRoomDO TbPkRoomDO) {
	        this.TbPkRoomDO = TbPkRoomDO;
	    }

	    public void addUser(int location, UserDO2 user) {
	        this.map.put(location, user);
	    }

	    public Map<Integer, UserDO2> getMap() {
	        return map;
	    }
	    
	    public CheckJoinZJHRoomMsg toMsg(User user){
	        final CheckJoinZJHRoomMsg m = new CheckJoinZJHRoomMsg();

//	        Map<Integer,Integer> map2 = new HashMap<Integer,Integer>();
	        int id = TbPkRoomDO.getId();
	        TbPkRoomDao dao = new TbPkRoomDao();
	        TbPkRoomDO tbPkRoomDO= dao.get(id);
	        int initScore = tbPkRoomDO.getInitScore();
//	        List<TbPkRoomUserDO> room_users = tbPkRoomDO.getCreate_user().getRooms();
//	        for (int i = 0; i < room_users.size(); i++) {
//				map2.put(room_users.get(i).getUser().getId(), room_users.get(i).getInitScore());
//			}
//	        m.setUSMap(map2);
	        m.setUuid(tbPkRoomDO.getUuid());
	        m.setUserMax(tbPkRoomDO.getUserMax());
	        m.setCreateUserId(tbPkRoomDO.getUser().getId());
	        m.setRoomCheckId(tbPkRoomDO.getRoomCheckId());
	        m.setRoomId(tbPkRoomDO.getId());
	        m.setJoinGatewayId(user.getGatewayId());
	        m.setJoinSessionId(user.getSessionId());
	        m.setJoinUserId(user.getUserId());
	        m.setOptions(tbPkRoomDO.getPokerConfig().getOptions());
	        map.entrySet().stream().map(e -> {
	        	UserDO userDO2 = user.getUserDO();
	            //UserDO2 userDO = e.getValue();
	            ZJHUserInfo userInfo = new ZJHUserInfo();
	            userInfo.setUserId(userDO2.getId());
	            userInfo.setLocationIndex(e.getKey());
//	            userInfo.setSessionId(sessionId);
//	            userInfo.setGatewayId((short) user.getGatewayId());
	            userInfo.setIp(userDO2.getIp());
	            userInfo.setLongitude(userDO2.getLongitude());
	            userInfo.setLatitude(userDO2.getLatitude());
	            userInfo.setScore(initScore);
	            userInfo.setUserName(userDO2.getName());
	            userInfo.setAvatar(userDO2.getAvatar());
	            userInfo.setSex(userDO2.getSex());
	            userInfo.setGold(userDO2.getGold());
	            return userInfo;
	        }).collect(Collectors.toCollection(m::getZJHuserInfos));
	        return m;
	    }
	    @Override
	    public String toString() {
	        return "ZJHRoom{" +
	                "ZJHroomDO=" + TbPkRoomDO +
	                '}';
	    }

	    public void setStart(boolean start) {
	        this.start = start;
	    }

	    public boolean isStart() {
	        return start;
	    }

}
