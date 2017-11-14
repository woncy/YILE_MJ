package game.boss.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import game.boss.dao.dao.TbPkRoomUserDao;
import game.boss.dao.entity.TbPkRoomDO;
import game.boss.dao.entity.TbPkRoomUserDO;
import game.boss.dao.entity.UserDO;
import game.douniu.scene.msg.CheckJoinRoom2Msg;
import game.scene.msg.UserInfo;

/**
 * 
 * @ClassName: DouniuRoom
 * @Description: 房间简单实体
 * @author administor
 * @date 2017年7月10日
 *
 */

public class DouniuRoom {
	private TbPkRoomDO tbPkRoomDO;
	private Map<Integer, UserDO> map = new HashMap<>();
	private boolean start;
	private Map<Integer,Integer> userId_scores = new HashMap<Integer, Integer>();
	private TbPkRoomUserDao roomUserDao;
	
	private UserDO control;
	
	/*
	 * RoomDo @qixianghui get set
	 * 
	 */
	private int location = -1;

	public void addUser(UserDO user) {
		this.map.put(++location, user);
	}
	
    public void addScore(int userId,int score){
    	Integer userScore = userId_scores.get(userId);
        if(userScore == null){
        	userScore = score;
        }else{
        	userScore += score;
        }
        this.userId_scores.put(userId, userScore);
    }
    
    public int getScoreByUserId(int userId){
    	Integer score = userId_scores.get(userId);
    	if(score==null){
    		score = 0;
    	}
    	return score;
    }

    public DouniuRoom(TbPkRoomDO tbPkRoomDO,TbPkRoomUserDao roomUserDao) {
        this.tbPkRoomDO = tbPkRoomDO;
        this.roomUserDao = roomUserDao;
    }
    
	public DouniuRoom(TbPkRoomDO TbPkRoomDO) {
		this.tbPkRoomDO = TbPkRoomDO;
	}

	public DouniuRoom() {
		super();
	}

	public void removeUser(int userId) {
		map.entrySet().removeIf(entry -> entry.getValue().getId() == userId);
	}

	public TbPkRoomDO getTbPkRoomDO() {
		return tbPkRoomDO;
	}

	public void setTbPkRoomDO(TbPkRoomDO TbPkRoomDO) {
		this.tbPkRoomDO = TbPkRoomDO;
	}

	public Map<Integer, UserDO> getMap() {
		return map;
	}
	
	public UserDO getControl() {
		return control;
	}

	public void setControl(UserDO control) {
		this.control = control;
	}

	public CheckJoinRoom2Msg toMsg(User user) {
		final CheckJoinRoom2Msg m = new CheckJoinRoom2Msg();
		m.setUuid(tbPkRoomDO.getUuid());
		m.setUserMax(tbPkRoomDO.getUserMax());
		m.setCreateUserId(tbPkRoomDO.getCreateUserId());
		m.setRoomCheckId(tbPkRoomDO.getRoomCheckId());
		m.setRoomId(tbPkRoomDO.getId());
		m.setJoinGatewayId(user.getGatewayId());
		m.setJoinSessionId(user.getSessionId());
		m.setJoinUserId(user.getUserId());
		m.setOptions(tbPkRoomDO.getConfig().getOptions());
		ArrayList<UserInfo> userInfos = (ArrayList<UserInfo>) m.getUserInfos();
		Iterator<Entry<Integer, UserDO>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, UserDO> e = iterator.next();
			UserDO userDO = e.getValue();
			UserInfo userInfo = new UserInfo();

			userInfo.setUserId(userDO.getId());
			userInfo.setLocationIndex(e.getKey());
			userInfo.setSessionId(user.getSessionId());
			userInfo.setGatewayId((short) user.getGatewayId());
			userInfo.setIp(userDO.getIp());
			userInfo.setLongitude(userDO.getLongitude());
			userInfo.setLatitude(userDO.getLatitude());
			userInfo.setUserName(userDO.getName());
			userInfo.setAvatar(userDO.getAvatar());
			userInfo.setSex(userDO.getSex());
			userInfo.setGold(userDO.getGold());
              System.out.println("s=-=-=-"+  tbPkRoomDO.getId()+"........."+userDO.getId());
              System.out.println("TbPkRoomUserDO:::"+ TbPkRoomUserDO.Table.ROOM_ID);
              System.out.println("TbPkRoomUserDO:::"+ TbPkRoomUserDO.Table.USER_ID );
//			TbPkRoomUserDO roomUserDO = roomUserDao.findObject(TbPkRoomUserDO.Table.ROOM_ID, tbPkRoomDO.getId(),
//					TbPkRoomUserDO.Table.USER_ID, userDO.getId());
//			System.out.println("roomUserDo::::::"+ roomUserDO.getId());
//			Integer score = roomUserDO.getEndScore();
//			if (score == null) {
//				score = 0;
//			}
			//userInfo.setScore(score);  有问题
			
			userInfos.add(userInfo);
		}
		m.setUserInfos(userInfos);
		/*if(control != null){
			DouniuUserInfo userInfo = new DouniuUserInfo();

			userInfo.setUserId(control.getId());
			userInfo.setLocationIndex(-1);
			userInfo.setSessionId(user.getSessionId());
			userInfo.setGatewayId((short) user.getGatewayId());
			userInfo.setIp(control.getIp());
			userInfo.setLongitude(control.getLongitude());
			userInfo.setLatitude(control.getLatitude());
			userInfo.setUserName(control.getName());
			userInfo.setAvatar(control.getAvatar());
			userInfo.setSex(control.getSex());
			userInfo.setGold(control.getGold());
			
			m.setControlUser(userInfo);
		}*/
		return m;
	}
	@Override
	public String toString() {
		return "DouniuRoom [tbPkRoomDO=" + tbPkRoomDO + ", map=" + map
				+ ", start=" + start + ", userId_scores=" + userId_scores
				+ ", roomUserDao=" + roomUserDao + ", control=" + control
				+ ", location=" + location + "]";
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isStart() {
		return start;
	}

}
