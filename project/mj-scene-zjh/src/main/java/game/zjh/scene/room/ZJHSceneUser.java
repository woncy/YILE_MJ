package game.zjh.scene.room;

import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.net.message.Message;

import game.type.NoticeType;
import game.zjh.scene.net.ZJHSceneService;
import mj.net.message.game.GameUserInfo;
import mj.net.message.game.zjh.GameUserinfo2;
import mj.net.message.login.Notice;

public class ZJHSceneUser {
	  private int userId;//玩家ID
	    private String userName;//玩家昵称
	    private int locationIndex;//当前位置
	    private int sessionId = -1;//sessionID
	    private int gatewayId = -1;//gatewayId
	    private boolean joinGame;//是否加入游戏

	    private String avatar;//头像
	    /**
	     * 0:女生,1:男生,2:未知
	     */
	    private int sex;//男女
	    private int gold;//房卡数
	    private int score;//分数

	    //private RoomImpi room;
	    /**
	     * 在同一个局内压住次数!
	     */
	    //private int quanHuNums;
	    private int YaZhuNums;

	    @Autowired
	    private ZJHSceneService ZJHsceneService;
	    private boolean online;//是否在线
	    
	    private ZJHRoomImpl room;
	    private String ip;//Ip
	    /**
	     * 经度
	     */
	    private double longitude;
	    /**
	     * 纬度
	     */
	    private double latitude;

	    
	    public ZJHSceneUser() {

	    }

	    public GameUserinfo2 toMessage() {
	    	GameUserinfo2 m = new GameUserinfo2();
	        m.setUserId(userId);
	        m.setSex(sex);
	        m.setLocationIndex(locationIndex);
	        m.setUserName(userName);
	        m.setScore(score);
	        m.setOnline(online);
	        m.setIp(ip);
	        m.setAvatar(avatar);
	        return m;
	    }
	    
	    public void sendMessage(Message msg) {
	        if (gatewayId > -1) {
	        	ZJHsceneService.sendMessage(gatewayId, sessionId, msg);
	        }
	    }
	  
	    public void noticeError(String key) {
	        noticeError(key, new String[0]);
	    }
	    
	    public void noticeError(String key, String[] args) {
	        notice(key, args, NoticeType.ERROR, false);
	    }

	    public void noticeMain(String key) {
	        noticeMain(key, new String[0]);
	    }

	    public void noticeMain(String key, String[] args) {
	        notice(key, args, NoticeType.MAIN, false);
	    }

	    public void notice(String key, String[] args, NoticeType type) {
	        notice(key, args, type, false);
	    }

	    public void notice(String key, String[] args, NoticeType type, boolean reboot) {
	        if(joinGame){
	            sendMessage(new Notice(key, args, type.ordinal(), reboot));
	        }
	    }


		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public int getLocationIndex() {
			return locationIndex;
		}

		public void setLocationIndex(int locationIndex) {
			this.locationIndex = locationIndex;
		}

		public int getSessionId() {
			return sessionId;
		}

		public void setSessionId(int sessionId) {
			this.sessionId = sessionId;
		}

		public int getGatewayId() {
			return gatewayId;
		}

		public void setGatewayId(int gatewayId) {
			this.gatewayId = gatewayId;
		}

		public boolean isJoinGame() {
			return joinGame;
		}

		public void setJoinGame(boolean joinGame) {
			this.joinGame = joinGame;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public int getSex() {
			return sex;
		}

		public void setSex(int sex) {
			this.sex = sex;
		}

		public int getGold() {
			return gold;
		}

		public void setGold(int gold) {
			this.gold = gold;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public int getYaZhuNums() {
			return YaZhuNums;
		}

		public void setQuanHuNums(int YaZhuNums) {
			this.YaZhuNums = YaZhuNums;
		}

		public ZJHSceneService getZJHsceneService() {
			return ZJHsceneService;
		}

		public void setZJHsceneService(ZJHSceneService zJHsceneService) {
			ZJHsceneService = zJHsceneService;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		
		public ZJHRoomImpl getRoom() {
			return room;
		}

		public void setRoom(ZJHRoomImpl room) {
			this.room = room;
		}

		public void setYaZhuNums(int yaZhuNums) {
			YaZhuNums = yaZhuNums;
		}

		@Override
		public String toString() {
			return "ZJHSceneUser [userId=" + userId + 
					", userName=" + userName + 
					", locationIndex=" + locationIndex + 
					", sessionId=" + sessionId + 
					", gatewayId=" + gatewayId + 
					", joinGame=" + joinGame + 
					", ZJHSceneService=" + ZJHsceneService + 
					", sex=" + sex + 
					", gold=" + gold + 
					", score=" + score + 
					", avatar=" + avatar + 
					", online=" + online + 
					", ip=" + ip + 
					", longitude=" + longitude + 
					", latitude=" + latitude + "]";
		}
		  public void removeScore(int scoreNums) {
		        score -= scoreNums;
		    }

		    public void addScore(int scoreNums) {
		        score += scoreNums;
		    }

			public boolean isOnline() {
				return online;
			}

			public void setOnline(boolean online) {
				this.online = online;
			}
}
