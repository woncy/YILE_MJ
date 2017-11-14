package game.boss.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.boss.dao.entity.Room2DO;
import game.boss.dao.entity.UserDO;
import game.scene.msg.CheckJoinRoomMsg;
import game.scene.msg.UserInfo;

public class Room2 {
	public static enum TYPE{MJ,DN};
	private Room2DO roomDo;
	private int roomId;
	private UserDO users[] = new UserDO[5];
	private Map<Integer,Integer> gatewayIdMap = new HashMap<Integer, Integer>();
	private Map<Integer,Integer> sessionIdMap = new HashMap<Integer, Integer>();
	private boolean isStart;
	private TYPE type;
	
	public Room2(Room2DO roomDo) {
		super();
		this.roomDo = roomDo;
		if(roomDo.getSceneId()==1003){
			this.type = TYPE.DN;
		}else if(roomDo.getSceneId()==1000){
			this.type = TYPE.MJ;
		}
	}

	public int getRoomId() {
		return roomId;
	}
	


	public TYPE getType() {
		return type;
	}



	public void setType(TYPE type) {
		this.type = type;
	}



	public UserDO[] getUsers() {
		return users;
	}



	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}



	public Room2DO getRoomDo() {
		return roomDo;
	}
	
	
	public boolean isStart() {
		return isStart;
	}


	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}


	public void setUsers(UserDO[] users) {
		this.users = users;
	}


	public void setRoomDo(Room2DO roomDo) {
		this.roomDo = roomDo;
	}
	/**
	 * 
	* @Title: addUser 
	* @Description: TODO(添加用户并返回位) 
	* @param @param user
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int addUser(User user){
		gatewayIdMap.put(user.getUserId(),user.getGatewayId());
		sessionIdMap.put(user.getUserId(),(int)user.getSessionId());
		return addUser(user.getUserDO());
	}
	
	public int addUser(UserDO user){
		if(user==null){
			return -1;
		}
		for (int i = 0; i < users.length; i++) {
			UserDO u = users[i];
			if(u!=null && u.getId() == user.getId()){
				users[i] = user;
				return i;
			}
			
		}
		for (int i = 0; i < users.length; i++) {
			UserDO u = users[i];
			if(u==null){
				users[i] = user;
				return i;
			}
		}
		return -1;
	}
	
	public void removeUser(UserDO user){
		for (int i = 0; i < users.length; i++) {
			UserDO u = users[i];
			if(u!=null && user!=null && u.getId()== user.getId()){
				users[i] = null;
				gatewayIdMap.remove(user.getId());
				sessionIdMap.remove(user.getId());
			}
			
		}
	}



	public CheckJoinRoomMsg toMsg(User user) {
		CheckJoinRoomMsg msg = new CheckJoinRoomMsg();
		msg.setCreateUserId(this.roomDo.getCreateUserId());
		msg.setJoinGatewayId(user.getGatewayId());
		msg.setJoinSessionId(user.getSessionId());
		msg.setJoinUserId(user.getUserId());
		msg.setOptions(roomDo.getConfig().getOptions());
		msg.setRoomCheckId(roomDo.getRoomCheckId());
		msg.setRoomId(roomId);
		msg.setSceneId(roomDo.getSceneId().shortValue());
		List<UserInfo> infos = new ArrayList<UserInfo>();
		for (int i = 0; i < users.length; i++) {
			UserDO userDO = users[i];
			if(userDO!=null){
				UserInfo info = new UserInfo();
				info.setAvatar(userDO.getAvatar());
				Integer gateWayId = gatewayIdMap.get(userDO.getId());
				if(gateWayId==null){
					gateWayId = -1;
				}
				info.setGatewayId(gateWayId);
				Integer sessionId = sessionIdMap.get(userDO.getId());
				if(sessionId==null){
					sessionId = -1;
				}
				info.setSessionId(sessionId);
				info.setGold(userDO.getGold());
				info.setIp(userDO.getIp());
				info.setLatitude(userDO.getLatitude());
				info.setLocationIndex(i);
				info.setLongitude(userDO.getLongitude());
				info.setScore(0);
				info.setSex(userDO.getSex());
				info.setUserId(userDO.getId());
				info.setUserName(userDO.getName());
				infos.add(info);
			}
		}
		msg.setUserInfos(infos);
		msg.setUserMax(5);
		return msg;
	}
	
	
}
